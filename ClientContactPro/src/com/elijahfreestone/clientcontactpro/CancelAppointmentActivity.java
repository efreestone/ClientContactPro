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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import android.app.Activity; 
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
/**
 * The Class CancelAppointmentActivity.
 */
public class CancelAppointmentActivity extends Activity implements OnClickListener {
	String TAG = "CancelAppointmentActivity";
	TextView startCancelDateTV, startCancelTimeTV, finishCancelDateTV, finishCancelTimeTV, setTextView;
	Button cancelButton;
	Calendar myCalendar;
	Context myContext = MainActivity.myContext;
	
	int currentYear, currentMonth, currentDay , currentHour, currentMinute;
	String datePicked, timePicked;
	String startDatePicked, startTimePicked, endDatePicked, endTimePicked;
	String startTimeAndDate, endTimeAndDate;
	String appointmentTypeEntered, appointmentAddressEntered;
	
	ArrayList<HashMap<String, String>> textClientsArrayList;
	ArrayList<HashMap<String, String>> emailClientsArrayList;
	ArrayList<HashMap<String, String>> clientsWithAppsArrayList;
	
	
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
		// Set am or pm and change to 12 hour
		String startAMorPM;
		if (currentHour > 12) {
			startAMorPM = " AM";
			currentHour = currentHour - 12;
		} else {
			startAMorPM = " PM";
		}

		startTimePicked = currentHour + ":" + currentMinute + startAMorPM;
		int dayPlusOne = currentDay +1;
		endDatePicked = monthPlusOne + "/" + dayPlusOne + "/" + currentYear;
		endTimePicked = startTimePicked;
		
		//Grab date/time spinners and cancel button 
		startCancelDateTV = (TextView) findViewById(R.id.startCancelDate);
		startCancelTimeTV = (TextView) findViewById(R.id.startCancelTime);
		finishCancelDateTV = (TextView) findViewById(R.id.finishCancelDate);
		finishCancelTimeTV = (TextView) findViewById(R.id.finishCancelTime);
		cancelButton = (Button) findViewById(R.id.cancelAppointmentsButton);
		
		//Set onClick 
		startCancelDateTV.setOnClickListener(this);
		startCancelTimeTV.setOnClickListener(this);
		finishCancelDateTV.setOnClickListener(this);
		finishCancelTimeTV.setOnClickListener(this);
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
	
