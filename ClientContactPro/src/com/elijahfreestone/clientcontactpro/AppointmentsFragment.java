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
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class AppointmentsFragment extends Fragment implements OnItemClickListener{
	String TAG = "AppointmentsFragment";
	static ListView appointmentsListView;
	Context myContext;
	View rootView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//return super.onCreateView(inflater, container, savedInstanceState);
		rootView = inflater.inflate(R.layout.fragment_appointments, container, false);
		
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
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}
	
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
