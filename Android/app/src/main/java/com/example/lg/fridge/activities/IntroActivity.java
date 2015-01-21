package com.example.lg.fridge.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

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

    public static class MySharedPrefs {
        //SharedPref를 사용하기 위한 static class
        //앱 시작할 때 처음 생성되어 static이기때문에 앱이 꺼질 때 까지 메모리에 남아있을 것이다.
        //즉, IntroAcitivty가 finish()되어도 앱이 계속 켜져있는 한 다른 액티비티에서도 이 클래스에 접근할 수 있다?

        //아이디와 비번에 대한 key, value 값은 동일하다
        //예컨대, 아이디가 giantsol이고 비번이 12345 인걸로 회원가입을 했으면
        //SharedPreferences에도 <key,value>라면 <giantsol, giantsol>, <'12345', '12345'> 식으로 저장되어있다.

        //TODO: 그냥 따로 외부 클래스로 만들어 쓰는게 나을듯
        //TODO: 또, last_exit이라는 key를 만들어서 마지막에 사용된 id를 저장한다. 그걸로 다음에 켰을 때 바로 로그인하도록.
        //TODO: SharedPreferences의 값을 <giantsol, giantsol>식이 아니라 <giantsol, '12345'> 식으로 바꿔야겠다
        //왜냐하면 id를 key값으로 불러들일 일은 있지만 password를 key값으로 불러들일 일은 없을거같기때문..

        private static Context c;      //SharedPreferences를 사용하려면 context가 필요하다
        private static SharedPreferences pref;
        private static final String FILE_NAME = "personalId";

        private MySharedPrefs(Context c) {
            this.c = c;
            pref = c.getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        }

        public static String getId(String _id) {
            //저장된 ID가 없을 시 길이가 0인 string을 반환하도록 함.
            //즉, 이 말은 초기에 ID를 정할 때 길이가 0이면 안되게 만들어야한다는 뜻.
            return pref.getString(_id, "");
        }

        public static String getPassword(String _pw) {
            //패스워드도 마찬가지로 초기 회원가입 시 길이가 0이면 안되게 막아둬야함
            return pref.getString(_pw, "");
        }

        public static void setId(String _id) {
            //parameter로 넘겨받은 string으로 id를 저장한다.
            SharedPreferences.Editor e = pref.edit();
            e.putString(_id, _id);
            e.commit();
        }

        public static void setPassword(String _pw) {
            //parameter로 넘겨받은 string으로 password를 저장한다.
            SharedPreferences.Editor e = pref.edit();
            e.putString(_pw, _pw);
            e.commit();
        }

        public static void removeId() {
            //만약 필요하게 되면 ID와 그 id에 맞는 password 둘 다 지운다.
            //아직은 구현안함
        }
    }

}
