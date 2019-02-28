package shared.results;

public class FillResult
{

    public FillResult(){};
    String message;

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public void badRequest()
    {
        message= "Invalid Request";
    }





    //sends a success or reason for failure message


}
