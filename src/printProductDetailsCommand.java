import java.sql.SQLException;

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
//        try {
//            db.QueryDB("SELECT * FROM products WHERE code = '" + code + "';");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        Product product = srv.getProduct();
//        if (product == null){
//            update("Product not found");
//            return false;
//        }
//        System.out.println(product.toString());
//        System.out.println(product.toStringAllOrders());
//        System.out.println("Total Product Profit: " + product.calculateTotalProductProfit());
//
//        if (!product.getClass().equals(ProductSoldThroughWebsite.class))
//            showInvoices((ProductSoldInCountry) product);
        return true;
    }

    private void showInvoices(ProductSoldInCountry p){
        if (p.getInvoices().isEmpty()) {
            System.out.println("No invoices!");
            return;
        }
        char choice = 'n';
        do {
            printAllInvoicesReduced(p);
            int inv = srv.getInput((Integer i) -> i < 1 || i > p.getInvoices().size(), "Please Choose an Invoice to Show: ")-1;
            char CustOrAcc = srv.getInput((Character c) -> c != 'c' && c != 'a', "Show Invoice for Accountant or Customer: <a/c>");
            if (CustOrAcc == 'c')
                System.out.println(p.invoiceToStringForCustomer(inv));
            else
                System.out.println(p.invoiceToStringForAccountant(inv));

            choice = srv.getInput((Character c) -> c != 'y' && c != 'n', "Show another Invoice: <y/n>");
        }while(choice != 'n');
    }

    private void printAllInvoicesReduced(ProductSoldInCountry p){
        for (int i = 0; i < p.getInvoices().size(); i++) {
            System.out.println((i+1) + ". " + p.getInvoices().get(i).invoiceReducedToString());
        }
    }
}
