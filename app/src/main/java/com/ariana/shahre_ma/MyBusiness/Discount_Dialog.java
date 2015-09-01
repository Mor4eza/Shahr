package com.ariana.shahre_ma.MyBusiness;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.appyvet.rangebar.RangeBar;
import com.ariana.shahre_ma.Date.CalendarTool;
import com.ariana.shahre_ma.Date.DateTime;
import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServiceGet.SqliteTOjson;
import com.ariana.shahre_ma.WebServicePost.HTTPPostDisCount;
import com.ariana.shahre_ma.WebServicePost.HTTPPostDisCountEdit;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

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
    Button Expire;
    String ExpireDate="";
    Integer countday=7;
    int ViewId;
    FragmentManager fragmentManager;
    CalendarTool ct=new CalendarTool();
    CalendarTool ct1=new CalendarTool();

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
                    ct.setIranianDate(Integer.valueOf(tv_date.getText().toString().substring(0, 4)), Integer.valueOf(tv_date.getText().toString().substring(5, 7)), Integer.valueOf(tv_date.getText().toString().substring(8, 10)));
                    Log.i("startDateEdit", ct.getGregorianDate());
                    fc.SetStartDate_DisCount(ct.getGregorianDate());
                    ct1.setIranianDate(Integer.valueOf(ExpireDate.toString().substring(0, 4)), Integer.valueOf(ExpireDate.toString().substring(5, 7)), Integer.valueOf(ExpireDate.toString().substring(8, 10)));
                    Log.i("ExpireDateEdit", ct1.getGregorianDate());
                    fc.SetExpirationDate_DisCount(ct1.getGregorianDate());
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

                    }
                    else
                    {

                        json = sqliteTOjson.getDisCountTOjson(fc.GetId_DisCount(), fc.GetText_DisCount(), fc.GetImage_DisCount(), fc.GetStartDate_DisCount(), fc.GetExpirationDate_DisCount(), fc.GetDescription_DisCount(), fc.GetPercent_DisCount(), fc.GetBusinessId_DisCount());

                        HTTPPostDisCountEdit httpPostDisCount = new HTTPPostDisCountEdit(getContext());
                        httpPostDisCount.SetDisCountJson(json);
                        httpPostDisCount.execute();

                    }


                }
                else
                {
                    Log.i("Save", "false");
                    fc.SetId_DisCount(1);
                    fc.SetText_DisCount(tv_title.getText().toString());
                    fc.SetImage_DisCount("");
                    ct.setIranianDate(Integer.valueOf(tv_date.getText().toString().substring(0, 4)), Integer.valueOf(tv_date.getText().toString().substring(5, 7)), Integer.valueOf(tv_date.getText().toString().substring(8, 10)));
                    Log.i("startDate", ct.getGregorianDate());
                    fc.SetStartDate_DisCount(ct.getGregorianDate());
                    ct1.setIranianDate(Integer.valueOf(ExpireDate.toString().substring(0, 4)), Integer.valueOf(ExpireDate.toString().substring(5, 7)), Integer.valueOf(ExpireDate.toString().substring(8, 10)));
                    Log.i("ExpireDate", ct1.getGregorianDate());
                    fc.SetExpirationDate_DisCount(ct1.getGregorianDate());
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
                ViewId=tv_date.getId();
                dpd.show(fragmentManager,"StartDate");
                
            }
        });


        Expire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersianCalendar now = new PersianCalendar();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        Discount_Dialog.this,
                        now.getPersianYear(),
                        now.getPersianMonth(),
                        now.getPersianDay()
                );
                ViewId=Expire.getId();
                dpd.show(fragmentManager,"EndDate");

            }
        });
    }


    void InitViews(){
        tv_desc=(EditText)findViewById(R.id.et_desc);
        tv_date=(Button)findViewById(R.id.btn_discount_date);
        btn_save=(Button)findViewById(R.id.discount_save);
        rangeBar=(RangeBar)findViewById(R.id.rangebar);
        tv_title=(EditText)findViewById(R.id.et_discount_title);
        Expire=(Button)findViewById(R.id.btn_discount_expire);

    }

    private  void ShowEditDisCount()
    {
        try {

            tv_title.setText("");
            tv_desc.setText("");
            tv_date.setText("");

            DataBaseSqlite db = new DataBaseSqlite(getContext());
            Log.i("Id-DisCount", String.valueOf(fc.GetId_DisCount()));
            Cursor rows = db.select_AllDisCountMember(fc.GetId_DisCount());
            rows.moveToFirst();
            Log.i("Exception", rows.getString(1));
            Log.i("Exception", rows.getString(5));
            Log.i("Exception", rows.getString(3));
            tv_title.setText(rows.getString(1));
            tv_desc.setText(rows.getString(5));
            tv_date.setText((rows.getString(3).substring(0, 4)) + "/" + (rows.getString(3).substring(5, 7)) + "/" + (rows.getString(3).substring(8, 10)));
            Expire.setText((rows.getString(4).substring(0, 4)) + "/" + (rows.getString(4).substring(5, 7)) + "/" + (rows.getString(4).substring(8, 10)));
            Log.i("rangebar", (rows.getString(6)));
            rangeBar.setSeekPinByValue(Float.valueOf(rows.getString(6)));



            if(fc.GetId_DisCount()>0) {
                SaveEdit = true;
            }
        }catch (Exception e){
            Log.i("Exception",e.toString());
        }

    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        String Month=String.valueOf(month+1);
        String Day=String.valueOf(day);
        String Year=String.valueOf(year);

       Log.i("date",String.valueOf(year)+String.valueOf(month)+String.valueOf(day));
        if (month <= 9)
        {
            Month="0"+(Month);
        }
        if (day<=9)
        {
           Day="0"+Day;
        }
        if (ViewId==tv_date.getId()) {
            tv_date.setText(String.valueOf(Year + "/" + Month + "/" + Day));
        }else{
            Expire.setText(String.valueOf(Year + "/" + Month + "/" + Day));
            ExpireDate=Expire.getText().toString();
        }
    }
}
