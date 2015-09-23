package com.ariana.shahre_ma.Bazarche;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ariana.shahre_ma.Bazarche.WebServiceGet.HTTPGetProductpropertiesJson;
import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.Fields.FieldDataBase;
import com.ariana.shahre_ma.R;
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
    TextView details1;
    ImageView img1;
    ImageView img2;
    ImageView img3;
    ImageView img4;
    RelativeLayout rel_desc,Rel_com;
    ProgressBar progressBar_product;
    String urlImage[]=new String[4];

    DataBaseSqlite db=new DataBaseSqlite(this);
    FieldDataBase fieldDataBase=new FieldDataBase();
    Query query=new Query(this);
    FieldClass fc=new FieldClass();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__details);
        Initilize();
        LocalBroadcastManager.getInstance(this).registerReceiver(mProductReceiver, new IntentFilter("Product_property"));

        HTTPGetProductpropertiesJson httpGetProductPropertyJson=new HTTPGetProductpropertiesJson(this);
        httpGetProductPropertyJson.setProductId(fc.GetProductId());
        httpGetProductPropertyJson.execute();


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
        details1=(TextView)findViewById(R.id.product_details1);
        Rel_com=(RelativeLayout) findViewById(R.id.rel_com);
        rel_desc=(RelativeLayout) findViewById(R.id.rel_desc);
        progressBar_product=(ProgressBar)findViewById(R.id.progress_product);
        img1=(ImageView) findViewById(R.id.image1);
        img2=(ImageView) findViewById(R.id.image2);
        img3=(ImageView) findViewById(R.id.image3);
        img4=(ImageView) findViewById(R.id.image4);
    }

    private void LoadData()
    {

        try {

            String adaptive = "";
            for (int i = 0; i < fieldDataBase.getName_Product().size(); i++) {
                phone.setText("تلفن: " + fieldDataBase.getPhone_Product().get(i));
                date.setText("تاریخ: " + fieldDataBase.getDate_Product().get(i));
                description.setText("توضیحات: " + fieldDataBase.getDescription_Product().get(i));
                property.setText("خصوصیات: " + fieldDataBase.getProperty_Product().get(i));
                email.setText("ایمیل: " + fieldDataBase.getEmail_Product().get(i));
                address.setText("آدرس: " + fieldDataBase.getAddress_Product().get(i));
                name.setText(fieldDataBase.getName_Product().get(i));
                ///images
                urlImage[0] = fieldDataBase.getImage_Product().get(i);
                urlImage[1] = fieldDataBase.getImage_Product2().get(i);
                urlImage[2] = fieldDataBase.getImage_Product3().get(i);
                urlImage[3] = fieldDataBase.getImage_Product4().get(i);

                if (fieldDataBase.getAdaptive_Product().get(i)) {
                    adaptive = "(توافقی)";
                } else {
                    adaptive = "(مقطوع)";
                }
                price.setText(String.valueOf(fieldDataBase.getprice_Product().get(i)) + adaptive);
                for (int h = 0; h < fieldDataBase.getPropertyId_Product().size(); h++) {
                    String namevalue = "";
                    if (fieldDataBase.getValue_Product().get(h).equals(""))
                        namevalue = "وارد نشده";
                    else
                        namevalue = query.getValueName(Integer.parseInt(fieldDataBase.getValue_Product().get(h).replaceAll("[\\D]", "0")));

                    if (namevalue.equals("") || namevalue.equals("null") || namevalue.equals(null))
                        details1.setText(details1.getText() + "\n\n" + query.getPropertyName(fieldDataBase.getPropertyId_Product().get(h)) + " : " + (fieldDataBase.getValue_Product().get(h)));
                    else
                        details1.setText(details1.getText() + "\n\n" + query.getPropertyName(fieldDataBase.getPropertyId_Product().get(h)) + " : " + namevalue);
                    LoadImage();
                }
            }
        }catch (Exception e){

        }

    }

    private void LoadImage()
    {
        try
        {
            Picasso.with(this).load("http://www.shahrma.com/image/business/" + urlImage[0]).placeholder(R.drawable.img_not_found).into(img1);
            Picasso.with(this).load("http://www.shahrma.com/image/business/" + urlImage[1]).placeholder(R.drawable.img_not_found).into(img2);
            Picasso.with(this).load("http://www.shahrma.com/image/business/" + urlImage[2]).placeholder(R.drawable.img_not_found).into(img3);
            Picasso.with(this).load("http://www.shahrma.com/image/business/" + urlImage[3]).placeholder(R.drawable.img_not_found).into(img4);
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
            }
            else {
                LoadData();
                progressBar_product.setVisibility(View.GONE);
                rel_desc.setVisibility(View.VISIBLE);
                Rel_com.setVisibility(View.VISIBLE);
            }
        }

    };

}
