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

import android.app.Activity;
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
public class ClientsFragment extends Fragment implements OnItemClickListener{
	static String TAG = "ClientsFragment";
	static ListView clientListView;
	public static View footerView;
	Context myContext;
	
	String clientName, clientAddress, phoneNumber, emailAddress, contactMethod, basicInfo;
	String nextAppointment, appointmentType;
	
//	/**
//	 * The Interface onListItemSelected.
//	 */
//	public interface OnListItemSelected {
//		void startActivityForResult(Intent detailsIntent, int requestCode);
//	}
//
//	private OnListItemSelected parentActivity;
//
//	/*
//	 * (non-Javadoc)
//	 * @see android.app.Fragment#onAttach(android.app.Activity)
//	 */
//	@Override
//	public void onAttach(Activity activity) {
//		super.onAttach(activity);
//		if (activity instanceof OnListItemSelected) {
//			parentActivity = (OnListItemSelected) activity;
//		} else {
//			Log.e(TAG, "Must implement OnListItemSelected");
//		}
//	} // onAttach Close
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//Inflate view
		View rootView = inflater.inflate(R.layout.fragment_clients, container, false);
		
		myContext = MainActivity.myContext; 
		
		clientListView = (ListView) rootView.findViewById(R.id.clientListView); 	
		
		if (clientListView != null) {  
			Log.i(TAG, "clientListView != null");   
			//JSONData.displayDataFromFile(); 
			clientListView.setAdapter(JSONData.clientListAdapter);
			clientListView.setOnItemClickListener(this);
			//clientListView.addFooterView(footerView);
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
		
		//clientListView.setOnItemClickListener(this);
		
		return rootView;      
	} //onCreateView close 

	/* (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) { 
		Log.i(TAG, "Item " + position + " clicked");
		
		//ClientDetailsFragment clientDetailsFragment = (ClientDetailsFragment) getFragmentManager().findFragmentById(R.id.clientDetailsFragment);
		
		Intent clientDetailsIntent = new Intent(MainActivity.myContext, ClientDetails.class);
		startActivity(clientDetailsIntent); 
		
	} //onItemClick close
	
	/*
	 * onCancelClick is triggered when the Cancel Appointments button is clicked.
	 * It creates a basic intent and starts the cancel activity
	 */
	void onCancelClick(View view){ 
		Log.i(TAG, "Cancel Appointment clicked");
		
		Intent cancelButtonIntent = new Intent(myContext, CancelAppointmentActivity.class);
		startActivity(cancelButtonIntent);
	} //onCancelClick close
}
