import java.sql.ResultSet;
import java.sql.SQLException;

public class dropAllTablesCommand extends MenuActionCompleteListener implements Command{
    DataBase db = DataBase.getInstance();

    @Override
    public boolean execute() {
        try{
            db.UpdateDB("DO\n" +
                    "$$\n" +
                    "DECLARE\n" +
                    "    r RECORD;\n" +
                    "BEGIN\n" +
                    "    FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = current_schema()) LOOP\n" +
                    "        EXECUTE 'DROP TABLE IF EXISTS ' || quote_ident(r.tablename) || ' CASCADE';\n" +
                    "    END LOOP;\n" +
                    "END\n" +
                    "$$;");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            update("not droped");
            return false;
        }
        update("DROPED!");
        return true;
    }
}
