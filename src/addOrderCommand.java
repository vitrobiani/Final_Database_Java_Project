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

        Product product = srv.getProduct();
        if (product == null || product.getStock() == 0){
            update("Product not found or none left in stock");
            return false;
        }
        set.addPair("Product", product);
        set.addPair("ProductClass", product.getClass().getSimpleName());

        Customer customer = initCustomer();
        set.addPair("Customer", customer);

        int quantity = srv.getInput((Integer i) -> i < 0 || i > product.getStock(), "please enter the quantity");
        set.addPair("Quantity", quantity);

        if (product.getClass().equals(ProductSoldThroughWebsite.class)){
            set.addPair("ShippingType", chooseShippingType((ProductSoldThroughWebsite) product));
        }

        creator.create(set);

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
        System.out.println("please enter the customer name");
        String name = s.nextLine();
        System.out.println("please enter the customer phone number");
        String phone = s.nextLine();
        return new Customer(name, phone);
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
