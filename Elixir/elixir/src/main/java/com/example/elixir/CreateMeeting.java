package com.example.elixir;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;
/**
 * Created by manojthakur on 2/28/15.
 */
public class CreateMeeting extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_meeting);
    }

    public void submit(View v) {

        String name = ((EditText)findViewById(R.id.meeting_name)).getText().toString();
        String category = ((Spinner)findViewById(R.id.category)).getSelectedItem().toString();

        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(this).getAccounts();
        HashSet<String> emailIDs = new HashSet<String>();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                String possibleEmail = account.name;
                // print out accounts
                Log.i("accountName", possibleEmail);
                emailIDs.add(possibleEmail);
            }
        }
        String email = emailIDs.iterator().next();

        TelephonyManager tMgr = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number().trim();

        List<MeetingBean.MemberBean> memberBeans = new ArrayList<>();
        memberBeans.add(new MeetingBean.MemberBean("",null, mPhoneNumber, email, null));

        String number = ((EditText) findViewById(R.id.phone1)).getText().toString();
        String loc = ((EditText) findViewById(R.id.address1)).getText().toString();

        if(!isEmpty(number))
            memberBeans.add(new MeetingBean.MemberBean("",null, number, loc, null));

        number = ((EditText) findViewById(R.id.phone2)).getText().toString();
        loc = ((EditText) findViewById(R.id.address2)).getText().toString();

        if(!isEmpty(number))
            memberBeans.add(new MeetingBean.MemberBean("",null, number, loc, null));

        number = ((EditText) findViewById(R.id.phone3)).getText().toString();
        loc = ((EditText) findViewById(R.id.address3)).getText().toString();

        if(!isEmpty(number))
            memberBeans.add(new MeetingBean.MemberBean("",null, number, loc, null));

        number = ((EditText) findViewById(R.id.phone4)).getText().toString();
        loc = ((EditText) findViewById(R.id.address4)).getText().toString();

        if(!isEmpty(number))
            memberBeans.add(new MeetingBean.MemberBean("",null, number, loc, null));

        MeetingBean mb = new MeetingBean();
        mb.name = name;
        mb.cat = Categories.valueOf(category);
        mb.members = memberBeans;
        mb.suggestions = new ArrayList<>();
        mb.selected = null;

        process(mb);
    }

    private void process(MeetingBean mb) {

        if(MilieuApp.loc == null) {
            LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            LocListener list = new LocListener(this, mb, mLocationManager);

            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
                    0, list);
        } else {
            Location location = MilieuApp.loc;
            double lat = location.getLatitude();
            double lng = location.getLongitude();

            mb.members.get(0).loc = new LatLng(lat, lng);
            try {
                JSONObject req = new JSONObject();
                req.put("name", mb.getName());
                req.put("category", mb.cat.toString());
                req.put("desc", mb.getName());
                req.put("selectedFinal", new JSONObject());
                req.put("suggestions", new JSONArray());
                JSONArray invited = new JSONArray();
                for (int i = 0; i < mb.members.size(); i++) {
                    JSONObject j = new JSONObject();
                    String name = getContactName(this.getApplicationContext(), mb.members.get(i).phone);
                    j.put("name", isEmpty(name)? mb.members.get(i).phone:name);
                    j.put("phoneNumber", mb.members.get(i).phone);
                    if(mb.members.get(i).loc == null)
                        j.put("loc", mb.members.get(i).email == null?"":mb.members.get(i).email);// hack
                    else {
                        j.put("lat", mb.members.get(i).loc.latitude);
                        j.put("lng", mb.members.get(i).loc.longitude);
                    }
                    invited.put(j);
                    if(i == 0) {
                        req.put("owner", j);
                    }
                }
                req.put("invitedMembers", invited);

                URI u = new URI("http://10.60.0.191:3000/createNewMeeting");
                boolean get = false;
                HashMap<String, String> params = new HashMap<String, String>();

                MilieuHttpClient.Request request = new MilieuHttpClient.Request(u,params, req,get);
                new MilieuClientAsyncTask(this).execute(request);
            } catch (Exception ex) {

            }
        }
    }

    private static class LocListener implements LocationListener {

        Activity act;
        MeetingBean mb;
        LocationManager mLocationManager;

        LocListener(Activity act, MeetingBean mb, LocationManager mLocationManager) {
            this.act = act;
            this.mb = mb;
            this.mLocationManager = mLocationManager;
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

                mb.members.get(0).loc = new LatLng(lat, lng);
                try {
                    JSONObject req = new JSONObject();
                    req.put("name", mb.getName());
                    req.put("category", mb.cat.toString());
                    req.put("desc", mb.getName());
                    req.put("selectedFinal", new JSONObject());
                    req.put("suggestions", new JSONArray());
                    JSONArray invited = new JSONArray();
                    for (int i = 0; i < mb.members.size(); i++) {
                        JSONObject j = new JSONObject();
                        String name = getContactName(act.getApplicationContext(), mb.members.get(i).phone);
                        j.put("name", isEmpty(name) ? mb.members.get(i).phone : name);
                        j.put("phoneNumber", mb.members.get(i).phone);
                        if (mb.members.get(i).loc == null)
                            j.put("loc", mb.members.get(i).email == null ? "" : mb.members.get(i).email);// hack
                        else {
                            j.put("lat", mb.members.get(i).loc.latitude);
                            j.put("lng", mb.members.get(i).loc.longitude);
                        }
                        invited.put(j);
                        if (i == 0) {
                            req.put("owner", j);
                        }
                    }
                    req.put("invitedMembers", invited);

                    URI u = new URI("http://10.60.0.191:3000/createNewMeeting");
                    boolean get = false;
                    HashMap<String, String> params = new HashMap<String, String>();

                    MilieuHttpClient.Request request = new MilieuHttpClient.Request(u, params, req, get);
                    new MilieuClientAsyncTask(act).execute(request);
                   // this.mLocationManager.removeUpdates(this);

                } catch (Exception ex) {

                } finally {
                    this.mLocationManager.removeUpdates(this);
                }
        }
    }

    private static String getContactName(Context context, String number) {

        String name = null;

        // define the columns I want the query to return
        String[] projection = new String[] {
                ContactsContract.PhoneLookup.DISPLAY_NAME,
                ContactsContract.PhoneLookup._ID};

        // encode the phone number and build the filter URI
        Uri contactUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));

        // query time
        Cursor cursor = context.getContentResolver().query(contactUri, projection, null, null, null);

        if(cursor != null) {
            if (cursor.moveToFirst()) {
                name =      cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
                Log.v("MILIEU", "Started uploadcontactphoto: Contact Found @ " + number);
                Log.v("MILIEU", "Started uploadcontactphoto: Contact name  = " + name);
            } else {
                Log.v("MILIEU", "Contact Not Found @ " + number);
            }
            cursor.close();
        }
        return name;
    }

    private static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public void clear(View v) {
        EditText meeting_name=(EditText) findViewById(R.id.meeting_name);
        meeting_name.setText("");

        EditText phone=(EditText) findViewById(R.id.phone1);
        phone.setText("");
        phone=(EditText) findViewById(R.id.phone2);
        phone.setText("");
        phone=(EditText) findViewById(R.id.phone3);
        phone.setText("");
        phone=(EditText) findViewById(R.id.phone4);
        phone.setText("");

        EditText addr=(EditText) findViewById(R.id.address1);
        addr.setText("");
        addr=(EditText) findViewById(R.id.address2);
        addr.setText("");
        addr=(EditText) findViewById(R.id.address3);
        addr.setText("");
        addr=(EditText) findViewById(R.id.address4);
        addr.setText("");

        /*
        EditText addressEntry1=(EditText) findViewById(R.id.addressEntry1);
        addressEntry1.setText("");
        EditText emailEntry1=(EditText) findViewById(R.id.emailEntry1);
        emailEntry1.setText("");
        EditText participantName1=(EditText) findViewById(R.id.participantName1);
        participantName1.setText("");
        EditText participantName2=(EditText) findViewById(R.id.participantName2);
        participantName2.setText("");
        */
    }
}
