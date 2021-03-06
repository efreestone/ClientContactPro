/*
 * @author	Elijah Freestone 
 *
 * Project	ClientContactPro
 * 
 * Package	com.elijahfreestone.clientcontactpro
 * 
 * Date		Aug 27, 2014
 */

package com.elijahfreestone.clientcontactpro;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

// TODO: Auto-generated Javadoc
/**
 * The Class ReminderReceiver. 
 */
public class ReminderReceiver extends BroadcastReceiver {
	String TAG = "ReminderReceiver";

	/* (non-Javadoc)  
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		//Toast.makeText(context, "Reminder Received!", Toast.LENGTH_LONG).show();
		Intent incomingIntent = intent;
		String clientName = incomingIntent.getStringExtra("clientName");
		String contactMethod = incomingIntent.getStringExtra("contactMethod");
		String emailAddress = incomingIntent.getStringExtra("emailAddress");
		String phoneNumber = incomingIntent.getStringExtra("phoneNumber"); 
		String startTimeAndDate = incomingIntent.getStringExtra("startTimeAndDate");
		Log.i(TAG, "Reminder Received for " + clientName);
		
		IntentManager myIntentManager = IntentManager.getInstance();
		
		if (clientName.equalsIgnoreCase("") || clientName == null) {
			clientName = "clientName Extra Blank";
		}
		
		myIntentManager.createReminderIntent(clientName, contactMethod, emailAddress, phoneNumber, startTimeAndDate); 
		

	}

}
