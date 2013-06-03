package com.yahoo.android.client.imlost.models;

public class Contact {

	private long mId;
	private String mName;
	private String mNumber;
	
	public Contact() {
		mId = -1;
		mName = "";
		mNumber = "";
	}
	
	public long getId() {
		return mId;
	}

	public void setId(long id) {
		this.mId = id;
	}

	public String getName() {
		return mName;
	}
	
	public void setName(String name) {
		this.mName = name;
	}
	
	public String getNumber() {
		return mNumber;
	}

	public void setNumber(String number) {
		this.mNumber = number;
	}
}
