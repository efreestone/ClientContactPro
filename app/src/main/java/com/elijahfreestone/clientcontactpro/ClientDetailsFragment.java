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

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class ClientDetailsFragment.
 */
public class ClientDetailsFragment extends Fragment{
	String TAG = "ClientDetailsFragment";
	Context myContext;
	TextView clientNameTV, clientAddressTV, clientPhoneNumberTV, clientEmailTV, clientContactTV, 
		clientBasicInfoTV, clientNextAppTV, clientAppTypeTV;
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View detailsView = inflater.inflate(R.layout.details_client, container);
		
		myContext = MainActivity.myContext;
		
		//Grab textviews
		clientNameTV = (TextView) detailsView.findViewById(R.id.clientNameTV);
		clientAddressTV = (TextView) detailsView.findViewById(R.id.clientAddressTV);
		clientPhoneNumberTV = (TextView) detailsView.findViewById(R.id.clientPhoneNumberTV);
		clientEmailTV = (TextView) detailsView.findViewById(R.id.clientEmailTV);
		clientContactTV = (TextView) detailsView.findViewById(R.id.clientContactTV);
		clientBasicInfoTV = (TextView) detailsView.findViewById(R.id.clientBasicInfoTV);
		clientNextAppTV = (TextView) detailsView.findViewById(R.id.clientNextAppTV);
		clientAppTypeTV = (TextView) detailsView.findViewById(R.id.clientAppTypeTV); 

		// Grab cancel button and set onClick
		Button cancelButton = (Button) detailsView.findViewById(R.id.cancelButton);
		cancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// onCancelClick creates a basic intent and starts the cancel activity
				onCancelClick(v);
			}
		}); 

		return detailsView;
	} // onCreateView close
	
	/*
	 * onCancelClick is triggered when the Cancel Appointments button is clicked.
	 * It creates a basic intent and starts the cancel activity
	 */
	void onCancelClick(View view){ 
		Log.i(TAG, "Cancel Appointment clicked");
		
		Intent cancelButtonIntent = new Intent(myContext, CancelAppointmentActivity.class);
		//Intent cancelButtonIntent = new Intent()
		startActivity(cancelButtonIntent);
	} //onCancelClick close
	
	/*
	* displayClientDetails displays details of the client selected, called from
	* DetailsActivity
	*/
	public void displayClientDetails(String clientName, String clientAddress,
			String phoneNumber, String emailAddress, String contactMethod,
			String basicInfo, String nextAppointment, String appointmentType) {
		
		// Set textviews to passed strings
		clientNameTV.setText(clientName);
		clientAddressTV.setText(clientAddress);
		clientPhoneNumberTV.setText(phoneNumber);
		clientEmailTV.setText(emailAddress);
		clientContactTV.setText(contactMethod);
		clientBasicInfoTV.setText(basicInfo);
		clientNextAppTV.setText(nextAppointment);
		clientAppTypeTV.setText(appointmentType);
		
		Log.i(TAG, "displayClientData called");
	} //displayClientDetails close
	
}
