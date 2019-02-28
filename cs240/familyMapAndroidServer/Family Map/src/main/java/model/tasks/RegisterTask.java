package model.tasks;

import android.os.AsyncTask;

import com.bignerdranch.android.fmapp.interfaces.LoginInterface;

import model.DataSingleton;
import model.ServerProxy;
import shared.requests.RegisterRequest;
import shared.results.RegisterResult;

/**
 * Created by Riley on 3/24/18.
 */

public class RegisterTask extends AsyncTask<Void,Void,RegisterResult>
{
	LoginInterface loginInterface;
	public void setLoginInterface(LoginInterface loginInterface)
	{
		this.loginInterface = loginInterface;
	}

	@Override
	protected RegisterResult doInBackground(Void... voids)
	{
		DataSingleton ds = DataSingleton.getInstance();
		ServerProxy serverProxy = new ServerProxy(ds.getHost(),ds.getPort());

		RegisterRequest registerRequest = new RegisterRequest();
		registerRequest.setUserName(ds.getUserName());
		registerRequest.setGender(ds.getGender());
		registerRequest.setPassword(ds.getPassword());
		registerRequest.setLastName(ds.getLastName());
		registerRequest.setFirstName(ds.getFirstName());
		registerRequest.setEmail(ds.getEmail());

		RegisterResult result = serverProxy.register(registerRequest);
		return result;
	}

	@Override
	protected void onPostExecute(RegisterResult result)
	{
		if(result==null)
		{
			loginInterface.registerFail(result);
		}
		if(result.getUserName()==null)
		{
			loginInterface.registerFail(result);
		}
		else
		{
			loginInterface.registerSuccess(result);
		}
	}


}
