package com.ariana.shahre_ma.Bazarche;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.ariana.shahre_ma.Fields.FieldDataBase;
import com.ariana.shahre_ma.R;

public class product_Details extends ActionBarActivity {



    TextView phone;
    TextView date;
    TextView description;
    TextView property;
    TextView email;
    TextView address;
    TextView name;
    TextView price;

    ImageView img1;
    ImageView img2;
    ImageView img3;
    ImageView img4;

    FieldDataBase fieldDataBase=new FieldDataBase();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__details);

        Initilize();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product__details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private  void Initilize()
    {
        phone=(TextView) findViewById(R.id.product_tell);
        date=(TextView) findViewById(R.id.product_date);
        description=(TextView) findViewById(R.id.product_desc);
        property=(TextView) findViewById(R.id.product_property);
        email =(TextView) findViewById(R.id.product_email);
        address=(TextView) findViewById(R.id.product_address);
        name=(TextView) findViewById(R.id.product_name);
        price=(TextView) findViewById(R.id.product_price);


        img1=(ImageView) findViewById(R.id.image1);
        img2=(ImageView) findViewById(R.id.image2);
        img3=(ImageView) findViewById(R.id.image3);
        img4=(ImageView) findViewById(R.id.image4);
    }

    private void LoadData()
    {
        for(int i=0; i<fieldDataBase.getName_Product().size();i++) {
            phone.setText(fieldDataBase.getPhone__Product().get(i));
            date.setText(fieldDataBase.getDate_Product().get(i));
            description.setText(fieldDataBase.getDescription_Product().get(i));
            property .setText(fieldDataBase.getProperty_Product().get(i));
            email.setText(fieldDataBase.getEmail_Product().get(i));
            address.setText(fieldDataBase.getAddress_Product().get(i));
            name.setText(fieldDataBase.getName_Collection().get(i));
            price.setText(String.valueOf(fieldDataBase.getprice_Product().get(i)));
        }

    }
}