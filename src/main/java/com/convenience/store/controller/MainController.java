package com.convenience.store.controller;

import com.convenience.store.entity.User;
import com.convenience.store.entity.Product;
import com.convenience.store.entity.Inventory;
import com.convenience.store.service.UserService;
import com.convenience.store.service.ProductService;
import com.convenience.store.service.InventoryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * 系统主控制器 - 管理系统主界面的所有功能
 * 负责用户管理、商品管理和库存管理的控制逻辑
 * 是整个系统的核心控制器，协调各个功能模块
 */
public class MainController {
    @FXML
    private Text welcomeText;
    
    // 用户管理相关控件
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, Integer> userIdCol;
    @FXML
    private TableColumn<User, String> usernameCol;
    @FXML
    private TableColumn<User, String> fullNameCol;
    @FXML
    private TableColumn<User, String> emailCol;
    @FXML
    private TableColumn<User, String> phoneCol;
    @FXML
    private TableColumn<User, String> roleCol;
    @FXML
    private TableColumn<User, String> statusCol;
    @FXML
    private Button addUserButton;
    @FXML
    private Button updateUserButton;
    @FXML
    private Button deleteUserButton;
    @FXML
    private Button batchDeleteUserButton;
    @FXML
    private TextField searchUserField;
    @FXML
    private Button searchUserButton;

    // 商品管理相关控件
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> productIdCol;
    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, String> categoryCol;
    @FXML
    private TableColumn<Product, String> unitCol;
    @FXML
    private TableColumn<Product, Double> priceCol;
    @FXML
    private TableColumn<Product, String> descriptionCol;
    @FXML
    private Button addProductButton;
    @FXML
    private Button updateProductButton;
    @FXML
    private Button deleteProductButton;
    @FXML
    private TextField searchProductField;
    @FXML
    private Button searchProductButton;

    // 库存管理相关控件
    @FXML
    private TableView<Inventory> inventoryTable;
    @FXML
    private TableColumn<Inventory, Integer> inventoryInventoryIdCol;
    @FXML
    private TableColumn<Inventory, String> inventoryProductNameCol;
    @FXML
    private TableColumn<Inventory, String> inventoryWarehouseNameCol;
    @FXML
    private TableColumn<Inventory, Integer> inventoryQuantityCol;
    @FXML
    private TableColumn<Inventory, Integer> inventoryMinStockCol; // 最小库存列
    @FXML
    private TableColumn<Inventory, String> inventoryLastUpdatedCol; // 最后更新时间列
    
    // 库存管理按钮
    @FXML
    private Button addInventoryButton;           // 添加库存按钮
    @FXML
    private Button updateInventoryButton;        // 更新库存按钮
    @FXML
    private Button deleteInventoryButton;        // 删除库存按钮
    
    // 库存搜索控件
    @FXML
    private TextField searchInventoryField;      // 库存搜索输入框
    @FXML
    private Button searchInventoryButton;        // 库存搜索按钮

    // ===============================
    // 服务层实例
    // ===============================
    private UserService userService = new UserService();          // 用户服务 - 处理用户相关业务逻辑
    private ProductService productService = new ProductService(); // 商品服务 - 处理商品相关业务逻辑
    private InventoryService inventoryService = new InventoryService(); // 库存服务 - 处理库存相关业务逻辑

    // ===============================
    // 数据集合 - 用于JavaFX表格显示
    // ===============================
    private ObservableList<User> userData = FXCollections.observableArrayList();          // 用户数据集合
    private ObservableList<Product> productData = FXCollections.observableArrayList();     // 商品数据集合
    private ObservableList<Inventory> inventoryData = FXCollections.observableArrayList(); // 库存数据集合

