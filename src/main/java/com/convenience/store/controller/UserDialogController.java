package com.convenience.store.controller;

import com.convenience.store.entity.User;
import com.convenience.store.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserDialogController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField fullNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private ComboBox<String> roleComboBox;
    @FXML
    private ComboBox<String> statusComboBox;

    private UserService userService = new UserService();
    private User user;
    private boolean isEditMode = false;
    private Stage dialogStage;

    @FXML
    public void initialize() {
        // 初始化角色ComboBox
        ObservableList<String> roles = FXCollections.observableArrayList("admin", "staff");
        roleComboBox.setItems(roles);
        
        // 初始化状态ComboBox
        ObservableList<String> statuses = FXCollections.observableArrayList("active", "inactive");
        statusComboBox.setItems(statuses);
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setUser(User user) {
        this.user = user;
        if (user != null) {
            isEditMode = true;
            // 填充现有数据
            usernameField.setText(user.getUsername());
            passwordField.setText(user.getPassword());
            fullNameField.setText(user.getFullName());
            emailField.setText(user.getEmail());
            phoneField.setText(user.getPhone());
            roleComboBox.setValue(user.getRole());
            statusComboBox.setValue(user.getStatus());
        } else {
            isEditMode = false;
            this.user = new User();
            // 设置默认值
            roleComboBox.setValue("staff");
            statusComboBox.setValue("active");
        }
    }

    @FXML
    void saveUser(ActionEvent event) {
        if (!validateInput()) {
            return;
        }

        // 设置用户属性
        user.setUsername(usernameField.getText());
        user.setPassword(passwordField.getText());
        user.setFullName(fullNameField.getText());
        user.setEmail(emailField.getText());
        user.setPhone(phoneField.getText());
        user.setRole(roleComboBox.getValue());
        user.setStatus(statusComboBox.getValue());

        boolean success;
        if (isEditMode) {
            success = userService.updateUser(user);
        } else {
            success = userService.addUser(user);
        }

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "操作成功", isEditMode ? "用户已成功修改" : "用户已成功添加");
            dialogStage.close();
        } else {
            showAlert(Alert.AlertType.ERROR, "操作失败", isEditMode ? "修改用户时发生错误" : "添加用户时发生错误");
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        dialogStage.close();
    }

    private boolean validateInput() {
        if (usernameField.getText().isEmpty() ||
            passwordField.getText().isEmpty() ||
            fullNameField.getText().isEmpty() ||
            emailField.getText().isEmpty() ||
            roleComboBox.getValue() == null ||
            statusComboBox.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "输入错误", "请填写所有必填字段");
            return false;
        }

        // 简单的邮箱格式验证
        String email = emailField.getText();
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            showAlert(Alert.AlertType.WARNING, "输入错误", "请输入有效的邮箱地址");
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