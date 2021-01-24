package com.snackpub.swing.frame;

import com.snackpub.swing.constant.MessageConstant;
import com.snackpub.swing.moduel.Bank;
import com.snackpub.swing.moduel.Log;
import com.snackpub.swing.moduel.User;
import com.snackpub.swing.service.ATMService;
import com.snackpub.swing.service.LogService;
import com.snackpub.swing.service.UserService;
import com.snackpub.swing.service.impl.ATMServiceImpl;
import com.snackpub.swing.service.impl.LogServiceImpl;
import com.snackpub.swing.service.impl.UserServiceImpl;
import com.snackpub.util.Func;
import com.snackpub.util.Printll;
import com.snackpub.util.UUIDUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Vector;

/**
 * 管理窗口
 *
 * @author snackpub
 * @date 2021/1/9
 */
public class ManagerFrame extends JFrame {


    public static void main(String[] args) {
        ManagerFrame managerFrame = new ManagerFrame();
        managerFrame.showManagerFrame();
    }


    public void showManagerFrame() {
        // 背景图片
        ImageIcon background = new ImageIcon(this.getClass().getResource("/img/1.jpg"));
        // 把背景图片显示在一个标签里面
        JLabel label = new JLabel(background);
        // 设置标签大小
        label.setBounds(0, 0, 501, 377);
        JPanel panel = (JPanel) this.getContentPane();
        /* 如果为真，则组件绘制其边界内的每个像素。
         否则，组件可能不会绘制其部分或全部像素，从而允许底层像素显示出来。 */
        panel.setOpaque(false);
        // 把背景图片添加到分层窗格的最底层作为背景
        this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
        this.setSize(background.getIconWidth(), background.getIconHeight());

        //创建并设置JFrame容器窗口
        this.setTitle("管理后台");
        //创建横向Box容器
        Box b1 = Box.createHorizontalBox();
        //将外层横向Box添加进窗体
        this.add(b1);
        //添加高度为200的垂直框架
        b1.add(Box.createVerticalStrut(200));
        JButton addBtn = new JButton("增加账户");
        JButton logOutBtn = new JButton("注销账户");
        JButton queryBtn = new JButton("查询账户");
        JButton updateBtn = new JButton("修改账户");
        JButton exitBtn = new JButton("退出");
        //添加按钮
        b1.add(addBtn);
        //在两个组件之间强制留出一定的空间。
        b1.add(Box.createHorizontalStrut(5));
        b1.add(logOutBtn);
        b1.add(Box.createHorizontalStrut(5));
        b1.add(queryBtn);
        b1.add(Box.createHorizontalStrut(5));
        b1.add(updateBtn);
        b1.add(Box.createHorizontalStrut(5));
        b1.add(exitBtn);
        //添加水平胶水
        b1.add(Box.createHorizontalGlue());

        // 标题
        JLabel title = new JLabel("", JLabel.CENTER);
        title.setFont(new Font("head name", Font.BOLD, 25));

        //将创建好的标签组件添加到窗体中，并设置标签所在的区域
        this.add(title, BorderLayout.NORTH);
        //设置窗口尺寸
        this.setSize(501, 377);
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

        // 增加按钮单击事件监听
        addBtn.addActionListener(arg0 -> {
            AddFrame addFrame = new AddFrame();
            addFrame.showAddFrame();
        });

        // 注销按钮单击事件监听
        logOutBtn.addActionListener(arg0 -> {
            LogOutFrame logOutFrame = new LogOutFrame();
            logOutFrame.showLogOutFrame();
        });

        // 查询按钮单击事件监听
        queryBtn.addActionListener(arg0 -> {
            QueryFrame queryFrame = new QueryFrame();

            // 表头（列名）
            Vector<String> columnNames = new Vector<>();
            columnNames.add("姓名");
            columnNames.add("卡号");
            columnNames.add("流水号");
            columnNames.add("创建时间");
            //columnNames.add("联系电话");
            columnNames.add("性别");
            columnNames.add("是否注销");

            ATMService atmService = new ATMServiceImpl();
            Vector allBank = atmService.getAllBank();
            queryFrame.showQueryFrame(allBank, columnNames);
        });

        // 修改
        updateBtn.addActionListener(arg0 -> {
            UpdateFrame updateFrame = new UpdateFrame();
            updateFrame.showUpdateFrame();
        });

        // 退出按钮单击事件监听
        exitBtn.addActionListener(arg0 -> {
            Printll.prlnMsg("bye~");
            Runtime.getRuntime().exit(1);
        });

    }

