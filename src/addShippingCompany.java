import java.util.*;

public class addShippingCompany extends MenuActionCompleteListener implements Command{
    private Scanner s = new Scanner(System.in);
    private DataBase db = DataBase.getInstance();
    private Services srv = IOServices.getInstance();
    @Override
    public boolean execute() {
        if (db.addShippingCompany(getName(), getContact(), getRegularShippingMult(),getExpressShippingMult())){
            update("Shipping Company Added!");
            return true;
        }
        update("Shipping Company Was Not Added");
        return false;
    }

    private String getName(){
        System.out.println("what is the name of the Shipping Company: \n");
        String name = s.nextLine();
        if (name.isEmpty() || name.length() > 50 || isAlphaNumeric(name)){
            System.out.println("Please enter a valid name");
            return getName();
        }
        return "'"+name+"'";
    }

    private Contact getContact(){
        System.out.println("what is the contact's name: ");
        String name = s.nextLine();
        while (name.isEmpty() || name.length() > 50 || !isAlpha(name)){
            System.out.println("Please enter a valid name");
            name = s.nextLine();
        }

        System.out.println("what is the contact's number: ");
        String number = s.nextLine();
        while (name.isEmpty() || name.length() > 50 || !isNumeric(number)){
            System.out.println("Please enter a valid number");
            number = s.nextLine();
        }
        return new Contact("'"+name+"'", "'"+number+"'");
    }

    private double getRegularShippingMult(){
        System.out.println("please enter the the regular multiplier: ");
        double mult = -1;
        do {
            try {
                mult = s.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid multiplier");
                s.next();
            }
        }while(mult < 0);
        return mult;
    }

    private double getExpressShippingMult(){
        System.out.println("please enter the express shipping multiplier: ");
        double mult = -1;
        do {
            try {
                mult = s.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid multiplier");
                s.next();
            }
        }while(mult < 0);
        return mult;
    }

    private boolean isAlpha(String str){
        int len = str.length();
        int i = 0;
        while (i < len){
            if (!Character.isLetter(str.charAt(i))) {
                return false;
            }
            i++;
        }
        return true;
    }

    private boolean isNumeric(String str) {
        int len = str.length();
        int i = 0;
        while (i < len){
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
            i++;
        }
        return true;
    }

    private boolean isAlphaNumeric(String str) {
        return isNumeric(str) && isAlpha(str);
    }

}
