package com.ariana.shahre_ma;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.DeleteDataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldDataBusiness;
import com.ariana.shahre_ma.Search.SearchOfline;

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
    FieldDataBusiness fdb=new FieldDataBusiness();
    DeleteDataBaseSqlite dda=new DeleteDataBaseSqlite(getActivity());
    Integer subsetid=0;
    Integer Cityid=0;
    Integer fieldactivity=0;

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
                    txtAddress.setText("");
                    txtField.setText("");
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


            dda.delete_Search();
            Query query = new Query(getActivity());
            fieldactivity=query.getFieldActivityId(txtField.getText().toString());
            Cityid = query.getCityId(txtWhere.getText().toString());
            subsetid = query.getAdvancesubsetID(txtField.getText().toString());
            fdb.ClearAll();
            Log.i("fieldactivity", String.valueOf(fieldactivity));
            Log.i("subsetid", String.valueOf(subsetid));
            Log.i("Cityid", String.valueOf(Cityid));

            if (txtField.getText().equals(null) || txtField.getText().equals("") || txtField.getText().length()==0)
            {
                if(txtWhat.getText().length()==0 || txtWhat.getText().equals(" ") || txtWhat.getText().equals("")) {
                    txtWhat.setError("یه چیز بنویس  ، مثلا نام فروشگاه یا مشاغل");
                    txtWhat.requestFocus();
                }
                else
                {
                    if(txtWhat.getText().toString().trim().length()<=2)
                    {
                        txtWhat.setError("تعداد حروف باید حداقل سه حرف باشه");
                        txtWhat.requestFocus();
                    }
                    else
                    {
                        SearchOfline searchOfline = new SearchOfline(getActivity());
                        searchOfline.TextSearch(txtWhere.getText().toString(), txtWhat.getText().toString());
                    }
                }
            }
            else
            {
                if(subsetid>0 || fieldactivity>0)
                {
                    if(Cityid<=0)
                    {
                        MessageDialog messageDialog=new MessageDialog(getActivity());
                        messageDialog.ShowMessage("هشدار","شهر مورد نظر یافت نشد","باشه","false");
                    }
                    else
                    {
                        if(txtAddress.getText().length()==0 || txtAddress.getText().equals(null) || txtAddress.getText().equals(""))
                        {
                            if(subsetid>0) {
                                Log.i("address", "spaceSubset");
                                SearchOfline searchOfline = new SearchOfline(getActivity());
                                searchOfline.TextAdvancedSearch(Cityid, txtWhat.getText().toString(), " ", txtField.getText().toString());
                            }
                            else
                            {
                                Log.i("address", "spaceField");
                                SearchOfline searchOfline = new SearchOfline(getActivity());
                                searchOfline.TextAdvancedSearch2(Cityid, txtWhat.getText().toString(), " ", txtField.getText().toString());
                            }
                        }
                        else if(subsetid>0)
                        {
                            SearchOfline searchOfline = new SearchOfline(getActivity());
                            searchOfline.TextAdvancedSearch(Cityid, txtWhat.getText().toString(), txtAddress.getText().toString(), txtField.getText().toString());
                        }
                        else if(fieldactivity>0)
                        {
                            SearchOfline searchOfline = new SearchOfline(getActivity());
                            searchOfline.TextAdvancedSearch2(Cityid, txtWhat.getText().toString(), txtAddress.getText().toString(), txtField.getText().toString());
                        }
                        else
                        {
                            SearchOfline searchOfline = new SearchOfline(getActivity());
                            searchOfline.TextAdvancedSearch(Cityid, txtWhat.getText().toString(), txtAddress.getText().toString(), txtField.getText().toString());
                        }

                    }
                }
                else
                {
                    MessageDialog messageDialog=new MessageDialog(getActivity());
                    messageDialog.ShowMessage("هشدار","زیر مجموعه مورد نظر یافت نشد","باشه","false");
                }

            }
            }

    });


    }catch (Exception e){}
        return view;
    }

        public List<String> getId2() {

            DataBaseSqlite db=new DataBaseSqlite(getActivity());
            List<String> studentList = new ArrayList<String>();
            Cursor allrows=db.select_AllCity();
            if (allrows.moveToFirst()) {
                do
                {
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

            //Subset
            Cursor allrows=db.select_Subset();
            if (allrows.moveToFirst()) {
                do
                {
                    studentList.add(allrows.getString(1));
                } while (allrows.moveToNext());
            }

            //FieldActivity
            Cursor allrowsFieldActivity=db.select_FieldActivity();
            if (allrowsFieldActivity.moveToFirst()) {
                do
                {
                    studentList.add(allrowsFieldActivity.getString(1));
                } while (allrowsFieldActivity.moveToNext());
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
