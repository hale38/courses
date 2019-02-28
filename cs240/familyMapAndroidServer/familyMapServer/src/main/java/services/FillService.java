package services;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

import DAOs.AuthDAO;
import DAOs.EventDAO;
import DAOs.PersonDAO;
import DAOs.UserDAO;
import shared.models.Event;
import shared.models.Person;
import shared.models.User;
import shared.requests.FillRequest;
import shared.results.FillResult;


public class FillService
{
    AuthDAO authD;
    EventDAO eventD;
    PersonDAO personD;
    UserDAO userD;
    String descendID;
    int numGens;

    private String [] mnames;
    private String [] fnames;
    private String [] snames;
    private EventLocation[] locations;
    private User user;
    private String [] eventType;
    private int numPersons = 0;
    private int numEvents = 0;
    private int currentYear = 2018;

    private ArrayList<Person> peopleList = new ArrayList<>();


    public FillService()
    {
        eventD = new EventDAO();
        personD = new PersonDAO();
        userD = new UserDAO();
        eventType = new String[]{"Birth","Baptism","Marriage","Death"};

        loadMnames();
        loadFnames();
        loadSnames();
        loadLocations();
    };


    //takes a request, fills data base with info
    //returns appropriate response
    //Depending on how things go, I might add additional methods for each type of request

    public FillResult takeRequest(FillRequest request)
    {
        FillResult fillResult = new FillResult();
        if(!userD.checkUserExists(request.getUserName()))
        {
            fillResult.setMessage("User Does Not Exist In Database");
            return fillResult;
        }
        String userID = userD.getPID(request.getUserName());

        user = userD.getUser(userID);
        personD.purgeDescendant(user.getUserName());
        eventD.purgeDescendent(user.getUserName());

        Person person = new Person();
        person.setGender(user.getGender());
        person.setFirstName(user.getFirstName());
        person.setLastName(user.getLastName());
        person.setPersonId(user.getPersonId());
        person.setDescendent(user.getUserName());

        descendID = user.getUserName();

        numGens=request.getGenerations();


        fillWrapper(person,request.getGenerations());


        fillResult.setMessage("Successfully added " + numPersons +" people and " + numEvents +" events to the Database");
        return fillResult;
    }

    public void fillWrapper(Person person, int numGen)
    {

        //make events for new person

        eventD.purgeUser(person.getPersonId());
        int birth=generateBirth(currentYear);
        int bapt=generateInBetween(birth,currentYear);
        int marr=generateInBetween(birth,currentYear);

        person.addEvent(generateEvent("Birth",Integer.toString(birth),null,person.getPersonId()));
        person.addEvent(generateEvent("Baptism",Integer.toString(bapt),null,person.getPersonId()));
        person.addEvent(generateEvent("Marriage",Integer.toString(marr),null, person.getPersonId()));


        personD.removePerson(person); //removes person
       //personD.addSinglePerson(person);//updates person
        numPersons++;

        currentYear = birth;
        if(numGen == 0)
        {
            return;
        }

        fillRecurse(person, 1);

        for (Person people: peopleList)
        {
            personD.addSinglePerson(people);
        }

    }

    private void fillRecurse(Person person, int currentGen)
    {
        peopleList.add(person);
        Random random = new Random();
        Person father = new Person();
        Person mother = new Person();

        String surName = generateSname();


        int mBirth=generateBirth(currentYear);
        int mDeath=generateDeath(currentYear);
        int mBapt=generateInBetween(mBirth,mDeath);
        int mMarr=generateInBetween(mBirth,mDeath);

//set motherInfo

        //System.out.println("Mother " + currentGen);
        mother.setDescendent(descendID);
        mother.setSpouse(father.getPersonId());
        mother.setFirstName(generateFname());
        mother.setLastName(surName);
        mother.setGender("F");




       // personD.addSinglePerson(mother);
        numPersons++;

//set father info


        father.setSpouse(mother.getPersonId());
        father.setFirstName(generateMname());
        father.setDescendent(descendID);
        father.setLastName(surName);
        father.setGender("M");

        int fBirth=generateBirth(currentYear);
        int fDeath=generateDeath(currentYear);
        int fBapt=generateInBetween(fBirth,fDeath);
        int fMarr=generateInBetween(fBirth,fDeath);



       // personD.addSinglePerson(father);
        numPersons++;

        if(currentGen <numGens)
        {
            person.setMother(mother.getPersonId());
            person.setFather(father.getPersonId());
            mother.addEvent(generateEvent("Birth",Integer.toString(mBirth),mother.getDescendent(),mother.getPersonId()));
            mother.addEvent(generateEvent("Baptism",Integer.toString(mBapt),mother.getDescendent(),mother.getPersonId()));
            mother.addEvent(generateEvent("Marriage",Integer.toString(mMarr),mother.getDescendent(),mother.getPersonId()));
            mother.addEvent(generateEvent("Death",Integer.toString(mDeath),mother.getDescendent(),mother.getPersonId()));
            father.addEvent(generateEvent("Birth",Integer.toString(fBirth),father.getDescendent(),father.getPersonId()));
            father.addEvent(generateEvent("Baptism",Integer.toString(fBapt),father.getDescendent(),father.getPersonId()));
            father.addEvent(generateEvent("Marriage",Integer.toString(fMarr),father.getDescendent(),father.getPersonId()));
            father.addEvent(generateEvent("Death",Integer.toString(fDeath),father.getPersonId(),father.getPersonId()));

            currentGen++;
            currentYear=mBirth;
            fillRecurse(father,currentGen);
            currentYear=fBirth;
            fillRecurse(mother,currentGen);
        }
    }

