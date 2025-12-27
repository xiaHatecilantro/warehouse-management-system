package com.convenience.store.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * MyBatis工具类 - 负责管理SqlSessionFactory和SqlSession的创建与关闭
 * 提供了数据库连接的核心功能，采用单例模式确保SqlSessionFactory的唯一性
 */
public class MyBatisUtil {
    // SqlSessionFactory实例，用于创建SqlSession对象
    // 采用静态成员变量确保在应用程序生命周期内只创建一次
    private static SqlSessionFactory sqlSessionFactory;

    // 静态初始化块 - 在类加载时执行，用于初始化SqlSessionFactory
    static {
        try {
            // MyBatis配置文件的路径
            String resource = "mybatis-config.xml";
            
            // 通过Resources类加载配置文件，获取输入流
            InputStream inputStream = Resources.getResourceAsStream(resource);
            
            // 使用SqlSessionFactoryBuilder创建SqlSessionFactory实例
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            // 打印异常信息
            e.printStackTrace();
            // 抛出运行时异常，表明MyBatis初始化失败
            throw new RuntimeException("MyBatis初始化失败", e);
        }
    }

    /**
     * 获取SqlSession对象
     * SqlSession是MyBatis与数据库交互的主要接口
     * 
     * @return SqlSession对象
     */
    public static SqlSession getSqlSession() {
        // 调用SqlSessionFactory的openSession方法创建SqlSession
        // 默认情况下，SqlSession不会自动提交事务
        return sqlSessionFactory.openSession();
    }

    /**
     * 关闭SqlSession对象
     * 释放数据库连接资源
     * 
     * @param sqlSession 要关闭的SqlSession对象
     */
    public static void closeSqlSession(SqlSession sqlSession) {
        // 检查SqlSession是否为空
        if (sqlSession != null) {
            // 关闭SqlSession
            sqlSession.close();
        }
    }
}