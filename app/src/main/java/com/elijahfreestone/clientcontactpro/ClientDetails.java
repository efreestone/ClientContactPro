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

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
	String clientName, clientAddress, phoneNumber, emailAddress, contactMethod, basicInfo, nextAppointment;
	String appointmentType, startTimeAndDate, endTimeAndDate, appointmentAddress, otherContacts;
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
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
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
		startTimeAndDate = clientDetailsIntent.getStringExtra("startTimeAndDate");
		endTimeAndDate = clientDetailsIntent.getStringExtra("endTimeAndDate");
		appointmentAddress = clientDetailsIntent.getStringExtra("appointmentAddress");
		otherContacts = clientDetailsIntent.getStringExtra("otherContacts");
		
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
        getMenuInflater().inflate(R.menu.details_action_bar, menu);
		
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
        	Intent settingsIntent = new Intent(myContext, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        
        if (id == R.id.newPlusButton) {
			Log.i(TAG, "Plus clicked");
			onPlusClick();
		}
        
        if (id == android.R.id.home) {
        	finish();
		}
        
        if (id == R.id.deleteClient) {
        	onDeleteClient();
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
    	
    	newAppointmentIntent.putExtra("startTimeAndDate", startTimeAndDate);
    	newAppointmentIntent.putExtra("endTimeAndDate", endTimeAndDate);
    	newAppointmentIntent.putExtra("appointmentAddress", appointmentAddress);
    	newAppointmentIntent.putExtra("otherContacts", otherContacts);
    	
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
			//finish();
			Log.i(TAG, "onActivityResult resultCode = OK");
			if (detailsBackIntent.hasExtra("allClients")) {
				Log.i(TAG, "Back Intent has extra");
				//String passedAllClientsString = detailsBackIntent.getExtras().getString("allClients");
				//Log.i(TAG, "passed string: " + passedAllClientsString);
				
				//Force view pager to rebuild and in turn refresh client listview
				MainActivity.myViewPager.setAdapter(MainActivity.mySectionsPagerAdapter);  
			}
		}
	} //onActivityResult close
    
    void onDeleteClient(){
    	AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Delete Client?");
		alertDialog.setMessage("Remove this client from the device? This can not be undone.");
		alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "YES", (new DialogInterface.OnClickListener() {
			//Cancel confirmed
			@Override 
			public void onClick(DialogInterface dialog, int which) {
				ArrayList<HashMap<String, String>> fullClientArrayList = JSONData.getClientArrayList();
				int mapPosition = -1;
				for (HashMap<String, String> map : fullClientArrayList){
					//Check if element with client name exists
			        if(map.containsValue(clientName))
			        {
			        	mapPosition = fullClientArrayList.indexOf(map);
			        	//Pass position of duplicate element for replacement
			        	Log.i(TAG, "HashMap at position" + map);
			            //Log.i(TAG, "Position " + mapPosition);
			        	if (mapPosition != -1) {
			        		//Remove client
							fullClientArrayList.remove(mapPosition);
						}
			                break;
			        }
			    }
				String passedAllClientsString = fullClientArrayList.toString();

				JSONData.convertArrayListToJSON(fullClientArrayList);
				 
				MainActivity.myViewPager.setAdapter(MainActivity.mySectionsPagerAdapter); 
				MainActivity.forceRefreshListViews(passedAllClientsString);
				finish();
			}
		}));
		alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "NO", (DialogInterface.OnClickListener) null);
		alertDialog.show();
    	
    } //onDeleteClient close

}
