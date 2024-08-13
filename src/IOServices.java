import org.postgresql.util.PSQLException;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Predicate;

public class IOServices implements Services{
    public static IOServices[] _instance1 = new IOServices[1];
    public static Scanner s = new Scanner(System.in);
    public DataBase db;
    private IOServices(){
        db = DataBase.getInstance();
    }
    public static IOServices getInstance() {
        if (_instance1[0] == null) {
            _instance1[0] = new IOServices();
        }
        return _instance1[0];
    }
    public boolean isInteger(String str){
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public <T> T getInput(Predicate<T> condition, String mesg) {
        T choice = null;

        try {
            do {
                System.out.println(mesg);
                String input = s.next();
                try {
                    if (condition != null && isInteger(input)) {
                        choice = (T) (Integer) Integer.parseInt(input);
                    } else if (condition != null) {
                        if (input.length() == 1) {
                            choice = (T) (Character) input.charAt(0);
                        }
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please try again");
                    s.next(); // consume the invalid input
                } catch (Exception e) {
                    System.out.println("Please try again");
                }

                if (choice != null && condition.test(choice)) {
                    System.out.println("Invalid input. Please try again");
                }
            } while (choice == null || condition.test(choice));
        }catch (Exception e){
            System.out.println("an error occured, try again");
            return getInput(condition, mesg);
        }

        s.nextLine();
        return choice;
    }


    public String getDestCountry(){
        System.out.println("please enter the destination country");
        System.out.println(db.getCountries().toString());

//        System.out.println("would you like to add a new country? (y/n)");
//        char choice = getInput((Character c) -> c != 'y' && c != 'n', "");
//        if (choice == 'y') {
//            String country = addCountryToList();
//            return country;
//        }else {
            String country = s.nextLine();
            if (db.getCountries().get(country) != null){
                return country;
            }else {
                System.out.println("country not found");
                return getDestCountry();
            }
//        }
    }
    public String addCountryToList(){
        System.out.println("please enter the country name");
        String country = s.nextLine();
        System.out.println("please enter the import tax");
        int tax = getInput((Integer i) -> i < 0, "please enter a positive number");
        db.addImportTax(country, tax);
        return country;
    }

    public void printProductTypes(){
        for (int i = 0; i < ProductType.values().length; i++){
            System.out.println(i+1 + ". " + ProductType.values()[i]);
        }
    }

    public Product getProduct() {
        System.out.println(db.AllProductsInStoreToString());
        System.out.println("please enter the product code");
        String code = s.nextLine();
        for (Product p: db.getProducts()){
            if (p.getCode().equals(code)){
                return p;
            }
        }
        return null;
    }

    public String getProductCode(){
        ResultSet rs = null;
        String code = null;
        try {
            rs =db.QueryDB("SELECT * FROM " + TN.PRODUCT.tname());

            System.out.println("The Products: ");
            while (rs.next()){
                System.out.println("Code: " + rs.getString(TN.PRODUCT_CODE.tname()) + "  Name: " + rs.getString(TN.PRODUCT_NAME.tname()) + "  Stock: " + rs.getString(TN.PRODUCT_STOCK.tname()) + "  Type: " + rs.getString(TN.PRODUCT_TYPE.tname()));
            }
            System.out.println("Please enter the Product code: ");
            do {
                code = s.nextLine();
            }while (db.getProduct(code) == null);

        } catch (PSQLException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return code;
    }
}
