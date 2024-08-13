import java.util.HashMap;

public class ShippingCompany {
    public String Name;
    public Contact contact;
    public double regularShippingMult;
    public double expressShippingMult;

    public ShippingCompany(String Name, Contact contact, double regularShippingMult, double expressShippingMult){
        this.Name = Name;
        this.contact = contact;
        this.regularShippingMult = regularShippingMult;
        this.expressShippingMult = expressShippingMult;
    }

    public double calculateExpressShippingCost(Order order){
        return regularShippingMult * order.quantity;
    }
    public double calculateRegularShippingCost(Order order){
        return expressShippingMult * order.quantity;
    }

    public String getContactName(){
        return contact.getName();
    }
    public String getContactPhone(){
        return contact.getPhoneNumber();
    }

    public String toString(){
        return Name;
    }
}
