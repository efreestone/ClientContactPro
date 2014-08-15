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
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class LoginActivity extends Activity {
	String TAG = "LoginActivity";
	EditText emailEditText, passwordEditText;
	String emailEntered, passwordEntered;
	String savedEmail, savedPassword;
	String loggedEmail, loggedPassword;
	SharedPreferences sharedPreferences;
	
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText); 
        
        Button loginButton = (Button) findViewById(R.id.loginButton); 
        Button signUpButton = (Button) findViewById(R.id.signUpButton);
        
        sharedPreferences = MainActivity.sharedPreferences;
        loggedEmail = sharedPreferences.getString("email", "email");
    	loggedPassword = sharedPreferences.getString("password", "password");
    	
        boolean loggedIn = sharedPreferences.getBoolean("loggedIn", false); 
        if (loggedIn) {  
        	Log.i(TAG, "Auto logged");
			emailEditText.setText(loggedEmail);
			passwordEditText.setText(loggedPassword);
			//onSignInClick(loginButton);
		} else {
			Log.i(TAG, "loggedIn = " + loggedIn); 
		} 
        
        loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { 
				onSignInClick(v); 
			}  
		}); 
        
        signUpButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onSignUpClick(v);
				
			}
		});
		
	} //onCreate close
	
	public void onSignInClick(View view){
		//Grab entered login info 
		emailEntered = emailEditText.getText().toString(); 
		passwordEntered = passwordEditText.getText().toString();
		
		String emailFromPrefs = sharedPreferences.getString("email", "email");
		String passwordFromPrefs = sharedPreferences.getString("password", "password");
		
		CheckBox checkBox = (CheckBox) findViewById(R.id.autoLogCheckBox);
	
		if(emailEntered.equals(emailFromPrefs) && passwordEntered.equals(passwordFromPrefs)){
			//correct password
			Log.i(TAG, "Login successful"); 
			Editor editor = sharedPreferences.edit();
//			editor.putString("email", emailEntered);
//			editor.putString("password", passwordEntered);
			if (checkBox.isChecked()) {
				editor.putBoolean("loggedIn", true);
			} else {
				editor.putBoolean("loggedIn", false); 
			}
			
			editor.apply();  
			
			finish(); 
		}else{ 
			//wrong password
			Log.i(TAG, "Login failed"); 
		}
	}
	
	public void onSignUpClick(View view2){
		SignUpDialogFragment dialogFragment = new SignUpDialogFragment();
		dialogFragment.show(getFragmentManager(), "sign_up_dialog");
	}
	

}
