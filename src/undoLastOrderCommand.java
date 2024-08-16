import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

public class undoLastOrderCommand extends MenuActionCompleteListener implements Command{
    DataBase db = DataBase.getInstance();
    Scanner s = new Scanner(System.in);
    @Override
    public boolean execute() {
        if (db.getAllOrdersProducts().isEmpty()){
            update("No orders");
            return false;
        }
        db.printOrders();
        System.out.println("Please enter order's id:");
        int id = -1;
        do {
            try {
                id = s.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("enter a number");
                s.next();
            } catch (Exception e){
                System.out.println("Error occurred");
                s.next();
            }
        } while(id < 0);
        if (db.removeOrder(id)) {
            update("Order removed successfully!");
            return true;
        }
        System.out.println("Failed to remove order or no order to remove");
        return false;
    }
}
