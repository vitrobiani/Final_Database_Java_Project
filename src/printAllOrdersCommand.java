public class printAllOrdersCommand extends MenuActionCompleteListener implements Command{
    public static Services srv = IOServices.getInstance();
   DataBase db = DataBase.getInstance();
    @Override
    public boolean execute() {
        Product product = srv.getProduct();
        if (product == null){
            update("Product Not Found");
            return false;
        }
        System.out.println(product.toStringAllOrders() + "\n");
        return true;
    }
}
