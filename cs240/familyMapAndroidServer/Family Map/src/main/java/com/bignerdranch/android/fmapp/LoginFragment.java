package com.bignerdranch.android.fmapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bignerdranch.android.fmapp.interfaces.LoginInterface;

import model.DataSingleton;
import model.tasks.LoadEventsTask;
import model.tasks.LoadTask;
import model.tasks.LoginTask;
import model.tasks.RegisterTask;
import shared.models.Person;
import shared.models.User;
import shared.requests.LoginRequest;
import shared.requests.RegisterRequest;
import shared.results.LoginResult;
import shared.results.PersonResult;
import shared.results.RegisterResult;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginFragment extends Fragment implements LoginInterface
{
	private EditText serverHostField;
	private EditText serverPortField;
	private EditText userNameField;
	private EditText passwordField;
	private EditText firstNameField;
	private EditText lastNameField;
	private EditText emailField;
	private String serverHost = new String();
	private String serverPort = new String();
	private String userName = new String();
	private String password = new String();
	private String firstname = new String();
	private String lastName = new String();
	private String email = new String();
	private String gender = new String();


	User user;
	LoginRequest loginRequest;
	RegisterRequest registerRequest;


	public LoginFragment()
	{
		user = new User();
		loginRequest = new LoginRequest();
		registerRequest = new RegisterRequest();

	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{


		final View view = inflater.inflate(R.layout.login_fragment, container, false);
		final DataSingleton ds = DataSingleton.getInstance();
		final Button registerButton = (Button) view.findViewById(R.id.login_Register);

		registerButton.setEnabled(true);

		registerButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				ds.setHost(serverHost);
				ds.setPort(serverPort);
				ds.setUserName(userName);
				ds.setPassword(password);
				ds.setFirstName(firstname);
				ds.setLastName(lastName);
				ds.setEmail(email);
				ds.setGender(gender);
				RegisterTask registerTask = new RegisterTask();
				registerTask.setLoginInterface(LoginFragment.this);
				registerTask.execute();


			}
		});

		final Button loginButton = (Button) view.findViewById(R.id.login_login);
		loginButton.setEnabled(true);

		loginButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				ds.setHost(serverHost);
				ds.setPort(serverPort);
				ds.setUserName(userName);
				ds.setPassword(password);
				LoginTask loginTask = new LoginTask();
				loginTask.setLoginInterface(LoginFragment.this);
				loginTask.execute();
			}
		});

		final RadioButton male = (RadioButton) view.findViewById(R.id.login_male);
		final RadioButton female = (RadioButton) view.findViewById(R.id.login_female);

		male.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				female.setChecked(false);
				gender = "m";
			}
		});

		female.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				male.setChecked(false);
				gender = "f";
			}
		});


		serverHostField = (EditText) view.findViewById(R.id.login_serverHost);

		serverHostField.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
			{

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
			{
				serverHost = charSequence.toString();
				registerButton.setEnabled(allowRegister());
				loginButton.setEnabled(allowLogin());

			}

			@Override
			public void afterTextChanged(Editable editable)
			{

			}
		});

		serverPortField = (EditText) view.findViewById(R.id.login_serverPort);

		serverPortField.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
			{

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
			{
				serverPort = charSequence.toString();
				registerButton.setEnabled(allowRegister());
				loginButton.setEnabled(allowLogin());
			}

			@Override
			public void afterTextChanged(Editable editable)
			{

			}
		});

		userNameField = (EditText) view.findViewById(R.id.login_userName);

		userNameField.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
			{

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
			{
				userName = charSequence.toString();
				registerButton.setEnabled(allowRegister());
				loginButton.setEnabled(allowLogin());
				//System.out.println(charSequence.toString());
			}

			@Override
			public void afterTextChanged(Editable editable)
			{

			}
		});

		passwordField = (EditText) view.findViewById(R.id.login_password);

		passwordField.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
			{

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
			{
				password = charSequence.toString();
				registerButton.setEnabled(allowRegister());
				loginButton.setEnabled(allowLogin());
				//System.out.println(charSequence.toString());
			}

			@Override
			public void afterTextChanged(Editable editable)
			{

			}
		});


		firstNameField = (EditText) view.findViewById(R.id.login_firstName);

		firstNameField.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
			{

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
			{
				firstname = charSequence.toString();
				registerButton.setEnabled(allowRegister());
				loginButton.setEnabled(allowLogin());
				//System.out.println(charSequence.toString());
			}

			@Override
			public void afterTextChanged(Editable editable)
			{

			}
		});

		lastNameField = (EditText) view.findViewById(R.id.login_lastName);

		lastNameField.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
			{

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
			{
				lastName = charSequence.toString();
			}

			@Override
			public void afterTextChanged(Editable editable)
			{

			}
		});

		emailField = (EditText) view.findViewById(R.id.login_email);

		emailField.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
			{

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
			{
				email = charSequence.toString();
				registerButton.setEnabled(allowRegister());
				loginButton.setEnabled(allowLogin());
				//System.out.println(charSequence.toString());
			}

			@Override
			public void afterTextChanged(Editable editable)
			{

			}
		});
		return view;

	}

	private Boolean allowRegister()
	{
		if (serverPort.length() == 0) return false;
		if (serverHost.length() == 0) return false;
		if (userName.length() == 0) return false;
		if (firstname.length() == 0) return false;
		if (lastName.length() == 0) return false;
		if (email.length() == 0) return false;
		if (password.length() == 0) return false;
		if (gender.length() == 0) return false;

		return true;
	}

	private Boolean allowLogin()
	{
		if (serverHost.length() == 0) return false;
		if (serverPort.length() == 0) return false;
		if (userName.length() == 0) return false;
		if (password.length() == 0) return false;

		return true;
	}

	@Override
	public void loginSuccess(LoginResult result)
	{
		DataSingleton ds = DataSingleton.getInstance();

		//set user info
		ds.setUserName(result.getUserName());
		ds.setAuth(result.getAuth());
		ds.setPersonID(result.getPassWord());

		Context context = getContext();
		CharSequence text = "Successful Login";

		int duration = Toast.LENGTH_SHORT;


		Toast toast = Toast.makeText(context, text, duration);
		toast.show();

		LoadTask lt = new LoadTask();
		lt.setLoginInterface(LoginFragment.this);
		lt.execute();

	}

	@Override
	public void loginFail(LoginResult result)
	{
		Context context = getContext();
		CharSequence text = result.getBody();
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}

	@Override
	public void registerSuccess(RegisterResult result)
	{
		DataSingleton ds = DataSingleton.getInstance();


		Context context = getContext();
		CharSequence text = "User Successfully Registered";

		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();

		//set user info

		LoadTask lt = new LoadTask();
		lt.setLoginInterface(LoginFragment.this);
		ds.setUserName(result.getUserName());
		ds.setAuth(result.getAuthToken());

		lt.execute();

	}

	@Override
	public void registerFail(RegisterResult result)
	{

		Context context = getContext();
		CharSequence text;
		if (result == null)
		{
			text = "Register Failed";
		} else
		{
			text = result.getMessage();
		}


		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}

	@Override
	public void dataSyncSuccess(PersonResult result)
	{

		DataSingleton dataSingleton = DataSingleton.getInstance();
		dataSingleton.loadPeople(result.getBody());

		dataSingleton.login = true;

		loadUser();

		Context context = getContext();
		CharSequence text = "Welcome: " + dataSingleton.getFirstName() + " " + dataSingleton.getLastName();
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();


		LoadEventsTask loadEvents = new LoadEventsTask();
		loadEvents.setLoginInterface(LoginFragment.this);
		loadEvents.execute();


	}

	@Override
	public void loadMap()
	{
		MainActivity ma = (MainActivity) getActivity();
		ma.launchMapFragment();
	}


	@Override
	public void dataSyncFail(PersonResult result)
	{
		Context context = getContext();
		CharSequence text = "Unable to Sync Data";
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}

	private void loadUser()
	{
		DataSingleton dataSingleton = DataSingleton.getInstance();
		for (Person person : dataSingleton.getPeople())
		{
			if (person.getPersonId().equals(dataSingleton.getPersonID()))
			{
				dataSingleton.setFirstName(person.getFirstName());
				dataSingleton.setLastName(person.getLastName());
				return;
			}
		}
	}

}
