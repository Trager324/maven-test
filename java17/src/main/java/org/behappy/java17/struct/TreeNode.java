package org.behappy.java17.struct;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

import static org.behappy.java17.struct.Solution.list2Str;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int val) {
        this.val = val;
    }

    public static TreeNode parseTreeNode(String s) {
        String[] numStrings = s.substring(1, s.length() - 1).split(",");
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

    public static void main(String[] args) {
        TreeNode root = parseTreeNode("[1,2,3,null,5,6,null,10,11,null,13]");
        String jsonStr = Objects.requireNonNull(root).toJSONString(false);
        System.out.println(root);
        System.out.println(jsonStr);
        TreeNode parsed = JSON.parseObject(jsonStr, TreeNode.class);
        System.out.println(parsed);
        parsed = JSON.parseObject(jsonStr, TreeNode.class);
        System.out.println(parsed);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreeNode treeNode = (TreeNode)o;
        return val == treeNode.val;
    }

    public String serialize(TreeNode root) {
        return root.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(val);
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
        return list2Str(list);
    }

    public String toJSONString() {
        return toJSONString(true);
    }

    public String toJSONString(boolean briefly) {
        StringBuilder sb = new StringBuilder(briefly ? "{v:" : "{val:").append(val);
        if (left != null) sb.append(briefly ? ",l:" : ",left:").append(left.toJSONString(briefly));
        if (right != null) sb.append(briefly ? ",r:" : ",right:").append(right.toJSONString(briefly));
        return sb.append("}").toString();
    }
}
