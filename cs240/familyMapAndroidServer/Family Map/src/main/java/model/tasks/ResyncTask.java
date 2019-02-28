package model.tasks;

import android.os.AsyncTask;
import android.util.Pair;

import com.bignerdranch.android.fmapp.interfaces.SettingsInterface;

import model.DataSingleton;
import model.ServerProxy;
import shared.requests.EventRequest;
import shared.requests.PersonRequest;
import shared.results.EventResult;
import shared.results.PersonResult;

/**
 * Created by Riley on 4/13/18.
 */

public class ResyncTask extends AsyncTask<Void, Void, Pair>
{

	SettingsInterface settingsInterface;
	public void setInterace(SettingsInterface settingsInterface)
	{
		this.settingsInterface = settingsInterface;
	}



	@Override
	protected Pair doInBackground(Void... voids)
	{
		DataSingleton ds = DataSingleton.getInstance();

		ServerProxy sp = new ServerProxy(ds.getHost(), ds.getPort());


		//load all people info
		PersonRequest request = new PersonRequest();
		request.setAuthToken(ds.getAuth());
		PersonResult personResult = sp.getFamily(request);


		//load allEvents
		EventResult eventResult;
		EventRequest EventRequest = new EventRequest();
		EventRequest.setAuthToken(ds.getAuth());

		eventResult = sp.getEvents(EventRequest);

		return new Pair(personResult,eventResult);

	}


	@Override
	protected void onPostExecute(Pair pair)
	{
		PersonResult personResult= (PersonResult) pair.first;
		EventResult eventResult = (EventResult)pair.second;


		if(personResult==null||eventResult==null)
		{
			settingsInterface.reSyncFail("Failed to Re-Sync Data");

		}
		else if(personResult.getBody()==null ||eventResult.getBody()==null)
		{
			settingsInterface.reSyncFail("Failed to Re-Sync Data");
		}
		else if(personResult.getBody().length()<25 || eventResult.getBody().length()<25)
		{
			settingsInterface.reSyncFail("Failed to Re-Sync Data");
		}
		else
		{
			settingsInterface.reSyncSuccess(pair);
		}



	}
}
