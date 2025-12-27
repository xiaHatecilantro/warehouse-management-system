package com.convenience.store.dao;

import com.convenience.store.entity.Warehouse;

import java.util.List;

public interface WarehouseDao {
    Warehouse getWarehouseById(Integer warehouseId);
    List<Warehouse> getAllWarehouses();
    int insertWarehouse(Warehouse warehouse);
    int updateWarehouse(Warehouse warehouse);
    int deleteWarehouse(Integer warehouseId);
}