package com.example.lg.fridge;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by LG on 2015-01-22.
 */

public class MySharedPrefs {

    //왜냐하면 id를 key값으로 불러들일 일은 있지만 password를 key값으로 불러들일 일은 없을거같기때문..

    private final Context c;      //SharedPreferences를 사용하려면 context가 필요하다
    private final SharedPreferences pref;
    private static final String FILE_NAME = "personalId";
    private static final String LAST_LOGGED_IN = "last_logged_in";

    public MySharedPrefs(Context c) {
        this.c = c;
        pref = c.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    //length가 0인 string을 반환할 시에는 로그인을 해야 함을 뜻한다.
    public String getLastLoggedInId() {
        return pref.getString(LAST_LOGGED_IN, "");
    }

    public String getId(String _id) {
        //저장된 ID가 없을 시 길이가 0인 string을 반환하도록 함.
        return pref.getString(_id, "");
    }

    public void removeId() {
        //만약 필요하게 되면 ID와 그 id에 맞는 password 둘 다 지운다.
        //아직은 구현안함
    }

    public void registerNewId(String id, String pass) {
        SharedPreferences.Editor e = pref.edit();
        e.putString(id, pass);
        e.commit();
    }

    //ID를 LAST_EXIT을 key로하여 저장해둔다.
    //이 메소드는 어떠한 id로 로그인할때마다 불려야 한다.
    public void addToLastLoggedInId(String id) {
        SharedPreferences.Editor e = pref.edit();
        e.putString(LAST_LOGGED_IN, id);
        e.commit();
    }
}
