/*
 * @author	Elijah Freestone 
 *
 * Project	ClientContactPro
 * 
 * Package	com.elijahfreestone.clientcontactpro
 * 
 * Date		Aug 14, 2014
 */

package com.elijahfreestone.clientcontactpro;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class NewClientActivity.
 */
public class NewClientActivity extends Activity{
	static String TAG = "NewClientActivity";
	static Context myContext;
	static DataManager myDataManager;
	SharedPreferences sharedPreferences;
	boolean requiredFields; 
	EditText clientNameEditText, clientAddressEditText, phoneNumberEditText,
			emailAddressEditText, basicInfoEditText;
	String clientNameEntered, clientAddressEntered, phoneNumberEntered,
			emailAddressEntered, basicInfoEntered, contactMethod;
	static String myFileName = MainActivity.myFileName;
	static JSONObject allClientsJSONObject;
	String allClientJSONString;
	ClientsFragment clientsFragment;
	Intent detailsBackIntent;  
	
	String startTimeAndDate, endTimeAndDate, appointmentType,
			appointmentAddress, clientName, phoneNumber, emailAddress,
			basicInfo, otherContacts, clientAddress, nextAppointment;
	boolean isEdit;
	
	/* (non-Javadoc) 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_client);    
		//Grab instance of data manager
		myDataManager = DataManager.getInstance();
		myContext = this;
		
		sharedPreferences = MainActivity.sharedPreferences;
		//Check default contact setting and set contactMethod accordingly
		boolean checkboxEmail = sharedPreferences.getBoolean("checkboxEmail", false);
		contactMethod = checkboxEmail ? "email" : "text";
		//Log.i(TAG, "contactMethod: " + contactMethod);
		
		//Check for and get editClient intent from new appointments
		Intent editClientIntent = getIntent();
		startTimeAndDate = editClientIntent.getStringExtra("startTimeAndDate");
		endTimeAndDate = editClientIntent.getStringExtra("endTimeAndDate");
		appointmentType = editClientIntent.getStringExtra("appointmentType");
		appointmentAddress = editClientIntent.getStringExtra("appointmentAddress");
		clientName = editClientIntent.getStringExtra("clientName");
		phoneNumber = editClientIntent.getStringExtra("phoneNumber");
		emailAddress = editClientIntent.getStringExtra("emailAddress");
		contactMethod = editClientIntent.getStringExtra("contactMethod");
		basicInfo = editClientIntent.getStringExtra("basicInfo");
		otherContacts = editClientIntent.getStringExtra("otherContacts");
		clientAddress = editClientIntent.getStringExtra("clientAddress");
		nextAppointment = editClientIntent.getStringExtra("nextAppointment");
		isEdit = editClientIntent.getBooleanExtra("isEdit", false);
		
		//Create back intent and set result code to cancel by default
		detailsBackIntent = new Intent();
		//setResult(RESULT_CANCELED, detailsBackIntent);
		
		//Check if the file exists on the device.
		//Create a blank one if it does not
		checkDeviceForFile(); 
		  
		//Grab inputs
		clientNameEditText = (EditText) findViewById(R.id.newClientName);
		clientAddressEditText = (EditText) findViewById(R.id.newClientAddress);
		phoneNumberEditText = (EditText) findViewById(R.id.newPhoneNumber);
		//Format phone number as it is being entered
		phoneNumberEditText.addTextChangedListener(new PhoneNumberFormattingTextWatcher()); 
		emailAddressEditText = (EditText) findViewById(R.id.newEmailAddress);
		basicInfoEditText = (EditText) findViewById(R.id.newBasicInfo);
		
		clientNameEditText.setText(clientName);
		clientAddressEditText.setText(clientAddress);
		phoneNumberEditText.setText(phoneNumber);
		emailAddressEditText.setText(emailAddress);
		basicInfoEditText.setText(basicInfo);
		
		// Grab custom done/cancel action bar
		View customActionBarView = getLayoutInflater().inflate(
				R.layout.custom_action_bar, new LinearLayout(myContext), false);
		// Cancel button
		View cancelActionButton = customActionBarView
				.findViewById(R.id.actionbarCancelButton);
		cancelActionButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i(TAG, "Cancel Action Button clicked");
				// Dismiss New Client
				finish();
			}
		});

		// Done button
		View doneActionButton = customActionBarView
				.findViewById(R.id.actionbarDoneButton);
		doneActionButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i(TAG, "Done Action Button clicked");
				clientNameEntered = clientNameEditText.getText().toString();
				clientAddressEntered = clientAddressEditText.getText().toString();
				phoneNumberEntered = phoneNumberEditText.getText().toString();
				emailAddressEntered = emailAddressEditText.getText().toString();
				String contactMethodEntered = "text";
				basicInfoEntered = basicInfoEditText.getText().toString();
				String nextAppointmentEntered = "none";
				String appointmentTypeEntered = "none";
				String startTimeAndDateEntered = "none";
				String endTimeAndDateEntered = "none";
				String appointmentAddressEntered = clientAddressEntered;
				String otherContactsEntered = "none";
				String formatDateForSort = "1234"; 
				//Check if required fields have been added
				checkRequiredFields(); 
				
				Log.i(TAG, "Required Fields = " + requiredFields);
				
				if (requiredFields) {
					if (!isEdit) {
						JSONData.buildJSON(clientNameEntered,
								clientAddressEntered, phoneNumberEntered,
								emailAddressEntered, contactMethodEntered,
								basicInfoEntered, nextAppointmentEntered,
								appointmentTypeEntered,
								startTimeAndDateEntered, endTimeAndDateEntered,
								appointmentAddressEntered,
								otherContactsEntered, formatDateForSort);

						detailsBackIntent.putExtra("allClients",
								JSONData.allClientJSONString);
						detailsBackIntent.putExtra("tabPosition", 0);
						setResult(RESULT_OK, detailsBackIntent);

						finish();
						// JSONData.displayDataFromFile(allClientJSONString);
					} else {
						editClient();
						
						finish();
						
					}
				}
			}
		}); //done button close 

		/*
		 * Grab action bar and set params to show custom done/cancel bar and
		 * hide title and home icon
		 */
		ActionBar customActionBar = getActionBar();
		customActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM,
				ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME
						| ActionBar.DISPLAY_SHOW_TITLE);
		customActionBar.setCustomView(customActionBarView,
				new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
						ViewGroup.LayoutParams.MATCH_PARENT));

	} //onCreate close
	
	/*
	 * Check that the name field and either phone number or email are entered.
	 * Also checks length of phone number to make sure it is 10 digits before
	 * formatting (14 after)
	 */
	boolean checkRequiredFields(){ 
		if (!clientNameEntered.equalsIgnoreCase("")) {
			if (!phoneNumberEntered.equalsIgnoreCase("") && phoneNumberEntered.length() == 14 || !emailAddressEntered.equalsIgnoreCase("")) {
				Log.i(TAG, "Phone number length = " + phoneNumberEntered.length());
				requiredFields = true;
			}
			
		}
		return requiredFields;
	} //checkRequiredFields close	
	
	/* check the device for the clients file and create default if none exists */
	public void checkDeviceForFile() {
		// Check if the file already exists
		File file = this.getFileStreamPath(myFileName);
		Boolean fileExists = file.exists();
		String stringFromFile = "{\"clients\":[]}";
		if (fileExists) {
			// Display the data to the listview automatically if file exists
			stringFromFile = DataManager.readStringFromFile(myContext, myFileName);
			JSONData.allClientsJSONObject = JSONData.getJSONFromFile(stringFromFile);
			Log.i("File", "File exists");
		} else {
			try {
				JSONData.allClientsJSONObject = new JSONObject(stringFromFile);
				Log.i(TAG, "allClientsJSONObject" + allClientsJSONObject);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.i("File", "File DOESN'T exist!!");
			myDataManager.writeStringToFile(myContext, myFileName,
					stringFromFile);
		}
	} //checkDeviceForFile close
	
	void editClient(){
		Log.i(TAG, "editClient");
		ArrayList<HashMap<String, String>> fullClientArrayList = JSONData.getClientArrayList();
		for (HashMap<String, String> map : fullClientArrayList){
			//Check if element with client name exists
	        if(map.containsValue(clientName))
	        {
	        	//Pass position of duplicate element for replacement
	        	Log.i(TAG, "HashMap at position" + map);
	            //Log.i(TAG, "Position " + mapPosition);
	        	map.put("clientName", clientNameEntered);
	            map.put("clientAddress", clientAddressEntered);
	            map.put("phoneNumber", phoneNumberEntered);
	            map.put("emailAddress", emailAddressEntered);
	            map.put("basicInfo", basicInfoEntered);
	                break;
	        }
	    }
		String passedAllClientsString = fullClientArrayList.toString();
		
		detailsBackIntent.putExtra("allClients",
				passedAllClientsString);
		detailsBackIntent.putExtra("tabPosition", 0);
		setResult(RESULT_OK, detailsBackIntent);
		
		JSONData.convertArrayListToJSON(fullClientArrayList);
		 
		MainActivity.myViewPager.setAdapter(MainActivity.mySectionsPagerAdapter); 
		MainActivity.forceRefreshListViews(passedAllClientsString);
	} //editClient close
	
	/*
	 * finish is called when the activity is exited (such as the back button)
	 * This creates a new intent used to force trigger listview refresh
	 */
	@Override
	public void finish() {
		Log.i("Details Activity", "Finish called");
//		Intent detailsBackIntent = new Intent();
//		detailsBackIntent.putExtra("allClients", JSONData.allClientJSONString);
//		detailsBackIntent.putExtra("tabPosition", 0);
//		setResult(RESULT_OK, detailsBackIntent);
		super.finish();
	} // finish Close

}
