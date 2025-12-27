package com.convenience.store.entity;

import java.time.LocalDateTime;

/**
 * 库存实体类 - 表示商品在仓库中的库存信息
 * 对应数据库中的inventory表
 */
public class Inventory {
    // 库存记录ID - 主键
    private Integer inventoryId;
    
    // 商品ID - 外键，关联products表
    private Integer productId;
    
    // 仓库ID - 外键，关联warehouses表
    private Integer warehouseId;
    
    // 库存数量
    private Integer quantity;
    
    // 最小库存阈值，用于库存预警
    private Integer minStock;
    
    // 最后更新时间
    private LocalDateTime lastUpdated;
    
    // 关联的商品对象 - 用于一对一关联查询
    private Product product;
    
    // 关联的仓库对象 - 用于一对一关联查询
    private Warehouse warehouse;

    // Getters and Setters方法
    /**
     * 获取库存记录ID
     * @return 库存记录ID
     */
    public Integer getInventoryId() {
        return inventoryId;
    }

    /**
     * 设置库存记录ID
     * @param inventoryId 库存记录ID
     */
    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    /**
     * 获取商品ID
     * @return 商品ID
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * 设置商品ID
     * @param productId 商品ID
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * 获取仓库ID
     * @return 仓库ID
     */
    public Integer getWarehouseId() {
        return warehouseId;
    }

    /**
     * 设置仓库ID
     * @param warehouseId 仓库ID
     */
    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    /**
     * 获取库存数量
     * @return 库存数量
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * 设置库存数量
     * @param quantity 库存数量
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * 获取最小库存阈值
     * @return 最小库存阈值
     */
    public Integer getMinStock() {
        return minStock;
    }

    /**
     * 设置最小库存阈值
     * @param minStock 最小库存阈值
     */
    public void setMinStock(Integer minStock) {
        this.minStock = minStock;
    }

    /**
     * 获取最后更新时间
     * @return 最后更新时间
     */
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    /**
     * 设置最后更新时间
     * @param lastUpdated 最后更新时间
     */
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * 获取关联的商品对象
     * @return 商品对象
     */
    public Product getProduct() {
        return product;
    }

    /**
     * 设置关联的商品对象
     * @param product 商品对象
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * 获取关联的仓库对象
     * @return 仓库对象
     */
    public Warehouse getWarehouse() {
        return warehouse;
    }

    /**
     * 设置关联的仓库对象
     * @param warehouse 仓库对象
     */
    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
}