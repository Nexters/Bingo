package com.example.lg.fridge.calendar;
/**
 * Calendar만을 보여주는 fragment
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


public class CalendarFragment extends Fragment {
    private View v;         //최상위 view
    public GregorianCalendar month, itemmonth;// calendar instances.

    public CalendarAdapter adapter;// adapter instance
    //ArrayList<String> date;
    //ArrayList<String> desc;
    private ArrayList<String> twoSelectedDates = new ArrayList<String>();
    private ArrayList<View> twoSelectedViews = new ArrayList<View>();
    //private TextView tvFirstDate;
    //private TextView tvSecondDate;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.calendar, container, false);
        Locale.setDefault(Locale.KOREA);

        //tvFirstDate = (TextView)v.findViewById(R.id.cal_tv_firstdate);
        //tvSecondDate = (TextView)v.findViewById(R.id.cal_tv_seconddate);

        month = (GregorianCalendar) GregorianCalendar.getInstance();
        //itemmonth = (GregorianCalendar) month.clone();

        adapter = new CalendarAdapter(getActivity(), month);

        GridView gridview = (GridView) v.findViewById(R.id.gridview);
        gridview.setAdapter(adapter);

        TextView title = (TextView) v.findViewById(R.id.title);
        title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));

        RelativeLayout previous = (RelativeLayout) v.findViewById(R.id.previous);

        previous.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setPreviousMonth();
                refreshCalendar();
            }
        });

        RelativeLayout next = (RelativeLayout) v.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setNextMonth();
                refreshCalendar();

            }
        });

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                //desc = new ArrayList<String>();
                //date = new ArrayList<String>();
                //twoSelectedDates에 있는 날짜와 눌린 날짜를 비교하여
                //이미 리스트에 있는 날짜면 뒤의 처리를 안하고 넘어간다
                //((CalendarAdapter)parent.getAdapter()).clearTodayHighlight(); //이 메소드는 정말 더러운 코드지만 오늘 날짜 하이라이트 지우기위한것
                String selectedGridDate = CalendarAdapter.dayString
                        .get(position);                                             //selectedGridDate가 선택된 날짜의 string
                if (twoSelectedDates.contains(selectedGridDate)) {      //만약 두 개의 날짜에 이미 포함된 날짜가 클릭되었다면
                    //아무 처리도 안하고 넘어간다
                    return;
                } else {                //두 개의 날짜에 있지 않은 날짜가 클릭되었을 경우
                    addNewDateToSelectedDates(selectedGridDate);
                    refreshTextViews();
                    setSelectedViews(v);
                }

                //이번 달 밖의 날짜가 클릭되었을 때 calendar를 새로운 달로 바꾸기 위한 procedure
                String[] separatedTime = selectedGridDate.split("-");
                String gridvalueString = separatedTime[2].replaceFirst("^0*",
                        "");// taking last part of date. ie; 2 from 2012-12-02.
                int gridvalue = Integer.parseInt(gridvalueString);
                // navigate to next or previous month on clicking offdays.
                if ((gridvalue > 10) && (position < 8)) {
                    setPreviousMonth();
                    refreshCalendar();
                } else if ((gridvalue < 7) && (position > 28)) {
                    setNextMonth();
                    refreshCalendar();
                }


            }

        });
        return v;
    }

    private void addNewDateToSelectedDates(String date) {
        if (twoSelectedDates.size() == 2) {
            twoSelectedDates.remove(0);
        }
        twoSelectedDates.add(twoSelectedDates.size(), date);
    }

    //선택된 날짜를 highlight하기 위한 method
    private void setSelectedViews(View v) {
        if (twoSelectedViews.size() == 2) {
            twoSelectedViews.get(0).setBackgroundResource(R.drawable.list_item_background);
            twoSelectedViews.remove(0);
        }
        v.setBackgroundResource(R.drawable.calendar_cel_selectl);
        twoSelectedViews.add(twoSelectedViews.size(), v);
    }

    //선택된 두 날짜를 업뎃하기 위한 method
    private void refreshTextViews() {
        if (twoSelectedDates.size() == 1) {
            //tvFirstDate.setText(twoSelectedDates.get(0));
        }
        if (twoSelectedDates.size() == 2) {
            //tvFirstDate.setText(twoSelectedDates.get(0));
            //tvSecondDate.setText(twoSelectedDates.get(1));
        }
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
        TextView title = (TextView)v.findViewById(R.id.title);

        adapter.refreshDays();
        adapter.notifyDataSetChanged();
        //handler.post(calendarUpdater); // generate some calendar items

        title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
    }
}
