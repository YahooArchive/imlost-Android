package com.yahoo.android.client.imlost;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class ImLostPrefsActivity extends SherlockFragmentActivity {

	private Context context;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preferences);
		context = this.getApplicationContext();

		Button aboutButton = (Button)findViewById(R.id.prefs_about_button);
		aboutButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getBaseContext(), "About", Toast.LENGTH_SHORT).show();
			}
		});

		Button contactsButton = (Button)findViewById(R.id.prefs_contacts_button);
		contactsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(context, ImLostContactListActivity.class);
				startActivity(i);
			}
		});

		Button trackingButton = (Button)findViewById(R.id.prefs_tracking_button);
		trackingButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getBaseContext(), "Tracking", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
