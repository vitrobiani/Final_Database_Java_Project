import java.util.Scanner;

public class addProductCommand extends MenuActionCompleteListener implements Command{
    private DataBase db = DataBase.getInstance();
    private Scanner s = new Scanner(System.in);
    private Services srv = IOServices.getInstance();
    @Override
    public boolean execute() {
        PairSet set = new PairSet();
        try {

            set.addPair("ProductType", getProductType());
            set.addPair("code", getProductCode());
            set.addPair("name", getProductName());
            set.addPair("buyPrice", getBuyPrice());
            set.addPair("sellPrice", getSellPrice());
            set.addPair("weight", getWeight());

            if (set.get("ProductType") == ProductType.SOLD_IN_STORE) {
                ShippingType shippingType = srv.getShippingType();
                set.addPair("ShippingType", shippingType);
                String destCountry = srv.getDestCountry();
                set.addPair("destCountry", destCountry);
            }
        }catch (Exception e){
            update("An error occurred in adding product");
            return false;
        }
        db.addProductToDB(set);
        update("Product added successfully!");
        return true;
    }
    public ProductType getProductType(){
        System.out.println("what kind of Product would you like to add:\n");
        srv.printProductTypes();
        int type = srv.getInput((Integer i ) -> i < 0 || i > ProductType.values().length, "");
        return ProductType.values()[type];
    }

    private String getProductCode(){
        System.out.println("Enter the Product code:  ");
        String code;
        do {
            code = s.nextLine();
        }while (db.checkProductCode(code));
        return code;
    }

    private String getProductName(){
        System.out.println("Enter the Product name:  ");
        return s.nextLine();
    }

    private double getBuyPrice(){
        System.out.println("Enter the Product buy price:  ");
        double buyPrice = 0;
        do {
            buyPrice = s.nextDouble();
            if (buyPrice < 0)
                System.out.println("buy price must be positive!");
        }while(buyPrice < 0);
        return buyPrice;
    }

    private double getSellPrice(){
        System.out.println("Enter the Product sell price:  ");
        double sellPrice = 0;
        do {
            sellPrice = s.nextDouble();
            if (sellPrice < 0)
                System.out.println("buy price must be positive!");
        }while(sellPrice < 0);
        return sellPrice;
    }

    private int getWeight(){
        System.out.println("Enter the Product weight:  ");
        int weight = 0;
        do {
            weight = s.nextInt();
            if (weight < 0)
                System.out.println("buy price must be positive!");
        }while(weight < 0);
        return weight;
    }
}
