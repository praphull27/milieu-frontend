package com.example.elixir;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.elixir.adapter.MeetingListAdapter;
import com.elixir.dob.MeetingItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListViewActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.main_list_layout);

        List<MeetingItem> list = new ArrayList<MeetingItem>();
        list.add(new MeetingItem(R.drawable.coffee,"Meeting UCLA friends", "WestWood, LA", new Date()));
        list.add(new MeetingItem(R.drawable.coffee,"Indian Food", "Al-Watan LAX", new Date()));
        list.add(new MeetingItem(R.drawable.coffee,"Brunch", "Santa Monica", new Date()));
        MeetingListAdapter dAdapter = new MeetingListAdapter(this, R.layout.doctor_profile, list);

        ListView view = (ListView)findViewById(R.id.list);
        view.setAdapter(dAdapter);
	}
}

