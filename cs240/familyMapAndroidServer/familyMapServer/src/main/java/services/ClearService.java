package services;

import DAOs.AuthDAO;
import DAOs.EventDAO;
import DAOs.PersonDAO;
import DAOs.UserDAO;
import shared.models.Converter;
import shared.results.ClearResult;

public class ClearService
{


    AuthDAO authD = new AuthDAO();
    EventDAO eventD = new EventDAO();
    PersonDAO personD = new PersonDAO();
    UserDAO userD = new UserDAO();


    public ClearService()
    {

    }

    public ClearResult clearDB()
    {
        String result="";
        ClearResult cr = new ClearResult();
        Converter converter = new Converter();
        try
        {
            authD.createTable();
            eventD.createTable();
            personD.createTable();
            userD.createTable();
        }
        catch (Exception e)
        {
            result=e.getMessage();
            cr.setMessage(result);
            return cr;
        }

        result = "Clear Succeeded";

        cr.setMessage(result);

        return cr;
    }

    //clear db, make create new tables

   /*public ClearResult takeRequest(ClearRequest request)
    {
        return null;
    }
    */

}
