public class CommandCreator implements Creator<Command>{

    @Override
    public Command create(PairSet set) {
        menuOptions command = (menuOptions) set.get("command");
        if (command == null) return null;
        switch (command){
            case AUTO_ADD_PRODUCT: {
                return new autoAddProductCommand();
            }
            case ADD_PRODUCT: {
                return new addProductCommand();
            }
            case REMOVE_PRODUCT: {
                return new removeProductCommand();
            }
            case UPDATE_PRODUCT: {
                return new updateProductCommand();
            }
            case ADD_ORDER: {
                return new addOrderCommand();
            }
            case UNDO_LAST_ORDER: {
                return new undoLastOrderCommand();
            }
            case ADD_SHIPPING_COMPANY: {
                return new addShippingCompany();
            }
            case REMOVE_SHIPPING_COMPANY:{
                return new removeShippingCompany();
            }
            case PRINT_PRODUCT_DETAILS: {
                return new printProductDetailsCommand();
            }
            case PRINT_ALL_PRODUCTS: {
                return new printAllProductsCommand();
            }
            case PRINT_ALL_ORDERS: {
                return new printAllOrdersCommand();
            }
            case DROP_ALL_TABLES:{
                return new dropAllTablesCommand();
            }
            default:{
                System.out.println("Exiting System...");
            }
        }
        return null;
    }
}