	/* Present start date picker dialog */
	void showStartDatePickerDialog(int currentYear, int currentMonth, int currentDay) {
		// Set up date picker dialog listener
		DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// Add 1 to fix 0 based
				monthOfYear = monthOfYear + 1;

				// Cast month to string and add 0 if below 10.
				String monthString = String.valueOf(monthOfYear);
				if (monthOfYear < 10) {
					monthString = "0" + String.valueOf(monthOfYear);
				}
				// Cast day of month to string and add 0 if below 10
				String dayString = String.valueOf(dayOfMonth);
				int intDayPlusOne = dayOfMonth + 1;
				String dayPlusOne = String.valueOf(intDayPlusOne);
				if (dayOfMonth < 10) {
					dayString = "0" + String.valueOf(dayOfMonth);
				}
				if (intDayPlusOne < 10) {
					dayPlusOne = "0" + String.valueOf(intDayPlusOne);
				}

				datePicked = monthString + "/" + dayString + "/" + year;
				startCancelDateTV.setText(datePicked);
				//Set end date to 1 day later
				String defaultEndDate = monthString + "/" + dayPlusOne + "/" + year;
				finishCancelDateTV.setText(defaultEndDate);
				// Log.i(TAG, datePicked);
			}
		};
		// Create date picker dialog
		DatePickerDialog datePickerDialog = new DatePickerDialog(this,
				datePickerListener, currentYear, currentMonth, currentDay);
		// Set minimum date to now minus 1 sec
		datePickerDialog.getDatePicker()
				.setMinDate(new Date().getTime() - 1000);
		datePickerDialog.show();
	} //showStartDatePickerDialog close
	
	/* Present date picker dialog */
	void showEndDatePickerDialog(int currentYear, int currentMonth, int currentDay) {
		// Set up date picker dialog listener
		DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// Add 1 to fix 0 based
				monthOfYear = monthOfYear + 1;

				// Cast month to string and add 0 if below 10.
				String monthString = String.valueOf(monthOfYear);
				if (monthOfYear < 10) {
					monthString = "0" + String.valueOf(monthOfYear);
				}
				// Cast day of month to string and add 0 if below 10
				String dayString = String.valueOf(dayOfMonth);
				if (dayOfMonth < 10) {
					dayString = "0" + String.valueOf(monthOfYear);
				}

				datePicked = monthString + "/" + dayString + "/" + year;
				finishCancelDateTV.setText(datePicked);
				// Log.i(TAG, datePicked);
			}
		};
		// Create date picker dialog
		DatePickerDialog datePickerDialog = new DatePickerDialog(this,
				datePickerListener, currentYear, currentMonth, currentDay);
		// Set minimum date to now minus 1 sec
		datePickerDialog.getDatePicker()
				.setMinDate(new Date().getTime() - 1000);
		datePickerDialog.show();
	} //showEndDatePickerDialog close
	
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
		
	} //showTimePickerDialog close

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//Start date
		case R.id.startCancelDate:
			Log.i(TAG, "Start Cancel Date");
			//Display dialog with todays date as starting point
			showStartDatePickerDialog(currentYear, currentMonth, currentDay); 
			
			//setTextView = startCancelDateTV;
			break;
		//Start time
		case R.id.startCancelTime:
			Log.i(TAG, "Start Cancel Time");
			showTimePickerDialog(currentHour, currentMinute);
			setTextView = startCancelTimeTV;
			break;
		//End date
		case R.id.finishCancelDate:
			Log.i(TAG, "Finish Cancel Date");
			int dayPlusOne = currentDay + 1;
			//Display dialog with tomorrows date as starting point
			showEndDatePickerDialog(currentYear, currentMonth, dayPlusOne);
			
			//setTextView = finishCancelDateTV;
			break;
		//End time
		case R.id.finishCancelTime:
			Log.i(TAG, "Finish Cancel Time");
			showTimePickerDialog(currentHour, currentMinute);
			setTextView = finishCancelTimeTV;
			break;
			
		case R.id.cancelAppointmentsButton:
			Log.i(TAG, "Cancel Button");
			onCancelAppointmentsClicked();
			
			break;

		default:
			break;
		}
		
	} //onClick close
	
	void onCancelAppointmentsClicked(){
		if (!startCancelDateTV.getText().toString().equalsIgnoreCase("Start Date")) {
			startDatePicked = startCancelDateTV.getText().toString();
		} 
		
		if (!startCancelTimeTV.getText().toString().equalsIgnoreCase("Time")) {
			startTimePicked = startCancelTimeTV.getText().toString();
		}
		
		if (!finishCancelDateTV.getText().toString().equalsIgnoreCase("Finish Date")) {
			endDatePicked = finishCancelDateTV.getText().toString();
		}
		
		if (!finishCancelTimeTV.getText().toString().equalsIgnoreCase("Time")) {
			endTimePicked = finishCancelTimeTV.getText().toString();
		}
		startTimeAndDate = startDatePicked + " " + startTimePicked;
		Log.i(TAG, "Start Date and Time: " + startTimeAndDate);
		endTimeAndDate = endDatePicked + " " + endTimePicked;
		Log.i(TAG, "End Date and Time: " + endTimeAndDate);
		
		SimpleDateFormat startEndDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		Date startCancelDate = new Date();
		Date endCancelDate = new Date();
		try {
			startCancelDate = startEndDateFormat.parse(startTimeAndDate);
			endCancelDate = startEndDateFormat.parse(endTimeAndDate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			Log.e(TAG, e1.getMessage().toString());
		}
		
		ArrayList<HashMap<String, String>> allClientsArraylist = JSONData.getClientArrayList();
		clientsWithAppsArrayList = new ArrayList<HashMap<String,String>>();
		textClientsArrayList = new ArrayList<HashMap<String,String>>();
		emailClientsArrayList = new ArrayList<HashMap<String,String>>();
		
		//int position;
		Date nextAppointmentDate = new Date();
		
		for (int i = 0; i < allClientsArraylist.size(); i++) {
			HashMap<String, String> clientHashMap = allClientsArraylist.get(i);
			String nextAppointment = allClientsArraylist.get(i).get("nextAppointment");
			// String formatDateForSort = allClientsArraylist.get(i).get("formatDateForSort");

			// Search for appointments and make Date object for those that exist
			if (!nextAppointment.equalsIgnoreCase("none")) {
				// Create date object of nextAppointment to compare with range entered
				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy 'at' hh:mm a");

				try {
					nextAppointmentDate = dateFormat.parse(nextAppointment);
					Log.i(TAG, "nextAppointmentDate " + nextAppointmentDate);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					Log.e(TAG, e1.getMessage().toString());
				} 
				// Compare appointment date object with range entered
				if (nextAppointmentDate.before(endCancelDate)
						&& nextAppointmentDate.after(startCancelDate)) {
					// Pass position of duplicate element for replacement
					clientsWithAppsArrayList.add(clientHashMap);
					// clientArrayList.remove(mapPosition);
				} else { 
					Log.i(TAG, "No dates in range");
				}
			} 
		} //for loop close  
		
		//Sort back over arraylist and seperate based on contactMethod
		if (clientsWithAppsArrayList.size() != 0) { 
			Log.i(TAG, "App array length: " + clientsWithAppsArrayList.size()); 
			for (int j = 0; j < clientsWithAppsArrayList.size(); j++) {
				HashMap<String, String> newClientHashMap = allClientsArraylist.get(j);
				String contactMethod = clientsWithAppsArrayList.get(j).get("contactMethod");
				
				if (contactMethod.equalsIgnoreCase("text")) { 
					textClientsArrayList.add(newClientHashMap);
					
				} else {
					emailClientsArrayList.add(newClientHashMap);
				}
			}
			Log.i(TAG, "Text: " + textClientsArrayList.size());
			Log.i(TAG, "Email: " + emailClientsArrayList.size());
			//Call alert method
			showCancelConfrimAlert(); 
		} else {
			//No appointments in the range selected
			Toast.makeText(this, "No appointments in date range", Toast.LENGTH_LONG).show();
		}
	} //onCancelAppointmentsClicked close
	
	void showCancelConfrimAlert(){
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Cancel Appointments?");
		alertDialog.setMessage("Cancel all appointments from " + startTimeAndDate + " to " + endTimeAndDate + "?");
		alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "YES", (new DialogInterface.OnClickListener() {
			//Cancel confirmed
			@Override 
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
//				if (textClientsArrayList.size() > 0) {
//					ClientManager.cancelAppointments(textClientsArrayList);
//				}
//				if (emailClientsArrayList.size() > 0) {
//					ClientManager.cancelAppointments(emailClientsArrayList);
//				}
				
				ClientManager.cancelAppointments(clientsWithAppsArrayList);
				
			}
		}));
		alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "NO", (new DialogInterface.OnClickListener() {
			//Cancel aborted
			@Override 
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(myContext, "No appointments canceled!", Toast.LENGTH_LONG).show();
				finish();
			}
		}));
		alertDialog.show();
	}
	
}
