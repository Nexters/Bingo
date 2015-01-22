package com.example.lg.fridge.calendar;
/**
 * Calendar를 보여주는 fragment
 * 구글에서 떠온거에 혹 이리저리 붙인 코드라 더러움
 * 양해바람
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lg.fridge.R;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;

/*
Todo: Builder 클래스를 만들어서 customizing을 직관적으로 할 수 있게 만들것
eg)CustomCalendar cal = CustomCalendar.Builder().textSize(15).backgroundImage(R.drawable.bg).selectedDateBackgroundImage(R.drawable.selected).build();
 */
public class CalendarFragment extends Fragment {
    private GregorianCalendar month;
    private CalendarAdapter adapter;
    private ArrayList<String> twoSelectedDates = new ArrayList<String>();
    private ArrayList<View> twoSelectedViews = new ArrayList<View>();
    private TextView title;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.calendar, container, false);
        Locale.setDefault(Locale.KOREA);

        month = (GregorianCalendar)GregorianCalendar.getInstance();
        adapter = new CalendarAdapter(getActivity(), month);

        GridView gridview = (GridView)v.findViewById(R.id.gridview);
        gridview.setAdapter(adapter);

        title = (TextView)v.findViewById(R.id.title);
        title.setText(android.text.format.DateFormat.format("MMMM yyyy",month));

        RelativeLayout previous = (RelativeLayout)v.findViewById(R.id.previous);
        previous.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setPreviousMonth();
                refreshCalendar();
            }
        });

        RelativeLayout next = (RelativeLayout)v.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setNextMonth();
                refreshCalendar();
            }
        });

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                //twoSelectedDates에 있는 날짜와 눌린 날짜를 비교하여
                //이미 리스트에 있는 날짜면 리스트에서 삭제
                String selectedGridDate = CalendarAdapter.dayString.get(position);
                if(twoSelectedDates.contains(selectedGridDate)) {
                    //만약 두 개의 날짜에 이미 포함된 날짜가 클릭되었다면 ~~
                    deleteDateFromSelectedDates(selectedGridDate);
                    unsetSelectedView(v);
                    return;
                } else {
                    addNewDateToSelectedDates(selectedGridDate);
                    setSelectedView(v);
                }

                //이번 달 밖의 날짜가 클릭되었을 때 calendar를 새로운 달로 바꾸기 위한 procedure
                //String[] separatedTime = selectedGridDate.split("-");
                //String gridvalueString = separatedTime[2].replaceFirst("^0*",
                //       "");// taking last part of date. ie; 2 from 2012-12-02.
                //int gridvalue = Integer.parseInt(gridvalueString);
                // navigate to next or previous month on clicking offdays.
                //if ((gridvalue > 10) && (position < 8)) {
                //    setPreviousMonth();
                //    refreshCalendar();
                //} else if ((gridvalue < 7) && (position > 28)) {
                //    setNextMonth();
                //    refreshCalendar();
                //}


            }
        });
        return v;
    }

    private void deleteDateFromSelectedDates(String date) {
        twoSelectedDates.remove(date);

    }

    private void addNewDateToSelectedDates(String date) {
        if (twoSelectedDates.size() == 2) {
            twoSelectedDates.remove(0);
        }
        twoSelectedDates.add(twoSelectedDates.size(), date);
    }

    private void unsetSelectedView(View v) {
        twoSelectedViews.remove(v);
        v.setBackgroundResource(R.drawable.list_item_background);
    }

    //선택된 날짜를 highlight하기 위한 method
    private void setSelectedView(View v) {
        if (twoSelectedViews.size() == 2) {
            twoSelectedViews.get(0).setBackgroundResource(R.drawable.list_item_background);
            twoSelectedViews.remove(0);
        }
        v.setBackgroundResource(R.drawable.calendar_cel_selectl);
        twoSelectedViews.add(twoSelectedViews.size(), v);
    }

    protected void setNextMonth() {
        if (month.get(GregorianCalendar.MONTH) == month
                .getActualMaximum(GregorianCalendar.MONTH)) {
            month.set((month.get(GregorianCalendar.YEAR) + 1),
                    month.getActualMinimum(GregorianCalendar.MONTH), 1);
        } else {
            month.set(GregorianCalendar.MONTH,
                    month.get(GregorianCalendar.MONTH) + 1);
        }

    }

    protected void setPreviousMonth() {
        if (month.get(GregorianCalendar.MONTH) == month
                .getActualMinimum(GregorianCalendar.MONTH)) {
            month.set((month.get(GregorianCalendar.YEAR) - 1),
                    month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            month.set(GregorianCalendar.MONTH,
                    month.get(GregorianCalendar.MONTH) - 1);
        }

    }

    public void refreshCalendar() {
        adapter.refreshDays();
        adapter.notifyDataSetChanged();
        //handler.post(calendarUpdater); // generate some calendar items

        title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
    }
}