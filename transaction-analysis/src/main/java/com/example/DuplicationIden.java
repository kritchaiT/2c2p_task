package com.example;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DuplicationIden {

    private static LocalDate safeParseUtcDay(String utc) {
    try {
        return OffsetDateTime.parse(utc).toLocalDate();
    } catch (Exception e) {
        return null; // invalid date
    }
}

    public static void identifyDup(List<Transaction> txxs) {
        // txxs -> transactions
        // txn -> each transaction from transactions(txxs)
        System.out.println("duplication identification working...");

        // Count occurrences of each transactionId
        Map<String, Integer> countByTxnId = new HashMap<>();
        Map<String, Integer> countByMerchantAmountDay = new HashMap<>();

        // for (Transaction txn : txxs) {
        //     String txnId = txn.getTransactionId();
        //     if (txnId != null && !txnId.isEmpty()) {
        //         countByTxnId.put(txnId, countByTxnId.getOrDefault(txnId, 0) + 1);
        //     }
        // }

        // ---------- FIRST PASS: count ----------
        for (Transaction txn : txxs) {

            // Rule 1 count
            String txnId = txn.getTransactionId();
            if (txnId != null && !txnId.isEmpty()) {
                countByTxnId.put(txnId, countByTxnId.getOrDefault(txnId, 0) + 1);
            }

            // Rule 2 count
            if (txn.getMerchantRef() != null &&
                txn.getAmount() != null &&
                txn.getCurrency() != null &&
                txn.getCreatedAtUtc() != null) {

                LocalDate utcDay = safeParseUtcDay(txn.getCreatedAtUtc());
                if (utcDay == null) {
                    continue; // skip Rule 2 counting
                }

                String key = txn.getMerchantRef() + "|" +
                             txn.getAmount() + "|" +
                             txn.getCurrency() + "|" +
                             utcDay;

                countByMerchantAmountDay.put(
                        key,
                        countByMerchantAmountDay.getOrDefault(key, 0) + 1
                );
            }
        }

        // Flag duplicates
        for (Transaction txn : txxs) {
            String txnId = txn.getTransactionId();

            // // Case 1: if count > 1, flag as duplicate
            // if (txnId != null && countByTxnId.getOrDefault(txnId, 0) > 1) {
            //     txn.flagDuplicate();
            //     txn.setReasonForRejection("Duplicated Txn ID");
            //     System.out.println("Duplicate found: " + txn);
            // }
            // // Case 2: if transactionId is missing, also flag as duplicate
            // else if (txnId == null || txnId.isEmpty()) {
            //     txn.flagDuplicate();
            //     txn.setReasonForRejection("Missing Txn ID");
            //     System.out.println("Missing Txn ID found: " + txn);
            // }
            // else if (){

            // }
            // // Else, not a duplicate
            // Case 1: Duplicate Transaction ID
            if (txnId != null && countByTxnId.getOrDefault(txnId, 0) > 1) {
                txn.flagDuplicate();
                txn.setReasonForRejection("Duplicated Txn ID");
                System.out.println("Duplicate found: " + txn);
            }

            // Case 2: Missing Transaction ID
            else if (txnId == null || txnId.isEmpty()) {
                txn.flagDuplicate();
                txn.setReasonForRejection("Missing Txn ID");
                System.out.println("Missing Txn ID found: " + txn);
            }

            // Case 3: MERCHANT_AMOUNT_DAY
            else if (txn.getMerchantRef() != null &&
                     txn.getAmount() != null &&
                     txn.getCurrency() != null &&
                     txn.getCreatedAtUtc() != null) {

                LocalDate utcDay = safeParseUtcDay(txn.getCreatedAtUtc());
                if (utcDay == null) {
                    txn.flagDuplicate();
                    txn.setReasonForRejection("Invalid createdAtUtc");
                    System.out.println("Invalid date found: " + txn);
                    continue;
                }

                String key = txn.getMerchantRef() + "|" +
                             txn.getAmount() + "|" +
                             txn.getCurrency() + "|" +
                             utcDay;

                if (countByMerchantAmountDay.getOrDefault(key, 0) > 1) {
                    txn.flagDuplicate();
                    txn.setReasonForRejection("Duplicated MERCHANT_AMOUNT_DAY");
                    System.out.println("Merchant/Amount/Day duplicate found: " + txn);
                }
            }
            // else → valid transaction
        }
    }
}
