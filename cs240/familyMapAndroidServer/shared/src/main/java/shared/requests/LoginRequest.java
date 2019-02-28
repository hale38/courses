package shared.requests;

import shared.models.User;
import shared.results.LoginResult;

public class LoginRequest
{

    private String userName;
    private String password;

    private LoginResult result;

    public LoginRequest(){};


    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }



    //takes in a User object, extracts username and password sends self to service
    public void sendRequest(User User)
    {

    }
}
