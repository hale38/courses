package model;

import com.google.gson.Gson;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import shared.models.Event;
import shared.models.Person;
import shared.requests.EventRequest;
import shared.requests.PersonRequest;
import shared.results.EventResult;
import shared.results.PersonResult;

/**
 * Created by Riley on 4/18/18.
 */
public class DataSingletonTest
{

	ServerProxy serverProxy;
	DataSingleton dataSingleton;
	Gson gson;
	@Before
	public void setup()
	{
		serverProxy = new ServerProxy("localhost","8071");
		gson = new Gson();
	}

	@Test
	public void loadPeople() throws Exception
	{
		dataSingleton = DataSingleton.getInstance();
		PersonRequest personRequest = new PersonRequest();
		personRequest.setAuthToken("106baa4d-f355-44e8-9265-bb6f23ae498b");

		PersonResult personResult = serverProxy.getFamily(personRequest);
		dataSingleton.loadPeople(personResult.getBody());


		Assert.assertEquals(15,dataSingleton.getPeople().length);


	}


	@Test
	public void loadAllEvents() throws Exception
	{
		dataSingleton = DataSingleton.getInstance();
		//need to load person Data
		PersonRequest personRequest = new PersonRequest();
		personRequest.setAuthToken("40d93345-54c8-46f5-a699-b196ebd7476f");

		PersonResult personResult = serverProxy.getFamily(personRequest);
		dataSingleton.loadPeople(personResult.getBody());


		dataSingleton.setPersonID("8f3e4fc8-bf98-4ba5-9305-56e68e400b21");

		EventRequest eRequest = new EventRequest();
		eRequest.setAuthToken("40d93345-54c8-46f5-a699-b196ebd7476f");

		EventResult eResult = serverProxy.getEvents(eRequest);
		dataSingleton.loadAllEvents(eResult.getBody());


		Assert.assertEquals(59,dataSingleton.getEvents().size());
	}

	@Test
	public void findEventByIDFail() throws Exception
	{
		loadData();
		Event e = DataSingleton.getInstance().findEventByID("Asdasd");
		Assert.assertEquals(null,e);
	}

	@Test
	public void findEventByID() throws Exception
	{
		loadData();
		Event e = DataSingleton.getInstance().findEventByID("551006f3-ab15-4086-af06-810c34d06743");
		Assert.assertEquals("canada",e.getCountry().toLowerCase());
	}

	@Test
	public void findPersonByIDFail() throws Exception
	{
		loadData();
		Person p = dataSingleton.findPersonByID("Asdasd");
		Assert.assertEquals(null,p);
	}

	@Test
	public void findPersonByID() throws Exception
	{
		loadData();
		Person p = dataSingleton.findPersonByID("116d9069-3ee3-4b8d-917e-a410e6023204");
		Assert.assertEquals("Jeffry",p.getFirstName());
	}

	@Test
	public void findChild() throws Exception
	{
		loadData();
		ArrayList<String> p =DataSingleton.getInstance().findChild("6f2993db-4160-4f4b-ba51-ad99d57bc269");
		Person kid = DataSingleton.getInstance().findPersonByID(p.get(0));

		Assert.assertEquals("firstname",kid.getFirstName());
	}

	@Test
	public void findChildFail() throws Exception
	{
		loadData();
		ArrayList<String> p =DataSingleton.getInstance().findChild("8f3e4fc8-bf98-4ba5-9305-56e68e400b21");


		Assert.assertEquals(0,p.size());
	}

	@Test
	public void searchData() throws Exception
	{
		loadData();
		dataSingleton = DataSingleton.getInstance();

		ArrayList<String> results = dataSingleton.searchData("bapt");

		Assert.assertEquals(15,results.size());
	}

	@Test
	public void searchDataFail() throws Exception
	{
		loadData();
		dataSingleton = DataSingleton.getInstance();

		ArrayList<String> results = dataSingleton.searchData("baptismx");

		Assert.assertNotEquals(15,results.size());
	}


	@Test
	public void setEventType() throws Exception
	{
		loadData();
		dataSingleton=DataSingleton.getInstance();
		int size = dataSingleton.filterMap.keySet().size();

		Assert.assertEquals(8,size);
	}

	@Test
	public void removeEventType() throws Exception
	{
		loadData();
		dataSingleton=DataSingleton.getInstance();

		dataSingleton.removeEventType("baptism");
		int size = dataSingleton.searchData("baptism").size();


		Assert.assertEquals(0, size);

	}

	private void loadData()
	{
		dataSingleton = DataSingleton.getInstance();
		//need to load person Data
		PersonRequest personRequest = new PersonRequest();
		personRequest.setAuthToken("40d93345-54c8-46f5-a699-b196ebd7476f");

		PersonResult personResult = serverProxy.getFamily(personRequest);
		dataSingleton.loadPeople(personResult.getBody());


		dataSingleton.setPersonID("8f3e4fc8-bf98-4ba5-9305-56e68e400b21");

		EventRequest eRequest = new EventRequest();
		eRequest.setAuthToken("40d93345-54c8-46f5-a699-b196ebd7476f");

		EventResult eResult = serverProxy.getEvents(eRequest);
		dataSingleton.loadAllEvents(eResult.getBody());

	}


}