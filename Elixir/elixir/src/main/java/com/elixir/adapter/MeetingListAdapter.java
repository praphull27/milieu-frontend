package com.elixir.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.elixir.dob.MeetingItem;
import com.example.elixir.R;

public class MeetingListAdapter extends ArrayAdapter<MeetingItem> {
	Context context;
	int layoutResourceId;
	List<MeetingItem> doctorsList;

	public MeetingListAdapter(Context context, int layoutResourceId,
                              List<MeetingItem> doctorsList) {
		super(context, layoutResourceId, doctorsList);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.doctorsList = doctorsList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		DoctorItemHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new DoctorItemHolder();
			holder.imgIcon = (ImageView)row.findViewById(R.id.docIcon);
            holder.secondLine = (TextView)row.findViewById(R.id.secondLine);
            holder.firstLine = (TextView)row.findViewById(R.id.firstLine);
			row.setTag(holder);
		} else {
			holder = (DoctorItemHolder) row.getTag();
		}

		MeetingItem doctor = doctorsList.get(position);
		holder.firstLine.setText(doctor.name);
		holder.secondLine.setText(doctor.location +"\n"+doctor.time);
		holder.imgIcon.setImageResource(doctor.image);

		return row;
	}

	static class DoctorItemHolder {
		ImageView imgIcon;
		TextView firstLine;
		TextView secondLine;
	}

}
