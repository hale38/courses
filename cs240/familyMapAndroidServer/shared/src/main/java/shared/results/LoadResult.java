package shared.results;

public class LoadResult
{

    public LoadResult(){}

    private String message;
    private String numUsers;
    private String numEvents;
    private String numPersons;

    public String getResponse()
    {
        return message;
    }

    public void setSuccess(String numUsers, String numEvents, String numPersons)
    {
    }


    public void setResponse(String response)
    {

        this.message = response;
    }


}
