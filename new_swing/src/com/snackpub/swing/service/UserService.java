package com.snackpub.swing.service;

import com.snackpub.swing.moduel.Log;
import com.snackpub.swing.moduel.User;

import java.util.List;

/**
 * 用户服务接口
 *
 * @author snackpub
 * @date 2020/1/10
 */
public interface UserService {

    /**
     * 保存用户信息
     *
     * @param user 用户模型
     * @return ${int}
     */
    int save(User user);

    /**
     * 保存用户信息
     *
     * @param user 用户模型
     * @return ${int}
     */
    int update(User user);

    /**
     * 根据流水号删除用户
     *
     * @param lsh 流水号
     */
    int delete(String lsh);

    /**
     * 根据流水号获取用户
     * @param lsh 流水号
     */
    User getUser(String lsh);


}
