package com.bignerdranch.android.fmapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;

import java.util.List;

import adapters.FilterAdapter;
import model.DataSingleton;

/**
 * Created by Riley on 4/7/18.
 */

public class FilterActivity extends AppCompatActivity
{

	private RecyclerView recyclerView;
	private FilterAdapter filterAdapter;
	DataSingleton ds;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.filter_recycle);
		recyclerView = (RecyclerView) findViewById(R.id.filter_recycle);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		ds = DataSingleton.getInstance();
		updateUI();
	}




	private void updateUI()
	{
		List<Pair<String, String>> filters = ds.getAllFilterInfo();
		filterAdapter = new FilterAdapter(this, filters);
		recyclerView.setAdapter(filterAdapter);
	}

	protected void onDestroy()
	{
		System.out.print("Asdad");
		super.onDestroy();
	}


}
