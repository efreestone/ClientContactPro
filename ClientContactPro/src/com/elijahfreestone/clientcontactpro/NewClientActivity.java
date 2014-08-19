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

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class NewClientActivity extends Activity{
	String TAG = "NewClientActivity";
	EditText clientNameEditText, clientAddressEditText, phoneNumberEditText,
			emailAddressEditText, basicInfoEditText;
	String clientNameEntered, clientAddressEntered, phoneNumberEntered,
			emailAddressEntered, basicInforEntered;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_client);
		  
		//Grab inputs
		clientNameEditText = (EditText) findViewById(R.id.newClientName);
		clientAddressEditText = (EditText) findViewById(R.id.newClientAddress);
		phoneNumberEditText = (EditText) findViewById(R.id.newPhoneNumber);
		emailAddressEditText = (EditText) findViewById(R.id.newEmailAddress);
		basicInfoEditText = (EditText) findViewById(R.id.newBasicInfo); 
		
		
		Button testbutton = (Button) findViewById(R.id.testButton);
		testbutton.setOnClickListener(new OnClickListener() { 
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clientNameEntered = clientNameEditText.getText().toString();
				clientAddressEntered = clientAddressEditText.getText().toString();
				phoneNumberEntered = phoneNumberEditText.getText().toString();
				emailAddressEntered = emailAddressEditText.getText().toString();
				basicInforEntered = basicInfoEditText.getText().toString();
				
				Log.i(TAG, "Name: " + clientNameEntered + ", Address: "
						+ clientAddressEntered + "\nPhone Number: "
						+ phoneNumberEntered + ", Email: "
						+ emailAddressEntered + "\n" + "Basic Info: "
						+ basicInforEntered); 
				
			}
		});
		
	}

}
