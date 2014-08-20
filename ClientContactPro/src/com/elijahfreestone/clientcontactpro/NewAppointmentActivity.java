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

import java.util.zip.Inflater;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class NewAppointmentActivity.
 */
public class NewAppointmentActivity extends Activity {
	String TAG = "NewAppointmentActivity";
	Context myContext;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_appointment);
		myContext = this;
		 
		//LayoutInflater inflater = new LayoutInflater(myContext); 
		
		//View actionBarButtons = inflater.inflate(R.layout.custom_action_bar, new LinearLayout(myContext), false);
		View actionBarButtons = getLayoutInflater().inflate(R.layout.custom_action_bar, new LinearLayout(myContext), false);
		View cancelActionView = actionBarButtons.findViewById(R.id.action_cancel);
		cancelActionView.setOnClickListener(new OnClickListener() {     

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub 

			}
		});   
		View doneActionView = actionBarButtons.findViewById(R.id.action_done);
		doneActionView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		this.getActionBar().setDisplayShowCustomEnabled(true);
		this.getActionBar().setDisplayShowHomeEnabled(false);
		this.getActionBar().setCustomView(actionBarButtons);
	}

}
