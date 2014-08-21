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
 * The Class ClientDetails.
 */
public class ClientDetails extends Activity {
	String TAG = "ClientDetails";
	Context myContext = MainActivity.myContext;
	String clientName, clientAddress, phoneNumber, emailAddress, contactMethod, basicInfo;
	String nextAppointment, appointmentType;
	int clientPosition;
	long clientID;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_details_client);
		
		ClientDetailsFragment clientDetailsFragment = (ClientDetailsFragment) getFragmentManager()
				.findFragmentById(R.id.clientDetailsFragment);
		
		//Grab intent extras to be displayed in textviews
		Intent clientDetailsIntent = getIntent();
		clientName = clientDetailsIntent.getStringExtra("clientName");
		clientAddress = clientDetailsIntent.getStringExtra("clientAddress");
		phoneNumber = clientDetailsIntent.getStringExtra("phoneNumber");
		emailAddress = clientDetailsIntent.getStringExtra("emailAddress");
		contactMethod = clientDetailsIntent.getStringExtra("contactMethod");
		basicInfo = clientDetailsIntent.getStringExtra("basicInfo");
		nextAppointment = clientDetailsIntent.getStringExtra("nextAppointment");
		appointmentType = clientDetailsIntent.getStringExtra("appointmentType");
		
		clientPosition = clientDetailsIntent.getIntExtra("clientPosition", 1);
		//Log.i(TAG, "clientPosition: " + clientPosition);
		clientID = clientDetailsIntent.getLongExtra("clientID", 1);
		//Log.i(TAG, "clientID: " + clientID);
		
		
		
		if (clientDetailsIntent != null) {
			//Log.i(TAG, "Existing Client Details Intent");
			if (clientDetailsFragment != null) {
				Log.i(TAG, "Client Details Fragment EXISTS");
				//Call display method
				clientDetailsFragment.displayClientDetails(clientName,
						clientAddress, phoneNumber, emailAddress,
						contactMethod, basicInfo, nextAppointment,
						appointmentType);
			} else {
				Log.i(TAG, "Client Details Fragment NULL");
			}
		}
		
	} //onCreate close
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    } //onCreateOptionsMenu close

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
    } //onOptionsItemSelected close
    
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
    		startActivity(newAppointmentIntent);
		}
				
    } //onPlusClick close

}
