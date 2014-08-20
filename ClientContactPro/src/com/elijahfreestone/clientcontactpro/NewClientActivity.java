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

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.InputType;
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
	String TAG = "NewClientActivity";
	Context myContext;
	boolean requiredFields;
	EditText clientNameEditText, clientAddressEditText, phoneNumberEditText,
			emailAddressEditText, basicInfoEditText;
	String clientNameEntered, clientAddressEntered, phoneNumberEntered,
			emailAddressEntered, basicInforEntered;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_client);
		
		myContext = this;
		  
		//Grab inputs
		clientNameEditText = (EditText) findViewById(R.id.newClientName);
		clientAddressEditText = (EditText) findViewById(R.id.newClientAddress);
		phoneNumberEditText = (EditText) findViewById(R.id.newPhoneNumber);
		phoneNumberEditText.addTextChangedListener(new PhoneNumberFormattingTextWatcher()); 
		
		emailAddressEditText = (EditText) findViewById(R.id.newEmailAddress);
		basicInfoEditText = (EditText) findViewById(R.id.newBasicInfo); 
		
		
//		Button testbutton = (Button) findViewById(R.id.testButton);
//		testbutton.setOnClickListener(new OnClickListener() {  
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				clientNameEntered = clientNameEditText.getText().toString();
//				clientAddressEntered = clientAddressEditText.getText().toString();
//				phoneNumberEntered = phoneNumberEditText.getText().toString();
//				emailAddressEntered = emailAddressEditText.getText().toString();
//				basicInforEntered = basicInfoEditText.getText().toString();
//				
//				Log.i(TAG, "Name: " + clientNameEntered + ", Address: "
//						+ clientAddressEntered + "\nPhone Number: "
//						+ phoneNumberEntered + ", Email: "
//						+ emailAddressEntered + "\n" + "Basic Info: "
//						+ basicInforEntered); 
//			} //onClick close
//		}); //setOnClickListener close
		
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
				basicInforEntered = basicInfoEditText.getText().toString();
				//Check if required fields have been added
				checkRequiredFields();
				
				Log.i(TAG, "Required Fields = " + requiredFields);
				
				if (requiredFields) {
					Log.i(TAG, "Name: " + clientNameEntered + ", Address: "
							+ clientAddressEntered + "\nPhone Number: "
							+ phoneNumberEntered + ", Email: "
							+ emailAddressEntered + "\n" + "Basic Info: "
							+ basicInforEntered); 
					finish();
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
	
	boolean checkRequiredFields(){
		if (!clientNameEntered.equalsIgnoreCase("")) {
			if (!phoneNumberEntered.equalsIgnoreCase("") && phoneNumberEntered.length() == 14 || !emailAddressEntered.equalsIgnoreCase("")) {
				Log.i(TAG, "Phone number length = " + phoneNumberEntered.length());
				requiredFields = true;
			}
			
		}
		return requiredFields;
	} //checkRequiredFields

}
