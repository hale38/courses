package DAOs;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.TreeSet;

import familyMapDB.FMDatabase;
import shared.models.Person;

public class PersonDAO
{

    FMDatabase db;
    Connection conn;
    Statement stmt;
    public PersonDAO()
    {
        db = FMDatabase.getInstance();
    }


    //adds Person to DB
    public Boolean addSinglePerson(Person person)
    {
        Boolean status;
        openDbConnection();
        PreparedStatement prepped = null;
        try
        {
            String sql = "INSERT INTO Person(descendant, personID,firstName,lastName, gender, father, mother, spouse)";
            sql+=" VALUES (?,?,?,?,?,?,?,?);";
            prepped = conn.prepareStatement(sql);
            prepped.setString(1, person.getDescendent());
            prepped.setString(2,person.getPersonId());
            prepped.setString(3,person.getFirstName());
            prepped.setString(4,person.getLastName());
            prepped.setString(5,person.getGender());
            prepped.setString(6,person.getFather());
            prepped.setString(7,person.getMother());
            prepped.setString(8,person.getSpouse());

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

    public void addPersonList(Person[] people )
    {
        openDbConnection();
        for (Person person : people)
        {
            PreparedStatement prepped = null;
            try
            {
                String sql = "INSERT INTO Person(descendant, personID,firstName,lastName, gender, father, mother, spouse)";
                sql+=" VALUES (?,?,?,?,?,?,?,?);";
                prepped = conn.prepareStatement(sql);
                prepped.setString(1, person.getDescendent());
                prepped.setString(2,person.getPersonId());
                prepped.setString(3,person.getFirstName());
                prepped.setString(4,person.getLastName());
                prepped.setString(5,person.getGender());
                prepped.setString(6,person.getFather());
                prepped.setString(7,person.getMother());
                prepped.setString(8,person.getSpouse());

                if(prepped.executeUpdate()==1)
                {

                }
                else
                {
                    System.out.println("Error creating Person");

                }

            }
            catch (Exception e)
            {
                System.out.print("There was an error in adding  multiple people");
            }
        }
        closeDbConnection();
    }

    public void removePerson(Person person)
    {
        openDbConnection();
        String query = "delete from Person where personID = '" + person.getPersonId()+"';";
        try
        {
            Statement state = conn.createStatement();
            state.execute(query);

        }
        catch (Exception e)
        {
            System.out.println("Error removing Person");
        }


        closeDbConnection();
    }

    public void removeAllPerson()
    {
        createTable();
    }

    public void createTable()
    {
        openDbConnection();
        try
        {
            stmt =conn.createStatement();
            stmt.executeUpdate("drop table if exists Person");
            stmt.executeUpdate("create table if not exists Person(" +
                    "descendant text," +
                    "personID text NOT NULL," +
                    "firstName text NOT NULL," +
                    "lastName text NOT NULL," +
                    "gender text," +
                    "father text," +
                    "mother text," +
                    "spouse text" +
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



    public Person getSinglePerson(String ID, String userName)
    {
        Person person =null;
        openDbConnection();
        PreparedStatement preparedStmt =null;
        ResultSet results =null;
        try
        {
            Statement state = conn.createStatement();

            results=state.executeQuery("SELECT * from Person WHERE personID  = '" +ID +"'and descendant = '" + userName+"' ;");

            //results = preparedStmt.executeQuery();

            while (results.next())
            {
               // System.out.print(results.getString(1));
                person =new Person();
                person.setDescendent(results.getString("descendant"));
                person.setPersonId(results.getString("personID"));
                person.setFirstName(results.getString("firstName"));
                person.setLastName(results.getString("lastName"));
                person.setGender(results.getString("gender"));
                person.setFather(results.getString("father"));
                person.setMother(results.getString("mother"));
                person.setSpouse(results.getString("spouse"));

            }


        }
        catch (Exception e)
        {
            System.out.print("There was an error retrieving person");
        }


       // results.close();
       // preparedStmt.close();


        closeDbConnection();

        return person;
    }
    public TreeSet<Person> getMultiplePerson(String id)
    {
        openDbConnection();
        TreeSet<Person> persons = new TreeSet<>();
        EventDAO eventDAO = new EventDAO();


        try
        {
          //  Statement state = conn.createStatement();
            ResultSet results;
            String query= "SELECT * from Person WHERE descendant = ? ";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,id);
            results=stmt.executeQuery();

            //results = preparedStmt.executeQuery();
            Person person;
            while (results.next())
            {
                // System.out.print(results.getString(1));
                person =new Person();
                person.setDescendent(results.getString("descendant"));
                person.setPersonId(results.getString("personID"));
                person.setFirstName(results.getString("firstName"));
                person.setLastName(results.getString("lastName"));
                person.setGender(results.getString("gender"));
                person.setFather(results.getString("father"));
                person.setMother(results.getString("mother"));
                person.setSpouse(results.getString("spouse"));

                //person.setEvents(eventDAO.getEventsPersonID(person.getPersonId()));
                persons.add(person);
            }


        }
        catch (Exception e)
        {
            System.out.print("There was an error retrieving person");
        }

        for (Person person: persons)
        {
            person.setEvents(eventDAO.getEventsPersonID(person.getPersonId()));
        }


        closeDbConnection();

        return persons;
    }


    public Boolean purgeDescendant(String descendantID)
    {
        Person person =null;
        openDbConnection();
        PreparedStatement preparedStmt =null;
        ResultSet results =null;
        try
        {
            Statement state = conn.createStatement();

            state.execute("DELETE from Person WHERE descendant  = '" + descendantID +"' ;");

            //results = preparedStmt.executeQuery();
        }
        catch (Exception e)
        {
            System.out.print("There was an error Deleting person");
            return false;
        }


        // results.close();
        // preparedStmt.close();


        closeDbConnection();

        return true;
    }



}
