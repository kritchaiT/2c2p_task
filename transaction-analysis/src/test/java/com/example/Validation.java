package com.example;

import java.time.Instant;
import java.util.*;

public class Validation {

    public static Map<String, List<Transaction>> validateTransactions(
            List<Transaction> txs) {

        List<Transaction> valid = new ArrayList<>();
        List<Transaction> invalid = new ArrayList<>();

        for (Transaction tx : txs) {

            if (tx.getTransactionId() == null ||
                tx.getMerchantRef() == null ||
                tx.getCurrency() == null ||
                tx.getStatus() == null ||
                tx.getCreatedAtUtc() == null) {

                tx.setReasonForRejection("MISSING_FIELD");
                invalid.add(tx);
                continue;
            }

            if (tx.getAmount() <= 0) {
                tx.setReasonForRejection("INVALID_AMOUNT");
                invalid.add(tx);
                continue;
            }

            if (tx.getCurrency().length() != 3) {
                tx.setReasonForRejection("INVALID_CURRENCY");
                invalid.add(tx);
                continue;
            }

            if (!Set.of("SUCCESS", "FAILED", "PENDING")
                    .contains(tx.getStatus())) {
                tx.setReasonForRejection("INVALID_STATUS");
                invalid.add(tx);
                continue;
            }

            try {
                Instant.parse(tx.getCreatedAtUtc());
            } catch (Exception e) {
                tx.setReasonForRejection("INVALID_DATE");
                invalid.add(tx);
                continue;
            }

            valid.add(tx);
        }

        Map<String, List<Transaction>> result = new HashMap<>();
        result.put("valid", valid);
        result.put("invalid", invalid);
        return result;
    }
}
