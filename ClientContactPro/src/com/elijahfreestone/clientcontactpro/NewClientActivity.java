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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

// TODO: Auto-generated Javadoc
/**
 * The Class NewClientActivity.
 */
public class NewClientActivity extends Activity{
	static String TAG = "NewClientActivity";
	static Context myContext;
	static DataManager myDataManager;
	boolean requiredFields;
	EditText clientNameEditText, clientAddressEditText, phoneNumberEditText,
			emailAddressEditText, basicInfoEditText;
	String clientNameEntered, clientAddressEntered, phoneNumberEntered,
			emailAddressEntered, basicInfoEntered;
	static String myFileName = MainActivity.myFileName;
	static JSONObject allClientsJSONObject;
	
	
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
		
//		File file = this.getFileStreamPath(myFileName);
//		boolean fileExists = file.exists();
//		if (!fileExists) {
//			checkDeviceForFile();
//		}
		checkDeviceForFile(); 
		  
		//Grab inputs
		clientNameEditText = (EditText) findViewById(R.id.newClientName);
		clientAddressEditText = (EditText) findViewById(R.id.newClientAddress);
		phoneNumberEditText = (EditText) findViewById(R.id.newPhoneNumber);
		//Format phone number as it is being entered
		phoneNumberEditText.addTextChangedListener(new PhoneNumberFormattingTextWatcher()); 
		emailAddressEditText = (EditText) findViewById(R.id.newEmailAddress);
		basicInfoEditText = (EditText) findViewById(R.id.newBasicInfo); 
		
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
				basicInfoEntered = basicInfoEditText.getText().toString();
				//Check if required fields have been added
				checkRequiredFields();
				
				Log.i(TAG, "Required Fields = " + requiredFields);
				
				if (requiredFields) {
//					Log.i(TAG, "Name: " + clientNameEntered + ", Address: "
//							+ clientAddressEntered + "\nPhone Number: "
//							+ phoneNumberEntered + ", Email: "
//							+ emailAddressEntered + "\n" + "Basic Info: "
//							+ basicInfoEntered); 
//					JSONData.buildJSONNewClient(clientNameEntered,
//							clientAddressEntered, phoneNumberEntered,
//							emailAddressEntered, basicInfoEntered);
					buildJSON();
					finish();
					JSONData.displayDataFromFile();
				}
				
			}
		});

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
	
	public void checkDeviceForFile() {
		// Check if the file already exists
		File file = this.getFileStreamPath(myFileName);
		Boolean fileExists = file.exists();
		String stringFromFile = "{\"clients\":[]}";
		if (fileExists) {
			// Display the data to the listview automatically if file exists
			// JSONData.displayDataFromFile();
			// JSONData.sendArrayListToWidget();
			stringFromFile = DataManager.readStringFromFile(myContext, myFileName);
			allClientsJSONObject = JSONData.getJSONFromFile(stringFromFile);
			Log.i("File", "File exists");
		} else {
			try {
				allClientsJSONObject = new JSONObject(stringFromFile);
				Log.i(TAG, "allClientsJSONObject" + allClientsJSONObject);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.i("File", "File DOESN'T exist!!");
			myDataManager.writeStringToFile(myContext, myFileName,
					stringFromFile);
		}
	}

	void buildJSON() {
		JSONObject clientJSONObject = new JSONObject();
		JSONObject detailsObject = new JSONObject();
		try {
			detailsObject.put("clientName", clientNameEntered);
			detailsObject.put("clientAddress", clientAddressEntered);
			detailsObject.put("phoneNumber", phoneNumberEntered);
			detailsObject.put("emailAddress", emailAddressEntered);
			detailsObject.put("basicInfo", basicInfoEntered);
			
			detailsObject.put("nextAppointment", "none"); 
			
			// clientJSONObject.put(clientNameEntered, detailsObject);
			// Log.i(TAG, "Client JSON: " + clientJSONObject);
			clientJSONObject.put(clientNameEntered, detailsObject);
			JSONArray allClientJSONArray = allClientsJSONObject.getJSONArray("clients");
			allClientJSONArray.put(detailsObject); 
			// allClientsJSONObject.put(clientJSONObject); 
			
			JSONObject newAllClientsObject = new JSONObject();
			newAllClientsObject.put("clients", allClientJSONArray);
			Log.i(TAG, "All Clients JSON: " + newAllClientsObject);
			String allClientJSONString = newAllClientsObject.toString();
			myDataManager.writeStringToFile(myContext, myFileName,
					allClientJSONString);
			
			
			//BaseAdapter listAdapter = ClientsFragment.clientListView.get
			
			JSONData.clientListAdapter.notifyDataSetChanged();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, e.getMessage().toString());
		}
	} // buildJSON close
	
	public void refreshList(){
		JSONData.clientListAdapter.notifyDataSetChanged();
	}

}
