package com.yahoo.android.client.imlost.db;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

/**
 * This class is essentially a rewrite of <code>CursorLoader</code> except it
 * doesn't require a <code>ContentProvider</code>
 * <br /><br />
 * This code was copies from the 'Big Nerd Ranch Android Programming Guide' v1. 
 */
public abstract class SQLiteCursorLoader extends AsyncTaskLoader<Cursor> {

	private Cursor mCursor;
	
	public SQLiteCursorLoader(Context context) {
		super(context);
	}

	protected abstract Cursor loadCursor();
	
	@Override
	public Cursor loadInBackground() {
		Cursor cursor = loadCursor();
		if(cursor != null) {
			// Ensure that the content window is filled
			cursor.getCount();
		}
		return cursor;
	}
	
	@Override
	public void deliverResult(Cursor data) {
		Cursor oldCursor = mCursor;
		mCursor = data;
		
		if(isStarted())
			super.deliverResult(data);
		
		if(oldCursor != null && oldCursor != data && !oldCursor.isClosed())
			oldCursor.close();
	}
	
	@Override
	protected void onStartLoading() {
		if(mCursor != null)
			deliverResult(mCursor);

		if(takeContentChanged() || mCursor == null)
			forceLoad();
	}
	
	@Override
	protected void onStopLoading() {
		// Attempt to cancel the current load task if possible
		cancelLoad();
	}
	
	@Override
	public void onCanceled(Cursor cursor) {
		if(cursor != null && !cursor.isClosed())
			cursor.close();
	}
	
	@Override
	public void onReset() {
		super.onReset();
		
		// Ensure the loader is stopped
		onStopLoading();
		
		if(mCursor != null && mCursor.isClosed())
			mCursor.close();

		mCursor = null;
	}
}