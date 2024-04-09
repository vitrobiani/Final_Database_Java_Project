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
                    if (condition instanceof Predicate<?> && isInteger(input)) {
                        choice = (T) (Integer) Integer.parseInt(input);
                    } else if (condition instanceof Predicate<?>) {
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

    public ShippingType getShippingType(){
        System.out.println("please choose a shipping type:" +
                "\n1. Regular" +
                "\n2. Express");
        for (int i = 0; i < ShippingType.values().length; i++) {
            System.out.println((i + 1) + ". " + ShippingType.values()[i]);
        }

        int choice = getInput((Integer i) -> i > 2 || i < 1, "please enter a number between 1 and 3");
        return ShippingType.values()[choice - 1];
    }

    public String getDestCountry(){
        System.out.println("please enter the destination country");
        for (String country : db.importTax.keySet()) {
            System.out.println(country);
        }
        System.out.println("would you like to add a new country? (y/n)");
        char choice = getInput((Character c) -> c != 'y' && c != 'n', "");
        if (choice == 'y') {
            String country = addCountryToList();
            return country;
        }else {
            String country = s.nextLine();
            if (db.importTax.containsKey(country)) {
                return country;
            }else {
                System.out.println("country not found");
                return getDestCountry();
            }
        }
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

    public PairSet getProductDetails(){
        PairSet set = new PairSet();
        return set;
    }

    public boolean removeProduct(){
        System.out.println("please enter the product code");
        String code = s.nextLine();
        for (Product p: db.getProducts()){
            if (p.getCode().equals(code)){
                db.getProducts().remove(p);
                return true;
            }
        }
        return false;
    }

    public void updateProductInventory(){
        System.out.println("please enter the product code");
        String code = s.nextLine();
        for (Product p: db.getProducts()){
            if (p.getCode().equals(code)){
                System.out.println("please enter the current amount: ");
                int amount = getInput((Integer i) -> i < 0, "");
                p.updateStock(amount);
                System.out.println("inventory updated successfully");
                return;
            }
        }
        System.out.println("Product not found");
    }
}
