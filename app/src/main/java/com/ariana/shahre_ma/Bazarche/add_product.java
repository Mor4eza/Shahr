package com.ariana.shahre_ma.Bazarche;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.ariana.shahre_ma.Bazarche.WebServiceGet.HTTPGetProductPropertyJson;
import com.ariana.shahre_ma.Bazarche.WebServiceGet.HTTPGetProductValueJson;
import com.ariana.shahre_ma.Date.DateTime;
import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldDataBase;
import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServiceGet.SqliteTOjson;
import com.ariana.shahre_ma.Bazarche.WebServicePost.HTTPPostProductJson;

import java.util.ArrayList;
import java.util.List;

public class add_product extends ActionBarActivity {


    EditText tv_product_name;
    EditText tv_product_price;
    EditText tv_product_tell;
    EditText tv_product_mobile;
    EditText tv_product_email;
    EditText tv_product_desc;
    EditText tv_product_property;
    EditText tv_product_address;
    Spinner  Sp_collection;
    Spinner  Sp_subset;
    AutoCompleteTextView tv_product_subset;
    AutoCompleteTextView tv_product_city;
    AutoCompleteTextView tv_product_area;
    CheckBox cb_adaptive_product;
    RadioGroup radioGroup;
    String json="";
    String name="";
    Double price=0.0;
    String tell="";
    String mobile="";
    String email="";
    String descripction="";
    String property="";
    String address="";
    Integer subsetid=0;
    Integer cityid=0;
    Integer areaid=0;
    Double latitude=0.0;
    Double longtiude=0.0;
    Boolean adaptive=true;


    SqliteTOjson sqliteTOjson=new SqliteTOjson(this);
    Query query=new Query(this);
    NetState net=new NetState(this);
    FieldDataBase fieldDataBase=new FieldDataBase();
    DateTime dt=new DateTime();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        initViews();

        GetNameCity();
        GetNameSubset();
        GetNameCollection();

