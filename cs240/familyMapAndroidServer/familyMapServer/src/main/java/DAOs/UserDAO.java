package DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import familyMapDB.FMDatabase;
import shared.models.Person;
import shared.models.User;

public class UserDAO
{

    //return false if User cannot be made
    FMDatabase db;
    Connection conn;
    Statement stmt;

    public UserDAO()
    {
        db = FMDatabase.getInstance();
    }




    public boolean addUser(User user)
    {

        openDbConnection();
        Boolean status = null;
        PreparedStatement prepped = null;
        try
        {
            String sql = "INSERT INTO Users (userName, password, email, firstName, lastName, gender, userID)";
            sql+=" VALUES (?,?,?,?,?,?,?);";
            prepped = conn.prepareStatement(sql);
            prepped.setString(1,user.getUserName());
            prepped.setString(2,user.getPassword());
            prepped.setString(3,user.getEmail());
            prepped.setString(4,user.getFirstName());
            prepped.setString(5,user.getLastName());
            prepped.setString(6,user.getGender());
            prepped.setString(7,user.getPersonId());

            if(prepped.executeUpdate()==1)
            {
                status = true;
            }
            else
            {
                System.out.println("Error creating Person");
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

    public Boolean addMultipleUsers(User[]users)
    {
        openDbConnection();

        Boolean status = null;
        PreparedStatement prepped = null;
        for (User user :users)
        {
            try
            {
                String sql = "INSERT INTO Users(userName, password, email,firstName, lastName, gender, userID)";
                sql+=" VALUES (?,?,?,?,?,?,?);";
                prepped = conn.prepareStatement(sql);
                prepped.setString(1,user.getUserName());
                prepped.setString(2,user.getPassword());
                prepped.setString(3,user.getEmail());
                prepped.setString(4,user.getFirstName());
                prepped.setString(5,user.getLastName());
                prepped.setString(6,user.getGender());
                prepped.setString(7,user.getPersonId());;

                if(prepped.executeUpdate()==1)
                {
                    status = true;
                }
                else
                {
                    System.out.println("Error creating Multiple Events");
                    status = false;
                }

                status=true;
            }
            catch (Exception e)
            {
                status=false;
            }

        }

        closeDbConnection();
        return status;
    }

    public void removeUser()
    {
        openDbConnection();


        closeDbConnection();
    }

    public void removeMultipleUser()
    {
        openDbConnection();


        closeDbConnection();
    }


    public void removeAllUsers()
    {
        openDbConnection();


        closeDbConnection();
    }

    public Boolean checkUserPassword(String user, String passWord)
    {
        openDbConnection();
        ResultSet resultSet;
        Boolean result = false;

            PreparedStatement preparedStmt =null;
            ResultSet results =null;
            try
            {
                Statement state = conn.createStatement();

                results=state.executeQuery("SELECT * from Users WHERE userName = '" + user +"'and password = '" + passWord+"' ;");

                //results = preparedStmt.executeQuery();
                if (!results.next()) result = false;
                else result = true;



            }
            catch (Exception e)
            {
                System.out.print("There was an error retrieving person");
                result =false;
            }


        closeDbConnection();

        return result;
    }

    public Boolean checkUserExists(String user)
    {
        openDbConnection();
        ResultSet resultSet;
        Boolean result = false;

        PreparedStatement preparedStmt =null;
        ResultSet results =null;
        try
        {
            Statement state = conn.createStatement();

            results=state.executeQuery("SELECT * from Users WHERE userName = '" + user +"' ;");

            //results = preparedStmt.executeQuery();
            if (!results.next()) result = false;
            else result = true;



        }
        catch (Exception e)
        {
            System.out.print("There was an error retrieving person");
            result =false;
        }


        closeDbConnection();

        return result;
    }

    public void createTable()
    {
        openDbConnection();
        try
        {
            stmt =conn.createStatement();
            stmt.executeUpdate("drop table if exists Users");

            stmt.executeUpdate("create table if not exists Users(" +
                    "userName text NOT NULL," +
                    "password text NOT NULL," +
                    "email text NOT NULL," +
                    "firstName text NOT NULL," +
                    "lastName text NOT NULL," +
                    "gender text," +
                    "userID text NOT NULL" +
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


    //returns User from dataBase
    public User getUser(String personID)
    {
        openDbConnection();


        Person person =null;
        openDbConnection();
        PreparedStatement preparedStmt =null;
        ResultSet results =null;
        User user = new User();
        try
        {
            Statement state = conn.createStatement();

            results=state.executeQuery("SELECT * from Users WHERE userID = '" +personID +"' ;");

            //results = preparedStmt.executeQuery();

            while (results.next())
            {

                user.setUserName(results.getString("userName"));
                user.setPassword(results.getString("password"));
                user.setEmail(results.getString("email"));
                user.setFirstName(results.getString("firstName"));
                user.setLastName(results.getString("lastName"));
                user.setGender(results.getString("gender"));
                user.setPersonId(results.getString("userID"));

            }


        }
        catch (Exception e)
        {
            System.out.print("There was an error retrieving person");
            return null;
        }



        closeDbConnection();
        return user;
    }

    public String getPID(String userName)
    {
        openDbConnection();


        Person person =null;
        openDbConnection();
        PreparedStatement preparedStmt =null;
        ResultSet results =null;
        User user = new User();
        try
        {
            Statement state = conn.createStatement();

            results=state.executeQuery("SELECT * from Users WHERE userName = '" +userName +"' ;");

            //results = preparedStmt.executeQuery();

            while (results.next())
            {

                user.setUserName(results.getString("userName"));
                user.setPassword(results.getString("password"));
                user.setEmail(results.getString("email"));
                user.setFirstName(results.getString("firstName"));
                user.setLastName(results.getString("lastName"));
                user.setGender(results.getString("gender"));
                user.setPersonId(results.getString("userID"));

            }


        }
        catch (Exception e)
        {
            System.out.print("There was an error retrieving person");
            return null;
        }



        closeDbConnection();
        return user.getPersonId();
    }

    public User[] getUsers()
    {
        return null;
    }




}
