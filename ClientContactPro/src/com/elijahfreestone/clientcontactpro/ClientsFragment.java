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

public class ClientsFragment extends Fragment implements OnItemClickListener{
	static String TAG = "ClientsFragment";
	static ListView clientListView;
	Context myContext;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//return super.onCreateView(inflater, container, savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_clients, container, false);
		
		clientListView = (ListView) rootView.findViewById(R.id.clientListView); 
		
		if (clientListView != null) {  
			Log.i(TAG, "clientListView != null");   
			JSONData.displayDataFromFile(); 
		} else {
			Log.i(TAG, "clientListView == null"); 
		}
		
		clientListView.setOnItemClickListener(this);
		
		return rootView;     
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Log.i(TAG, "Item " + position + " clicked");
		
	} 
}
