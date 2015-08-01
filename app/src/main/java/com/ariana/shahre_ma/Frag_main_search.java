package com.ariana.shahre_ma;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ariana.shahre_ma.WebServiceGet.HTTPGetOnlineSearchJson;

/**
 * Created by ariana2 on 7/16/2015.
 */
public class Frag_main_search extends Fragment {
    private String title;
    private int page;
    private Button btnSearch;
    private TextView txtWhat;
    private TextView txtWhere;

    // newInstance constructor for creating fragment with arguments
    public static Frag_main_search newInstance(int page, String title) {
        Frag_main_search fragmentFirst = new Frag_main_search();
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
        View view = inflater.inflate(R.layout.frag_main_search, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.tvLabel);
        tvLabel.setText(page + " -- " + title);
        btnSearch=(Button)view.findViewById(R.id.btn_search);
        txtWhat=(EditText)view.findViewById(R.id.et_search_what);
        txtWhere=(EditText)view.findViewById(R.id.et_search_where);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HTTPGetOnlineSearchJson httpGetOnlineSearchJson=new HTTPGetOnlineSearchJson(getActivity());
                httpGetOnlineSearchJson.SetValueSearch(txtWhat.getText().toString(),68);
                httpGetOnlineSearchJson.execute();
            }
        });

        return view;
    }
}
