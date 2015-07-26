package com.ariana.shahre_ma.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.support.v7.widget.SwitchCompat;
import android.widget.Toast;

import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;
import com.ariana.shahre_ma.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Setting extends ActionBarActivity implements TimePickerDialog.OnTimeSetListener{

    Calendar calendar;

    SwitchCompat SwitchCash;
    KeySettings setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setting=new KeySettings(getApplicationContext());

        calendar=Calendar.getInstance();
        SwitchCash=(SwitchCompat)findViewById(R.id.sw_cash);
        SwitchCash.setChecked(setting.getCacheImage());



        ListView list_notify=(ListView)findViewById(R.id.notify_list);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getnotification());
        list_notify.setAdapter(adapter);
        list_notify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    //     TimePickerDialog.newInstance(Setting.this, calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE), false).show(getFragmentManager(), "timePicker");

                    TimePickerDialog t = new TimePickerDialog();
                    t.initialize(Setting.this, 9, 00, false);
                    t.show(getFragmentManager(), "end");
                    t.setOnTimeSetListener(new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(RadialPickerLayout radialPickerLayout, int i, int i1) {
                            try {



                                setting.saveAMtime(String.valueOf(i) + ":" + String.valueOf(i1));

                                Toast.makeText(getApplicationContext(), String.valueOf(i) + ":" + String.valueOf(i1), Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                            }
                        }
                    });

                } else if (position == 1) {

                    TimePickerDialog t = new TimePickerDialog();
                    t.initialize(Setting.this, 16, 00, false);
                    t.show(getFragmentManager(), "end");
                    t.setUserVisibleHint(true);
                    t.setOnTimeSetListener(new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(RadialPickerLayout radialPickerLayout, int i, int i1) {

                            setting = new KeySettings(Setting.this);
                            setting.savePMtime(String.valueOf(i) + ":" + String.valueOf(i1));

                            Toast.makeText(getApplicationContext(), String.valueOf(i) + ":" + String.valueOf(i1), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });



        SwitchCash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (isChecked)
                {
                    setting.saveCacheImage(true);
                }
                else
                {
                    setting.saveCacheImage(false);
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
    public void UpdateActivity(View v){

        Intent i=new Intent(getApplicationContext(),UpdateActivity.class);
        startActivity(i);
    }
    public void Cashing(View v){
        if (SwitchCash.isChecked()){
            SwitchCash.setChecked(false);
        }else{
            SwitchCash.setChecked(true);
        }

    }
}

