package com.snackpub.swing.frame;

import com.snackpub.swing.moduel.Bank;
import com.snackpub.swing.service.ATMService;
import com.snackpub.swing.service.impl.ATMServiceImpl;
import com.snackpub.util.Printll;

import java.util.Scanner;

/**
 * ATM 类只做初始化展示，对其它操作方法进行封装
 *
 * @author snackpub
 * @date 2021/1/9
 */
public class ATM {


    public static void main(String[] args) {

        // ==================-----用户操作-----======================
        System.out.println("=====================================================");
        System.out.println("================欢迎来到SnackPubBLANCK================");
        System.out.println("=====================================================");

        while (true) {
            System.out.println("请输入银行卡号：");
            Scanner cardNumberInput = new Scanner(System.in);
            String cardNumber = cardNumberInput.next();
            System.out.println("请输入密码：");
            Scanner passwordInput = new Scanner(System.in);
            String password = passwordInput.next();
            ATMService atmService = new ATMServiceImpl();
            Bank bank = new Bank(cardNumber, password);
            boolean isExist = atmService.isExistBank(bank);
            if (isExist) {
                Bank bank1 = atmService.getBank(bank.getCardNumber());
                String userName = bank1.getUserName();
                System.out.println("=====================================================");
                Printll.logForMat("登录成功！欢迎 [%s] [%s]", userName, bank1.getSex());
                System.out.println("=====================================================");
                // 判断是否是管理员登陆
                if (bank1.getUserType() == 2) {

                }else {
                    UserFrame frameMain = new UserFrame();
                    frameMain.showFrame(bank1);
                    break;
                }
            } else {
                System.out.println("=====================================================");
                System.out.println("================账号或密码错误！=======================");
                System.out.println("=====================================================");
            }
        }

    }
}
