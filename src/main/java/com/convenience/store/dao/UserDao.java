package com.convenience.store.dao;

import com.convenience.store.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户数据访问接口 - 定义了用户相关的数据库操作
 * 使用MyBatis XML方式配置SQL映射关系
 */
public interface UserDao {
    /**
     * 根据用户ID获取用户信息
     * 
     * @param userId 用户ID
     * @return User - 用户对象
     */
    User getUserById(@Param("userId") Integer userId);

    /**
     * 根据用户名获取用户信息
     * 
     * @param username 用户名
     * @return User - 用户对象
     */
    User getUserByUsername(@Param("username") String username);

    /**
     * 获取所有用户信息
     * 
     * @return List<User> - 用户列表，按创建时间降序排序
     */
    List<User> getAllUsers();

    /**
     * 添加新用户
     * 
     * @param user 用户对象
     * @return int - 受影响的行数
     */
    int insertUser(User user);

    /**
     * 更新用户信息
     * 
     * @param user 用户对象
     * @return int - 受影响的行数
     */
    int updateUser(User user);

    /**
     * 删除用户
     * 
     * @param userId 用户ID
     * @return int - 受影响的行数
     */
    int deleteUser(@Param("userId") Integer userId);

    /**
     * 批量删除用户
     * 
     * @param userIds 用户ID列表
     * @return int - 受影响的行数
     */
    int batchDeleteUsers(@Param("userIds") List<Integer> userIds);

    /**
     * 根据条件搜索用户
     * 
     * @param params 搜索参数
     * @return List<User> - 符合条件的用户列表
     */
    List<User> searchUsers(@Param("params") Map<String, Object> params);

    /**
     * 用户登录验证
     * 
     * @param username 用户名
     * @param password 密码
     * @return User - 登录成功的用户对象
     */
    User login(@Param("username") String username, @Param("password") String password);
}