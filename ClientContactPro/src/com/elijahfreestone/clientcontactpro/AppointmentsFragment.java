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

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

// TODO: Auto-generated Javadoc
/**
 * The Class AppointmentsFragment.
 */
public class AppointmentsFragment extends Fragment implements OnItemClickListener{
	String TAG = "AppointmentsFragment";
	static ListView appointmentsListView;
	Context myContext;
	View rootView;
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//Inflate view
		rootView = inflater.inflate(R.layout.fragment_appointments, container, false);
		
		//View cancelSubview = inflater.inflate(R.layout.subview_cancel_button, container);
		
		appointmentsListView = (ListView) rootView.findViewById(R.id.appointmentListView); 
		
		if (appointmentsListView != null) {  
			Log.i(TAG, "appointmentsListView != null");   
			//JSONData.displayDataFromFile(); 
			appointmentsListView.setAdapter(JSONData.appointmentListAdapter);
			appointmentsListView.setOnItemClickListener(this);
		} else {
			Log.i(TAG, "appointmentsListView == null"); 
		}  
		
		
		return rootView; 
	} //onCreateView close

	/* (non-Javadoc) 
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent appointmentDetailsIntent = new Intent(MainActivity.myContext, AppointmentDetails.class);
		startActivity(appointmentDetailsIntent); 
		
	} //onItemClick close
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onStart()
	 */
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		appointmentsListView = (ListView) rootView.findViewById(R.id.clientListView);
		if (appointmentsListView == null) {
			Log.i(TAG, "onStart list view null");
		}
		Log.i(TAG, "onStart"); 
	}

}
