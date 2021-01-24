package com.snackpub.swing.service.impl;

import com.snackpub.swing.constant.SystemConstant;
import com.snackpub.swing.exception.ServiceException;
import com.snackpub.swing.moduel.User;
import com.snackpub.swing.service.UserService;
import com.snackpub.util.Func;
import com.snackpub.util.JDBCUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Objects;

/**
 * 日志具体实现
 *
 * @author snackpub
 * @date 2021/1/9
 */
public class UserServiceImpl implements UserService {

    @Override
    public int save(User user) {
        int count = 0;
        Connection conn = JDBCUtil.getConn();
        PreparedStatement ps = null;
        String sql = "INSERT INTO t_user(user_name,id_card,tel,age,address,sex,create_time,del_flag,lsh) VALUES(?,?,?,?,?,?,?,?,?)";
        try {
            JDBCUtil.beginTransaction(conn);
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getiDCard());
            ps.setString(3, user.getTel());
            ps.setString(4, user.getAge());
            ps.setString(5, user.getAddress());
            ps.setInt(6, Func.sexConversion(user.getSex()));
            ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            ps.setInt(8, SystemConstant.NON_DEL_FLAG);
            ps.setString(9, user.getLsh());
            count = ps.executeUpdate();
            if (count > 0) JDBCUtil.commitTransaction(conn);
        } catch (SQLException e) {
            JDBCUtil.rollBackTransaction(conn);
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConn(conn, ps, null);
        }
        return count;
    }

    @Override
    public int update(User user) {
        int count = 0;
        Connection conn = JDBCUtil.getConn();
        PreparedStatement ps = null;
        String sql = "UPDATE  t_user set user_name = ?,id_card=?,tel=?,address=?,sex=?  WHERE lsh=?";
        try {
            JDBCUtil.beginTransaction(conn);
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getiDCard());
            ps.setString(3, user.getTel());
            ps.setString(4, user.getAddress());
            ps.setInt(5, Func.sexConversion(user.getSex()));
            ps.setString(6, user.getLsh());
            count = ps.executeUpdate();
            if (count > 0) JDBCUtil.commitTransaction(conn);
        } catch (SQLException e) {
            JDBCUtil.rollBackTransaction(conn);
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConn(conn, ps, null);
        }
        return count;
    }

    @Override
    public int delete(String lsh) {
        int count = 0;
        User user = getUser(lsh);
        if (Objects.nonNull(user)) {
            Connection conn = JDBCUtil.getConn();
            PreparedStatement ps = null;
            String sql = "UPDATE  t_user SET del_flag = ? WHERE lsh=?";
            try {
                JDBCUtil.beginTransaction(conn);
                ps = conn.prepareStatement(sql);
                ps.setInt(1, SystemConstant.HAV_DEL_FLAG);
                ps.setString(2, user.getLsh());
                count = ps.executeUpdate();
                if (count > 0) JDBCUtil.commitTransaction(conn);
            } catch (SQLException e) {
                JDBCUtil.rollBackTransaction(conn);
                throw new ServiceException("删除用户信息失败！", e);
            } finally {
                JDBCUtil.closeConn(conn, ps, null);
            }
        }

        return count;
    }

    @Override
    public User getUser(String lsh) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        try {
            conn = JDBCUtil.getConn();
            String sql2 = "SELECT id,user_name,id_card,tel,age,address,sex,create_time,del_flag,lsh,create_time FROM t_user" +
                    " WHERE lsh = ?";
            ps = conn.prepareStatement(sql2);
            ps.setString(1, lsh);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                int id = rs.getInt("id");
                String userName = rs.getString("user_name");
                String idCard = rs.getString("id_card");
                String tel = rs.getString("tel");
                int age = rs.getInt("age");
                String address = rs.getString("address");
                int sex = rs.getInt("sex");
                Timestamp createTime = rs.getTimestamp("create_time");
                String lsh1 = rs.getString("lsh");

                user.setId(id);
                user.setUserName(userName);
                user.setiDCard(idCard);
                user.setTel(tel);
                user.setAge(age + "");
                user.setAddress(address);
                user.setSex(Func.sexConversion(sex));
                user.setCreateTime(createTime);
                user.setLsh(lsh1);
            }
        } catch (SQLException e) {
            throw new ServiceException("查询失败！", e);
        } finally {
            JDBCUtil.closeConn(conn, ps, rs);
        }
        return user;
    }


}
