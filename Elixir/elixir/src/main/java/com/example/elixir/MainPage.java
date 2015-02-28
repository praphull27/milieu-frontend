package com.example.elixir;

import android.app.ActionBar;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class MainPage extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_page);

		TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);

		TabSpec tab_map = tabHost.newTabSpec("Create Meeting");
		TabSpec tab_list = tabHost.newTabSpec("My Meetings");

		tab_map.setIndicator("Create Meeting");
		tab_map.setContent(new Intent(this, MapViewActivity.class));

		tab_list.setIndicator("My Meetings");
		tab_list.setContent(new Intent(this, ListViewActivity.class));

		/** Add the tabs to the TabHost to display. */
		tabHost.addTab(tab_map);
		tabHost.addTab(tab_list);

		ActionBar actionBar = getActionBar();
		//actionBar.setCustomView(R.layout.action_bar_layout);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
				| ActionBar.DISPLAY_SHOW_HOME);
		actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.component_colors)));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.custom_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
}
