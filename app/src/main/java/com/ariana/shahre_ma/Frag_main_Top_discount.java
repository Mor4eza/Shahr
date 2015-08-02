package com.ariana.shahre_ma;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ariana.shahre_ma.Cards.TopDiscount_Card_Adapter;

/**
 * Created by ariana2 on 5/23/2015.
 */
public class Frag_main_Top_discount extends Fragment {
    // Store instance variables
    private String title;
    private int page;

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    // newInstance constructor for creating fragment with arguments
    public static Frag_main_Top_discount newInstance(int page, String title) {
        Frag_main_Top_discount fragmentFirst = new Frag_main_Top_discount();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_main_top_discount, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.tvLabel);
        tvLabel.setText(page + " -- " + title);


        mRecyclerView = (RecyclerView) view.findViewById(R.id.top_dis_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new TopDiscount_Card_Adapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}