package com.example.lg.fridge;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class FridgeRow extends Fragment {
    private static final String ARG_ROWHEIGHT = "rowheight";

    private int rowHeight;

    public static FridgeRow newInstance(int rowHeight) {
        FridgeRow fragment = new FridgeRow();
        Bundle args = new Bundle();
        args.putInt(ARG_ROWHEIGHT, rowHeight);
        fragment.setArguments(args);
        return fragment;
    }

    public FridgeRow() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            rowHeight = getArguments().getInt(ARG_ROWHEIGHT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fridge_row, container, false);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, rowHeight);
        v.setLayoutParams(lp);
        return v;
    }
}
