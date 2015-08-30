package com.ariana.shahre_ma.MyBusiness;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.appyvet.rangebar.RangeBar;
import com.ariana.shahre_ma.Date.DateTime;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServiceGet.SqliteTOjson;
import com.ariana.shahre_ma.WebServicePost.HTTPPostDisCount;
import com.ariana.shahre_ma.WebServicePost.HTTPPostDisCountEdit;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ariana2 on 7/12/2015.
 */
public class Discount_Dialog extends Dialog implements   DatePickerDialog.OnDateSetListener {

    Context context;
    DateTime dt=new DateTime();
    EditText tv_desc;
    Button tv_date;
    EditText tv_title;
    Button btn_save;
    RangeBar rangeBar;
    FieldClass fc=new FieldClass();
    String percent="";
    Boolean SaveEdit=false;
    Spinner Sp_City;
    Integer countday=7;
    FragmentManager fragmentManager;
    public Discount_Dialog(Context context) {
        super(context);
        this.context=context;
        fragmentManager = ((FragmentActivity) context).getFragmentManager();

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
        ShowEditDisCount();
        rangeBar.setRight(20);
        rangeBar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int i, int i1, String s, String s1) {
                Log.i("RangeBar", s1);
                percent = s1;
            }
        });


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

                SqliteTOjson sqliteTOjson = new SqliteTOjson(context);
                String json;
                if (SaveEdit == true) {
                    Log.i("Edit", "true");
                    fc.SetId_DisCount(fc.GetId_DisCount());
                    fc.SetText_DisCount(tv_title.getText().toString());
                    fc.SetImage_DisCount("");
                    fc.SetStartDate_DisCount(tv_date.getText().toString());
                    fc.SetExpirationDate_DisCount(dt.Add(countday));
                    fc.SetDescription_DisCount(tv_desc.getText().toString());
                    fc.SetPercent_DisCount(percent);
                    fc.SetBusinessId_DisCount(fc.GetBusiness_Id());

                    if (fc.GetText_DisCount().equals("")) {
                        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                        alertDialog.setTitle("هشدار");
                        alertDialog.setMessage("متن تخفیف را وارد کنید");
                        alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                tv_title.requestFocus();

                            }
                        });


                        alertDialog.show();
                    } else if (fc.GetDescription_DisCount().equals("")) {
                        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                        alertDialog.setTitle("هشدار");
                        alertDialog.setMessage("توضیحات برای تخیفیف را وارد کنید");
                        alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                tv_title.requestFocus();

                            }
                        });


                        alertDialog.show();

                    } else if (fc.GetPercent_DisCount().length() == 0) {
                        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                        alertDialog.setTitle("هشدار");
                        alertDialog.setMessage("درصد تخفیف را معیین کنید");
                        alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                tv_title.requestFocus();

                            }
                        });


                        alertDialog.show();

                    } else {
                        json = sqliteTOjson.getDisCountTOjson(fc.GetId_DisCount(), fc.GetText_DisCount(), fc.GetImage_DisCount(), fc.GetStartDate_DisCount(), fc.GetExpirationDate_DisCount(), fc.GetDescription_DisCount(), fc.GetPercent_DisCount(), fc.GetBusinessId_DisCount());

                        HTTPPostDisCountEdit httpPostDisCount = new HTTPPostDisCountEdit(getContext());
                        httpPostDisCount.SetDisCountJson(json);
                        httpPostDisCount.execute();
                    }


                }

                {
                    Log.i("Save", "false");
                    fc.SetId_DisCount(1);
                    fc.SetText_DisCount(tv_title.getText().toString());
                    fc.SetImage_DisCount("");
                    fc.SetStartDate_DisCount(dt.Now());
                    fc.SetExpirationDate_DisCount(dt.Add(countday));
                    fc.SetDescription_DisCount(tv_desc.getText().toString());
                    fc.SetPercent_DisCount(percent);
                    fc.SetBusinessId_DisCount(fc.GetBusiness_Id());

                    if (fc.GetText_DisCount().equals("")) {
                        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                        alertDialog.setTitle("هشدار");
                        alertDialog.setMessage("متن تخفیف را وارد کنید");
                        alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                tv_title.requestFocus();

                            }
                        });


                        alertDialog.show();
                    } else if (fc.GetDescription_DisCount().equals("")) {
                        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                        alertDialog.setTitle("هشدار");
                        alertDialog.setMessage("توضیحات برای تخیفیف را وارد کنید");
                        alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                tv_title.requestFocus();

                            }
                        });


                        alertDialog.show();

                    } else if (fc.GetPercent_DisCount().length() == 0) {
                        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                        alertDialog.setTitle("هشدار");
                        alertDialog.setMessage("درصد تخفیف را معیین کنید");
                        alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                tv_title.requestFocus();

                            }
                        });


                        alertDialog.show();

                    } else {
                        json = sqliteTOjson.getDisCountTOjson(fc.GetId_DisCount(), fc.GetText_DisCount(), fc.GetImage_DisCount(), fc.GetStartDate_DisCount(), fc.GetExpirationDate_DisCount(), fc.GetDescription_DisCount(), fc.GetPercent_DisCount(), fc.GetBusinessId_DisCount());

                        HTTPPostDisCount httpPostDisCount = new HTTPPostDisCount(getContext());
                        httpPostDisCount.SetDisCountJson(json);
                        httpPostDisCount.execute();
                    }
                }
            }
        });

        Sp_City.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                switch (pos) {
                    case 0:

                        countday = 7;
                        break;
                    case 1:

                        countday = 10;
                        break;
                    case 2:

                        countday = 20;
                        break;
                    case 3:

                        countday = 30;
                        break;
                    case 4:
                        countday = 60;
                        break;
                    case 5:
                        countday = 90;
                        break;

                }
            }


            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });


        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersianCalendar now = new PersianCalendar();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        Discount_Dialog.this,
                        now.getPersianYear(),
                        now.getPersianMonth(),
                        now.getPersianDay()
                );

                dpd.show(fragmentManager,"date");
                
            }
        });

    }


    void InitViews(){
        tv_desc=(EditText)findViewById(R.id.et_desc);
        tv_date=(Button)findViewById(R.id.btn_discount_date);
        btn_save=(Button)findViewById(R.id.discount_save);
        rangeBar=(RangeBar)findViewById(R.id.rangebar);
        tv_title=(EditText)findViewById(R.id.et_discount_title);

    }

    void SpinnerSetUp(){
         Sp_City = (Spinner) findViewById(R.id.sp_expire);
        Sp_City.setPrompt("مدت تخفیف:");
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

    private  void ShowEditDisCount()
    {
       /* try {
            DataBaseSqlite db = new DataBaseSqlite(getContext());
            Log.i("Id-DisCount", String.valueOf(fc.GetId_DisCount()));
            Cursor rows = db.select_AllDisCountMember(fc.GetId_DisCount());
            rows.moveToFirst();
            Log.i("Exception", rows.getString(1));
            Log.i("Exception", rows.getString(5));
            Log.i("Exception", rows.getString(3));
            tv_title.setText(rows.getString(1));
            tv_desc.setText(rows.getString(5));
            tv_date.setText(rows.getString(3));
            rangeBar.setRight(Integer.parseInt(rows.getString(6)));
            if(fc.GetId_DisCount()>0)
                SaveEdit=true;
        }catch (Exception e){
            Log.i("Exception",e.toString());
        }*/

    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        if (month <= 9)
        {
            month=Integer.valueOf("0"+(String.valueOf(month)));
        }
        if (day<=9)
        {
            day=Integer.valueOf("0"+(String.valueOf(day)));
        }
        tv_date.setText(String.valueOf(year + "/" + month + "/" + day));
    }
}
