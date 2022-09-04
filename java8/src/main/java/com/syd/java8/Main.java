package com.syd.java8;

import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt(), q = input.nextInt(), k = input.nextInt(), x = input.nextInt();
        List<Integer> list = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            list.add(input.nextInt());
        }
        while (q-- > 0) {
            int l = input.nextInt(), r = input.nextInt();
            PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
            pq.addAll(list.subList(l - 1, r));
            int sum = 0, i = 0;
            while (pq.size() > 0 && i < k) {
                sum += pq.poll();
                if (sum >= x) {
                    break;
                }
                i++;
            }
            System.out.println(sum >= x ? "Y" : "N");
        }
        input.close();
    }
}

