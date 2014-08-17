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
 * The Class ClientsFragment.
 */
public class ClientsFragment extends Fragment implements OnItemClickListener{
	static String TAG = "ClientsFragment";
	static ListView clientListView;
	public static View footerView;
	Context myContext;
	
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
		
		return rootView;      
	} //onCreateView close 

	/* (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) { 
		Log.i(TAG, "Item " + position + " clicked");
		
		Intent clientDetailsIntent = new Intent(MainActivity.myContext, ClientDetails.class);
		startActivity(clientDetailsIntent); 
		
	} //onItemClick close
}
