import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

public class printProductDetailsCommand extends MenuActionCompleteListener implements Command {
    Services srv = IOServices.getInstance();
    DataBase db = DataBase.getInstance();
    @Override
    public boolean execute() {
        String code = srv.getProductCode();
        if(code == null){
            update("Product not found");
            return false;
        }
        System.out.println();
        Product p = db.getProduct(code);
        if (p == null){
            update("Product not found");
            return false;
        }
        System.out.println("Product Details:\n"+"code: "+p.getCode()+" "+"name: "+p.getName()+"\n"+"buyPrice: "+p.getBuyPrice()+" "+"sellPrice: "+p.getSellPrice()+"\n"+"weight: "+p.getWeight()+" "+"stock: "+p.getStock()+"\n Product Type:"+p.getClass().getSimpleName());
        System.out.println("Total Product Profit: " + p.calculateTotalProductProfit());
        showInvoices(p);
        return true;
    }

    private void showInvoices(Product p){
        ArrayList<Invoice> lst = db.getAllProductsInvoices(p.code);
        if (lst.isEmpty()) {
            System.out.println("No invoices for this product!");
            return;
        }
        char choice = 'n';
        do {
            printAllInvoicesReduced(lst);
            int inv = srv.getInput((Integer i) -> i < 0 || i > lst.size(), "Please Choose an Invoice to Show: <0 to exit>")-1;
            if (inv == -1) return;
            char CustOrAcc = srv.getInput((Character c) -> c != 'c' && c != 'a', "Show Invoice for Accountant or Customer: <a/c>");
            if (CustOrAcc == 'c')
                System.out.println(lst.get(inv).invoiceFormatForCustomer());
            else
                System.out.println(lst.get(inv).invoiceFormatForAccountant());

            choice = srv.getInput((Character c) -> c != 'y' && c != 'n', "Show another Invoice: <y/n>");
        }while(choice != 'n');
    }

    private void printAllInvoicesReduced(ArrayList<Invoice> lst){
        for (int i = 0; i < lst.size(); i++) {
            System.out.println((i+1) + ". " + lst.get(i).invoiceReducedToString());
        }
    }
}
