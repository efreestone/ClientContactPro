/*
 * @author	Elijah Freestone 
 *
 * Project	ClientContactPro
 * 
 * Package	com.elijahfreestone.clientcontactpro
 * 
 * Date		Aug 25, 2014
 */

package com.elijahfreestone.clientcontactpro;

import android.app.Activity;
import android.os.Bundle; 

// TODO: Auto-generated Javadoc
/**
 * The Class SettingsActivity.
 */
public class SettingsActivity extends Activity {  
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
		
	}

}
