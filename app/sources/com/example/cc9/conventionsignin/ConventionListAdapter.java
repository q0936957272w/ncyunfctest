package com.example.cc9.conventionsignin;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONObject;

public class ConventionListAdapter extends BaseAdapter {
    private static JSONArray itemArray;
    private LayoutInflater mInflater;

    static class ViewHolder {
        ImageView Image1;
        TextView Item1;
        TextView Item2;

        ViewHolder() {
        }
    }

    public ConventionListAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        createAdapterData();
    }

    private void createAdapterData() {
        try {
            AsyncTask<String, Void, JSONObject> task = new HttpGetJSON<>();
            task.execute(new String[]{"https://web085004.adm.ncyu.edu.tw/conventioncloud/api/conventionList?processId=" + GlobalVars.CurrentProcessId});
            itemArray = ((JSONObject) task.get()).getJSONArray("Data");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getCount() {
        return itemArray.length();
    }

    public Object getItem(int position) {
        try {
            return itemArray.getJSONObject(position);
        } catch (Exception e) {
            return null;
        }
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = this.mInflater.inflate(C0008R.layout.row, null);
            holder = new ViewHolder();
            holder.Item1 = (TextView) convertView.findViewById(C0008R.C0009id.textView2);
            holder.Item2 = (TextView) convertView.findViewById(C0008R.C0009id.textView3);
            holder.Image1 = (ImageView) convertView.findViewById(C0008R.C0009id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        try {
            JSONObject item = itemArray.getJSONObject(position);
            holder.Item1.setText(item.getString("Name"));
            holder.Item2.setText(item.getString("Description"));
            holder.Image1.setImageResource(C0008R.drawable.convention);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }
}
