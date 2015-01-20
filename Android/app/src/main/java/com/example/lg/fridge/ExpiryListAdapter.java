package com.example.lg.fridge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Created by LG on 2015-01-21.
 */
public class ExpiryListAdapter extends BaseAdapter{
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;

    private ArrayList<String> mData = new ArrayList<String>();
    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();

    private LayoutInflater mInflater;

    public ExpiryListAdapter(Context c) {
        mInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(final String item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void addSectionHeaderItem(final String item) {
        mData.add(item);
        sectionHeader.add(mData.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        int rowType = getItemViewType(position);

        if (convertView == null) {
            holder = new ViewHolder();
            switch(rowType) {
                case TYPE_ITEM:
                    convertView = mInflater.inflate(R.layout.expiry_list_item, null);
                    holder.textView = (TextView)convertView.findViewById(R.id.expiry_list_item_text);
                    break;
                case TYPE_SEPARATOR:
                    convertView = mInflater.inflate(R.layout.expiry_list_header, null);
                    holder.textView = (TextView)convertView.findViewById(R.id.expiry_list_header_text);
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(mData.get(position));

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return sectionHeader.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    //static class로 한 이유는 ViewHolder holder = new ViewHolder();를 부를 때 마다
    //새로운 객체를 매번 생성하는 것을 막기 위함.
    public static class ViewHolder {
        public TextView textView;
    }
}
