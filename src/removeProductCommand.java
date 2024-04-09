public class removeProductCommand extends MenuActionCompleteListener implements Command{
    Services srv = IOServices.getInstance();
    @Override
    public boolean execute() {
        if (srv.removeProduct()){
            update("Product Removed Successfully");
        }else {
            update("Product not found");
        }
        return true;
    }
}
