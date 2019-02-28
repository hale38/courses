package model.tasks;

import android.os.AsyncTask;

import com.bignerdranch.android.fmapp.interfaces.LoginInterface;

import model.DataSingleton;
import model.ServerProxy;
import shared.requests.EventRequest;
import shared.results.EventResult;

/**
 * Created by Riley on 4/4/18.
 */

public class LoadEventsTask extends AsyncTask <Void,Void,EventResult>
{

	LoginInterface loginInterface;



	public void setLoginInterface(LoginInterface loginInterface)
	{
		this.loginInterface = loginInterface;
	}


	@Override
	protected EventResult doInBackground(Void... voids)
	{
		EventResult result;
		DataSingleton ds = DataSingleton.getInstance();
		ServerProxy sp = new ServerProxy(ds.getHost(),ds.getPort());
		EventRequest request = new EventRequest();
		request.setAuthToken(ds.getAuth());

		result = sp.getEvents(request);
		return result;
	}

	protected void onPostExecute(EventResult result)
	{
		DataSingleton ds = DataSingleton.getInstance();
		ds.loadAllEvents(result.getBody());

		loginInterface.loadMap();
	}
}
