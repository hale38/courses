package model.tasks;

import android.os.AsyncTask;

import com.bignerdranch.android.fmapp.interfaces.LoginInterface;

import model.DataSingleton;
import model.ServerProxy;
import shared.requests.LoginRequest;
import shared.results.LoginResult;

/**
 * Created by Riley on 3/24/18.
 */

public class LoginTask extends AsyncTask<Void, Void, LoginResult>
{
	public void setLoginInterface(LoginInterface loginInterface)
	{
		this.loginInterface = loginInterface;
	}

	LoginInterface loginInterface;
	@Override
	protected LoginResult doInBackground(Void... objects)
	{

		DataSingleton ds = DataSingleton.getInstance();
		ServerProxy serverProxy = new ServerProxy(ds.getHost(),ds.getPort());
		LoginRequest request = new LoginRequest();



		request.setUserName(ds.getUserName());
		request.setPassword(ds.getPassword());




		LoginResult result = serverProxy.login(request);


		return result;

	}

	@Override
	protected void onPostExecute(LoginResult result)
	{
		if(result.getAuth()==null)
		{
			loginInterface.loginFail(result);
		}
		else
		{
			loginInterface.loginSuccess(result);
		}

	}
}
