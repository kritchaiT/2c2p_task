package com.example;

import java.util.List;
import java.util.Map;

public class Report {

    // ---------------- Overall record counts ----------------

    /** Total records loaded from input */
    public int totalRecords;

    /** Records that passed validation */
    public int validRecords;

    /** Records that failed validation */
    public int invalidRecords;

    /** Invalid records breakdown by rejection reason */
    public Map<String, Integer> invalidBreakdown;

    // ---------------- Status summary ----------------

    /**
     * Counts by status for VALID records only
     * Example: SUCCESS=10, FAILED=3, PENDING=2
     */
    public Map<String, Integer> statusCounts;

    // ---------------- SUCCESS statistics ----------------

    /**
     * Min / Max / Avg amount
     * Computed from VALID + SUCCESS + NON-DUPLICATE transactions only
     */
    public AmountStats successAmountStats;

    // ---------------- Duplicate summary ----------------

    public DuplicateSummary duplicates;

    // ====================================================
    // Inner classes
    // ====================================================

    public static class DuplicateSummary {

        /** Number of duplicate groups detected */
        public int duplicateGroupCount;

        /** Total number of records involved in duplicates */
        public int duplicateRecordCount;

        /** Detailed duplicate groups */
        public List<DuplicateGroup> groups;

        public DuplicateSummary(
                int duplicateGroupCount,
                int duplicateRecordCount,
                List<DuplicateGroup> groups
        ) {
            this.duplicateGroupCount = duplicateGroupCount;
            this.duplicateRecordCount = duplicateRecordCount;
            this.groups = groups;
        }
    }
}
