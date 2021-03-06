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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
/**
 * The Class NewAppointmentActivity.
 */
public class NewAppointmentActivity extends Activity implements OnClickListener {
	String TAG = "NewAppointmentActivity";
	Context myContext; 
	String clientNameExtra, clientAddress, phoneNumber, emailAddress, contactMethod, basicInfo, appointmentAddress, otherContacts;
	String nextAppointment, appointmentType;
	TextView clientNameTV , phoneNumberTV, emailAddressTV, basicInfoTV;
	TextView appStartDateTV, appStartTimeTV, appFinishDateTV, appFinishTimeTV, setTextView;
	EditText appointmentTypeET, appointmentAddressET;
	RadioGroup contactRadioGroup;
	RadioButton textRadioButton;
	RadioButton emailRadioButton;
	int clientPosition;
	long clientID;
	Button editButton;
	
	SharedPreferences sharedPreferences;
	
	int currentYear, currentMonth, currentDay , currentHour, currentMinute;
	int endYear, endMonth, endDay, endHour, endMinute;
	int dayMinusTime;
	String datePicked, timePicked;
	String startDatePicked, startTimePicked, endDatePicked, endTimePicked;
	String startTimeAndDate, endTimeAndDate;
	String appointmentTypeEntered, appointmentAddressEntered;
	
	HashMap<String, String> currentClientHashMap;
	ArrayList<HashMap<String, String>> clientArrayList;
	Intent detailsBackIntent;
	String allClientsString;
	int mapPosition;
	
	String reminderTime;
	
