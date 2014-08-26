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

public class SettingsActivity extends Activity {  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
		
//		getFragmentManager().beginTransaction().replace(android.R.id.content,
//                new PrefsFragment()).commit();
		
	}

}
