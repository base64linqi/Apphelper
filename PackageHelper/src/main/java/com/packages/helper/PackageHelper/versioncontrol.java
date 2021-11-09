package com.packages.helper.PackageHelper;

import sun.security.mscapi.CPublicKey;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class versioncontrol extends JFrame {
	private JTextField textField;
	private JTextField textField_1;
    public  versioncontrol(){
        Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
        setSize(698,365);//按照屏幕大小按一定比列设置主窗口，此方法的参数都是int型
        setLocation((int)(dim.width*0.36),(int)(dim.height*0.29));//按照屏幕大小按一定比列设置主窗口位置，此方法的参数都是int型
        setTitle("Frida安卓端版本控制器");//设置窗口名称

        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(null);
        JLabel label1=new JLabel("Frida安卓端版本控制器");
        label1.setFont(new Font("宋体", Font.PLAIN, 22));
        JLabel label2=new JLabel("当前:");
        label2.setFont(new Font("宋体", Font.PLAIN, 18));
        JButton label3=new JButton("切换");
        label3.setFont(new Font("宋体", Font.PLAIN, 14));
        JButton label4=new JButton("自动获取");
        label4.setFont(new Font("宋体", Font.PLAIN, 14));
        JButton label5=new JButton("停止进程");
        label5.setFont(new Font("宋体", Font.PLAIN, 14));
        Dimension dim1=new Dimension((int)(dim.width*0.05),(int)(dim.height*0.05));
        label1.setBounds(190,20,300,35);
        label2.setBounds(40,85,59,20);
        label3.setBounds(460,77,110,40);
        label4.setBounds(460,150,110,40);
        label5.setBounds(90,218,90,40);
        mainpanel.add(label1);
        mainpanel.add(label2);
        mainpanel.add(label3);
        mainpanel.add(label4);
        mainpanel.add(label5);
        getContentPane().add(mainpanel);
        
        JLabel label = new JLabel("获取前置名称:");
        label.setFont(new Font("宋体", Font.PLAIN, 18));
        label.setBounds(10, 162, 149, 28);
        mainpanel.add(label);
        
        textField = new JTextField();
        textField.setBounds(169, 155, 286, 32);
        mainpanel.add(textField);
        textField.setColumns(10);
        
        JLabel lblNewLabel = new JLabel("Pid:2222");
        lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 14));
        lblNewLabel.setBounds(251, 219, 54, 39);
        mainpanel.add(lblNewLabel);
        
        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(169, 82, 286, 32);
        mainpanel.add(textField_1);

        /*事件监听*/
        /*切换按钮事件监听*/
        label3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        /*自动获取按钮事件监听*/
        label4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {



            }
        });



        /*停止进程按钮事件监听*/
        label5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

//                System.exit(0);
            }
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);//此方法的意思是说数据模型已经构造好了，允许jvm根据数据模型执行paint方法开始画图的到屏幕上
    }
    public static void main(String[] args) {
		new versioncontrol().setVisible(true);
	}
}
