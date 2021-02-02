package com.sty.algorithm.greedy;

import java.util.Arrays;

/**
 * 分糖果
 *  ①题目：
 *      有`N`个小孩站成一列，每个小孩有一个评级，按照以下要求，给小孩分糖果：
 *          * 每个小孩至少得到一颗糖果；
 *          * 评级越高的小孩比他相邻的两个小孩得到更多的糖果。
 *      最少需要准备多少颗糖果？
 *      There are N children standing in a line. Each child is assigned a rating value.
 *    You are giving candies to these children subjected to the following requirements:
 *      Each child must have at least one candy. Children with a higher rating get more candies
 *    than their neighbors. What is the minimum candies you must give?
 *  ②算法思路：
 *      首先我们会给每个小孩一颗糖果，然后从左到右，假设第`i`个小孩的等级比第`i-1`个小孩高，
 *    那么第`i`个小孩的糖果数量就是第`i-1`个小孩的糖果数量再加一。然后我们从右到左，如果第`i`个小孩的等级大于第`i+1`个小孩的，
 *    同时第`i`个小孩此时的糖果数量小于第`i+1`的小孩，那么第`i`个小孩的糖果数量就是第`i+1`个小孩的糖果数量再加一。
 * @Author: tian
 * @UpdateDate: 2021/2/2 9:24 AM
 */
public class Candy {
    public static void main(String[] args) {
        int[] ratings = {1, 0, 2};
        System.out.println(candy(ratings));
    }

    /**
     * [1 0 2]
     *  1 1 1
     *  1 1 2
     *  2 1 2
     * @param ratings
     * @return
     */
    public static int candy(int[] ratings) {
        if(ratings == null || ratings.length == 0) {
            return 0;
        }

        int[] count = new int[ratings.length];
        Arrays.fill(count, 1);
        int sum = 0;
        for (int i = 1; i < ratings.length; i++) {
            if(ratings[i] > ratings[i - 1]) {
                count[i] = count[i - 1] + 1;
            }
        }

        for (int i = ratings.length - 1; i > 0; i--) {
            sum += count[i];
            if(ratings[i - 1] > ratings[i] && count[i - 1] <= count[i]) { //second round has 2 conditions
                count[i - 1] = count[i] + 1;
            }
        }
        sum += count[0];

        return sum;
    }
}
