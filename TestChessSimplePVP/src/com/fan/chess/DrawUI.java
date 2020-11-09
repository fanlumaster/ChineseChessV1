package com.fan.chess;

import javax.swing.*;
import java.awt.*;

/**
 * @author LuFan
 * @date 2020/11/7 - 14:11
 */
public class DrawUI extends JPanel {

    public void initUI() {
        // 创建面板
        JFrame jf = new JFrame();
        // 设置面板属性
        jf.setSize(819, 860); // ？？--？？这个地方之后要修改
        jf.setTitle("中国象棋");
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // 设置关闭窗体即清除进程
        jf.getContentPane().setBackground(Color.WHITE); // 设置背景颜色为白色
        jf.setLocationRelativeTo(null); // 窗口置于屏幕中央
        jf.setResizable(false); // 设置窗口不可放缩
        this.setBackground(Color.white); // 设置 DrawUI 这个类的背景
        jf.add(this); // 将 DrawUI 添加进来
        Rectangle bounds = jf.getBounds();
        System.out.println(bounds);
        jf.setVisible(true); // 设置窗体可见

        System.out.println(this.getInsets());
        System.out.println(this.getSize());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // 获取棋盘的图片
        Image BoardImage = new ImageIcon(getClass().getResource("image\\" + "棋盘.jpg")).getImage();
        // 画棋盘
        g.drawImage(BoardImage, 90, 60, 625, 700, this);

        g.setColor(Color.blue);
        g.drawLine(7, 7, 798, 7);
        g.drawLine(7, 7, 7, 816);
        g.drawLine(7, 816, 798, 816);
        g.drawLine(798, 816, 798, 7);

        g.drawLine(90, 60, 715, 60);
        g.drawLine(90, 60, 90, 760);
        g.drawLine(90, 760, 715, 760);
        g.drawLine(715, 760, 715, 60);

        g.drawLine(90, 60, 715, 60);
        g.drawLine(90, 60, 90, 760);
        g.drawLine(90, 760, 715, 760);
        g.drawLine(715, 760, 715, 60);

        Insets insets = this.getInsets();
        System.out.println("===>" + insets);

    }

    public static void main(String[] args) {
        DrawUI UI = new DrawUI();
        UI.initUI();
    }
}
