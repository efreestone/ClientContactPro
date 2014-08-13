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

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {
	String TAG = "Login Activity";
	EditText emailEditText, passwordEditText;
	String emailEntered, passwordEntered;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText); 
        
        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { 
				onSignInClick(v);
			}
		});
		
	}
	
	public void onSignInClick(View view){
		//Grab entered login info
		emailEntered = emailEditText.getText().toString(); 
		passwordEntered = passwordEditText.getText().toString();
	
		if(emailEntered.equals("test@email.com") && passwordEntered.equals("test")){
			//correct password
			Log.i(TAG, "Login successful");
			finish();
		}else{
			//wrong password
			Log.i(TAG, "Login failed");
		}
	}

}
