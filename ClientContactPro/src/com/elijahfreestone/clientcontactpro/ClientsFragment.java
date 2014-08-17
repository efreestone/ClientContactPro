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
//		View rootView = inflater.inflate(R.layout.container_layout, container, false);
//		ScrollView scrollView = (ScrollView) rootView.findViewById(R.id.scrollableContent);
//		View viewWithList = inflater.inflate(R.layout.fragment_clients, scrollView);
		View rootView = inflater.inflate(R.layout.fragment_clients, container, false);
		
		myContext = MainActivity.myContext; 
		
		//clientListView = (ListView) viewWithList.findViewById(R.id.clientListView);   
		clientListView = (ListView) rootView.findViewById(R.id.clientListView); 
		//View listHeader = inflater.inflate(R.layout.subview_cancel_button, null);
		//clientListView.addFooterView(listHeader);
		//myListView = (ListView) mainView.findViewById(R.id.listView);
		//View listHeader = inflater.inflate(R.layout.listview_header, null);
		//myListView.addHeaderView(listHeader); 
		
//		View footerView = inflater.inflate(R.layout.subview_cancel_button, container);
//		
//		if (footerView != null) {
//			Log.i(TAG, "Footer layout exists"); 
//			//clientListView.addFooterView(footerView);
//		} else {
//			Log.i(TAG, "Footer layout null");  
//		}    
		
		
		if (clientListView != null) {  
			Log.i(TAG, "clientListView != null");   
			//JSONData.displayDataFromFile(); 
			clientListView.setAdapter(JSONData.clientListAdapter);
			clientListView.setOnItemClickListener(this);
			//clientListView.addFooterView(footerView);
		} else {
			Log.i(TAG, "clientListView == null"); 
		} 
		
//		//Create linear layout instance
//        LinearLayout myLayout = new LinearLayout(getActivity());
//        //Set orientation
//        myLayout.setOrientation(LinearLayout.VERTICAL);
//        //Set my layout parameters to match the parent item
//        myLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//        //Create center params used to center various items
//        LinearLayout.LayoutParams centerParams = new LinearLayout.LayoutParams(
//        LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//        //Set gravity to center
//        centerParams.gravity = Gravity.CENTER;
//        //Create title text view
//        TextView titleView = new TextView(getActivity());
//        //Set text for title from resources
//        titleView.setText(R.string.app_name);
//        //Add title with centered params
//        myLayout.addView(titleView, centerParams);
//        
//        ((ViewGroup)rootView).addView(myLayout);
		
		//Fragment cancelLayout = getChildFragmentManager().findFragmentById(R.id.cancelLinear);
//		if (cancelLayout != null) {
//			Log.i(TAG, "Cancel layout exists"); 
//			//rootView.in
//		} else {
//			Log.i(TAG, "Cancel layout null");  
//		}  
		
		
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
}
