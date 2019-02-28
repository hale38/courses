package shared.requests;

public class PersonRequest
{

    private String body;
    private String authToken;

    public PersonRequest(){}

    public String getBody()
    {
        return body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    public String getAuthToken()
    {
        return authToken;
    }

    public void setAuthToken(String authToken)
    {
        this.authToken = authToken;
    }




    //empty body
    //maybe send User name and associated ID?



}
