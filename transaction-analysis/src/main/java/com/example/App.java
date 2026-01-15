import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        String filePath = "transaction.json"; // identify path file for input

        try {
            // Jackson Object Mapper
            ObjectMapper mapper = new ObjectMapper();

            // Load and Parse the JSON file into a List of Transaction objects
            // identify List named "transactions" -> for easy usage
            List<Transaction> transactions = mapper.readValue(
                new File(filePath), // file path
                new TypeReference<List<Transaction>>() {} // using the List<Transaction> type
            );
            displayDataShowcase(transactions);

        } catch (IOException e) {
            System.err.println("Error reading the JSON file: " + e.getMessage());
        }
    }

// Method to rewrite
public static void displayDataShowcase(List<Transaction> list) {
        System.out.println("====================================================");
        System.out.println("TRANSACTION DATA LOAD SUCCESSFUL");
        System.out.println("Total Records Found: " + list.size());
        System.out.println("====================================================");
        
        // Print headers
        System.out.printf("%-12s | %-12s | %-8s | %-8s | %-10s%n", 
                          "ID", "REF", "AMOUNT", "CURR", "STATUS");
        System.out.println("----------------------------------------------------");

        // Display each record
        for (Transaction tx : list) {
            System.out.printf("%-12s | %-12s | %-8.2f | %-8s | %-10s%n",
                tx.getTransactionId(),
                tx.getMerchantRef(),
                tx.getAmount() != null ? tx.getAmount() : 0.0,
                tx.getCurrency(),
                tx.getStatus());
        }
        System.out.println("====================================================");
    }
}
