package com.convenience.store;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * 应用程序入口类 - 继承自Application类
 * 负责启动JavaFX应用程序，加载并显示登录界面
 */
public class App extends Application {
    /**
     * JavaFX应用程序的启动方法
     * 当应用程序启动时，JavaFX运行时会调用此方法
     * 
     * @param primaryStage 主舞台对象，是应用程序的主窗口
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            // 创建FXMLLoader对象，用于加载登录界面的FXML文件
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
            
            // 加载FXML文件，创建Scene对象，设置界面大小为400x300
            Scene scene = new Scene(root, 400, 300);
            
            // 设置主舞台的标题
            primaryStage.setTitle("便利店仓库管理系统 - 登录");
            
            // 设置主舞台的场景
            primaryStage.setScene(scene);
            
            // 设置窗口不可调整大小
            primaryStage.setResizable(false);
            
            // 显示主舞台
            primaryStage.show();
        } catch (IOException e) {
            // 捕获并打印加载FXML文件时可能发生的异常
            e.printStackTrace();
        }
    }

    /**
     * 应用程序的主方法
     * 是Java应用程序的入口点
     * 
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 测试数据库连接
        System.out.println("[DEBUG] Testing database connection before launching application...");
        com.convenience.store.service.UserService userService = new com.convenience.store.service.UserService();
        boolean isConnected = userService.testDatabaseConnection();
        System.out.println("[DEBUG] Database connection test result: " + isConnected);
        
        // 启动JavaFX应用程序
        // 这会调用start方法
        launch(args);
    }
}