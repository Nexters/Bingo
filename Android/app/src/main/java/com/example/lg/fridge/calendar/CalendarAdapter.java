package com.example.lg.fridge.calendar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lg.fridge.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/***
 * Custom calendar를 위한 adapter
 */

/**
 * Created by LG on 2015-01-15.
 */
public class CalendarAdapter extends BaseAdapter {
    private Context mContext;

    private java.util.Calendar month;
    public GregorianCalendar pmonth; // calendar instance for previous month
    /**
     * calendar instance for previous month for getting complete view
     */
    public GregorianCalendar pmonthmaxset;
    //private GregorianCalendar selectedDate;
    int firstDay;
    int maxWeeknumber;
    int maxP;
    int calMaxP;
    int mnthlength;
    String itemvalue, curentDateString;
    DateFormat df;
    private Calendar cal;

    //private ArrayList<String> items;
    public static List<String> dayString;
    private View previousView;

    public CalendarAdapter(Context c, GregorianCalendar monthCalendar) {
        CalendarAdapter.dayString = new ArrayList<String>();
        Locale.setDefault(Locale.KOREA);
        month = monthCalendar;
        //selectedDate = (GregorianCalendar) monthCalendar.clone();
        mContext = c;
        month.set(GregorianCalendar.DAY_OF_MONTH, 1);
        //this.items = new ArrayList<String>();
        df = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        curentDateString = df.format(month.getTime());
        cal = Calendar.getInstance();
        refreshDays();
    }
/*
    public void setItems(ArrayList<String> items) {
        for (int i = 0; i != items.size(); i++) {
            if (items.get(i).length() == 1) {
                items.set(i, "0" + items.get(i));
            }
        }
        //this.items = items;
    }
*/
    public int getCount() {
        return dayString.size();
    }

    public Object getItem(int position) {

        return dayString.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new view for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        TextView dayView;
        if (convertView == null) { // if it's not recycled, initialize some
            // attributes
            LayoutInflater vi = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.calendar_item, null);

        }
        dayView = (TextView) v.findViewById(R.id.date);
        // separates daystring into parts.
        String[] separatedTime = dayString.get(position).split("-");

        //토,일을 걸러내서 빨간색날짜로 표기
        cal.set(Calendar.YEAR, Integer.parseInt(separatedTime[0]));
        cal.set(Calendar.MONTH, Integer.parseInt(separatedTime[1])-1);
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(separatedTime[2]));
        int day = cal.get(Calendar.DAY_OF_WEEK);

        // taking last part of date. ie; 2 from 2012-12-02
        String gridvalue = separatedTime[2].replaceFirst("^0*", "");
        if (day == Calendar.SUNDAY || day == Calendar.SATURDAY) {
            //만약 주말이면 빨간색 글씨로
            dayView.setTextColor(Color.RED);
        } else {
            // checking whether the day is in current month or not.
            if ((Integer.parseInt(gridvalue) > 1) && (position < firstDay)) {
                // setting offdays to white color.
                dayView.setTextColor(Color.WHITE);                      //이 달에 포함된 날짜가 아닌 경우의 색깔을 정하는 곳
                dayView.setClickable(false);
                dayView.setFocusable(false);
            } else if ((Integer.parseInt(gridvalue) < 7) && (position > 28)) {
                dayView.setTextColor(Color.WHITE);                      //이 달에 포함된 날짜가 아닌 경우의 색깔을 정하는 곳
                dayView.setClickable(false);
                dayView.setFocusable(false);
            } else{
                // setting curent month's days in blue color.
                dayView.setTextColor(Color.BLUE);                      //이 달에 포함된 날짜인 경우의 색깔을 정하는 곳
            }
        }

        if (dayString.get(position).equals(curentDateString)) {
            setSelected(v);
            previousView = v;
        } else {
            v.setBackgroundResource(R.drawable.list_item_background);                           //셀이 눌렸을때와 안눌렸을때의 배경 설정
        }
        dayView.setText(gridvalue);

        // create date string for comparison
        /*
        String date = dayString.get(position);

        if (date.length() == 1) {
            date = "0" + date;
        }
        String monthStr = "" + (month.get(GregorianCalendar.MONTH) + 1);
        if (monthStr.length() == 1) {
            monthStr = "0" + monthStr;
        }
        */
        // show icon if date is not empty and it exists in the items array
        /*
        ImageView iw = (ImageView) v.findViewById(R.id.date_icon);
        if (date.length() > 0 && items != null && items.contains(date)) {
            iw.setVisibility(View.VISIBLE);
        } else {
            iw.setVisibility(View.INVISIBLE);
        }
        */
        return v;
    }

    public View setSelected(View view) {
        if (previousView != null) {
            previousView.setBackgroundResource(R.drawable.list_item_background);
        }
        previousView = view;
        view.setBackgroundColor(Color.RED);
        return view;
    }
    //완전 더러운 코드
    //근데 다른 사람꺼 가져와서 하는거라 그냥 떼어붙임
    //clearTodayHighlight() 는 사용자가 날짜를 클릭했을 때
    //기본으로 오늘 날짜가 하이라이트 된 것을 지우기 위함
    public void clearTodayHighlight() {
        if (previousView != null) {
            previousView.setBackgroundResource(R.drawable.list_item_background);
            previousView = null;
        }

    }

    public void refreshDays() {
        // clear items
        //items.clear();
        dayString.clear();
        Locale.setDefault(Locale.KOREA);
        pmonth = (GregorianCalendar) month.clone();
        // month start day. ie; sun, mon, etc
        firstDay = month.get(GregorianCalendar.DAY_OF_WEEK);
        // finding number of weeks in current month.
        maxWeeknumber = month.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
        // allocating maximum row number for the gridview.
        mnthlength = maxWeeknumber * 7;
        maxP = getMaxP(); // previous month maximum day 31,30....
        calMaxP = maxP - (firstDay - 1);// calendar offday starting 24,25 ...
        /**
         * Calendar instance for getting a complete gridview including the three
         * month's (previous,current,next) dates.
         */
        pmonthmaxset = (GregorianCalendar) pmonth.clone();
        /**
         * setting the start date as previous month's required date.
         */
        pmonthmaxset.set(GregorianCalendar.DAY_OF_MONTH, calMaxP + 1);

        /**
         * filling calendar gridview.
         */
        for (int n = 0; n < mnthlength; n++) {

            itemvalue = df.format(pmonthmaxset.getTime());
            pmonthmaxset.add(GregorianCalendar.DATE, 1);
            dayString.add(itemvalue);

        }
    }

    private int getMaxP() {
        int maxP;
        if (month.get(GregorianCalendar.MONTH) == month
                .getActualMinimum(GregorianCalendar.MONTH)) {
            pmonth.set((month.get(GregorianCalendar.YEAR) - 1),
                    month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            pmonth.set(GregorianCalendar.MONTH,
                    month.get(GregorianCalendar.MONTH) - 1);
        }
        maxP = pmonth.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

        return maxP;
    }


}
