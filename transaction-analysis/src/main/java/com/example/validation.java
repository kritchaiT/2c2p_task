package com.example;

public class validation {
    public static boolean isValidTransaction(Transaction tx) {
        // if any logic was wrong -> return false
        if (tx.getTransactionId() == null || tx.getTransactionId().isEmpty()) {
            return false;
        }
        if (tx.getMerchantRef() == null || tx.getMerchantRef().isEmpty()) {
            return false;
        }
        if (tx.getAmount() == null || tx.getAmount() < 0) {
            return false;
        }
        if ((tx.getCurrency() == null || tx.getCurrency().isEmpty()) && tx.getCurrency().length() == 3) {
            return false;
        }
        if (tx.getStatus() == null || tx.getStatus().isEmpty()) {
            return false;
        }
        if (tx.getCreatedAtUtc() == null || tx.getCreatedAtUtc().isEmpty()) {
            return false;
        }
        return true;
    }
}
