package shared.results;


import com.google.gson.Gson;

public class ClearResult
{

    private String message="message : ";
    public ClearResult(){};

    public ClearResult(String message)
    {
        this.message += message;
    };

    public String sendResponse()

    {
        return null;
    }

    public String getMessage()
    {
        Gson gson = new Gson();
        return gson.toJson(message);
    }

    public void setMessage(String message)
    {
        this.message = message;
    }



    //sends back a message whether or not clearing the DB worked

}

