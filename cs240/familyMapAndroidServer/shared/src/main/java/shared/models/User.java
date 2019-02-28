package shared.models;

public class User
{

    private String userName;

    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String personID;




    //need to find places to set and get, maybe constructor?



    private String authToken; //need to find places to set and get

    private Person Self;




    public User()
    {
        personID = java.util.UUID.randomUUID().toString();
    };

    public User(String userName, String password, String email, String firstName, String lastName, String gender)
    {
        this.userName =userName;
        this.password = password;
        this.email =email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender =gender;
        personID = java.util.UUID.randomUUID().toString();
    }

    public String getAuthToken() {
        return authToken;
    }

    public Person getSelf()
    {
        return null;
    }



    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
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

    public String getPersonId() {
        return personID;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public void setPersonId(String personId)
    {
        this.personID = personId;
    }

    public void setAuthToken (String auth){this.authToken=auth;}

    public void setSelf(Person self)
    {
        Self = self;
    }

}
