package com.yahoo.android.client.imlost.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.yahoo.android.client.imlost.R;
import com.yahoo.android.client.imlost.data.ContactManager;
import com.yahoo.android.client.imlost.db.ContactDatabaseHelper.ContactCursor;
import com.yahoo.android.client.imlost.db.SQLiteCursorLoader;
import com.yahoo.android.client.imlost.models.Contact;

public class ImLostContactListFragment extends SherlockListFragment
				implements LoaderManager.LoaderCallbacks<Cursor> {

	//private static final String TAG = ImLostContactListActivity.class.getSimpleName();
	private static final int CONTACT_LOADER_ID = 0;
	private static final int REQUEST_CONTACT = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getLoaderManager().initLoader(CONTACT_LOADER_ID, null, this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		setHasOptionsMenu(true);
		return inflater.inflate(R.layout.fragment_contact_list, container, false);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_contacts, menu);
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
			Cursor c = getActivity().getContentResolver()
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
				Toast.makeText(getActivity(), "Not a mobile", Toast.LENGTH_SHORT).show();
			} else {
				Contact contact = new Contact();
				contact.setName(c.getString(c.getColumnIndex(Phone.DISPLAY_NAME)));
				contact.setNumber(c.getString(c.getColumnIndex(Phone.NUMBER)));
				ContactManager.get(getActivity()).insertContact(contact);
				getLoaderManager().restartLoader(CONTACT_LOADER_ID, null, this);
			}
			c.close();
		}
	}
	
	/* LOADER CALLBACKS */
	
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
		return new ContactListCursorLoader(getActivity());
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		ContactCursorAdapter adapter =
				new ContactCursorAdapter(getActivity(), (ContactCursor)cursor);
		setListAdapter(adapter);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		setListAdapter(null);
	}
	
	/* LOADER */
	
	private static class ContactListCursorLoader extends SQLiteCursorLoader {

		public ContactListCursorLoader(Context context) {
			super(context);
		}

		@Override
		protected ContactCursor loadCursor() {
			return ContactManager.get(getContext()).queryContacts();
		}
		
	}
	
	/* ADAPTER */
	
	private class ContactCursorAdapter extends CursorAdapter {

		private ContactCursor mContactCursor;
		
		public ContactCursorAdapter(Context context, ContactCursor cursor) {
			super(context, cursor, 0);
			mContactCursor = cursor;
		}
		
		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// Use a layout inflater to get a row view
			LayoutInflater inflater = (LayoutInflater)context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.list_item_contact, parent, false);
			
			
			return view;
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			// Get the contact for the current row
			Contact contact = mContactCursor.getContact();
			
			// Setup the contents for the view\
			TextView displayName = (TextView)view.findViewById(R.id.contact_list_item_displayName);
			displayName.setText(contact.getName());
			
			TextView phoneNumber = (TextView)view.findViewById(R.id.contact_list_item_phoneNumber);
			phoneNumber.setText(contact.getNumber());
		}
	}
}
