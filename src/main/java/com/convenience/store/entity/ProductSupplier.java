package com.convenience.store.entity;

import java.time.LocalDateTime;

public class ProductSupplier {
    private Integer productId;
    private Integer supplierId;
    private Double supplyPrice;
    private LocalDateTime lastSupplyDate;
    private Product product;
    private Supplier supplier;

    // Getters and Setters
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Double getSupplyPrice() {
        return supplyPrice;
    }

    public void setSupplyPrice(Double supplyPrice) {
        this.supplyPrice = supplyPrice;
    }

    public LocalDateTime getLastSupplyDate() {
        return lastSupplyDate;
    }

    public void setLastSupplyDate(LocalDateTime lastSupplyDate) {
        this.lastSupplyDate = lastSupplyDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}