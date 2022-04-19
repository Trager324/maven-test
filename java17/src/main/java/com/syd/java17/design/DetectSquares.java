package com.syd.java17.design;

import java.util.HashMap;
import java.util.Map;

//
//class DetectSquares {
//    static class P {
//        int x, y;
//        P(int x, int y) {this.x = x; this.y = y;}
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//            P p = (P)o;
//            return x == p.x && y == p.y;
//        }
//
//        @Override
//        public int hashCode() {
//            return Objects.hash(x, y);
//        }
//    }
//    Map<Integer, List<P>> positiveMap = new HashMap<>();
//    Map<Integer, List<P>> negativeMap = new HashMap<>();
//    Map<P, Integer> countMap = new HashMap<>();
//
//    public DetectSquares() {
//    }
//
//    public void add(int[] point) {
//        P p = new P(point[0], point[1]);
//        positiveMap.computeIfAbsent(p.y - p.x, k -> new ArrayList<>()).add(p);
//        negativeMap.computeIfAbsent(p.x + p.y, k -> new ArrayList<>()).add(p);
//        countMap.merge(p, 1, Integer::sum);
//    }
//
//    private int getCount(List<P> list, int x, int y) {
//        int res = 0;
//        if (list != null) {
//            for (P p : list) {
//                if (p.x == x) continue;
//                int countX = countMap.getOrDefault(new P(x, p.y), 0);
//                int countY = countMap.getOrDefault(new P(p.x, y), 0);
//                res += countX * countY;
//            }
//        }
//        return res;
//    }
//
//    public int count(int[] point) {
//        int x = point[0], y = point[1];
//        return getCount(positiveMap.get(y - x), x, y) + getCount(negativeMap.get(x + y), x, y);
//    }
//    public static void main(String[] args) {
//        DetectSquares ds = new DetectSquares();
//        ds.add(new int[]{3,10});
//        ds.add(new int[]{11,2});
//        ds.add(new int[]{3,2});
//        System.out.println(ds.count(new int[]{11, 10}));
//        System.out.println(ds.count(new int[]{14, 8}));
//        ds.add(new int[]{11,2});
//        System.out.println(ds.count(new int[]{11, 10}));
//    }
//}
class DetectSquares {
    Map<Integer, Map<Integer, Integer>> xyMap = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> yxMap = new HashMap<>();
    public DetectSquares() {}

    public void add(int[] point) {
        int x = point[0];
        int y = point[1];
        put(xyMap, x, y);
        put(yxMap, y, x);
    }

    private void put(Map<Integer, Map<Integer, Integer>> xyMap, int x, int y) {
        xyMap.computeIfAbsent(x, (key) -> new HashMap<>())
                .compute(y, (key, val) -> val == null ? 1 : val + 1);
    }

    public int count(int[] point) {
        int x = point[0];
        int y = point[1];
        Map<Integer, Integer> xMap = yxMap.get(y);
        if (xMap == null) {
            return 0;
        }
        Map<Integer, Integer> yMap = xyMap.get(x);
        if (yMap == null) {
            return 0;
        }
        if (xMap.size() < yMap.size()) {
            return count(xMap, yMap, xyMap, x, y);
        } else {
            return count(yMap, xMap, yxMap, y, x);
        }
    }

    private int count(Map<Integer, Integer> xMap, Map<Integer, Integer> yMap, Map<Integer, Map<Integer, Integer>> xyMap, int x, int y) {
        int count = 0;
        for (Map.Entry<Integer, Integer> e : xMap.entrySet()) {
            int x1 = e.getKey();
            if (x1 == x) {
                continue;
            }
            for (int sig = -1; sig <= 1; sig += 2) {
                int y1 = y + sig * (x1 - x);
                Integer count1 = yMap.get(y1);
                if (count1 == null) {
                    continue;
                }
                Integer count2 = xyMap.get(x1).get(y1);
                if (count2 == null) {
                    continue;
                }
                count += e.getValue() * count1 * count2;
            }
        }
        return count;
    }
}

