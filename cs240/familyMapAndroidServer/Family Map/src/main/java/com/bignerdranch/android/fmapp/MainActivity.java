package com.bignerdranch.android.fmapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import model.DataSingleton;
import shared.models.Person;

public class MainActivity extends AppCompatActivity
{

	Person anchor;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		DataSingleton ds = DataSingleton.getInstance();

		FragmentManager fragmentManager = getSupportFragmentManager();
		Fragment loginFragment = fragmentManager.findFragmentById(R.id.fragmentFrame);

		if (loginFragment == null)
		{
			loginFragment = new LoginFragment();
			fragmentManager.beginTransaction().add(R.id.fragmentFrame, loginFragment).commit();
		}

	}

	void launchMapFragment()
	{
		FragmentManager fragmentManager = getSupportFragmentManager();
		Fragment mapFragment;
		mapFragment = new MapFragment();
		DataSingleton.getInstance().setFilterTypes();

		android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);

		setSupportActionBar(toolbar);

		fragmentManager.beginTransaction().replace(R.id.fragmentFrame, mapFragment).commit();

	}

	@Override
	protected void onResume()
	{
		super.onResume();
		if (DataSingleton.getInstance().login == false)
		{
			FragmentManager fragmentManager = getSupportFragmentManager();
			LoginFragment loginFragment = new LoginFragment();
			fragmentManager.beginTransaction().replace(R.id.fragmentFrame, loginFragment).commit();
		}
	}

}
