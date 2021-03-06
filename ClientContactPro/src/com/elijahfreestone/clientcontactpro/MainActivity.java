/*
 * @author	Elijah Freestone 
 *
 * Project	ClientContactPro
 * 
 * Package	com.elijahfreestone.clientcontactpro
 * 
 * Date		Aug 12, 2014
 */

package com.elijahfreestone.clientcontactpro;

import java.io.File;
import java.util.Locale;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
 

// TODO: Auto-generated Javadoc     
/**
 * The Class MainActivity.
 */
public class MainActivity extends FragmentActivity implements ActionBar.TabListener {
	static String TAG = "MainActivity";
	static Context myContext;
	public static final String PREFS_NAME = "SharedPrefsFile";
	static SharedPreferences sharedPreferences;
	static String myFileName = "string_from_url.txt";
	ListView clientListView;
	ListView appointmentsListView;   
	static boolean fileExists;
	static DataManager myDataManager; 
	static IntentManager myIntentManager;
	static String JSONString;

    /**   
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */   
    static SectionsPagerAdapter mySectionsPagerAdapter;   
    static ViewPager myViewPager;    
    static ActionBar myActionBar;
    String[] tabNames = {"Clients", "Appointments"};

    /* (non-Javadoc) 
     * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
     */
    @Override  
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  
        
        myContext = this;                  
        
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(myContext);
        String reminderString = sharedPreferences.getString("defaultReminderMessage", "reminder");
        //Check if settings exist and create default if they don't
        if (reminderString.equalsIgnoreCase("reminder")) {
        	Log.i(TAG, "Default settings created");
        	reminderString = getResources().getString(R.string.defaultReminderHint);
        	String cancelString = getResources().getString(R.string.defaultCancelHint);
        	
        	Editor editor = sharedPreferences.edit();
        	editor.putString("reminderTime", "24"); 
        	editor.putBoolean("checkboxEmail", false);
        	editor.putString("defaultReminderMessage", reminderString);
        	editor.putString("defaultCancelMessage", cancelString);
        	//editor.putString("", value) 
			//editor.apply();
        	editor.commit();
		}
        
        myDataManager = DataManager.getInstance();
        myIntentManager = IntentManager.getInstance(); 

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mySectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        myViewPager = (ViewPager) findViewById(R.id.pager);
        myViewPager.setAdapter(mySectionsPagerAdapter); 
        
        //Grab action bar and set up
        myActionBar = getActionBar(); 
        myActionBar.setHomeButtonEnabled(false);
        myActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
       
        boolean isLoggedIn = sharedPreferences.getBoolean("loggedIn", false);
        String userName = sharedPreferences.getString("name", "user");
        Intent loginIntent = new Intent(this, LoginActivity.class);
        if (!isLoggedIn) {
        	startActivity(loginIntent);  
		} else {
			Toast.makeText(getApplicationContext(), "Welcome, " + userName, Toast.LENGTH_LONG).show();
		} 
        
        for (String tab_name : tabNames) { 
        	myActionBar.addTab(myActionBar.newTab().setText(tab_name).setTabListener(this));
        	//myViewPager.addView(cancelSubView); 
        	 
		}        
        
