public enum menuOptions {
    AUTO_ADD_PRODUCT("add Products automatically"),
    ADD_PRODUCT("Add a new product"),
    REMOVE_PRODUCT("remove a product"),
    UPDATE_PRODUCT("update product inventory"),
    ADD_ORDER("add an order"),
    UNDO_LAST_ORDER("Undo last order"),
    PRINT_PRODUCT_DETAILS("print Product details"),
    PRINT_ALL_PRODUCTS("print all products and store profit"),
    PRINT_ALL_ORDERS("print all orders of a product and the profit"),
    BACKUP_SYSTEM("backup the system to file"),
    RESTORE_SYSTEM("restore the system from file");

    private String option;
    menuOptions(String option){
        this.option = option;
    }

    @Override
    public String toString() {
        return option;
    }
}
