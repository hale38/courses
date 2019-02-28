package services;

import java.util.TreeSet;

import DAOs.AuthDAO;
import DAOs.EventDAO;
import DAOs.PersonDAO;
import DAOs.UserDAO;
import shared.models.Converter;
import shared.models.Person;
import shared.requests.PersonRequest;
import shared.results.PersonResult;

public class PersonService
{

    public PersonService(){};
    PersonDAO personD = new PersonDAO();
    UserDAO userDAO = new UserDAO();
    EventDAO eventDAO = new EventDAO();

    //process request
    //in a request and returns appropriate response
    //Depending on how things go, I might add additional methods for each type of request
    public PersonResult takeRequestSingle (PersonRequest request)
    {

        //check for valid auth
        AuthDAO authDAO = new AuthDAO();
        PersonResult personResult = new PersonResult();
        String pid = authDAO.getPersonID(request.getAuthToken());
        String userName = userDAO.getUser(pid).getUserName();

        Converter converter = new Converter();

        if(pid == null)
        {
            personResult.setBody("{" + "\"Message\": " + "\"Invalid Auth Token\""+"}");
            return personResult;
        }

        /*
        if(userName == null)
        {
            personResult.setBody("{" + "\"Message\": " + "\"Invalid personID\""+"}");
            return personResult;
        }
        */

        //get single person
        Person person = personD.getSinglePerson(request.getBody(), pid);


        if(person != null)
        {

            person.setEvents(eventDAO.getEventsPersonID(person.getPersonId()));
            personResult.setBody(converter.toJson(person));
        }
        else
        {
            personResult.setBody("{" + "\"Message\": " + "\"Invalid personID\""+"}");
        }






        return personResult;
    }

    public PersonResult getAllPersons(PersonRequest request)
    {
        AuthDAO authDAO = new AuthDAO();
        PersonDAO personDAO = new PersonDAO();
        PersonResult personResult = new PersonResult();
        Converter converter = new Converter();

        if(authDAO.getPersonID(request.getAuthToken())==null)
        {
            personResult.setBody("{" + "\"Message\": " + "\"Invalid Auth Token\""+"}");
            return personResult;
        }
        String pid = authDAO.getPersonID(request.getAuthToken());

        TreeSet<Person> persons = personDAO.getMultiplePerson(pid);

        personResult.setBody(converter.toJson(persons));




        return personResult;
    }





}
