package shared.results;

public class LoginResult
{

    private String userName;
    private String authToken;
    private String personID;

    public LoginResult()
    {
    }
    public LoginResult(String userName, String auth,String personID)
    {
        this.userName=userName;
        this.authToken=auth;
        this.personID=personID;
    }


    private String message;

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getAuth()
    {
        return authToken;
    }

    public void setAuth(String auth)
    {
        this.authToken = auth;
    }

    public String getPassWord()
    {
        return personID;
    }

    public void setPassWord(String passWord)
    {
        this.personID = passWord;
    }


    public String getBody()
    {
        return message;
    }

    public void setBody(String body)
    {
        this.message = body;
    }

    //takes in a User object, extracts info and sends back as a JSON string
    //otherwise sends back error code

}
