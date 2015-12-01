package com.sample.appmojo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sample.appmojo.R;

/**
 * Created by nutron on 12/1/15 AD.
 */
public class MainListAdapter extends BaseAdapter {

    private String[] dataSet;
    private LayoutInflater mInflater;

    public MainListAdapter(Context context, String[] dataSet) {
        this.dataSet = dataSet;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dataSet.length;
    }

    @Override
    public Object getItem(int position) {
        return dataSet[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // object item based on the position
        String data = dataSet[position];
        if(data != null) {
            viewHolder.mTextView.setText(data);
        }
        return convertView;
    }



    public static class ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolder(View v) {
            mTextView = (TextView) v.findViewById(R.id.item_title_tv);
        }
    }
}
