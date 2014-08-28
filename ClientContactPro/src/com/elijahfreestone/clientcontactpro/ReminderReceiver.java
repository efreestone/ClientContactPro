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
import android.net.Uri;
import android.util.Log; 
import android.widget.Toast;

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
		Toast.makeText(context, "Reminder Received!", Toast.LENGTH_LONG).show();
		Intent incomingIntent = intent;
		String clientName = incomingIntent.getStringExtra("clientName");
		String contactMethod = incomingIntent.getStringExtra("contactMethod");
		String emailAddress = incomingIntent.getStringExtra("emailAddress");
		String phoneNumber = incomingIntent.getStringExtra("phoneNumber"); 
		Log.i(TAG, "Reminder Received for " + clientName);
		
		IntentManager myIntentManager = IntentManager.getInstance();
		
		if (clientName.equalsIgnoreCase("") || clientName == null) {
			clientName = "clientName Extra Blank";
		}
		
		myIntentManager.createReminderIntent(clientName, contactMethod, emailAddress, phoneNumber); 
		
//		//start activity
//        Intent i = new Intent();
//        i.setClassName("com.test", "com.test.MainActivity");
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(i);
		
//		Intent reminderIntent = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
//		reminderIntent.setType("message/rfc822");
//		reminderIntent.putExtra(Intent.EXTRA_EMAIL, "e_freestone@yahoo.com");
//		reminderIntent.putExtra(Intent.EXTRA_SUBJECT, "test");
//		reminderIntent.putExtra(Intent.EXTRA_TEXT, "This is a sleep test. Client name: " + clientName);
//		//reminderIntent.createChooser(target, title)
//		reminderIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		
//		MainActivity.myContext.startActivity(Intent.createChooser(reminderIntent, "Choose an email client"));

	}

}
