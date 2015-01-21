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
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_BLANK = 2;

    private ArrayList<String> mData = new ArrayList<String>();
    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();
    //걍 한번 만들어보는 section간의 간격 구현
    private TreeSet<Integer> blankSpace = new TreeSet<Integer>();

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

    /**
     *
     * @param item: could be anything. doesen't matter. just set it to blank string
     */
    public void addBlankSpace(final String item) {
        mData.add(item);
        blankSpace.add(mData.size() - 1);
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
                case TYPE_HEADER:
                    convertView = mInflater.inflate(R.layout.expiry_list_header, null);
                    holder.textView = (TextView)convertView.findViewById(R.id.expiry_list_header_text);
                    break;
                case TYPE_BLANK:
                    //blank space일 경우
                    convertView = mInflater.inflate(R.layout.expiry_list_blank, null);
                    holder.textView = null;     //set textview to null because textview doesn't exist in blank space
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (holder.textView != null)
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
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        if (sectionHeader.contains(position)) {
            //header일 경우
            return TYPE_HEADER;
        } else if (blankSpace.contains(position)) {
            //blank space일 경우
            return TYPE_BLANK;
        }else {
            //item일 경우
            return TYPE_ITEM;
        }
    }

    //static class로 한 이유는 ViewHolder holder = new ViewHolder();를 부를 때 마다
    //새로운 객체를 매번 생성하는 것을 막기 위함.
    public static class ViewHolder {
        public TextView textView;
    }
}
