package com.fan.chess;

/**
 * @author LuFan
 * @date 2020/11/7 - 17:45
 */
public interface Init {

    int initialX = 90 + 10; // 初始的左上角的格点的横坐标, 10 代表外边框与内部格子的间距, 下同
    int initialY = 60 + 10; // 初始的左上角的格点的纵坐标

    int x00 = 70;
    int x0 = 108;
    int y0 = 100;
    int row = 10; // 10 行线
    int column = 9; // 9 列线
    int chessSize = 67; // 象棋的尺寸
    int size = 76; // 方格的尺寸

}
