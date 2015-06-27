package com.ariana.shahre_ma;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Setting extends ActionBarActivity implements TimePickerDialog.OnTimeSetListener{

    Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        calendar=Calendar.getInstance();

        ListView list_notify=(ListView)findViewById(R.id.notify_list);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getnotification());
        list_notify.setAdapter(adapter);
        list_notify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
               //     TimePickerDialog.newInstance(Setting.this, calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE), false).show(getFragmentManager(), "timePicker");

                    TimePickerDialog t=new TimePickerDialog();
                    t.initialize(Setting.this,9,00,false);
                    t.show(getFragmentManager(), "end");
                    t.setOnTimeSetListener(new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(RadialPickerLayout radialPickerLayout, int i, int i1) {
//detect
                            SharedPreferences per=getSharedPreferences("Setting",MODE_PRIVATE);
                            SharedPreferences.Editor editor=per.edit();
                            editor.putString("AMtime",String.valueOf(i)+":" +String.valueOf(i1));

                            Toast.makeText(getApplicationContext(),String.valueOf(i)+":" +String.valueOf(i1),Toast.LENGTH_LONG).show();
                        }
                    });

                }else if (position==1){

                    TimePickerDialog t=new TimePickerDialog();
                    t.initialize(Setting.this,16,00,false);
                    t.show(getFragmentManager(), "end");
                    t.setUserVisibleHint(true);
                    t.setOnTimeSetListener(new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(RadialPickerLayout radialPickerLayout, int i, int i1) {

                            SharedPreferences per=getSharedPreferences("Setting",MODE_PRIVATE);
                            SharedPreferences.Editor editor=per.edit();
                            editor.putString("PMtime",String.valueOf(i)+":" +String.valueOf(i1));
                            Toast.makeText(getApplicationContext(),String.valueOf(i)+":" +String.valueOf(i1),Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });


    }
    private List<String> getnotification() {
        List<String> item = new ArrayList<String>();
        item.add("قبل از ظهر");
        item.add("بعد از ظهر");
    return item;
    }

    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int i, int i1) {

    }
}

