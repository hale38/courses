package shared.requests;

import shared.results.ClearResult;

public class ClearRequest
{


    public ClearRequest()
    {

    };
    //no request body
    public ClearResult sendRequest()
    {
        return new ClearResult();
    }
}
