package com.sty.algorithm.dynamicprograming;

/**
 * 股票交易最高价格
 *     一个数组`prices`，其中`prices[i]`表示第`i`天股票的价格，需要最大利润。
 *
 * 一、仅限一次交易
 *     需要在最低价买入，最高价卖出，当然买入一定要在卖出之前，需要遍历一次数组，通过一个变量记录当前最低价格，
 *   同时算出此次交易利润，并与当前最大值比较即可。
 *
 * 二、不限交易次数，但不能同时参与多个交易
 *     因为不限制交易次数，我们在第`i`天买入，如果发现`i+1`天比`i`高，那么就可以卖出累加到利润里面。
 *
 * 三、最多允许两次交易
 *     最多允许两次不相交的交易，也就意味着这两次交易间存在某一分界线，考虑到可以只交易一次，也可交易0次，
 *   故分界线的变化范围为第一天至最后一天，值需要考虑分界线两边各自的最大利润，最后选出利润和最大的即可。
 *     这种方法抽象之后则为首先将 [1, n] 拆分为 [1, i] 和 [i+1, n]，参考买股票的第一题计算各自区间的最大利润即可。
 *   [1, i] 区间的最大利润很好算，但是如何计算 [i+1, n] 区间的最大利润呢？难道有重复`n`次才能得到？
 *   注意到区间的右侧`n`是个不变值，我们从 [1, i] 计算最大利润时更新波谷的值，那么我们可否逆序计算最大利润呢？
 *   这时候就需要更新记录波峰的值了。
 *
 * @Author: tian
 * @UpdateDate: 2021/1/8 10:02 AM
 */
public class StockTransaction {
    public static void main(String[] args) {
        int[] prices = {4, 4, 6, 1, 1, 4, 2, 5};
        System.out.println(maxProfitInOneTransaction(prices));  //4
        System.out.println(maxProfitInMultiTransactions(prices));  //8
        System.out.println(maxProfitInAtMostTwoTransactions(prices));  //6
    }


    /**
     * 仅限一次交易
     * @param prices 价格数组
     * @return
     */
    private static int maxProfitInOneTransaction(int[] prices) {
        if(prices == null || prices.length == 0) {
            return 0;
        }

        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;
        for (int price : prices) {
            if (price < minPrice) {
                minPrice = price;
            }
            maxProfit = Math.max(maxProfit, price - minPrice);
        }

        return maxProfit;
    }

    /**
     * 可以多次交易
     * @param prices 价格数组
     * @return
     */
    private static int maxProfitInMultiTransactions(int[] prices) {
        if(prices == null || prices.length == 0) {
            return 0;
        }

        int maxProfit = 0;
        for (int i = 0; i < prices.length - 1; i++) {
            int diff = prices[i + 1] - prices[i];
            if(diff > 0) {
                maxProfit += diff;
            }
        }

        return maxProfit;
    }

    /**
     * 最多两次交易
     * 价格数组：    4  4  6  1  1  4  2  5
     * profitFont:  0  0  2  2  2  3  3  4
     * profitBack:  4  4  4  4  4  3  3  0
     * @param prices 价格数组
     * @return
     */
    private static int maxProfitInAtMostTwoTransactions(int[] prices) {
        if(prices == null || prices.length == 0) {
            return 0;
        }

        //get profit in the front of prices
        int[] profitFront = new int[prices.length]; //列表中存放在每个价格出售时的最大利润（从前往后）
        profitFront[0] = 0;
        for (int i = 1, valley = prices[0]; i < prices.length; i++) {
            profitFront[i] = Math.max(profitFront[i - 1], prices[i] - valley);
            valley = Math.min(valley, prices[i]);  //valley代表当前之前的最低价格
        }

        //get profit in the back of prices, (i, n)
        int[] profitBack = new int[prices.length];  //列表中存放在每个价格出售时的最大利润（从后往前）
        profitBack[prices.length - 1] = 0;
        for (int i = prices.length - 2, peak = prices[prices.length - 1]; i >=0 ; i--) {
            profitBack[i] = Math.max(profitBack[i + 1], peak - prices[i]);
            peak = Math.max(peak, prices[i]); //peak代表当前之后的最高价格
        }

        //add the profit front and back
        int profit = 0;
        for (int i = 0; i < prices.length; i++) {
            profit = Math.max(profit, profitFront[i] + profitBack[i]);
        }

        return profit;
    }


}
