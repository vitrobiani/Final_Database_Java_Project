import java.util.Scanner;

public class addShippingCompany extends MenuActionCompleteListener implements Command{
    private Scanner s = new Scanner(System.in);
    private DataBase db = DataBase.getInstance();
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
        if (name.isEmpty() || name.length() > 50){
            System.out.println("Please enter a valid name");
            return getName();
        }
        return "'"+name+"'";
    }

    private Contact getContact(){
        System.out.println("what is the contact's name: ");
        String name = s.nextLine();
        while (name.isEmpty() || name.length() > 50){
            System.out.println("Please enter a valid name");
            name = s.nextLine();
        }

        System.out.println("what is the contact's number: ");
        String number = s.nextLine();
        while (name.isEmpty() || name.length() > 50){
            System.out.println("Please enter a valid number");
            number = s.nextLine();
        }
        return new Contact("'"+name+"'", "'"+number+"'");
    }

    private double getRegularShippingMult(){
        System.out.println("please enter the regular shipping multiplier: ");
        double mult = s.nextDouble();
        if (mult < 0){
            System.out.println("please enter a valid multiplier");
            getRegularShippingMult();
        }
        return mult;
    }

    private double getExpressShippingMult(){
        System.out.println("please enter the express shipping multiplier: ");
        double mult = s.nextDouble();
        if (mult < 0){
            System.out.println("please enter a valid multiplier");
            getRegularShippingMult();
        }
        return mult;
    }
}
