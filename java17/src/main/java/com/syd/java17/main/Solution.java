package com.syd.java17.main;

import com.alibaba.fastjson2.JSON;

// Definition for a QuadTree node.
class Node {
    public boolean val;
    public boolean isLeaf;
    public Node topLeft;
    public Node topRight;
    public Node bottomLeft;
    public Node bottomRight;


    public Node() {
        this.val = false;
        this.isLeaf = false;
        this.topLeft = null;
        this.topRight = null;
        this.bottomLeft = null;
        this.bottomRight = null;
    }

    public Node(boolean val, boolean isLeaf) {
        this.val = val;
        this.isLeaf = isLeaf;
        this.topLeft = null;
        this.topRight = null;
        this.bottomLeft = null;
        this.bottomRight = null;
    }

    public Node(boolean val, boolean isLeaf, Node topLeft, Node topRight, Node bottomLeft, Node bottomRight) {
        this.val = val;
        this.isLeaf = isLeaf;
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
    }
};


class Solution {
    Node construct(int[][] grid, int x0, int x1, int y0, int y1) {
        if (x1 - x0 == 1 && y1 - y0 == 1) {
            return new Node(grid[x0][y0] == 1, true);
        }
        int xm = (x0 + x1) >> 1;
        int ym = (y0 + y1) >> 1;
        Node topLeft = construct(grid, x0, xm, y0, ym);
        Node topRight = construct(grid, x0, xm, ym, y1);
        Node bottomLeft = construct(grid, xm, x1, y0, ym);
        Node bottomRight = construct(grid, xm, x1, ym, y1);
        if (topLeft.isLeaf && topRight.isLeaf && bottomLeft.isLeaf && bottomRight.isLeaf &&
                topLeft.val == topRight.val && topRight.val == bottomLeft.val && bottomLeft.val == bottomRight.val) {
            return new Node(topLeft.val, true);
        }
        return new Node(true, false, topLeft, topRight, bottomLeft, bottomRight);
    }

    public Node construct(int[][] grid) {
        return construct(grid, 0, grid.length, 0, grid[0].length);
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        Node node = solution.construct(com.syd.java17.struct.Solution.parseIntMatrix(
                "[[0,1],[1,0]]"
        ));
        System.out.println(JSON.toJSONString(node));
//        Node node = solution.construct(new int[][]{
//                {1, 1, 1, 1, 0, 0, 0, 0},
//                {1, 1, 1, 1, 0, 0, 0, 0},
//                {1, 1, 1, 1, 1, 1, 1, 1},
//                {1, 1, 1, 1, 1, 1, 1, 1},
//                {1, 1, 1, 1, 0, 0, 0, 0},
//                {1, 1, 1, 1, 0, 0, 0, 0},
//                {1, 1, 1, 1, 0, 0, 0, 0},
//                {1, 1, 1, 1, 0, 0, 0, 0}
//        });
//        System.out.println(node.val);
    }
}
