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

import java.util.Locale;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
 

// TODO: Auto-generated Javadoc   
/**
 * The Class MainActivity.
 */
public class MainActivity extends FragmentActivity implements ActionBar.TabListener {
	static Context myContext;
	public static final String PREFS_NAME = "SharedPrefsFile";
	static SharedPreferences sharedPreferences;
	String TAG = "MainActivity";
	ListView clientListView;
	ListView appointmentsListView;   

    /**   
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */   
    SectionsPagerAdapter mySectionsPagerAdapter;   
    ViewPager myViewPager;    
    ActionBar myActionBar;
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

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mySectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        myViewPager = (ViewPager) findViewById(R.id.pager);
        myViewPager.setAdapter(mySectionsPagerAdapter); 
        
        //View cancelSubView = (View) findViewById(R.layout.subview_cancel_button); 
        
//        LayoutInflater factory = getLayoutInflater();
//
//        View layout = factory.inflate(R.layout.fragment_clients, null);
//        View linearLayout = layout.findViewById(R.id.clientsLinear);
//
//        TextView valueTV = new TextView(this);
//        valueTV.setText("hallo hallo");
//        valueTV.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
//
//        ((LinearLayout) linearLayout).addView(valueTV); 
//        myViewPager.addView(linearLayout);                     
                
        
//        //Create linear layout instance        
//        LinearLayout myLayout = new LinearLayout(this);
//        //Set orientation
//        myLayout.setOrientation(LinearLayout.VERTICAL);
//        //Set my layout parameters to match the parent item
//        myLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//        //Create center params used to center various items
//        LinearLayout.LayoutParams centerParams = new LinearLayout.LayoutParams(
//        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        //Set gravity to center
//        centerParams.gravity = Gravity.CENTER; 
//        //Create title text view
//        TextView titleView = new TextView(this);
//        //Set text appearance for title
//        titleView.setTextAppearance(this, android.R.attr.textAppearanceLarge);
//        //Set text for title from resources
//        titleView.setText(R.string.app_name);
//        //Add title with centered params
//        myLayout.addView(titleView, centerParams); 
//        
//        myViewPager.addView(myLayout);
        
        myActionBar = getActionBar();
        myActionBar.setHomeButtonEnabled(false);
        myActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent); 
        
        //appointmentsListView = (ListView) findViewById(R.id.appointmentListView);
        
        for (String tab_name : tabNames) { 
        	myActionBar.addTab(myActionBar.newTab().setText(tab_name).setTabListener(this));
        	//myViewPager.addView(cancelSubView); 
        	 
		}
        
        //Change tab selected when screen is swiped
        myViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() { 
			
			@Override 
			public void onPageSelected(int position) {
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
            return true; 
        }
        
        if (id == R.id.newAppointmentPlus) {
			Log.i(TAG, "Plus clicked");
			onNewClientClick();
		}
        
        return super.onOptionsItemSelected(item);
    } //onOptionsItemSelected close

    

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
        /* (non-Javadoc)
		 * @see android.support.v13.app.FragmentPagerAdapter#getItem(int)
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
		JSONData.displayDataFromFile(); 
	}  
 

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
		startActivity(newClientIntent);
	}

}
