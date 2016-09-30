/*
 * @author	Elijah Freestone 
 *
 * Project	ClientContactPro
 * 
 * Package	com.elijahfreestone.clientcontactpro
 * 
 * Date		Aug 28, 2014
 */

package com.elijahfreestone.clientcontactpro;

import java.util.ArrayList;
import java.util.HashMap;

import android.util.Log;

// TODO: Auto-generated Javadoc
/**
 * The Class ClientManager.
 */
public class ClientManager {
	static String TAG = "ClientManager";
	static ArrayList<HashMap<String, String>> fullClientArrayList = JSONData.getClientArrayList();
	static String passedAllClientsString;
	
	/*
	 * Cancel appointments triggers intents for each appointment that falls
	 * within the timeframe selected. The client Hashmap is then handed to
	 * removeAppHashMap to clear appointment info
	 */
	public static void cancelAppointments(ArrayList<HashMap<String, String>> clientArrayList){
		Log.i(TAG, "clientArrayList: " + clientArrayList);
		
		for (int i = 0; i < clientArrayList.size(); i++) {
			HashMap<String, String> clientHashMap = clientArrayList.get(i);
			String clientName = clientHashMap.get("clientName");
			String contactMethod = clientHashMap.get("contactMethod");
			String phoneNumber = clientHashMap.get("phoneNumber");
			String emailAddress = clientHashMap.get("emailAddress");
			String startTimeAndDate = clientHashMap.get("startTimeAndDate");
			
			IntentManager myIntentManager = IntentManager.getInstance();
			
			myIntentManager.createCancelInten(clientName, contactMethod, emailAddress, phoneNumber, startTimeAndDate);
			
			fullClientArrayList = JSONData.getClientArrayList();
			
			Log.i(TAG, "clientArrayList Length: " + clientArrayList.size());
			
			removeAppFromHashMap(clientName);
		} 
	} //cancelAppointments close
	
	/*
	 * Grab HashMap of the client and replace all fields related to an
	 * appointment. This is called from appointment details as well as cancel
	 */
	static void removeAppFromHashMap(String clientName){ 
		if (fullClientArrayList.size() == 0) {
			fullClientArrayList = JSONData.getClientArrayList();
		}
		
		for (HashMap<String, String> map : fullClientArrayList){
			//Check if element with client name exists
	        if(map.containsValue(clientName))
	        {
	        	//Pass position of duplicate element for replacement
	        	Log.i(TAG, "HashMap at position" + map);
	            //Log.i(TAG, "Position " + mapPosition);
	            map.put("nextAppointment", "none");
	            map.put("appointmentType", "none");
	            map.put("startTimeAndDate", "none");
	            map.put("endTimeAndDate", "none");
	            map.put("formatDateForSort", "none");
	                break;
	        }
	    }
		passedAllClientsString = fullClientArrayList.toString();
		
		JSONData.convertArrayListToJSON(fullClientArrayList);
		 
		MainActivity.myViewPager.setAdapter(MainActivity.mySectionsPagerAdapter); 
		MainActivity.forceRefreshListViews(passedAllClientsString); 
	} //editClientHashMap close

}
