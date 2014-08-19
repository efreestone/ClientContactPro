/*
 * @author	Elijah Freestone 
 *
 * Project	ClientContactPro
 * 
 * Package	com.elijahfreestone.clientcontactpro
 * 
 * Date		Aug 14, 2014
 */

package com.elijahfreestone.clientcontactpro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

// TODO: Auto-generated Javadoc
/**
 * The Class AppointmentDetails.
 */
public class AppointmentDetails extends Activity {
	String TAG = "AppointmentDetails";
	Context myContext = MainActivity.myContext;
	String startTime, endTime, appointmentType, appointmentAddress, clientName, phoneNumber, emailAddress, contactMethod, basicInfo, otherContacts;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_details_appointment);
		
		AppointmentDetailsFragment appointmentDetailsFragment = (AppointmentDetailsFragment) getFragmentManager()
				.findFragmentById(R.id.appointmentDetailsFragment);
		
		//Grab intent extras to be displayed in textviews
		Intent appointmentDetailsIntent = getIntent();
		startTime = appointmentDetailsIntent.getStringExtra("startTime");
		endTime = appointmentDetailsIntent.getStringExtra("endTime");
		appointmentType = appointmentDetailsIntent.getStringExtra("appointmentType");
		appointmentAddress = appointmentDetailsIntent.getStringExtra("appointmentAddress");
		clientName = appointmentDetailsIntent.getStringExtra("clientName");
		phoneNumber = appointmentDetailsIntent.getStringExtra("phoneNumber");
		emailAddress = appointmentDetailsIntent.getStringExtra("emailAddress");
		contactMethod = appointmentDetailsIntent.getStringExtra("contactMethod");
		basicInfo = appointmentDetailsIntent.getStringExtra("basicInfo");
		otherContacts = appointmentDetailsIntent.getStringExtra("otherContacts");
		
		if (appointmentDetailsIntent != null) {
			//Log.i(TAG, "Existing Appointment Details Intent");
			if (appointmentDetailsFragment != null) {
				Log.i(TAG, "Appointment Details Fragment EXISTS");
				//Call display method
				appointmentDetailsFragment.displayAppointmentDetails(startTime,
						endTime, appointmentType, appointmentAddress,
						clientName, phoneNumber, emailAddress, contactMethod,
						basicInfo, otherContacts);
			} else {
				Log.i(TAG, "Appointment Details Fragment NULL");
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    } 

    /* (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override 
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        
        if (id == R.id.newPlusButton) {
			Log.i(TAG, "Plus clicked");
			onPlusClick();
		}
        
        return super.onOptionsItemSelected(item);
    }
    
    /*
     * On plus click is trigger when the Plus icon is clicked.
     * Starts New Appointment Activity and passes client info
     */
    void onPlusClick(){
    	Intent newAppointmentIntent = new Intent(myContext, NewAppointmentActivity.class);
		startActivity(newAppointmentIntent);
    }

}
