public class MenuCommandsController {
    public PairSet set = new PairSet();
    public static Creator creator = new CommandCreator();

    public MenuCommandsController(int choice) {
        switch (choice) {
            case 1: {
                set.addPair("command", menuOptions.ADD_PRODUCT);
                break;
            }
            case 2: {
                set.addPair("command", menuOptions.REMOVE_PRODUCT);
                break;
            }
            case 3: {
                set.addPair("command", menuOptions.UPDATE_PRODUCT);
                break;
            }
            case 4: {
                set.addPair("command", menuOptions.ADD_ORDER);
                break;
            }
            case 5: {
                set.addPair("command", menuOptions.UNDO_LAST_ORDER);
                break;
            }
            case 6: {
                set.addPair("command", menuOptions.PRINT_PRODUCT_DETAILS);
                break;
            }
            case 7: {
                set.addPair("command", menuOptions.PRINT_ALL_PRODUCTS);
                break;
            }
            case 8: {
                set.addPair("command", menuOptions.PRINT_ALL_ORDERS);
                break;
            }
            case 9: {
                set.addPair("command", menuOptions.BACKUP_SYSTEM);
                break;
            }
            case 10: {
                set.addPair("command", menuOptions.RESTORE_SYSTEM);
                break;
            }
            case 11: {
                set.addPair("command", menuOptions.AUTO_ADD_PRODUCT);
                break;
            }
            default: {
                set.addPair("command", null);
            }
        }
    }

    public Command getCommand() {
        return (Command) creator.create(set);
    }
}