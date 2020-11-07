package com.fan.chess;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author LuFan
 * @date 2020/10/24 - 17:30
 */
public class Listener extends MouseAdapter implements ActionListener {
    Graphics g;
    DrawUI ui;
    int x1, x2, y1, y2;
    int c = -1; // 如果为 0，则会在一开始选中 flag[0][0] 处的車，故设置为  -1
    int r = -1;
    int[] curchess = new int[3];
    int[] beforechess = new int[3];
    int chessflag = 1; // 红方先走为 1


    // 初始化棋盘
    int[][] flag = new int[][]{
            {1, 2, 3, 4, 5, 4, 3, 2, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 6, 0, 0, 0, 0, 0, 6, 0},
            {7, 0, 7, 0, 7, 0, 7, 0, 7},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {77, 0, 77, 0, 77, 0, 77, 0, 77},
            {0, 66, 0, 0, 0, 0, 0, 66, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {11, 22, 33, 44, 55, 44, 33, 22, 11}
    };

    // 传递画布
    public void setG(Graphics g) {
        this.g = g;
    }

    public void setUI(DrawUI ui) {
        this.ui = ui;
    }

    // 更新现在点中的棋子
    public void recurchess() {
        if (r != -1) {
            curchess[0] = r;
            curchess[1] = c;
            curchess[2] = flag[r][c];
        }
    }

    // 更新上一次点中的棋子
    public void rebec() {
        beforechess[0] = curchess[0];
        beforechess[1] = curchess[1];
        beforechess[2] = curchess[2];
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("点击");
        x1 = e.getX();
        y1 = e.getY();
        if (x1 > init.x0 - init.size / 2 && y1 > init.y0 - init.size / 2
            && x1 < init.x0 + init.size / 2 + init.column * init.size
            && y1 < init.y0 + init.row * init.size + init.size / 2) {
            x2 = ((x1 - init.x0 + init.size / 2) / init.size) * init.size + init.x0;
            y2 = ((y1 - init.y0 + init.size / 2) / init.size) * init.size + init.y0;
            // 当前点击的位置
            getcr(); // 获得此时点击处的位置
            rebec(); // 更新前一颗棋子
            ui.repaint();
            recurchess();
        }
    }

    // 得到当前点击的位置
    public void getcr() {
        x2 = ((x1 - init.x0 + init.size / 2) / init.size) * init.size + init.x0;
        y2 = ((y1 - init.y0 + init.size / 2) / init.size) * init.size + init.y0;
        // 当前点击的位置
        c = (x2 - init.x0) / init.size;
        r = (y2 - init.y0) / init.size;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
