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
	String startTimeAndDate, endTimeAndDate, appointmentType, appointmentAddress, clientName, 
	phoneNumber, emailAddress, contactMethod, basicInfo, otherContacts, clientAddress, nextAppointment;
	int clientPosition;
	long clientID;
	
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
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		//Grab intent extras to be displayed in textviews
		Intent appointmentDetailsIntent = getIntent();
		startTimeAndDate = appointmentDetailsIntent.getStringExtra("startTimeAndDate");
		endTimeAndDate = appointmentDetailsIntent.getStringExtra("endTimeAndDate");
		appointmentType = appointmentDetailsIntent.getStringExtra("appointmentType");
		appointmentAddress = appointmentDetailsIntent.getStringExtra("appointmentAddress");
		clientName = appointmentDetailsIntent.getStringExtra("clientName");
		phoneNumber = appointmentDetailsIntent.getStringExtra("phoneNumber");
		emailAddress = appointmentDetailsIntent.getStringExtra("emailAddress");
		contactMethod = appointmentDetailsIntent.getStringExtra("contactMethod");
		basicInfo = appointmentDetailsIntent.getStringExtra("basicInfo");
		otherContacts = appointmentDetailsIntent.getStringExtra("otherContacts");
		clientAddress = appointmentDetailsIntent.getStringExtra("clientAddress");
		nextAppointment = appointmentDetailsIntent.getStringExtra("nextAppointment");
		
		clientPosition = appointmentDetailsIntent.getIntExtra("clientPosition", 30);
		//Log.i(TAG, "clientPosition: " + clientPosition);
		clientID = appointmentDetailsIntent.getLongExtra("clientID", 35);
		//Log.i(TAG, "clientID: " + clientID);
		
//		appointmentDetailsIntent.putExtra("nextAppointment", nextAppointment);
//		appointmentDetailsIntent.putExtra("clientPosition", position);
//		appointmentDetailsIntent.putExtra("clientID", id);
		
		if (appointmentDetailsIntent != null) {
			//Log.i(TAG, "Existing Appointment Details Intent");
			if (appointmentDetailsFragment != null) {
				Log.i(TAG, "Appointment Details Fragment EXISTS");
				//Call display method
				appointmentDetailsFragment.displayAppointmentDetails(
						startTimeAndDate, endTimeAndDate, appointmentType,
						appointmentAddress, clientName, phoneNumber,
						emailAddress, contactMethod, basicInfo, otherContacts,
						clientAddress, nextAppointment);
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
        	Intent settingsIntent = new Intent(myContext, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        
        if (id == R.id.newPlusButton) {
			Log.i(TAG, "Plus clicked");
			onPlusClick();
		}
        
        if (id == android.R.id.home) {
        	int tabPosition = 1;
        	MainActivity.myActionBar.setSelectedNavigationItem(tabPosition); 
        	finish();
		}
        
        return super.onOptionsItemSelected(item);
    }
    
    /*
     * On plus click is trigger when the Plus icon is clicked.
     * Starts New Appointment Activity and passes client info
     */
    void onPlusClick(){
    	// Create explicit intent and pass details as extras
    	Intent newAppointmentIntent = new Intent(myContext, NewAppointmentActivity.class);
    	newAppointmentIntent.putExtra("clientName", clientName);
    	newAppointmentIntent.putExtra("clientAddress", clientAddress);
    	newAppointmentIntent.putExtra("phoneNumber", phoneNumber);
    	newAppointmentIntent.putExtra("emailAddress", emailAddress);
    	newAppointmentIntent.putExtra("contactMethod", contactMethod);
    	newAppointmentIntent.putExtra("basicInfo", basicInfo);

    	newAppointmentIntent.putExtra("nextAppointment", nextAppointment);
    	newAppointmentIntent.putExtra("appointmentType", appointmentType);
    	
    	newAppointmentIntent.putExtra("clientPosition", clientPosition);
    	newAppointmentIntent.putExtra("clientID", clientID);
		
    	if (newAppointmentIntent != null) {
    		startActivityForResult(newAppointmentIntent, 0); 
		}
				
    } //onPlusClick close
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent detailsBackIntent) {
		Log.i(TAG, "On Activity Result"); 
		//super.onActivityResult(requestCode, resultCode, detailsBackIntent);
		if (resultCode == RESULT_OK && requestCode == 0) {
			Log.i(TAG, "onActivityResult resultCode = OK");
			if (detailsBackIntent.hasExtra("allClients")) {
				Log.i(TAG, "Back Intent has extra");
				String passedAllClientsString = detailsBackIntent.getExtras().getString("allClients");
				MainActivity.forceRefreshListViews(passedAllClientsString);
//				JSONData.displayDataFromFile(passedAllClientsString);
//				//Force view pager to rebuild and in turn refresh client listview
//				myViewPager.setAdapter(mySectionsPagerAdapter); 
			}
		} 
	} //onActivityResult close


}
