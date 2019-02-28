package DAOs;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.TreeSet;

import familyMapDB.FMDatabase;
import shared.models.Event;

public class EventDAO
{
    FMDatabase db;
    Connection conn;
    Statement stmt;
    public EventDAO()
    {
    db = FMDatabase.getInstance();
    }



    public boolean addSingleEvent(Event event)
    {
        openDbConnection();
        Boolean status = null;
        PreparedStatement prepped = null;
        try
        {
            String sql = "INSERT INTO Events(descendant, eventID, personID,latitude, longitude, country, city, eventType, year)";
            sql+=" VALUES (?,?,?,?,?,?,?,?,?);";
            prepped = conn.prepareStatement(sql);
            prepped.setString(1,event.getDescendent());
            prepped.setString(2,event.getEventID());
            prepped.setString(3,event.getPersonID());
            prepped.setDouble(4,event.getLatitude());
            prepped.setDouble(5,event.getLongitude());
            prepped.setString(6,event.getCountry());
            prepped.setString(7,event.getCity());
            prepped.setString(8,event.getEventType());
            prepped.setString(9,event.getYear());

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

    public Boolean addEventList(Event[] events)
    {
        openDbConnection();
        Boolean status = null;
        PreparedStatement prepped = null;
        for (Event event :events)
        {
            try
            {
                String sql = "INSERT INTO Events(descendant, eventID, personID,latitude, longitude, country, city, eventType, year)";
                sql+=" VALUES (?,?,?,?,?,?,?,?,?);";
                prepped = conn.prepareStatement(sql);
                prepped.setString(1,event.getDescendent());
                prepped.setString(2,event.getEventID());
                prepped.setString(3,event.getPersonID());
                prepped.setDouble(4,event.getLatitude());
                prepped.setDouble(5,event.getLongitude());
                prepped.setString(6,event.getCountry());
                prepped.setString(7,event.getCity());
                prepped.setString(8,event.getEventType());
                prepped.setString(9,event.getYear());

                if(prepped.executeUpdate()==1)
                {
                    status = true;
                }
                else
                {
                    System.out.println("Error creating event");
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

    //not sure if I need this
    public void removeSingleEvent()
    {
        openDbConnection();


        closeDbConnection();

    }

    public void removeAllEvent()
    {
        createTable();
    }

    public void createTable()
    {
        openDbConnection();
        try
        {
            stmt =conn.createStatement();
            stmt.executeUpdate("drop table if exists Events");
            stmt.executeUpdate("create table if not exists Events(" +
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






    public Event getEvent(String eventID)
{
    openDbConnection();
    Event event = null;

    PreparedStatement preparedStmt =null;
    ResultSet results =null;
    try
    {
        Statement state = conn.createStatement();

        results=state.executeQuery("SELECT * from Events WHERE eventID = '" +eventID +"' ;");

        //results = preparedStmt.executeQuery();

        while (results.next())
        {
            // System.out.print(results.getString(1));
            event =new Event();
            event.setDescendent(results.getString("descendant"));
            event.setEventID(results.getString("eventID"));
            event.setPersonID(results.getString("personID"));
            event.setLongitude(results.getDouble("longitude"));
            event.setLatitude(results.getDouble("latitude"));
            event.setCountry(results.getString("country"));
            event.setCity(results.getString("city"));
            event.setEventType(results.getString("eventType"));
            event.setYear(results.getString("year"));
        }


    }
    catch (Exception e)
    {
        System.out.print("There was an error retrieving person");
    }

    closeDbConnection();

    return event;
}

    public Event getEventPersonID(String personID)
    {
        openDbConnection();
        Event event = null;

        PreparedStatement preparedStmt =null;
        ResultSet results =null;
        try
        {
            Statement state = conn.createStatement();

            results=state.executeQuery("SELECT * from Events WHERE personID = '" +personID +"' ;");

            //results = preparedStmt.executeQuery();

            while (results.next())
            {
                // System.out.print(results.getString(1));
                event =new Event();
                event.setDescendent(results.getString("descendant"));
                event.setEventID(results.getString("eventID"));
                event.setPersonID(results.getString("personID"));
                event.setLongitude(results.getDouble("longitude"));
                event.setLatitude(results.getDouble("latitude"));
                event.setCountry(results.getString("country"));
                event.setCity(results.getString("city"));
                event.setEventType(results.getString("eventType"));
                event.setYear(results.getString("year"));
            }


        }
        catch (Exception e)
        {
            System.out.print("There was an error retrieving person");
        }

        closeDbConnection();

        return event;
    }

    public TreeSet<Event> getEvents(TreeSet<String>eventIDs)
    {
        TreeSet<Event>events = new TreeSet<>();

        openDbConnection();
        Event event = null;

        PreparedStatement preparedStmt =null;
        ResultSet results =null;


        for (String eventID:eventIDs)
        {
            try
            {
                Statement state = conn.createStatement();

                results = state.executeQuery("SELECT * from Events WHERE eventID = '" + eventID + "' ;");

                //results = preparedStmt.executeQuery();

                while (results.next())
                {
                    // System.out.print(results.getString(1));
                    event = new Event();
                    event.setDescendent(results.getString("descendant"));
                    event.setEventID(results.getString("eventID"));
                    event.setPersonID(results.getString("personID"));
                    event.setLongitude(results.getDouble("longitude"));
                    event.setLatitude(results.getDouble("latitude"));
                    event.setCountry(results.getString("country"));
                    event.setCity(results.getString("city"));
                    event.setEventType(results.getString("eventType"));
                    event.setYear(results.getString("year"));

                    events.add(event);
                }


            } catch (Exception e)
            {
                System.out.print("There was an error retrieving person");
            }


        }
        closeDbConnection();
        return events;
    }



    public TreeSet<String> getEventsPersonID(String personID)
    {
        TreeSet<String>events = new TreeSet<>();

        openDbConnection();
        Event event = null;

        PreparedStatement preparedStmt =null;
        ResultSet results =null;

        try
            {
                Statement state = conn.createStatement();

                results = state.executeQuery("SELECT * from Events WHERE personID = '" + personID + "' ;");

                //results = preparedStmt.executeQuery();
                while (results.next())
                {
                    // System.out.print(results.getString(1));
                    String eventId = results.getString("eventID");


                    events.add(eventId);
                }
            } catch (Exception e)
            {
                System.out.print("There was an error retrieving person");
            }



        closeDbConnection();
        return events;
    }


    public TreeSet<String> getEventsFromDescendant(String descendant)
    {
        TreeSet<String>events = new TreeSet<>();

        openDbConnection();
        Event event = null;

        PreparedStatement preparedStmt =null;
        ResultSet results =null;

        try
        {
            Statement state = conn.createStatement();

            results = state.executeQuery("SELECT * from Events WHERE descendant = '" + descendant + "' ;");

            //results = preparedStmt.executeQuery();
            while (results.next())
            {
                // System.out.print(results.getString(1));
                String eventId = results.getString("eventID");


                events.add(eventId);
            }
        } catch (Exception e)
        {
            System.out.print("There was an error retrieving person");
        }



        closeDbConnection();
        return events;
    }

    public Boolean purgeDescendent(String descendentID)
    {
        openDbConnection();


        PreparedStatement preparedStmt =null;
        ResultSet results =null;
        try
        {
            Statement state = conn.createStatement();

            state.execute(" DELETE from Events WHERE descendant = '" + descendentID +"' ;");

        }
        catch (Exception e)
        {
            System.out.print("There was an error Deleting Event");
            return false;
        }

        closeDbConnection();

        return true;
    }

    public Boolean purgeUser(String personID)
    {
        openDbConnection();


        PreparedStatement preparedStmt =null;
        ResultSet results =null;
        try
        {
            Statement state = conn.createStatement();

            state.execute(" DELETE from Events WHERE personID = '" + personID +"' ;");

        }
        catch (Exception e)
        {
            System.out.print("There was an error Deleting Event");
            return false;
        }

        closeDbConnection();

        return true;
    }
}
