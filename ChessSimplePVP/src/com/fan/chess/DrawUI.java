package com.fan.chess;

import javax.swing.*;
import java.awt.*;

/**
 * @author LuFan
 * @date 2020/11/7 - 14:11
 */
public class DrawUI extends JPanel {

    Listener listener = new Listener(); // 监听器

    public void initUI() {
        // 创建面板
        JFrame jf = new JFrame();
        // 设置面板属性
        jf.setSize(819, 860); // JPanel 的大小是 Width-->805, Height-->823, 但是 JFrame 的边框是 top=30,left=7,bottom=7,right=7, 所以要加上, 虽然使用坐标时用的是 JPanel 的尺寸
        jf.setTitle("中国象棋");
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // 设置关闭窗体即清除进程
        jf.getContentPane().setBackground(Color.WHITE); // 设置背景颜色为白色
        jf.setLocationRelativeTo(null); // 窗口置于屏幕中央
        jf.setResizable(false); // 设置窗口不可放缩
        this.setBackground(Color.white); // 设置 DrawUI 这个类的背景
        jf.add(this); // 将 DrawUI 添加进来
        jf.setVisible(true); // 设置窗体可见
        // 给画板添加监听器
        jf.addMouseListener(listener);
        Graphics g = jf.getGraphics();
        listener.setG(g);
        listener.setUI(this);

    }

    // 重绘
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // 获取棋盘的图片
        Image BoardImage = new ImageIcon(getClass().getResource("image\\" + "棋盘.jpg")).getImage();
        // 画棋盘
        g.drawImage(BoardImage, 90, 60, 625, 700, this);

        // 根据 flag 画棋子
        for (int i = 0; i < Init.row; i++) {
            for (int j = 0; j < Init.column; j++) {
                if (listener.flag[i][j] > 0) {
                    Image img = new ImageIcon(getClass().getResource("image\\" + listener.flag[i][j] + ".png")).getImage(); // 获取相应的棋子的图片
                    int x = Init.initialX + j * Init.size - Init.chessSize / 2; // 棋子的横坐标
                    int y = Init.initialY + i * Init.size - Init.chessSize / 2; // 棋子的纵坐标
                    int width = Init.chessSize; // 棋子的宽
                    int height = Init.chessSize; // 棋子的高

                    // 绘制棋子的边框, 最后可以删掉, 这里仅仅是为了方便确定坐标
                    g.drawRect(x, y, width, height);

                    g.drawImage(img, x, y, width, height, this);
                }
            }
        }

        // 将选中的棋子放大
        if (listener.rowNum != -1) {
            if (listener.flag[listener.rowNum][listener.columnNum] > 0) {
                if ((listener.chessflag == 1 && listener.flag[listener.rowNum][listener.columnNum] > 10) ||
                        (listener.chessflag == 2 & listener.flag[listener.rowNum][listener.columnNum] < 10)) {
                    int newExSize = 8; // 棋子增大的额外的尺寸
                    Image img = new ImageIcon(getClass().getResource("image\\" + listener.flag[listener.rowNum][listener.columnNum] + ".png")).getImage(); // 获取相应的棋子的图片
                    int x = Init.initialX + listener.columnNum * Init.size - (Init.chessSize + newExSize) / 2; // 棋子的横坐标
                    int y = Init.initialY + listener.rowNum * Init.size - (Init.chessSize + newExSize) / 2; // 棋子的纵坐标
                    int width = Init.chessSize + newExSize; // 棋子的宽
                    int height = Init.chessSize + newExSize; // 棋子的高
                    g.drawImage(img, x, y, width, height, this);
                }
            }
        }

    }

    public static void main(String[] args) {
        DrawUI UI = new DrawUI();
        UI.initUI();
    }
}
