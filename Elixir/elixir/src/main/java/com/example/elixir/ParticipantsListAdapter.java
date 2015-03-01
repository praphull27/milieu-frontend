package com.example.elixir;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by manojthakur on 2/28/15.
 */
public class ParticipantsListAdapter extends ArrayAdapter<ParticipantsListAdapter.ParticipantFields> {

    Context context;
    int layoutResourceId;
    List<ParticipantFields> memberList;

    public ParticipantsListAdapter(Context context, int layoutResourceId,
                             List<ParticipantFields> memberList) {
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
            holder.phone = (EditText)row.findViewById(R.id.pnumber);
            holder.address = (EditText)row.findViewById(R.id.paddress);
            row.setTag(holder);
        } else {
            holder = (MemberItemHolder) row.getTag();
        }

        //we need to update adapter once we finish with editing
        holder.phone.setOnFocusChangeListener(new FocusChangeListener(position, false));

        holder.address.setOnFocusChangeListener(new FocusChangeListener(position, true));

        ParticipantFields member = memberList.get(position);
        holder.phone.setText("");
        holder.address.setText("");

        return row;
    }

    class FocusChangeListener implements View.OnFocusChangeListener {

        int id;
        boolean address;
        FocusChangeListener(int id, boolean address) {
            this.id = id;
            this.address = address;
        }

        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus){
                final int position = v.getId();
                final EditText Caption = (EditText) v;
                if(address)
                    memberList.get(id).address = Caption.getText().toString();
                else
                    memberList.get(id).phone = Caption.getText().toString();
            }
        }
    }
    static class MemberItemHolder {
        EditText phone;
        EditText address;
    }

    static class ParticipantFields {
        String phone = "";
        String address = "";
    }
}
