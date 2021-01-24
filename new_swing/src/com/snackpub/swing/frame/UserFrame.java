package com.snackpub.swing.frame;

import com.snackpub.swing.moduel.Bank;
import com.snackpub.swing.moduel.Log;
import com.snackpub.swing.service.ATMService;
import com.snackpub.swing.service.LogService;
import com.snackpub.swing.service.impl.ATMServiceImpl;
import com.snackpub.swing.service.impl.LogServiceImpl;
import com.snackpub.util.Printll;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

/**
 * 用户窗口
 *
 * @author snackpub
 * @date 2021/1/8
 */
public class UserFrame extends JFrame {


    /**
     * 展示窗口
     *
     * @param bank 登录银行卡信息
     * @return JFrame
     */
    public JFrame showFrame(Bank bank) {
        // 背景图片
        ImageIcon background = new ImageIcon(this.getClass().getResource("/img/4.jpg"));
        // 把背景图片显示在一个标签里面
        JLabel label = new JLabel(background);
        // 设置标签大小
        label.setBounds(0, 0, 1300, 900);
        JPanel panel = (JPanel) this.getContentPane();
        /* 如果为真，则组件绘制其边界内的每个像素。
         否则，组件可能不会绘制其部分或全部像素，从而允许底层像素显示出来。 */
        panel.setOpaque(false);
        // 把背景图片添加到分层窗格的最底层作为背景
        this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
        this.setSize(background.getIconWidth(), background.getIconHeight());

        //创建并设置JFrame容器窗口
        this.setTitle("ATM窗口");
        //创建横向Box容器
        Box b1 = Box.createHorizontalBox();
        //将外层横向Box添加进窗体
        this.add(b1);
        //添加高度为200的垂直框架
        b1.add(Box.createVerticalStrut(200));
        JButton withdrawBtn = new JButton("取款");
        JButton depositBtn = new JButton("存款");
        JButton queryBtn = new JButton("余额查询");
        JButton logBtn = new JButton("交易记录查询");
        JButton exitBtn = new JButton("退出");
        //添加按钮
        b1.add(withdrawBtn);
        //在两个组件之间强制留出一定的空间。
        b1.add(Box.createHorizontalStrut(5));
        b1.add(depositBtn);
        b1.add(Box.createHorizontalStrut(5));
        b1.add(queryBtn);
        b1.add(Box.createHorizontalStrut(5));
        b1.add(logBtn);
        b1.add(Box.createHorizontalStrut(5));
        b1.add(exitBtn);
        //添加水平胶水
        b1.add(Box.createHorizontalGlue());

        // 标题
        JLabel title = new JLabel("SnackPub BLANK", JLabel.CENTER);
        title.setFont(new Font("标题", Font.BOLD, 25));

        // Bottom initialization
        String message = String.format("账户:[%s]余额:[%s]", bank.getCardNumber(), bank.getResidual().toString());
        JLabel bottom = new JLabel(message, JLabel.CENTER);
        bottom.setFont(new Font("提示", Font.BOLD, 20));
        this.add(bottom, BorderLayout.SOUTH);

        //将创建好的标签组件添加到窗体中，并设置标签所在的区域
        this.add(title, BorderLayout.NORTH);
        //设置窗口尺寸
        this.setSize(1301, 901);
        // 设置窗口显示的位置
        this.setLocation(300, 200);
        // 设置此窗口是否始终高于其他窗口。
        this.setAlwaysOnTop(false);
        //设置窗体可见
        this.setVisible(true);
        //使用 System exit 方法退出应用程序。
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //使窗口居中对齐
        this.setLocationRelativeTo(null);
        // //禁止窗口最大化
        this.setResizable(false);

        // 取款按钮单击事件监听
        withdrawBtn.addActionListener(arg0 -> {

            ATMService atmService = new ATMServiceImpl();
            Bank rBank = atmService.withdraw(bank);


            // 移除
//            this.invalidate();
//            this.validate();
//            this.repaint();
//            this.revalidate();


            // Invalidates the container.
            // Validates this container and all of its subcomponents.
            this.validate();
        });

        // 存款按钮单击事件监听
        depositBtn.addActionListener(arg0 -> {
            ATMService atmService = new ATMServiceImpl();
            atmService.depositBtn(bank);
        });

        // 查询按钮单击事件监听
        queryBtn.addActionListener(arg0 -> {
            ATMService atmService = new ATMServiceImpl();
            atmService.queryBtn(bank);
        });

        // 交易记录查询
        logBtn.addActionListener(arg0 -> {
            LogService logService = new LogServiceImpl();
            List<Log> logs = logService.recentlyTenTimeLog(bank.getCardNumber());
            if (Objects.nonNull(logs) && logs.size() > 0) {
                StringBuffer sb = new StringBuffer();
                logs.forEach(item -> sb.append(item.getDetailedInfo()).append("\n"));
                Printll.logForMat("最近10次交易记录：\n%s", sb.toString());
            }else {
                JOptionPane.showMessageDialog(null, "暂无记录！", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // 退出按钮单击事件监听
        exitBtn.addActionListener(arg0 -> {
            Printll.prlnMsg("bye~");
            Runtime.getRuntime().exit(1);
        });


        return this;
    }


    public JLabel setLabel(Bank rBank) {
        String text = String.format("账户:[%s]余额:[%s]", rBank.getCardNumber(), rBank.getResidual().toString());
        JLabel newLabel = new JLabel(text, JLabel.CENTER);
        ;
        Container contentPane = this.getContentPane();
        contentPane.add(newLabel);
        return newLabel;
    }

}