	Calendar myCalendar;
	Calendar alarmCalendar;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_appointment);
		myContext = this;
		
		Intent detailsBackIntent = new Intent();
		setResult(RESULT_CANCELED, detailsBackIntent);
		
		myCalendar = Calendar.getInstance();
		alarmCalendar = (Calendar) myCalendar.clone();
		
		sharedPreferences = MainActivity.sharedPreferences;
		reminderTime = sharedPreferences.getString("reminderTime", "24");
		
		mapPosition = -1;
		
		//Grab layout elements
		clientNameTV = (TextView) findViewById(R.id.newAppClientName);
		phoneNumberTV = (TextView) findViewById(R.id.newAppPhoneNumber);
		emailAddressTV = (TextView) findViewById(R.id.newAppEmailAddress);
		
		editButton = (Button) findViewById(R.id.editClientbutton); 
		editButton.setOnClickListener(this);
		
		appointmentTypeET = (EditText) findViewById(R.id.appointmentTypeET);
		appointmentAddressET = (EditText) findViewById(R.id.appointmentAddressET);
		
		//Grab date/time elements and set onClickListeners
		appStartDateTV = (TextView) findViewById(R.id.newAppStartDateTV);
		appStartDateTV.setOnClickListener(this);
		appStartTimeTV = (TextView) findViewById(R.id.newAppStartTimeTV);
		appStartTimeTV.setOnClickListener(this);
		appFinishDateTV = (TextView) findViewById(R.id.newAppFinishDateTV);
		appFinishDateTV.setOnClickListener(this);
		appFinishTimeTV = (TextView) findViewById(R.id.newAppFinishTimeTV);
		appFinishTimeTV.setOnClickListener(this);
		
		// Grab intent extras to be displayed in textviews
		Intent newAppointmentIntent = getIntent();
		clientNameExtra = newAppointmentIntent.getStringExtra("clientName");
		clientAddress = newAppointmentIntent.getStringExtra("clientAddress");
		appointmentAddress = newAppointmentIntent.getStringExtra("clientAddress");
		phoneNumber = newAppointmentIntent.getStringExtra("phoneNumber");
		emailAddress = newAppointmentIntent.getStringExtra("emailAddress");
		contactMethod = newAppointmentIntent.getStringExtra("contactMethod");
		basicInfo = newAppointmentIntent.getStringExtra("basicInfo");
		nextAppointment = newAppointmentIntent.getStringExtra("nextAppointment");
		appointmentType = newAppointmentIntent.getStringExtra("appointmentType");
		otherContacts = newAppointmentIntent.getStringExtra("otherContacts");
		startTimeAndDate = newAppointmentIntent.getStringExtra("startTimeAndDate");
		endTimeAndDate = newAppointmentIntent.getStringExtra("endTimeAndDate");
		
		contactRadioGroup = (RadioGroup) findViewById(R.id.contactRadioGroup);
		textRadioButton = (RadioButton) findViewById(R.id.textRadio);
		emailRadioButton = (RadioButton) findViewById(R.id.emailRadio);
		
		//Check contact method set and which elements exist for client
		if (contactMethod.equalsIgnoreCase("text")) {
			if (!phoneNumber.equalsIgnoreCase("")) {
				textRadioButton.setChecked(true);
			} else {
				Toast.makeText(myContext, "No phone number for this client", Toast.LENGTH_LONG).show();
				emailRadioButton.setChecked(true);
				contactMethod = "email";
			}
		} else {
			if (!emailAddress.equalsIgnoreCase("")) {
				emailRadioButton.setChecked(true);
			} else {
				Toast.makeText(myContext, "No email for this client", Toast.LENGTH_LONG).show();
				textRadioButton.setChecked(true);
				contactMethod = "text";
			}
		}
		
		//Radio on checked verifies that contactMethod selected exists for the client
		contactRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				RadioButton selectedButton = (RadioButton) findViewById(checkedId);
				String radioText = selectedButton.getText().toString();
				if (radioText.equalsIgnoreCase("email")) {
					if (emailAddress.equalsIgnoreCase("")) {
						Toast.makeText(myContext, "No email for this client", Toast.LENGTH_LONG).show();
						textRadioButton.setChecked(true);
						contactMethod = "text";
					} else {
						contactMethod = "email";
						Log.i(TAG, "Email Radio");
					}
				} else {
					if (phoneNumber.equalsIgnoreCase("")) {
						Toast.makeText(myContext, "No phone number for this client", Toast.LENGTH_LONG).show();
						emailRadioButton.setChecked(true);
						contactMethod = "email";
					} else {
						contactMethod = "text";
						Log.i(TAG, "Text Radio");
					}
				}
			}
		});
		
		if (startTimeAndDate.equalsIgnoreCase("none")) {
			setDefaultDateAndTime();
			Log.i(TAG, "Start time and date blank");
		} else {
			splitTimeAndDate(startTimeAndDate, endTimeAndDate);
			Log.i(TAG, "start: " + startTimeAndDate); 
		}
		
		clientPosition = newAppointmentIntent.getIntExtra("clientPosition", 1);
		Log.i(TAG, "clientPosition: " + clientPosition); 
		clientID = newAppointmentIntent.getLongExtra("clientID", 1);
		Log.i(TAG, "clientID: " + clientID);
		
		if (!clientNameExtra.equalsIgnoreCase("")) {
			//Log.i(TAG, "new app intent good: " + clientName);
			displayClientDetails();
		}
		
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
				
				clientArrayList = JSONData.getClientArrayList();
				
				Log.i(TAG, "clientArrayList Length: " + clientArrayList.size());
				
				for (HashMap<String, String> map : clientArrayList){
					//Check if element with client name exists
			        if(map.containsValue(clientNameExtra))
			        {
			        	//Pass position of duplicate element for replacement
			        	mapPosition = clientArrayList.indexOf(map);
			        	Log.i(TAG, "HashMap at position" + map);
			            Log.i(TAG, "Position " + mapPosition);
			            //clientArrayList.remove(mapPosition);
			                break;
			        }
			    }
				
				/*
				 * Trigger method to delete original element and add new one to
				 * arraylist before being saved to the device
				 */
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
	private void displayClientDetails(){
		clientNameTV.setText(clientNameExtra);
		phoneNumberTV.setText(phoneNumber);
		emailAddressTV.setText(emailAddress);
		
		appointmentAddressET.setText(appointmentAddress);
		
	} //displayClientDetails close
	
	/* Present date picker dialog */
	private void showDatePickerDialog(int currentYear, int currentMonth,
			int currentDay) {
		//Set up date picker dialog listener
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
				setTextView.setText(datePicked);
				// Log.i(TAG, datePicked);
			}
		};
		//Create date picker dialog
		DatePickerDialog datePickerDialog = new DatePickerDialog(this,
				datePickerListener, currentYear, currentMonth, currentDay);
		//Set minimum date to now minus 1 sec
		datePickerDialog.getDatePicker().setMinDate(new Date().getTime() - 1000);
		datePickerDialog.show();
	} // showDatePickerDialog close
	
	/* Present time picker dialog */
	private void showTimePickerDialog(int currentHour, int currentMinute){
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
						
						//Cast minute to string and add 0 if below 10.
						String minuteString = String.valueOf(minute);
						if (minute < 10) {
							minuteString = "0"+ String.valueOf(minute);
						}
						
						timePicked = hourOfDay + ":" + minuteString + AMorPM;
						setTextView.setText(timePicked);
						
					}
		        }, currentHour, currentMinute, false); //bool is to set 24 hour time 
		timePickerDialog.show();   
		
	} //showTimePickerDialog close

	/*
	 * onClick is triggered by any of the time/date textviews and triggers date
	 * pickers from custom methods above
	 */
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
			//currentDay = currentDay + 1;
			//Display dialog with tomorrows date as starting point
			showDatePickerDialog(endYear, endMonth, endDay);
			
			setTextView = appFinishDateTV;
			break;
		//End time
		case R.id.newAppFinishTimeTV:
			Log.i(TAG, "Finish Date Clicked");
			showTimePickerDialog(endHour, endMinute);
			setTextView = appFinishTimeTV;
			break; 
		//Edit client button
		case R.id.editClientbutton:
			//Log.i(TAG, "Edit");
			onEditClicked();
		default:
			break;
		}
		
	} //onClick close
	
	/*
	 * onDoneClicked deletes object new app is being created and formats input
	 * data before sending it on to be saved. Called when done is clicked in the
	 * action bar
	 */
	void onDoneClicked(){
		if (mapPosition != -1) {
			clientArrayList.remove(mapPosition);
			Log.i(TAG, "clientArrayList Length after remove: " + clientArrayList.size());
		}
		
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
		
		seperateDateTimeForReminder(startDatePicked, startTimePicked);
		
	    SimpleDateFormat dateFormat = new SimpleDateFormat("M/dd/yyyy 'at' hh:mm a");
		Date convertedDate = new Date();
		try {
			convertedDate = dateFormat.parse(startTimeAndDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SimpleDateFormat sortFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm a");
		String formatDateForSort = sortFormat.format(convertedDate);
	    Log.i(TAG, "Sort Date: " + formatDateForSort);
	    
		endTimeAndDate = endDatePicked + " at " + endTimePicked;
		appointmentTypeEntered = appointmentTypeET.getText().toString();
		appointmentAddressEntered = appointmentAddressET.getText().toString();
		
		String clientNameEntered = clientNameExtra; 
		String clientAddressEntered = clientAddress;
		String phoneNumberEntered = phoneNumber;
		String emailAddressEntered = emailAddress;
		
		String contactMethodEntered = contactMethod;
		
		String basicInfoEntered = basicInfo; 
		String nextAppointmentEntered = startTimeAndDate;
		String startTimeAndDateEntered = startDatePicked + " at " + startTimePicked;
		String endTimeAndDateEntered = endDatePicked + " at " + endTimePicked;
		String appointmentAddressEntered = appointmentAddressET.getText().toString();
		String otherContactsEntered = "none"; 
		
		//Create hashmap of client with new appointment to be added to arraylis 
		HashMap<String, String> newAppointmentMap = new HashMap<String, String>(); 
		newAppointmentMap.put("clientName", clientNameEntered);
		newAppointmentMap.put("clientAddress", clientAddressEntered);
		newAppointmentMap.put("phoneNumber", phoneNumberEntered);
		newAppointmentMap.put("emailAddress", emailAddressEntered);
		newAppointmentMap.put("contactMethod", contactMethodEntered);
		newAppointmentMap.put("basicInfo", basicInfoEntered);
		newAppointmentMap.put("nextAppointment", nextAppointmentEntered);
		newAppointmentMap.put("appointmentType", appointmentTypeEntered);
		newAppointmentMap.put("startTimeAndDate", startTimeAndDateEntered);
		newAppointmentMap.put("endTimeAndDate", endTimeAndDateEntered);
		newAppointmentMap.put("appointmentAddress", appointmentAddressEntered);
		newAppointmentMap.put("otherContacts", otherContactsEntered);
		newAppointmentMap.put("formatDateForSort", formatDateForSort); 
		
		clientArrayList.add(newAppointmentMap);
		
		String newAppAllClientsString = "{clients:" + clientArrayList.toString() + "}";
		
		//Log.i(TAG, "List after add: " + newAppAllClientsString);
		
		Intent detailsBackIntent = new Intent();
		if (!newAppAllClientsString.equalsIgnoreCase("")) {
			detailsBackIntent.putExtra("allClients", newAppAllClientsString);
		}
		setResult(RESULT_OK, detailsBackIntent);  
		
		//Pass the new arraylist along to be converted and saved to the device 
		JSONData.convertArrayListToJSON(clientArrayList);
		
		finish(); 
	} //onDoneClicked close  
	
	/*
	 * Set a default date and time for start/finish if none are selected with
	 * the picker dialogs.
	 */
	void setDefaultDateAndTime() {
		// Get current date ints
		currentYear = myCalendar.get(Calendar.YEAR);
		currentMonth = myCalendar.get(Calendar.MONTH);
		currentDay = myCalendar.get(Calendar.DAY_OF_MONTH);
		currentHour = myCalendar.get(Calendar.HOUR);
		currentMinute = myCalendar.get(Calendar.MINUTE); 
		
		endYear = currentYear;
		endMonth = currentMonth;
		endDay = currentDay;
		endHour = currentHour + 1;
		endMinute = currentMinute;

		// Set default dates/times if none selected 
		int monthPlusOne = currentMonth + 1; 
		// Cast month and add 0 if below 10
		String defaultMonthString = String.valueOf(monthPlusOne);
		if (monthPlusOne < 10) {
			defaultMonthString = "0" + String.valueOf(monthPlusOne);
		}
		// Cast month and add 0 if below 10
		String defaultDayString = String.valueOf(currentDay);
		if (currentDay < 10) {
			defaultDayString = "0" + String.valueOf(currentDay);
		}
		startDatePicked = defaultMonthString + "/" + defaultDayString + "/"
				+ currentYear;
		// Set am or pm and change to 12 hour
		String startAMorPM;
		if (currentHour > 12) {
			startAMorPM = " AM";
			currentHour = currentHour - 12;
		} else {
			startAMorPM = " PM";
		}
		startTimePicked = currentHour + ":" + currentMinute + startAMorPM;
		endDatePicked = startDatePicked;
		int hourPlusOne = currentHour + 1;
		endTimePicked = hourPlusOne + ":" + currentMinute + startAMorPM ;

	} //setDefaultDateAndTime close
	
	/*
	 * splitTimeAndDate is used to seperate date/time strings for display in
	 * case of editing an existing appointment
	 */
	void splitTimeAndDate(String newStartTimeAndDate, String newEndTimeAndDate){
		String[] startArray = newStartTimeAndDate.split(" at ");
		//Grab start date and time to apply to textviews
		startDatePicked = startArray[0];
		appStartDateTV.setText(startDatePicked);
		startTimePicked = startArray[1];
		appStartTimeTV.setText(startTimePicked);
		
		String[] splitStartDate = startDatePicked.split("/");
		currentMonth = Integer.valueOf(splitStartDate[0]);
		currentDay = Integer.valueOf(splitStartDate[1]);
		currentYear = Integer.valueOf(splitStartDate[2]);
		
		String[] splitStartTime = startTimePicked.split(":");
		currentHour = Integer.valueOf(splitStartTime[0]);
		String[] splitMinAMPM = splitStartTime[1].split(" ");
		currentMinute = Integer.valueOf(splitMinAMPM[0]);
		//currentYear = Integer.valueOf(splitStartTime[2]);
		
		String[] endArray = newEndTimeAndDate.split(" at ");
		//Grab end date and time to apply to textviews
		endDatePicked = endArray[0];
		appFinishDateTV.setText(endDatePicked); 
		endTimePicked = endArray[1];
		appFinishTimeTV.setText(endTimePicked);
		
		String[] splitEndDate = endDatePicked.split("/");
		endMonth = Integer.valueOf(splitEndDate[0]);
		endDay = Integer.valueOf(splitEndDate[1]);
		endYear = Integer.valueOf(splitEndDate[2]);
		
		String[] splitEndTime = endTimePicked.split(":");
		endHour = Integer.valueOf(splitEndTime[0]);
		String[] splitEndMinAMPM = splitEndTime[1].split(" ");
		endMinute = Integer.valueOf(splitEndMinAMPM[0]);
	}
	
	/* parse date and time to be used for reminder alarms */
	void seperateDateTimeForReminder(String newStartDatePicked, String newStartTimePicked){
		String[] dateSplit = newStartDatePicked.split("/");
		String monthPicked = dateSplit[0];
		String dayPicked = dateSplit[1];
		String yearPicked = dateSplit[2];
		
		int dayInt = Integer.valueOf(dayPicked);
		
		//Check reminder time setting and subtract accordingly
		if (reminderTime.equalsIgnoreCase("48")) {
			Log.i(TAG, "Reminder time set for 48 hours");
			dayMinusTime = dayInt - 2;
		} else {
			Log.i(TAG, "Reminder time set for 24 hours");
			dayMinusTime = dayInt - 1;
		}
		
		
		String newDateTimeString = monthPicked + "/" + dayMinusTime + "/" + yearPicked + " " + newStartTimePicked;
		//Log.i(TAG, "New date: " + newDateTimeString); 
		
		SimpleDateFormat newDateFormatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		try {
			Date newAlarmDate = newDateFormatter.parse(newDateTimeString);
			Log.i(TAG, "newAlarmDate: " + newAlarmDate);
			alarmCalendar.setTime(newAlarmDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setReminderAlarm(alarmCalendar);
		
	} //seperateDateTimeForReminder close 
	
	/* Create intents and alarm manager to snd reminders */
	void setReminderAlarm(Calendar newAlarmCalendar){ 
		Toast.makeText(getApplicationContext(), "Reminder set for " + newAlarmCalendar.getTime(), Toast.LENGTH_LONG).show(); 
		
		Intent reminderIntent = new Intent(myContext, ReminderReceiver.class);
		reminderIntent.putExtra("clientName", clientNameExtra);
		reminderIntent.putExtra("contactMethod", contactMethod);
		reminderIntent.putExtra("emailAddress", emailAddress);
		reminderIntent.putExtra("phoneNumber", phoneNumber);
		reminderIntent.putExtra("startTimeAndDate", startTimeAndDate);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(myContext, clientPosition, reminderIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager reminderManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		reminderManager.set(AlarmManager.RTC_WAKEUP, newAlarmCalendar.getTimeInMillis(), pendingIntent);
	} //setReminderAlarm close 
	
	//Pass client details to New Client to edit
	void onEditClicked(){
		Intent editClientIntent = new Intent(myContext, NewClientActivity.class);
		editClientIntent.putExtra("clientName", clientNameExtra);
		editClientIntent.putExtra("clientAddress", clientAddress);
		editClientIntent.putExtra("emailAddress", emailAddress);
		editClientIntent.putExtra("appointmentAddress", appointmentAddress);
		editClientIntent.putExtra("phoneNumber", phoneNumber);
		editClientIntent.putExtra("contactMethod", contactMethod);
		editClientIntent.putExtra("basicInfo", basicInfo);
		editClientIntent.putExtra("nextAppointment", nextAppointment);
		editClientIntent.putExtra("appointmentType", appointmentType);
		editClientIntent.putExtra("otherContacts", otherContacts);
		editClientIntent.putExtra("startTimeAndDate", startTimeAndDate);
		editClientIntent.putExtra("endTimeAndDate", endTimeAndDate);
		editClientIntent.putExtra("isEdit", true);
		
		startActivityForResult(editClientIntent, 0);	
	} //onEditClicked close
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == 0) {
			finish();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override 
	public void finish() {  
		Log.i(TAG, "Finish called");
//		Intent detailsBackIntent = new Intent();
//		detailsBackIntent.putExtra("allClients", JSONData.allClientJSONString);
//		setResult(RESULT_OK, detailsBackIntent);
		super.finish();  
	} // finish Close  

} 
