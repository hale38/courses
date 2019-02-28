package adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bignerdranch.android.fmapp.EventActivity;
import com.bignerdranch.android.fmapp.PersonActivity;
import com.bignerdranch.android.fmapp.R;

import java.util.ArrayList;

import model.DataSingleton;
import shared.models.Event;
import shared.models.Person;

/**
 * Created by Riley on 4/16/18.
 */

public class SearchAdapter extends RecyclerView.Adapter
{


	public static class ViewHolder extends RecyclerView.ViewHolder
	{

		public TextView topText;
		public TextView bottomText;
		public ImageView icon;
		public LinearLayout resultBox;


		public ViewHolder(View itemView)
		{
			super(itemView);
			topText = (TextView) itemView.findViewById(R.id.search_result_top);
			bottomText = (TextView) itemView.findViewById(R.id.search_result_bottom);
			resultBox = (LinearLayout) itemView.findViewById(R.id.search_result_info_box);
			icon = (ImageView) itemView.findViewById(R.id.search_result_icon);
		}

	}

	Context context;
	ArrayList<String> results;
	DataSingleton ds;

	public SearchAdapter(Context context, ArrayList<String> results)
	{
		this.context = context;
		this.results = results;
		ds = DataSingleton.getInstance();
	}


	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		View view = layoutInflater.inflate(R.layout.search_result, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
	{
		ViewHolder viewHolder = (ViewHolder) holder;
		Person person = null;
		Event event = null;

		final char type;
		final String id;
		if (ds.findPersonByID(results.get(position)) != null)
		{
			type = 'p';
			person = ds.findPersonByID(results.get(position));
			id = person.getPersonId();
			viewHolder.topText.setText(person.getFirstName() + " " + person.getLastName());

			if (person.getGender().toLowerCase().equals("m"))
			{
				viewHolder.icon.setImageResource(R.mipmap.gicon_male);
			} else
			{
				viewHolder.icon.setImageResource(R.mipmap.gicon_female);
			}
		} else
		{
			type = 'e';
			event = ds.findEventByID(results.get(position));
			id = event.getEventID();
			Person pTemp = ds.findPersonByID(event.getPersonID());
			viewHolder.topText.setText(event.getEventType() + ": " + event.getCity() + ", " + event.getCountry() + " (" + event.getYear() + ")");
			viewHolder.bottomText.setText(pTemp.getFirstName() + " " + pTemp.getLastName());
			viewHolder.icon.setImageResource(R.drawable.ic_event_icon);
		}


		viewHolder.resultBox.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				if (type == 'e')
				{

					Intent event_intent = new Intent(context, EventActivity.class);
					event_intent.putExtra("eventID", id);

					context.startActivity(event_intent);
				} else
				{

					Intent person_intent = new Intent(context, PersonActivity.class);
					person_intent.putExtra("personID", id);

					context.startActivity(person_intent);
				}
			}
		});
	}

	@Override
	public int getItemCount()
	{
		return results.size();
	}
}
