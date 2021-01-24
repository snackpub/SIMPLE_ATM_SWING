package com.snackpub.util;

import com.snackpub.swing.constant.SystemConstant;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * 通用工具类
 *
 * @author snackpub
 * @date 2021/1/9
 */
public class Func {


    private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 判断输入的金额是否正常
     * <p>
     * 1. 100的整数倍
     * 2. 非<=0的数值
     * </p>
     *
     * @param money 金额
     * @return bool
     */
    public static boolean isMoney(BigDecimal money) {
        boolean bool = false;
        //compareTo -1表示小于，0是等于，1是大于
        if (money.intValue() % 100 == 0 &&
                money.compareTo(BigDecimal.ZERO) > 0) {
            bool = true;
        }
        return bool;
    }

    /**
     * 性别转换
     */
    public static String sexConversion(int sex) {
        String val;
        if (sex == 1) {
            val = "男";
        } else if (sex == 2) {
            val = "女";
        } else {
            val = "人妖";
        }
        return val;
    }


    public static int sexConversion(String sex) {
        int val;
        if (sex.equals("男")) {
            val = 1;
        } else if (sex.equals("女")) {
            val = 2;
        } else {
            val = 0;
        }
        return val;
    }

    public static String delFlagConversion(int delFlag) {
        String r;
        if (delFlag == SystemConstant.NON_DEL_FLAG) {
            r = "正常";
        } else {
            r = "已注销";
        }
        return r;
    }

    public static String getDateTime() {
        return DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS).format(LocalDateTime.now(ZoneOffset.of("+8")));
    }


    public static boolean isNotBlank(String str) {
        boolean flag = false;
        if (Objects.nonNull(str) && !Objects.equals("", str)) {
            flag = true;
        }
        return flag;
    }

}