        //Radio Group

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.tavafoq) {
                    adaptive = false;
                } else {
                    adaptive = true;
                }
            }
        });



        tv_product_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("onItemClick", tv_product_city.getText().toString());
                GetNameArea(tv_product_city.getText().toString());
            }
        });

        Sp_collection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                GetNameSubset(Sp_collection.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        HTTPGetProductPropertyJson httpGetProductPropertyJson=new HTTPGetProductPropertyJson(this);
        httpGetProductPropertyJson.execute();

        HTTPGetProductValueJson httpGetProductValueJson=new HTTPGetProductValueJson(this);
        httpGetProductValueJson.execute();
    }

    public void product_save(View view)
    {
      try
      {
           name=tv_product_name.getText().toString();
           price=Double.parseDouble(tv_product_price.getText().toString());
           tell=tv_product_tell.getText().toString();
           mobile=tv_product_mobile.getText().toString();
           email=tv_product_email.getText().toString();
           descripction=tv_product_desc.getText().toString();
           property=tv_product_property.getText().toString();
           address=tv_product_address.getText().toString();
           subsetid=query.getsubsetProductID(tv_product_subset.getText().toString());
           areaid= query.getAreaID(tv_product_area.getText().toString());
           Log.i("areaid",String.valueOf(query.getAreaID(tv_product_area.getText().toString())));
           Log.i("areaname",String.valueOf(tv_product_area.getText().toString()));



          if(net.checkInternetConnection())
          {
             /* if(name.equals(""))
              {
                  AlertDialog alertDialog = new AlertDialog.Builder(add_product.this).create();
                  alertDialog.setTitle("هشدار");
                  alertDialog.setMessage("نام کالا را وارد کنید");
                  alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int which) {


                      }
                  });
                  alertDialog.show();
              }
              else if(price.equals("") || adaptive==false)
              {
                  AlertDialog alertDialog = new AlertDialog.Builder(add_product.this).create();
                  alertDialog.setTitle("هشدار");
                  alertDialog.setMessage("قیمت را وارد کنید .");
                  alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int which) {


                      }
                  });
                  alertDialog.show();
              }
              else  if(tell.equals("") || mobile.equals(""))
              {
                  AlertDialog alertDialog = new AlertDialog.Builder(add_product.this).create();
                  alertDialog.setTitle("هشدار");
                  alertDialog.setMessage("شماره تلفن یا موبایل خود را وارد کنید.");
                  alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int which) {


                      }
                  });
                  alertDialog.show();
              }
              else if(areaid==0)
              {
                  AlertDialog alertDialog = new AlertDialog.Builder(add_product.this).create();
                  alertDialog.setTitle("هشدار");
                  alertDialog.setMessage("نام منتطه را وارد کنید");
                  alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int which) {


                      }
                  });
                  alertDialog.show();
              }*/
             /* else
              {*/
                  json = sqliteTOjson.ProductTOjson(query.getMemberId(), name,dt.Now(), property, price, latitude, longtiude, adaptive, descripction, tell, mobile, address, email, subsetid, areaid);
                  HTTPPostProductJson httpPostProductJson = new HTTPPostProductJson(this);
                  httpPostProductJson.SetProduct_Json(json);
                  httpPostProductJson.execute();
             // }
          }
          else
          {
              AlertDialog alertDialog = new AlertDialog.Builder(add_product.this).create();
              alertDialog.setTitle("اینترنت");
              alertDialog.setMessage("اینترنت قطع می باشد .");
              alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialog, int which) {


                  }
              });
              alertDialog.show();
          }

      }catch (Exception e)
      {

      }

    }

    public void initViews(){
        tv_product_name=(EditText)findViewById(R.id.add_product_name);
        tv_product_price=(EditText)findViewById(R.id.add_product_price);
        tv_product_tell=(EditText)findViewById(R.id.add_product_tell);
        tv_product_mobile=(EditText)findViewById(R.id.add_product_phone);
        tv_product_email=(EditText)findViewById(R.id.add_product_email);
        tv_product_desc=(EditText)findViewById(R.id.add_product_desc);
        tv_product_property=(EditText)findViewById(R.id.add_product_property);
        tv_product_address=(EditText)findViewById(R.id.add_product_address);
        tv_product_subset=(AutoCompleteTextView)findViewById(R.id.ac_product_subset);
        tv_product_city=(AutoCompleteTextView)findViewById(R.id.ac_product_city);
        tv_product_area=(AutoCompleteTextView)findViewById(R.id.ac_product_area);
        Sp_collection = (Spinner)findViewById(R.id.sp_collection);
        Sp_subset = (Spinner)findViewById(R.id.sp_Subset);
       // cb_adaptive_product=(CheckBox)findViewById(R.id.chk_tavafoq);
        radioGroup=(RadioGroup)findViewById(R.id.radio_price);


    }


    public void select_map(View view) {
    }


    public void GetNameArea(String namecity)
    {
        try {

            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, getId(query.getCityId(namecity)));
            tv_product_area.setAdapter(adapter);

        }
        catch (Exception e)
        {
            Log.e("ExceptionSQL", e.toString());
        }
    }


    public List<String> getId(Integer cityid)
    {

        DataBaseSqlite db=new DataBaseSqlite(this);
        List<String> studentList = new ArrayList<String>();
        Cursor allrows  = db.select_Area(cityid);
        if (allrows.moveToFirst()) {
            do {

                Log.i("area",allrows.getString(1));
                studentList.add(allrows.getString(1));


            } while (allrows.moveToNext());
        }

        return studentList;
    }



    public void GetNameCity()
    {
        try {

            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, getId2());
            tv_product_city.setAdapter(adapter);
        }
        catch (Exception e)
        {
            Log.e("ExceptionSQL", e.toString());
        }
    }
    public List<String> getId2() {

        DataBaseSqlite db=new DataBaseSqlite(this);
        List<String> studentList = new ArrayList<String>();
        Cursor allrows  = db.select_AllCity();
        if (allrows.moveToFirst()) {
            do {

                Log.i("city",allrows.getString(1));
                studentList.add(allrows.getString(1));


            } while (allrows.moveToNext());
        }

        return studentList;
    }



    public void GetNameSubset()
    {
        try {

            Log.i("subsetProduct",String.valueOf(fieldDataBase.getName_Subset().size()));
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,getId3());
            tv_product_subset.setAdapter(adapter);
        }
        catch (Exception e)
        {
            Log.e("ExceptionSQL", e.toString());
        }
    }


    public List<String> getId3() {

        DataBaseSqlite db=new DataBaseSqlite(this);
        List<String> studentList = new ArrayList<String>();
        Cursor allrows  = db.select_Subset_Product();
        if (allrows.moveToFirst()) {
            do {

                studentList.add(allrows.getString(1));

            } while (allrows.moveToNext());
        }

        return studentList;
    }

    public void GetNameCollection()
    {
        try {

            Log.i("subsetProduct",String.valueOf(fieldDataBase.getName_Subset().size()));
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,getnamecollect());
            Sp_collection.setAdapter(adapter);
        }
        catch (Exception e)
        {
            Log.e("ExceptionSQL", e.toString());
        }
    }


    public List<String> getnamecollect() {

        DataBaseSqlite db=new DataBaseSqlite(this);
        List<String> studentList = new ArrayList<String>();
        Cursor allrows  = db.select_Collection_Product();
        if (allrows.moveToFirst()) {
            do {

                studentList.add(allrows.getString(1));

            } while (allrows.moveToNext());
        }

        return studentList;
    }



    public void GetNameSubset(String collectionproduct)
    {
        try {

            Log.i("subsetProduct",String.valueOf(fieldDataBase.getName_Subset().size()));
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,getnamesubset( collectionproduct));
            Sp_subset.setAdapter(adapter);
        }
        catch (Exception e)
        {
            Log.e("ExceptionSQL", e.toString());
        }
    }


    public List<String> getnamesubset(String collectionproduct) {

        DataBaseSqlite db=new DataBaseSqlite(this);
        List<String> studentList = new ArrayList<String>();
        Cursor allrows  = db.select_Subset_Product(query.getCollectionIdProduct(collectionproduct));
        if (allrows.moveToFirst()) {
            do {

                studentList.add(allrows.getString(1));

            } while (allrows.moveToNext());
        }

        return studentList;
    }




}
