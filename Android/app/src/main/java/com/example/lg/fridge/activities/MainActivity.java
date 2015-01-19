package com.example.lg.fridge.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.lg.fridge.R;

import com.example.lg.fridge.ViewFoodFragment;

import java.util.Locale;

public class MainActivity extends ActionBarActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private Toolbar actionBar;
    private Button tab01;
    private Button tab02;
    private Button tab03;
    private Button tab04;
    private final String FRIDGE_FRAGMENT_TAG = "fridge_fragment";


    /**
     * The {@link ViewPager} that will host the section contents.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the action bar.
        actionBar = (Toolbar)findViewById(R.id.actionBar);
        setSupportActionBar(actionBar);
        //getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

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
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if (v.isSelected()) return;     //만약 이미 선택되어있던 tab이면 아무 처리도 하지 않는다
            Log.d("TESTING", "clicked");
            setSelected(v);         //tab 4개 중 선택한 버튼을 선택된 채로 유지하기 위한 method
            switch(v.getId()) {
                case R.id.main_tab01:
                    callUpFridgeFragment(0);
                    break;
                case R.id.main_tab02:
                    callUpFridgeFragment(1);
                    break;
                case R.id.main_tab03:
                    callUpFridgeFragment(2);
                    break;
                case R.id.main_tab04:
                    callUpFridgeFragment(3);
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

    //pos에 따른 냉장고 fragment를 가져옴. --> 0 ~ 3 값
    private void callUpFridgeFragment(int pos) {
        PlaceholderFragment pf = PlaceholderFragment.newInstance(pos);
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        Fragment f = getSupportFragmentManager().findFragmentByTag(FRIDGE_FRAGMENT_TAG);
        if (f != null) {
            //만약 이미 fridge fragment가 하나 떠 있다면 replace를한다.
            t.replace(R.id.main_fragment_container, pf, FRIDGE_FRAGMENT_TAG);
            t.commit();
            return;
        }
        t.add(R.id.main_fragment_container, pf, FRIDGE_FRAGMENT_TAG);
        t.commit();
        return;

    }

    private void callViewFoodDialog() {
        FragmentManager fm = getSupportFragmentManager();
        ViewFoodFragment f = new ViewFoodFragment();
        f.show(fm,"");
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_activity2, container, false);
            //임시적
            Bundle b = getArguments();
            TextView t = (TextView)rootView.findViewById(R.id.section_label);
            String text = String.valueOf(b.getInt(ARG_SECTION_NUMBER));
            t.setText(text);


            return rootView;
        }
    }

}
