package com.convenience.store.dao;

import com.convenience.store.entity.Supplier;

import java.util.List;

public interface SupplierDao {
    Supplier getSupplierById(Integer supplierId);
    List<Supplier> getAllSuppliers();
    int insertSupplier(Supplier supplier);
    int updateSupplier(Supplier supplier);
    int deleteSupplier(Integer supplierId);
}