package services;

import java.util.TreeSet;

import DAOs.AuthDAO;
import DAOs.EventDAO;
import DAOs.PersonDAO;
import DAOs.UserDAO;
import shared.models.Converter;
import shared.models.Event;
import shared.requests.EventRequest;
import shared.results.EventResult;

public class EventService
{
    EventDAO eventD = new EventDAO();
    AuthDAO authDAO = new AuthDAO();
    Converter converter = new Converter();
    PersonDAO personDAO=new PersonDAO();
    UserDAO userDAO = new UserDAO();

    public EventService(){};

    //process request
    //returns appropriate response
    //Depending on how things go, I might add additional methods for each type of request
    public EventResult takeRequest(EventRequest eventRequest)
    {
        EventResult eventResult;

        if(authDAO.getPersonID(eventRequest.getAuthToken())!=null)
        {

            Event result = eventD.getEvent(eventRequest.getEventID());
            if(result == null)
            {
                eventResult = new EventResult();
                eventResult.setBody("{" + "\"Message\": " + "\"Event Doesn't Exist\""+"}");
                return eventResult;
            }

            if(result.getDescendent().equals(authDAO.getPersonID(eventRequest.getAuthToken())))
            {
                eventResult = new EventResult(converter.toJson(result));
            }
            else
            {
                eventResult = new EventResult();
                eventResult.setBody("{" + "\"Message\": " + "\"Invalid Auth Token\""+"}");
            }


        }
        else
        {
            eventResult=new EventResult();
            eventResult.setBody("{" + "\"Message\": " + "\"Invalid Auth Token\""+"}");
        }

        return eventResult;
    }

    public EventResult getAllevents(EventRequest eventRequest)
    {
        EventResult eventResult;

        if(authDAO.getPersonID(eventRequest.getAuthToken())!=null)
        {
            Event result = eventD.getEvent(eventRequest.getEventID());
            eventResult = new EventResult(converter.toJson(result));
            String userName= authDAO.getPersonID(eventRequest.getAuthToken());

            TreeSet<String>eventIDs= eventD.getEventsFromDescendant(userName);
            TreeSet<Event>events = eventD.getEvents(eventIDs);
            //eventResult.setEvents(new ArrayList<Event>(events));
            eventResult.setBody(converter.toJson(events));
        }
        else
        {
            eventResult=new EventResult();
            eventResult.setBody("{" + "\"Message\": " + "\"Invalid Auth Token\""+"}");
        }

        return eventResult;

    }




}
