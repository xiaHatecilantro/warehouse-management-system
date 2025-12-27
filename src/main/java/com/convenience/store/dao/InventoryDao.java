package com.convenience.store.dao;

import com.convenience.store.entity.Inventory;

import java.util.List;

/**
 * 库存数据访问接口
 * 定义库存相关的数据库操作方法
 */
public interface InventoryDao {
    /**
     * 根据库存ID获取库存记录
     * @param inventoryId 库存ID
     * @return 库存实体对象，如果没有找到则返回null
     */
    Inventory getInventoryById(Integer inventoryId);
    
    /**
     * 获取所有库存记录
     * @return 库存实体对象列表
     */
    List<Inventory> getAllInventory();
    
    /**
     * 插入库存记录
     * @param inventory 库存实体对象
     * @return 插入成功的记录数
     */
    int insertInventory(Inventory inventory);
    
    /**
     * 更新库存记录
     * @param inventory 库存实体对象（包含更新后的信息）
     * @return 更新成功的记录数
     */
    int updateInventory(Inventory inventory);
    
    /**
     * 删除库存记录
     * @param inventoryId 库存ID
     * @return 删除成功的记录数
     */
    int deleteInventory(Integer inventoryId);
    
    /**
     * 根据产品ID获取库存记录
     * @param productId 产品ID
     * @return 库存实体对象列表
     */
    List<Inventory> getInventoryByProductId(Integer productId);
}