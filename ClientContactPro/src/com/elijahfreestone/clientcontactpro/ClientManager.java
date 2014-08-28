package com.elijahfreestone.clientcontactpro;

import java.util.ArrayList;
import java.util.HashMap;

import android.util.Log;

public class ClientManager {
	static String TAG = "ClientManager";
	
	public static void cancelAppointmens(ArrayList<HashMap<String, String>> clientArrayList){
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
			
		}
		
	}

}
