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
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServiceGet.SqliteTOjson;
import com.ariana.shahre_ma.WebServicePost.HTTPPostDisCount;

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
    FieldClass fc=new FieldClass();
    String percent="";
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
                percent=s1;
            }
        });


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

                SqliteTOjson sqliteTOjson=new SqliteTOjson(context);

                fc.SetId_DisCount(1);
                fc.SetText_DisCount(tv_title.getText().toString());
                fc.SetImage_DisCount("");
                fc.SetStartDate_DisCount(tv_date.getText().toString());
                fc.SetExpirationDate_DisCount("7/5/2015");
                fc.SetDescription_DisCount(tv_desc.getText().toString());
                fc.SetPercent_DisCount(percent);
                fc.SetBusinessId_DisCount(fc.GetBusiness_Id());

                String json;
                json=sqliteTOjson.getDisCountTOjson(fc.GetId_DisCount(),fc.GetText_DisCount(),fc.GetImage_DisCount(),fc.GetStartDate_DisCount(),fc.GetExpirationDate_DisCount(),fc.GetDescription_DisCount(),fc.GetPercent_DisCount(),fc.GetBusinessId_DisCount());

                HTTPPostDisCount httpPostDisCount=new HTTPPostDisCount(getContext());
                httpPostDisCount.SetDisCountJson(json);
                httpPostDisCount.execute();
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
