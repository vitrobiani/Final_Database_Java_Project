public class printAllProductsCommand extends MenuActionCompleteListener implements Command{
    DataBase db = DataBase.getInstance();
    @Override
    public boolean execute() {
        System.out.println("\nThe Products in the store: ");
        double sum = 0;
        int i = 1;
        for (Product p : db.getProducts()){
            System.out.println(i + ". " + p);
            i++;
            System.out.print("Profit From Product: " + p.calculateTotalProductProfit() + "\n\n");
            sum += p.calculateTotalProductProfit();
        }
        System.out.println("Total Profit From Products: " + sum );
        update("Finished Printing");
        return true;
    }
}
