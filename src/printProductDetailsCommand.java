public class printProductDetailsCommand extends MenuActionCompleteListener implements Command {
    Services srv = IOServices.getInstance();
    @Override
    public boolean execute() {
        Product product = srv.getProduct();
        if (product == null){
            update("Product not found");
            return false;
        }
        System.out.println(product.toString());
        System.out.println(product.toStringAllOrders());
        return true;
    }
}
