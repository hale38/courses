package shared.requests;

public class FillRequest
{
    int generations;
    String userName;

    public FillRequest()
    {
        generations = 4;
    };

    public int getGenerations()
    {
        return generations;
    }

    public void setGenerations(int generations)
    {
        this.generations = generations;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }




}
