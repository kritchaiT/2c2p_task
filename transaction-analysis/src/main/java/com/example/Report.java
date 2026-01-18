package com.example;

import java.util.List;
import java.util.Map;

public class Report {

    // Overall counts
    public int totalRecords;
    public int validRecords;
    public int invalidRecords;

    // Validation
    public Map<String, Integer> invalidBreakdown;

    // Applied (idempotent) SUCCESS only
    public int successAppliedRecords;

    // Status summary (usually only SUCCESS)
    public Map<String, Integer> statusCounts;

    // SUCCESS amount statistics
    public AmountStats successAmountStats;

    // Duplicate section
    public DuplicateSection duplicates;

    // ----------------------------------------------------

    public static class DuplicateSection {
        public int duplicateGroupCount;
        public List<DuplicateGroup> groups;

        public DuplicateSection(int count, List<DuplicateGroup> groups) {
            this.duplicateGroupCount = count;
            this.groups = groups;
        }
    }
}
