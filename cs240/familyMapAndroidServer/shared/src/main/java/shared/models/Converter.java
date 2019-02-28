package shared.models;


import com.google.gson.Gson;

import shared.requests.LoginRequest;

/**
 * Created by Riley on 3/5/18.
 */


public class Converter
{
	Gson gson;



	public Converter() {
	gson = new Gson();
	};

	public String toJson(Object object)
	{
	String json = gson.toJson(object);
	return json;
	//return null;
	}

	public Person toPerson(String json)
	{

		return null;
	}

	public User toUser(String json)
	{
		User user = gson.fromJson(json,User.class);

		return user;
	}

	public Event toEvent(String json)
	{
		Event event = gson.fromJson(json,Event.class);
		return event;
	}



	public LoginRequest toLoginReq(String json)
	{
		LoginRequest loginRequest = gson.fromJson(json,LoginRequest.class);
		return loginRequest;
	}







}
