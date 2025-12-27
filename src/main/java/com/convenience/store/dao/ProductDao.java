package com.convenience.store.dao;

import com.convenience.store.entity.Product;

import java.util.List;
import java.util.Map;

/**
 * 产品数据访问接口
 * 定义产品相关的数据库操作方法
 */
public interface ProductDao {
    /**
     * 根据产品ID获取产品信息
     * @param productId 产品ID
     * @return 产品实体对象，如果没有找到则返回null
     */
    Product getProductById(Integer productId);
    
    /**
     * 获取所有产品信息
     * @return 产品实体对象列表
     */
    List<Product> getAllProducts();
    
    /**
     * 插入产品信息
     * @param product 产品实体对象
     * @return 插入成功的记录数
     */
    int insertProduct(Product product);
    
    /**
     * 更新产品信息
     * @param product 产品实体对象（包含更新后的信息）
     * @return 更新成功的记录数
     */
    int updateProduct(Product product);
    
    /**
     * 删除产品信息
     * @param productId 产品ID
     * @return 删除成功的记录数
     */
    int deleteProduct(Integer productId);
    
    /**
     * 搜索产品信息
     * @param params 搜索参数，包含产品名称、类别等条件
     * @return 符合条件的产品实体对象列表
     */
    List<Product> searchProducts(Map<String, Object> params);
}