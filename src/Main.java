import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.Scanner;
import java.util.Timer;

public class Main {
    public static Scanner s = new Scanner(System.in);
    public static DataBase db = DataBase.getInstance();
    public static IOServices srv = IOServices.getInstance();
    public static boolean first = true;
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        starter();
        lobby();
    }

    public static void lobby(){
        int choice = 0;
        do {
            pressToContinue();
            first = false;
            System.out.println("\n ~ Welcome to the store management system ~");
            for (int i = 0; i < menuOptions.values().length; i++) {
                System.out.println((i + 1) + ". " + menuOptions.values()[i] );
            }
            choice = srv.getInput((Integer i) -> (i > menuOptions.values().length || i < 1) ,"Please Enter Your Choice:  ");
            MenuCommandsController controller = new MenuCommandsController(choice);
            Command command = controller.getCommand();
            if (command != null)
                command.execute();
        }while(choice != menuOptions.EXIT_PROGRAM.ordinal()+1);
        System.out.println("Goodbye!");
    }

    public static void starter() throws SQLException, ClassNotFoundException {
        db.addCompanies();
        db.addCountries();
    }

    public static void pressToContinue(){
        for (int i = 0; i < 100 && first; i++) {
            System.out.println();
        }
        if (!first){
            System.out.println("Press Enter to continue...");
            s.nextLine();  // Waits for the user to press Enter
        }
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }
}