    static class UpdateFrame extends JFrame {
        void showUpdateFrame() {
            this.setTitle("修改账户");
            JLabel bankCardLabel = new JLabel("银行卡号:", JLabel.CENTER);
            bankCardLabel.setFont(new Font("粗体", Font.BOLD, 15));
            JTextField bankCardText = new JTextField();

            // 网格布局
            GridLayout gridLayout = new GridLayout(2, 4, 0, 10);
            this.setLayout(gridLayout);

            JButton logout = new JButton("查詢");
            JButton close = new JButton("清空");

            this.add(new Label());
            this.add(bankCardLabel);
            this.add(bankCardText);
            this.add(new Label());

            this.add(new Label());
            this.add(logout);
            this.add(close);
            this.add(new Label());

            // 设置窗口显示的位置
            this.setLocation(300, 200);
            this.setBounds(300, 200, 500, 100);
            this.setVisible(true);
            this.setAlwaysOnTop(false);
            //使窗口居中对齐
            this.setLocationRelativeTo(null);
            // The hide-window default window close operation
            this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

            logout.addActionListener(args -> {
                String cardNum = bankCardText.getText();
                if (Func.isNotBlank(cardNum)) {
                    ATMService atmService = new ATMServiceImpl();
                    Bank bank = atmService.getBank(cardNum);
                    if (Objects.isNull(bank)) {
                        JOptionPane.showMessageDialog(null, "账户不存在！", "提示", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    UserService userService = new UserServiceImpl();
                    User user = userService.getUser(bank.getUserLsh());
                    //将数据绑定到窗口
                    UpdateFrame2 updateFrame2 = new UpdateFrame2();
                    updateFrame2.showUpdateFrame(bank,user);
                    //                    if (count > 0 && delete > 0) {
//                        JOptionPane.showMessageDialog(null, "注销成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
//                    } else {
//                        JOptionPane.showMessageDialog(null, "注销失败！", "提示", JOptionPane.WARNING_MESSAGE);
//                    }
                } else {
                    JOptionPane.showMessageDialog(null, "卡号不能为空！", "提示", JOptionPane.WARNING_MESSAGE);
                }
            });

            close.addActionListener(args -> bankCardText.setText(""));
        }
    }

    static class UpdateFrame2 extends JFrame {

        void showUpdateFrame(Bank bank, User user) {
            this.setTitle("修改賬戶");

            JLabel userNameLabel = new JLabel("用户名:", JLabel.LEFT);
            userNameLabel.setFont(new Font("粗体", Font.BOLD, 15));
            JTextField userNameText = new JTextField("111");

            JLabel idCardLabel = new JLabel("身份证号:", JLabel.LEFT);
            idCardLabel.setFont(new Font("粗体", Font.BOLD, 15));
            JTextField idCard = new JTextField(20);

            JLabel telLabel = new JLabel("联系电话:", JLabel.LEFT);
            telLabel.setFont(new Font("粗体", Font.BOLD, 15));
            JTextField tel = new JTextField(20);

            JLabel sexLabel = new JLabel("性别:", JLabel.LEFT);
            sexLabel.setFont(new Font("粗体", Font.BOLD, 15));
            JTextField sex = new JTextField(20);

            JLabel bankCardLabel = new JLabel("银行卡号:", JLabel.LEFT);
            bankCardLabel.setFont(new Font("粗体", Font.BOLD, 15));
            JTextField bankCard = new JTextField(20);

            JLabel pwdLabel = new JLabel("密码:", JLabel.LEFT);
            pwdLabel.setFont(new Font("粗体", Font.BOLD, 15));
            JTextField pwd = new JTextField(20);

            JButton register = new JButton("修改");
            JButton close = new JButton("清空");


            // 网格布局
            GridLayout gridLayout = new GridLayout(7, 4, 0, 10);
            this.setLayout(gridLayout);

            this.add(new Label());
            this.add(userNameLabel);
            this.add(userNameText);
            this.add(new Label());

            this.add(new Label());
            this.add(idCardLabel);
            this.add(idCard);
            this.add(new Label());

            this.add(new Label());
            this.add(telLabel);
            this.add(tel);
            this.add(new Label());

            this.add(new Label());
            this.add(sexLabel);
            this.add(sex);
            this.add(new Label());

            this.add(new Label());
            this.add(bankCardLabel);
            this.add(bankCard);
            this.add(new Label());

            this.add(new Label());
            this.add(pwdLabel);
            this.add(pwd);
            this.add(new Label());

            this.add(new Label());
            this.add(register);
            this.add(close);
            this.add(new Label());
            // 设置窗口显示的位置
            this.setLocation(300, 200);
            this.setBounds(300, 200, 500, 300);
            this.setVisible(true);
            this.setAlwaysOnTop(false);
            //使窗口居中对齐
            this.setLocationRelativeTo(null);
            // The hide-window default window close operation
            this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

            // init
            userNameText.setText(bank.getUserName());
            idCard.setText(user.getiDCard());
            tel.setText(user.getTel());
            sex.setText(user.getSex());
            bankCard.setText(bank.getCardNumber());
            pwd.setText(bank.getPassword());
//            confirmPwd.setText("");

            register.addActionListener(args -> {
                String text = userNameText.getText();
                String text1 = idCard.getText();
                String text2 = tel.getText();
                String text3 = sex.getText();
                String text4 = bankCard.getText();
                String text5 = pwd.getText();

                // 可以优化判断校验长度以及是否为空哦
                // -------------------------------


                UserService userService = new UserServiceImpl();
                User user2 = new User();
                user2.setUserName(text);
                user2.setiDCard(text1);
                user2.setTel(text2);
                user2.setSex(text3);
                // 用户流水号
                String lsh =user.getLsh();
                user2.setLsh(lsh);
                int count1 = userService.update(user2);

                ATMService atmService = new ATMServiceImpl();
                Bank bank2 = new Bank();
                // 初始化10元卡费
                bank2.setResidual(new BigDecimal(10));
                bank2.setUserName(user2.getUserName());
                bank2.setCardNumber(text4);
                bank2.setSex(text3);
                bank2.setUserLsh(lsh);
                bank2.setPassword(text5.toLowerCase());

                int count2 = atmService.update(bank2);
                if (count1 > 0 && count2 > 0) {
                    JOptionPane.showMessageDialog(null, MessageConstant.DEFAULT_MESSAGE, "提示", JOptionPane.INFORMATION_MESSAGE);
                }

            });

            close.addActionListener(args -> {
                userNameText.setText("");
                idCard.setText("");
                tel.setText("");
                sex.setText("");
                bankCard.setText("");
                pwd.setText("");
            });

        }
    }

    class QueryFrame extends JFrame {
        void showQueryFrame(Vector data, Vector<String> columnNames) {
            this.setTitle("查询账户");
            this.setLayout(new BorderLayout());


            DefaultTableModel defaultTableModel = new DefaultTableModel(data, columnNames);

            // 创建一个表格，指定 所有行数据 和 表头
            JTable table = new JTable(defaultTableModel);
            // 把 表头 添加到容器顶部（使用普通的中间容器添加表格时，表头 和 内容 需要分开添加）
            this.add(table.getTableHeader(), BorderLayout.NORTH);
            // 把 表格内容 添加到容器中心
            this.add(table, BorderLayout.CENTER);

            // 设置窗口显示的位置
            this.setLocation(300, 200);
            this.setBounds(300, 200, 700, 500);
            this.setVisible(true);
            this.setAlwaysOnTop(false);
            //使窗口居中对齐
            this.setLocationRelativeTo(null);
            // The hide-window default window close operation
            this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        }
    }

    static class LogOutFrame extends JFrame {
        void showLogOutFrame() {
            this.setTitle("注销账户");
            JLabel bankCardLabel = new JLabel("银行卡号:", JLabel.CENTER);
            bankCardLabel.setFont(new Font("粗体", Font.BOLD, 15));
            JTextField bankCardText = new JTextField();
            bankCardText.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    //当组件获得键盘焦点时调用。
                    // userNameText.setText("");
                }

                @Override
                public void focusLost(FocusEvent e) {
                    //当组件丢失键盘焦点时调用。
                }
            });

