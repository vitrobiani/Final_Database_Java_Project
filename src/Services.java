import java.io.IOException;
import java.util.InputMismatchException;
import java.util.function.Predicate;

public interface Services {
    public boolean isInteger(String str);

    public <T> T getInput(Predicate<T> condition, String mesg);

    public Country getCountry();

    public void printProductTypes();

    public String getProductCode();

    public Product getProduct();
}
