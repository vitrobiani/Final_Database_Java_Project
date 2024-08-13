import javax.xml.crypto.Data;
import java.util.Scanner;

public class addOrderCommand extends MenuActionCompleteListener implements Command{
    public static Scanner s = new Scanner(System.in);
    public Services srv = IOServices.getInstance();
    public DataBase db = DataBase.getInstance();
    @Override
    public boolean execute() {
        PairSet set = new PairSet();
        Creator<Order> creator = new OrderCreator();

        String pCode = srv.getProductCode();
        Product product = db.getProduct(pCode);
        if (product == null || product.getStock() == 0){
            update("Product not found or none left in stock");
            return false;
        }
        set.addPair("Product", product);
        set.addPair("ProductClass", product.getClass().getSimpleName());

        int quantity = srv.getInput((Integer i) -> i < 0 || i > product.getStock(), "please enter the quantity");
        set.addPair("Quantity", quantity);

        Customer customer = initCustomer();
        set.addPair("Customer", customer);

        if (product.getClass().equals(ProductSoldThroughWebsite.class)){
            set.addPair("ShippingType", chooseShippingType((ProductSoldThroughWebsite) product));
        }

        creator.create(set);
        db.addOrder(customer, quantity, product);

        if (product.getClass().equals(ProductSoldInStore.class)){
            char inv = srv.getInput((Character c) -> c != 'y' && c != 'n', "Would you like to print the Invoice: <y/n>");
            if(inv == 'y'){
                int len = ((ProductSoldInStore) product).getInvoices().size();
                String s = ((ProductSoldInStore) product).invoiceToStringForCustomer(len-1);
                System.out.println(s);
            }
        }

        return true;
    }

    private Customer initCustomer(){
        System.out.println("Enter the customer's phone number: ");
        String number = s.nextLine();
        while(number.length() > 10){
            System.out.println("too long! try again");
            number = s.nextLine();
        }

        char ch = 'n';
        Customer c1 = db.getCustomer(number);
        if (c1 != null){
            System.out.println("There is already a customer with this phone number in the database");
            System.out.println("would you like to continue with the existing customer: <y/n>");
            ch = srv.getInput((Character c) -> c != 'y' && c != 'n',"");
            if (ch == 'n') {
                System.out.println("then please enter a different phone number");
                return initCustomer();
            }
        }
        String name = "";
        if (ch == 'n') {
            System.out.println("What is the customer name: ");
            name = s.nextLine();
            while (name.length() > 50) {
                System.out.println("too long! try again");
                name = s.nextLine();
            }
            db.addCustomer("'"+name+"'",number);
        }

        return (ch == 'y') ? c1 : new Customer(name, number);
    }

    private ShippingType chooseShippingType(ProductSoldThroughWebsite p){
        if (!p.getType().equals(ShippingType.ALL_SHIPPING)){
            return p.getType();
        }
        ShippingType[] types = ShippingType.values();
        for (int i = 0; i < types.length; i++) {
            if (types[i].forOrder){
                System.out.println((i+1) + ". " + types[i]);
            }
        }
        int choice = srv.getInput((Integer i) -> !(ShippingType.values()[i-1].forOrder), "Please Enter Preferred Shipping: ");
        return types[choice-1];
    }
}
