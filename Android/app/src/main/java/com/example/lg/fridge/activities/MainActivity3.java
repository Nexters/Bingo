package com.example.lg.fridge.activities;
/***
 * Custom Calendar를 위한 activity
 */

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.example.lg.fridge.R;
import com.example.lg.fridge.ViewFoodFragment;


public class MainActivity3 extends ActionBarActivity {
    private Button calButton;
    public static final String TAG_DIALOG = "dialog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        calButton = (Button)findViewById(R.id.main_btn_calendar);
    }

    public void onClicked(View v) {
        switch(v.getId()) {
            case R.id.main_btn_calendar:
                callViewFoodDialog();
                break;
        }
    }

    private void callViewFoodDialog() {
        FragmentManager fm = getSupportFragmentManager();
        ViewFoodFragment f = new ViewFoodFragment();
        f.show(fm, TAG_DIALOG);
    }
}