    /**
     * 初始化方法 - 界面加载完成后自动调用
     * 负责初始化所有表格和加载初始数据
     */
    @FXML
    public void initialize() {
        // 初始化用户表并加载数据
        initUserTable();
        loadUserData();
        
        // 初始化商品表并加载数据
        initProductTable();
        loadProductData();
        
        // 初始化库存表并加载数据
        initInventoryTable();
        loadInventoryData();
        
        // 设置欢迎文本
        welcomeText.setText("欢迎使用便利店仓库管理系统");
    }
//------------------------用户页---------------------//
    // 初始化用户表列
    private void initUserTable() {
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        fullNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    /**
     * 加载用户数据到用户表格
     * 从用户服务层获取所有用户信息，并更新表格显示
     */
    private void loadUserData() {
        // 调用UserService的getAllUsers方法获取所有用户信息
        List<User> users = userService.getAllUsers();
        
        // 清空现有用户数据集合
        userData.clear();
        
        // 将获取到的用户列表添加到数据集合中
        userData.addAll(users);
        
        // 设置用户表格的数据源
        userTable.setItems(userData);
    }

    // 显示添加用户对话框
    @FXML
    void showAddUserDialog(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UserDialog.fxml"));
            Parent root = loader.load();
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle("添加用户");
            dialogStage.setScene(new Scene(root, 400, 350));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            
            UserDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setUser(null);
            
            dialogStage.showAndWait();
            loadUserData(); // 刷新用户列表
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "系统错误", "无法加载添加用户对话框");
        }
    }

    // 显示修改用户对话框
    @FXML
    void showUpdateUserDialog(ActionEvent event) {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            showAlert(Alert.AlertType.WARNING, "提示", "请选择要修改的用户");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UserDialog.fxml"));
            Parent root = loader.load();
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle("修改用户");
            dialogStage.setScene(new Scene(root, 400, 350));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            
            UserDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setUser(selectedUser);
            
            dialogStage.showAndWait();
            loadUserData(); // 刷新用户列表
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "系统错误", "无法加载修改用户对话框");
        }
    }

    // 删除用户
    @FXML
    void deleteUser(ActionEvent event) {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            showAlert(Alert.AlertType.WARNING, "提示", "请选择要删除的用户");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("确认删除");
        alert.setHeaderText(null);
        alert.setContentText("确定要删除用户 " + selectedUser.getUsername() + " 吗？");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                boolean success = userService.deleteUser(selectedUser.getUserId());
                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "删除成功", "用户已成功删除");
                    loadUserData();
                } else {
                    showAlert(Alert.AlertType.ERROR, "删除失败", "删除用户时发生错误");
                }
            }
        });
    }

    // 批量删除用户
    @FXML
    void batchDeleteUsers(ActionEvent event) {
        ObservableList<User> selectedUsers = userTable.getSelectionModel().getSelectedItems();
        if (selectedUsers.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "提示", "请选择要删除的用户");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("确认批量删除");
        alert.setHeaderText(null);
        alert.setContentText("确定要删除选中的 " + selectedUsers.size() + " 个用户吗？");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                List<Integer> userIds = selectedUsers.stream().map(User::getUserId).toList();
                boolean success = userService.batchDeleteUsers(userIds);
                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "删除成功", "选中的用户已成功删除");
                    loadUserData();
                } else {
                    showAlert(Alert.AlertType.ERROR, "删除失败", "批量删除用户时发生错误");
                }
            }
        });
    }

    // 搜索用户
    @FXML
    void searchUsers(ActionEvent event) {
        String username = searchUserField.getText();
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        
        List<User> users = userService.searchUsers(params);
        userData.clear();
        userData.addAll(users);
        userTable.setItems(userData);
    }
