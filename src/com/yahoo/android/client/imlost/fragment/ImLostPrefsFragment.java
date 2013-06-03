package com.yahoo.android.client.imlost.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.yahoo.android.client.imlost.ImLostContactListActivity;
import com.yahoo.android.client.imlost.R;

public class ImLostPrefsFragment extends SherlockFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		View view = inflater.inflate(R.layout.fragment_preferences, container, false);
		
		Button aboutButton = (Button)view.findViewById(R.id.prefs_about_button);
		aboutButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "About", Toast.LENGTH_SHORT).show();
			}
		});

		Button contactsButton = (Button)view.findViewById(R.id.prefs_contacts_button);
		contactsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), ImLostContactListActivity.class);
				startActivity(i);
			}
		});

		Button trackingButton = (Button)view.findViewById(R.id.prefs_tracking_button);
		trackingButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Tracking", Toast.LENGTH_SHORT).show();
			}
		});
		
		return view;
	}
}
