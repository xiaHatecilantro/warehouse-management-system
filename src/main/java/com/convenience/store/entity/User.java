package com.convenience.store.entity;

import java.time.LocalDateTime;

/**
 * 用户实体类 - 对应数据库中的user表
 * 用于封装用户信息，包括登录凭证、个人信息和账号状态
 */
public class User {
    // 用户ID - 主键，自增
    private Integer userId;
    // 用户名 - 登录账号，唯一
    private String username;
    // 密码 - 登录密码，存储加密后的字符串
    private String password;
    // 全名 - 用户的完整姓名
    private String fullName;
    // 邮箱 - 用户的联系邮箱
    private String email;
    // 电话 - 用户的联系电话
    private String phone;
    // 角色 - 用户权限，如admin(管理员)、user(普通用户)
    private String role;
    // 状态 - 用户账号状态，如active(激活)、inactive(未激活)
    private String status;
    // 创建时间 - 用户账号的创建时间
    private LocalDateTime createdAt;
    // 更新时间 - 用户信息的最后更新时间
    private LocalDateTime updatedAt;

    /**
     * 获取用户ID
     *
     * @return 用户ID
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取用户名
     *
     * @return 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取密码
     *
     * @return 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取全名
     *
     * @return 全名
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * 设置全名
     *
     * @param fullName 全名
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * 获取邮箱
     *
     * @return 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取电话
     *
     * @return 电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置电话
     *
     * @param phone 电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取角色
     *
     * @return 角色
     */
    public String getRole() {
        return role;
    }

    /**
     * 设置角色
     *
     * @param role 角色
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * 获取状态
     *
     * @return 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取创建时间
     *
     * @return 创建时间
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置创建时间
     *
     * @param createdAt 创建时间
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 获取更新时间
     *
     * @return 更新时间
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 设置更新时间
     *
     * @param updatedAt 更新时间
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}