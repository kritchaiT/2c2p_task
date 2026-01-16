public class Transaction {
    
    private String transactionId;
    private String merchantRef;
    private Double amount;
    private String currency;
    private String status;
    private String createdAtUtc;

    private int flagDup = 0; // default 0 -> not-duplicate, 1 -> duplicated
    private int flagIncompleted = 0; // default 0 -> completed, 1 -> incompleted

    // Getters
    public String getTransactionId() { return transactionId; }
    public String getMerchantRef() { return merchantRef; }
    public Double getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public String getStatus() { return status; }
    public String getCreatedAtUtc() { return createdAtUtc; }
    public int getFlagDup() {return flagDup; }
    public int getFlagIncompleted() {return flagIncompleted; }

    // Setters -> for Jackson to map JSON properties
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public void setMerchantRef(String merchantRef) { this.merchantRef = merchantRef; }
    public void setAmount(Double amount) { this.amount = amount; }
    public void setCurrency(String currency) { this.currency = currency; }
    public void setStatus(String status) { this.status = status; }
    public void setCreatedAtUtc(String createdAtUtc) { this.createdAtUtc = createdAtUtc; }

    @Override
    public String toString() {
        return String.format("[%s] %s: %.2f %s (%s)", 
            createdAtUtc, transactionId, amount, currency, status);
    }

    public void doFlagDup(int inflag) {
        this.flagDup = inflag;
    }
    public void doFlagIncompleted(int inflag) {
        this.flagIncompleted = inflag;
    }
}
