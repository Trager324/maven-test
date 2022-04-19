package com.syd.java17.main;

import com.syd.java17.struct.ListNode;

import java.util.*;

/**
 * @author SYD
 * @description
 * @date 2021/9/18
 */
public class Solution {
    int a;

    class Child {
        int b;
        {
            Solution.this.a = 1;
        }
    }


    public static void main(String[] args) {
        Iterator<String> it;
        Solution solution = new Solution();
        final Child child = solution.new Child();
        System.out.println(child);
        Map<String, String> map = new HashMap<>();
        List<String> list = new ArrayList<>();

    }
}
