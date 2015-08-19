package com.ariana.shahre_ma.Bazarche;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.ariana.shahre_ma.R;

public class Edit_Product extends ActionBarActivity {
    EditText tv_product_name;
    EditText tv_product_price;
    EditText tv_product_tell;
    EditText tv_product_mobile;
    EditText tv_product_email;
    EditText tv_product_desc;
    EditText tv_product_property;
    EditText tv_product_address;
    AutoCompleteTextView tv_product_subset;
    AutoCompleteTextView tv_product_city;
    AutoCompleteTextView tv_product_area;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__product);
        initViews();

    }

    public void product_save(View view) {


    }

    public void initViews(){
        tv_product_name=(EditText)findViewById(R.id.edit_product_name);
        tv_product_price=(EditText)findViewById(R.id.edit_product_price);
        tv_product_tell=(EditText)findViewById(R.id.edit_product_tell);
        tv_product_mobile=(EditText)findViewById(R.id.edit_product_phone);
        tv_product_email=(EditText)findViewById(R.id.edit_product_email);
        tv_product_desc=(EditText)findViewById(R.id.edit_product_desc);
        tv_product_property=(EditText)findViewById(R.id.edit_product_property);
        tv_product_address=(EditText)findViewById(R.id.edit_product_address);
        tv_product_subset=(AutoCompleteTextView)findViewById(R.id.edit_ac_product_subset);
        tv_product_city=(AutoCompleteTextView)findViewById(R.id.edit_ac_product_city);
        tv_product_area=(AutoCompleteTextView)findViewById(R.id.edit_ac_product_area);
    }


    public void select_map(View view) {


    }
}
