package com.sty.algorithm;

import android.text.TextUtils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    public static void main(String[] args) {
        //String s = "25525511135";
        String s = "001011";
        printArr(solution(s));
    }

    public static boolean isValid(String s) {
        if(s == null || s.length() == 0 || s.length() > 3 || (s.charAt(0) == '0' && s.length() > 1) || Integer.parseInt(s) > 255) {
            return false;
        }
        return true;
    }

    public static List<String> solution(String s) {
        List<String> res = new ArrayList<>();
        if(s == null || s.length() < 4 || s.length() > 12) {
            return res;
        }

        for (int i = 1; i < s.length() - 2 && i < 4; i++) {
            for (int j = i + 1; j < s.length() - 1 && j < i + 4; j++) {
                for (int k = j + 1; k < s.length() && k < j + 4; k++) {
                    String s1 = s.substring(0, i);
                    String s2 = s.substring(i, j);
                    String s3 = s.substring(j, k);
                    String s4 = s.substring(k);
                    if(isValid(s1) && isValid(s2) && isValid(s3) && isValid(s4)) {
                        res.add(s1 + "." + s2 + "." + s3 + "." + s4);
                    }
                }
            }
        }
        return res;
    }

    private static void printArr(List<String> res) {
        if(res == null || res.isEmpty()) {
            System.out.println("没有找到符合要求的数据");
            return;
        }
        for (String re : res) {
            System.out.println(re);
        }
    }
}