//---------------------------------商品页------------------------------//
    // 初始化商品表列
    private void initProductTable() {
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        unitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    // 加载商品数据
    private void loadProductData() {
        List<Product> products = productService.getAllProducts();
        productData.clear();
        productData.addAll(products);
        productTable.setItems(productData);
    }

    // 显示添加商品对话框
    @FXML
    void showAddProductDialog(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ProductDialog.fxml"));
            Parent root = loader.load();
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle("添加商品");
            dialogStage.setScene(new Scene(root, 400, 300));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            
            ProductDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setProduct(null);
            
            dialogStage.showAndWait();
            loadProductData(); // 刷新商品列表
            loadInventoryData(); // 刷新库存列表
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "系统错误", "无法加载添加商品对话框");
        }
    }

    // 显示修改商品对话框
    @FXML
    void showUpdateProductDialog(ActionEvent event) {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            showAlert(Alert.AlertType.WARNING, "提示", "请选择要修改的商品");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ProductDialog.fxml"));
            Parent root = loader.load();
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle("修改商品");
            dialogStage.setScene(new Scene(root, 400, 300));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            
            ProductDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setProduct(selectedProduct);
            
            dialogStage.showAndWait();
            loadProductData(); // 刷新商品列表
            loadInventoryData(); // 刷新库存列表
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "系统错误", "无法加载修改商品对话框");
        }
    }

    // 删除商品
    @FXML
    void deleteProduct(ActionEvent event) {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            showAlert(Alert.AlertType.WARNING, "提示", "请选择要删除的商品");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("确认删除");
        alert.setHeaderText(null);
        alert.setContentText("确定要删除商品 " + selectedProduct.getProductName() + " 吗？");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                boolean success = productService.deleteProduct(selectedProduct.getProductId());
                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "删除成功", "商品已成功删除");
                    loadProductData();
                    loadInventoryData(); // 同时更新库存数据
                } else {
                    showAlert(Alert.AlertType.ERROR, "删除失败", "删除商品时发生错误");
                }
            }
        });
    }

    // 搜索商品
    @FXML
    void searchProducts(ActionEvent event) {
        String productName = searchProductField.getText();
        Map<String, Object> params = new HashMap<>();
        params.put("productName", productName);
        
        List<Product> products = productService.searchProducts(params);
        productData.clear();
        productData.addAll(products);
        productTable.setItems(productData);
    }

    //------------------------------库存页----------------------------//
    // 初始化库存表列
    private void initInventoryTable() {
        inventoryInventoryIdCol.setCellValueFactory(new PropertyValueFactory<>("inventoryId"));
        
        // 使用自定义CellValueFactory处理商品名称
        inventoryProductNameCol.setCellValueFactory(cellData -> {
            Inventory inventory = cellData.getValue();
            if (inventory != null && inventory.getProduct() != null) {
                return new javafx.beans.property.SimpleStringProperty(inventory.getProduct().getProductName());
            } else {
                return new javafx.beans.property.SimpleStringProperty("");
            }
        });
        
        // 使用自定义CellValueFactory处理仓库名称
        inventoryWarehouseNameCol.setCellValueFactory(cellData -> {
            Inventory inventory = cellData.getValue();
            if (inventory != null && inventory.getWarehouse() != null) {
                return new javafx.beans.property.SimpleStringProperty(inventory.getWarehouse().getWarehouseName());
            } else {
                return new javafx.beans.property.SimpleStringProperty("");
            }
        });
        
        inventoryQuantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        inventoryMinStockCol.setCellValueFactory(new PropertyValueFactory<>("minStock"));
        inventoryLastUpdatedCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdated"));
    }

    // 加载库存数据
    private void loadInventoryData() {
        List<Inventory> inventory = inventoryService.getAllInventory();
        inventoryData.clear();
        inventoryData.addAll(inventory);
        inventoryTable.setItems(inventoryData);
    }

    /**
     * 显示添加库存对话框
     * 1. 加载库存对话框的FXML界面
     * 2. 设置对话框的属性（标题、大小、模态性）
     * 3. 初始化控制器并传递必要的信息
     * 4. 显示对话框并等待用户操作
     * 5. 操作完成后刷新库存列表
     */
    @FXML
    void showAddInventoryDialog(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/InventoryDialog.fxml"));
            Parent root = loader.load();
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle("添加库存");
            dialogStage.setScene(new Scene(root, 400, 250));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            
            InventoryDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setInventory(null);
            
            dialogStage.showAndWait();
            loadInventoryData(); // 刷新库存列表
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "系统错误", "无法加载添加库存对话框");
        }
    }

    /**
     * 显示修改库存对话框
     * 1. 检查是否选择了要修改的库存记录
     * 2. 加载库存对话框的FXML界面
     * 3. 设置对话框的属性（标题、大小、模态性）
     * 4. 初始化控制器并传递选中的库存记录
     * 5. 显示对话框并等待用户操作
     * 6. 操作完成后刷新库存列表
     */
    @FXML
    void showUpdateInventoryDialog(ActionEvent event) {
        Inventory selectedInventory = inventoryTable.getSelectionModel().getSelectedItem();
        if (selectedInventory == null) {
            showAlert(Alert.AlertType.WARNING, "提示", "请选择要修改的库存记录");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/InventoryDialog.fxml"));
            Parent root = loader.load();
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle("修改库存");
            dialogStage.setScene(new Scene(root, 400, 250));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            
            InventoryDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setInventory(selectedInventory);
            
            dialogStage.showAndWait();
            loadInventoryData(); // 刷新库存列表
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "系统错误", "无法加载修改库存对话框");
        }
    }

    /**
     * 删除库存记录
     * 1. 检查是否选择了要删除的库存记录
     * 2. 显示确认删除对话框
     * 3. 如果用户确认删除，则调用服务层删除库存记录
     * 4. 根据删除结果显示相应提示
     * 5. 刷新库存列表
     */
    @FXML
    void deleteInventory(ActionEvent event) {
        Inventory selectedInventory = inventoryTable.getSelectionModel().getSelectedItem();
        if (selectedInventory == null) {
            showAlert(Alert.AlertType.WARNING, "提示", "请选择要删除的库存记录");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("确认删除");
        alert.setHeaderText(null);
        alert.setContentText("确定要删除该库存记录吗？");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                boolean success = inventoryService.deleteInventory(selectedInventory.getInventoryId());
                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "删除成功", "库存记录已成功删除");
                    loadInventoryData();
                } else {
                    showAlert(Alert.AlertType.ERROR, "删除失败", "删除库存记录时发生错误");
                }
            }
        });
    }

    /**
     * 搜索库存
     * 1. 获取搜索关键词
     * 2. 根据关键词搜索库存记录
     * 3. 目前简化实现，直接刷新显示所有库存
     * 4. 显示功能提示信息
     */
    @FXML
    void searchInventory(ActionEvent event) {
        // 获取搜索关键词
        String keyword = searchInventoryField.getText().trim();
        try {
            List<Inventory> inventories;
            // 如果关键词为空，则加载所有库存数据
            if (keyword.isEmpty()) {
                inventories = inventoryService.getAllInventory();
            } else {
                // 根据关键词搜索库存（目前简化实现，直接返回所有库存）
                // 后续可扩展为根据产品名称搜索
                inventories = inventoryService.getAllInventory();
            }
            // 更新表格显示
            inventoryData.clear();
            inventoryData.addAll(inventories);
            inventoryTable.setItems(inventoryData);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "搜索错误", "搜索库存时发生错误");
        }
    }

    // 显示提示信息
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}