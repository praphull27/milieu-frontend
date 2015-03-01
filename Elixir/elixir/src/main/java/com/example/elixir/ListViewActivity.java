package com.example.elixir;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.elixir.adapter.MeetingListAdapter;
import com.elixir.dob.MeetingItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ListViewActivity extends Activity {

    private List<MeetingItem> meetings = new ArrayList<MeetingItem>();
    private List<MeetingBean> meetingBean = new ArrayList<MeetingBean>();

    public ListViewActivity() {
        super();

        /*
        List<MeetingItem> list = new ArrayList<MeetingItem>();
        list.add(new MeetingItem(R.drawable.coffee,"Meeting UCLA friends", "WestWood, LA", new Date()));
        list.add(new MeetingItem(R.drawable.coffee,"Indian Food", "Al-Watan LAX", new Date()));
        list.add(new MeetingItem(R.drawable.coffee,"Brunch", "Santa Monica", new Date()));
        */
    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.main_list_layout);

        try {
            TelephonyManager tMgr = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
            URI u = new URI("http://10.60.0.191:3000/memberMeetings/"+tMgr.getLine1Number().trim());
            boolean get = true;
            HashMap<String, String> params = new HashMap<String, String>();
            MilieuHttpClient.Request request = new MilieuHttpClient.Request(u, params, null, get);
            new MilieuClientAsyncTask(this).execute(request);
        } catch (Exception ex) {}
	}

    public void postHttpCallProcess(MilieuHttpClient.Response resp) {
        List<MeetingBean> ret = new ArrayList<>();
        try {
            JSONArray json = resp.content.getJSONArray("response");
            for (int i = 0; i < json.length(); i++) {
                MeetingBean b = Util.getMeetingGivenJSON(json.getJSONObject(i));
                ret.add(b);
            }

        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        for(MeetingBean mb: ret){
            meetings.add(new MeetingItem(R.drawable.coffee, mb.getName(), mb.getDesc(), new Date()));
            meetingBean.add(mb);
        }
        MeetingListAdapter dAdapter = new MeetingListAdapter(this, R.layout.doctor_profile, this.meetings);

        ListView view = (ListView)findViewById(R.id.list);
        view.setAdapter(dAdapter);

        view.setOnItemClickListener(new MapRenderer(this));
    }

    private static class MapRenderer implements AdapterView.OnItemClickListener {

        private ListViewActivity act;

        public MapRenderer(ListViewActivity act) {
            this.act = act;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            MeetingBean toBePassed = act.meetingBean.get(position);
            boolean pass = false;
            TelephonyManager tMgr = (TelephonyManager)act.getSystemService(Context.TELEPHONY_SERVICE);
            String phone = tMgr.getLine1Number().trim();

            for(MeetingBean.MemberBean mb: toBePassed.members) {
                if(mb.getPhone().equals(phone))
                    pass = true;
            }
            if(pass) {
                Intent i = new Intent(act, MainPage.class);
                i.putExtra("init", true);
                i.putExtra("meeting", toBePassed);
                act.startActivity(i);
            } else {
                // need to be added
                /*
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(act);
                alertDialogBuilder.setMessage("We will need your location to setup the meeting");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Intent positveActivity = new Intent(act.getApplicationContext(), MainPage.class);
                                positveActivity.putExtra("init", true);
                                positveActivity.putExtra("meeting", act.meetingBean.get(position));
                                act.startActivity(positveActivity);

                            }
                        });
                alertDialogBuilder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent negativeActivity = new Intent(act.getApplicationContext(), MainPage.class);
                                act.startActivity(negativeActivity);
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                */
                // addUserToMeeting, post, {id, name, phone, loc}
                try {
                    URI u = new URI("http://10.60.0.191:3000/addUserToMeeting");
                    boolean get = false;
                    HashMap<String, String> params = new HashMap<String, String>();
                    String _id = toBePassed.getId();

                    JSONObject jo = new JSONObject();
                    jo.put("id", _id);
                    jo.put("phoneNumber", Integer.parseInt(phone));
                    jo.put("name", phone);

                    LocationManager mLocationManager = (LocationManager) act.getSystemService(LOCATION_SERVICE);

                    LocListener list = new LocListener(act, jo, mLocationManager, u);

                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
                            0, list);


                } catch (Exception ex) {

                }
            }
        }
    }

    private static class LocListener implements LocationListener {

        Activity act;
        JSONObject req;
        LocationManager mLocationManager;
        URI uri;

        LocListener(Activity act, JSONObject req, LocationManager mLocationManager, URI uri) {
            this.act = act;
            this.req = req;
            this.mLocationManager = mLocationManager;
            this.uri = uri;
        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onLocationChanged(final Location location) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            this.mLocationManager.removeUpdates(this);
            try {
                req.put("lat", lat);
                req.put("lng", lng);
            }catch (Exception ex) {

            }
            MilieuHttpClient.Request request = new MilieuHttpClient.Request(uri, new HashMap<String, String>(), req, false);
            MilieuClientAsyncTask tsk = new MilieuClientAsyncTask(act);
            tsk.flag = true;
            tsk.execute(request);
        }
    }
}

