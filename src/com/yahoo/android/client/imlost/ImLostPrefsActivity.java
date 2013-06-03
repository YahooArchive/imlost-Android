package com.yahoo.android.client.imlost;

import android.support.v4.app.Fragment;

import com.yahoo.android.client.imlost.fragment.ImLostPrefsFragment;

public class ImLostPrefsActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new ImLostPrefsFragment();
	}

}
