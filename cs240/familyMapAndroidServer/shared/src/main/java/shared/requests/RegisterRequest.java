package shared.requests;


import shared.results.RegisterResult;

public class RegisterRequest
{
    //takes in a User object, extracts data, converts data to json object and sends request

    String body;
    String auth;
    String userName;
    String password;
    String email;
    String firstName;



    String lastName;
    String gender;

    public RegisterRequest(){};

    public void  setBody(String body)
    {
        this.body = body;
    }

    public void setAuth(String auth)
    {
        this.auth = auth;
    }

    public String getBody()
    {
        return body;
    }

    public String getAuth()
    {
        return getAuth();
    }


    public RegisterResult sendRequest()
    {

        return null;
    }


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

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }
}
