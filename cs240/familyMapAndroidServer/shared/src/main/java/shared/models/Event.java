package shared.models;

public class Event implements Comparable
{
    private String eventID;
    private String descendant;
    private String user;

    private String country;
    private String city;
    private String eventType;
    private String year;



    private String personID;



    private double latitude;
    private double longitude;

    public Event()
    {
        eventID = java.util.UUID.randomUUID().toString();
    };
    public Event(String personID, String descendent, String country, String city, String eventType, String year) {
        this.personID = personID;
        this.descendant = descendent;
        //this.user = user;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
        eventID = java.util.UUID.randomUUID().toString();
    }

    private void makeEventID()
    {

    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }



    public String getEventID() {
        return eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public String getDescendent() {
        return descendant;
    }

    public String getUser() {
        return user;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getEventType() {
        return eventType;
    }

    public String getYear() {
        return year;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setEventID(String eventID)
    {
        this.eventID = eventID;
    }

    public void setDescendent(String descendent)
    {
        this.descendant = descendent;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public void setCity(String city)
    {
        this.city = city;

    }

    public void setEventType(String eventType)
    {
        this.eventType = eventType;
    }

    public void setYear(String year)
    {
        this.year = year;
    }

    public void setPersonID(String personID)
    {
        this.personID = personID;
    }


    @Override
    public String toString() {
        return "Event{" +
                "eventID='" + eventID + '\'' +
                ", descendent='" + descendant + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", eventType='" + eventType + '\'' +
                ", year='" + year + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", personID='" + personID + '\'' +
                '}';
    }


    @Override
    public int compareTo(Object o)
    {
        return 1;
    }

    public Boolean contains(String string)
    {
        String param = string.toLowerCase();
        if(country.toLowerCase().contains(param))return true;
        if(city.toLowerCase().contains(param))return true;
        if(eventType.toLowerCase().contains(param))return true;
        if(year.toLowerCase().contains(param))return true;
        return false;
    }


}
