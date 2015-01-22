package com.example.lg.fridge.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.example.lg.fridge.MySharedPrefs;
import com.example.lg.fridge.R;

public class IntroActivity extends ActionBarActivity {
    private boolean isLoggedIn = false;
    private Intent i;             //계정 유무를 통해 어느 페이지로 이동할지 정할 intent
    private Thread background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        MySharedPrefs sp = new MySharedPrefs(this);     //SharedPreferences를 사용하기 위해 context를 넘겨준다.

        background = new Thread() {
          public void run() {
              try {
                  //Thread will sleep for 3 seconds
                  sleep(2*1000);

                  startActivity(i);
                  finish();
                  //왠일인지 overridePendingTransition이 작동하지않는다
                  //overridePendingTransition(R.anim.dialog_fade_in, R.anim.dialog_fade_out);

              } catch (Exception e) {

              }
          }
        };

        checkForId();


        //background.start();
    }

    private void checkForId() {
        //이미 로그인된 아이디가 존재하는지 확인하는 method
        if (isLoggedIn) {
            loggedIn();
        } else {
            needLoggingIn();
        }
    }

    private void loggedIn() {
        //기존 아이디를 통해 메인페이지로 이동
        this.i = new Intent(getApplicationContext(), MainActivity.class);
        background.start();
    }

    private void needLoggingIn() {
        //로그인 및 회원가입 표시가 있는 페이지로 이동
        this.i = new Intent(getApplicationContext(), LogInActivity.class);
        background.start();
    }



}
