import java.util.Scanner;

public class undoLastOrderCommand extends MenuActionCompleteListener implements Command{
    DataBase db = DataBase.getInstance();
    Scanner s = new Scanner(System.in);
    @Override
    public boolean execute() {
        db.printOrders();
        System.out.println("Please enter order's id:");
        int id = s.nextInt();
        if (db.removeOrder(id)) {
            update("Order removed successfully!");
            return true;
        }
        System.out.println("Failed to remove order");
        return false;
    }
}
