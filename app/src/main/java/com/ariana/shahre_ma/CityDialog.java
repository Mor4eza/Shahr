package com.ariana.shahre_ma;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ariana2 on 7/9/2015.
 */
public class CityDialog extends Dialog {

    Button btnchange;
    public CityDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.city_dialog_popup);
        super.onCreate(savedInstanceState);
        sp();
        btnchange = (Button)findViewById(R.id.btn_selected_city);
        btnchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


    }

    public void sp(){

        DataBaseSqlite db=new DataBaseSqlite(getContext());
        Cursor allrows=db.select_AllCity();

        Spinner Sp_City = (Spinner) findViewById(R.id.select_city_dialog);
        Sp_City.setPrompt("انتخاب شهر:");
        List<String> list = new ArrayList<String>();
        if(allrows.moveToFirst())
        {
            do
            {
                list.add(allrows.getString(1));

            }while (allrows.moveToNext());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Sp_City.setAdapter(dataAdapter);
    }
}
