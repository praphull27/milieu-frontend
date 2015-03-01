package com.example.elixir;

/**
 * Created by manojthakur on 2/27/15.
 */
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapViewActivity extends Activity {
    static final LatLng LA = new LatLng(34.0500, -118.2500);

    private GoogleMap map;
    private MeetingBean mbean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_map_view);
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        // Move the camera instantly to hamburg with a zoom of 15.
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LA, 20));
        // Zoom in, animating the camera.
        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        map.setMyLocationEnabled(true);

        mbean = getIntent().getParcelableExtra("meeting");
        for(MeetingBean.MemberBean mem: mbean.getMembers()) {
            if(mem.getLoc() != null && mem.getLoc().latitude != -1) {
                Marker position = map.addMarker(new MarkerOptions()
                        .position(mem.getLoc())
                        .title(mbean.getName())
                        .snippet(mbean.getDesc()));
            }
        }
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);

        for(MeetingBean.SuggestionBean mem: mbean.getSuggestions()) {
            if(mem.getLoc() != null  && mem.getLoc().latitude != -1) {
                Marker position = map.addMarker(new MarkerOptions()
                        .position(mem.getLoc())
                        .title(mbean.getName())
                        .icon(bitmapDescriptor)
                        .snippet(mbean.getDesc()));
            }
        }

    }
}