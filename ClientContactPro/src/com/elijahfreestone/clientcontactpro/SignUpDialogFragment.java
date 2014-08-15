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

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpDialogFragment extends DialogFragment {
	SharedPreferences sharedPreferences = MainActivity.sharedPreferences;
	View signUpView;
	String TAG = "SignUpDialogFragment";
	String nameEntered, emailEntered, appKeyEntered, passwordEntered, passwordConfirmEntered;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.DialogFragment#onCreateDialog(android.os.Bundle)
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(
				getActivity());
		LayoutInflater layoutInflater = getActivity().getLayoutInflater();
		
		signUpView = layoutInflater.inflate(R.layout.fragment_sign_up_dialog, null);
		
		alertBuilder.setView(signUpView)
		// Set Positive and Negative Action Buttons
		.setPositiveButton("Sign Up", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				onSignUpClick();
				
			}
		}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog,
				int which) {
					SignUpDialogFragment.this.getDialog().cancel();
				}
		});

		return alertBuilder.create();
	} // onCreateDialog Close
	
	void onSignUpClick(){
		EditText nameEditText, emailEditText, appKeyEditText, passwordEditText, passwordConfirmEditText;
		
		nameEditText = (EditText) signUpView.findViewById(R.id.userName);
		emailEditText = (EditText) signUpView.findViewById(R.id.emailAddress);
		appKeyEditText = (EditText) signUpView.findViewById(R.id.appKey);
		passwordEditText = (EditText) signUpView.findViewById(R.id.password);
		passwordConfirmEditText = (EditText) signUpView.findViewById(R.id.passwordConfirm);
		
		nameEntered = nameEditText.getText().toString();
		emailEntered = emailEditText.getText().toString();
		appKeyEntered = appKeyEditText.getText().toString();
		passwordEntered = passwordEditText.getText().toString();
		passwordConfirmEntered = passwordConfirmEditText.getText().toString();
		
		if (!emailEntered.equalsIgnoreCase("")
				&& !passwordEntered.equalsIgnoreCase("")
				&& !passwordConfirmEntered.equalsIgnoreCase("")) {
			Log.i(TAG, "Email and password entered");
			if (passwordEntered.equalsIgnoreCase(passwordConfirmEntered)) {
				Log.i(TAG, "Passwords match");
				saveToPrefs();
				Toast.makeText(getActivity(), "Sign Up Successful!", Toast.LENGTH_LONG).show();
			}
		} else {
			AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
			alertDialog.setTitle("Error");
			alertDialog.setMessage("Sign Up Failed due to missing information. Please fill out all fields and try again.");
			alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", (DialogInterface.OnClickListener) null);
			alertDialog.show();
		}
	} //onSignUpClick close
	
	void saveToPrefs(){
		Editor editor = sharedPreferences.edit();
		//clear out  
		editor.clear();
		editor.putString("name", nameEntered);
		editor.putString("email", emailEntered);
		editor.putString("key", appKeyEntered);
		editor.putString("password", passwordEntered);
		editor.apply();
	}

}