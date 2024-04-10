import javax.xml.crypto.Data;
import java.io.IOException;

public class backupSystemCommand extends MenuActionCompleteListener implements Command {
    public static FileFunctions ff = new FileFunctions();
    public static DataBase db = DataBase.getInstance();
    @Override
    public boolean execute() {
            if (ff.saveDatabase(db)) {
                update("System Backup Complete!");
                return true;
            }else {
                update("System Backup Failed");
                return false;
            }
    }
}
