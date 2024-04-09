import java.util.Scanner;

public class Main {
    public static Scanner s = new Scanner(System.in);
    public static DataBase db = DataBase.getInstance();
    public static IOServices srv = IOServices.getInstance();
    public static void main(String[] args) {
        lobby();
    }

    public static void lobby(){
        int choice = 0;
        do {
            System.out.println("Welcome to the store management system\n");
            for (int i = 0; i < menuOptions.values().length; i++) {
                System.out.println((i + 1) + ". " + menuOptions.values()[i] );
            }
            choice = srv.getInput((Integer i) -> (i > 11 || i < 1) && (i != -1), "");
            MenuCommandsController controller = new MenuCommandsController(choice);
            Command command = controller.getCommand();
            if (command != null)
                command.execute();
        }while(choice != -1);
    }

    public static void addProductsAutomatically(){
    }

    public static void addNewProduct(){
    }

    public static void printAllProductsInStore(){
        System.out.println("\nThe Products in the store: ");
        int i = 1;
        for (Product p : db.getProducts()){
            System.out.println(i + ". " + p+"\n");
            i++;
        }
    }

    public static void removeProduct(){
        if (srv.removeProduct()){
            System.out.println("Product removed successfully");
        }else {
            System.out.println("Product not found");
        }
    }

    public static void updateProductInventory(){
        srv.updateProductInventory();
    }
}