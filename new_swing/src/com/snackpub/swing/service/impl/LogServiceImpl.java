package com.snackpub.swing.service.impl;

import com.snackpub.swing.moduel.Log;
import com.snackpub.swing.service.LogService;
import com.snackpub.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 日志具体实现
 *
 * @author snackpub
 * @date 2021/1/9
 */
public class LogServiceImpl implements LogService {

    @Override
    public int save(Log log) {
        int count = 0;
        Connection conn = JDBCUtil.getConn();
        PreparedStatement ps = null;
        String sql = "INSERT INTO T_LOG(create_by,title,detailed_info,card_number,create_time) VALUES(?,?,?,?,?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, log.getCreateBy());
            ps.setString(2, log.getTitle());
            ps.setString(3, log.getDetailedInfo());
            ps.setString(4, log.getCardNumber());
            ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            count = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConn(conn, ps, null);
        }
        return count;
    }

    @Override
    public List<Log> recentlyTenTimeLog(String cardNumber) {
        List<Log> list = new ArrayList<>();
        Connection conn = JDBCUtil.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select id,create_by,title,detailed_info,card_number,create_time FROM T_LOG WHERE card_number = ? ORDER BY create_time desc";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, cardNumber);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String createBy = rs.getString(2);
                String title = rs.getString(3);
                String detailedInfo = rs.getString(4);
                String cardNumberV = rs.getString(5);
                Timestamp createTime = rs.getTimestamp(6);
                Log log1 = new Log(id, createBy, title, detailedInfo, cardNumberV, createTime);
                list.add(log1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConn(conn, ps, rs);
        }
        return list;
    }
}
