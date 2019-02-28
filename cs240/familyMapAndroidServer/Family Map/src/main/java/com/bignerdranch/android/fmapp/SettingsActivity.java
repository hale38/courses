package com.bignerdranch.android.fmapp;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.bignerdranch.android.fmapp.interfaces.SettingsInterface;

import model.DataSingleton;
import model.tasks.ResyncTask;
import model.SettingSingleton;
import shared.results.EventResult;
import shared.results.PersonResult;

/**
 * Created by Riley on 4/6/18.
 */

public class SettingsActivity extends AppCompatActivity implements SettingsInterface
{
	Boolean startState = true;
	SettingSingleton ss;

	protected void onCreate(Bundle savedInstanceState)
	{
		ss = SettingSingleton.getInstance();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		View view = findViewById(R.id.settings_layout);

		Switch lifeStorySwitch = view.findViewById(R.id.settings_lifestory_switch);
		Switch familyTreeSwitch = view.findViewById(R.id.settings_familytree_switch);
		Switch spouseSwitch = view.findViewById(R.id.settings_spouse_switch);
		LinearLayout logout = view.findViewById(R.id.settings_logout_box);

		final Spinner mapSpinner = view.findViewById(R.id.settings_map_spinner);
		final Spinner lifeStoryspinner = view.findViewById(R.id.settings_lifestory_spinner);
		final Spinner familyTreeSpinner = view.findViewById(R.id.settings_familytree_spinner);
		final Spinner spouseLineSpinner = view.findViewById(R.id.settings_spouse_spinner);


		final LinearLayout reSync = view.findViewById(R.id.settings_data_box);
		final DataSingleton dataSingleton = DataSingleton.getInstance();


		lifeStorySwitch.setChecked(ss.getShowLifeStory());
		familyTreeSwitch.setChecked(ss.getShowFamilyTree());
		spouseSwitch.setChecked(ss.getShowSpouseLine());


		lifeStorySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b)
			{
				ss.setShowLifeStory(b);
			}
		});


		familyTreeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b)
			{
				ss.setShowFamilyTree(b);
			}
		});

		spouseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b)
			{
				ss.setShowSpouseLine(b);
			}
		});


		logout.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				//Context context =

				CharSequence text = "Good Bye";
				int duration = Toast.LENGTH_SHORT;

				Toast toast = Toast.makeText(getApplicationContext(), text, duration);
				toast.show();
				dataSingleton.clearData();
				finish();
			}
		});

		reSync.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				ResyncTask resyncTask = new ResyncTask();
				resyncTask.setInterace(SettingsActivity.this);
				resyncTask.execute();
			}
		});

		mapSpinner.setSelection(SettingSingleton.getInstance().getMapType());


		mapSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
			{
				SettingSingleton.getInstance().setMapType(i);
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView)
			{

			}
		});

		lifeStoryspinner.setSelection(SettingSingleton.getInstance().getLifeEventColorPos());

		lifeStoryspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
			{
				SettingSingleton.getInstance().setLifeStoryLineColor(i);
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView)
			{

			}
		});

		familyTreeSpinner.setSelection(SettingSingleton.getInstance().getFamTreeColorPos());
		familyTreeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
			{
				SettingSingleton.getInstance().setFamTreeLineColor(i);
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView)
			{

			}
		});


		spouseLineSpinner.setSelection(SettingSingleton.getInstance().getSpouseColorPos());
		spouseLineSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
			{
				SettingSingleton.getInstance().setSpouseLineColor(i);
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView)
			{

			}
		});
		//startState=false;
	}

	@Override
	public void onResume()
	{
		super.onResume();
		startState = true;
	}


	@Override
	public void reSyncSuccess(Pair pair)
	{
		PersonResult personResult = (PersonResult) pair.first;
		EventResult eventResult = (EventResult) pair.second;

		DataSingleton dataSingleton = DataSingleton.getInstance();

		dataSingleton.clearCache();

		//set all people and all events
		dataSingleton.loadPeople(personResult.getBody());
		dataSingleton.loadAllEvents(eventResult.getBody());


		CharSequence text = "Re-Sync Successful!";
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(getApplicationContext(), text, duration);
		toast.show();
		finish();

	}

	@Override
	public void reSyncFail(String message)
	{
		CharSequence text = "Re-Sync Failed";
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(getApplicationContext(), text, duration);
		toast.show();
	}
}
