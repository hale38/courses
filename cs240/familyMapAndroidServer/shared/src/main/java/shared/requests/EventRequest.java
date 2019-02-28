package shared.requests;

public class EventRequest
{

    String authToken;
    String eventID;


    public EventRequest(){};

    public EventRequest(String eventID, String authToken)
    {
        this.authToken=authToken;
        this.eventID=eventID;
    }



    //sends an empty body reqest to service


    public String getAuthToken()
    {
        return authToken;
    }

    public void setAuthToken(String authToken)
    {
        this.authToken = authToken;
    }

    public String getEventID()
    {
        return eventID;
    }

    public void setEventID(String eventID)
    {
        this.eventID = eventID;
    }
}
