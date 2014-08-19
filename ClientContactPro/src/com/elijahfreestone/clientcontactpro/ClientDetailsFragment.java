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

public class ClientDetailsFragment extends Fragment{
	String TAG = "ClientDetailsFragment";
	Context myContext;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View detailsView = inflater.inflate(R.layout.details_client, container);
		
		myContext = MainActivity.myContext;

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
	
}
