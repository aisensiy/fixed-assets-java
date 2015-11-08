package com.tw.domain;

import java.sql.Timestamp;

public class Base {
    private Timestamp createdAt;
    private int amount;
    private final int depreciation;

    public Base(int amount, int depreciation, Timestamp createdAt) {

        this.amount = amount;
        this.depreciation = depreciation;
        this.createdAt = createdAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public int getAmount() {
        return amount;
    }
}
