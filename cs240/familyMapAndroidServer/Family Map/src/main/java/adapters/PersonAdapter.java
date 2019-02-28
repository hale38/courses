package adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bignerdranch.android.fmapp.PersonActivity;
import com.bignerdranch.android.fmapp.R;

import java.util.ArrayList;

import model.DataSingleton;
import shared.models.Person;

/**
 * Created by Riley on 4/13/18.
 */

public class PersonAdapter extends RecyclerView.Adapter
{
	DataSingleton ds = DataSingleton.getInstance();

	public static class ViewHolder extends RecyclerView.ViewHolder
	{

		public TextView personName;
		public TextView personRelation;
		public LinearLayout personBox;
		public ImageView genderIcon;

		public ViewHolder(View itemView)
		{
			super(itemView);
			personName = (TextView) itemView.findViewById(R.id.person_info_name_text);
			personRelation = (TextView) itemView.findViewById(R.id.person_info_relationship_text);
			genderIcon = (ImageView) itemView.findViewById(R.id.person_recycle_gender_icon);
			personBox = (LinearLayout) itemView.findViewById(R.id.person_person_info_box);
		}
	}

	Context context;
	private ArrayList<Pair<String, String>> people;

	public PersonAdapter(Context context, ArrayList<Pair<String, String>> people)
	{
		this.context = context;
		this.people = people;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{

		LayoutInflater layoutInflater = LayoutInflater.from(context);
		View view = layoutInflater.inflate(R.layout.person_person_info_box, parent, false);

		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
	{
		ViewHolder vHolder = (ViewHolder) holder;


		String relation = people.get(position).first;
		final Person person = ds.findPersonByID(people.get(position).second);


		//find relationships


		if (person != null)
		{
			vHolder.personName.setText(person.getFirstName() + " " + person.getLastName());
			vHolder.personRelation.setText(relation);


			if (!person.getGender().equals("M"))
			{
				vHolder.genderIcon.setImageResource(R.mipmap.gicon_female);
			}

			vHolder.personBox.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{

					Intent person_intent = new Intent(context, PersonActivity.class);
					person_intent.putExtra("personID", person.getPersonId());

					context.startActivity(person_intent);

				}
			});

		}
	}

	@Override
	public int getItemCount()
	{
		return people.size();
	}
}