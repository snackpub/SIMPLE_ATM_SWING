package com.snackpub.util;

import java.util.Objects;

/**
 * system print
 *
 * @author snackpub
 * @date 2021/1/8
 */
public final class Printll {

    private static final String style = "=====================================================";

    public static void prln() {
        System.out.println();
    }

    public static void prlnMsg(String strMsg) {
        prln();
        String printFormat = style + "\n" + strMsg + "\n" + style;
        System.err.println(printFormat);
    }

    public static void logForMat(String message, Object... args) {
        String tip = String.format(message, args);
        System.out.println(tip);
    }

    public static void logForMat(String message, String value) {
        String tip = String.format(message, value);
        System.out.println(tip);
    }

    public static void logForMat(String message) {
        String tip = String.format(message, "");
        System.out.println(tip);
    }


}
