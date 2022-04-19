package com.syd.java17.struct;

class NumArray {
    int[] nums;
    int n;
    int[] tree;

    public int lowbit(int num) {
        return num & (-num);
    }

    public int query(int index) {
        int res = 0;
        for (int i = index; i > 0; i -= lowbit(i)) {
            res += tree[i];
        }
        return res;
    }

    public void add(int index, int val) {
        for (int i = index; i <= n; i += lowbit(i)) {
            tree[i] += val;
        }
    }

    public NumArray(int[] nums) {
        this.nums = nums;
        n = nums.length;
        tree = new int[n + 1];
        for (int i = 0; i < n; ++i) {
            add(i + 1, nums[i]);
        }
    }

    public void update(int index, int val) {
        int change = val - nums[index];
        nums[index] = val;
        add(index + 1, change);
    }

    public int sumRange(int left, int right) {
        return query(right + 1) - query(left);
    }
}

/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * obj.update(index,val);
 * int param_2 = obj.sumRange(left,right);
 */
