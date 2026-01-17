package com.example;

public class Transaction {

    private String transactionId;
    private String merchantRef;
    private Double amount;
    private String currency;
    private String status;
    private String createdAtUtc;

    private int flagDup = 0;
    private int flagIncompleted = 0;

    // Getters
    public String getTransactionId() { return transactionId; }
    public String getMerchantRef() { return merchantRef; }
    public Double getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public String getStatus() { return status; }
    public String getCreatedAtUtc() { return createdAtUtc; }
    public int getFlagDup() { return flagDup; }
    public int getFlagIncompleted() { return flagIncompleted; }

    // Setters
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public void setMerchantRef(String merchantRef) { this.merchantRef = merchantRef; }
    public void setAmount(Double amount) { this.amount = amount; }
    public void setCurrency(String currency) { this.currency = currency; }
    public void setStatus(String status) { this.status = status; }
    public void setCreatedAtUtc(String createdAtUtc) { this.createdAtUtc = createdAtUtc; }

    public void flagDuplicate() {
        this.flagDup = 0;
    }

    public void flagIncomplete() {
        this.flagIncompleted = 0;
    }

    // mimicing the printout of object --> for debugging purposes
    @Override
    public String toString() {
        return String.format("[%s] %s: %.2f %s (%s) - %d - %d",
                createdAtUtc, transactionId, amount, currency, status, flagDup, flagIncompleted);
    }
}
