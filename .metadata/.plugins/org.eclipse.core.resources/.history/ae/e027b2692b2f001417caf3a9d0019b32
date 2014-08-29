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
 * The Class AppointmentsFragment.
 */
public class AppointmentsFragment extends Fragment implements OnItemClickListener {
	String TAG = "AppointmentsFragment";
	static ListView appointmentsListView;
	Context myContext;
	
	String clientName, clientAddress, phoneNumber, emailAddress, contactMethod, basicInfo;
	String nextAppointment, appointmentType, startTimeAndDate, endTimeAndDate, appointmentAddress, otherContacts;
	
	HashMap<String, String> selectedClient;
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override  
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//Inflate view
		View rootView = inflater.inflate(R.layout.fragment_appointments, container, false);
		
		myContext = MainActivity.myContext;
		
		appointmentsListView = (ListView) rootView.findViewById(R.id.appointmentListView); 
		
		if (appointmentsListView != null) {  
			Log.i(TAG, "appointmentsListView != null");   
			//Set adapter and click listener
			appointmentsListView.setAdapter(JSONData.appointmentListAdapter);
			appointmentsListView.setOnItemClickListener(this);
		} else {
			Log.i(TAG, "appointmentsListView == null");  
		}  
		
		// Grab cancel button and set onClick
		Button cancelButton = (Button) rootView.findViewById(R.id.cancelButton);
		cancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//onCancelClick creates a basic intent and starts the cancel activity
				onCancelClick(v);
			}
		}); 
		
		//appointmentsListView.setOnItemClickListener(this);
		
		return rootView; 
	} //onCreateView close

	/* (non-Javadoc) 
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		//Intent appointmentDetailsIntent = new Intent(MainActivity.myContext, AppointmentDetails.class);
		//startActivity(); 
		
		// Grab object selected from ArrayList as a HashMap and split into
		// details strings.
		selectedClient = JSONData.appointmentList.get(position);
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
		
		Log.i(TAG, "Client Selected: " + clientName + "\nTime and date: " + startTimeAndDate);

		// Create explicit intent and pass details as extras
		Intent appointmentDetailsIntent = new Intent(MainActivity.myContext,
				AppointmentDetails.class);
		appointmentDetailsIntent.putExtra("clientName", clientName);
		appointmentDetailsIntent.putExtra("clientAddress", clientAddress);
		appointmentDetailsIntent.putExtra("phoneNumber", phoneNumber);
		appointmentDetailsIntent.putExtra("emailAddress", emailAddress);
		appointmentDetailsIntent.putExtra("contactMethod", contactMethod);
		appointmentDetailsIntent.putExtra("basicInfo", basicInfo);
		appointmentDetailsIntent.putExtra("nextAppointment", nextAppointment);
		appointmentDetailsIntent.putExtra("appointmentType", appointmentType);
		appointmentDetailsIntent.putExtra("startTimeAndDate", startTimeAndDate);
		appointmentDetailsIntent.putExtra("endTimeAndDate", endTimeAndDate);
		appointmentDetailsIntent.putExtra("appointmentAddress", appointmentAddress);
		appointmentDetailsIntent.putExtra("otherContacts", otherContacts);
		appointmentDetailsIntent.putExtra("clientPosition", position);
		appointmentDetailsIntent.putExtra("clientID", id);

		if (appointmentDetailsIntent != null) {
			startActivity(appointmentDetailsIntent);
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
