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
        db.addProduct("'HHH'", "'product10'", 100.0, 550.0, 10, 20, "'Store'", null, null);
        db.addProduct("'III'", "'product11'", 110.0, 650.0, 11, 20, "'Store'", null, null);
        db.addProduct("'JJJ'", "'product12'", 120.0, 750.0, 12, 20, "'Website'", "'USA'", "'standard'");
        db.addProduct("'ABC'", "'product13'", 130.0, 890.0, 13, 20, "'Store'", null, null);
        db.addProduct("'ABFF'", "'product14'", 1333.0, 8890.0, 52, 20, "'Website'", "'Israel'", "'both'");
        db.addProduct("'FFAB'", "'product15'", 198.90, 890.0, 1, 20, "'Website'","'France'","'both'");
        db.addProduct("'LSAG'", "'product16'", 18.0, 89.0, 3, 20, "'Wholesalers'","'Germany'","'standard'");
        db.addProduct("'KJH'", "'product17'", 1.0, 8.0, 1, 20, "'Wholesalers'", "'Thailand'","'express'" );
        db.addCustomer("'John'","'123456789'");
        db.addCustomer("'Doe'","'987654321'");
        db.addCustomer("'Jane'","'123123123'");
        db.addOrder(1,5,"'HHH'");

        return true;
    }
}
