package com.sty.foroffer.array;

/**
 * @Author: tian
 * @UpdateDate: 2021/2/22 11:17 AM
 */
public class PrintMatrixInCircle {

    /**
     *    1  2  3  4  5
     *    6  7  8  9  10
     *    11 12 13 14 15
     *    16 17 18 19 20
     *
     * @param args
     */
    public static void main(String[] args) {
        int[][] arr = {
            {1,  2,  3,  4,  5},
            {6,  7,  8,  9,  10},
            {11, 12, 13, 14, 15},
            {16, 17, 18, 19, 20}
        };
        printMatrixClockWisely(arr);
    }

    public static void printMatrixClockWisely(int[][] numbers) {
        //输入的参数不能为空
        if(numbers == null) {
            return;
        }

        //记录一圈（环）的开始位置的行
        int x = 0;
        //记录一圈（环）的开始位置的列
        int y = 0;
        //对每一圈（环）进行处理：行号最大是 (numbers.length - 1)/2 ; 列号最大是(numbers[0].length - 1)/2
        while (x * 2 < numbers.length && y * 2 < numbers[0].length) {
            printMatrixInCircle(numbers, x, y);
            //指向下一个要处理的环的第一个位置
            x++;
            y++;
        }
    }

    private static void printMatrixInCircle(int[][] numbers, int x, int y) {
        //数组的行数
        int rows = numbers.length;
        //数组的列数
        int cols = numbers[0].length;

        //输出环的上面一行，包括最终的那个数字
        for (int i = y; i <= cols - y - 1; i++){
            System.out.print(numbers[x][i] + " ");
        }
        System.out.println();

        // 环的高度至少为2才会输出右边的一列，
        // rows-x-1 表示的是环最下的那一行的行号
        if(rows - x - 1 > x) {
            //因为右边那一列的最上面的那个已经被输出了，所以行号从 x+1 开始，输出包括右边那列的最下面的那个
            for (int i = x + 1; i <= rows - x - 1; i++) {
                System.out.print(numbers[i][cols - y - 1] + " ");
            }
            System.out.println();
        }

        // 环的高度至少为2并且环的宽度至少为2才会输出下边的一行,
        // cols-1-y 表示的是环最右边那一列的列号
        if(rows - x - 1 > x && cols - 1 - y > y) {
            //因为环的右下角的位置已经输出了，所以列号从 cols-y-2 开始
            for (int i = cols - y - 2; i >= y; i--) {
                System.out.print(numbers[rows - x - 1][i] + " ");
            }
            System.out.println();
        }

        // 环的宽度至少为2并且环的高度至少为3才会输出最左边的一列
        // rows-x-1 表示的是环最下的那一行的行号
        if(rows - 1 - x > x + 1 && cols - 1 - y > y) {
            //因为环的最左边那一列的第一个和最后一个已经输出了
            for (int i = rows - 1 - x - 1; i >= x + 1; i--) {
                System.out.print(numbers[i][y] + " ");
            }
            System.out.println();
        }
    }
}
