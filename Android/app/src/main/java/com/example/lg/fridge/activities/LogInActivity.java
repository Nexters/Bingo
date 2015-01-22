package com.example.lg.fridge.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lg.fridge.MySharedPrefs;
import com.example.lg.fridge.R;

public class LogInActivity extends ActionBarActivity {
    private EditText idText;
    private EditText passText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        idText = (EditText)findViewById(R.id.log_in_id);
        passText = (EditText)findViewById(R.id.log_in_password);

        Button gotoMainBtn = (Button)findViewById(R.id.log_in_btn_to_main);
        gotoMainBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //id와 password 창에 입력한 회원정보가 있는지 확인
                //존재하면 그 아이디를 통해 mainactivity로 이동
                if(checkForId()) {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "ID OR PASSWORD IS NOT CORRECT", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private boolean checkForId() {
        //id나 password의 length가 0일 수는 없다.
        //if (isIdOrPasswordOfZeroLength()) {
        //    return false;
        //}                 /*지금은 주석처리. 아직 회원가입 기능이 구현 안되어있기 때문*/
        MySharedPrefs sp = new MySharedPrefs(getApplicationContext());
        String _id = idText.getText().toString();
        String _pass = passText.getText().toString();
        if(_pass.equals(sp.getId(_id))) //만약 아이디와 일치하는 패스워드가 있으면 true
            return true;
        return false;
    }

    private boolean isIdOrPasswordOfZeroLength() {
        return (idText.getText().toString().length() == 0) || (passText.getText().toString().length() == 0);
    }

}
