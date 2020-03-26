package com.example.cc9.conventionsignin;

import android.os.AsyncTask;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpGetString extends AsyncTask<String, Void, String> {
    /* access modifiers changed from: protected */
    public String doInBackground(String... apiURL) {
        String result = BuildConfig.FLAVOR;
        try {
            HttpResponse httpResponse = new DefaultHttpClient().execute(new HttpGet(apiURL[0]));
            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                return result;
            }
            String result2 = EntityUtils.toString(httpResponse.getEntity());
            if (result2 != "\"\"") {
                return result2.substring(1, result2.length() - 1);
            }
            return result2;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
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
    public void onPostExecute(String result) {
        super.onPostExecute(result);
    }
}
