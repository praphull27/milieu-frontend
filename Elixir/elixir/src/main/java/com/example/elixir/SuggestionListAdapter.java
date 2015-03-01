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
public class SuggestionListAdapter extends ArrayAdapter<MeetingBean.SuggestionBean> {

    Context context;
    int layoutResourceId;
    List<MeetingBean.SuggestionBean> suggestionList;

    public SuggestionListAdapter(Context context, int layoutResourceId,
                             List<MeetingBean.SuggestionBean> suggestionList) {
        super(context, layoutResourceId, suggestionList);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.suggestionList = suggestionList;
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

        MeetingBean.SuggestionBean sugg = suggestionList.get(position);
        holder.firstLine.setText(sugg.getName());
        holder.secondLine.setText(sugg.getDesc()+"("+sugg.getCategory() + ")" + "\n" + sugg.getRating()+"/5");
        holder.imgIcon.setImageResource(R.drawable.coffee);

        return row;
    }

    static class MemberItemHolder {
        ImageView imgIcon;
        TextView firstLine;
        TextView secondLine;
    }
}
