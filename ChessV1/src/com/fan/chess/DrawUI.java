package com.fan.chess;

import javax.swing.*;
import java.awt.*;


/**
 * @author LuFan
 * @date 2020/10/24 - 16:13
 */
public class DrawUI extends JPanel {

    Listener ls = new Listener();

    public void initui() {
        // 创建面板
        JFrame jf = new JFrame();
        // 设置面板属性
        jf.setSize(1240, 860);
        jf.setTitle("中国象棋");
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // 设置关闭窗体即清楚进程
        jf.getContentPane().setBackground(Color.WHITE); // 设置背景颜色为白色，这里如果不 getContentPane 的话，无法起作用
        jf.setLocationRelativeTo(null); // 窗口，null 表示放在屏幕中央
        jf.setResizable(false); // 设置窗体不可放缩
        jf.add(this); // 将当前这个面板添加到 jf 中

        // 添加 Panel
        JPanel jp = new JPanel();
        jp.setPreferredSize(new Dimension(450, 1));
        jp.setBackground(Color.white);
        jp.setLayout(null); // 这里一定要设置
        jf.add(jp, BorderLayout.EAST);

        // 添加 Label
        JLabel label = new JLabel("中国象棋") {
            Image image = new ImageIcon(this.getClass().getResource("image\\" + "中国象棋.png")).getImage();

            @Override
            public void paint(Graphics g) {
                g.drawImage(image, 0, 0, 400, 204, null);
            }
        };
        label.setBounds(0, 0, 400, 204);
        jp.add(label);

        // 添加按钮
        String[] ShapeBtn = {"开始游戏", "重新开始", "悔棋"};
        for (int i = 0; i < ShapeBtn.length; i++) {
            String name = ShapeBtn[i];
            JButton button = new JButton(name) {
                Image image = new ImageIcon(this.getClass().getResource("image\\" + name + ".png")).getImage();

                @Override
                public void paint(Graphics g) {
                    g.drawImage(image, 0, 0, 250, 100, null);
                }
            };
            button.setBounds(100, 260 + 150 * i, 250, 100); // 更改位置和大小
            button.addActionListener(ls); // 添加监听事件
            jp.add(button); // 这些个按钮属于上面的那个 Panel
        }


        // 添加监听器
        jf.addMouseListener(ls);
        jf.setVisible(true);
        Graphics g = jf.getGraphics();
        ls.setG(g);
        ls.setUI(this);
    }

    // 重绘
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制棋盘
        g.drawImage(new ImageIcon(this.getClass().getResource("image\\" + "棋盘.jpg")).getImage(), 90, 60, 625, 700, this);

        // 根据 flag 画棋子
        for (int i = 0; i < init.row; i++) { // 注意，接口里的属性相当于静态变量，所以可以直接引用
            for (int j = 0; j < init.column; j++) {
                if (ls.flag[i][j] > 0) {
                    g.drawImage(new ImageIcon(getClass().getResource("image\\" + (Integer.toString(ls.flag[i][j])) + ".png")).getImage(), init.y0 + j * init.size - init.chessSize / 2,init.x00 + i * init.size - init.chessSize / 2, init.chessSize, init.chessSize, this);
                }
            }
        }

        // 将选中的棋子变大
        if (ls.r != -1) {
            if (ls.flag[ls.r][ls.c] > 0) {
                if (ls.chessflag == 1 & ls.flag[ls.r][ls.c] > 10 | ls.chessflag == 2 & ls.flag[ls.r][ls.c] < 10) {
                    int newexsize = 8;
                    g.drawImage(new ImageIcon(getClass().getResource("image\\"+(Integer.toString(ls.flag[ls.r][ls.c])) + ".png")).getImage(), init.y0 + ls.c * init.size - (init.chessSize+newexsize) / 2,init.x00 + ls.r * init.size - (init.chessSize+newexsize) / 2,init.chessSize+newexsize, init.chessSize+newexsize, this);
                }
            }
        }

    }


    public static void main(String[] args) {
        DrawUI ui = new DrawUI();
        ui.initui();
    }
}
