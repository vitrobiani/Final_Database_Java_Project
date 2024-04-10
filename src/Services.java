import java.io.IOException;
import java.util.InputMismatchException;
import java.util.function.Predicate;

public interface Services {
    public boolean isInteger(String str);
    public <T> T getInput(Predicate<T> condition, String mesg);

    public ShippingType getShippingType();

    public String getDestCountry();

    public String addCountryToList();

    public void printProductTypes();

    public boolean removeProduct();

    public Product getProduct();
}
