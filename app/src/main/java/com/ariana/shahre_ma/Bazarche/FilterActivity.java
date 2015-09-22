package com.ariana.shahre_ma.Bazarche;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ariana.shahre_ma.Bazarche.WebServicePost.HTTPPostFilterJson;
import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldDataBase;
import com.ariana.shahre_ma.MessageDialog;
import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServiceGet.SqliteTOjson;
import com.wrapp.floatlabelededittext.FloatLabeledEditText;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends ActionBarActivity {


    Spinner  Sp_collection;
    Spinner  Sp_subset;
    EditText tv_product_name;
    EditText et_Price;
    List<String> nameProperty;
    List<Integer> propertyid;
    List<String> namevalue;
    SqliteTOjson sqliteTOjson=new SqliteTOjson(this);
    String json="";
    String Search="";
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
    List<Integer> valueid = new ArrayList<>();
    List<String> valuetext=new ArrayList<>();
    MessageDialog messageDialog=new MessageDialog(this);
    Query query=new Query(this);
    NetState net=new NetState(this);
    FieldDataBase fieldDataBase=new FieldDataBase();
    RelativeLayout rel_val1,rel_val2,rel_val3,rel_val4,rel_val5,rel_val6,rel_val7,rel_val8;
    TextView tv_val1,tv_val2,tv_val3,tv_val4,tv_val5,tv_val6,tv_val7,tv_val8;
    Spinner Sp_val1,Sp_val2,Sp_val3,Sp_val4,Sp_val5,Sp_val6,Sp_val7,Sp_val8;
    FloatLabeledEditText float1,float2,float3,float4,float5,float6,float7,float8;
    EditText et_prop1,et_prop2,et_prop3,et_prop4,et_prop5,et_prop6,et_prop7,et_prop8;
    Boolean _enable1=false,_enable2=false,_enable3=false,_enable4=false,_enable5=false,_enable6=false,_enable7=false,_enable8=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        initViews();
        GetNameCollection();

        Sp_collection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rel_val1.setVisibility(View.GONE);
                rel_val2.setVisibility(View.GONE);
                rel_val3.setVisibility(View.GONE);
                rel_val4.setVisibility(View.GONE);
                rel_val5.setVisibility(View.GONE);
                rel_val6.setVisibility(View.GONE);
                rel_val7.setVisibility(View.GONE);
                rel_val8.setVisibility(View.GONE);
                float1.setVisibility(View.GONE);
                float2.setVisibility(View.GONE);
                float3.setVisibility(View.GONE);
                float4.setVisibility(View.GONE);
                float5.setVisibility(View.GONE);
                float6.setVisibility(View.GONE);
                float7.setVisibility(View.GONE);
                float8.setVisibility(View.GONE);


                GetNameSubset(Sp_collection.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Sp_subset.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Sp_subset", Sp_subset.getSelectedItem().toString());
                getnameproperty(Sp_subset.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    public void initViews(){

        /////
        tv_product_name=(EditText)findViewById(R.id.filter_product_name);
        Sp_collection = (Spinner)findViewById(R.id.sp_collection);
        Sp_subset = (Spinner)findViewById(R.id.sp_Subset);
        et_Price=(EditText)findViewById(R.id.et_price1);
        /////
        Sp_val1 = (Spinner)findViewById(R.id.sp_val1);
        Sp_val2 = (Spinner)findViewById(R.id.sp_val2);
        Sp_val3 = (Spinner)findViewById(R.id.sp_val3);
        Sp_val4 = (Spinner)findViewById(R.id.sp_val4);
        Sp_val5 = (Spinner)findViewById(R.id.sp_val5);
        Sp_val6 = (Spinner)findViewById(R.id.sp_val6);
        Sp_val7 = (Spinner)findViewById(R.id.sp_val7);
        Sp_val8 = (Spinner)findViewById(R.id.sp_val8);
        //////
        et_prop1=(EditText)findViewById(R.id.et_prop1);
        et_prop2=(EditText)findViewById(R.id.et_prop2);
        et_prop3=(EditText)findViewById(R.id.et_prop3);
        et_prop4=(EditText)findViewById(R.id.et_prop4);
        et_prop5=(EditText)findViewById(R.id.et_prop5);
        et_prop6=(EditText)findViewById(R.id.et_prop6);
        et_prop7=(EditText)findViewById(R.id.et_prop7);
        et_prop8=(EditText)findViewById(R.id.et_prop8);
        //////
        rel_val1=(RelativeLayout)findViewById(R.id.rel_val1);
        rel_val2=(RelativeLayout)findViewById(R.id.rel_val2);
        rel_val3=(RelativeLayout)findViewById(R.id.rel_val3);
        rel_val4=(RelativeLayout)findViewById(R.id.rel_val4);
        rel_val5=(RelativeLayout)findViewById(R.id.rel_val5);
        rel_val6=(RelativeLayout)findViewById(R.id.rel_val6);
        rel_val7=(RelativeLayout)findViewById(R.id.rel_val7);
        rel_val8=(RelativeLayout)findViewById(R.id.rel_val8);

        //////
        tv_val1=(TextView)findViewById(R.id.tv_val1);
        tv_val2=(TextView)findViewById(R.id.tv_val2);
        tv_val3=(TextView)findViewById(R.id.tv_val3);
        tv_val4=(TextView)findViewById(R.id.tv_val4);
        tv_val5=(TextView)findViewById(R.id.tv_val5);
        tv_val6=(TextView)findViewById(R.id.tv_val6);
        tv_val7=(TextView)findViewById(R.id.tv_val7);
        tv_val8=(TextView)findViewById(R.id.tv_val8);
        /////
        float1= (FloatLabeledEditText) findViewById(R.id.float1);
        float2= (FloatLabeledEditText) findViewById(R.id.float2);
        float3= (FloatLabeledEditText) findViewById(R.id.float3);
        float4= (FloatLabeledEditText) findViewById(R.id.float4);
        float5= (FloatLabeledEditText) findViewById(R.id.float5);
        float6= (FloatLabeledEditText) findViewById(R.id.float6);
        float7= (FloatLabeledEditText) findViewById(R.id.float7);
        float8= (FloatLabeledEditText) findViewById(R.id.float8);
    }


    public void filter(View view) {
     /* try
      {*/
        Search=tv_product_name.getText().toString();
        price=Double.parseDouble(et_Price.getText().toString());
        subsetid=query.getsubsetProductID(Sp_subset.getSelectedItem().toString());
        areaid= 165;

        if(net.checkInternetConnection())
        {

            if(tv_product_name.getText().length()==0)
            {
                messageDialog.ShowMessage("پیام","نام کالا را وارد کنید","باشه","false");
            }
            else if(et_Price.getText().length()==0){
                messageDialog.ShowMessage("پیام","قیمت را وارد کنید","باشه","false");
            }
            else if(et_Price.getText().toString().substring(0,1).equals("0")){
                messageDialog.ShowMessage("پیام","قیمت وارد شده صحیح نیست","باشه","false");
            }
           /* else if(subsetid==0){
                messageDialog.ShowMessage("پیام","زیر گروه را انتخاب کنید","باشه","false");
            }
            else  if(areaid==0){
                messageDialog.ShowMessage("پیام","منطقه و شهر خود را انتخاب کنید","باشه","false");
            }*/

            else
            {
                if (_enable1) {
                    valuetext.add("");
                    Log.i("vlaueID", String.valueOf(query.getValueId(Sp_val1.getSelectedItem().toString())));
                    valueid.add(query.getValueId(Sp_val1.getSelectedItem().toString()));// spineer 1
                } else {

                    valuetext.add(et_prop1.getText().toString()); // edit text 1
                    valueid.add(0);
                }

                if (_enable2) {
                    valuetext.add("");
                    Log.i("vlaueID", String.valueOf(query.getValueId(Sp_val2.getSelectedItem().toString())));
                    valueid.add(query.getValueId(Sp_val2.getSelectedItem().toString())); // spineer 2
                } else {
                    valuetext.add(et_prop2.getText().toString()); // edit text 2
                    valueid.add(0);
                }


                if (_enable3) {
                    valuetext.add("");
                    Log.i("vlaueID", String.valueOf(query.getValueId(Sp_val3.getSelectedItem().toString())));
                    valueid.add(query.getValueId(Sp_val3.getSelectedItem().toString())); // spineer 3
                } else {

                    valuetext.add(et_prop3.getText().toString()); // edit text 3
                    valueid.add(0);
                }

                if (_enable4) {
                    valuetext.add("");
                    Log.i("vlaueID", String.valueOf(query.getValueId(Sp_val4.getSelectedItem().toString())));
                    valueid.add(query.getValueId(Sp_val4.getSelectedItem().toString())); // spineer 4
                } else {
                    valuetext.add(et_prop4.getText().toString()); // edit text 4
                    valueid.add(0);
                }


                if (_enable5) {
                    valuetext.add("");
                    Log.i("vlaueID", String.valueOf(query.getValueId(Sp_val5.getSelectedItem().toString())));
                    valueid.add(query.getValueId(Sp_val5.getSelectedItem().toString()));// spineer 5
                } else {
                    valuetext.add(et_prop5.getText().toString()); // edit text 5
                    valueid.add(0);
                }

                if (_enable6) {
                    valuetext.add("");
                    Log.i("vlaueID", String.valueOf(query.getValueId(Sp_val6.getSelectedItem().toString())));
                    valueid.add(query.getValueId(Sp_val6.getSelectedItem().toString())); // spineer 6
                } else {
                    valuetext.add(et_prop6.getText().toString()); // edit text 6
                    valueid.add(0);
                }

                if (_enable7) {
                    valuetext.add("");
                    Log.i("vlaueID", String.valueOf(query.getValueId(Sp_val7.getSelectedItem().toString())));
                    valueid.add(query.getValueId(Sp_val7.getSelectedItem().toString())); // spineer 7
                } else {
                    valuetext.add(et_prop7.getText().toString()); // edit text 7
                    valueid.add(0);
                }

                if (_enable8) {
                    valuetext.add("");
                    Log.i("vlaueID", String.valueOf(query.getValueId(Sp_val8.getSelectedItem().toString())));
                    valueid.add(query.getValueId(Sp_val8.getSelectedItem().toString())); // spineer 8
                } else {
                    valuetext.add(et_prop8.getText().toString()); // edit text 8
                    valueid.add(0);
                }


                json = sqliteTOjson.FilterTOjson(Search,68, price, 1000000.0, false, subsetid,165, valuetext, valueid, propertyid);
                HTTPPostFilterJson httpPostFilterJson = new HTTPPostFilterJson(this);
                httpPostFilterJson.SetProduct_Json(json);
                httpPostFilterJson.execute();
            }

        }
        else
        {
            messageDialog.ShowMessage("پیام","اینترنت قطع می باشد","باشه","false");
        }

      /*}catch (Exception e)
      {

      }*/

    }

    public void GetNameCollection()
    {
        // try {

        Log.i("subsetProduct",String.valueOf(fieldDataBase.getName_Subset().size()));
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,getnamecollect());
        Sp_collection.setAdapter(adapter);
      /*  }
        catch (Exception e)
        {
            Log.e("ExceptionSQL", e.toString());
        }*/
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
        // try {

        Log.i("subsetProduct",String.valueOf(fieldDataBase.getName_Subset().size()));
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,getnamesubset(collectionproduct));
        Sp_subset.setAdapter(adapter);
        /*}
        catch (Exception e)
        {
            Log.e("ExceptionSQL", e.toString());
        }*/
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

    public List<String> getnameproperty(String namesubset) {

        _enable1=false;
        _enable2=false;
        _enable3=false;
        _enable4=false;
        _enable5=false;
        _enable6=false;
        _enable7=false;
        _enable8=false;

        Integer j=0;
        Integer h=0;
        DataBaseSqlite db=new DataBaseSqlite(this);
        propertyid = new ArrayList<Integer>();
        nameProperty = new ArrayList<>();

        try {
            Cursor allrows  = db.select_SubsetProperty_Product(query.getsubsetProductID(namesubset));
            if (allrows.moveToFirst()) {
                do {

                    Log.i("SubsetProperty",String.valueOf(allrows.getInt(0)));
                    propertyid.add(allrows.getInt(0));

                } while (allrows.moveToNext());
            }

            for(Integer id:propertyid)
            {
                Cursor rows  = db.select_Property_Product(id);
                if(rows.moveToFirst())
                {
                    do {
                        nameProperty.add(rows.getString(0));
                        Log.i("Property_Product", rows.getString(0));

                    }while (rows.moveToNext());

                }

            }

            for(int i=0;i<propertyid.size();i++) {
                namevalue = new ArrayList<>();

                Cursor rows = db.select_Value_Product(propertyid.get(i));
                if (rows.moveToFirst())
                {
                    do
                    {
                        namevalue.add(rows.getString(1));
                        Log.i("Value_Product" + i, rows.getString(1));
                        // j++;
                    } while (rows.moveToNext());
                }
                ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,namevalue);
                //Log.i("sizeList", String.valueOf(adapter.getCount()));
                if (namevalue.size() > 0)
                {
                    Log.i("sizeListif", String.valueOf(namevalue.size()));
                    switch (i) {
                        case 0:
                            rel_val1.setVisibility(View.VISIBLE);
                            tv_val1.setText(nameProperty.get(i).toString());
                            Sp_val1.setAdapter(adapter);
                            _enable1 =true;
                            break;
                        case 1:
                            rel_val2.setVisibility(View.VISIBLE);
                            tv_val2.setText(nameProperty.get(i).toString());
                            Sp_val2.setAdapter(adapter);
                            _enable2=true;
                            break;
                        case 2:
                            rel_val3.setVisibility(View.VISIBLE);
                            tv_val3.setText(nameProperty.get(i).toString());
                            Sp_val3.setAdapter(adapter);
                            _enable3=true;
                            break;
                        case 3:
                            rel_val4.setVisibility(View.VISIBLE);
                            tv_val4.setText(nameProperty.get(i).toString());
                            Sp_val4.setAdapter(adapter);
                            _enable4=true;
                            break;
                        case 4:
                            rel_val5.setVisibility(View.VISIBLE);
                            tv_val5.setText(nameProperty.get(i).toString());
                            Sp_val5.setAdapter(adapter);
                            _enable5=true;
                            break;
                        case 5:
                            rel_val6.setVisibility(View.VISIBLE);
                            tv_val6.setText(nameProperty.get(i).toString());
                            Sp_val6.setAdapter(adapter);
                            _enable6=true;
                            break;
                        case 6:
                            rel_val7.setVisibility(View.VISIBLE);
                            tv_val7.setText(nameProperty.get(i).toString());
                            Sp_val7.setAdapter(adapter);
                            _enable7=true;
                            break;
                        case 7:
                            rel_val8.setVisibility(View.VISIBLE);
                            tv_val8.setText(nameProperty.get(i).toString());
                            Sp_val8.setAdapter(adapter);
                            _enable8=true;
                            break;
                    }
                    //namevalue.clear();
                }
                else
                {
                    Log.i("sizeListelse", String.valueOf(namevalue.size()));
                    switch (i) {
                        case 0:
                            float1.setVisibility(View.VISIBLE);
                            et_prop1.setHint(nameProperty.get(i));
                            break;
                        case 1:
                            float2.setVisibility(View.VISIBLE);
                            et_prop2.setHint(nameProperty.get(i));
                            break;
                        case 2:
                            float3.setVisibility(View.VISIBLE);
                            et_prop3.setHint(nameProperty.get(i));
                            break;
                        case 3:
                            float4.setVisibility(View.VISIBLE);
                            et_prop4.setHint(nameProperty.get(i));
                            break;
                        case 4:
                            float5.setVisibility(View.VISIBLE);
                            et_prop5.setHint(nameProperty.get(i));
                            break;
                        case 5:
                            float6.setVisibility(View.VISIBLE);
                            et_prop6.setHint(nameProperty.get(i));
                            break;
                        case 6:
                            float7.setVisibility(View.VISIBLE);
                            et_prop7.setHint(nameProperty.get(i));
                            break;
                        case 7:
                            float8.setVisibility(View.VISIBLE);
                            et_prop8.setHint(nameProperty.get(i));
                            break;
                    }
                }

            }
        }
        catch (Exception e){}

        return nameProperty;
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(this,Product_List.class);
        startActivity(i);
        super.onBackPressed();
    }
}
