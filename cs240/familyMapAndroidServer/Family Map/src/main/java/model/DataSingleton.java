package model;

import android.util.Pair;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import shared.models.Event;
import shared.models.Person;

/**
 * Created by Riley on 3/24/18.
 */

public class DataSingleton
{
	private static DataSingleton fmapSingleton;
	private String host;


	private String port;
	private String auth;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String gender;
	private String email;
	private String personID;
	public Boolean login = false;
	private Person[] people = new Person[]{};
	private HashMap<String, Integer> colorMap = new HashMap<>();
	TreeSet<String> filters = new TreeSet<>();


	private Person currentPerson;
	private Event currentEvent;


	private ArrayList<Event> events;
	private ArrayList<Event> masterEvents;
	ArrayList<String> fatherSideEvents;
	ArrayList<String> motherSideEvents;




	private DataSingleton()
	{
	}

	public static DataSingleton getInstance()
	{
		if (fmapSingleton == null)
		{
			fmapSingleton = new DataSingleton();
		}
		return fmapSingleton;
	}


	public void loadPeople(String json)
	{
		people = new Gson().fromJson(json, Person[].class);
	}

	public void loadAllEvents(String json)
	{
		Event[] tempevents = new Gson().fromJson(json, Event[].class);
		events = new ArrayList<Event>(Arrays.asList(tempevents));
		masterEvents = new ArrayList<>(events);
		initializeFilters();
		setEventTypes();
	}

	public void setEventTypes()
	{
		TreeSet<String> eventTypes = new TreeSet<>();
		for (Event event : masterEvents)
		{
			eventTypes.add(event.getEventType().toLowerCase());
		}

		ArrayList<String> eventArray = new ArrayList<>(eventTypes);

		for (int i = 0; i < eventTypes.size(); i++)
		{
			colorMap.put(eventArray.get(i), i * (360 / eventTypes.size()));
		}


	}


	public Event findEventByID(String id)
	{
		for (Event event : events)
		{
			if (event.getEventID().equals(id))
			{
				return event;
			}

		}

		return null;
	}

	public Person findPersonByID(String id)
	{
		for (Person person : people)
		{
			if (person.getPersonId().equals(id))
			{
				return person;
			}
		}
		return null;
	}


//finds all of the event types, and populates and loads all of the filter info

	ArrayList<Filter> filterInfo = new ArrayList<>();

	public void setFilterTypes()
	{
		TreeSet<String> filters = new TreeSet<>();
		//events = masterEvents;


		//populate filters with info and description
		if (masterEvents != null)
		{

			filterInfo = new ArrayList<>();
			for (Event event : masterEvents)
			{

				if (filters.add(event.getEventType().toLowerCase()))
				{
					Filter filter = new Filter(event.getEventType(), false);
					filter.description = "FILTER BY " + event.getEventType().toUpperCase() + " EVENTS";
					filterInfo.add(filter);
					filters.add(event.getEventType());
				}

			}
		}

		Filter father = new Filter("Father's Side", false);
		father.description = "FILTER BY FATHERS SIDE OF FAMILY";
		filterInfo.add(father);

		Filter mother = new Filter("Mother's Side", false);
		mother.description = "FILTER BY MOTHERS SIDE OF FAMILY";
		filterInfo.add(mother);

		Filter genderM = new Filter("Male Events", false);
		genderM.description = "FILTER EVENTS BASED ON GENDER";
		filterInfo.add(genderM);

		Filter genderF = new Filter("Female Events", false);
		genderF.description = "FILTER EVENTS BASED ON GENDER";
		filterInfo.add(genderF);
	}


	public boolean getFilterStatus(String type)
	{
		for (Filter filter : filterInfo)
		{
			if (filter.filter.equals(type))
			{
				return filter.state;
			}
		}

		return false;
	}


	public void setFilterStatus(String type, Boolean status)
	{
		for (Filter filter : filterInfo)
		{
			if (filter.filter.equals(type))
			{
				filter.state = status;
			}
		}
	}

	//return a list of pairs of filter and description

	public List<Pair<String, String>> getAllFilterInfo()
	{
		List<Pair<String, String>> filterTypes = new ArrayList<>();
		if (filterInfo != null)
		{
			for (Filter filter : filterInfo)
			{
				filterTypes.add(new Pair<String, String>(filter.filter, filter.description));
			}
		}
		return filterTypes;
	}


	//add and remove filters from the tree set
	public void addFilter(String filter)
	{
		filters.add(filter);
	}

	public void removeFilter(String filter)
	{
		filters.remove(filter);
	}

	//stores filter and its state
	private class Filter
	{
		String filter;
		Boolean state;
		String description;

		private Filter(String filter, Boolean state)
		{
			this.filter = filter;
			this.state = state;
		}

	}

	private ArrayList<Event> familyEventList;


	//returns a list of all event IDs associted with a persons line

	//grabs all events for a person and his/her family


	public ArrayList<String> findChild(String parentID)
	{
		ArrayList<String> children = new ArrayList<>();

		for (Person person : people)
		{

			if (person.getMother() != null && person.getMother().equals(parentID))
			{
				children.add(person.getPersonId());
			} else if (person.getFather() != null && person.getFather().equals(parentID))
			{
				children.add(person.getPersonId());
			}
		}
		return children;
	}


