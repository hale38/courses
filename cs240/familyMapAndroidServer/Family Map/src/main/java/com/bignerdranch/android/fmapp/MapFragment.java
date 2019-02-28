package com.bignerdranch.android.fmapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import model.DataSingleton;
import model.SettingSingleton;
import shared.models.Event;
import shared.models.Person;

/**
 * Created by Riley on 3/28/18.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback
{
	public MapFragment(){}

	private Event currentEvent;
	private Person currentPerson;
	private SettingSingleton settings = SettingSingleton.getInstance();
	private DataSingleton ds = DataSingleton.getInstance();

	private Polyline spouseLine;
	private ArrayList<Polyline> familyLine = new ArrayList<>();
	private ArrayList<Polyline> lifeStoryLine = new ArrayList<>();
	private  GoogleMap mMap;

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		settings = SettingSingleton.getInstance();
		final View view = inflater.inflate(R.layout.map_fragment, container, false);

		super.onCreate(savedInstanceState);

		SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);

		if(getActivity() instanceof MainActivity)
		{
			Toolbar toolbar =   view.findViewById(R.id.toolbar);

			//getActivity().setActionBar(toolbar);
			((MainActivity)getActivity()).setSupportActionBar(toolbar);
			setHasOptionsMenu(true);

		}

		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater)
	{
		menuInflater.inflate(R.menu.menu_main,menu);
		super.onCreateOptionsMenu(menu,menuInflater);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.toolbar_filter:

				Intent filter_intent = new Intent(getActivity(),FilterActivity.class);
				startActivity(filter_intent);

				return true;
			case R.id.toolbar_search:
				Intent search_intent = new Intent(getActivity(),SearchActivity.class);
				startActivity(search_intent);

				return true;
			case R.id.toolbar_settings:


				Intent settings_intent = new Intent(getActivity(),SettingsActivity.class);
				startActivity(settings_intent);
				return true;

		}
		return true;
	}


	//get child fragment manager
	public void onMapReady(GoogleMap googleMap)
	{

		mMap = googleMap;

		final View view = getView();
		setHasOptionsMenu(true);

		mMap.setMapType(SettingSingleton.getInstance().getMapType()+1);


		final LinearLayout eventBox = (LinearLayout) view.findViewById(R.id.event_info_box);

		setMarkers(mMap,view);


		if(getActivity() instanceof EventActivity)
		{
			setHasOptionsMenu(false);
			currentEvent = ds.findEventByID(((EventActivity)getActivity()).currentEvent);
			currentPerson = ds.findPersonByID(currentEvent.getPersonID());

			drawSpouseLine(mMap,currentEvent);
			drawLifeStory(mMap,currentEvent);
			drawFamilyTree(mMap,currentEvent);

			LatLng pos = new LatLng(currentEvent.getLatitude(),currentEvent.getLongitude());
			mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));


			//remove toolbar
			Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);
			toolbar.setVisibility(View.GONE);



			TextView personName = (TextView)view.findViewById(R.id.event_Person);
			personName.setText(currentPerson.getFirstName()+" "+currentPerson.getLastName());



			TextView eventDetails = (TextView)view.findViewById(R.id.event_Details);
			eventDetails.setText(currentEvent.getEventType()+": "+currentEvent.getYear());

			TextView location = (TextView)view.findViewById(R.id.event_Location);
			location.setText(currentEvent.getCity()+", "+currentEvent.getCountry());

			ImageView genderIcon = (ImageView)view.findViewById(R.id.event_genderView);

			if(currentPerson.getGender().toLowerCase().equals("m"))
			{
				genderIcon.setImageResource(R.mipmap.gicon_male);
			}
			else
			{
				genderIcon.setImageResource(R.mipmap.gicon_female);
			}



		}
		else
		{

		}


		//start person event
		eventBox.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{

				if(currentPerson!=null && DataSingleton.getInstance().findEventByID(currentEvent.getEventID())!=null)
				{
					Intent person_intent = new Intent(getActivity(), PersonActivity.class);
					person_intent.putExtra("personID", currentPerson.getPersonId());

					startActivity(person_intent);
				}

			}
		});


	}

	@Override
	public void onResume()
	{
		super.onResume();
		if(mMap!=null)
		{
			mMap.setMapType(SettingSingleton.getInstance().getMapType()+1);
			setMarkers(mMap,getView());
			currentPerson=settings.getRootPerson();
			currentEvent=settings.getRootEvent();

			if(currentEvent!=null && DataSingleton.getInstance().findEventByID(currentEvent.getEventID())!=null)
			{
				drawSpouseLine(mMap,currentEvent);
				drawLifeStory(mMap,currentEvent);
				drawFamilyTree(mMap,currentEvent);
			}

			if(currentEvent!=null&&DataSingleton.getInstance().findEventByID(currentEvent.getEventID())==null)
			{


				TextView personName = (TextView)getView().findViewById(R.id.event_Person);
				personName.setText(currentPerson.getFirstName()+" "+currentPerson.getLastName());

				personName.setText("");

				TextView eventDetails = (TextView)getView().findViewById(R.id.event_Details);
				eventDetails.setText(currentEvent.getEventType()+": "+currentEvent.getYear());
				eventDetails.setText("");

				TextView location = (TextView)getView().findViewById(R.id.event_Location);
				location.setText(currentEvent.getCity()+", "+currentEvent.getCountry());
				location.setText("");

				ImageView genderIcon = (ImageView)getView().findViewById(R.id.event_genderView);
				genderIcon.setVisibility(View.INVISIBLE);
			}
			else
			{
				ImageView genderIcon = (ImageView) getView().findViewById(R.id.event_genderView);
				genderIcon.setVisibility(View.VISIBLE);
			}



		}


	}

	private void setMarkers(final GoogleMap mMap, final View view)
	{
		mMap.clear();
		DataSingleton ds = DataSingleton.getInstance();
		if( ds.getEvents() != null)
		{for (Event event : ds.getEvents())
			{
				LatLng pos = new LatLng(event.getLatitude(), event.getLongitude());
				MarkerOptions mo  = new MarkerOptions().position(pos).title(event.getEventID());

				mo.icon(BitmapDescriptorFactory.defaultMarker((float)ds.getColor(event.getEventType().toLowerCase())));
				mMap.addMarker(mo);
			}
		}

		mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
		{
			@Override
			public boolean onMarkerClick(Marker marker)
			{



				DataSingleton ds = DataSingleton.getInstance();
				Event event =ds.findEventByID(marker.getTitle());




				TextView personName = (TextView)view.findViewById(R.id.event_Person);
				Person person = ds.findPersonByID(event.getPersonID());
				personName.setText(person.getFirstName()+" "+person.getLastName());



				currentEvent = event;
				currentPerson = person;
				settings.setRootPerson(currentPerson);
				settings.setRootEvent(event);

				/*
				if(getActivity() instanceof MainActivity)
				{
					((MainActivity) getActivity()).anchor=person;
				}*/

				TextView eventDetails = (TextView)view.findViewById(R.id.event_Details);
				eventDetails.setText(event.getEventType()+": "+event.getYear());

				TextView location = (TextView)view.findViewById(R.id.event_Location);
				location.setText(event.getCity()+", "+event.getCountry());

				ImageView genderIcon = (ImageView)view.findViewById(R.id.event_genderView);
				genderIcon.setVisibility(View.VISIBLE);

				if (person.getGender().toLowerCase().equals("m"))
				{
					genderIcon.setImageResource(R.mipmap.gicon_male);
				}
				else
				{
					genderIcon.setImageResource(R.mipmap.gicon_female);
				}


				drawSpouseLine(mMap,event);
				drawLifeStory(mMap,event);
				drawFamilyTree(mMap,event);

				return true;
			}
		});




	}


	public void drawLifeStory(GoogleMap mMap, Event rootEvent)
	{
		if(settings.getShowLifeStory()==true)
		{
			if(lifeStoryLine !=null)
			{
				for(Polyline p : lifeStoryLine)
				{
					p.remove();
				}
			}

			Person person = ds.findPersonByID(rootEvent.getPersonID());
			ArrayList<Event>events = getEvents(person);

			ArrayList<Event>eventSorted=settings.sortEvents(events);


			for (int i =0; i<eventSorted.size()-1; i++)
			{
				LatLng source = new LatLng(eventSorted.get(i).getLatitude(),eventSorted.get(i).getLongitude());
				LatLng dest = new LatLng(eventSorted.get(i+1).getLatitude(),eventSorted.get(i+1).getLongitude());
				Polyline pl = mMap.addPolyline(new PolylineOptions().add(source,dest));
				pl.setColor(settings.getLifeStoryLineColor());
				lifeStoryLine.add(pl);
			}


		}

	}


	public void drawSpouseLine(GoogleMap mMap, Event rootEvent)
	{

		if(rootEvent==null)return;
		if (settings.getShowSpouseLine() == true)
		{
			if(spouseLine !=null)
			{
				spouseLine.remove();
			}

			Person person = ds.findPersonByID(rootEvent.getPersonID());
			if(person.getSpouse()!=null)
			{

				Person spouse = ds.findPersonByID(person.getSpouse());
				ArrayList<Event> events = getEvents(spouse);


				if(events.size()!=0)
				{
					LatLng source = new LatLng(rootEvent.getLatitude(),rootEvent.getLongitude());
					LatLng dest = settings.getEarliest(events,ds.getEvents());

					if(dest!=null)
					{
						spouseLine = mMap.addPolyline(new PolylineOptions().add(source, dest));
						spouseLine.setColor(settings.getSpouseLineColor());
						//settings.setSpouseLine(source, dest);
					}
				}

			}
		}

	}


	private  Integer generation;
	private void drawFamilyTree(GoogleMap mMap, Event rootEvent)
	{
		if(rootEvent == null) return;
		if(settings.getShowFamilyTree()==true)
		{

			if(familyLine !=null)
			{
				for (Polyline polyline : familyLine)
				{
					polyline.remove();
				}
			}

			generation=1;
			Person person = ds.findPersonByID(rootEvent.getPersonID());
			plotFamilyTreeRecurse(mMap, person, rootEvent);
		}

	}


	float polyLineWidth = 6;



	private void plotFamilyTreeRecurse(GoogleMap mMap, Person person, Event event)
	{
		if(person==null)return;

		if(person.getFather()!=null)
		{
			Person father = ds.findPersonByID(person.getFather());

			ArrayList<Event> events = getEvents(father);


			if(events.size()!=0)
			{
				LatLng source = new LatLng(event.getLatitude(),event.getLongitude());
				LatLng dest = settings.getEarliest(events,ds.getEvents());

				if(dest!=null)
				{
					Polyline polyline = mMap.addPolyline(new PolylineOptions().add(source, dest).width(polyLineWidth/generation));
					polyline.setColor(settings.getFamTreeLineColor());
					familyLine.add(polyline);
				}

				generation++;
				plotFamilyTreeRecurse(mMap,father,events.get(0));
				generation--;
			}
			else
			{
				generation++;
				plotFamilyTreeRecurse(mMap,father,event);
				generation--;
			}

		}

		if(person.getMother()!=null)
		{
			Person mother = ds.findPersonByID(person.getMother());


			ArrayList<Event> events = getEvents(mother);
			if(events.size()!=0)
			{
				LatLng source = new LatLng(event.getLatitude(),event.getLongitude());
				LatLng dest = settings.getEarliest(events,ds.getEvents());

				if(dest!=null)
				{
					Polyline polyline = mMap.addPolyline(new PolylineOptions().add(source, dest).width(polyLineWidth/generation));
					polyline.setColor(settings.getFamTreeLineColor());
					familyLine.add(polyline);
				}

				generation++;
				plotFamilyTreeRecurse(mMap,mother,events.get(0));
				generation--;
			}
			else
			{
				generation++;
				plotFamilyTreeRecurse(mMap,mother,event);
				generation--;
			}

		}






	}

	private ArrayList<Event>getEvents(Person person)
	{
		ArrayList<Event>events = new ArrayList<>();
		for(String e: person.getEvents())
		{
			if(ds.findEventByID(e)!=null)
			{
				events.add(ds.findEventByID(e));
			}

		}
		return events;
	}













}
