package com.example.lg.fridge;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.example.lg.fridge.calendar.CalendarFragment;

public class ViewFoodFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types and number of parameters
    public static ViewFoodFragment newInstance(String param1, String param2) {
        ViewFoodFragment fragment = new ViewFoodFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);            //Titlebar 없앰
        View v = inflater.inflate(R.layout.view_food_fragment, container, false);
        Button goToEditBtn = (Button)v.findViewById(R.id.view_food_fragment_btn_go_to_edit);
        goToEditBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                callEditFoodFragment();
            }
        });
        //Calendar 추가
        CalendarFragment c = new CalendarFragment(); //Todo:기본생성자로 하지 말고 newInstance로 생성하자
        FragmentManager m = getChildFragmentManager();
        FragmentTransaction t = m.beginTransaction();
        t.add(R.id.view_food_fragment_calendar_container, c).commit();

        return v;
    }

    public void onResume() {

        //없어질때 fade out 하는 애니메이션
        getDialog().getWindow().setWindowAnimations(R.style.dialog_fade_out_animation);
        super.onResume();

    }

    //EditFoodFragment 창을 불러낸다.
    //나중에는 bundle에 현재 food의 information을 담아서 같이 보내줘서
    //EditFoodFragment에 현재 저장되어 있는 정보를 보여준다.
    private void callEditFoodFragment() {
        Dialog d = this.getDialog();
        FragmentManager m = getFragmentManager();
        d.dismiss();        //ViewFoodDialog는 없앤다.
        EditFoodFragment e = new EditFoodFragment();        //EditFoodFragment를 부른다.
        e.show(m,"");    //Tag는 일단 그냥 빈 상태로 둔다.
    }
}
