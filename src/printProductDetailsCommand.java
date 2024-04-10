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
        if (!product.getClass().equals(ProductSoldThroughWebsite.class))
            showInvoices((ProductSoldInCountry) product);
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
            int inv = srv.getInput((Integer i) -> i < 0 || i > p.getInvoices().size(), "Please Choose an Invoice to Show: ");
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
            System.out.println(i + ". " + p.getInvoices().get(i).invoiceReducedToString());
        }
    }
}
