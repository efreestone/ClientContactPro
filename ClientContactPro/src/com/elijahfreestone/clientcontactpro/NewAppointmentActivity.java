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

import java.util.Calendar;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

// TODO: Auto-generated Javadoc
/**
 * The Class NewAppointmentActivity.
 */
public class NewAppointmentActivity extends Activity implements OnClickListener {
	String TAG = "NewAppointmentActivity";
	Context myContext; 
	String clientName, clientAddress, phoneNumber, emailAddress, contactMethod, basicInfo, appointmentAddress, otherContacts;
	String nextAppointment, appointmentType;
	TextView clientNameTV , phoneNumberTV, emailAddressTV, basicInfoTV;
	TextView appStartDateTV, appStartTimeTV, appFinishDateTV, appFinishTimeTV, setTextView;
	EditText appointmentTypeET, appointmentAddressET;
	int clientPosition;
	long clientID;
	
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
		setContentView(R.layout.activity_new_appointment);
		myContext = this;
		
		//Get current date ints
		final Calendar myCalendar = Calendar.getInstance();
		currentYear = myCalendar.get(Calendar.YEAR);
		currentMonth = myCalendar.get(Calendar.MONTH);
		currentDay = myCalendar.get(Calendar.DAY_OF_MONTH);
		currentHour = myCalendar.get(Calendar.HOUR);
		currentMinute = myCalendar.get(Calendar.MINUTE);
		//AMorPM = myCalendar.get(Calendar.AM_PM);
		
		int monthPlusOne = currentMonth + 1;
		startDatePicked = monthPlusOne + "/" + currentDay + "/" + currentYear;
		String startAMorPM;
		if(currentHour < 12) {
			startAMorPM = " AM";
        } else {
        	startAMorPM = " PM";
        	currentHour = currentHour - 12;
        }
		startTimePicked = currentHour + ":" + currentMinute + startAMorPM;
		endDatePicked = startDatePicked;
		endTimePicked = startTimePicked;
		
		//startTimeAndDate = startDatePicked = " at " + startTimePicked;
		
		//Grab layout elements
		clientNameTV = (TextView) findViewById(R.id.newAppClientName);
		phoneNumberTV = (TextView) findViewById(R.id.newAppPhoneNumber);
		emailAddressTV = (TextView) findViewById(R.id.newAppEmailAddress);
		//Grab date/time elements and set onClickListeners
		appStartDateTV = (TextView) findViewById(R.id.newAppStartDateTV);
		appStartDateTV.setOnClickListener(this);
		appStartTimeTV = (TextView) findViewById(R.id.newAppStartTimeTV);
		appStartTimeTV.setOnClickListener(this);
		appFinishDateTV = (TextView) findViewById(R.id.newAppFinishDateTV);
		appFinishDateTV.setOnClickListener(this);
		appFinishTimeTV = (TextView) findViewById(R.id.newAppFinishTimeTV);
		appFinishTimeTV.setOnClickListener(this);
		
		appointmentTypeET = (EditText) findViewById(R.id.appointmentTypeET);
		appointmentAddressET = (EditText) findViewById(R.id.appointmentAddressET);
		
		// Grab intent extras to be displayed in textviews
		Intent newAppointmentIntent = getIntent();
		clientName = newAppointmentIntent.getStringExtra("clientName");
		clientAddress = newAppointmentIntent.getStringExtra("clientAddress");
		appointmentAddress = newAppointmentIntent.getStringExtra("clientAddress");
		phoneNumber = newAppointmentIntent.getStringExtra("phoneNumber");
		emailAddress = newAppointmentIntent.getStringExtra("emailAddress");
		contactMethod = newAppointmentIntent.getStringExtra("contactMethod");
		basicInfo = newAppointmentIntent.getStringExtra("basicInfo");
		nextAppointment = newAppointmentIntent.getStringExtra("nextAppointment");
		appointmentType = newAppointmentIntent.getStringExtra("appointmentType");
		otherContacts = newAppointmentIntent.getStringExtra("otherContacts");
		
		clientPosition = newAppointmentIntent.getIntExtra("clientPosition", 1);
		Log.i(TAG, "clientPosition: " + clientPosition); 
		clientID = newAppointmentIntent.getLongExtra("clientID", 1);
		Log.i(TAG, "clientID: " + clientID);
		
		if (!clientName.equalsIgnoreCase("")) {
			//Log.i(TAG, "new app intent good: " + clientName);
			displayClientDetails();
		}
		
//		//Grab current time and date
//		Time currentTime = new Time();
//		currentTime.setToNow();
//		Log.i(TAG, "Current Time: " + currentTime);
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
//		String currentDateAndTime = dateFormat.format(new Date());
//		Log.i(TAG, "Date and Time: " + currentDateAndTime);
//		
//		String myDate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
//		Log.i(TAG, "myDate: " + myDate);
		
