package com.example.cc9.conventionsignin;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class RecordListActivity extends Activity {
    public ListView recordListView;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0008R.layout.activity_record_list);
        this.recordListView = (ListView) findViewById(C0008R.C0009id.recordListView);
        this.recordListView.setAdapter(new RecordListAdapter(this));
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
