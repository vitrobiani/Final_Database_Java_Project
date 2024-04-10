import javax.xml.crypto.Data;
import java.io.File;

public class restoreSystemCommand extends MenuActionCompleteListener implements Command{
    public static FileFunctions ff = new FileFunctions();
    public DataBase db = DataBase.getInstance();
    @Override
    public boolean execute() {
        DataBase inFile = ff.loadDataBase();
        if (inFile == null){
            update("Loading From File Failed");
            return false;
        }
        db.loadDataBase(inFile);
        update("Loading Completed");
        return true;
    }
}
