package com.convenience.store.controller;

import com.convenience.store.entity.Product;
import com.convenience.store.service.ProductService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ProductDialogController {
    @FXML
    private TextField productNameField;
    @FXML
    private TextField categoryField;
    @FXML
    private TextField unitField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField descriptionField;

    private ProductService productService = new ProductService();
    private Product product;
    private boolean isEditMode = false;
    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setProduct(Product product) {
        this.product = product;
        if (product != null) {
            isEditMode = true;
            // 填充现有数据
            productNameField.setText(product.getProductName());
            categoryField.setText(product.getCategory());
            unitField.setText(product.getUnit());
            priceField.setText(String.valueOf(product.getPrice()));
            descriptionField.setText(product.getDescription());
        } else {
            isEditMode = false;
            this.product = new Product();
        }
    }

    @FXML
    void saveProduct(ActionEvent event) {
        if (!validateInput()) {
            return;
        }

        // 设置商品属性
        product.setProductName(productNameField.getText());
        product.setCategory(categoryField.getText());
        product.setUnit(unitField.getText());
        product.setPrice(Double.parseDouble(priceField.getText()));
        product.setDescription(descriptionField.getText());

        boolean success;
        if (isEditMode) {
            success = productService.updateProduct(product);
        } else {
            success = productService.addProduct(product);
        }

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "操作成功", isEditMode ? "商品已成功修改" : "商品已成功添加");
            dialogStage.close();
        } else {
            showAlert(Alert.AlertType.ERROR, "操作失败", isEditMode ? "修改商品时发生错误" : "添加商品时发生错误");
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        dialogStage.close();
    }

    private boolean validateInput() {
        if (productNameField.getText().isEmpty() ||
            categoryField.getText().isEmpty() ||
            unitField.getText().isEmpty() ||
            priceField.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "输入错误", "请填写所有必填字段");
            return false;
        }

        try {
            Double.parseDouble(priceField.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "输入错误", "价格必须是数字");
            return false;
        }

        return true;
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}