package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.bignerdranch.android.fmapp.R;

import java.util.List;

import model.DataSingleton;

/**
 * Created by Riley on 4/11/18.
 */

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder>
{


	public static class ViewHolder extends RecyclerView.ViewHolder
	{
		public TextView eventType;
		public TextView eventDescription;
		public Switch eventSwitch;

		public ViewHolder(View itemView)
		{
			super(itemView);
			eventType = (TextView) itemView.findViewById(R.id.filter_event_type);
			eventDescription = (TextView) itemView.findViewById(R.id.filter_event_description);
			eventSwitch = (Switch) itemView.findViewById(R.id.filter_event_switch);
		}
	}


	Context context;
	private List<Pair<String, String>> events;

	public FilterAdapter(Context context, List<Pair<String, String>> events)
	{
		this.events = events;
		this.context = context;

	}


	@Override
	public FilterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		View view = layoutInflater.inflate(R.layout.filter_event_box, parent, false);

		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(FilterAdapter.ViewHolder holder, int position)
	{
		final String type = events.get(position).first;
		String description = events.get(position).second;


		holder.eventType.setText(type);
		holder.eventDescription.setText(description);
		holder.eventSwitch.setChecked(DataSingleton.getInstance().getFilterStatus(type));


		holder.eventSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked)
			{
				if (isChecked)
				{
					DataSingleton.getInstance().removeEventType(type.toLowerCase());
					DataSingleton.getInstance().setFilterStatus(type, true);
				} else
				{
					DataSingleton.getInstance().addEventType(type.toLowerCase());
					DataSingleton.getInstance().setFilterStatus(type, false);
				}
			}
		});


	}

	@Override
	public int getItemCount()
	{
		return events.size();
	}
}
