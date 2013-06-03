package com.yahoo.android.client.imlost.data;

import android.content.Context;

import com.yahoo.android.client.imlost.db.ContactDatabaseHelper;
import com.yahoo.android.client.imlost.db.ContactDatabaseHelper.ContactCursor;
import com.yahoo.android.client.imlost.models.Contact;

public class ContactManager {

	private static ContactManager sInstance;
	private Context mAppContext;
	private ContactDatabaseHelper mHelper;
	
	private ContactManager(Context appContext) {
		mAppContext = appContext;
		mHelper = new ContactDatabaseHelper(mAppContext);
	}
	
	public static ContactManager get(Context context) {
		if(sInstance == null)
			sInstance = new ContactManager(context.getApplicationContext());
		return sInstance;
	}
	
	public ContactCursor queryContacts() {
		return mHelper.queryContacts();
	}
	
	public void insertContact(Contact contact) {
		mHelper.insertContact(contact);
	}
}
