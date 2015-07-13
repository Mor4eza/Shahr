package com.ariana.shahre_ma.MyBusiness;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.datetimepicker.date.DatePickerDialog;
import com.appyvet.rangebar.RangeBar;
import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ariana2 on 7/12/2015.
 */
public class Discount_Dialog extends Dialog {

    Context context;
    EditText tv_desc;
    EditText tv_date;
    EditText tv_title;
    Button btn_save;
    RangeBar rangeBar;
    public Discount_Dialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discount_dialog);

      /*  NumberPicker np = (NumberPicker) findViewById(R.id.np1);
        np.setMaxValue(100);
        np.setMinValue(0);*/

        setTitle("ثبت تخفیف جدید");
        SpinnerSetUp();
        InitViews();
        rangeBar.setRight(20);
        rangeBar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int i, int i1, String s, String s1) {
                Log.i("RangeBar", s1);
            }
        });


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


    }


    void InitViews(){
        tv_desc=(EditText)findViewById(R.id.et_desc);
        tv_date=(EditText)findViewById(R.id.tv_discount_date);
        btn_save=(Button)findViewById(R.id.discount_save);
        rangeBar=(RangeBar)findViewById(R.id.rangebar);
        tv_title=(EditText)findViewById(R.id.et_discount_title);

    }

    void SpinnerSetUp(){
        Spinner Sp_City = (Spinner) findViewById(R.id.sp_expire);
        Sp_City.setPrompt("انتخاب شهر:");
        List<String> list = new ArrayList<String>();
        list.add("1 هفته");
        list.add("10 روز");
        list.add("20 روز");
        list.add("1 ماه");
        list.add("2 ماه");
        list.add("3 ماه");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Sp_City.setAdapter(dataAdapter);

    }

}
