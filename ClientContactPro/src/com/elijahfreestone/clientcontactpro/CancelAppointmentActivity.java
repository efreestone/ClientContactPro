/*
 * @author	Elijah Freestone 
 *
 * Project	ClientContactPro
 * 
 * Package	com.elijahfreestone.clientcontactpro
 * 
 * Date		Aug 19, 2014
 */

package com.elijahfreestone.clientcontactpro;

import android.app.Activity; 
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class CancelAppointmentActivity.
 */
public class CancelAppointmentActivity extends Activity {
	String TAG = "CancelAppointmentActivity";
	TextView startCancelDate, startCancelTime, finishCancelDate, finishCancelTime;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cancel_appointments);
		
		startCancelDate = (TextView) findViewById(R.id.startCancelDate);
		startCancelTime = (TextView) findViewById(R.id.startCancelTime);
		finishCancelDate = (TextView) findViewById(R.id.finishCancelDate);
		finishCancelTime = (TextView) findViewById(R.id.finishCancelTime);
		
		startCancelDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i(TAG, "Start Date");
			}
		});
		
		
	} //onCreate close
	
	/*
	 * gerCurrentDate is called in onCreate and grabs the current date and time.
	 * This is used to set the default dates and times for canceling appointments
	 */
	void getCurrentDate(){
		
		
	}
	
}
