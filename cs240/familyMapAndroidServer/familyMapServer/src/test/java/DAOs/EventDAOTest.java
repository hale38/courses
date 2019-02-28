package DAOs;

import org.junit.Before;
import org.junit.Test;

import java.util.TreeSet;

import shared.models.Event;

import static org.junit.Assert.assertTrue;

/**
 * Created by Riley on 3/13/18.
 */
public class EventDAOTest
{
	EventDAO eventDAO;
	Event event1 =new Event();
	Event event2 = new Event();
	Event event3 = new Event();


	@Before
	public void setup()
	{
		eventDAO = new EventDAO();

		event1.setPersonID("bob");
		event1.setEventType("birth");

		event2.setPersonID("janet");
		event2.setEventType("death");



		event3.setPersonID("jackson");
		event3.setEventType("divorce");
	}


	@Test
	public void addSingleEvent() throws Exception
	{
		assertTrue(eventDAO.addSingleEvent(event1));
	}

	@Test
	public void addBadSingleEvent() throws Exception
	{
		Event badE= new Event();
		assertTrue(eventDAO.addSingleEvent(badE)==false);
	}

	@Test
	public void addEventList() throws Exception
	{
		Event [] events= new Event[] {event1,event2,event3};

		assertTrue(eventDAO.addEventList(events));
	}

	@Test
	public void removeSingleEvent() throws Exception
	{
	}

	@Test
	public void removeAllEvent() throws Exception
	{
		eventDAO.addSingleEvent(event1);
		eventDAO.removeAllEvent();

		assertTrue(eventDAO.getEvent(event1.getEventID())==null);
	}

	@Test
	public void getEvent() throws Exception
	{
		eventDAO.removeAllEvent();
		eventDAO.addSingleEvent(event1);

		Event result = eventDAO.getEvent(event1.getEventID());
		assertTrue(result.getEventID().equals(event1.getEventID()));
	}


	@Test
	public void getEventBad() throws Exception
	{
		eventDAO.removeAllEvent();
		eventDAO.addSingleEvent(event1);

		Event result = eventDAO.getEvent(event1.getEventID());
		assertTrue(result.getEventID().equals(event2.getEventID())==false);
	}

	@Test
	public void getEventPersonID() throws Exception
	{
		eventDAO.removeAllEvent();
		event1.setPersonID("bob");
		eventDAO.addSingleEvent(event1);
		Event result = eventDAO.getEventPersonID(event1.getPersonID());

		assertTrue(result.getEventID().equals(event1.getEventID()));
	}

	@Test
	public void getEvents() throws Exception
	{
		TreeSet<String> events = new TreeSet<>();

		Event[] add = new Event[] {event1,event2,event3};
		eventDAO.addEventList(add);

		events.add(event1.getEventID());
		events.add(event2.getEventID());
		events.add(event3.getEventID());




		TreeSet<Event> results = eventDAO.getEvents(events);
		assertTrue(results.size()==3);
	}

	@Test
	public void getEventsPersonID() throws Exception
	{
	}

	@Test
	public void getEventsFromDescendant() throws Exception
	{
	}

	@Test
	public void purgeDescendent() throws Exception
	{
	}

	@Test
	public void purgeUser() throws Exception
	{
	}

}