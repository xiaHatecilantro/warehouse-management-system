package com.convenience.store.controller;

import com.convenience.store.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * 登录控制器类 - 处理登录界面的用户交互
 * 负责验证用户登录信息并跳转到主界面
 */
public class LoginController {
    // 用户名输入框 - 通过FXML注入
    @FXML
    private TextField usernameField;
    
    // 密码输入框 - 通过FXML注入
    @FXML
    private PasswordField passwordField;
    
    // 登录按钮 - 通过FXML注入
    @FXML
    private Button loginButton;
    
    // 退出按钮 - 通过FXML注入
    @FXML
    private Button exitButton;

    // 用户服务对象，用于调用登录验证方法
    private UserService userService = new UserService();

    /**
     * 登录按钮点击事件处理方法
     * 处理用户登录请求，验证用户名和密码
     * 
     * @param event 事件对象，包含事件的触发源和上下文信息
     */
    @FXML
    void login(ActionEvent event) {
        // 获取用户输入的用户名和密码
        String username = usernameField.getText();
        String password = passwordField.getText();

        // 验证输入是否为空
        if (username.isEmpty() || password.isEmpty()) {
            // 显示错误提示
            showAlert(Alert.AlertType.ERROR, "登录失败", "请输入用户名和密码");
            return;
        }

        // 调用用户服务进行登录验证
        boolean success = userService.login(username, password);
        if (success) {
            // 登录成功，显示成功提示
            showAlert(Alert.AlertType.INFORMATION, "登录成功", "欢迎使用便利店仓库管理系统");
            
            // 跳转到主界面
            try {
                // 加载主界面FXML文件
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
                // 获取当前舞台(窗口)
                Stage stage = (Stage) loginButton.getScene().getWindow();
                // 创建主界面场景，设置宽度800px，高度600px
                Scene scene = new Scene(root, 800, 600);
                // 设置窗口标题
                stage.setTitle("便利店仓库管理系统");
                // 设置新场景
                stage.setScene(scene);
                // 设置窗口可调整大小
                stage.setResizable(true);
                // 显示主界面
                stage.show();
            } catch (IOException e) {
                // 加载失败时打印异常
                e.printStackTrace();
                // 显示错误提示
                showAlert(Alert.AlertType.ERROR, "系统错误", "无法加载主界面");
            }
        } else {
            // 登录失败，显示错误提示
            showAlert(Alert.AlertType.ERROR, "登录失败", "用户名或密码错误");
        }
    }

    /**
     * 退出按钮点击事件处理方法
     * 处理用户退出请求，关闭登录窗口
     * 
     * @param event 事件对象，包含事件的触发源和上下文信息
     */
    @FXML
    void exit(ActionEvent event) {
        // 获取当前舞台(窗口)
        Stage stage = (Stage) exitButton.getScene().getWindow();
        // 关闭窗口
        stage.close();
    }

    /**
     * 显示提示对话框
     * 封装Alert对话框的创建和显示逻辑，简化代码复用
     * 
     * @param alertType 提示类型(ERROR, INFORMATION等)
     * @param title 对话框标题
     * @param content 对话框内容
     */
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        // 创建提示对话框
        Alert alert = new Alert(alertType);
        // 设置对话框标题
        alert.setTitle(title);
        // 设置对话框头部文本(设为null不显示)
        alert.setHeaderText(null);
        // 设置对话框内容
        alert.setContentText(content);
        // 显示对话框并等待用户响应
        alert.showAndWait();
    }
}