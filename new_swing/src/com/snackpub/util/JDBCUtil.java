package com.snackpub.util;


import java.sql.*;

/**
 * 数据库连接管理
 *
 * @author lyn
 * @date 2021/01/02
 */
public class JDBCUtil {

    //初始化数据连接
    private static final String URL = "jdbc:mysql://localhost:3306/awt_swing?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "snackpub";

    /**
     * 获取数据库连接
     *
     * @return 数据库连接
     * @author lyn
     * @date 2021/01/02
     */
    public static Connection getConn() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            System.out.println("获取数据库连接异常!");
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 关闭数据库连接
     *
     * @author lyn 李雨凝
     * @date 2021/01/02
     */
    public static void closeConn(Connection conn, PreparedStatement pres, ResultSet resultSet) {

        try {
            if (resultSet != null) resultSet.close();
        } catch (SQLException e) {
            System.err.println("关闭数据库结果集异常!");
            e.printStackTrace();
        }

        try {
            if (pres != null) pres.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.err.println("关闭数据库连接异常!");
            e.printStackTrace();
        }
    }


    /**
     * 开始事务
     */
    public static void beginTransaction(Connection cnn) {
        if (cnn != null) {
            try {
                if (cnn.getAutoCommit()) {
                    cnn.setAutoCommit(false);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 提交事务
     */
    public static void commitTransaction(Connection cnn) {
        if (cnn != null) {
            try {
                if (!cnn.getAutoCommit()) {
                    cnn.commit();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 回滚事务
     */
    public static void rollBackTransaction(Connection cnn) {
        if (cnn != null) {
            try {
                if (!cnn.getAutoCommit()) {
                    cnn.rollback();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
