import com.fasterxml.jackson.annotation.JsonProperty;

public class Transaction {
    @JsonProperty("transactionId")
    private String transactionId;
    
    @JsonProperty("merchantRef")
    private String merchantRef;
    
    @JsonProperty("amount")
    private Double amount;
    
    @JsonProperty("currency")
    private String currency;
    
    @JsonProperty("status")
    private String status;

    @JsonProperty("createdAtUtc")
    private String createdAtUtc;

    // Getters and Setters
    public String getTransactionId() { return transactionId; }
    public String getMerchantRef() { return merchantRef; }
    public Double getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public String getStatus() { return status; }
    public String getCreatedAtUtc() { return createdAtUtc; }

    // Setters
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
}
