package com.example;

public class Validation {

    public static boolean isValidTransaction(Transaction tx) {

        if (tx.getTransactionId() == null || tx.getTransactionId().isEmpty()) {
            tx.flagIncomplete();
            return false;
        }

        if (tx.getMerchantRef() == null || tx.getMerchantRef().isEmpty()) {
            tx.flagIncomplete();
            return false;
        }

        if (tx.getAmount() == null || tx.getAmount() < 0) {
            tx.flagIncomplete();
            return false;
        }

        if (tx.getCurrency() == null
                || tx.getCurrency().isEmpty()
                || tx.getCurrency().length() != 3) {
            tx.flagIncomplete();
            return false;
        }

        if (tx.getStatus() == null || tx.getStatus().isEmpty()) {
            tx.flagIncomplete();
            return false;
        }

        if (tx.getCreatedAtUtc() == null || tx.getCreatedAtUtc().isEmpty()) {
            tx.flagIncomplete();
            return false;
        }

        return true;
    }
}
