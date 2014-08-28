package com.elijahfreestone.clientcontactpro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;

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

	public void createReminderIntent(String clientName, String contactMethod, String emailAddress, String phoneNumber){
		
		if (contactMethod.equalsIgnoreCase("email")) {
			Intent emailIntent = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
			//Intent reminderIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto:", "e_freestone@yahoo.com", null));
			emailIntent.setType("message/rfc822");
			emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "e_freestone@yahoo.com" });
			emailIntent.putExtra(Intent.EXTRA_SUBJECT, "test");
			emailIntent.putExtra(Intent.EXTRA_TEXT, "This is a sleep test. Client name: " + clientName);
			
			myContext.startActivity(Intent.createChooser(emailIntent, "Choose an email client"));
			
		} else {
			SmsManager smsManager = SmsManager.getDefault();
			String testMessage = "Test message clientName: " + clientName;
			smsManager.sendTextMessage(phoneNumber, null, testMessage, null, null);
		}
		
		
		
//		String number = "(970)988-8190";  // The number on which you want to send SMS  
//        myContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
		
//		Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
//	            "mailto","abc@gmail.com", null));
		
	} //createReminderIntent close
	
	
	

}
