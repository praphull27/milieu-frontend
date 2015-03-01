package com.example.elixir;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by manojthakur on 2/28/15.
 */
public class MilieuClientAsyncTask extends AsyncTask<MilieuHttpClient.Request, Integer, MilieuHttpClient.Response> {
    private Activity a;
    public boolean flag = false;

    MilieuClientAsyncTask(Activity a) {
        this.a = a;
    }
    @Override
    protected MilieuHttpClient.Response doInBackground(MilieuHttpClient.Request... params) {
        return MilieuHttpClient.request(params[0].uri, params[0].get ,params[0].parameters, params[0].reqContent);
    }

    @Override
    protected void onPostExecute(MilieuHttpClient.Response response) {
        //super.onPostExecute(response);
        //Toast.makeText(a.getApplicationContext(), response.content.toString(), Toast.LENGTH_LONG).show();
        if(a instanceof ListViewActivity) {
            if(!flag)
                ((ListViewActivity)a).postHttpCallProcess(response);
            else {
                Intent i = new Intent(a, MainPage.class);
                a.startActivity(i);
            }
        } else if(a instanceof CreateMeeting) {
            Intent i = new Intent(a, MainPage.class);
            a.startActivity(i);
        }
    }
}
