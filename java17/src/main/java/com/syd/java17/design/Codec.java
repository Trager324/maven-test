package com.syd.java17.design;

import com.syd.java17.struct.TreeNode;

import java.util.*;

public class Codec {
    public String serialize(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
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
        StringBuilder sb = new StringBuilder("[");
        for (Object o : list) {
            sb.append(o).append(",");
        }
        return sb.deleteCharAt(sb.length() - 1).append("]").toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String[] numStrings = data.substring(1, data.length() - 1).split(",");
        int n = numStrings.length;
        if (n == 0) return null;
        TreeNode root = "null".equals(numStrings[0]) ? null : new TreeNode(Integer.parseInt(numStrings[0]));
        if (root == null) return null;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        for (int i = 1; i < n; ) {
            TreeNode now = Objects.requireNonNull(q.poll());
            now.left = "null".equals(numStrings[i]) ? null : new TreeNode(Integer.parseInt(numStrings[i]));
            if (++i == n) break;
            now.right = "null".equals(numStrings[i]) ? null : new TreeNode(Integer.parseInt(numStrings[i]));
            i++;
            if (now.left != null) q.offer(now.left);
            if (now.right != null) q.offer(now.right);
        }
        return root;
    }
}
