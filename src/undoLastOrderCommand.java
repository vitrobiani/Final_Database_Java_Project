public class undoLastOrderCommand extends MenuActionCompleteListener implements Command{
    DataBase db = DataBase.getInstance();
    @Override
    public boolean execute() {
        if (db.getStack().isEmpty()){
            update("No Order To Undo!");
            return false;
        }
        Order order = db.getStack().peek().order;
        Product p = db.getStack().peek().product;
        System.out.println("Dear customer, your order for: " + order.getQuantity() + " " + p.getName() +
                "\nwas canceled due to warehouse problems." +
                "\nWe're terribly sorry for the inconvenience");
        p.setMemento(db.getStack().pop());
        update("Undo Completed!");
        return true;
    }
}
