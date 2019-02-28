package DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import familyMapDB.FMDatabase;
import shared.models.User;

public class AuthDAO
{

    FMDatabase db;
    Connection conn;
    Statement stmt;

    public AuthDAO()
    {
       db = FMDatabase.getInstance();
    }


    public Boolean addSingleAuth(String uid, String auth)
    {
        openDbConnection();
        Boolean status = null;
        PreparedStatement prepped = null;
        try
        {
            String sql = "INSERT INTO auth(userID,authToken)";
            sql+=" VALUES (?,?);";
            prepped = conn.prepareStatement(sql);
            prepped.setString(1,uid);
            prepped.setString(2,auth);

            if(prepped.executeUpdate()==1)
            {
                status = true;
            }
            else
            {
                System.out.println("Error creating Auth" );
                status = false;
            }

            status=true;
        }
        catch (Exception e)
        {
            status=false;
        }



        closeDbConnection();
        return status;
    }

    public boolean addMultipleAuths(ArrayList<User>users)
    {
        openDbConnection();

        Boolean status = null;
        PreparedStatement prepped = null;
        try
        {
            for (User user: users)
            {


                String sql = "INSERT INTO auth(userID,authToken)";
                sql += " VALUES (?,?);";
                prepped = conn.prepareStatement(sql);
                prepped.setString(1, user.getPersonId());
                prepped.setString(2, user.getAuthToken());

                if (prepped.executeUpdate() == 1)
                {
                    status = true;
                } else
                {
                    System.out.println("Error creating Auth");
                    status = false;
                }
            }

            status=true;
        }
        catch (Exception e)
        {
            status=false;
        }

        closeDbConnection();
        return status;
    }


    public void removeAllAuth()
    {
        createTable();
    }


    public void createTable()
    {
        openDbConnection();
        try
        {
            stmt =conn.createStatement();
            stmt.executeUpdate("drop table if exists auth");
            stmt.executeUpdate("create table if not exists auth(" +
                                "userID text NOT NULL, " +
                                "authToken text NOT NULL" +
                                ");");
            stmt.close();
        }
        catch (Exception e)
        {
            System.out.print("Creating Table Failed");
        }


        closeDbConnection();
    }


    private void openDbConnection()
    {
        db.openConnection();
        conn= db.getConnection();
    }

    private void closeDbConnection()
    {
        db.closeConnection();
    }



    public String getPersonID(String authToken)
    {
        openDbConnection();
        PreparedStatement preparedStmt =null;
        ResultSet results =null;
        String pid = null;
        try
        {
            Statement state = conn.createStatement();

            results=state.executeQuery("SELECT * from auth WHERE authToken = '" + authToken +"' ;");

            //results = preparedStmt.executeQuery();

            while (results.next())
            {
                pid = results.getString("userId");
                // System.out.print(results.getString(1));
                //PreparedStatement preparedStm
            }


        }
        catch (Exception e)
        {
            System.out.print("There was an error retrieving Auth");
        }

        closeDbConnection();
        return pid;
    }

    public String getMultiple()
    {
        return null;
    }





}
