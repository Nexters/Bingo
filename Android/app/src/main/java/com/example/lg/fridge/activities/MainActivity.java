package com.example.lg.fridge.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lg.fridge.FridgeFragment;
import com.example.lg.fridge.R;
import com.example.lg.fridge.ViewFoodFragment;

public class MainActivity extends ActionBarActivity {

    private Toolbar actionBar;
    private Button tab01;
    private Button tab02;
    private Button tab03;
    private Button tab04;
    private final String FRIDGE_FRAGMENT_TAG = "fridge_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the action bar.
        actionBar = (Toolbar)findViewById(R.id.actionBar);
        setSupportActionBar(actionBar);

        //toolbar의 alarm image click을 테스트해보기위함
        ImageView actionbar_alarm = (ImageView)findViewById(R.id.actionbar_alarm);
        actionbar_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "alarm clicked", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), ExpiryListActivity.class);
                startActivity(i);
                return;
            }
        });

        //tab을 위장한 버튼 4개들
        tab01 = (Button)findViewById(R.id.main_tab01);
        tab02 = (Button)findViewById(R.id.main_tab02);
        tab03 = (Button)findViewById(R.id.main_tab03);
        tab04 = (Button)findViewById(R.id.main_tab04);
        tab01.setOnClickListener(mOnClickListener);
        tab02.setOnClickListener(mOnClickListener);
        tab03.setOnClickListener(mOnClickListener);
        tab04.setOnClickListener(mOnClickListener);
        tab01.performClick();   //맨 처음 시작될 때 tab 01이 기본으로 켜지도록


        //임시코드
        Button btn = (Button)findViewById(R.id.main_btn_view_as_image);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callViewFoodDialog();

            }
        });

        Button btn2 = (Button)findViewById(R.id.main_btn_view_as_list);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SpeechActivity.class);
                startActivity(i);
            }
        });
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if (v.isSelected()) return;     //만약 이미 선택되어있던 tab이면 아무 처리도 하지 않는다
            Log.d("TESTING", "clicked");
            setSelected(v);         //tab 4개 중 선택한 버튼을 선택된 채로 유지하기 위한 method
            switch(v.getId()) {
                case R.id.main_tab01:
                    callUpFridgeFragment(0, 2);
                    break;
                case R.id.main_tab02:
                    callUpFridgeFragment(1, 1);
                    break;
                case R.id.main_tab03:
                    callUpFridgeFragment(2, 4);
                    break;
                case R.id.main_tab04:
                    callUpFridgeFragment(3, 3);
                    break;
            }
        }
    };

    //더러운 코드
    private void setSelected(View v) {
        tab01.setSelected(false);
        tab02.setSelected(false);
        tab03.setSelected(false);
        tab04.setSelected(false);
        v.setSelected(true);
    }

    //pos에 따른 냉장고 fragment를 가져옴. --> 1 ~ 4 값
    private void callUpFridgeFragment(int pos, int rowNum) {
        /*FridgeFragment01 fr = FridgeFragment01.newInstance(pos);
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        Fragment existingFrag = getSupportFragmentManager().findFragmentByTag(FRIDGE_FRAGMENT_TAG);
        if (existingFrag != null) {     //만약 이미 떠있는 fridgefragment가있으면 replace를한다.
            t.replace(R.id.main_fragment_container, fr, FRIDGE_FRAGMENT_TAG);
            t.commit();
            return;
        }
        t.add(R.id.main_fragment_container, fr, FRIDGE_FRAGMENT_TAG);
        t.commit();*/
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        Fragment existingFrag = getSupportFragmentManager().findFragmentByTag(FRIDGE_FRAGMENT_TAG);
        if (existingFrag != null) {     //만약 이미 떠있는 fridgefragment가있으면 replace를한다.
            t.replace(R.id.main_fragment_container, FridgeFragment.newInstance(pos, rowNum), FRIDGE_FRAGMENT_TAG);
            t.commit();
            return;
        }
        t.add(R.id.main_fragment_container, FridgeFragment.newInstance(pos, rowNum), FRIDGE_FRAGMENT_TAG).commit();
    }

    private void callViewFoodDialog() {
        FragmentManager fm = getSupportFragmentManager();
        ViewFoodFragment f = new ViewFoodFragment();
        f.show(fm,"");
    }

}
