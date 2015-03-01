package com.example.elixir;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by manojthakur on 2/28/15.
 */
public class MemberListAdapter extends ArrayAdapter<MeetingBean.MemberBean> {

    Context context;
    int layoutResourceId;
    List<MeetingBean.MemberBean> memberList;

    public MemberListAdapter(Context context, int layoutResourceId,
                              List<MeetingBean.MemberBean> memberList) {
        super(context, layoutResourceId, memberList);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.memberList = memberList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MemberItemHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new MemberItemHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.docIcon);
            holder.secondLine = (TextView)row.findViewById(R.id.secondLine);
            holder.firstLine = (TextView)row.findViewById(R.id.firstLine);
            row.setTag(holder);
        } else {
            holder = (MemberItemHolder) row.getTag();
        }

        MeetingBean.MemberBean member = memberList.get(position);
        holder.firstLine.setText(member.getName());
        holder.secondLine.setText("Phone: " + member.getPhone());
        holder.imgIcon.setImageResource(R.drawable.doctor1);

        return row;
    }

    static class MemberItemHolder {
        ImageView imgIcon;
        TextView firstLine;
        TextView secondLine;
    }
}