        //Change tab selected when screen is swiped
        myViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() { 
			
			@Override 
			public void onPageSelected(int position) {
				Log.i(TAG, "onPageSelected position: " + position);
				myActionBar.setSelectedNavigationItem(position);	 
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub 
				
			} 
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		}); //OnPageChangeListener close
        
        fileExists = checkFileExists();
        
    } //onCreate close  

    /* (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */ 
    @Override     
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;   
    }   

    /* (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override      
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
        	Intent settingsIntent = new Intent(myContext, SettingsActivity.class);
            startActivity(settingsIntent); 
            return true; 
        }
        
        //newPlusButton launches NewClient from everywhere except details screens.
        //Launches NewAppointment from them
        if (id == R.id.newPlusButton) { 
			Log.i(TAG, "Plus clicked");
			onNewClientClick(); 
		} 
        
        //Triggers alert and logs user out/finishes MainActivity upon positive click
        if (id == R.id.logOut) {
        	Log.i(TAG, "Log Out clicked");
        	showLogOutAlert();
		}
        
        return super.onOptionsItemSelected(item);
    } //onOptionsItemSelected close
    
    static boolean checkFileExists() {
		// Check if the file already exists
		File file = myContext.getFileStreamPath(myFileName);
		fileExists = file.exists();
		if (fileExists) {
			JSONString = DataManager.readStringFromFile(myContext, myFileName);
			// Display the data to the listview automatically if file exists
			JSONData.displayDataFromFile(JSONString);
			// JSONData.sendArrayListToWidget();
			Log.i(TAG, "checkFileExists");
		} else {
			// JSONData.checkDeviceForFile(fileExists);
			Log.i(TAG, "File DOESN'T exist!!");
		}
		return fileExists;
	} //checkFileExists close 

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        /*
         * Instantiates a new sections pager adapter.
         */
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        } 

		/*
		 * getItem is called to instantiate the fragment for the given page.
		 * Return the tab fragment based on position.
		 */
		@Override 
        public Fragment getItem(int position) {
            
        	if (position == 0) {
        		Log.i(TAG, "Client Tab");
        		return new ClientsFragment(); 
			} else {
				Log.i(TAG, "Appointment tab");
				return new AppointmentsFragment(); 
			}
        } //getItem close 

        /* (non-Javadoc)
         * @see android.support.v4.view.PagerAdapter#getCount()
         */
        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2; 
        } //getCount close 
 
        /* (non-Javadoc)
         * @see android.support.v4.view.PagerAdapter#getPageTitle(int)
         */
        @Override        
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.titleSection1).toUpperCase(l);
                case 1:
                    return getString(R.string.titleSection2).toUpperCase(l);
            }
            return null; 
        } //getPageTitle close
    } //SectionsPagerAdapter close

	/* (non-Javadoc)
	 * @see android.app.ActionBar.TabListener#onTabSelected(android.app.ActionBar.Tab, android.app.FragmentTransaction)
	 */
	@Override 
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		Log.i(TAG, "onTabSelected");
		myViewPager.setCurrentItem(tab.getPosition());
		//Call method to display client entries
		if (fileExists) {
			Log.i(TAG, "onTab file exists"); 
		} 
	} //onTabSelected close 

	/* (non-Javadoc)
	 * @see android.app.ActionBar.TabListener#onTabUnselected(android.app.ActionBar.Tab, android.app.FragmentTransaction)
	 */
	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	} 
 

	/* (non-Javadoc)
	 * @see android.app.ActionBar.TabListener#onTabReselected(android.app.ActionBar.Tab, android.app.FragmentTransaction)
	 */
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}
	
	/*
	 * On new client click is called when the Plus icon in the action bar is clicked.
	 */
	void onNewClientClick(){ 
		Intent newClientIntent = new Intent(myContext, NewClientActivity.class);
		//startActivity(newClientIntent);
		startActivityForResult(newClientIntent, 0);
	} //onNewClientClick close
	
	/*
	 * Log User Out is called when the Log Out button in the action bar is clicked.
	 * The user is first given an alert dialog confirming log out
	 */
	void logUserOut(){    
		Editor editor = sharedPreferences.edit();
    	editor.remove("loggedIn");
    	editor.apply();
    	finish(); 
	} //logOutUser close    
	
	/*
	 * Displays an alert asking the user to confirm logging out.
	 * triggers logUserOut on positive button click
	 */ 
	void showLogOutAlert(){
		AlertDialog alertDialog = new AlertDialog.Builder(myContext).create();
		alertDialog.setTitle("Log Out");
		alertDialog.setMessage("Are you sure you would like to Log Out and exit the application?");
		alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", (new DialogInterface.OnClickListener() {

			@Override 
			public void onClick(DialogInterface dialog, int which) {
				logUserOut();
			}
		})); 
		alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", (DialogInterface.OnClickListener) null);
		alertDialog.show();
	} //showLogOutAlert close
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent detailsBackIntent) {
		Log.i(TAG, "On Activity Result"); 
		//super.onActivityResult(requestCode, resultCode, detailsBackIntent);
		if (resultCode == RESULT_OK && requestCode == 0) {
			Log.i(TAG, "onActivityResult resultCode = OK");
			if (detailsBackIntent.hasExtra("allClients")) {
				Log.i(TAG, "Back Intent has extra");
				String passedAllClientsString = detailsBackIntent.getExtras().getString("allClients");
				//Log.i(TAG, "Passed string: " + passedAllClientsString);
				int tabPosition = 0;
				if (!passedAllClientsString.equalsIgnoreCase("")) {
					forceRefreshListViews(passedAllClientsString);
				} 
				
				//JSONData.displayDataFromFile(passedAllClientsString);
				//Force view pager to rebuild and in turn refresh client listview
				myViewPager.setAdapter(mySectionsPagerAdapter); 
				myActionBar.setSelectedNavigationItem(tabPosition); 
			}   
		} 
	} //onActivityResult close 
	
	static void forceRefreshListViews(String passedAllClientsString){
		Log.i(TAG, "Passed string: " + passedAllClientsString);
		Log.i(TAG, "Force refresh"); 
		int tabPosition = 0;
		//Force view pager to rebuild and in turn refresh client listview
		myActionBar.setSelectedNavigationItem(tabPosition); 
		myViewPager.setCurrentItem(tabPosition);
		boolean checkFile = checkFileExists();
		if (checkFile) {
			Log.i(TAG, "Force Refresh File exists");
		} else {
			JSONData.displayDataFromFile(passedAllClientsString);
		}
	} //forceRefreshListViews

}
