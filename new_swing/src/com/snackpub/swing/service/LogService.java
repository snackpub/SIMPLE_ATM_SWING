package com.snackpub.swing.service;

import com.snackpub.swing.moduel.Log;

import java.util.List;

/**
 * 日志服务接口
 *
 * @author snackpub
 * @date 2020/1/9
 */
public interface LogService {

    /**
     * 保存日志信息
     *
     * @param log 日志模型
     * @return ${int}
     */
    int save(Log log);

    /**
     * 最近10条记录查询
     *
     * @param cardNumber 卡号
     * @return ${list}
     */
    List<Log> recentlyTenTimeLog(String cardNumber);

}
