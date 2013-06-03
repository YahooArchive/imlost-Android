package com.yahoo.android.client.imlost.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.yahoo.android.client.imlost.models.Contact;


public class ContactDatabaseHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "imlost_contacts.sqlite";
	private static final int VERSION = 1;

	private static final String TABLE_CONTACTS = "contacts";
	private static final String COLUMN_CONTACT_ID = BaseColumns._ID;
	private static final String COLUMN_CONTACT_NAME = "contact_name";
	private static final String COLUMN_CONTACT_NUMBER = "contact_number";
	
	public ContactDatabaseHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//Create the contact table
		db.execSQL("CREATE TABLE " + TABLE_CONTACTS + "("
				+ COLUMN_CONTACT_ID + " INTEGER PRIMARY_KEY AUTO_INCREMENT,"
				+ COLUMN_CONTACT_NAME + " VARCHAR NOT NULL,"
				+ COLUMN_CONTACT_NUMBER + " VARCHAR NOT NULL)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//Nothing here yet
	}
	
	public ContactCursor queryContacts() {
		Cursor wrappedCursor = getReadableDatabase().query(TABLE_CONTACTS,
				null, null, null, null, null, COLUMN_CONTACT_NAME + " DESC");
		return new ContactCursor(wrappedCursor);
	}
	
	public long insertContact(Contact contact) {
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_CONTACT_NAME, contact.getName());
		cv.put(COLUMN_CONTACT_NUMBER, contact.getNumber());
		return getWritableDatabase().insert(TABLE_CONTACTS, null, cv);
	}
	
	public static class ContactCursor extends CursorWrapper {
		
		public ContactCursor(Cursor c) {
			super(c);
		}
		
		public Contact getContact() {
			if(isBeforeFirst() || isAfterLast())
				return null;
			
			Contact contact = new Contact();
			// Set the ID
			long id = getLong(getColumnIndex(COLUMN_CONTACT_ID));
			contact.setId(id);
			
			// Set the name
			String name = getString(getColumnIndex(COLUMN_CONTACT_NAME));
			contact.setName(name);
			
			// Set the phone number
			String number = getString(getColumnIndex(COLUMN_CONTACT_NUMBER));
			contact.setNumber(number);
			
			return contact;
		}
	}
}
