import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public class printAllProductsCommand extends MenuActionCompleteListener implements Command{
    DataBase db = DataBase.getInstance();
    @Override
    public boolean execute() {
        System.out.println("\nThe Products in the store: ");
        double sum = 0;
        int i = 1;
        Set<Product> pSet = db.getAllProducts();

        for (Product p : pSet){
            System.out.println(i + ". " + p);
            i++;
            double k = p.calculateTotalProductProfit();
            System.out.print("Profit From Product: " + k + "\n\n");
            sum += k;
        }
        System.out.println("Total Profit From Products: " + sum );
        update("Finished Printing");
        return true;
    }
}
