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

import java.util.Calendar;

import android.app.Activity; 
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class CancelAppointmentActivity.
 */
public class CancelAppointmentActivity extends Activity implements OnClickListener {
	String TAG = "CancelAppointmentActivity";
	TextView startCancelDate, startCancelTime, finishCancelDate, finishCancelTime;
	Button cancelButton;
	Calendar myCalendar;
	int currentDay, currentMonth, currentYear;
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cancel_appointments);
		
		myCalendar = Calendar.getInstance();
		
		//Grab date/time spinners and cancel button 
		startCancelDate = (TextView) findViewById(R.id.startCancelDate);
		startCancelTime = (TextView) findViewById(R.id.startCancelTime);
		finishCancelDate = (TextView) findViewById(R.id.finishCancelDate);
		finishCancelTime = (TextView) findViewById(R.id.finishCancelTime);
		cancelButton = (Button) findViewById(R.id.cancelAppointmentsButton);
		
		//Set onClick
		startCancelDate.setOnClickListener(this);
		startCancelTime.setOnClickListener(this);
		finishCancelDate.setOnClickListener(this);
		finishCancelTime.setOnClickListener(this);
		cancelButton.setOnClickListener(this);
		
//		startCancelDate.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Log.i(TAG, "Start Date");
//			}
//		});
		
		getCurrentDate();
		
	} //onCreate close
	
	/*
	 * gerCurrentDate is called in onCreate and grabs the current date and time.
	 * This is used to set the default dates and times for canceling appointments
	 */
	void getCurrentDate(){
		currentDay = myCalendar.get(Calendar.DAY_OF_MONTH);
		currentMonth = myCalendar.get(Calendar.MONTH);
		currentYear = myCalendar.get(Calendar.YEAR);
		Log.i(TAG, "Current Date: " + currentMonth + "/" + currentDay + "/" + currentYear);
		
	} //getCurrentDate close

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.startCancelDate:
			Log.i(TAG, "Start Cancel Date");
			break;
			
		case R.id.startCancelTime:
			Log.i(TAG, "Start Cancel Time");
			break;
			
		case R.id.finishCancelDate:
			Log.i(TAG, "Finish Cancel Date");
			break;
			
		case R.id.finishCancelTime:
			Log.i(TAG, "Finish Cancel Time");
			break;
			
		case R.id.cancelAppointmentsButton:
			Log.i(TAG, "Cancel Button");
			break;

		default:
			break;
		}
		
	} //onClick close
	
}
