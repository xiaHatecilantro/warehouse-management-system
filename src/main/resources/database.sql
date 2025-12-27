-- 创建数据库
CREATE DATABASE IF NOT EXISTS convenience_store;
USE convenience_store;

-- 1. 用户表
CREATE TABLE IF NOT EXISTS users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20),
    role ENUM('admin', 'staff') NOT NULL DEFAULT 'staff',
    status ENUM('active', 'inactive') NOT NULL DEFAULT 'active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT chk_role CHECK (role IN ('admin', 'staff')),
    CONSTRAINT chk_status CHECK (status IN ('active', 'inactive'))
);

-- 2. 商品表
CREATE TABLE IF NOT EXISTS products (
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    unit VARCHAR(20) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT chk_price CHECK (price > 0)
);

-- 3. 供应商表
CREATE TABLE IF NOT EXISTS suppliers (
    supplier_id INT PRIMARY KEY AUTO_INCREMENT,
    supplier_name VARCHAR(100) NOT NULL,
    contact_person VARCHAR(50) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(100),
    address TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 4. 仓库表
CREATE TABLE IF NOT EXISTS warehouses (
    warehouse_id INT PRIMARY KEY AUTO_INCREMENT,
    warehouse_name VARCHAR(100) NOT NULL,
    location VARCHAR(100) NOT NULL,
    capacity INT NOT NULL,
    manager_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_manager FOREIGN KEY (manager_id) REFERENCES users(user_id) ON DELETE SET NULL,
    CONSTRAINT chk_capacity CHECK (capacity > 0)
);

-- 5. 库存表
CREATE TABLE IF NOT EXISTS inventory (
    inventory_id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT NOT NULL,
    warehouse_id INT NOT NULL,
    quantity INT NOT NULL,
    min_stock INT NOT NULL DEFAULT 10,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_inventory_product FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE,
    CONSTRAINT fk_inventory_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id) ON DELETE CASCADE,
    CONSTRAINT chk_quantity CHECK (quantity >= 0),
    CONSTRAINT chk_min_stock CHECK (min_stock >= 0),
    UNIQUE KEY uk_product_warehouse (product_id, warehouse_id)
);

-- 6. 商品供应商关系表（多对多）
CREATE TABLE IF NOT EXISTS product_supplier (
    product_id INT NOT NULL,
    supplier_id INT NOT NULL,
    supply_price DECIMAL(10, 2) NOT NULL,
    last_supply_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (product_id, supplier_id),
    CONSTRAINT fk_ps_product FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE,
    CONSTRAINT fk_ps_supplier FOREIGN KEY (supplier_id) REFERENCES suppliers(supplier_id) ON DELETE CASCADE,
    CONSTRAINT chk_supply_price CHECK (supply_price > 0)
);

-- 触发器1：当库存数量低于最小库存时，更新商品状态
DELIMITER //
CREATE TRIGGER trg_check_stock_level AFTER UPDATE ON inventory
FOR EACH ROW
BEGIN
    IF NEW.quantity < NEW.min_stock THEN
        INSERT INTO stock_alerts (product_id, warehouse_id, current_quantity, min_stock, alert_time)
        VALUES (NEW.product_id, NEW.warehouse_id, NEW.quantity, NEW.min_stock, NOW())
        ON DUPLICATE KEY UPDATE current_quantity = NEW.quantity, alert_time = NOW();
    END IF;
END //
DELIMITER ;

-- 触发器2：自动更新商品的最后供应日期
DELIMITER //
CREATE TRIGGER trg_update_supply_date BEFORE INSERT ON product_supplier
FOR EACH ROW
BEGIN
    SET NEW.last_supply_date = NOW();
END //
DELIMITER ;

-- 存储过程1：获取商品库存信息
DELIMITER //
CREATE PROCEDURE GetProductInventory(IN p_product_id INT)
BEGIN
    SELECT p.product_name, w.warehouse_name, i.quantity, i.min_stock, i.last_updated
    FROM inventory i
    JOIN products p ON i.product_id = p.product_id
    JOIN warehouses w ON i.warehouse_id = w.warehouse_id
    WHERE i.product_id = p_product_id;
END //
DELIMITER ;

-- 存储过程2：批量更新库存
DELIMITER //
CREATE PROCEDURE UpdateInventoryBatch(IN p_product_id INT, IN p_warehouse_id INT, IN p_quantity_change INT)
BEGIN
    UPDATE inventory
    SET quantity = quantity + p_quantity_change
    WHERE product_id = p_product_id AND warehouse_id = p_warehouse_id;
    
    -- 如果库存记录不存在，则创建新记录
    IF ROW_COUNT() = 0 THEN
        INSERT INTO inventory (product_id, warehouse_id, quantity, min_stock)
        VALUES (p_product_id, p_warehouse_id, p_quantity_change, 10);
    END IF;
END //
DELIMITER ;

-- 创建库存预警表（用于触发器）
CREATE TABLE IF NOT EXISTS stock_alerts (
    alert_id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT NOT NULL,
    warehouse_id INT NOT NULL,
    current_quantity INT NOT NULL,
    min_stock INT NOT NULL,
    alert_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_alert_product FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE,
    CONSTRAINT fk_alert_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id) ON DELETE CASCADE,
    UNIQUE KEY uk_alert_product_warehouse (product_id, warehouse_id)
);

-- 插入测试数据
-- 1. 用户数据
INSERT INTO users (username, password, full_name, email, phone, role, status) VALUES
('admin', 'admin123', '管理员', 'admin@store.com', '13800138000', 'admin', 'active'),
('staff1', 'staff123', '员工1', 'staff1@store.com', '13800138001', 'staff', 'active'),
('staff2', 'staff123', '员工2', 'staff2@store.com', '13800138002', 'staff', 'active');

-- 2. 供应商数据
INSERT INTO suppliers (supplier_name, contact_person, phone, email, address) VALUES
('供应商A', '张三', '13900139001', 'supplierA@example.com', '深圳市南山区'),
('供应商B', '李四', '13900139002', 'supplierB@example.com', '阳江市江城区'),
('供应商C', '王五', '13900139003', 'supplierC@example.com', '广州市天河区');

-- 3. 商品数据
INSERT INTO products (product_name, category, unit, price, description) VALUES
('矿泉水', '饮料', '瓶', 2.00, '500ml瓶装矿泉水'),
('方便面', '食品', '包', 3.50, '红烧牛肉面'),
('面包', '食品', '个', 5.00, '全麦面包'),
('牙膏', '日用品', '支', 12.00, '薄荷味牙膏'),
('纸巾', '日用品', '包', 8.00, '抽纸');

-- 4. 仓库数据
INSERT INTO warehouses (warehouse_name, location, capacity, manager_id) VALUES
('主仓库', '深圳市南山区', 10000, 1),
('分仓库1', '阳江市江城区', 5000, 2),
('分仓库2', '广州市天河区', 5000, 3);

-- 5. 库存数据
INSERT INTO inventory (product_id, warehouse_id, quantity, min_stock) VALUES
(1, 1, 1000, 100),
(1, 2, 500, 50),
(2, 1, 800, 100),
(2, 3, 400, 50),
(3, 1, 300, 50),
(4, 2, 200, 30),
(5, 3, 150, 30);

-- 6. 商品供应商关系数据
INSERT INTO product_supplier (product_id, supplier_id, supply_price) VALUES
(1, 1, 1.50),
(1, 2, 1.60),
(2, 1, 3.00),
(3, 3, 4.50),
(4, 2, 10.00),
(5, 3, 7.00);