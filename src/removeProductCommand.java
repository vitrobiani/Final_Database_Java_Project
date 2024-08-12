import org.postgresql.util.PSQLException;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.jar.JarOutputStream;

public class removeProductCommand extends MenuActionCompleteListener implements Command{
    public static DataBase db = DataBase.getInstance();
    Services srv = IOServices.getInstance();
    @Override
    public boolean execute() {
        if (db.removeProduct(srv.getProductCode())){
            update("Product Removed Successfully");
        }else {
            update("Product not found");
        }
        return true;
    }
}
