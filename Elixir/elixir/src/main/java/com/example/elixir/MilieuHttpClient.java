package com.example.elixir;

import android.net.http.AndroidHttpClient;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by manojthakur on 2/28/15.
 */
public class MilieuHttpClient {

    public static Response request(URI url, boolean get, Map<String, String> parameters, JSONObject content) {
        HttpRequest req;
        HttpHost host = new HttpHost(url.getHost(), url.getPort(), url.getScheme());
        if(get)
            req = new HttpGet(url.toString());
        else {
            try {
                req = new HttpPost(url.toString());
                if (content != null) {
                    final StringEntity se = new StringEntity(content.toString(), "UTF-8");
                    se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    se.setContentType("application/json");
                    ((HttpPost) req).setEntity(se);
                }
            }catch (Exception e) {
                return null;
            }
        }

        HttpParams params  = new BasicHttpParams();
        for(Map.Entry<String, String> entry: parameters.entrySet()) {
           params.setParameter(entry.getKey(), entry.getValue());
        }

        req.setHeader("Content-type","application/json");
        req.setHeader("Accept","application/json");

        try {
            HttpClient client = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(client.getParams(), 30000);
            HttpConnectionParams.setSoTimeout(client.getParams(), 50000);

            HttpResponse response = client.execute((req instanceof HttpPost)?(HttpPost)req:(HttpGet)req);
            int ret = response.getStatusLine().getStatusCode();
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            String line = null;
            StringBuffer buf = new StringBuffer();
            while((line = reader.readLine()) != null) {
                buf.append(line).append("\n");
            }
            JSONObject json = new JSONObject(buf.toString());
            return new Response(json, ret);
        } catch (IOException | JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static class Request {
        public URI uri;
        public HashMap<String, String> parameters;
        public JSONObject reqContent;
        public boolean get;

        public Request(URI uri, HashMap<String, String> parameters, JSONObject reqContent, boolean get) {
            this.uri = uri;
            this.parameters = parameters;
            this.reqContent = reqContent;
            this.get = get;
        }
    }

    public  static class Response {
        public JSONObject content;
        public int code;

        public Response(JSONObject content, int code) {
            this.content = content;
            this.code = code;
        }
    }
}
