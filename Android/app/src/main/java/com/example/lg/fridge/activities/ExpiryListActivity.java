package com.example.lg.fridge.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.lg.fridge.ExpiryListAdapter;
import com.example.lg.fridge.R;

public class ExpiryListActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{
    private ExpiryListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expiry_list);
        mAdapter = new ExpiryListAdapter(this);

        //feed data into adapter
        /*TODO: DB에서 음식들을 유통기한순으로 리스트로 가져오는 method를 이용하여 리스트를 가져온다
        * TODO: 각 리스트 아이템을 adapter에 아이템으로 넣으면서 유통기한 체크
        * TODO: 유통기한이 바뀌는 시점에서 새로운 헤더를 추가, 그 후 계속 아이템 추가
        * */
        //testing code
        for (int i = 1; i < 30; i++) {
            mAdapter.addItem("Row Item #" + i);
            if (i % 4 == 0) {
                mAdapter.addSectionHeaderItem("Section #" + i);
            }
        }

        //listview에 adapter를 적용
        ListView view = (ListView)findViewById(R.id.expiry_list_listview);
        view.setAdapter(mAdapter);
        view.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //mAdapter.getItemViewType(position)을 이용해서 클릭된 아이템이 item인지 header인지를 구분
    }
}
