package com.sty.algorithm.other;

import java.util.Random;

/**
 * 蓄水池抽样算法
 * 题目描述：
 *      从n个数中随机取m个数（n>m）
 *
 * 算法思想：
 *      源数组为a[]，大小为n，“水池”即存放取出的数组b[]，大小为m，若要做到随机，则每个数据被选中的概率为m/n
 * 操作如下：
 *      ①对数组a[]进行遍历，设置j为遍历时当前元素的下标，将a[]数组的前m个数据依次存入数组b[]中，此操作a[]数组中的前
 *          m个数被加入b[]数组的概率为100%
 *      ②当i=m-1时，此时b[]数组的元素已满，若后面的元素要加入b[]数组，则要与池中的元素替换，替换的规则如下：
 *          在0-i（i为当前元素下标，此时i>=m）的范围内随机出一个数字r,r<m则将i对应的元素a[i]值赋予水池中的b[r],
 *          水池中r对应的数据被替换掉；
 *          如果r>=m，则i++向前移动，也就表示池中的数据没有被替换
 *      ③i每向前移动一步，则重复步骤②，直到数组a[]被完全遍历。
 *
 * 分析概率（为分析简单，以下的i从1开始）：
 *      ①对于第i个数（i<=m）,在第i步之前，被选中的概率为1；当走到第m+1步时，被m+1个元素替换的概率 = 第m+1个元素被
 *          选中的概率 * i被选中替换的概率，即m/(m+1) * 1/m = 1/(m+1)。则不被m+1个元素替换的概率为1 - 1/(m+1) = m/(m+1).
 *          依次类推，不被m+2个元素替换的概率为1 - m/(m+2) * 1/m = (m+1)/(m+2),则到第n步时，第i个数让保留的概率 =
 *          被选中的概率 * 不被替换的概率，即：1 * m/(m+1) * m+1/(m+2) * (m+2)/(m+3) * ... * (n-1)/n = m/n.
 *      ②对于第j个数（j>m），我们知道，在第j步被选中的概率为m/j(选中了就会被放到池子中，但要保留的话就要保证该数
 *          不会被后面的步骤替换掉)。不被j+1个元素替换的概率为 1 - m/(j+1) * 1/m = j/(j+1)；则运行到第n步时，
 *          被保留的概率 = 被选中的概率 * 不被替换的概率，即：m/j * j/(j+1) * ... * (n-1)/n = m/n.
 *      所以对于其中的每个元素，被保留的概率都为m/n.
 *
 * @Author: tian
 * @UpdateDate: 2020/11/16 5:22 PM
 */
public class ReservoirSampling {


    public static void main(String[] args) {
        probabilityTest();
    }

    /**
     * 蓄水池抽样算法
     * @param k 未知大小的数据源
     * @param m 蓄水池大小（随机抽样的个数）
     * @return
     */
    public static int[] reservoirSampling(int k[], int m) {
        int[] pool = new int[m];
        if(k.length <= m) {
            return new int[0];
        }

        Random random = new Random();
        for (int i = 0; i < k.length; i++) {
            if(i < m) {
                pool[i] = k[i]; //将前m个数放入数组（池）中
            }else { //从 0-i 中随机出一个数
                int r = random.nextInt(i + 1);
                if(r < m) {
                    pool[r] = k[i]; //如果随机出的r<水池大小，则进行替换
                }
            }
        }

        return pool;
    }


    //概率测试：从5个数中随机抽出3个数，抽10000000次，看每个数出现的概率
    public static void probabilityTest() {
        int N[] = {0, 1, 2, 3, 4};
        int p[] = {0, 0, 0, 0, 0};
        for (int i = 0; i < 10000000; i++) {
            int l[] = reservoirSampling(N, 3);
            for (int j = 0; j < l.length; j++) {
                for (int k = 0; k < N.length; k++) {
                    if(l[j] == N[k]) {
                        p[k]++;
                    }
                }
            }
        }

        for (int i = 0; i < N.length; i++) {
            System.out.print("   " + N[i] + "    ");
        }
        System.out.println();
        for (int i = 0; i < p.length; i++) {
            System.out.print(p[i] + " ");
        }
        System.out.println();

        //    0       1       2       3       4
        // 5999097 5997547 6000220 6003226 5999910
    }
}
