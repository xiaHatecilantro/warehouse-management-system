package com.convenience.store.service;

import com.convenience.store.dao.ProductDao;
import com.convenience.store.entity.Product;
import com.convenience.store.util.MyBatisUtil;
import javafx.collections.FXCollections;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

/**
 * 商品服务类 - 处理商品相关的业务逻辑
 * 作为控制器层和数据访问层之间的桥梁，实现商品业务逻辑的封装
 */
public class ProductService {
    /**
     * 根据商品ID获取商品信息
     * 
     * @param productId 商品ID
     * @return Product - 商品对象
     */
    public Product getProductById(Integer productId) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            ProductDao productDao = sqlSession.getMapper(ProductDao.class);
            return productDao.getProductById(productId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取所有商品信息
     * 
     * @return List<Product> - 商品列表
     */
    public List<Product> getAllProducts() {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            ProductDao productDao = sqlSession.getMapper(ProductDao.class);
            return productDao.getAllProducts();
        } catch (Exception e) {
            e.printStackTrace();
            return FXCollections.observableArrayList();
        }
    }

    /**
     * 添加新商品
     * 
     * @param product 商品对象
     * @return boolean - 添加成功返回true，失败返回false
     */
    public boolean addProduct(Product product) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            ProductDao productDao = sqlSession.getMapper(ProductDao.class);
            int result = productDao.insertProduct(product);
            sqlSession.commit();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新商品信息
     * 
     * @param product 商品对象
     * @return boolean - 更新成功返回true，失败返回false
     */
    public boolean updateProduct(Product product) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            ProductDao productDao = sqlSession.getMapper(ProductDao.class);
            int result = productDao.updateProduct(product);
            sqlSession.commit();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除商品
     * 
     * @param productId 商品ID
     * @return boolean - 删除成功返回true，失败返回false
     */
    public boolean deleteProduct(Integer productId) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            ProductDao productDao = sqlSession.getMapper(ProductDao.class);
            int result = productDao.deleteProduct(productId);
            sqlSession.commit();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据条件搜索商品
     * 
     * @param params 搜索条件参数
     * @return List<Product> - 符合条件的商品列表
     */
    public List<Product> searchProducts(Map<String, Object> params) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            ProductDao productDao = sqlSession.getMapper(ProductDao.class);
            return productDao.searchProducts(params);
        } catch (Exception e) {
            e.printStackTrace();
            return FXCollections.observableArrayList();
        }
    }
}