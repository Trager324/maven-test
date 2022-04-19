package com.syd.java17.test;

import com.syd.java17.struct.ListNode;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

//class Solution {
//    int size = 0;
//    TreeMap<Integer, Integer> treeMap = new TreeMap<>();
//    ThreadLocalRandom random = ThreadLocalRandom.current();
//
//    public Solution(ListNode head) {
//        Map<Integer, Integer> hashMap = new HashMap<>();
//        for (ListNode n = head; n != null; n = n.next) {
//            int key = n.val;
//            hashMap.put(key, hashMap.getOrDefault(key, 0) + 1);
//        }
//        for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
//            treeMap.put(size, entry.getKey());
//            size += entry.getValue();
//        }
//    }
//
//    public int getRandom() {
//        return treeMap.floorEntry(random.nextInt(size)).getValue();
//    }
//
//    public static void main(String[] args) {
//        ListNode n = ListNode.parseListNode("[1,1,1,2,2,3,3,3,3,4,4,4,4,4,4]");
//        Solution solution = new Solution(n);
//        for (int i = 0; i < 100; i++) {
//            System.out.println(solution.getRandom());
//        }
//    }
//}
class Solution {
    int[] arr;
//    ThreadLocalRandom random = ThreadLocalRandom.current();
    Random random = new Random();
    public Solution(ListNode head) {
        int count = 0;
        ListNode p = head;
        while (p != null) {
            count++;
            p = p.next;
        }
        arr = new int[count];
        for (int i = 0; i < count; i++) {
            arr[i] = head.val;
            head = head.next;
        }
    }

    public int getRandom() {
        return arr[random.nextInt(arr.length)];
    }
}
