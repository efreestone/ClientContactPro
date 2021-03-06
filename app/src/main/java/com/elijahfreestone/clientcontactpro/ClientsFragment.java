/*
 * @author	Elijah Freestone 
 *
 * Project	ClientContactPro
 * 
 * Package	com.elijahfreestone.clientcontactpro
 * 
 * Date		Aug 12, 2014
 */

package com.elijahfreestone.clientcontactpro;

import java.util.HashMap;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

// TODO: Auto-generated Javadoc
/**
 * The Class ClientsFragment. 
 */
public class ClientsFragment extends Fragment implements OnItemClickListener {
	static String TAG = "ClientsFragment";
	static ListView clientListView;
	public static View footerView;
	static Context myContext;
	static View rootView;
	private static ClientsFragment clientsFragmentInstance;
	
	String clientName, clientAddress, phoneNumber, emailAddress, contactMethod, basicInfo;
	String nextAppointment, appointmentType, startTimeAndDate, endTimeAndDate, appointmentAddress, otherContacts;
	
	HashMap<String, String> selectedClient;
	
	// Create getInstance method and check if instance exists
	public static ClientsFragment getInstance() {
		if (clientsFragmentInstance == null) {
			clientsFragmentInstance = new ClientsFragment();
		}
		return clientsFragmentInstance;
	}

	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//Inflate view
		rootView = inflater.inflate(R.layout.fragment_clients, container, false);
		
		myContext = MainActivity.myContext; 
		
		clientListView = (ListView) rootView.findViewById(R.id.clientListView); 	
		
		if (clientListView != null) {  
			Log.i(TAG, "clientListView != null");  
			//Set adapter and click listener
			clientListView.setAdapter(JSONData.clientListAdapter);
			clientListView.setOnItemClickListener(this);
		} else {
			Log.i(TAG, "clientListView == null"); 
		} 
		
		//Grab cancel button and set onClick
		Button cancelButton = (Button) rootView.findViewById(R.id.cancelButton);
		cancelButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//onCancelClick creates a basic intent and starts the cancel activity
				onCancelClick(v);
			}
		}); 
		
		return rootView;      
	} //onCreateView close  

	/* (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) { 
		// ClientDetailsFragment detailsFragment = (ClientDetailsFragment)
		// getFragmentManager().findFragmentById(R.id.clientDetailsFragment);
		
		//Log.i(TAG, "Item " + position + " clicked");
		// Grab object selected from ArrayList as a HashMap and split into details strings.
		selectedClient = JSONData.clientList.get(position); 
		clientName = selectedClient.get("clientName"); 
		clientAddress = selectedClient.get("clientAddress");
		phoneNumber = selectedClient.get("phoneNumber");
		emailAddress = selectedClient.get("emailAddress");
		contactMethod = selectedClient.get("contactMethod");
		basicInfo = selectedClient.get("basicInfo");
		nextAppointment = selectedClient.get("nextAppointment");
		appointmentType = selectedClient.get("appointmentType");
		startTimeAndDate = selectedClient.get("startTimeAndDate");
		endTimeAndDate = selectedClient.get("endTimeAndDate");
		appointmentAddress = selectedClient.get("appointmentAddress");
		otherContacts = selectedClient.get("otherContacts");
		Log.i(TAG, "Client Selected: " + clientName);
		
		// Create explicit intent and pass details as extras
		Intent clientDetailsIntent = new Intent(MainActivity.myContext, ClientDetails.class);
		clientDetailsIntent.putExtra("clientName", clientName);
		clientDetailsIntent.putExtra("clientAddress", clientAddress);
		clientDetailsIntent.putExtra("phoneNumber", phoneNumber);
		clientDetailsIntent.putExtra("emailAddress", emailAddress);
		clientDetailsIntent.putExtra("contactMethod", contactMethod);
		clientDetailsIntent.putExtra("basicInfo", basicInfo);
		clientDetailsIntent.putExtra("nextAppointment", nextAppointment);
		clientDetailsIntent.putExtra("appointmentType", appointmentType); 
		clientDetailsIntent.putExtra("startTimeAndDate", startTimeAndDate);
		clientDetailsIntent.putExtra("endTimeAndDate", endTimeAndDate);
		clientDetailsIntent.putExtra("appointmentAddress", appointmentAddress);
		clientDetailsIntent.putExtra("otherContacts", otherContacts);
		
		clientDetailsIntent.putExtra("clientPosition", position);
		clientDetailsIntent.putExtra("clientID", id);
		
		if (clientDetailsIntent != null) {
			startActivity(clientDetailsIntent); 
		}
		
	} //onItemClick close
	
	/*
	 * onCancelClick is triggered when the Cancel Appointments button is clicked.
	 * It creates a basic intent and starts the cancel activity
	 */
	void onCancelClick(View view) {
		Log.i(TAG, "Cancel Appointment clicked");

		Intent cancelButtonIntent = new Intent(myContext,
				CancelAppointmentActivity.class);
		startActivity(cancelButtonIntent);
	} // onCancelClick close

}
