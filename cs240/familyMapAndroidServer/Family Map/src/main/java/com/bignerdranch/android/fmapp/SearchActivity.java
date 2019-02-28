package com.bignerdranch.android.fmapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;

import adapters.SearchAdapter;
import model.DataSingleton;

/**
 * Created by Riley on 4/6/18.
 */

public class SearchActivity extends AppCompatActivity
{
	RecyclerView recyclerView;
	ArrayList<String> results;
	SearchAdapter searchAdapter;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		final DataSingleton ds = DataSingleton.getInstance();

		EditText searchParam = (EditText) findViewById(R.id.search_input);
		recyclerView = (RecyclerView) findViewById(R.id.search_recycle_view);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));


		searchParam.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
			{

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
			{
				if (charSequence.length() == 0)
				{
					results = new ArrayList<>();
					updateUI();
				} else
				{
					results = ds.searchData(charSequence.toString());
					updateUI();
				}
			}

			@Override
			public void afterTextChanged(Editable editable)
			{

			}
		});

	}

	private void updateUI()
	{
		searchAdapter = new SearchAdapter(this, results);
		recyclerView.setAdapter(searchAdapter);
	}


}
