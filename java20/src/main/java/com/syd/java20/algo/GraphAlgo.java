package com.syd.java20.algo;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 图算法
 *
 * @author songyide
 * @date 2022/11/26
 */
public class GraphAlgo {
    /**
     * dijkstra求无负权图最短距离
     *
     * @param graph 邻接表，第0项为下一个点，第1项为距离
     * @param start 开始点
     * @return 最短路径
     */
    public static int[] dijkstra(List<int[]>[] graph, int start) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparing(a -> a[0]));
        int[] dis = new int[graph.length];
        Arrays.fill(dis, Integer.MAX_VALUE);
        pq.offer(new int[]{0, start});
        dis[start] = 0;
        while (!pq.isEmpty()) {
            var now = pq.poll();
            int x = now[1], d = now[0];
            if (d > dis[x]) continue;
            for (var nxt : graph[x]) {
                int nx = nxt[0], td = d + nxt[1];
                if (td < dis[nx]) {
                    dis[nx] = td;
                    pq.offer(new int[]{td, nx});
                }
            }
        }
        return dis;
    }
}
