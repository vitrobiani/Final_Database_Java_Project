import org.postgresql.util.PSQLException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class updateProductCommand extends MenuActionCompleteListener implements Command{
    DataBase db = DataBase.getInstance();
    Scanner s = new Scanner(System.in);
    Services srv = IOServices.getInstance();
    @Override
    public boolean execute() {
        if (db.updateProductStock(srv.getProductCode(), getNewStock())){
            update("Stock updated");
            return true;
        }
        update("Stock was not updated");
        return false;
    }

    private int getNewStock(){
        System.out.println("Please enter the new stock: ");
        int stock = -1;
        while (stock < 0) {
            try {
                stock = s.nextInt();
            } catch (Exception e) {
                System.out.println("Try Again");
            }
        }
        return stock;
    }
}
