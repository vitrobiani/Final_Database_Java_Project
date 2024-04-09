public class printAllProductsCommand extends MenuActionCompleteListener implements Command{
    DataBase db = DataBase.getInstance();
    @Override
    public boolean execute() {
        System.out.println("\nThe Products in the store: ");
        int i = 1;
        for (Product p : db.getProducts()){
            System.out.println(i + ". " + p+"\n");
            i++;
        }
        update("Finished Printing");
        return true;
    }
}
