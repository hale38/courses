package com.bignerdranch.android.fmapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import model.DataSingleton;

/**
 * Created by Riley on 4/7/18.
 */

public class EventActivity extends AppCompatActivity
{

	public String currentEvent;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event);

		currentEvent = getIntent().getStringExtra("eventID");
		DataSingleton ds = DataSingleton.getInstance();



	}
}
