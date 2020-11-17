package com.db.model;

public class AccountDTO {
    private String Account;
    private int money;

    public AccountDTO(String Account, int money) {
        this.Account = Account;
        this.money = money;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String Account) {
        this.Account = Account;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
