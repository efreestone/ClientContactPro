/*
 * @author	Elijah Freestone 
 *
 * Project	ClientContactPro
 * 
 * Package	com.elijahfreestone.clientcontactpro
 * 
 * Date		Aug 28, 2014
 */

package com.elijahfreestone.clientcontactpro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;
import android.widget.Toast;

public class IntentManager extends Activity {
	// Create instance of Singleton
	private static IntentManager intentManagerInstance;
	Context myContext = MainActivity.myContext;
	
	// Create getInstance method and check if instance exists
	public static IntentManager getInstance() {
		if (intentManagerInstance == null) {
			intentManagerInstance = new IntentManager();
		}
		return intentManagerInstance;
	}

	/* createReminderIntent creates a reminder intent based on the clients contact method */
	public void createReminderIntent(String clientName, String contactMethod, String emailAddress, String phoneNumber, String startTimeAndDate){
		//Check contact method for client
		if (contactMethod.equalsIgnoreCase("email")) {
			Intent emailIntent = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
			//Intent reminderIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto:", "e_freestone@yahoo.com", null));
			emailIntent.setType("message/rfc822");
			emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { emailAddress });
			emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Appointment Reminder");
			emailIntent.putExtra(Intent.EXTRA_TEXT, MainActivity.sharedPreferences.getString("defaultReminderMessage", "reminder") + " " + startTimeAndDate);
			
			myContext.startActivity(Intent.createChooser(emailIntent, "Choose an email client"));
		} else {
			SmsManager smsManager = SmsManager.getDefault();
			String textMessage = MainActivity.sharedPreferences.getString("defaultReminderMessage", "reminder") + " " + startTimeAndDate;
			smsManager.sendTextMessage(phoneNumber, null, textMessage, null, null);
		}
	} //createReminderIntent close
	
	/* createCancelInten creates a cancel intent based on the clients contact method */
	public void createCancelInten(String clientName, String contactMethod, String emailAddress, String phoneNumber, String startTimeAndDate){
		//Log.i("Cancel", "createCancelIntent");
		//Check contact method for client
		if (contactMethod.equalsIgnoreCase("email")) {
			Intent emailIntent = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
			emailIntent.setType("message/rfc822");
			emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { emailAddress });
			emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Appointment Canceled");
			emailIntent.putExtra(Intent.EXTRA_TEXT, MainActivity.sharedPreferences.getString("defaultCancelMessage", "cancel"));
			
			myContext.startActivity(Intent.createChooser(emailIntent, "Choose an email client"));
		} else {
			SmsManager smsManager = SmsManager.getDefault();
			String cancelTextMessage = MainActivity.sharedPreferences.getString("defaultCancelMessage", "cancel");
			smsManager.sendTextMessage(phoneNumber, null, cancelTextMessage, null, null);
			Toast.makeText(MainActivity.myContext, "Text Sent to " + clientName, Toast.LENGTH_LONG).show();
		}
	} //createCancelInten close

}
