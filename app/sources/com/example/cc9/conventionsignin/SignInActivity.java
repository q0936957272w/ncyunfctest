package com.example.cc9.conventionsignin;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONObject;

public class SignInActivity extends Activity {
    public Button btnSubmit;
    private String cardHexKey;
    public NfcAdapter mAdapter;
    public IntentFilter[] mFilters;
    public PendingIntent mPendingIntent;
    public String[][] mTechLists;
    public TextView signInText;
    public EditText tbxPersonId;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0008R.layout.activity_sign_in);
        this.mAdapter = NfcAdapter.getDefaultAdapter(this);
        this.mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(536870912), 0);
        this.signInText = (TextView) findViewById(C0008R.C0009id.signInText);
        this.tbxPersonId = (EditText) findViewById(C0008R.C0009id.tbxPersonId);
        this.btnSubmit = (Button) findViewById(C0008R.C0009id.btnSubmit);
        this.btnSubmit.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String personId = SignInActivity.this.tbxPersonId.getText().toString();
                try {
                    AsyncTask<String, Void, JSONObject> task = new HttpGetJSON<>();
                    task.execute(new String[]{"https://web085004.adm.ncyu.edu.tw/conventioncloud/api/signIn2?personId=" + personId + "&conventionId=" + GlobalVars.CurrentConventionId + "&processId=" + GlobalVars.CurrentProcessId});
                    Toast.makeText(SignInActivity.this, ((JSONObject) task.get()).getString("Message"), 1).show();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
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
                AsyncTask<String, Void, JSONObject> task = new HttpGetJSON<>();
                task.execute(new String[]{"https://web085004.adm.ncyu.edu.tw/conventioncloud/api/signIn?cardHexKey=" + this.cardHexKey + "&conventionId=" + GlobalVars.CurrentConventionId + "&processId=" + GlobalVars.CurrentProcessId});
                Toast.makeText(this, ((JSONObject) task.get()).getString("Message"), 0).show();
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
