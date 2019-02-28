package model;

import android.graphics.Color;
import android.util.Pair;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;

import shared.models.Event;
import shared.models.Person;

/**
 * Created by Riley on 4/16/18.
 */

public class SettingSingleton
{
	private static final SettingSingleton ourInstance = new SettingSingleton();

	public static SettingSingleton getInstance()
	{
		return ourInstance;
	}

	private int lifeStoryLineColor = 0;
	private int famTreeLineColor = 1;
	private int spouseLineColor = 2;
	private int mapType = 0;

	private Boolean showFamilyTree = false;
	private Boolean showSpouseLine = false;
	private Boolean showLifeStory = false;
	private Pair<LatLng, LatLng> spouseLine;
	private Person rootPerson;
	private Event rootEvent;


	private SettingSingleton()
	{
	}

	public void reset()
	{
		lifeStoryLineColor = 0;
		famTreeLineColor = 1;
		spouseLineColor = 2;
		mapType = 0;
	}


	private int getColor(int position)
	{

		switch (position)
		{

			case 0:
				return Color.RED;

			case 1:
				return Color.GREEN;

			case 2:
				return Color.BLUE;

			case 3:
				return Color.YELLOW;
		}

		return Color.BLUE;
	}

	public LatLng getEarliest(ArrayList<Event> events, ArrayList<Event> container)
	{
		ArrayList<Event> results = sortEvents(events);

		if (results == null) return null;
		for (int i = 0; i < results.size(); i++)
		{
			if (container.contains(results.get(i)))
			{
				Event e = results.get(i);
				return new LatLng(e.getLatitude(), e.getLongitude());
			}
		}

		return null;
	}

	public ArrayList<Event> sortEvents(ArrayList<Event> events)
	{
		ArrayList<Integer> years = new ArrayList<>();
		ArrayList<Event> eventTemp = new ArrayList<>();

		if (events == null) return null;
		for (Event e : events)
		{
			if (!years.contains(Integer.valueOf(e.getYear())))
			{
				years.add(Integer.valueOf(e.getYear()));
			}
		}

		Collections.sort(years);

		for (Integer year : years)
		{
			for (Event e : events)
			{
				if (year.equals(Integer.valueOf(e.getYear())))
				{
					eventTemp.add(e);
				}
			}

		}

		return eventTemp;
	}

	;

	public Pair<LatLng, LatLng> getSpouseLine()
	{
		return spouseLine;
	}

	public void setSpouseLine(LatLng first, LatLng second)
	{
		this.spouseLine = new Pair<>(first, second);
	}

	public Person getRootPerson()
	{
		return rootPerson;
	}

	public void setRootPerson(Person rootPerson)
	{
		this.rootPerson = rootPerson;
	}

	public Event getRootEvent()
	{
		return rootEvent;
	}

	public void setRootEvent(Event rootEvent)
	{
		this.rootEvent = rootEvent;
	}

	public int getSpouseColorPos()
	{
		return spouseLineColor;
	}

	public int getFamTreeColorPos()
	{
		return famTreeLineColor;
	}

	public int getLifeEventColorPos()
	{
		return lifeStoryLineColor;
	}

	public int getMapType() {return mapType;}

	public void setMapType(int mapType) {this.mapType = mapType;}

	public int getFamTreeLineColor()
	{
		return getColor(famTreeLineColor);
	}

	public void setFamTreeLineColor(int famTreeLineColor)
	{
		this.famTreeLineColor = famTreeLineColor;
	}

	public int getLifeStoryLineColor()
	{
		return getColor(lifeStoryLineColor);
	}

	public void setLifeStoryLineColor(int lifeStoryLine)
	{
		this.lifeStoryLineColor = lifeStoryLine;
	}

	public int getSpouseLineColor()
	{
		return getColor(spouseLineColor);
	}

	public void setSpouseLineColor(int spouseLineColor)
	{
		this.spouseLineColor = spouseLineColor;
	}

	public Boolean getShowFamilyTree()
	{
		return showFamilyTree;
	}

	public void setShowFamilyTree(Boolean showFamilyTree)
	{
		this.showFamilyTree = showFamilyTree;
	}

	public Boolean getShowSpouseLine()
	{
		return showSpouseLine;
	}

	public void setShowSpouseLine(Boolean showSpouseLine)
	{
		this.showSpouseLine = showSpouseLine;
	}

	public Boolean getShowLifeStory()
	{
		return showLifeStory;
	}

	public void setShowLifeStory(Boolean showLifeStory)
	{
		this.showLifeStory = showLifeStory;
	}

}
