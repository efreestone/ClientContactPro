/*
 * @author	Elijah Freestone 
 *
 * Project	ClientContactPro
 * 
 * Package	com.elijahfreestone.clientcontactpro
 * 
 * Date		Aug 13, 2014
 */

package com.elijahfreestone.clientcontactpro;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

// TODO: Auto-generated Javadoc
/**
 * The Class JSONData.
 */
public class JSONData {
	static String TAG = "JSONData"; 
	static Context myContext;
	static String JSONString;
	static ArrayList<HashMap<String, String>> clientList;
	static ArrayList<HashMap<String, String>> appointmentList; 
	static BaseAdapter clientListAdapter;
	static SimpleAdapter appointmentListAdapter;  
	static String myFileName = MainActivity.myFileName;
	static JSONObject allClientsJSONObject;
	static DataManager myDataManager;
	
	/*
	* Display data from file pulls string from locally stored file and creates
	* ArrayList.
	*/
	public static void displayDataFromFile() {  
		Log.i(TAG, "displayDataFromFile called"); 
		myContext = MainActivity.myContext;
		
		String clientName, nextAppointment; 
		
		//JSONString = myContext.getString(R.string.clientString); 
		JSONString = DataManager.readStringFromFile(myContext, myFileName);
		
		//Log.i(TAG, "JSONString = " + JSONString);
		
		clientList = new ArrayList<HashMap<String, String>>();
		appointmentList = new ArrayList<HashMap<String, String>>();
		JSONObject jsonObject = null;
		JSONArray clientJSONArray = null;    
		 
		try {
			Log.i(TAG, "displayData try");
			jsonObject = new JSONObject(JSONString);
			clientJSONArray = jsonObject.getJSONArray("clients");
			int clientsArraySize = clientJSONArray.length();
			Log.i(TAG, "clientsArraySize = " + clientsArraySize);  
			
			for (int i = 0; i < clientsArraySize; i++) {
				
				//Log.i(TAG, "displayData for loop");
				
				clientName = clientJSONArray.getJSONObject(i).getString("clientName");
				//Log.i(TAG, "name = " + clientName);
				nextAppointment = clientJSONArray.getJSONObject(i).getString("nextAppointment");
				//Log.i(TAG, "next_appointment = " + nextAppointment);
				
				//Instantiate Hash Map for array and pass in strings with key/value pairs
				HashMap<String, String> clientDisplayMap = new HashMap<String, String>();
				clientDisplayMap.put("clientName", clientName);
				clientDisplayMap.put("nextAppointment", nextAppointment);
				
				//Sort out all entries that contain an appointment
				HashMap<String, String> appointmentDisplayMap = new HashMap<String, String>();
				if (!nextAppointment.equalsIgnoreCase("none")) {
					//Log.i(TAG, "appointmentDisplayMap");
					appointmentDisplayMap.put("nextAppointment", nextAppointment);
					appointmentDisplayMap.put("clientName", clientName); 
					appointmentList.add(appointmentDisplayMap);
				} 
				
				//Add hash maps to array list
				clientList.add(clientDisplayMap);    
				
			}
			
//			ListView newListView = ClientsFragment.clientListView;
//			
//			newListView.destroyDrawingCache();
//			newListView.setVisibility(ListView.INVISIBLE);
//			newListView.setVisibility(ListView.VISIBLE);
			
			//Create simple adapter and set up with array of clients 
			clientListAdapter = new SimpleAdapter(myContext, 
					clientList, R.layout.client_listview_row, new String[] {
							"clientName", "nextAppointment" }, new int[] {
							R.id.clientName, R.id.nextAppointment }); 
			//ClientsFragment.setAdapter();
			
			//ClientsFragment.clientListView.setAdapter(clientListAdapter);   
			
			//Create simple adapter and set up with array of clients 
			appointmentListAdapter = new SimpleAdapter(myContext,
					appointmentList, R.layout.appointment_listview_row, new String[] {
							"nextAppointment", "clientName" }, new int[] {
							R.id.appNextAppointment, R.id.appClientName });
			
			//AppointmentsFragment.appointmentsListView.setAdapter(appointmentListAdapter);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("displayDataFromFile ERROR", e.getMessage().toString());
		}
	} //displayDataFromFile close
	
	
	
	public static JSONObject getJSONFromFile(String stringFromFile){
		JSONObject clientsObjectFromFile; 
		try {
			Log.i(TAG, "getJSONFromFile try");
			clientsObjectFromFile = new JSONObject(stringFromFile);
			Log.i(TAG, "JSONObject: " + clientsObjectFromFile);
			return clientsObjectFromFile;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, e.getMessage().toString());
		}
		
		return null;
		
		//return clientsObjectFromFile;
	} //getJSONfromFile close
	
	
//	public static void buildJSONNewClient(String clientNameEntered,
//			String clientAddressEntered, String phoneNumberEntered,
//			String emailAddressEntered, String basicInfoEntered){
//		JSONObject clientJSONObject = new JSONObject();
//		JSONObject detailsObject = new JSONObject();
//		
//		try {
//			detailsObject.put("clientName", clientNameEntered);
//			detailsObject.put("clientAddress", clientAddressEntered);
//			detailsObject.put("phoneNumber", phoneNumberEntered);
//			detailsObject.put("emailAddress", emailAddressEntered);
//			detailsObject.put("basicInfo", basicInfoEntered); 
//			
//			//clientJSONObject.put(clientNameEntered, detailsObject);
//			//Log.i(TAG, "Client JSON: " + clientJSONObject);
//			
//			clientJSONObject.put(clientNameEntered, detailsObject);
//			
//			JSONArray allClientJSONArray = allClientsJSONObject.getJSONArray("clients");
//			
//			allClientJSONArray.put(clientJSONObject);
//			
//			//allClientsJSONObject.put(clientJSONObject);
//			
//			JSONObject newAllClientsObject = new JSONObject();
//			newAllClientsObject.put("clients", allClientJSONArray);
//			
//			Log.i(TAG, "All Clients JSON: " + newAllClientsObject);
//			
//			String allClientJSONString = newAllClientsObject.toString();
//			
//			myDataManager.writeStringToFile(myContext, myFileName, allClientJSONString); 
//			
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			Log.e(TAG, e.getMessage().toString());
//		}	
//	} //buildJSONNewClient close
	
	

}
