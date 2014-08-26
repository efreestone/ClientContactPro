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
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

// TODO: Auto-generated Javadoc
/**
 * The Class CancelAppointmentActivity.
 */
public class CancelAppointmentActivity extends Activity implements OnClickListener {
	String TAG = "CancelAppointmentActivity";
	TextView startCancelDate, startCancelTime, finishCancelDate, finishCancelTime, setTextView;
	Button cancelButton;
	Calendar myCalendar;
	
	int currentYear, currentMonth, currentDay , currentHour, currentMinute;
	String datePicked, timePicked;
	String startDatePicked, startTimePicked, endDatePicked, endTimePicked;
	String startTimeAndDate, endTimeAndDate;
	String appointmentTypeEntered, appointmentAddressEntered;
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cancel_appointments);
		
		// Get current date ints
		myCalendar = Calendar.getInstance();
		currentYear = myCalendar.get(Calendar.YEAR);
		currentMonth = myCalendar.get(Calendar.MONTH);
		currentDay = myCalendar.get(Calendar.DAY_OF_MONTH);
		currentHour = myCalendar.get(Calendar.HOUR);
		currentMinute = myCalendar.get(Calendar.MINUTE);
		// AMorPM = myCalendar.get(Calendar.AM_PM);

		int monthPlusOne = currentMonth + 1;
		startDatePicked = monthPlusOne + "/" + currentDay + "/" + currentYear;
		String startAMorPM;
		if (currentHour < 12) {
			startAMorPM = " AM";
		} else {
			startAMorPM = " PM";
			currentHour = currentHour - 12;
		}
		startTimePicked = currentHour + ":" + currentMinute + startAMorPM;
		endDatePicked = startDatePicked;
		endTimePicked = startTimePicked;
		
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
	
	/* Present date picker dialog */
	void showDatePickerDialog(int currentYear, int currentMonth, int currentDay){
		DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				monthOfYear = monthOfYear + 1;
				datePicked = monthOfYear + "/" + dayOfMonth + "/" + year;
				setTextView.setText(datePicked);
				Log.i(TAG, datePicked);
				
			}
		}, currentYear, currentMonth, currentDay);
		datePickerDialog.show(); 
			
	}
	
	/* Present time picker dialog */
	void showTimePickerDialog(int currentHour, int currentMinute){
		TimePickerDialog timePickerDialog = new TimePickerDialog(this,
		        new TimePickerDialog.OnTimeSetListener() {
		 
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {
						//String AMorPM = (hourOfDay < 12) ? "AM" : "PM";
						String AMorPM;
						if(hourOfDay < 12) {
							AMorPM = " AM";
		                } else {
		                	AMorPM = " PM";
		                	hourOfDay = hourOfDay - 12;
		                }
						timePicked = hourOfDay + ":" + minute + AMorPM;
						setTextView.setText(timePicked);
						
					}
		        }, currentHour, currentMinute, false); //bool is to set 24 hour time 
		timePickerDialog.show();   
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//Start date
		case R.id.startCancelDate:
			Log.i(TAG, "Start Cancel Date");
			//Display dialog with todays date as starting point
			showDatePickerDialog(currentYear, currentMonth, currentDay); 
			
			setTextView = startCancelDate;
			break;
		//Start time
		case R.id.startCancelTime:
			Log.i(TAG, "Start Cancel Time");
			showTimePickerDialog(currentHour, currentMinute);
			setTextView = startCancelTime;
			break;
		//End date
		case R.id.finishCancelDate:
			Log.i(TAG, "Finish Cancel Date");
			//Display dialog with tomorrows date as starting point
			showDatePickerDialog(currentYear, currentMonth, currentDay);
			
			setTextView = finishCancelDate;
			break;
		//End time
		case R.id.finishCancelTime:
			Log.i(TAG, "Finish Cancel Time");
			showTimePickerDialog(currentHour, currentMinute);
			setTextView = finishCancelTime;
			break;
			
		case R.id.cancelAppointmentsButton:
			Log.i(TAG, "Cancel Button");
			break;

		default:
			break;
		}
		
	} //onClick close
	
}
