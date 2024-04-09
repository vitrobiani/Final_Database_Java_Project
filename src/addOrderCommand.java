import java.util.Scanner;

public class addOrderCommand extends MenuActionCompleteListener implements Command{
    public static Scanner s = new Scanner(System.in);
    public Services srv = IOServices.getInstance();
    @Override
    public boolean execute() {
        Product product = srv.getProduct();
        if (product == null || product.getStock() == 0){
            update("Product not found or none left in stock");
            return false;
        }
        Customer customer = initCustomer();
        int quantity = srv.getInput((Integer i) -> i < 0 || i > product.getStock(), "please enter the quantity");
        product.addOrder(new Order(customer, quantity, product));
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
