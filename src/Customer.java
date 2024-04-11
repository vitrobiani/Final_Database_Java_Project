import java.io.Serializable;

public class Customer extends Person implements Serializable {
    public Customer(String name, String phoneNumber) {
        super(name, phoneNumber);
    }
}
