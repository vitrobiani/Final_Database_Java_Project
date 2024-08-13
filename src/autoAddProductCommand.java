import java.sql.SQLException;

public class autoAddProductCommand extends MenuActionCompleteListener implements Command{
    public DataBase db = DataBase.getInstance();
    private static final int amount = 3;
    private int ind = 0;
    private final String[] codes = {"HHH", "III", "JJJ", "ABC", "ABFF", "PPPP", "LLL"};
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
        try {
            db.initDB();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        db.addProduct("'HHH'", "'product10'", 100.0, 550.0, 10, 20, "'Store'", null, null);
        db.addProduct("'III'", "'product11'", 110.0, 650.0, 11, 20, "'Store'", null, null);
        db.addProduct("'JJJ'", "'product12'", 120.0, 750.0, 12, 20, "'Website'", "'USA'", "'standard'");
        db.addProduct("'ABC'", "'product13'", 130.0, 890.0, 13, 20, "'Store'", null, null);
        db.addProduct("'ABFF'", "'product14'", 1333.0, 8890.0, 52, 20, "'Website'", "'Israel'", "'both'");
        db.addProduct("'FFAB'", "'product15'", 198.90, 890.0, 1, 20, "'Website'","'France'","'express'");
        db.addProduct("'PPPP'", "'product19'", 69.90, 420, 8, 20, "'Website'","'Omerland'","'both'");
        db.addProduct("'LSAG'", "'product16'", 18.0, 89.0, 3, 20, "'Wholesalers'",null,null);
        db.addProduct("'KJH'", "'product17'", 1.0, 8.0, 1, 20, "'Wholesalers'", null,null );
        db.addProduct("'LLL'", "'product18'", 2.5, 11.0, 1, 20, "'Wholesalers'", null,null );

        db.addCustomer("'John'","'123456789'");
        db.addCustomer("'Doe'","'987654321'");
        db.addCustomer("'Jane'","'123123123'");

        db.addShippingCompany("'UPS'", new Contact("'dolev'", "555666"), 1.5, 2.3);
        db.addShippingCompany("'FedEx'", new Contact("'omer'", "555666"), 1.1, 2.4);
        db.addShippingCompany("'DHL'", new Contact("'daniel'", "555666"), 1.1, 2.4);

        Creator<Order> orderCreator = new OrderCreator();

        for (int i = 0; i < codes.length; i++) {
            PairSet set = new PairSet();
            Product p = db.getProduct(codes[i]);
            set.addPair("Product", p);
            set.addPair("ProductClass", p.getClass().getSimpleName());
            set.addPair("Quantity", i+1);
            Customer c = db.getCustomer("123123123");
            set.addPair("Customer", c);
            if (i == 2 || i == 4)
                set.addPair("ShippingType", ShippingType.STANDARD);
            else if (i == 5)
                set.addPair("ShippingType", ShippingType.EXPRESS);
            db.addOrder(orderCreator.create(set));
            db.addInvoice(i+1);
        }

        update("Added!");
        return true;
    }
}
