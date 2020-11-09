package com.fan.chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author LuFan
 * @date 2020/11/7 - 17:52
 */
public class Listener extends MouseAdapter implements ActionListener {

    Graphics g;
    String action;
    int x1, y1, x2, y2, index, beindex;
    int rowNum = -1; // 行, 初始值赋 -1, 不能赋 0, 因为左上角的車的坐标是 [0, 0]
    int columnNum = -1; // 列
    int chessflag = 1;// 红方先走为1, 且, 如果是1, 则为红方走, 如果是2, 则为黑方走
    DrawUI UI;
    int[][] chessList = new int[99999][6]; // 用来保存棋子每一次的移动情况
    int[] curChess = new int[3]; // 棋子初始位置，现在的位置，棋子的编号，棋子占的位本来的棋子的编号
    int[] beforeChess = new int[3]; // 数组中的三个位置分别保存 行, 列, flag[rowNum][columnNum]

    // 初始化棋盘, 横排为行, 纵列为列
    int[][] flag = new int[][] {
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

    // 将画布传递过来, 下面这两个函数在初始化的时候调用, 本类中的其他方法都是基于已经获得了 g 和 UI 来设计的
    public void setG(Graphics g) {
        this.g = g;
    }

    public void setUI(DrawUI UI) {
        this.UI = UI;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("点击");

        // 获取当前点击处的坐标
        x1 = e.getX();
        y1 = e.getY();

        // 确定一个矩形框, 在该矩形框中点击才有响应效果
        int leftAndTopX = Init.x0 - Init.size / 2;
        int leftAndTopY = Init.y0 - Init.size / 2;
        int rightAndBottomX = Init.x0 + (Init.column - 1) * Init.size + Init.size / 2;
        int rightAndBottomY = Init.y0 + (Init.row - 1) * Init.size + Init.size / 2;

        // 测试矩形框区域
        // g.drawRect(leftAndTopX, leftAndTopY, rightAndBottomX - leftAndTopX, rightAndBottomY - leftAndTopY);

        if (x1 > leftAndTopX && y1 > leftAndTopY && x1 < rightAndBottomX && y1 < rightAndBottomY) {
            x2 = ((x1 - Init.x0 + Init.size / 2) / Init.size) * Init.size + Init.x0;
            y2 = ((y1 - Init.y0 + Init.size / 2) / Init.size) * Init.size + Init.y0;

            getCurColAndRow(); // 获得此时点击处的棋子的二维格点坐标
            refreshBeforeChess(); // 更新前一颗棋子
            UI.repaint();
            refreshCurChess(); // 更新当前的棋子

            if (rowNum != -1) {
                // 如果当前的棋子的格点数为 0, 不管是红棋走还是黑棋走, 都是走空位
                if (curChess[2] == 0 & chessflag == 1 & beforeChess[2] > 10
                | curChess[2] == 0 & chessflag == 2 & beforeChess[2] < 10) {
                    if (canWalk(beforeChess[2]) == 1) {
                        System.out.println("走空位");
                        walk();
                    }
                } else if (beforeChess[2] > 10 & curChess[2] < 10 & chessflag == 1 & flag[rowNum][columnNum] < 10) { // 红棋走
                    if (canWalk(beforeChess[2]) == 1) {
                        // 如果手中有棋子
                        if (curChess[2] != 0) {
                            System.out.println("红棋吃黑棋");
                            walk();
                        }
                    }
                } else if (beforeChess[2] < 10 & curChess[2] > 10 & beforeChess[2] > 0 & chessflag == 2 & flag[rowNum][columnNum] > 10) {
                    if (canWalk(beforeChess[2]) == 1) {
                        // 如果手中有棋子
                        if (curChess[2] != 0) {
                            System.out.println("黑棋吃红棋");
                            walk();
                        }
                    }
                }
            }

            System.out.println("坐标: " + columnNum + "  " + rowNum);
        }


    }

    // 获取坐标对应的格点的二维坐标
    public void getCurColAndRow() {
        // 当前点击的位置
        columnNum = (x2 - Init.x0) / Init.size;
        rowNum = (y2 - Init.y0) / Init.size;
    }

    // 更新现在点中的棋子
    public void refreshCurChess() {
        if (rowNum != -1) {
            curChess[0] = rowNum;
            curChess[1] = columnNum;
            curChess[2] = flag[rowNum][columnNum];
        }
    }

    // 更新上一次点中的棋子
    public void refreshBeforeChess() {
        beforeChess[0] = curChess[0];
        beforeChess[1] = curChess[1];
        beforeChess[2] = curChess[2];
    }

    // 更新黑方红方, 在每一次 walk() 之后都需要调用此函数
    public void refreshChessflag() {
        if (chessflag == 1) {
            chessflag = 2;
        } else if (chessflag == 2) {
            chessflag = 1;
        }
    }

    // 走棋
    public void walk() {
        flag[rowNum][columnNum] = beforeChess[2];
        flag[beforeChess[0]][beforeChess[1]] = 0; // 把原来的格点坐标清零
        curChess = new int[3]; // curChess 清零

        // 解除选中状态
        columnNum = -1;
        rowNum = -1;

        // 更新走棋的一方
        refreshChessflag();

        UI.repaint();
    }

    // 找到某一起点到终点中含有的棋子数, 此方法用来找出开始位置和点击位置在一条直线上时中间的棋子数目, 用来判断車和炮是否可以移动
    public int findNumInOneLine(int row1, int col1, int row2, int col2) {
        int num = 0;
        if (row1 == row2) {
            for (int i = Math.min(col1, col2) + 1; i < Math.max(col1, col2); i++) {
                if (flag[row1][i] > 0) {
                    num++;
                }
            }
        } else if (col1 == col2) {
            for (int i = Math.min(row1, row2) + 1; i < Math.max(row1, row2); i++) {
                if (flag[i][col1] > 0) {
                    num++;
                }
            }
        }
        return num;
    }

    // 判断选中的棋子是否可以移动到现在选择的位置
    public int canWalk(int who) {
        int ifFlag = 0;

        // 将的走法
        if (who == 5) {
            if (rowNum < 3 && columnNum < 6 && columnNum > 2) {
                // 横着走或者竖着走，且只能走一格
                if (beforeChess[0] == curChess[0] & Math.abs(beforeChess[1] - curChess[1]) == 1
                | beforeChess[1] == curChess[1] & Math.abs(beforeChess[0] - curChess[0]) == 1) {
                    ifFlag = 1;
                }
            }
        }

        // 帅的走法
        else if (who == 55) {
            if (rowNum > 6 & columnNum < 6 & columnNum > 2) {
                if (beforeChess[0] == curChess[0] & Math.abs(beforeChess[1] - curChess[1]) == 1
                        | beforeChess[1] == curChess[1] & Math.abs(beforeChess[0] - curChess[0]) == 1) {
                    ifFlag = 1;
                }
            }
        }

        // 車的走法
        else if (who == 1 | who == 11) {
            if (beforeChess[0] == curChess[0] | beforeChess[1] == curChess[1]) {
                if (findNumInOneLine(beforeChess[0], beforeChess[1], curChess[0], curChess[1]) == 0) {
                    ifFlag = 1;
                }
            }
        }

        // 马的走法
        else if (who == 2 | who == 22) {
            // 向上走日
            if (beforeChess[0] > 0) {
                if (beforeChess[0] - curChess[0] == 2 & Math.abs(beforeChess[1] - curChess[1]) == 1
                & flag[beforeChess[0] - 1][beforeChess[1]] == 0) { // 最后一个条件是不能有绊马腿的棋子
                    ifFlag = 1;
                }
            }

            // 向下走日
            if (beforeChess[0] < 9) {
                if (beforeChess[0] - curChess[0] == -2 & Math.abs(beforeChess[1] - curChess[1]) == 1
                & flag[beforeChess[0] + 1][beforeChess[1]] == 0) {
                    ifFlag = 1;
                }
            }

            // 向右走日
            if (beforeChess[1] < 8) {
                if (beforeChess[1] - curChess[1] == -2 & Math.abs(beforeChess[0] - curChess[0]) == 1
                & flag[beforeChess[0]][beforeChess[1] + 1] == 0) {
                    ifFlag = 1;
                }
            }

            // 向左走日
            if (beforeChess[1] > 0) {
                if (beforeChess[1] - curChess[1] == 2 & Math.abs(beforeChess[0] - curChess[0]) == 1
                & flag[beforeChess[0]][beforeChess[1] - 1] == 0) {
                    ifFlag = 1;
                }
            }
        }

        // 象(相)的走法
        else if (who == 3 | who == 33) {
            // 向左上角走田
            if (beforeChess[0] > 0 & beforeChess[1] > 0) {
                if (beforeChess[0] - curChess[0] == 2 & beforeChess[1] - curChess[1] == 2
                & flag[beforeChess[0] - 1][beforeChess[1] - 1] == 0) { // 最后一个条件是田字的中心没有绊象腿
                    ifFlag = 1;
                }
            }

            // 向右上角走田
            if (beforeChess[0] > 0 & beforeChess[1] < 8) {
                if (beforeChess[0] - curChess[0] == 2 & beforeChess[1] - curChess[1] == -2
                & flag[beforeChess[0] - 1][beforeChess[1] + 1] == 0) {
                    ifFlag = 1;
                }
            }

            // 向左下角走田
            if (beforeChess[0] < 9 & beforeChess[1] > 0) {
                if (beforeChess[0] - curChess[0] == -2 & beforeChess[1] - curChess[1] == 2
                & flag[beforeChess[0] + 1][beforeChess[1] - 1] == 0) {
                    ifFlag = 1;
                }
            }

            // 向右下角走田
            if (beforeChess[0] < 9 & beforeChess[1] < 8) {
                if (beforeChess[0] - curChess[0] == -2 & beforeChess[1] - curChess[1] == -2
                & flag[beforeChess[0] + 1][beforeChess[1] + 1] == 0) {
                    ifFlag = 1;
                }
            }
        }

        // 士的走法
        else if (who == 4) {
            if (rowNum < 3 & columnNum < 6 & columnNum > 2) {
                if (Math.abs(beforeChess[1] - curChess[1]) == 1 & Math.abs(beforeChess[0] - curChess[0]) == 1) {
                    ifFlag = 1;
                }
            }
        }

        // 仕的走法
        else if(who == 44) {
            if (rowNum > 6 & columnNum < 6 & columnNum > 2) {
                if (Math.abs(beforeChess[1] - curChess[1]) == 1 & Math.abs(beforeChess[0] - curChess[0]) == 1) {
                    ifFlag = 1;
                }
            }
        }

        // 炮的走法
        else if(who == 6 | who == 66) {
            if (beforeChess[0] == curChess[0] | beforeChess[1] == curChess[1]) {
                if (findNumInOneLine(beforeChess[0], beforeChess[1], curChess[0], curChess[1]) == 1 & curChess[2] != 0
                | findNumInOneLine(beforeChess[0], beforeChess[1], curChess[0], curChess[1]) == 0 & curChess[2] == 0) {
                    ifFlag = 1;
                }
            }
        }

        // 卒的走法
        else if (who == 7) {
            if (beforeChess[0] < 5 & beforeChess[1] == curChess[1] & beforeChess[0] - curChess[0] == -1
            | beforeChess[0] > 4 & beforeChess[1] == curChess[1] & beforeChess[0] - curChess[0] == -1
            | beforeChess[0] > 4 & beforeChess[0] == curChess[0] & Math.abs(beforeChess[1] - curChess[1]) == 1) {
                ifFlag = 1;
            }
        }

        // 兵的走法
        else if (who == 77) {
            if (beforeChess[0] > 4 & beforeChess[1] == curChess[1] & beforeChess[0] - curChess[0] == 1
            | beforeChess[0] < 5 & beforeChess[1] == curChess[1] & beforeChess[0] - curChess[0] == 1
            | beforeChess[0] < 5 & beforeChess[0] == curChess[0] & Math.abs(beforeChess[1] - curChess[1]) == 1) {
                ifFlag = 1;
            }
        }

        System.out.println("ifFlag = " + ifFlag);
        return ifFlag;
    }

    // 悔棋方法
    public void regretChess() {
        rowNum = -1;
        if (index > 0) {
            flag[chessList[index - 1][0]][chessList[index - 1][1]] = chessList[index - 1][4];
            flag[chessList[index - 1][2]][chessList[index - 1][3]] = chessList[index - 1][5];
            refreshChessflag();
            index--;
        }
    }

    // 更新悔棋列表
    public void updateChessList() {
        // 上一次点击的棋子的行列信息
        chessList[index][0] = beforeChess[0];
        chessList[index][1] = beforeChess[1];
        // 当前点击的棋子的行列信息
        chessList[index][2] = rowNum;
        chessList[index][3] = columnNum;
        // 两次点击的位置的格点信息
        chessList[index][4] = beforeChess[2];
        chessList[index][5] = flag[rowNum][columnNum];
        index++;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
