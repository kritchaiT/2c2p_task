package com.example;

import java.util.List;

public class DuplicateGroup {
    public String rule;
    public String key;
    public List<Transaction> transactions;

    public DuplicateGroup(String rule, String key, List<Transaction> txns) {
        this.rule = rule;
        this.key = key;
        this.transactions = txns;
    }
}
