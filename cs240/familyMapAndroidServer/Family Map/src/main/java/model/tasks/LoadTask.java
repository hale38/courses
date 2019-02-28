package model.tasks;

import android.os.AsyncTask;

import com.bignerdranch.android.fmapp.interfaces.LoginInterface;

import model.DataSingleton;
import model.ServerProxy;
import shared.requests.PersonRequest;
import shared.results.PersonResult;

/**
 * Created by Riley on 3/27/18.
 */

public class LoadTask extends AsyncTask<Void,Void,PersonResult>
{
	LoginInterface loginInterface;

	public void setLoginInterface(LoginInterface loginInterface)
	{
		this.loginInterface = loginInterface;
	}

	@Override
	protected PersonResult doInBackground(Void... Objects)
	{
		DataSingleton ds = DataSingleton.getInstance();
		ServerProxy sp = new ServerProxy(ds.getHost(),ds.getPort());

		PersonRequest request = new PersonRequest();
		request.setAuthToken(ds.getAuth());

		PersonResult personResult = sp.getFamily(request);


		return personResult;
	}

	@Override
	protected void onPostExecute(PersonResult result)
	{
		if(result.getBody().length()<25)
		{
			loginInterface.dataSyncFail(result);
		}
		else
		{
			loginInterface.dataSyncSuccess(result);
		}
	}

}
