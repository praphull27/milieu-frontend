package com.example.elixir;

import android.app.Activity;
import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class CreateMeetingActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_create_meeting_view);

        Button button = (Button) findViewById(R.id.create);
        button.setOnClickListener(new ButtonClickListener(this));
    }

    private static class ButtonClickListener implements View.OnClickListener {
        private Activity act;

        public ButtonClickListener(Activity act) {
            this.act = act;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(act, CreateMeeting.class);
            act.startActivity(intent);
            /*
            try {
                URI u = new URI("http://ip.jsontest.com/");
                boolean get = true;
                HashMap<String, String> params = new HashMap<String, String>();

                new MilieuClientAsyncTask(act).execute(new MilieuHttpClient.Request(u, params, null, get));
            } catch (URISyntaxException ex) {

            }
            */
        }
    }
}
