package com.snackpub.swing.service.impl;

import com.snackpub.swing.constant.MessageConstant;
import com.snackpub.swing.constant.SystemConstant;
import com.snackpub.swing.exception.ServiceException;
import com.snackpub.swing.moduel.Bank;
import com.snackpub.swing.moduel.Log;
import com.snackpub.swing.service.ATMService;
import com.snackpub.swing.service.LogService;
import com.snackpub.util.JDBCUtil;
import com.snackpub.util.Func;
import com.snackpub.util.Printll;

import javax.swing.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Objects;
import java.util.Scanner;
import java.util.Vector;

/**
 * ATM操作具体实现类
 *
 * @author snackpub
 * @date 2021/1/8
 */
public class ATMServiceImpl implements ATMService {

    @Override
    public int add(Bank bank) {
        int count = 0;
        Connection conn = JDBCUtil.getConn();
        PreparedStatement ps = null;
        String sql = "INSERT INTO t_bank(card_number,password,residual,user_name,sex,create_time,user_lsh,del_flag,user_type) VALUES(?,?,?,?,?,?,?,?,?)";
        try {
            JDBCUtil.beginTransaction(conn);
            ps = conn.prepareStatement(sql);
            ps.setString(1, bank.getCardNumber());
            ps.setString(2, bank.getPassword());
            ps.setBigDecimal(3, bank.getResidual());
            ps.setString(4, bank.getUserName());
            ps.setInt(5, Func.sexConversion(bank.getSex()));
            ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            ps.setString(7, bank.getUserLsh());
            ps.setInt(8, SystemConstant.NON_DEL_FLAG);
            ps.setInt(9, SystemConstant.DEFAULT_USER_TYPE);
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
    public int update(Bank bank) {
        int count = 0;
        Connection conn = JDBCUtil.getConn();
        PreparedStatement ps = null;
        String sql = "UPDATE   t_bank SET card_number=?,password=?,user_name=?,sex=?  WHERE user_lsh=?";
        try {
            JDBCUtil.beginTransaction(conn);
            ps = conn.prepareStatement(sql);
            ps.setString(1, bank.getCardNumber());
            ps.setString(2, bank.getPassword());
            ps.setString(3, bank.getUserName());
            ps.setInt(4, Func.sexConversion(bank.getSex()));
            ps.setString(5, bank.getUserLsh());
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
    public int delete(String cardNum) {
        int count = 0;
        if (Func.isNotBlank(cardNum)) {
            // 查询账户是否存在
            Bank bank = getBank(cardNum);
            if (Objects.nonNull(bank)) {
                bank.setDelFlag(SystemConstant.HAV_DEL_FLAG);
                String userLsh = bank.getUserLsh();
                // 删除用户信息
                Connection conn = JDBCUtil.getConn();
                PreparedStatement ps = null;
                String sql = "UPDATE t_bank SET del_flag = ? WHERE card_number = ?";
                try {
                    JDBCUtil.beginTransaction(conn);
                    ps = conn.prepareStatement(sql);
                    ps.setInt(1, bank.getDelFlag());
                    ps.setString(2, bank.getCardNumber());
                    count = ps.executeUpdate();
                    if (count > 0) JDBCUtil.commitTransaction(conn);
                } catch (SQLException e) {
                    JDBCUtil.rollBackTransaction(conn);
                    throw new ServiceException("注销用户失败！", e);
                } finally {
                    JDBCUtil.closeConn(conn, ps, null);
                }
            }
        }
        return count;
    }

    @Override
    public Bank withdraw(Bank bank) {
        Bank rBank = null;
        Printll.prlnMsg("请输入取款金额: ");
        while (true) {
            Scanner input = new Scanner(System.in);
            BigDecimal money = input.nextBigDecimal();
            //compareTo -1表示小于，0是等于，1是大于
            if (Func.isMoney(money)
                    && money.compareTo(new BigDecimal(10000)) < 1) {
                Connection conn = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                LogService logService = new LogServiceImpl();
                Log log = new Log();
                try {
                    conn = JDBCUtil.getConn();
                    // 非自动提交事务
                    JDBCUtil.beginTransaction(conn);
                    // 查询当前账户余额
                    BigDecimal residual = getCurrentBankMoney(bank.getCardNumber());
                    // 钱不够取
                    if (residual.compareTo(money) < 0) {
                        Printll.logForMat("取款失败！账户余额不足:%s", residual.toString());
                        break;
                    } else {
                        // 取完后的余额
                        BigDecimal currentMoney = residual.subtract(money);
                        String sql2 = "UPDATE T_BANK SET residual = ? WHERE card_number = ?";

                        // 保存日志
                        String info = "账户[%s]于[%s]存入金额：[%s]";
                        String format = String.format(info, bank.getCardNumber(), Func.getDateTime(), money.toString());

                        log.setCardNumber(bank.getCardNumber());
                        log.setCreateBy(bank.getUserName());
                        log.setTitle("存款成功！");
                        log.setDetailedInfo(format);

                        ps = conn.prepareStatement(sql2);
                        ps.setBigDecimal(1, currentMoney);
                        ps.setString(2, bank.getCardNumber());
                        int n = ps.executeUpdate();
                        if (n > 0) {
                            JDBCUtil.commitTransaction(conn);
                            rBank = getBank(bank.getCardNumber());
                            Printll.logForMat("取款成功！当前账户余额为：%s", currentMoney.toString());
                        } else {
                            Printll.logForMat("取款失败！当前账户余额为：%s", residual.toString());
                        }
                        break;
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "存款失败！", "提示", JOptionPane.ERROR_MESSAGE);
                    log.setTitle("存款失败！");
                    logService.save(log);
                    // 抛出异常回滚，保证数据的一致性
                    JDBCUtil.rollBackTransaction(conn);
                    throw new ServiceException("存款失败！", e);
                } finally {
                    JDBCUtil.closeConn(conn, ps, rs);
                }

            } else if (money.intValue() % 100 != 0) {
                Printll.prlnMsg("取款金额最低100元整！");
            } else if (money.compareTo(BigDecimal.ZERO) < 1) {
                Printll.prlnMsg("取款金额不能小于0！");
            } else {
                Printll.prlnMsg("取出金额不能超过1W元！");
            }
        }
        return rBank;
    }

    @Override
    public void depositBtn(Bank bank) {
        while (true) {
            Printll.prlnMsg("请输入存款金额: ");
            Scanner input = new Scanner(System.in);
            BigDecimal money = input.nextBigDecimal();
            // compareTo -1表示小于，0是等于，1是大于
            if (Func.isMoney(money)
                    && money.compareTo(new BigDecimal(10000)) < 1) {
                Connection conn = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                LogService logService = new LogServiceImpl();
                Log log = new Log();
                try {
                    conn = JDBCUtil.getConn();
                    JDBCUtil.beginTransaction(conn);
                    String sql = "UPDATE T_BANK SET residual = ? WHERE card_number = ?";
                    ps = conn.prepareStatement(sql);
                    // 获取当前账户的余额
                    BigDecimal currentBankMoney = getCurrentBankMoney(bank.getCardNumber());
                    ps.setBigDecimal(1, money.add(currentBankMoney));
                    ps.setString(2, bank.getCardNumber());
                    int n = ps.executeUpdate();

                    String info = "账户[%s]于[%s]存入金额：[%s]";
                    String format = String.format(info, bank.getCardNumber(), Func.getDateTime(), money.toString());
                    log.setCardNumber(bank.getCardNumber());
                    log.setCreateBy(bank.getUserName());
                    log.setTitle("存款成功！");
                    log.setDetailedInfo(format);

                    if (n > 0) {
                        // 非自动提交事务
                        JDBCUtil.commitTransaction(conn);
                        Printll.prlnMsg(MessageConstant.DEFAULT_MESSAGE);
                        // 查询存款后的余额
                        BigDecimal currentMoney = getCurrentBankMoney(bank.getCardNumber());
                        if (Objects.nonNull(currentMoney))
                            Printll.logForMat("当前余额为：%s", currentMoney.toString());
                    } else {
                        Printll.logForMat(MessageConstant.FAIL_MESSAGE);
                    }
                    break;
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "存款失败！", "提示", JOptionPane.ERROR_MESSAGE);
                    log.setTitle("存款失败！");
                    logService.save(log);
                    JDBCUtil.rollBackTransaction(conn);
                    throw new ServiceException("存款失败！", e);
                } finally {
                    JDBCUtil.closeConn(conn, ps, null);
                }
            } else if (money.intValue() % 100 != 0) {
                Printll.prlnMsg("存款金额最低100元整！");
            } else if (money.compareTo(BigDecimal.ZERO) < 1) {
                Printll.prlnMsg("存款金额不能小于0！");
            } else {
                Printll.prlnMsg("存款金额不能超过1W元！");
            }
        }

    }

    @Override
    public void queryBtn(Bank bank) {
        Bank bank1 = getBank(bank.getCardNumber());
        if (Objects.nonNull(bank1)) {
            JOptionPane.showMessageDialog(null, "账户总余额为："+bank1.getResidual().toString(), "提示", JOptionPane.INFORMATION_MESSAGE);
            Printll.logForMat("账户总余额为：%s", bank1.getResidual().toString());
        } else {
            JOptionPane.showMessageDialog(null, "查询失败", "提示", JOptionPane.ERROR_MESSAGE);
            Printll.prlnMsg("查询失败！");
        }
    }

    @Override
    public boolean isExistBank(Bank bank) {
        boolean mark = false;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtil.getConn();
            String sql = "SELECT count(*) AS count FROM T_BANK WHERE card_number = ? AND password = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, bank.getCardNumber());
            ps.setString(2, bank.getPassword());
            rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                if (count > 0) {
                    mark = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConn(conn, ps, rs);
        }
        return mark;
    }

    @Override
    public BigDecimal getCurrentBankMoney(String cardNumber) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        BigDecimal residual = null;
        try {
            conn = JDBCUtil.getConn();
            String sql2 = "SELECT residual FROM T_BANK WHERE card_number = ?";
            ps = conn.prepareStatement(sql2);
            ps.setString(1, cardNumber);
            rs = ps.executeQuery();
            if (rs.next()) {
                residual = rs.getBigDecimal("residual");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConn(conn, ps, rs);
        }
        return residual;
    }

    @Override
    public Bank getBank(String cardNumber) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Bank bank = null;
        try {
            conn = JDBCUtil.getConn();
            String sql2 = "SELECT id,card_number,residual,user_name,sex,user_type,user_lsh,password FROM T_BANK WHERE card_number = ?";
            ps = conn.prepareStatement(sql2);
            ps.setString(1, cardNumber);
            rs = ps.executeQuery();
            if (rs.next()) {
                bank = new Bank();
                int id = rs.getInt("id");
                String cardNumberVal = rs.getString("card_number");
                String userName = rs.getString("user_name");
                int sex = rs.getInt("sex");
                BigDecimal residual = rs.getBigDecimal("residual");
                String lsh = rs.getString("user_lsh");
                bank.setId(id);
                bank.setCardNumber(cardNumberVal);
                bank.setUserName(userName);
                bank.setResidual(residual);
                bank.setSex(Func.sexConversion(sex));
                bank.setUserLsh(lsh);
                bank.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConn(conn, ps, rs);
        }
        return bank;
    }

    @Override
    public Vector getAllBank() {
        Vector list = new Vector();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtil.getConn();
            String sql2 = "SELECT id,card_number,residual,user_name,sex,user_type,create_time,del_flag,user_lsh FROM T_BANK WHERE 1=1";
            ps = conn.prepareStatement(sql2);
            rs = ps.executeQuery();
            Vector rowData;
            while (rs.next()) {
                rowData = new Vector();
                int id = rs.getInt("id");
                String cardNumberVal = rs.getString("card_number");
                String userName = rs.getString("user_name");
                int sex = rs.getInt("sex");
                BigDecimal residual = rs.getBigDecimal("residual");
                Timestamp createTime = rs.getTimestamp("create_time");
                int delFlag = rs.getInt("del_flag");
                String userLsh = rs.getString("user_lsh");
                rowData.add(userName);
                rowData.add(cardNumberVal);
                rowData.add(userLsh);
                rowData.add(createTime);
                rowData.add(Func.sexConversion(sex));
                rowData.add(Func.delFlagConversion(delFlag));
                list.add(rowData);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "查询失败！", "提示", JOptionPane.ERROR_MESSAGE);
            throw new ServiceException("查询失败", e);
        } finally {
            JDBCUtil.closeConn(conn, ps, rs);
        }
        return list;
    }

}
