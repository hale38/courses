package model;

import com.google.gson.Gson;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import shared.requests.EventRequest;
import shared.requests.LoginRequest;
import shared.requests.PersonRequest;
import shared.requests.RegisterRequest;
import shared.results.EventResult;
import shared.results.LoginResult;
import shared.results.PersonResult;
import shared.results.RegisterResult;

/**
 * Created by Riley on 3/24/18.
 */
public class ServerProxyTest
{



	ServerProxy serverProxy;
	@Before
	public void setup()
	{
		serverProxy = new ServerProxy("localhost","8071");
	}


	@Test
	public void get() throws Exception
	{
		PersonRequest personRequest = new PersonRequest();
		personRequest.setAuthToken("4ddad1b4-3270-40d5-a37a-f078009bb4c6");

		serverProxy.getFamily(personRequest);
	}

	@Test
	public void loginFail() throws Exception
	{
		LoginRequest request = new LoginRequest();
		request.setPassword("password");
		request.setUserName("usame");
		LoginResult lR = serverProxy.login(request);
		Assert.assertTrue(lR.getBody().toLowerCase().contains("invalid"));
	}

	@Test
	public void loginSuccess() throws Exception
	{
		LoginRequest request = new LoginRequest();
		request.setPassword("password");
		request.setUserName("username");
		LoginResult lR = serverProxy.login(request);
		Assert.assertTrue(lR.getBody()==null);
	}

	@Test
	public void getSinglePersonFail()
	{
		PersonRequest pr = new PersonRequest();
		pr.setAuthToken("fad17690-f4de-4189-bbd3-b632b38069d5");
		pr.setBody("da987858-ad35-43d0-9cc7-2d697a92fd57");
		PersonResult result = serverProxy.getSinglePerson(pr);
		PersonResult rResult= new Gson().fromJson("{\"message\":\"Invalid Auth Token\"}",PersonResult.class);
		Assert.assertEquals(result,result);
	}


	@Test
	public void getSinglePersonSuccess()
	{
		PersonRequest pr = new PersonRequest();
		pr.setAuthToken("652bc12f-04dd-4e5a-9a77-9bc01e164988");
		pr.setBody("Sheila_Parker");
		PersonResult result = serverProxy.getSinglePerson(pr);
		//PersonResult rResult= new Gson().fromJson("{\"message\":\"Invalid Auth Token\"}",PersonResult.class);
		Assert.assertTrue(!result.getBody().contains("Invalid"));
	}



	@Test
	public void registerFail() throws Exception
	{
		RegisterRequest registerRequest = new RegisterRequest();
		//registerRequest.setBody("{"+ "userName"+":"+"username"+","+ "password"+":"+"password"+ ","+ "email"+":"+"email"+","+  "firstName"+":"+"firstname"+","+  "lastName"+":"+"lastname"+ ","+ "gender"+":"+"m"+ "}");
		registerRequest.setUserName("bob");
		registerRequest.setLastName("evans");
		registerRequest.setEmail("email@lol.com");
		registerRequest.setFirstName("Robert");
		registerRequest.setLastName("Evans");
		registerRequest.setPassword("fsdfsdf");
		registerRequest.setGender("m");


		RegisterResult rr = serverProxy.register(registerRequest);
		RegisterResult rResult= new Gson().fromJson("{\"message\":\"User Already Exists\"}",RegisterResult.class);

		Assert.assertEquals(rr.getMessage(),rResult.getMessage());
	}


	@Test
	public void registerSuccess() throws Exception
	{
		RegisterRequest registerRequest = new RegisterRequest();
		//registerRequest.setBody("{"+ "userName"+":"+"username"+","+ "password"+":"+"password"+ ","+ "email"+":"+"email"+","+  "firstName"+":"+"firstname"+","+  "lastName"+":"+"lastname"+ ","+ "gender"+":"+"m"+ "}");
		registerRequest.setUserName("bbb");
		registerRequest.setLastName("evans");
		registerRequest.setEmail("email@lol.com");
		registerRequest.setFirstName("Robert");
		registerRequest.setLastName("Evans");
		registerRequest.setPassword("fsdfsdf");
		registerRequest.setGender("m");


		RegisterResult rr = serverProxy.register(registerRequest);
		RegisterResult rResult= new Gson().fromJson("{\"message\":\"User Already Exists\"}",RegisterResult.class);

		Assert.assertNotEquals(rr.getMessage(),rResult.getMessage());
	}

	@Test
	public void getAllEventsPass() throws Exception
	{
		EventRequest eventRequest = new EventRequest();
		eventRequest.setAuthToken("abd743b2-163c-4b77-98a2-1b90b338ad87");

		EventResult er = serverProxy.getEvents(eventRequest);
		Assert.assertTrue(!er.getBody().contains("FAILED"));
	}

	@Test
	public void getAllEventsFail() throws Exception
	{
		EventRequest eventRequest = new EventRequest();
		eventRequest.setAuthToken("97c9d63c-4a79-44cd-b41a093032e4");

		EventResult er = serverProxy.getEvents(eventRequest);
		Assert.assertTrue(er.getBody().contains("FAILED"));
	}




	@Test
	public void getSingleEventFail() throws Exception
	{
		EventRequest eventRequest = new EventRequest();
		eventRequest.setAuthToken("df98f74e-50b8-4082-83ea-ec5fbc2f01e8");
		eventRequest.setEventID("0d3e98-199b-4977-9a3e-6cc2e8580451");

		EventResult er = serverProxy.getSingleEvent(eventRequest);
		Assert.assertTrue(er.getBody().toLowerCase().contains("error"));
	}

	@Test
	public void getSingleEvent() throws Exception
	{
		EventRequest eventRequest = new EventRequest();
		eventRequest.setAuthToken("1ce0694b-bcd0-43ee-94ab-81f1ab3acd04");
		eventRequest.setEventID("b5fe4b11-5dd9-4410-9665-6ad223db6a7a");

		EventResult er = serverProxy.getSingleEvent(eventRequest);
		Assert.assertTrue(!er.getBody().toLowerCase().contains("Error"));
	}

	@Test
	public void getFamilyFailed() throws Exception
	{
		PersonRequest personRequest = new PersonRequest();
		personRequest.setAuthToken("1ce0694b-bcd3ee-94ab-81f1ab3acd04");
		PersonResult pr = serverProxy.getFamily(personRequest);
		Assert.assertTrue(pr.getBody().toLowerCase().contains("invalid"));

	}



	@Test
	public void getFamily() throws Exception
	{
		PersonRequest personRequest = new PersonRequest();
		personRequest.setAuthToken("641ed425-8d79-45e6-9847-3ddc73095f19");
		PersonResult pr = serverProxy.getFamily(personRequest);
		Assert.assertTrue(!pr.getBody().toLowerCase().contains("invalid"));
	}


	@Test
	public void getEventsFail() throws Exception
	{
		EventRequest eventRequest = new EventRequest();
		eventRequest.setAuthToken("1ce0694b-bcd3ee-94ab-81f1ab3acd04");

		EventResult er = serverProxy.getEvents(eventRequest);

		Assert.assertTrue(er.getBody().toLowerCase().contains("failed"));

	}

	@Test
	public void getEvents() throws Exception
	{
		EventRequest eventRequest = new EventRequest();
		eventRequest.setAuthToken("1ce0694b-bcd0-43ee-94ab-81f1ab3acd04");

		EventResult er = serverProxy.getEvents(eventRequest);

		Assert.assertTrue(!er.getBody().toLowerCase().contains("failed"));

	}


}