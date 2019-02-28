package model;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import shared.models.Converter;
import shared.models.Event;
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

public class ServerProxy
{

	static String host;
	static String port;

	public ServerProxy(String host, String port)
	{
		this.host = host;
		this.port = port;
	}


	public void post()
	{

	}

	public PersonResult getFamily(PersonRequest personRequest)
	{
		PersonResult personResult = new PersonResult();
		try
		{
			Gson gson = new Gson();
			URL url = new URL("http://" + host + ":" + port + "/person/");
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("POST");
			http.setDoOutput(true);

			//set http body
			//http.addRequestProperty("Accept", "application/json");
			http.setRequestProperty("Authorization", personRequest.getAuthToken());
			http.connect();
			OutputStream reqBody = http.getOutputStream();
			writeString(new Converter().toJson(personRequest), reqBody);
			reqBody.close();


			//http.addRequestProperty("Accept","application/json");


			if (http.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				InputStream respBody = http.getInputStream();


				Reader reader = new InputStreamReader(http.getInputStream());

				personResult.setBody(readString(respBody));

			} else
			{
				personResult.setBody("Cannot Connect to Server");
			}


		} catch (Exception e)
		{

		}

		return personResult;
	}

	public PersonResult getSinglePerson(PersonRequest personRequest)
	{
		PersonResult personResult = new PersonResult();
		try
		{
			Gson gson = new Gson();
			URL url = new URL("http://" + host + ":" + port + "/person/" + personRequest.getBody());
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("POST");
			http.setDoOutput(true);


			http.setRequestProperty("Authorization", personRequest.getAuthToken());
			http.connect();
			OutputStream reqBody = http.getOutputStream();
			writeString(new Converter().toJson(personRequest), reqBody);
			reqBody.close();

			if (http.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				InputStream respBody = http.getInputStream();
				Reader reader = new InputStreamReader(http.getInputStream());

				personResult.setBody(readString(respBody));
				System.out.print(personResult.getBody());
			} else
			{
				personResult.setBody("Cannot Connect to Server");
			}


		} catch (Exception e)
		{
			System.out.print(e.getCause());
		}

		return personResult;
	}

	public LoginResult login(LoginRequest loginRequest)
	{
		LoginResult loginResult = null;
		try
		{
			Gson gson = new Gson();
			URL url = new URL("http://" + host + ":" + port + "/user/login");
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("POST");
			http.setDoOutput(true);

			//set http body
			http.addRequestProperty("Accept", "application/json");

			http.connect();
			OutputStream reqBody = http.getOutputStream();
			writeString(new Converter().toJson(loginRequest), reqBody);
			reqBody.close();


			if (http.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				InputStream respBody = http.getInputStream();
				Reader reader = new InputStreamReader(http.getInputStream());
				loginResult = gson.fromJson(reader, LoginResult.class);

			}

		} catch (Exception e)
		{
			loginResult = new LoginResult();
			loginResult.setBody("FAILED TO CONNECT TO SERVER");
		}
		return loginResult;
	}

	public RegisterResult register(RegisterRequest registerRequest)
	{
		RegisterResult registerResult = null;
		try
		{
			Gson gson = new Gson();
			URL url = new URL("http://" + host + ":" + port + "/user/register");
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("POST");
			http.setDoOutput(true);

			//set http body
			http.addRequestProperty("Accept", "application/json");

			OutputStream reqBody = http.getOutputStream();
			writeString(new Converter().toJson(registerRequest), reqBody);
			reqBody.close();
			http.connect();

			if (http.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				InputStream respBody = http.getInputStream();
				Reader reader = new InputStreamReader(http.getInputStream());
				registerResult = gson.fromJson(reader, RegisterResult.class);

			}

		} catch (Exception e)
		{

			registerResult = new RegisterResult();
			registerResult.setMesage("FAILED TO CONNECT TO SERVER");
		}

		return registerResult;
	}


	//return every event for every person for a selected individual
	public EventResult getEvents(EventRequest eventRequest)
	{
		EventResult eventResult = null;
		Event[] events;
		try
		{
			Gson gson = new Gson();
			URL url = new URL("http://" + host + ":" + port + "/event/");
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("POST");
			http.setRequestProperty("Authorization", eventRequest.getAuthToken());
			http.setDoOutput(true);

			http.connect();

			if (http.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				InputStream respBody = http.getInputStream();
				Reader reader = new InputStreamReader(http.getInputStream());
				events = gson.fromJson(reader, Event[].class);
				eventResult = new EventResult(gson.toJson(events));

			}

		} catch (Exception e)
		{

			eventResult = new EventResult();
			eventResult.setBody("FAILED TO CONNECT TO SERVER");
		}

		return eventResult;
	}


	//returns a single event
	public EventResult getSingleEvent(EventRequest eventRequest)
	{
		EventResult eventResult = null;
		try
		{
			Gson gson = new Gson();
			URL url = new URL("http://" + host + ":" + port + "/event/" + eventRequest.getEventID());
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("POST");
			http.setDoOutput(true);


			http.addRequestProperty("Accept", "application/json");
			http.setRequestProperty("Authorization", eventRequest.getAuthToken());
			http.connect();

			if (http.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				InputStream respBody = http.getInputStream();


				Reader reader = new InputStreamReader(http.getInputStream());
				Event event = gson.fromJson(reader, Event.class);

				if (event.getDescendent() == null)
				{
					eventResult = new EventResult();
					eventResult.setBody("Error Retrieving Event");
				} else
				{
					eventResult = new EventResult(gson.toJson(event));
				}

			}

		} catch (Exception e)
		{
			eventResult = new EventResult();
			eventResult.setBody("FAILED TO CONNECT TO SERVER");
		}

		return eventResult;
	}

	private static String readString(InputStream is) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		InputStreamReader sr = new InputStreamReader(is);
		char[] buf = new char[1024];
		int len;
		while ((len = sr.read(buf)) > 0)
		{
			sb.append(buf, 0, len);
		}
		return sb.toString();
	}

	private static void writeString(String str, OutputStream os) throws IOException
	{
		OutputStreamWriter sw = new OutputStreamWriter(os);
		sw.write(str);
		sw.flush();
	}


}
