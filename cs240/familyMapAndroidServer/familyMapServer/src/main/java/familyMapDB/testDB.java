package familyMapDB;

/**
 * Created by hale38 on 2/24/18.
 */

public class testDB {
   // private static DB

    public static void main(String args[])
    {
        FMDatabase tdb = new FMDatabase();
        tdb.openConnection();
        //tdb.closeConnection();
        tdb.createTables();
    }

}
