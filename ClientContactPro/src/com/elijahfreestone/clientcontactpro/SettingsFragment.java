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

import android.os.Bundle; 
import android.preference.PreferenceFragment; 
 
// TODO: Auto-generated Javadoc
/**
 * The Class SettingsFragment.
 */
public class SettingsFragment extends PreferenceFragment {    
	
	/* (non-Javadoc)
	 * @see android.preference.PreferenceFragment#onCreate(android.os.Bundle)
	 */
	@Override  
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub 
		super.onCreate(savedInstanceState);   
		
		//Add prefs from resources   
		addPreferencesFromResource(R.xml.fragment_settings);  
	}   

}