	ArrayList<String> searchResults = new ArrayList<>();

	public ArrayList<String> searchData(String param)
	{
		searchResults = new ArrayList<>();
		for (Person person : people)
		{
			if (person.contains(param))
			{
				searchResults.add(person.getPersonId());
			} else
			{
				if (searchResults.contains(person.getPersonId()))
				{
					searchResults.remove(person.getPersonId());
				}
			}
		}

		for (Event event : events)
		{
			if (event.contains(param))
			{
				searchResults.add(event.getEventID());
			} else
			{
				if (searchResults.contains(event.getEventID()))
				{
					searchResults.remove(event.getEventID());
				}
			}
		}

		return searchResults;
	}


	//newly rebuilt filter
	HashMap<String, TreeSet<Event>> filterMap = new HashMap<>();

	public void initializeFilters()
	{
		ArrayList<Event> events = masterEvents;
		setFilterTypes();
		filterMap.put("male events", new TreeSet<Event>());
		filterMap.put("female events", new TreeSet<Event>());


		filterMap.put("mother's side", new TreeSet<Event>(filterFamily(findPersonByID(personID).getMother())));
		filterMap.put("father's side", new TreeSet<Event>(filterFamily(findPersonByID(personID).getFather())));


		for (Event e : events)
		{
			//if event type is not already in hashmap add it to the hash map;
			if (!filterMap.containsKey(e.getEventType().toLowerCase()))
			{
				filterMap.put(e.getEventType().toLowerCase(), new TreeSet<Event>());
			}
			{
				filterMap.get(e.getEventType().toLowerCase()).add(e);
			}


			Person person = findPersonByID(e.getPersonID());
			if (person.getGender().toLowerCase().equals("m"))
			{
				filterMap.get("male events").add(e);
			} else
			{
				filterMap.get("female events").add(e);
			}

		}

	}

	public ArrayList<Event> filterFamily(String personID)
	{
		familyEventList = new ArrayList<>();
		Person branch = findPersonByID(personID);

		for (String event : findPersonByID(personID).getEvents())
		{
			familyEventList.add(findEventByID(event));
		}


		filterFamilyRecurse(findPersonByID(personID));
		return new ArrayList<>(familyEventList);
	}


	private void filterFamilyRecurse(Person person)
	{
		if (person.getFather() == null || person.getMother() == null)
		{
			return;
		} else
		{
			Person mother = findPersonByID(person.getMother());
			for (String event : mother.getEvents())
			{
				familyEventList.add(findEventByID(event));
			}

			filterFamilyRecurse(mother);

			Person father = findPersonByID(person.getFather());
			for (String event : father.getEvents())
			{
				familyEventList.add(findEventByID(event));
			}
			filterFamilyRecurse(father);
		}
	}


	public void removeEventType(String type)
	{

		ArrayList<Event> eventsTemp = new ArrayList<>();
		TreeSet<Event> removeEvents = filterMap.get(type.toLowerCase());

		for (Event e : removeEvents)
		{
			events.remove(e);
		}
		//events.removeAll(eventsTemp);

	}

	public void addEventType(String type)
	{
		events.addAll(new ArrayList<Event>(filterMap.get(type.toLowerCase())));
	}

	public void clearData()
	{
		host = new String();
		port = new String();
		auth = new String();
		userName = new String();
		password = new String();
		firstName = new String();
		lastName = new String();
		gender = new String();
		email = new String();
		login = false;
		filters = new TreeSet<>();
		events = new ArrayList<>();
		masterEvents = new ArrayList<>();
		people = new Person[]{};
		filterMap = new HashMap<>();
		SettingSingleton.getInstance().reset();
	}

	public void clearCache()
	{
		filters = new TreeSet<>();
		events = new ArrayList<>();
		masterEvents = new ArrayList<>();
		fatherSideEvents = new ArrayList<>();
		motherSideEvents = new ArrayList<>();
		people = new Person[]{};

	}

	public int getColor(String key)
	{
		return colorMap.get(key.toLowerCase());
	}


	public Person getCurrentPerson()
	{
		return currentPerson;
	}

	public void setCurrentPerson(Person currentPerson)
	{
		this.currentPerson = currentPerson;
	}

	public Event getCurrentEvent()
	{
		return currentEvent;
	}

	public void setCurrentEvent(Event currentEvent)
	{
		this.currentEvent = currentEvent;
	}

	public String getPersonID()
	{
		return personID;
	}

	public void setPersonID(String personID)
	{
		this.personID = personID;
	}

	public String getHost()
	{
		return host;
	}

	public void setHost(String host)
	{
		this.host = host;
	}

	public String getPort()
	{
		return port;
	}

	public void setPort(String port)
	{
		this.port = port;
	}

	public String getAuth()
	{
		return auth;
	}

	public void setAuth(String auth)
	{
		this.auth = auth;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public void resetEvents()
	{
		events = new ArrayList<>(masterEvents);
	}

	public ArrayList<Event> getEvents()
	{
		return events;
	}
	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getGender()
	{
		return gender;
	}

	public void setGender(String gender)
	{
		this.gender = gender.toUpperCase();
	}

	public void setEmail(String email) {this.email = email;}

	public String getEmail() {return email;}

	public Person[] getPeople()
	{
		return people;
	}


}
