package com.yahoo.android.client.imlost;

import android.support.v4.app.Fragment;

import com.yahoo.android.client.imlost.fragment.ImLostContactListFragment;


public class ImLostContactListActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new ImLostContactListFragment();
	}
}
