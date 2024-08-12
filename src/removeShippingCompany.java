import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class removeShippingCompany extends MenuActionCompleteListener implements Command{
    private DataBase db = DataBase.getInstance();
    private Scanner s = new Scanner(System.in);
    @Override
    public boolean execute() {
        printAllShippingCompanies();
        if (db.removeShippingCompany(getName())){
            update("Removed Successfully!");
            return true;
        }
        update("Nothing Removed");
        return false;
    }

    private String getName(){
        System.out.println("what is the name of the Shipping Company: ");
        String name = s.nextLine();
        if (name.isEmpty() || name.length() > 50){
            System.out.println("Please enter a valid name");
            return getName();
        }
        return name;
    }

    private void printAllShippingCompanies(){
        ResultSet rs = null;
        System.out.println("The Shipping Companies: ");
        try {
            rs = db.QueryDB("SELECT * FROM ShippingCompanies");
            while (rs.next()) {
                System.out.println((rs != null) ? rs.getString("name") : "error");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