		// Grab custom done/cancel action bar
		View customActionBarView = getLayoutInflater().inflate(
				R.layout.custom_action_bar, new LinearLayout(myContext), false);
		//Cancel button
		View cancelActionButton = customActionBarView.findViewById(R.id.actionbarCancelButton);
		cancelActionButton.setOnClickListener(new OnClickListener() {     
			@Override
			public void onClick(View v) {
				Log.i(TAG, "Cancel Action Button clicked");
				//Dismiss New Appointment
				finish();
			}
		});   
		
		//Done button
		View doneActionButton = customActionBarView.findViewById(R.id.actionbarDoneButton);
		doneActionButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i(TAG, "Done Action Button clicked");
				onDoneClicked();
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
	
	/* Apply strings to textviews */
	void displayClientDetails(){
		clientNameTV.setText(clientName);
		phoneNumberTV.setText(phoneNumber);
		emailAddressTV.setText(emailAddress);
		
		appointmentAddressET.setText(appointmentAddress);
		
	}
	
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
		case R.id.newAppStartDateTV:
			Log.i(TAG, "Start Date Clicked");
			//Display dialog with todays date as starting point
			showDatePickerDialog(currentYear, currentMonth, currentDay); 
			
			setTextView = appStartDateTV;
			break;
		//Start time
		case R.id.newAppStartTimeTV:
			Log.i(TAG, "Start Time Clicked");
			showTimePickerDialog(currentHour, currentMinute);
			setTextView = appStartTimeTV;
			break;
		//End date
		case R.id.newAppFinishDateTV:
			Log.i(TAG, "Finish Date Clicked");
			currentDay = currentDay + 1;
			//Display dialog with tomorrows date as starting point
			showDatePickerDialog(currentYear, currentMonth, currentDay);
			
			setTextView = appFinishDateTV;
			break;
		//End time
		case R.id.newAppFinishTimeTV:
			Log.i(TAG, "Finish Date Clicked");
			showTimePickerDialog(currentHour, currentMinute);
			setTextView = appFinishTimeTV;
			break;
		default:
			break;
		}
		
	} //onClick close
	
	void onDoneClicked(){
		if (!appStartDateTV.getText().toString().equalsIgnoreCase("Start Date")) {
			startDatePicked = appStartDateTV.getText().toString();
		}
		
		if (!appStartTimeTV.getText().toString().equalsIgnoreCase("Time")) {
			startTimePicked = appStartTimeTV.getText().toString();
		}
		
		if (!appFinishDateTV.getText().toString().equalsIgnoreCase("Finish Date")) {
			endDatePicked = appFinishDateTV.getText().toString();
		}
		
		if (!appFinishTimeTV.getText().toString().equalsIgnoreCase("Time")) {
			endTimePicked = appFinishTimeTV.getText().toString();
		}
		
		startTimeAndDate = startDatePicked + " at " + startTimePicked;
		endTimeAndDate = endDatePicked + " at " + endTimePicked;
		appointmentTypeEntered = appointmentTypeET.getText().toString();
		appointmentAddressEntered = appointmentAddressET.getText().toString();
		
		String clientNameEntered = clientName;
		String clientAddressEntered = clientAddress;
		String phoneNumberEntered = phoneNumber;
		String emailAddressEntered = emailAddress;
		
		String contactMethodEntered = contactMethod;
		
		String basicInfoEntered = basicInfo;
		String nextAppointmentEntered = startTimeAndDate;
		String appointmentTypeEntered = appointmentTypeET.getText().toString();
		String startTimeAndDateEntered = startTimeAndDate;
		String endTimeAndDateEntered = endTimeAndDate;
		String appointmentAddressEntered = appointmentAddressET.getText().toString();
		String otherContactsEntered = "none";
		
		JSONData.buildJSON(clientNameEntered, clientAddressEntered,
				phoneNumberEntered, emailAddressEntered,
				contactMethodEntered, basicInfoEntered,
				nextAppointmentEntered, appointmentTypeEntered,
				startTimeAndDateEntered, endTimeAndDateEntered,
				appointmentAddressEntered, otherContactsEntered);
		
		finish();
	} //onDoneClicked close
	
	@Override
	public void finish() {
		Log.i(TAG, "Finish called");
		Intent detailsBackIntent = new Intent();
		detailsBackIntent.putExtra("allClients", JSONData.allClientJSONString);
		setResult(RESULT_OK, detailsBackIntent);
		super.finish();  
	} // finish Close

}
