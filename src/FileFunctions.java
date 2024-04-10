import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

public class FileFunctions {
    public static final String dir = System.getProperty("user.dir") + "\\";

    public void saveToFile(String fn, String str) throws FileNotFoundException {
        File testFile = new File(fn);
        PrintWriter pw = new PrintWriter(testFile);
        pw.print(str);
        pw.close();
    }

    public boolean saveDatabase(DataBase db) {
        try {
            File file = new File("Rep.dat");
            ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream(file));
            outFile.writeObject(db);
            outFile.close();
        }catch (IOException e){
            return false;
        }
        return true;
    }

    public DataBase loadDataBase() {
        try {
            ObjectInputStream inFile = new ObjectInputStream(new FileInputStream("Rep.dat"));
            DataBase db = (DataBase) inFile.readObject();
            inFile.close();
            return db;
        }catch(Exception e){
            return null;
        }
    }
}
