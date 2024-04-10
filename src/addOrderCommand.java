import javax.xml.crypto.Data;
import java.util.Scanner;

public class addOrderCommand extends MenuActionCompleteListener implements Command{
    public static Scanner s = new Scanner(System.in);
    public Services srv = IOServices.getInstance();
    public DataBase db = DataBase.getInstance();
    @Override
    public boolean execute() {
        Product product = srv.getProduct();
        if (product == null || product.getStock() == 0){
            update("Product not found or none left in stock");
            return false;
        }
        Customer customer = initCustomer();
        int quantity = srv.getInput((Integer i) -> i < 0 || i > product.getStock(), "please enter the quantity");
        new Order(customer, quantity, product);

        if (product.getClass().equals(ProductSoldInStore.class)){
            char inv = srv.getInput((Character c) -> c != 'y' && c != 'n', "Would you like to print the Invoice: <y/n>");
            if(inv == 'y'){
                int len = ((ProductSoldInCountry) product).getInvoices().size();
                String s = ((ProductSoldInCountry) product).invoiceToStringForCustomer(len);
                System.out.println(s);
            }
        }

        return true;
    }

    private Customer initCustomer(){
        System.out.println("please enter the customer name");
        String name = s.nextLine();
        System.out.println("please enter the customer phone number");
        String phone = s.nextLine();
        return new Customer(name, phone);
    }
}