    private void loadMnames()
    {
        Gson gson = new Gson();
        JsonParser jsonParser  = new JsonParser();

        try
        {
            Object obj = jsonParser.parse(new FileReader("webFiles/fillFiles/mnames.json"));
            JsonObject jsonObject = (JsonObject)obj;
            JsonArray jsonArray = jsonObject.getAsJsonArray("data");
            mnames = gson.fromJson(jsonArray,String [].class);

            //System.out.print("Error in Parsing Json");
           // JsonReader jsonReader = new JsonReader(new FileReader("webFiles/fillFiles/mnames.json"));

        }

        catch (Exception e)
        {
            System.out.print("Error in Parsing Json");
        }

    }

    private void loadFnames()
    {

        Gson gson = new Gson();
        JsonParser jsonParser  = new JsonParser();
        try
        {
            Object obj = jsonParser.parse(new FileReader("webFiles/fillFiles/fnames.json"));
            JsonObject jsonObject = (JsonObject)obj;
            JsonArray jsonArray = jsonObject.getAsJsonArray("data");
            fnames = gson.fromJson(jsonArray,String [].class);

            //System.out.print("Error in Parsing Json");
            // JsonReader jsonReader = new JsonReader(new FileReader("webFiles/fillFiles/mnames.json"));

        }
        catch (Exception e)
        {
            System.out.print("Error in Parsing Json");
        }
    }

    private void loadSnames()
    {

        Gson gson = new Gson();
        JsonParser jsonParser  = new JsonParser();
        try
        {
            Object obj = jsonParser.parse(new FileReader("webFiles/fillFiles/snames.json"));
            JsonObject jsonObject = (JsonObject)obj;
            JsonArray jsonArray = jsonObject.getAsJsonArray("data");
            snames = gson.fromJson(jsonArray,String [].class);


            // JsonReader jsonReader = new JsonReader(new FileReader("webFiles/fillFiles/mnames.json"));

        }
        catch (Exception e)
        {
            System.out.print("Error in Parsing Json");
        }
    }

    public void loadLocations()
    {
        Gson gson = new Gson();
        JsonParser jsonParser  = new JsonParser();
        locations = new EventLocation[]{};
        try
        {

            Object obj = jsonParser.parse(new FileReader("webFiles/fillFiles/locations.json"));
            JsonObject jsonObject = (JsonObject)obj;
            JsonArray jsonArray = jsonObject.getAsJsonArray("data");

            locations=gson.fromJson(jsonArray, EventLocation[].class);


            // System.out.print("Error in Parsing Json");

        }
        catch (Exception e)
        {
            System.out.print("Error in Parsing Json");
        }

    }



    private EventLocation generateLocation()
    {
        Random rn = new Random();
        return locations[rn.nextInt(locations.length)];
    }

    private String generateEvent(String type,String year,String descendentID,String personID)
    {
        Event event = new Event();
        EventLocation location = generateLocation();
        event.setEventType(type);
        event.setYear(year);
        event.setCity(location.city);
        event.setCountry(location.country);
        event.setLatitude(location.latitude);
        event.setLongitude(location.longitude);
        event.setDescendent(descendID);
        event.setPersonID(personID);

        eventD.addSingleEvent(event);
        numEvents++;
     return event.getEventID();
    }



    public String generateMname()
    {
        Random rn = new Random();

        return mnames[rn.nextInt(mnames.length)];
    }

    private String generateFname()
    {
        Random rn = new Random();

        return fnames[rn.nextInt(fnames.length)];
    }

    private String generateSname()
    {
        Random rn = new Random();

        return snames[rn.nextInt(snames.length)];
    }


    public int generateBirth(int currentYear)
    {
        Random random = new Random();
        int age= random.nextInt(92)+20;

        return currentYear-age;
    }

    public int generateDeath (int birthYear)
    {
        Random random = new Random();
        int age= random.nextInt(92)+15;
        return birthYear+age;
    }

    public int generateInBetween(int birth, int death)
    {
        Random random = new Random();
        int age= death-birth;

        return random.nextInt(age)+birth;
    }












    private class EventLocation
    {
        String country;
        String city;
        double latitude;
        double longitude;

        public EventLocation(){}


    }




}
