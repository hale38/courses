package shared.results;

public class PersonResult
{
    String message;
    String descendent;
    String personID;
    String firstName;
    String lastName;
    String gender;
    String father;
    String mother;
    String spouse;


    public PersonResult(){};

    public String getBody()
    {
        return message;
    }

    public void setBody(String body)
    {
        message = body;
    }

}
