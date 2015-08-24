package com.ariana.shahre_ma.Bazarche;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.Fields.FieldDataBase;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetBusinessImageJson;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetProductPropertyJson;
import com.squareup.picasso.Picasso;

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

    String urlImage[]=new String[2];

    DataBaseSqlite db=new DataBaseSqlite(this);
    FieldDataBase fieldDataBase=new FieldDataBase();
    FieldClass fc=new FieldClass();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__details);
        Initilize();
        LocalBroadcastManager.getInstance(this).registerReceiver(mProductReceiver, new IntentFilter("Product_property"));



        HTTPGetProductPropertyJson httpGetProductPropertyJson=new HTTPGetProductPropertyJson(this);
        httpGetProductPropertyJson.setProductId(fc.GetProductId());
        httpGetProductPropertyJson.execute();


        HTTPGetBusinessImageJson httpGetBusinessImageJson=new HTTPGetBusinessImageJson(this);
        httpGetBusinessImageJson.SetBusinessId(fc.GetProductId());
        httpGetBusinessImageJson.execute();


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
        try {
            for (int i = 0; i < fieldDataBase.getName_Product().size(); i++)
            {
                phone.setText(fieldDataBase.getPhone__Product().get(i));
                date.setText(fieldDataBase.getDate_Product().get(i));
                description.setText(fieldDataBase.getDescription_Product().get(i));
                property.setText(fieldDataBase.getProperty_Product().get(i));
                email.setText(fieldDataBase.getEmail_Product().get(i));
                address.setText(fieldDataBase.getAddress_Product().get(i));
                name.setText(fieldDataBase.getName_Product().get(i));
                price.setText(String.valueOf(fieldDataBase.getprice_Product().get(i)));


                Picasso.with(this).load("http://www.shahrma.com/image/business/"+fieldDataBase.getImage_Product().get(i)).into(img1);
            }
        }
        catch (Exception e)
        {

        }

    }

    private void LoadImage()
    {
        try
        {
            int i=0;
            Cursor rows=db.select_BusinessImage(fc.GetProductId());
            if(rows.moveToFirst())
            {
                do
                {
                    urlImage[i]="http://www.shahrma.com/image/business/"+rows.getString(2);
                    Log.i("AddressImage",urlImage[i]);
                    i++;

                }while (rows.moveToNext());
            }


            Picasso.with(this).load(urlImage[0]).into(img2);
            Picasso.with(this).load(urlImage[1]).into(img3);
            Picasso.with(this).load(urlImage[2]).into(img4);

        }
        catch (Exception e)
        {

        }
    }
    private BroadcastReceiver mProductReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if(fc.GetProductReceiver()) {
                LoadImage();
                fc.SetProductReceiver(false);
                Log.i("ProductReceiver","image");
            }
            else {
                LoadData();
                Log.i("ProductReceiver", "date");
            }
        }

    };

}
