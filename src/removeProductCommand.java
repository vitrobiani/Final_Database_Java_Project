import javax.xml.crypto.Data;

public class removeProductCommand extends MenuActionCompleteListener implements Command{
    public static DataBase db = DataBase.getInstance();
    Services srv = IOServices.getInstance();
    @Override
    public boolean execute() {
        if (removeProduct()){
            update("Product Removed Successfully");
        }else {
            update("Product not found");
        }
        return true;
    }

// TODO remove objects will cause problem
    public boolean removeProduct(){
        Product p = srv.getProduct();
        if (p == null) return false;
        db.getProducts().remove(p);
        return true;
    }
}
