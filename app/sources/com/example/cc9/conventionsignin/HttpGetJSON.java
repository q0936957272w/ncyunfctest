package com.example.cc9.conventionsignin;

import android.os.AsyncTask;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class HttpGetJSON extends AsyncTask<String, Void, JSONObject> {
    /* access modifiers changed from: protected */
    public JSONObject doInBackground(String... apiURL) {
        try {
            HttpResponse httpResponse = new DefaultHttpClient().execute(new HttpGet(apiURL[0]));
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                return new JSONObject(EntityUtils.toString(httpResponse.getEntity()));
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public void onPreExecute() {
        super.onPreExecute();
    }

    /* access modifiers changed from: protected */
    public void onProgressUpdate(Void... arg) {
        super.onProgressUpdate(arg);
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(JSONObject result) {
        super.onPostExecute(result);
    }
}
