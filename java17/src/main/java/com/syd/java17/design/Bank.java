package com.syd.java17.design;

class Bank {
    final int n;
    final long[] balance;
    public Bank(long[] balance) {
        n = balance.length;
        this.balance = balance;
    }

    public boolean transfer(int account1, int account2, long money) {
        if (account1 > n || account1 <= 0 || account2 > n || account2 <= 0) {
            return false;
        }
        if (balance[--account1] < money) {
            return false;
        }
        balance[account1] -= money;
        balance[--account2] += money;
        return true;
    }

    public boolean deposit(int account, long money) {
        if (account > n || account <= 0) {
            return false;
        }
        balance[--account] += money;
        return true;
    }

    public boolean withdraw(int account, long money) {
        if (account > n || account <= 0) {
            return false;
        }
        if (balance[--account] < money) {
            return false;
        }
        balance[account] -= money;
        return true;
    }
}
