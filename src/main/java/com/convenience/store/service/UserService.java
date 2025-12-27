package com.convenience.store.service;

import com.convenience.store.dao.UserDao;
import com.convenience.store.entity.User;
import com.convenience.store.util.MyBatisUtil;
import javafx.collections.FXCollections;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

/**
 * 用户服务类 - 处理用户相关的业务逻辑
 * 作为控制器层和数据访问层之间的桥梁，实现业务逻辑的封装
 * 主要提供用户的CRUD操作、登录验证等核心功能
 */
public class UserService {
    /**
     * 根据用户ID获取用户信息
     * 
     * @param userId 用户ID
     * @return 用户对象，如果不存在则返回null
     */
    public User getUserById(Integer userId) {
        // 使用try-with-resources自动管理SqlSession资源，确保资源正确关闭
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            // 获取UserDao接口的代理实现类
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            // 调用数据访问层方法获取用户信息
            return userDao.getUserById(userId);
        } catch (Exception e) {
            // 捕获并打印异常信息，便于调试和问题定位
            e.printStackTrace();
            // 异常情况下返回null，确保上层调用有明确的返回值
            return null;
        }
    }

    /**
     * 根据用户名获取用户信息
     * 
     * @param username 用户名
     * @return 用户对象，如果不存在则返回null
     */
    public User getUserByUsername(String username) {
        System.out.println("[DEBUG] Getting user by username: " + username);
        // 使用try-with-resources自动管理SqlSession资源
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            System.out.println("[DEBUG] SqlSession created");
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            System.out.println("[DEBUG] UserDao obtained");
            User user = userDao.getUserByUsername(username);
            System.out.println("[DEBUG] getUserByUsername returned: " + user);
            return user;
        } catch (Exception e) {
            System.out.println("[DEBUG] Exception in getUserByUsername: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取所有用户信息
     * 
     * @return 用户列表，如果发生异常则返回空列表
     */
    public List<User> getAllUsers() {
        // 使用try-with-resources自动管理SqlSession资源
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            // 调用数据访问层方法获取所有用户信息
            return userDao.getAllUsers();
        } catch (Exception e) {
            e.printStackTrace();
            // 异常情况下返回空的ObservableList，避免NullPointerException
            return FXCollections.observableArrayList();
        }
    }

    /**
     * 添加新用户
     * 
     * @param user 用户对象，包含用户的基本信息
     * @return 添加成功返回true，否则返回false
     */
    public boolean addUser(User user) {
        // 使用try-with-resources自动管理SqlSession资源
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            // 调用数据访问层方法插入用户信息
            int result = userDao.insertUser(user);
            // 手动提交事务，确保数据持久化到数据库
            sqlSession.commit();
            // 根据影响行数判断是否添加成功（大于0表示成功）
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            // 异常情况下返回false，表示添加失败
            return false;
        }
    }

    /**
     * 更新用户信息
     * 
     * @param user 用户对象，包含更新后的用户信息
     * @return 更新成功返回true，否则返回false
     */
    public boolean updateUser(User user) {
        // 使用try-with-resources自动管理SqlSession资源
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            // 调用数据访问层方法更新用户信息
            int result = userDao.updateUser(user);
            // 手动提交事务
            sqlSession.commit();
            // 根据影响行数判断是否更新成功
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除用户
     * 
     * @param userId 用户ID
     * @return 删除成功返回true，否则返回false
     */
    public boolean deleteUser(Integer userId) {
        // 使用try-with-resources自动管理SqlSession资源
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            // 调用数据访问层方法删除用户
            int result = userDao.deleteUser(userId);
            // 手动提交事务
            sqlSession.commit();
            // 根据影响行数判断是否删除成功
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 批量删除用户
     * 
     * @param userIds 用户ID列表
     * @return 删除成功返回true，否则返回false
     */
    public boolean batchDeleteUsers(List<Integer> userIds) {
        // 使用try-with-resources自动管理SqlSession资源
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            // 调用数据访问层方法批量删除用户
            int result = userDao.batchDeleteUsers(userIds);
            // 手动提交事务
            sqlSession.commit();
            // 根据影响行数判断是否批量删除成功
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据条件搜索用户
     * 
     * @param params 搜索参数，包含username（用户名）、role（角色）、status（状态）等
     * @return 符合条件的用户列表
     */
    public List<User> searchUsers(Map<String, Object> params) {
        // 使用try-with-resources自动管理SqlSession资源
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            // 调用数据访问层方法根据条件搜索用户
            return userDao.searchUsers(params);
        } catch (Exception e) {
            e.printStackTrace();
            // 异常情况下返回空的ObservableList
            return FXCollections.observableArrayList();
        }
    }

    /**
     * 测试数据库连接是否正常
     * 
     * @return true if database connection is successful, false otherwise
     */
    public boolean testDatabaseConnection() {
        System.out.println("[DEBUG] Testing database connection...");
        
        try (SqlSession sqlSession = MyBatisUtil.getSqlSession()) {
            System.out.println("[DEBUG] SqlSession obtained successfully");
            
            // Test
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            List<User> allUsers = userDao.getAllUsers();
            
            System.out.println("[DEBUG] All users count: " + (allUsers != null ? allUsers.size() : "null"));
            if (allUsers != null) {
                for (User user : allUsers) {
                    System.out.println("[DEBUG] User: " + user.getUsername() + ", Password: " + user.getPassword() + ", Status: " + user.getStatus());
                }
            }
            
            return true;
        } catch (Exception e) {
            System.out.println("[DEBUG] Database connection test failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 用户登录验证
     * 
     * @param username 用户名
     * @param password 密码
     * @return 登录成功返回true，否则返回false
     */
    public boolean login(String username, String password) {
        System.out.println("[DEBUG] Login attempt for username: " + username);
        
        // 根据用户名获取用户信息
        User user = getUserByUsername(username);
        
        System.out.println("[DEBUG] User found: " + user);
        
        if (user == null) {
            System.out.println("[DEBUG] User not found");
            return false;
        }
        
        System.out.println("[DEBUG] User password: " + user.getPassword());
        System.out.println("[DEBUG] User status: " + user.getStatus());
        
        boolean passwordMatch = user.getPassword().equals(password);
        boolean statusActive = "active".equals(user.getStatus());
        
        System.out.println("[DEBUG] Password match: " + passwordMatch);
        System.out.println("[DEBUG] Status active: " + statusActive);
        
        // 执行完整的登录验证逻辑：
        // 1. 验证用户是否存在（user != null）
        // 2. 验证密码是否正确（user.getPassword().equals(password)）
        // 3. 验证用户状态是否为激活（"active".equals(user.getStatus())）
        return passwordMatch && statusActive;
    }
}