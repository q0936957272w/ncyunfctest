package com.example.cc9.conventionsignin;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class MainActivity extends Activity {
    /* access modifiers changed from: private */
    public String cardHexKey;
    /* access modifiers changed from: private */
    public String[] loginIdItems;
    public TextView loginText;
    public NfcAdapter mAdapter;
    public IntentFilter[] mFilters;
    public PendingIntent mPendingIntent;
    public String[][] mTechLists;

    public class CardSignin extends AsyncTask<String, Void, String> {
        public CardSignin() {
        }

        /* access modifiers changed from: protected */
        public String doInBackground(String... apiURL) {
            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
            HttpConnectionParams.setSoTimeout(httpParams, 10000);
            SchemeRegistry schemeRegistry = new SchemeRegistry();
            schemeRegistry.register(new Scheme("https", new EasySSLSocketFactory(), 443));
            ClientConnectionManager connManager = new ThreadSafeClientConnManager(httpParams, schemeRegistry);
            String message = BuildConfig.FLAVOR;
            try {
                HttpResponse httpResponse = new DefaultHttpClient(connManager, httpParams).execute(new HttpGet(apiURL[0]));
                return httpResponse.getStatusLine().getStatusCode() == 200 ? new JSONObject(EntityUtils.toString(httpResponse.getEntity())).getString("Message") : message;
            } catch (ClientProtocolException ex) {
                return ex.getMessage();
            } catch (IOException ex2) {
                return ex2.getMessage();
            } catch (Exception ex3) {
                return ex3.getMessage();
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
            MainActivity.this.uidtext.setText(result);
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0008R.layout.activity_main);
        this.mAdapter = NfcAdapter.getDefaultAdapter(this);
        this.mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(536870912), 0);
        this.loginText = (TextView) findViewById(C0008R.C0009id.loginText);
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        Log.i("Foreground dispatch", "Discovered tag with intent: " + intent);
        resolveIntent(intent);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.mAdapter.disableForegroundDispatch(this);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.mAdapter.enableForegroundDispatch(this, this.mPendingIntent, this.mFilters, this.mTechLists);
    }

    /* access modifiers changed from: 0000 */
    public void resolveIntent(Intent intent) {
        Log.i("test", "start method");
        if ("android.nfc.action.TAG_DISCOVERED".equals(intent.getAction())) {
            Log.i("test", "in if");
            try {
                this.cardHexKey = CardHelper.getHexString(((Tag) intent.getParcelableExtra("android.nfc.extra.TAG")).getId());
                AsyncTask<String, Void, String> task = new HttpGetString<>();
                task.execute(new String[]{"https://web085004.adm.ncyu.edu.tw/conventioncloud/api/person?cardHexKey=" + this.cardHexKey});
                String message = (String) task.get();
                this.loginText.setText(message);
                Toast.makeText(this, message, 1).show();
                AsyncTask<String, Void, String> loginTask = new HttpGetString<>();
                loginTask.execute(new String[]{"https://web085004.adm.ncyu.edu.tw/conventioncloud/api/login?cardHexKey=" + this.cardHexKey});
                this.loginIdItems = ((String) loginTask.get()).split(",");
                Builder adBuild = new Builder(this);
                adBuild.setTitle(C0008R.string.login_dialog_title);
                adBuild.setItems(this.loginIdItems, new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Bundle bundle = new Bundle();
                        bundle.putString("userId", MainActivity.this.loginIdItems[which]);
                        bundle.putString("cardHexKey", MainActivity.this.cardHexKey);
                        Intent listActivityIntent = new Intent();
                        listActivityIntent.setClass(MainActivity.this, ConventionListActivity.class);
                        listActivityIntent.putExtras(bundle);
                        MainActivity.this.startActivity(listActivityIntent);
                        MainActivity.this.finish();
                    }
                });
                adBuild.setNegativeButton(C0008R.string.cancel, new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.loginText.setText(C0008R.string.loginText);
                    }
                });
                adBuild.create().show();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        Log.i("test", "end method");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == C0008R.C0009id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
