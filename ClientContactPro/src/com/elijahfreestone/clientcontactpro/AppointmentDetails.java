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

public class AppointmentDetails extends Activity {
	String TAG = "AppointmentDetails";
	Context myContext = MainActivity.myContext;
	String clientName, clientAddress, phoneNumber, emailAddress, contactMethod, basicInfo;
	String nextAppointment, appointmentType;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_details_appointment);
		
		AppointmentDetailsFragment appointmentDetailsFragment = (AppointmentDetailsFragment) getFragmentManager()
				.findFragmentById(R.id.appointmentDetailsFragment);
		
		//Grab intent extras to be displayed in textviews
		Intent appointmentDetailsIntent = getIntent();
		clientName = appointmentDetailsIntent.getStringExtra("clientName");
		clientAddress = appointmentDetailsIntent.getStringExtra("clientAddress");
		phoneNumber = appointmentDetailsIntent.getStringExtra("phoneNumber");
		emailAddress = appointmentDetailsIntent.getStringExtra("emailAddress");
		contactMethod = appointmentDetailsIntent.getStringExtra("contactMethod");
		basicInfo = appointmentDetailsIntent.getStringExtra("basicInfo");
		nextAppointment = appointmentDetailsIntent.getStringExtra("nextAppointment");
		appointmentType = appointmentDetailsIntent.getStringExtra("appointmentType");
		
		if (appointmentDetailsIntent != null) {
			//Log.i(TAG, "Existing Appointment Details Intent");
			if (appointmentDetailsFragment != null) {
				Log.i(TAG, "Appointment Details Fragment EXISTS");
			} else {
				Log.i(TAG, "Appointment Details Fragment NULL");
			}
		}
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    } 

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
    
    void onPlusClick(){
    	Intent newAppointmentIntent = new Intent(myContext, NewAppointmentActivity.class);
		startActivity(newAppointmentIntent);
    }

}
