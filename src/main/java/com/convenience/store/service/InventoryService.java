package com.convenience.store.service;

import com.convenience.store.dao.InventoryDao;
import com.convenience.store.entity.Inventory;
import com.convenience.store.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * 库存服务类
 * 处理库存相关的业务逻辑，连接DAO层和控制器层
 * 负责库存的增删改查操作，并处理事务管理
 */
public class InventoryService {
    /**
     * 根据库存ID获取库存信息
     * @param inventoryId 库存ID
     * @return 库存实体对象，如果没有找到则返回null
     */
    public Inventory getInventoryById(Integer inventoryId) {
        // 使用try-with-resources自动管理SqlSession生命周期
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            // 获取DAO接口的实现
            InventoryDao inventoryDao = sqlSession.getMapper(InventoryDao.class);
            // 调用DAO方法获取库存信息
            return inventoryDao.getInventoryById(inventoryId);
        }
    }

    /**
     * 获取所有库存信息
     * @return 库存实体对象列表
     */
    public List<Inventory> getAllInventory() {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            InventoryDao inventoryDao = sqlSession.getMapper(InventoryDao.class);
            return inventoryDao.getAllInventory();
        }
    }

    /**
     * 添加库存记录
     * @param inventory 库存实体对象
     * @return 添加成功返回true，失败返回false
     */
    public boolean addInventory(Inventory inventory) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            InventoryDao inventoryDao = sqlSession.getMapper(InventoryDao.class);
            // 调用DAO方法插入库存记录
            int result = inventoryDao.insertInventory(inventory);
            // 提交事务
            sqlSession.commit();
            // 根据插入结果判断是否成功
            return result > 0;
        } catch (Exception e) {
            // 打印异常信息
            e.printStackTrace();
            // 发生异常返回false
            return false;
        }
    }

    /**
     * 更新库存记录
     * @param inventory 库存实体对象（包含更新后的信息）
     * @return 更新成功返回true，失败返回false
     */
    public boolean updateInventory(Inventory inventory) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            InventoryDao inventoryDao = sqlSession.getMapper(InventoryDao.class);
            // 调用DAO方法更新库存记录
            int result = inventoryDao.updateInventory(inventory);
            // 提交事务
            sqlSession.commit();
            // 根据更新结果判断是否成功
            return result > 0;
        } catch (Exception e) {
            // 打印异常信息
            e.printStackTrace();
            // 发生异常返回false
            return false;
        }
    }

    /**
     * 删除库存记录
     * @param inventoryId 库存ID
     * @return 删除成功返回true，失败返回false
     */
    public boolean deleteInventory(Integer inventoryId) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            InventoryDao inventoryDao = sqlSession.getMapper(InventoryDao.class);
            // 调用DAO方法删除库存记录
            int result = inventoryDao.deleteInventory(inventoryId);
            // 提交事务
            sqlSession.commit();
            // 根据删除结果判断是否成功
            return result > 0;
        }
    }

    /**
     * 根据产品ID获取库存记录
     * @param productId 产品ID
     * @return 库存实体对象列表
     */
    public List<Inventory> getInventoryByProductId(Integer productId) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            InventoryDao inventoryDao = sqlSession.getMapper(InventoryDao.class);
            return inventoryDao.getInventoryByProductId(productId);
        }
    }
}