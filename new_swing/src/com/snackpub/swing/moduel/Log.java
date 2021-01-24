package com.snackpub.swing.moduel;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 存取款操作日志
 *
 * @author snackpub
 * @date 2021/1/9
 */
public class Log implements Serializable {

    private Integer id;

    /**
     * 操作用户
     */
    private String createBy;
    /**
     * 标题
     */
    private String title;

    /**
     * 操作详细信息
     */
    private String detailedInfo;

    private String cardNumber;

    private Timestamp createTime;

    public Log() {
    }

    public Log(Integer id, String createBy, String title, String detailedInfo, String cardNumber, Timestamp createTime) {
        this.id = id;
        this.createBy = createBy;
        this.title = title;
        this.detailedInfo = detailedInfo;
        this.cardNumber = cardNumber;
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetailedInfo() {
        return detailedInfo;
    }

    public void setDetailedInfo(String detailedInfo) {
        this.detailedInfo = detailedInfo;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", createBy='" + createBy + '\'' +
                ", title='" + title + '\'' +
                ", detailedInfo='" + detailedInfo + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
