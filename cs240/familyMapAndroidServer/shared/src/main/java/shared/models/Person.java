package shared.models;

import java.util.TreeSet;

public class Person implements Comparable
{



    //we make this one?
    private String personID;


    //Person must have these as strings
    private String descendant;


    private String firstName;
    private String lastName;



    private String gender;


    //can be null
    private String father;
    private String mother;

    private TreeSet<String> events = new TreeSet<>();

    private String spouse;
    public Person()
    {
        personID = java.util.UUID.randomUUID().toString();
    };

    public Person(String descendent, String firstName, String lastName, String gender) {
        this.descendant = descendent;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = java.util.UUID.randomUUID().toString();

    }

    public void setFather(String father) {
        this.father = father;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }


    public String getPersonId() {
        return personID;
    }

    public String getDescendent() {
        return descendant;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getFather() {
        return father;
    }

    public String getMother() {
        return mother;
    }

    public String getSpouse() {
        return spouse;
    }


    public void setPersonId(String personId)
    {
        this.personID = personId;

    }

    public void setDescendent(String descendant)
    {
        this.descendant = descendant;

    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;

    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;

    }


    public void setEvents(TreeSet<String> events)
    {
        this.events = events;

    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }


    public void addEvent(String eventID)
    {
        events.add(eventID);
    }



    public TreeSet<String> getEvents()
    {
        return events;
    }


    @Override
    public int compareTo(Object o)
    {
        return 1;
    }

    public Boolean contains(String param)
    {
        String search = param.toLowerCase();
        if(firstName.toLowerCase().contains(search)) return true;
        if(lastName.toLowerCase().contains(search))return true;
        return false;
    }

}
