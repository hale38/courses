package com.bignerdranch.android.fmapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import adapters.EventAdapter;
import adapters.PersonAdapter;
import model.DataSingleton;
import shared.models.Person;

/**
 * Created by Riley on 4/6/18.
 */

public class PersonActivity extends AppCompatActivity
{
	RecyclerView eventRecycle;
	RecyclerView familyRecycle;

	EventAdapter eventAdapter;
	PersonAdapter personAdapter;

	Person currentPerson;

	protected void onCreate(Bundle savedInstanceState)
	{

		DataSingleton ds = DataSingleton.getInstance();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person);

		TextView firstName = (TextView) findViewById(R.id.person_firstname);
		TextView lastName = (TextView) findViewById(R.id.person_lastname);
		TextView gender = (TextView) findViewById(R.id.person_gender);

		currentPerson = ds.findPersonByID(getIntent().getStringExtra("personID"));

		firstName.setText(currentPerson.getFirstName());
		;
		lastName.setText(currentPerson.getLastName());

		if (currentPerson.getGender().equals("M"))
		{
			gender.setText("Male");
		} else
		{
			gender.setText("Female");
		}

		eventRecycle = (RecyclerView) findViewById(R.id.person_event_recycle);
		familyRecycle = (RecyclerView) findViewById(R.id.person_family_recycle);

		eventRecycle.setVisibility(View.GONE);
		familyRecycle.setVisibility(View.GONE);

		eventRecycle.setLayoutManager(new LinearLayoutManager(this));
		familyRecycle.setLayoutManager(new LinearLayoutManager(this));

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);


		TextView personList = (TextView) findViewById(R.id.person_family_listener);
		TextView eventList = (TextView) findViewById(R.id.person_life_listener);


		personList.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				if (familyRecycle.getVisibility() == View.VISIBLE)
				{
					familyRecycle.setVisibility(View.GONE);
				} else
				{
					familyRecycle.setVisibility(View.VISIBLE);
				}
			}
		});

		eventList.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				if (eventRecycle.getVisibility() == View.VISIBLE)
				{
					eventRecycle.setVisibility(View.GONE);
				} else
				{
					eventRecycle.setVisibility(View.VISIBLE);
				}
			}
		});


		updateUI();
	}


	private void updateUI()
	{

		ArrayList<String> events = new ArrayList<>(currentPerson.getEvents());


		eventAdapter = new EventAdapter(this, events);
		eventRecycle.setAdapter(eventAdapter);

		ArrayList<Pair<String, String>> people = new ArrayList<>();

		String father = currentPerson.getFather();
		String spouse = currentPerson.getSpouse();
		String mother = currentPerson.getMother();


		if (father != null)
		{
			people.add(new Pair<String, String>("Father", father));
		}

		if (mother != null)
		{
			people.add(new Pair<String, String>("Mother", mother));
		}

		if (spouse != null)
		{
			people.add(new Pair<String, String>("Spouse", spouse));
		}

		ArrayList<String> children = DataSingleton.getInstance().findChild(currentPerson.getPersonId());


		for (String child : children)
		{
			people.add(new Pair<String, String>("Child", child));
		}

		personAdapter = new PersonAdapter(this, people);
		familyRecycle.setAdapter(personAdapter);

	}


}
