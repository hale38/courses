package shared.results;

public class RegisterResult
{

    private String userName;
    private String passWord;
    private String authToken;
    private String message;

    public RegisterResult(){};

    public RegisterResult(String userName, String passWord, String authToken)
    {
        this.userName = userName;
        this.passWord = passWord;
        this.authToken = authToken;
    }



    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getMessage(){return message;}
    public String getPassWord(){return passWord;}
    public String getAuthToken()
    {
        return authToken;
    }

    public void setAuthToken(String authToken)
    {
        this.authToken = authToken;
    }

    public void setMesage (String message)
    {
        this.message= message;
    }




   //need to find places to set and get



    //takes in User object, extracts data and sends back as a json string
    //if fails, sends a failure response


}

