package com.example.lg.fridge.calendar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

    private Calendar month;
    private GregorianCalendar pmonth;	//instance for previous month

    private GregorianCalendar pmonthmaxset;
    private GregorianCalendar selectedDate;
    private int firstDay;
    private int maxWeeknumber;
    private int maxP;
    private int calMaxP;
    private int mnthlength;
    private String itemvalue, currentDateString;
    private DateFormat df;
    private Calendar cal;
    private String gridvalue;		//각 grid에 들어갈 실질적인 string value

    public static List<String> dayString;

    public CalendarAdapter(Context c, GregorianCalendar monthCalendar) {
        CalendarAdapter.dayString = new ArrayList<String>();
        Locale.setDefault(Locale.KOREA);
        month = monthCalendar;
        selectedDate = (GregorianCalendar)monthCalendar.clone();
        mContext = c;
        month.set(GregorianCalendar.DAY_OF_MONTH, 1);
        df = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        currentDateString = df.format(selectedDate.getTime());
        cal = Calendar.getInstance();
        refreshDays();
    }

    protected void refreshDays() {
        dayString.clear();
        Locale.setDefault(Locale.KOREA);
        pmonth = (GregorianCalendar)month.clone();
        //현재 달의 제일 첫 번째 날을 구함
        firstDay = month.get(GregorianCalendar.DAY_OF_WEEK);
        //현재 달의 총 주(week) 수를 구함
        maxWeeknumber = month.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
        //gridview의 최대 row 갯수를 정함
        mnthlength = maxWeeknumber * 7;
        //지난달의 maximum day를 구함
        maxP = getMaxP();
        calMaxP = maxP - (firstDay - 1);
        pmonthmaxset = (GregorianCalendar)pmonth.clone();
        pmonthmaxset.set(GregorianCalendar.DAY_OF_MONTH, calMaxP + 1);

        //filling calendar gridview
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

    public int getCount() {
        return dayString.size();
    }

    public Object getItem(int position) {

        return dayString.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

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

        gridvalue = separatedTime[2].replaceFirst("0*", "");
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

        if (dayString.get(position).equals(currentDateString)) {
            highlightToday(v);
        } else {
            v.setBackgroundResource(R.drawable.list_item_background);                           //셀이 눌렸을때와 안눌렸을때의 배경 설정
        }
        dayView.setText(gridvalue);

        return v;
    }

    public void highlightToday(View v) {
        v.setBackgroundColor(Color.RED);
    }
}