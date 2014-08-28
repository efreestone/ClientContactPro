package com.elijahfreestone.clientcontactpro;

import java.util.ArrayList;
import java.util.HashMap;

import android.util.Log;

public class ClientManager {
	static String TAG = "ClientManager";
	static ArrayList<HashMap<String, String>> fullClientArrayList = JSONData.getClientArrayList();
	static String passedAllClientsString;
	
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
//		JSONData.convertArrayListToJSON(fullClientArrayList);
//		 
//		MainActivity.myViewPager.setAdapter(MainActivity.mySectionsPagerAdapter); 
//		MainActivity.forceRefreshListViews(passedAllClientsString); 
	} //cancelAppointments close
	
	static void removeAppFromHashMap(String clientName){
		if (fullClientArrayList.size() == 0) {
			fullClientArrayList = JSONData.getClientArrayList();
		}
		
		for (HashMap<String, String> map : fullClientArrayList){
			//Check if element with client name exists
	        if(map.containsValue(clientName))
	        {
	        	//Pass position of duplicate element for replacement
	        	int mapPosition = fullClientArrayList.indexOf(map);
	        	Log.i(TAG, "HashMap at position" + map);
	            //Log.i(TAG, "Position " + mapPosition);
	            //fullClientArrayList.remove(mapPosition); 
	            //map.remove("nextAppointment");
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
