package com.syd.java17.struct;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONAware;
import com.alibaba.fastjson.TypeReference;

import java.util.*;

import static com.syd.java17.struct.Solution.listToString;

public class TreeNode implements JSONAware {
    public int val;
    public TreeNode left;
    public TreeNode right;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreeNode treeNode = (TreeNode) o;
        return val == treeNode.val;
    }

    @Override
    public int hashCode() {
        return Objects.hash(val);
    }

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    public static TreeNode parseTreeNode(String s) {
        JSONArray array = JSONArray.parseArray(s);
        int n = array.size();
        if (n == 0) return null;
        TreeNode root = array.get(0) == null ? null : new TreeNode(array.getIntValue(0));
        if (root == null) return null;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        for (int i = 1; i < n; ) {
            TreeNode now = Objects.requireNonNull(q.poll());
            now.left = array.get(i) == null ? null : new TreeNode(array.getIntValue(i));
            if (++i == n) break;
            now.right = array.get(i) == null ? null : new TreeNode(array.getIntValue(i));
            i++;
            if (now.left != null) q.offer(now.left);
            if (now.right != null) q.offer(now.right);
        }
        return root;
    }

    @Override
    public String toString() {
        List<Integer> list = new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(this);
        while (!q.isEmpty()) {
            TreeNode now = q.poll();
            if (now == null) list.add(null);
            else {
                list.add(now.val);
                q.offer(now.left);
                q.offer(now.right);
            }
        }
        for (int i = list.size() - 1; i > 0; i--) {
            if (list.get(i) == null) {
                list.remove(i);
            } else {
                break;
            }
        }
        return listToString(list);
    }

    @Override
    public String toJSONString() {
        return toJSONString(true);
    }

    public String toJSONString(boolean briefly) {
        StringBuilder sb = new StringBuilder(briefly ? "{v:" : "{val:").append(val);
        if (left != null) sb.append(briefly ? ",l:" : ",left:").append(left.toJSONString(briefly));
        if (right != null) sb.append(briefly ? ",r:" : ",right:").append(right.toJSONString(briefly));
        return sb.append("}").toString();
    }

    public static void main(String[] args) {
        TreeNode root = parseTreeNode("[1,2,3,null,5,6,null,10,11,null,13]");
        String jsonStr = Objects.requireNonNull(root).toJSONString(false);
        System.out.println(root);
        System.out.println(jsonStr);
        TreeNode parsed = JSON.parseObject(jsonStr, TreeNode.class);
        System.out.println(parsed);
        parsed = JSON.parseObject(jsonStr, new TypeReference<>() {
        });
        System.out.println(parsed);
    }
}
