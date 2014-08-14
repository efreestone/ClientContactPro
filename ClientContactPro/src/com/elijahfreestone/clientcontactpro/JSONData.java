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
import org.json.JSONObject;
import org.json.JSONStringer;

import android.content.Context;

public class JSONData {
	String TAG = "JSONData";
	static Context myContext;
	static String JSONString;
	static ArrayList<HashMap<String, String>> clientList;
	
	/*
	* Display data from file pulls string from locally stored file and creates
	* ArrayList.
	*/
	public static void displayDataFromFile() { 
		myContext = MainActivity.myContext;
		
		String clientName, nextAppointment;
		
		JSONString = myContext.getString(R.string.clientString);
		
		clientList = new ArrayList<HashMap<String, String>>();
		JSONObject jsonObject = null;
		JSONArray clientJSONArray = null;
		
		
		
		
		
	}

}
