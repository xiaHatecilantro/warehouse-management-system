package com.convenience.store.controller;

import com.convenience.store.entity.Inventory;
import com.convenience.store.service.InventoryService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InventoryDialogController {
    @FXML
    private TextField productIdField;
    @FXML
    private TextField warehouseIdField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField minStockField;

    private InventoryService inventoryService = new InventoryService();
    private Inventory inventory;
    private boolean isEditMode = false;
    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
        if (inventory != null) {
            isEditMode = true;
            // 填充现有数据
            productIdField.setText(String.valueOf(inventory.getProductId()));
            warehouseIdField.setText(String.valueOf(inventory.getWarehouseId()));
            quantityField.setText(String.valueOf(inventory.getQuantity()));
            minStockField.setText(String.valueOf(inventory.getMinStock()));
        } else {
            isEditMode = false;
            this.inventory = new Inventory();
        }
    }

    @FXML
    void saveInventory(ActionEvent event) {
        if (!validateInput()) {
            return;
        }

        // 设置库存属性
        inventory.setProductId(Integer.parseInt(productIdField.getText()));
        inventory.setWarehouseId(Integer.parseInt(warehouseIdField.getText()));
        inventory.setQuantity(Integer.parseInt(quantityField.getText()));
        inventory.setMinStock(Integer.parseInt(minStockField.getText()));

        boolean success;
        if (isEditMode) {
            success = inventoryService.updateInventory(inventory);
        } else {
            success = inventoryService.addInventory(inventory);
        }

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "操作成功", isEditMode ? "库存已成功修改" : "库存已成功添加");
            dialogStage.close();
        } else {
            showAlert(Alert.AlertType.ERROR, "操作失败", isEditMode ? "修改库存时发生错误" : "添加库存时发生错误");
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        dialogStage.close();
    }

    private boolean validateInput() {
        if (productIdField.getText().isEmpty() ||
            warehouseIdField.getText().isEmpty() ||
            quantityField.getText().isEmpty() ||
            minStockField.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "输入错误", "请填写所有必填字段");
            return false;
        }

        try {
            Integer.parseInt(productIdField.getText());
            Integer.parseInt(warehouseIdField.getText());
            Integer.parseInt(quantityField.getText());
            Integer.parseInt(minStockField.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "输入错误", "商品ID、仓库ID、数量和最小库存必须是整数");
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