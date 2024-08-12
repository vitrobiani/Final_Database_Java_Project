public class undoLastOrderCommand extends MenuActionCompleteListener implements Command{
    DataBase db = DataBase.getInstance();
    @Override
    public boolean execute() {
        return true;
    }
}
