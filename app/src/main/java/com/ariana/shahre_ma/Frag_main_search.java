package com.ariana.shahre_ma;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.Fields.FieldDataBusiness;
import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.Search.SearchOfline;
import com.ariana.shahre_ma.Settings.KeySettings;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetOnlineSearchJson;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ariana2 on 7/16/2015.
 */
public class Frag_main_search extends Fragment {

    private String title;
    private int page;
    private Button btnSearch;
    private EditText txtWhat;
    private AutoCompleteTextView txtWhere;
    private AutoCompleteTextView txtField;
    private EditText txtAddress;
    private RelativeLayout advance;
    private Button  btnAdvance;
    private boolean visable=false;
    private FrameLayout frame;


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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_main_search, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.tvLabel);
        tvLabel.setText(page + " -- " + title);



    try {
        btnSearch = (Button) view.findViewById(R.id.btn_search);
        txtWhat = (EditText) view.findViewById(R.id.et_search_what);
        txtWhere = (AutoCompleteTextView) view.findViewById(R.id.et_search_where);
        txtField= (AutoCompleteTextView) view.findViewById(R.id.et_search_field);
        txtAddress =(EditText) view.findViewById(R.id.et_search_address);
        advance = (RelativeLayout) view.findViewById(R.id.relative_advance);
        btnAdvance=(Button) view.findViewById(R.id.btn_advanced);
        frame=(FrameLayout)view.findViewById(R.id.frameLayout);

        btnAdvance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!visable) {
                    MainActivity.top.closeTopView(true);
                    advance.setVisibility(View.VISIBLE);
                    advance.setAlpha(0.0f);
                    frame.setAlpha(0.0f);
                    advance.animate()
                            .translationY(0)
                            .setDuration(300)
                            .alpha(1.0f)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    advance.setVisibility(View.VISIBLE);
                                    frame.animate().alpha(1.0f).setDuration(10);
                                    btnAdvance.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(android.R.drawable.arrow_up_float), null, null, null);
                                }
                            });
                    visable=true;
                }else {
                    frame.setAlpha(0.0f);
                    advance.animate()
                            .translationY(0)
                            .alpha(0.0f)
                            .setDuration(300)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    advance.setVisibility(View.GONE);
                                    frame.animate().alpha(1.0f).setDuration(10);
                                    btnAdvance.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(android.R.drawable.arrow_down_float),null,null,null);
                                }
                            });
                    visable=false;
                }

            }
        });




        txtWhat.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    boolean isValidKey = event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER;
                    boolean isValidAction = actionId == EditorInfo.IME_ACTION_DONE;
                    if (isValidKey || isValidAction) {
                        btnSearch.performClick();
                    }
                return false;
        }
    });

        GetNameActivity();
        GetNameSubset();

        //Start search
    btnSearch.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Query query=new Query(getActivity());
            SearchOfline searchOfline=new SearchOfline(getActivity());
            //searchOfline.TextSearch(txtWhere.getText().toString(),txtWhat.getText().toString());
            searchOfline.TextSearch(query.getsubsetID(txtField.getText().toString()),txtWhat.getText().toString(),txtAddress.getText().toString(),query.getAreaID(txtWhere.getText().toString()));

        }
    });


    }catch (Exception e){}
        return view;
    }

        public List<String> getId2() {

            DataBaseSqlite db=new DataBaseSqlite(getActivity());
            List<String> studentList = new ArrayList<String>();
            Cursor allrows=db.select_AllArea();
            if (allrows.moveToFirst()) {
                do {

                    studentList.add(allrows.getString(1));
                    Log.i("FieldActivity", String.valueOf(allrows.getInt(0)) + " : " + allrows.getString(1));


                } while (allrows.moveToNext());
            }

            return studentList;
        }

        public void GetNameActivity()
        {
            try {

                ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, getId2());
                txtWhere.setAdapter(adapter);
                txtWhere.setThreshold(1);
              //  txtWhere.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
            }
            catch (Exception e)
            {
                Log.e("ExceptionSQL",e.toString());
            }
        }

        public List<String> getId3() {
            DataBaseSqlite db=new DataBaseSqlite(getActivity());
            List<String> studentList = new ArrayList<String>();
            Cursor allrows=db.select_Subset();
            if (allrows.moveToFirst()) {
                do {

                    studentList.add(allrows.getString(1));

                } while (allrows.moveToNext());
            }

            return studentList;
        }

        public void GetNameSubset()
        {
            try {

                ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, getId3());
                txtField.setAdapter(adapter);
                txtField.setThreshold(1);
                //  txtWhere.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
            }
            catch (Exception e)
            {
                Log.e("ExceptionSQL",e.toString());
            }
        }

}
