import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");


    // How to Build (Construct) it
    Transaction t1 = new Transaction("TXN_001", 150.0, "SUCCESS");

    // How to Access (Read) it
    // Notice: Records don't use "get", they just use the field name as a method
    String id = t1.id(); 
    double amt = t1.amount();

    System.out.println("Processing: " + t1.id());
    System.out.println("test print: " + id + " " + amt + " " + t1.status());

    List<Transaction> transactionList = new ArrayList<>();

    transactionList.add(t1);
    transactionList.add(new Transaction("TXN_002", 250.0, "PENDING"));
    transactionList.add(new Transaction("TXN_003", 350.0, "FAILED"));
    transactionList.add(new Transaction("TXN_004", 450.0, "SUCCESS"));
    int i = 0;
    for (Transaction txn : transactionList) {
        System.out.println(txn + " at index " + i);
        i++;
    }
}
}