package com.yahoo.android.client.imlost;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

public class ImLostActivity extends SherlockFragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imlost);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);

		Button imLostButton = (Button)findViewById(R.id.imlost_button);
		imLostButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getBaseContext(), "I'm Lost", Toast.LENGTH_SHORT).show();
			}
			
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		getSupportMenuInflater().inflate(R.menu.menu_imlost, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.imlost_menu_item_prefs:
				Intent i = new Intent(this, ImLostPrefsActivity.class);
				startActivity(i);
				
				if(NavUtils.getParentActivityName(this) != null) {
					NavUtils.navigateUpFromSameTask(this);
				}
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
