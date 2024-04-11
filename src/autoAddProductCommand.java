public class autoAddProductCommand extends MenuActionCompleteListener implements Command{
    public DataBase db = DataBase.getInstance();
    private static final int amount = 3;
    private int ind = 0;
    private final String[] codes = {"OOO", "OJK", "ITD", "TFK", "NFR", "ECP", "DEC", "NJK", "BDK"};
    private final String[] names = {"product1", "product2", "product3", "product4", "product5", "product6", "product7", "product8", "product9"};
    private final double[] sellPrices = {10.0, 20.0, 30.0, 40.0, 50.0, 60.0, 70.0, 80.0, 90.0};
    private final double[] buyPrices = {5.0, 10.0, 15.0, 20.0, 25.0, 30.0, 35.0, 40.0, 45.0};
    private final int[] weights = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    private final String[] CustomerNames = {"John", "Doe", "Jane"};
    private final String[] CustomerPhones = {"123456789", "987654321", "123123123"};
    private static final Creator<Order> creator = new OrderCreator();
    private static final Creator<Product> c = new ProductCreator();

    @Override
    public boolean execute() {
        int size = db.getProducts().size();
        for (int i = 0; i < ProductType.values().length; i++) {
            addAmountProduct(ProductType.values()[i]);
        }
        if (db.getProducts().size() == size + 9){
            update("Products added successfully");
            return true;
        }
        update("An error occurred in adding products");
        return false;
    }
    private void addAmountProduct(ProductType type){
        for (int i = 0; i < amount; i++) {
            PairSet set = new PairSet();
            set.addPair("ProductType", type);
            set.addPair("code", codes[ind]);
            set.addPair("name", names[ind]);
            set.addPair("sellPrice", sellPrices[ind]);
            set.addPair("buyPrice", buyPrices[ind]);
            set.addPair("weight", weights[ind++]);

            if (type == ProductType.SOLD_THROUGH_WEBSITE){
                set.addPair("ShippingType", ShippingType.values()[i]);
                set.addPair("destCountry", db.getCountries().getRandomKey());
            }
            Product p = c.create(set);
            db.addProductToDB(p);
            p.updateStock(20);
            createOrder(p);
        }
    }

    private void createOrder(Product p){
        for (int i = 0; i < amount; i++) {
            PairSet set = new PairSet();
            set.addPair("Product", p);
            set.addPair("ProductClass", p.getClass().getSimpleName());
            Customer c = new Customer(CustomerNames[i], CustomerPhones[i]);
            set.addPair("Customer", c);
            set.addPair("Quantity", 3);
            if (p.getClass().equals(ProductSoldThroughWebsite.class))
                set.addPair("ShippingType", ShippingType.values()[0]);
            creator.create(set);
        }
    }
}
