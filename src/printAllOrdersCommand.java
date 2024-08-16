import java.util.Set;

public class printAllOrdersCommand extends MenuActionCompleteListener implements Command{
    public static Services srv = IOServices.getInstance();
   DataBase db = DataBase.getInstance();
    @Override
    public boolean execute() {
        String code = srv.getProductCode();
        if (code == null){
            update("No Products");
            return false;
        }
        Product product = db.getProduct(code);
        if (product == null){
            update("Product Not Found");
            return false;
        }
        Set<Order> orders = db.getAllProductsOrders(product.getCode());
        StringBuilder sb = new StringBuilder();
        sb.append("Product Orders: \n");
        for (Order o: orders){
            sb.append(o.orderDetails()).append("\n");
        }
        System.out.println(sb.toString());
        return true;
    }
}
