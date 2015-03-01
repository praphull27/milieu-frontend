package com.example.elixir;

import android.app.ActionBar;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("deprecation")
public class MainPage extends TabActivity {


    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_page);

		TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);

        boolean isInit = getIntent().getBooleanExtra("init", false);

        if(!isInit) {
            try {
                TabHost tabHost1 = (TabHost) getParent().findViewById(android.R.id.tabhost);
                tabHost1.setCurrentTab(0);
                tabHost1.clearAllTabs();
            } catch (Exception ex) { }

            TabSpec tab_map = tabHost.newTabSpec("Create Meeting");
            TabSpec tab_list = tabHost.newTabSpec("My Meetings");

            tab_map.setIndicator("Create Meeting");
            tab_map.setContent(new Intent(this, CreateMeetingActivity.class));

            tab_list.setIndicator("My Meetings");
            tab_list.setContent(new Intent(this, ListViewActivity.class));

            /** Add the tabs to the TabHost to display. */
            tabHost.addTab(tab_map);
            tabHost.addTab(tab_list);
        } else {
            // we will get more information here

            // get participants
            MeetingBean meeting = getIntent().getParcelableExtra("meeting");

            TabSpec tab_map = tabHost.newTabSpec("Map View");
            TabSpec tab_details = tabHost.newTabSpec("Detailed View");
            TabSpec tab_list = tabHost.newTabSpec("Back");

            tab_map.setIndicator("Map View");
            Intent i = new Intent(this, MapViewActivity.class);
            i.putExtra("meeting", meeting);
            tab_map.setContent(i);

            tab_details.setIndicator("Detailed View");
            Intent i_d = new Intent(this, MeetingDetailsView.class);
            i_d.putExtra("meeting", meeting);
            tab_details.setContent(i_d);

            tab_list.setIndicator("Back");
            Intent i1 = new Intent(this, MainPage.class);
            i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i1.putExtra("init", false);
            tab_list.setContent(i1);

            /** Add the tabs to the TabHost to display. */
            tabHost.addTab(tab_map);
            tabHost.addTab(tab_details);
            tabHost.addTab(tab_list);
        }
		ActionBar actionBar = getActionBar();
        if(actionBar != null) {
            //actionBar.setCustomView(R.layout.action_bar_layout);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
                    | ActionBar.DISPLAY_SHOW_HOME);
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.component_colors)));
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.custom_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
}
