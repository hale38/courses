package familyMapDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by hale38 on 2/24/18.
 */

public class FMDatabase
{


    private Connection conn =null;
    private static FMDatabase instance = null;

    protected FMDatabase(){};

    static
    {
        try
        {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }



    public static FMDatabase getInstance()
    {
        if(instance == null)
        {
            instance = new FMDatabase();
        }
        return instance;
    }


    public Connection getConnection()
    {
        return conn;
    }


    public void openConnection()
    {
        try
        {
            conn = DriverManager.getConnection("jdbc:sqlite:FMSdata.db");
            //conn.setAutoCommit(false);
        }
        catch (SQLException e)
        {
            System.out.print("There was an error connecting to DB");
        }
    }

    public void closeConnection()
    {
        try
        {
            conn.close();
        }

        catch (Exception e)

        {

        }
    }








    public void createTables()
    {
       Statement stmt = null;

        try
        {
         stmt = conn.createStatement();
         stmt.executeUpdate("drop table if exists Person");
         stmt.executeUpdate("drop table if exists Users");
         stmt.executeUpdate("drop table if exists Events");
         stmt.executeUpdate("drop table if exists auth");

         String createPerson = "create table Person( descendant text, personID text NOT NULL, firstName text NOT NULL, lastName text NOT NULL, gender text, father text, mother text, spouse text);";
        // String createUsers= " create table pleaseWork(;)";
         //stmt.executeUpdate("create table Person( descendant text, personID text NOT NULL, firstName text NOT NULL, lastName text NOT NULL, gender text, father text, mother text, spouse text);");

            stmt.executeUpdate("create table Person(" +
                            "descendant text," +
                            "personID text NOT NULL," +
                            "firstName text NOT NULL" +
                            "lastName text NOT NULL," +
                            "gender text," +
                            "father text," +
                            "mother text," +
                            "spouse text" +
                            ");");



         stmt.executeUpdate("create table Users(" +
                 "userName text NOT NULL," +
                 "password text NOT NULL," +
                 "email text NOT NULL," +
                 "firstName text NOT NULL," +
                 "lastName text NOT NULL," +
                 "gender text," +
                 "userID text NOT NULL" +
                 ");");

         stmt.executeUpdate("create table Events(" +
                 "descendant text," +
                 "eventID text NOT NULL," +
                 "personID text NOT NULL," +
                 "latitude double," +
                 "longitude double," +
                 "country text," +
                 "city text," +
                 "eventType text," +
                 "year integer" +
                 ");");

         stmt.executeUpdate("create table auth(" +
                 "userID text NOT NULL, " +
                 "authToken text NOT NULL" +
                 ");");

         stmt.close();
         //System.out.print("failed");
        }
        catch (Exception e)
        {
            System.out.print("failed " + e.getMessage());
        }
        finally
        {

        }


    }







}
