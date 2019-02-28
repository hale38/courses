package shared.results;

public class EventResult
{
    String body;




    public EventResult(){};

    public EventResult(String Body)
    {
        this.body=Body;
    }

    public void setBody(String body)
    {
        this.body=body;
    }

    public String getBody()
    {
        return body;
    }


}
