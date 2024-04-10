public class printAllOrdersCommand extends MenuActionCompleteListener implements Command{
   DataBase db = DataBase.getInstance();
    @Override
    public boolean execute() {
        db.printAllProductsInStore();
        System.out.println("Please enter the product code: ");
        String code = Main.s.nextLine();
        db.printAllOrdersOfProduct(code);
        return true;
    }
}
