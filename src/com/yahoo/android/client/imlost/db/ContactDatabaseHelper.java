package com.yahoo.android.client.imlost.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yahoo.android.client.imlost.models.Contact;


public class ContactDatabaseHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "imlost_contacts.sqlite";
	private static final int VERSION = 1;

	private static final String TABLE_CONTACTS = "contacts";
	private static final String COLUMN_CONTACT_NAME = "contact_name";
	private static final String COLUMN_CONTACT_PHONE = "contact_phone";
	
	public ContactDatabaseHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//Create the contact table
		db.execSQL("CREATE TABLE " + TABLE_CONTACTS + "("
				+ "_id INTEGER PRIMARY_KEY AUTO_INCREMENT,"
				+ COLUMN_CONTACT_NAME + " VARCHAR NOT NULL,"
				+ COLUMN_CONTACT_PHONE + " VARCHAR NOT NULL)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//Nothing here yet
	}
	
	public long insertContact(Contact contact) {
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_CONTACT_NAME, contact.getName());
		cv.put(COLUMN_CONTACT_PHONE, contact.getNumber());
		return getWritableDatabase().insert(TABLE_CONTACTS, null, cv);
	}
}
