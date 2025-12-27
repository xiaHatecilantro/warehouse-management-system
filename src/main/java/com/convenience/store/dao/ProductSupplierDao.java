package com.convenience.store.dao;

import com.convenience.store.entity.ProductSupplier;

import java.util.List;
import java.util.Map;

public interface ProductSupplierDao {
    List<ProductSupplier> getProductSuppliers();
    List<ProductSupplier> getSuppliersByProductId(Integer productId);
    List<ProductSupplier> getProductsBySupplierId(Integer supplierId);
    int insertProductSupplier(ProductSupplier productSupplier);
    int deleteProductSupplier(Map<String, Object> params);
}