            // 网格布局
            GridLayout gridLayout = new GridLayout(2, 4, 0, 10);
            this.setLayout(gridLayout);

            JButton logout = new JButton("注销");
            JButton close = new JButton("清空");

            this.add(new Label());
            this.add(bankCardLabel);
            this.add(bankCardText);
            this.add(new Label());

            this.add(new Label());
            this.add(logout);
            this.add(close);
            this.add(new Label());

            // 设置窗口显示的位置
            this.setLocation(300, 200);
            this.setBounds(300, 200, 500, 100);
            this.setVisible(true);
            this.setAlwaysOnTop(false);
            //使窗口居中对齐
            this.setLocationRelativeTo(null);
            // The hide-window default window close operation
            this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

            logout.addActionListener(args -> {
                String cardNum = bankCardText.getText();
                if (Func.isNotBlank(cardNum)) {
                    ATMService atmService = new ATMServiceImpl();
                    int count = atmService.delete(cardNum);
                    Bank bank = atmService.getBank(cardNum);
                    if (Objects.isNull(bank)) {
                        JOptionPane.showMessageDialog(null, "账户不存在！", "提示", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    UserService userService = new UserServiceImpl();
                    int delete = userService.delete(bank.getUserLsh());
                    if (count > 0 && delete > 0) {
                        JOptionPane.showMessageDialog(null, "注销成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "注销失败！", "提示", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "卡号不能为空！", "提示", JOptionPane.WARNING_MESSAGE);
                }
            });

            close.addActionListener(args -> {
                bankCardText.setText("");
            });
        }
    }


    static class AddFrame extends JFrame {

        void showAddFrame() {
            this.setTitle("开户");

            JLabel userNameLabel = new JLabel("用户名:", JLabel.LEFT);
            userNameLabel.setFont(new Font("粗体", Font.BOLD, 15));
            JTextField userNameText = new JTextField("111");

            JLabel idCardLabel = new JLabel("身份证号:", JLabel.LEFT);
            idCardLabel.setFont(new Font("粗体", Font.BOLD, 15));
            JTextField idCard = new JTextField(20);

            JLabel telLabel = new JLabel("联系电话:", JLabel.LEFT);
            telLabel.setFont(new Font("粗体", Font.BOLD, 15));
            JTextField tel = new JTextField(20);

            JLabel sexLabel = new JLabel("性别:", JLabel.LEFT);
            sexLabel.setFont(new Font("粗体", Font.BOLD, 15));
            JTextField sex = new JTextField(20);

            JLabel bankCardLabel = new JLabel("银行卡号:", JLabel.LEFT);
            bankCardLabel.setFont(new Font("粗体", Font.BOLD, 15));
            JTextField bankCard = new JTextField(20);

            JLabel pwdLabel = new JLabel("密码:", JLabel.LEFT);
            pwdLabel.setFont(new Font("粗体", Font.BOLD, 15));
            JTextField pwd = new JTextField(20);

            JLabel confirmPwdCardLabel = new JLabel("确认密码:", JLabel.LEFT);
            confirmPwdCardLabel.setFont(new Font("粗体", Font.BOLD, 15));
            // 可以把密码设置为密码框而不是文本框哦
            JTextField confirmPwd = new JTextField(20);

            JButton register = new JButton("注册");
            JButton close = new JButton("清空");


            // 网格布局
            GridLayout gridLayout = new GridLayout(8, 4, 0, 10);
            this.setLayout(gridLayout);

            this.add(new Label());
            this.add(userNameLabel);
            this.add(userNameText);
            this.add(new Label());

            this.add(new Label());
            this.add(idCardLabel);
            this.add(idCard);
            this.add(new Label());

            this.add(new Label());
            this.add(telLabel);
            this.add(tel);
            this.add(new Label());

            this.add(new Label());
            this.add(sexLabel);
            this.add(sex);
            this.add(new Label());

            this.add(new Label());
            this.add(bankCardLabel);
            this.add(bankCard);
            this.add(new Label());

            this.add(new Label());
            this.add(pwdLabel);
            this.add(pwd);
            this.add(new Label());

            this.add(new Label());
            this.add(confirmPwdCardLabel);
            this.add(confirmPwd);
            this.add(new Label());

            this.add(new Label());
            this.add(register);
            this.add(close);
            this.add(new Label());
            // 设置窗口显示的位置
            this.setLocation(300, 200);
            this.setBounds(300, 200, 500, 300);
            this.setVisible(true);
            this.setAlwaysOnTop(false);
            //使窗口居中对齐
            this.setLocationRelativeTo(null);
            // The hide-window default window close operation
            this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

            // init
            userNameText.setText("");
            idCard.setText("");
            tel.setText("");
            sex.setText("");
            bankCard.setText("");
            pwd.setText("");
            confirmPwd.setText("");

            register.addActionListener(args -> {
                String text = userNameText.getText();
                String text1 = idCard.getText();
                String text2 = tel.getText();
                String text3 = sex.getText();
                String text4 = bankCard.getText();
                String text5 = pwd.getText();
                String text6 = confirmPwd.getText();

                // 可以优化判断校验长度以及是否为空哦
                // -------------------------------

                if (!text5.equalsIgnoreCase(text6)) {
                    JOptionPane.showMessageDialog(null, "密码不一致！", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                // 统一转换字母为小写
                String lowerCasePwd = text6.toLowerCase();

                UserService userService = new UserServiceImpl();
                User user = new User();
                user.setUserName(text);
                user.setiDCard(text1);
                user.setTel(text2);
                user.setSex(text3);
                // 用户流水号
                String lsh = UUIDUtil.getUUID();
                user.setLsh(lsh);
                int count1 = userService.save(user);

                ATMService atmService = new ATMServiceImpl();
                Bank bank = new Bank();
                // 初始化10元卡费
                bank.setResidual(new BigDecimal(10));
                bank.setUserName(user.getUserName());
                bank.setCardNumber(text4);
                bank.setSex(text3);
                bank.setUserLsh(lsh);
                bank.setPassword(lowerCasePwd);

                int count2 = atmService.add(bank);
                if (count1 > 0 && count2 > 0) {
                    JOptionPane.showMessageDialog(null, MessageConstant.DEFAULT_MESSAGE, "提示", JOptionPane.INFORMATION_MESSAGE);
                }

            });

            close.addActionListener(args -> {
                userNameText.setText("");
                idCard.setText("");
                tel.setText("");
                sex.setText("");
                bankCard.setText("");
                pwd.setText("");
                confirmPwd.setText("");
            });

        }
    }



}
