package services;

import com.google.gson.Gson;

import DAOs.AuthDAO;
import DAOs.EventDAO;
import DAOs.PersonDAO;
import DAOs.UserDAO;
import shared.models.Event;
import shared.models.Person;
import shared.models.User;
import shared.requests.LoadRequest;
import shared.results.LoadResult;

public class LoadService
{
    AuthDAO authD;
    EventDAO eventD;
    PersonDAO personD;
    UserDAO userD;

    public LoadService()
    {
        eventD = new EventDAO();
        personD = new PersonDAO();
        userD = new UserDAO();
    };



    //load database with given info

    public LoadResult takeRequest (LoadRequest request)
    {
        Gson gson = new Gson();
        LoadObj loadObj = gson.fromJson(request.getBody(), LoadObj.class);


        ClearService clearService = new ClearService();
        clearService.clearDB();

        userD.addMultipleUsers(loadObj.users);
        personD.addPersonList(loadObj.persons);
        eventD.addEventList(loadObj.events);


        LoadResult loadResult = new LoadResult();
        String message =     message = "Successfully added " + String.valueOf(loadObj.users.length) + " users, " +String.valueOf(loadObj.persons.length) + " persons, and "+ String.valueOf(loadObj.events.length)+ " events to the database.";
        loadResult.setResponse(message);

        return loadResult;
    }

    private class LoadObj
    {
        User[] users;
        Person[] persons;
        Event[] events;

        public LoadObj(){};


    }




}
