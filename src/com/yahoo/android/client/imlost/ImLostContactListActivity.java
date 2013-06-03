package com.yahoo.android.client.imlost;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.MenuItem;
import com.yahoo.android.client.imlost.db.ContactDatabaseHelper;
import com.yahoo.android.client.imlost.models.Contact;

public class ImLostContactListActivity extends SherlockListActivity {

	//private static final String TAG = ImLostContactListActivity.class.getSimpleName();
	private static final int REQUEST_CONTACT = 1;

	private Context context;
	private ContactAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_list);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		context = this.getApplicationContext();
		adapter = new ContactAdapter(new ArrayList<Contact>());
		
		ListView listView = (ListView)findViewById(android.R.id.list);
		listView.setAdapter(adapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		getSupportMenuInflater().inflate(R.menu.menu_contacts, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.contact_menu_item_add_contact:
				Intent i = new Intent(Intent.ACTION_PICK, Phone.CONTENT_URI);
				startActivityForResult(i, REQUEST_CONTACT);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REQUEST_CONTACT) {
			if(data == null)
				return;
			
			Uri contactUri = data.getData();
			
			//Specify the fields to query
			String[] queryFields = new String[] {
					Phone.TYPE,
					Phone.DISPLAY_NAME,
					Phone.NUMBER
			};

			//Perform query
			Cursor c = this.getContentResolver()
					.query(contactUri, queryFields, null, null, null);
			
			//Check for results
			if(c.getCount() == 0) {
				c.close();
				return;
			}
			
			//Extract the contact
			c.moveToFirst();
			String numberType = c.getString(0);
			if(!numberType.equalsIgnoreCase(String.valueOf(Phone.TYPE_MOBILE))) {
				Toast.makeText(context, "Not a mobile", Toast.LENGTH_SHORT).show();
			} else {
				ContactDatabaseHelper helper = new ContactDatabaseHelper(context);
				Contact contact = new Contact(c.getString(1), c.getString(2));
				helper.insertContact(contact);
				adapter.add(contact);
			}
			c.close();
		}
	}
	
	private class ContactAdapter extends ArrayAdapter<Contact> {

		public ContactAdapter(ArrayList<Contact> contacts) {
			super(context, 0, contacts);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// If we weren't given a view, inflate one
			if(convertView == null) {
				convertView = getLayoutInflater()
						.inflate(R.layout.list_item_contact, null);
			}
			
			Contact contact = getItem(position);
			
			TextView displayName = (TextView)convertView.findViewById(R.id.contact_list_item_displayName);
			displayName.setText(contact.getName());
			
			TextView phoneNumber = (TextView)convertView.findViewById(R.id.contact_list_item_phoneNumber);
			phoneNumber.setText(contact.getNumber());
			
			return convertView;
		}
	}
}
