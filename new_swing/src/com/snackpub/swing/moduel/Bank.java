package com.snackpub.swing.moduel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 银行卡实体模型
 *
 * @author snackpub
 * @date 2021/1/8
 */
public class Bank implements Serializable {
    private Integer id;
    /**
     * 卡号
     */
    private String cardNumber;
    /**
     * 密码
     */
    private String password;

    /**
     * 余额
     */
    private BigDecimal residual;
    /**
     * 用户名
     */
    private String userName;
    private String userLsh;
    /**
     * 性别
     */
    private String sex;

    private int userType;

    private java.sql.Timestamp createTime;
    private Timestamp updateTime;
    private int delFlag;

    public Bank() {
    }

    public Bank(String cardNumber, String password) {
        this.cardNumber = cardNumber;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getResidual() {
        return residual;
    }

    public void setResidual(BigDecimal residual) {
        this.residual = residual;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public String getUserLsh() {
        return userLsh;
    }

    public void setUserLsh(String userLsh) {
        this.userLsh = userLsh;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", password='" + password + '\'' +
                ", residual=" + residual +
                ", userName='" + userName + '\'' +
                ", sex='" + sex + '\'' +
                ", userType=" + userType +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", delFlag=" + delFlag +
                '}';
    }
}
