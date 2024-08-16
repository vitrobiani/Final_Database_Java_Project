import java.util.InputMismatchException;
import java.util.Scanner;

public class addProductCommand extends MenuActionCompleteListener implements Command{
    private DataBase db = DataBase.getInstance();
    private Scanner s = new Scanner(System.in);
    private Services srv = IOServices.getInstance();
    @Override
    public boolean execute() {
        ProductType TheProductType = null;
        String TheShippingType = null;
        ShippingType shippingType = null;
        String TheSrcCountry = null;
        Country srcCountry = null;
        PairSet set = new PairSet();
        try {
            TheProductType = getProductType();
            set.addPair("ProductType", TheProductType);
            set.addPair("code", getProductCode());
            set.addPair("name", getProductName());
            set.addPair("buyPrice", getBuyPrice());
            set.addPair("sellPrice", getSellPrice());
            set.addPair("weight", getWeight());

            if (set.get("ProductType") == ProductType.SOLD_THROUGH_WEBSITE) {
                shippingType = getShippingType();
                set.addPair("ShippingType", shippingType);
                srcCountry = srv.getCountry();
                set.addPair("srcCountry", srcCountry);
            }
        }catch (Exception e){
            update("An error occurred in adding product");
            return false;
        }
        if (shippingType != null){
            if (shippingType == ShippingType.STANDARD) {
                TheShippingType = "'standard'";
            } else if(shippingType == ShippingType.EXPRESS){
                TheShippingType = "'express'";
            } else {
                TheShippingType = "'both'";
            }
            TheSrcCountry = "'" + srcCountry.getCountryCode() + "'";
        }
        set.addPair("stock", 0);

        Creator c = new ProductCreator();
        Product p = (Product) c.create(set);
        if (db.addProduct( "'" + p.code +"'", "'" + p.name + "'", p.buyPrice, p.sellPrice, p.weight, 0 ,"'" + TheProductType.toString() + "'", TheSrcCountry, TheShippingType)){
            update("Product added successfully!");
            return true;
        }
        update("An error occurred in adding product");
        return false;
    }

    private ProductType getProductType(){
        System.out.println("what kind of Product would you like to add:\n");
        srv.printProductTypes();
        int type = srv.getInput((Integer i ) -> i < 0 || i > ProductType.values().length, "")-1;
        return ProductType.values()[type];
    }

    private String getProductCode(){
        System.out.println("Enter the Product code:  ");
        String code;
        do {
            code = s.nextLine();
        }while (!db.checkProductCode(code));
        return code;
    }

    private String getProductName(){
        System.out.println("Enter the Product name:  ");
        return s.nextLine();
    }

    private double getBuyPrice(){
        System.out.println("Enter the Product buy price:  ");
        double buyPrice = -1;
        do {
            try {
                buyPrice = s.nextDouble();
            }catch (InputMismatchException e){
                System.out.println("please enter an floating point number");
                s.next();
            }catch (Exception e){
                System.out.println("Error occurred");
                s.next();
            }
            if (buyPrice < 0)
                System.out.println("buy price must be positive!");
        }while(buyPrice < 0);
        return buyPrice;
    }

    private double getSellPrice(){
        System.out.println("Enter the Product sell price:  ");
        double sellPrice = -1;
        do {
            try {
                sellPrice = s.nextDouble();
            }catch (InputMismatchException e){
                System.out.println("please enter an floating point number");
                s.next();
            }catch (Exception e){
                System.out.println("Error occurred");
                s.next();
            }
            if (sellPrice < 0)
                System.out.println("buy price must be positive!");
        }while(sellPrice < 0);
        return sellPrice;
    }

    private int getWeight(){
        return srv.getInput((Integer i) -> i < 0, "Enter the Product weight:  ");
    }

    public ShippingType getShippingType(){
        for (int i = 0; i < ShippingType.values().length; i++) {
            System.out.println((i + 1) + ". " + ShippingType.values()[i]);
        }

        int choice = srv.getInput((Integer i) -> i > 3 || i < 1, "please enter a number between 1 and 3");
        return ShippingType.values()[choice - 1];
    }
}
