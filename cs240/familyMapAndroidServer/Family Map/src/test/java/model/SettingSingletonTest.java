package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import shared.models.Event;

/**
 * Created by Riley on 4/18/18.
 */
public class SettingSingletonTest
{

	SettingSingleton settingSingleton;



	@Before
	public void setup()
	{
		settingSingleton=SettingSingleton.getInstance();
	}



	@Test
	public void sortEvents() throws Exception
	{

		ArrayList<Event> events = settingSingleton.sortEvents(makeArray());

		int result =Integer.valueOf(events.get(0).getYear());
		int expected = 22;
		Assert.assertEquals(expected,result);
	}

	@Test
	public void sortEventsOldest() throws Exception
	{

		ArrayList<Event> events = settingSingleton.sortEvents(makeArray());

		int result =Integer.valueOf(events.get(4).getYear());
		int expected = 2098;
		Assert.assertEquals(expected,result);
	}

	private ArrayList<Event>makeArray()
	{
		ArrayList<Event> events = new ArrayList();
		Event a = new Event();
		Event b = new Event();
		Event c = new Event();
		Event d = new Event();
		Event e = new Event();

		a.setYear("2098");
		b.setYear("1982");
		c.setYear("1817");
		d.setYear("022");
		e.setYear("2000");

		events.add(a);
		events.add(b);
		events.add(c);
		events.add(d);
		events.add(e);




		return events;
	}



}