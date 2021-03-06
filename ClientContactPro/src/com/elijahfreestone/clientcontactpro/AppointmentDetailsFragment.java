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

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
/**
 * The Class AppointmentDetailsFragment.
 */
public class AppointmentDetailsFragment extends Fragment{
	String TAG = "AppointmentDetailsFragment";
	Context myContext;
	TextView appStartTimeTV, appEndTimeTV, appAppointmentTypeTV, appAddressTV, appClientNameTV, 
	appPhoneNumberTV, appEmailTV, appContactMethodTV, appBasicInfoTV, appOtherContactTV;
	boolean notifyIsChecked;
	
	String clientName, contactMethod, emailAddress, phoneNumber, startTimeAndDate;
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View detailsView = inflater.inflate(R.layout.details_appointment, container);
		
		clientName = AppointmentDetails.clientName;
		contactMethod = AppointmentDetails.contactMethod;
		emailAddress = AppointmentDetails.emailAddress;
		phoneNumber = AppointmentDetails.phoneNumber;
		startTimeAndDate = AppointmentDetails.startTimeAndDate;
		
		myContext = AppointmentDetails.myContext;
		
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
		
//		Intent cancelButtonIntent = new Intent(myContext, CancelAppointmentActivity.class);
//		//Intent cancelButtonIntent = new Intent()
//		startActivity(cancelButtonIntent);
		showCancelAlert();
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
	
	void showCancelAlert(){
		//AlertDialog alertDialog = new AlertDialog.Builder(myContext)
		View checkboxView = View.inflate(myContext, R.layout.checkbox_view, null);
		CheckBox contactCheckbox = (CheckBox) checkboxView.findViewById(R.id.contactCheckbox);
		contactCheckbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				notifyIsChecked = isChecked;
				
			}
		});
		
		AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
		alertDialog.setTitle("Cancel Appointment?");
		alertDialog.setView(checkboxView);
		alertDialog.setMessage("Cancel this appointment only?");
		alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "YES", (new DialogInterface.OnClickListener() {
			//Cancel confirmed
			@Override 
			public void onClick(DialogInterface dialog, int which) {
				
				ClientManager.removeAppFromHashMap(clientName);
				//ClientManager.cancelAppointments(clientsWithAppsArrayList);
				if (notifyIsChecked) {
					Log.i(TAG, "Notify checked");
					AppointmentDetails.startContactIntent();
				}
				AppointmentDetails.detailsActivity.finish();
			}
		}));
		alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "NO", (new DialogInterface.OnClickListener() {
			//Cancel aborted
			@Override 
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(myContext, "No appointments canceled!", Toast.LENGTH_LONG).show();
			}
		}));
		alertDialog.show(); 
	} //showCancelAlert close
	
}
