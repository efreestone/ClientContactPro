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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.BaseAdapter;
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
	static BaseAdapter appointmentListAdapter;  
	static String myFileName = MainActivity.myFileName;
	static JSONObject allClientsJSONObject;
	static DataManager myDataManager = DataManager.getInstance();
	static String allClientJSONString;
	static JSONArray clientJSONArray;
	
	/*
	* Display data from file pulls string from locally stored file and creates
	* ArrayList.
	*/
	public static void displayDataFromFile(String JSONString) {  
		Log.i(TAG, "displayDataFromFile called"); 
		myContext = MainActivity.myContext;
		
		String clientName, clientAddress, phoneNumber, emailAddress, contactMethod, basicInfo, nextAppointment, 
		appointmentType, startTimeAndDate, endTimeAndDate, appointmentAddress, otherContacts, formatDateForSort; 
		
		clientList = new ArrayList<HashMap<String, String>>();
		appointmentList = new ArrayList<HashMap<String, String>>();
		JSONObject jsonObject = null;
		clientJSONArray = null;    
		 
		try {
			Log.i(TAG, "displayData try");
			//Create Object with string passed in when method called
			jsonObject = new JSONObject(JSONString);
			
			allClientsJSONObject = jsonObject;
			
			//Grab array object
			clientJSONArray = jsonObject.getJSONArray("clients");
			int clientsArraySize = clientJSONArray.length();
			Log.i(TAG, "clientsArraySize = " + clientsArraySize);  
			
			for (int i = 0; i < clientsArraySize; i++) {
				clientName = clientJSONArray.getJSONObject(i).getString("clientName");
				//Log.i(TAG, "name = " + clientName);
				clientAddress = clientJSONArray.getJSONObject(i).getString("clientAddress");
				phoneNumber = clientJSONArray.getJSONObject(i).getString("phoneNumber");
				emailAddress = clientJSONArray.getJSONObject(i).getString("emailAddress");
				contactMethod = clientJSONArray.getJSONObject(i).getString("contactMethod");
				basicInfo = clientJSONArray.getJSONObject(i).getString("basicInfo");
				nextAppointment = clientJSONArray.getJSONObject(i).getString("nextAppointment");
				//Log.i(TAG, "next_appointment = " + nextAppointment);
				appointmentType = clientJSONArray.getJSONObject(i).getString("appointmentType");
				startTimeAndDate = clientJSONArray.getJSONObject(i).getString("startTimeAndDate");
				endTimeAndDate = clientJSONArray.getJSONObject(i).getString("endTimeAndDate");
				appointmentAddress = clientJSONArray.getJSONObject(i).getString("appointmentAddress");
				otherContacts = clientJSONArray.getJSONObject(i).getString("otherContacts");
				formatDateForSort = clientJSONArray.getJSONObject(i).getString("formatDateForSort");

				//Instantiate Hash Map for array and pass in strings with key/value pairs
				HashMap<String, String> clientDisplayMap = new HashMap<String, String>();
				clientDisplayMap.put("clientName", clientName);
				clientDisplayMap.put("clientAddress", clientAddress);
				clientDisplayMap.put("phoneNumber", phoneNumber);
				clientDisplayMap.put("emailAddress", emailAddress);
				clientDisplayMap.put("contactMethod", contactMethod);
				clientDisplayMap.put("basicInfo", basicInfo);
				clientDisplayMap.put("nextAppointment", nextAppointment);
				clientDisplayMap.put("appointmentType", appointmentType);
				clientDisplayMap.put("startTimeAndDate", startTimeAndDate);
				clientDisplayMap.put("endTimeAndDate", endTimeAndDate);
				clientDisplayMap.put("appointmentAddress", appointmentAddress);
				clientDisplayMap.put("otherContacts", otherContacts);
				clientDisplayMap.put("formatDateForSort", formatDateForSort);
				
				//Sort out all entries that contain an appointment
				HashMap<String, String> appointmentDisplayMap = new HashMap<String, String>();
				if (!nextAppointment.equalsIgnoreCase("none")) {
					//Log.i(TAG, "appointmentDisplayMap");
					appointmentDisplayMap.put("clientName", clientName);
					appointmentDisplayMap.put("clientAddress", clientAddress);
					appointmentDisplayMap.put("phoneNumber", phoneNumber);
					appointmentDisplayMap.put("emailAddress", emailAddress);
					appointmentDisplayMap.put("contactMethod", contactMethod);
					appointmentDisplayMap.put("basicInfo", basicInfo);
					appointmentDisplayMap.put("nextAppointment", nextAppointment);
					appointmentDisplayMap.put("appointmentType", appointmentType);
					appointmentDisplayMap.put("startTimeAndDate", startTimeAndDate);
					appointmentDisplayMap.put("endTimeAndDate", endTimeAndDate);
					appointmentDisplayMap.put("appointmentAddress", appointmentAddress);
					appointmentDisplayMap.put("otherContacts", otherContacts);
					
					appointmentDisplayMap.put("formatDateForSort", formatDateForSort);
					
					appointmentList.add(appointmentDisplayMap);
					
					//Comparator to sort client list by clientName in alphabetical order
					Comparator<HashMap<String, String>> clientMapComparator = new Comparator<HashMap<String, String>>() {
					    public int compare(HashMap<String, String> clientOne, HashMap<String, String> clientTwo) {
					        return clientOne.get("formatDateForSort").compareTo(clientTwo.get("formatDateForSort"));
					    } 
					};

					Collections.sort(appointmentList, clientMapComparator);
					//Log.i(TAG, "appointmentList" + appointmentList);
				} 
				
				//Add hash maps to array list
				clientList.add(clientDisplayMap);    
				
			}
			
			//Create adapters
			createClientsAdapter();
			createAppointmentsAdapter();
			
			//Comparator to sort client list by clientName in alphabetical order
			Comparator<HashMap<String, String>> clientMapComparator = new Comparator<HashMap<String, String>>() {
			    public int compare(HashMap<String, String> clientOne, HashMap<String, String> clientTwo) {
			        return clientOne.get("clientName").compareTo(clientTwo.get("clientName"));
			    }
			};

			Collections.sort(clientList, clientMapComparator);
			//Log.i(TAG, "clientList string: " + clientList.toString());
			//Log.i(TAG, "clientList" + clientList);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("displayDataFromFile ERROR", e.getMessage().toString());
		}
	} //displayDataFromFile close

	
	
	
	public static ArrayList<HashMap<String, String>> getClientArrayList(){
		Log.i(TAG, "Get client array list");
		return clientList;
	}
	
	public static JSONArray getClientJSONArray(){
		if (clientJSONArray != null) {
			Log.i(TAG, "++++++++++++++ allClientsJSONObject not null!! Length = " + clientJSONArray.length());
			return clientJSONArray;
		}
		return null;
	}
	
	
	
	
	/*
	 * getJSONFromFile is called when new clients are created. This grabs the
	 * clients currently saved to the device, which will then be processed into
	 * JSON before the new client added and they are resaved to the device
	 */
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
	
	/* createClientsAdapter createds the adapter for Clients listview */
	static void createClientsAdapter(){
		Log.i(TAG, "Create Client Adapter");
		//Create base adapter and set up with array of clients 
		clientListAdapter = new SimpleAdapter(myContext, clientList,
				R.layout.client_listview_row, new String[] { "clientName",
						"nextAppointment" }, new int[] { R.id.clientName,
						R.id.nextAppointment });

	} //createClientsAdapter close
	
	/* createAppointmentsAdapter createds the adapter for Appointments listview */
	static void createAppointmentsAdapter(){
		Log.i(TAG, "Create Appointment Adapter");
		//Create base adapter and set up with array of appointments 
		appointmentListAdapter = new SimpleAdapter(myContext,
				appointmentList, R.layout.appointment_listview_row, new String[] {
						"nextAppointment", "clientName" }, new int[] {
						R.id.appNextAppointment, R.id.appClientName });

	} //createAppointmentsAdapter close
	
	static void buildJSON(String clientNameEntered,
			String clientAddressEntered,
			String phoneNumberEntered,
			String emailAddressEntered, 
			String contactMethodEntered,
			String basicInfoEntered, 
			String nextAppointmentEntered,
			String appointmentTypeEntered,
			String startTimeAndDateEntered, 
			String endTimeAndDateEntered,
			String appointmentAddressEntered,  
			String otherContactsEntered,
			String formatDateForSort) { //, String formatDateForSort
		JSONObject clientJSONObject = new JSONObject();
		JSONObject detailsObject = new JSONObject();
		try {
			detailsObject.put("clientName", clientNameEntered);
			detailsObject.put("clientAddress", clientAddressEntered);
			detailsObject.put("phoneNumber", phoneNumberEntered);
			detailsObject.put("emailAddress", emailAddressEntered);
			detailsObject.put("contactMethod", contactMethodEntered);
			detailsObject.put("basicInfo", basicInfoEntered);
			detailsObject.put("nextAppointment", nextAppointmentEntered); 
			detailsObject.put("appointmentType", appointmentTypeEntered);
			detailsObject.put("startTimeAndDate", startTimeAndDateEntered);
			detailsObject.put("endTimeAndDate", endTimeAndDateEntered);
			detailsObject.put("appointmentAddress", appointmentAddressEntered);
			detailsObject.put("otherContacts", otherContactsEntered);
			detailsObject.put("formatDateForSort", formatDateForSort);
			
			// clientJSONObject.put(clientNameEntered, detailsObject);
			// Log.i(TAG, "Client JSON: " + clientJSONObject);
			clientJSONObject.put(clientNameEntered, detailsObject);
			JSONArray allClientJSONArray = allClientsJSONObject.getJSONArray("clients");
			allClientJSONArray.put(detailsObject); 
			// allClientsJSONObject.put(clientJSONObject);  
			
			JSONObject newAllClientsObject = new JSONObject();
			newAllClientsObject.put("clients", allClientJSONArray);
			//Log.i(TAG, "All Clients JSON: " + newAllClientsObject);
			allClientJSONString = newAllClientsObject.toString(); 
			
			if (!allClientJSONString.equalsIgnoreCase("")) {
				if (myDataManager != null) {
					myDataManager.writeStringToFile(MainActivity.myContext, myFileName, allClientJSONString); 
				}  
				
			} else {
				Log.i(TAG, "allClientJSONString is blank");
			}
			

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, e.getMessage().toString());
		}
	} // buildJSON close 
	
	
	public static void convertArrayListToJSON(ArrayList<HashMap<String, String>> clientsArrayList){
		 
		JSONObject newAllClientsObject = new JSONObject();
		//Cast ArrayList into JSONArray. Didn't know this was built into JSONArrays constructor before
		JSONArray newClientsJSONArray = new JSONArray(clientsArrayList);
		
		try {
			newAllClientsObject.put("clients", newClientsJSONArray);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}
		
		String newAllClientsString = newAllClientsObject.toString();
		Log.i("newAllClientsString", "newAllClientsString: " + newAllClientsString);
		
		//Make sure all clients string isn't blank before saving to the device
		if (!newAllClientsString.equalsIgnoreCase("")) {
			if (myDataManager != null) {
				Log.i("newAllClientsString", "Write String called in convert array");
				myDataManager.writeStringToFile(MainActivity.myContext, myFileName, newAllClientsString); 
			}   
		} else {
			Log.i("newAllClientsString", "newAllClientsString is blank"); 
		}
		//Force refresh of the listviews.
		MainActivity.forceRefreshListViews(newAllClientsString);
		
	} //convertArrayListToJSON close 
	
	
	
	void deleteEntryForAppointmentAdd(int position){ 
		
		
	}

}
