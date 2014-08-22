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
 * The Class AppointmentDetailsFragment.
 */
public class AppointmentDetailsFragment extends Fragment{
	String TAG = "AppointmentDetailsFragment";
	Context myContext;
	TextView appStartTimeTV, appEndTimeTV, appAppointmentTypeTV, appAddressTV, appClientNameTV, 
	appPhoneNumberTV, appEmailTV, appContactMethodTV, appBasicInfoTV, appOtherContactTV;
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View detailsView = inflater.inflate(R.layout.details_appointment, container);
		
		myContext = MainActivity.myContext;
		
		//Grab textviews
		appStartTimeTV = (TextView) detailsView.findViewById(R.id.appStartTimeTV);
		appEndTimeTV = (TextView) detailsView.findViewById(R.id.appEndTimeTV);
		appAppointmentTypeTV = (TextView) detailsView.findViewById(R.id.appAppointmentTypeTV);
		appAddressTV = (TextView) detailsView.findViewById(R.id.appAddressTV);
		
		appClientNameTV = (TextView) detailsView.findViewById(R.id.appClientNameTV);
		appPhoneNumberTV = (TextView) detailsView.findViewById(R.id.appPhoneNumberTV);
		appEmailTV = (TextView) detailsView.findViewById(R.id.appEmailTV);
		appContactMethodTV = (TextView) detailsView.findViewById(R.id.appContactMethodTV);
		appBasicInfoTV = (TextView) detailsView.findViewById(R.id.appBasicInfoTV);
		appOtherContactTV = (TextView) detailsView.findViewById(R.id.appOtherContactTV);
		
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
	} //onCreateView 
	
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
	 * displayAppointmentDetails displays details of the appointment selected,
	 * called from DetailsActivity
	 */
	public void displayAppointmentDetails(String startTimeAndDate, String endTimeAndDate,
			String appointmentType, String appointmentAddress,
			String clientName, String phoneNumber, String emailAddress,
			String contactMethod, String basicInfo, String otherContacts,
			String clientAddress, String nextAppointment) {

		// Set top section textviews to passed strings
		appStartTimeTV.setText(startTimeAndDate);
		appEndTimeTV.setText(endTimeAndDate);
		appAppointmentTypeTV.setText(appointmentType);
		appAddressTV.setText(appointmentAddress);
		//Bottom section
		appClientNameTV.setText(clientName);
		appPhoneNumberTV.setText(phoneNumber);
		appEmailTV.setText(emailAddress);
		appContactMethodTV.setText(contactMethod);
		appBasicInfoTV.setText(basicInfo);
		appOtherContactTV.setText(otherContacts);

		Log.i(TAG, "displayAppointmentDetails called");
	} // displayClientDetails close
	
}
