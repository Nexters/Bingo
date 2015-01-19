package com.example.lg.fridge;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.lg.fridge.calendar.CalendarFragment;

/**
 * Created by LG on 2015-01-16.
 */
public class EditFoodFragment extends DialogFragment{
    private Button showCalButton;
    private Button hideCalButton;
    private View mainView;
    private LinearLayout noCalendarView;
    private LinearLayout yesCalendarView;
    private CalendarFragment calendarFragment;


    public EditFoodFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        calendarFragment = new CalendarFragment();
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);            //Titlebar 없앰
        mainView = inflater.inflate(R.layout.edit_food_fragment, container, false);
        noCalendarView = (LinearLayout)mainView.findViewById(R.id.dialog_no_calendar_view);
        yesCalendarView = (LinearLayout)mainView.findViewById(R.id.dialog_calendar_view);

        //달력 fragment 삽입
        //Todo: t.add(~)에서 달력을 넣을 container를 xml에 지정해주면 될 것 같다.
        FragmentManager m = getChildFragmentManager();
        FragmentTransaction t = m.beginTransaction();
        t.add(R.id.dialog_calendar_view, calendarFragment).commit();

        showCalButton = (Button)mainView.findViewById(R.id.dialog_btn_show_calendar);
        showCalButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //TODO: 이 버튼을 클릭하면 뷰를 스위치하면서 CALENDAR를 보여준다
                //TEST CODE
                /*
                LinearLayout container = (LinearLayout)mainView.findViewById(R.id.dialog_linearlayout);
                TextView txt = new TextView(getActivity());
                txt.setText("I am added");
                container.addView(txt);
                */
                showCalendar();
            }
        });
        hideCalButton = (Button)mainView.findViewById(R.id.dialog_btn_hide_calendar);
        hideCalButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //TODO: 이 버튼을 누르면 뷰를 스위치하면서 CALENDAR를 숨긴다
                hideCalendar();
            }
        });
        return mainView;
    }

    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams)params);

        //나타날때 fade in 하는 애니메이션
        getDialog().getWindow().setWindowAnimations(R.style.dialog_fade_in_animation);
        super.onResume();

    }

    private void showCalendar() {
        noCalendarView.setVisibility(View.GONE);
        yesCalendarView.setVisibility(View.VISIBLE);
    }

    private void hideCalendar() {
        noCalendarView.setVisibility(View.VISIBLE);
        yesCalendarView.setVisibility(View.GONE);
    }
}
