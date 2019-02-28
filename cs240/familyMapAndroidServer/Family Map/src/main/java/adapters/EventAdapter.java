package adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bignerdranch.android.fmapp.EventActivity;
import com.bignerdranch.android.fmapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import model.DataSingleton;
import shared.models.Event;
import shared.models.Person;

/**
 * Created by Riley on 4/13/18.
 */

public class EventAdapter extends RecyclerView.Adapter
{
	DataSingleton ds = DataSingleton.getInstance();
	Context context;
	private ArrayList<String> events;
	private ArrayList<Event> eventArrayList = new ArrayList<>();
	private HashMap<Integer, Event> eventHashMap = new HashMap<>();
	private ArrayList<Integer> years = new ArrayList<>();


	public static class ViewHolder extends RecyclerView.ViewHolder
	{

		public TextView eventDescription;
		public TextView eventPerson;
		public LinearLayout eventBox;


		public ViewHolder(View itemView)
		{
			super(itemView);
			eventPerson = (TextView) itemView.findViewById(R.id.person_event_person_name);
			eventDescription = (TextView) itemView.findViewById(R.id.person_event_info_text);
			eventBox = (LinearLayout) itemView.findViewById(R.id.person_event_box);
		}
	}



	public EventAdapter(Context context, ArrayList<String> eventStrings)
	{
		this.context = context;

		for (String event : eventStrings)
		{
			Event eventObj = DataSingleton.getInstance().findEventByID(event);
			Integer year = Integer.valueOf(eventObj.getYear());

			if (!years.contains(year))
			{
				years.add(year);
			}

			eventArrayList.add(eventObj);
		}

		Collections.sort(years);

		ArrayList<Event> eventTemp = new ArrayList<>();

		//sort my stupid events
		for (int i = 0; i < years.size(); i++)
		{
			for (Event event : eventArrayList)
			{
				if (Integer.valueOf(event.getYear()).equals(years.get(i)))
				{
					eventTemp.add(event);
				}
			}
		}

		eventArrayList = new ArrayList<>(eventTemp);

	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{

		LayoutInflater layoutInflater = LayoutInflater.from(context);
		View view = layoutInflater.inflate(R.layout.person_event_box, parent, false);

		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
	{
		ViewHolder vHolder = (ViewHolder) holder;
		final Event event = eventArrayList.get(position);
		Person person = ds.findPersonByID(event.getPersonID());
		vHolder.eventDescription.setText(event.getEventType() + ": " + event.getCity() + ", " + event.getCountry() + " (" + event.getYear() + ")");
		vHolder.eventPerson.setText(person.getFirstName() + " " + person.getLastName());
		vHolder.eventBox.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				//Toast toast = Toast.makeText(context,"Start Event Activity", Toast.LENGTH_SHORT);
				//toast.show();

				Intent event_intent = new Intent(context, EventActivity.class);

				event_intent.putExtra("eventID", event.getEventID());

				context.startActivity(event_intent);
			}
		});

	}

	@Override
	public int getItemCount()
	{
		return eventArrayList.size();
	}
}
