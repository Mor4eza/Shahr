package com.ariana.shahre_ma.MyBusiness;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.*;
import android.view.*;
import com.ariana.shahre_ma.R;

import java.util.ArrayList;
import java.util.List;

public class Add_business extends ActionBarActivity {
    
    EditText Market_name;
    EditText Market_tell;
    EditText Market_mobile;
    EditText Market_fax;
    EditText Market_email;
    EditText Market_owner;
    EditText Market_address;
    EditText Market_desc;
    AutoCompleteTextView Market_subset;
    AutoCompleteTextView Market_zone;
    MultiAutoCompleteTextView Market_field;
    Spinner Market_gharar;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_business);
    Init_Views();

    }
    
   void Init_Views(){
       
       Market_name =(EditText)findViewById(R.id.add_market_name);
       Market_tell =(EditText)findViewById(R.id.add_market_tell);
       Market_mobile =(EditText)findViewById(R.id.add_market_phone);
       Market_fax =(EditText)findViewById(R.id.add_market_fax);
       Market_email =(EditText)findViewById(R.id.add_market_email);
       Market_owner =(EditText)findViewById(R.id.add_market_owner);
       Market_address =(EditText)findViewById(R.id.add_market_address);
       Market_desc =(EditText)findViewById(R.id.add_market_desc);
       Market_subset =(AutoCompleteTextView)findViewById(R.id.ac_subset);
       Market_zone =(AutoCompleteTextView)findViewById(R.id.ac_area);
       Market_field =(MultiAutoCompleteTextView)findViewById(R.id.ac_field);
       Market_gharar=(Spinner)findViewById(R.id.spinner_gh);
       SpinnerSetUp();
       
   }
    void SpinnerSetUp(){


        Market_gharar.setPrompt("مدت قرارداد");
        List<String> list = new ArrayList<String>();
        list.add("3");
        list.add("6");
        list.add("9");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Market_gharar.setAdapter(dataAdapter);

    }
    public void save_edit(View v){



    }

}
