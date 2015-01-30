package com.example.lg.fridge;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class FridgeFragment extends Fragment {
    private static final String FRAGMENT_NUMBER = "fragment_number";
    private static final String NUM_OF_ROWS = "num_of_rows";

    private int fragmentNumber;
    private int numOfRows;

    private int rowHeight;
    private FridgeRow.Style style;


    //fragmentNumber는 1~4, numOfRows는 한 냉장고칸에있는 row의 갯수
    public static FridgeFragment newInstance(int fragmentNumber, int numOfRows, FridgeRow.Style style) {
        FridgeFragment fragment = new FridgeFragment();
        Bundle args = new Bundle();
        args.putInt(FRAGMENT_NUMBER, fragmentNumber);
        args.putInt(NUM_OF_ROWS, numOfRows);
        fragment.setArguments(args);
        fragment.style = style;
        return fragment;
    }

    public FridgeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fragmentNumber = getArguments().getInt(FRAGMENT_NUMBER);
            numOfRows = getArguments().getInt(NUM_OF_ROWS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        switch(numOfRows) {
            case 1:
                rowHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float)350, getResources().getDisplayMetrics());
                break;
            case 2:
                rowHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float)250, getResources().getDisplayMetrics());
                break;
            default:
                rowHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float)150, getResources().getDisplayMetrics());
                break;

        }
        View main = inflater.inflate(R.layout.fragment_fridge, container, false);
        //이제 이 프래그먼트 안에 numOfRows 수 만큼 child fragment를 넣는다.
        createFridgeRows();
        return main;
    }

    private void createFridgeRows() {
        for (int i = 0; i < numOfRows; i++) {
            getChildFragmentManager().beginTransaction().add(R.id.fridge_fragment_container_for_rows, FridgeRow.newInstance(rowHeight, style)).commit();
            getChildFragmentManager().executePendingTransactions();
        }
    }

    public void changeRowStyle(FridgeRow.Style style) {
        this.style = style;
        Toast.makeText(getActivity(), "changing to style: " + style, Toast.LENGTH_SHORT).show();
        createFridgeRows();
    }

}
