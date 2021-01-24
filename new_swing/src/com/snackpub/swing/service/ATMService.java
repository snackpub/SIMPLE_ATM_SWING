package com.snackpub.swing.service;

import com.snackpub.swing.moduel.Bank;
import com.snackpub.swing.moduel.Log;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

/**
 * ATM 服务
 *
 * @author snackpub
 * @date 2021/1/8
 */
public interface ATMService {

    int add(Bank bank);

    /**
     *
     *
     * @param bank 用户模型
     * @return ${int}
     */
    int update(Bank bank);

    /**
     * 根据卡号逻辑删除
     *
     * @param cardNum 卡号
     * @return int
     */
    int delete(String cardNum);


    /**
     * 取款
     *
     * @param bank 账户信息
     */
    Bank withdraw(Bank bank);

    /**
     * 存款
     *
     * @param bank 账户信息
     */
    void depositBtn(Bank bank);

    /**
     * 查询
     *
     * @param bank 账户信息
     */
    void queryBtn(Bank bank);


    /**
     * 是否存在银行账户
     *
     * @param bank 银行卡实体模型
     */
    boolean isExistBank(Bank bank);

    /**
     * 根据卡号查询当前用户余额
     *
     * @param cardNumber 卡号
     * @return 余额
     */
    BigDecimal getCurrentBankMoney(String cardNumber);

    /**
     * 根据卡号查询当前用户余额
     *
     * @param cardNumber 卡号
     * @return 当前银行卡信息
     */
    Bank getBank(String cardNumber);



    /**
     * 查询所有账户
     *
     */
    Vector getAllBank();


}
