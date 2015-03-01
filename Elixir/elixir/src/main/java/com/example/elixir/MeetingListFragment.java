package com.example.elixir;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.elixir.adapter.MeetingListAdapter;
import com.elixir.dob.MeetingItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MeetingListFragment extends ListFragment {

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        List<MeetingItem> list = new ArrayList<MeetingItem>();
        list.add(new MeetingItem(R.drawable.coffee,"Meeting UCLA friends", "WestWood, LA", new Date()));
        list.add(new MeetingItem(R.drawable.coffee,"Indian Food", "Al-Watan LAX", new Date()));
        list.add(new MeetingItem(R.drawable.coffee,"Brunch", "Santa Monica", new Date()));
        MeetingListAdapter dAdapter = new MeetingListAdapter(getActivity(), R.layout.doctor_profile, list);
        setListAdapter(dAdapter);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
}
