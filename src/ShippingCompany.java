import java.util.HashMap;

public abstract class ShippingCompany {
    public Contact contact;
    public abstract double calculateExpressShippingCost(Order order);
    public abstract double calculateRegularShippingCost(Order order);

    public String getContactName(){
        return contact.getName();
    }
    public String getContactPhone(){
        return contact.getPhoneNumber();
    }
}
