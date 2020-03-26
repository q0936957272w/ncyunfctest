package com.example.cc9.conventionsignin;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import org.json.JSONObject;

public class ConventionListActivity extends Activity {
    public ListView conventionListView;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0008R.layout.activity_convention_list);
        this.conventionListView = (ListView) findViewById(C0008R.C0009id.conventionListView);
        Bundle bundle = getIntent().getExtras();
        String userId = bundle.getString("userId");
        String cardHexKey = bundle.getString("cardHexKey");
        try {
            AsyncTask<String, Void, String> task = new HttpGetString<>();
            task.execute(new String[]{"https://web085004.adm.ncyu.edu.tw/conventioncloud/api/authorize?cardHexKey=" + cardHexKey + "&userId=" + userId});
            GlobalVars.CurrentProcessId = (String) task.get();
            this.conventionListView.setAdapter(new ConventionListAdapter(this));
            this.conventionListView.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        JSONObject item = (JSONObject) parent.getItemAtPosition(position);
                        GlobalVars.CurrentConventionId = item.getString("Id");
                        Builder adBuild = new Builder(ConventionListActivity.this);
                        adBuild.setTitle(item.getString("Name"));
                        adBuild.setNegativeButton(C0008R.string.records, new OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.setClass(ConventionListActivity.this, RecordListActivity.class);
                                ConventionListActivity.this.startActivity(intent);
                            }
                        });
                        adBuild.setNeutralButton(C0008R.string.signin, new OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.setClass(ConventionListActivity.this, SignInActivity.class);
                                ConventionListActivity.this.startActivity(intent);
                            }
                        });
                        adBuild.setPositiveButton(C0008R.string.close, new OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        adBuild.create().show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return true;
        }
        Builder adBuild = new Builder(this);
        adBuild.setTitle(C0008R.string.exitApp_dialog_title);
        adBuild.setNegativeButton(C0008R.string.submit, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                System.exit(0);
            }
        });
        adBuild.setPositiveButton(C0008R.string.cancel, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        adBuild.create().show();
        return false;
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
