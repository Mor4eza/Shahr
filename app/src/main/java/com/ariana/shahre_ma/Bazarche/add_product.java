package com.ariana.shahre_ma.Bazarche;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ariana.shahre_ma.Bazarche.WebServiceGet.HTTPGetProductPropertyJson;
import com.ariana.shahre_ma.Bazarche.WebServiceGet.HTTPGetProductSubsetPropertyJson;
import com.ariana.shahre_ma.Bazarche.WebServiceGet.HTTPGetProductValueJson;
import com.ariana.shahre_ma.Bazarche.WebServicePost.HTTPPostProductJson;
import com.ariana.shahre_ma.Date.DateTime;
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

public class add_product extends ActionBarActivity {


    EditText tv_product_name;
    EditText tv_product_price;
    EditText tv_product_tell;
    EditText tv_product_email;
    EditText tv_product_desc;
    EditText tv_product_address;
    Spinner  Sp_collection;
    Spinner  Sp_subset;
    RelativeLayout rel_val1,rel_val2,rel_val3,rel_val4,rel_val5,rel_val6,rel_val7,rel_val8;
    TextView tv_val1,tv_val2,tv_val3,tv_val4,tv_val5,tv_val6,tv_val7,tv_val8;
    Spinner  Sp_val1,Sp_val2,Sp_val3,Sp_val4,Sp_val5,Sp_val6,Sp_val7,Sp_val8;
    FloatLabeledEditText float1,float2,float3,float4,float5,float6,float7,float8;
    EditText et_prop1,et_prop2,et_prop3,et_prop4,et_prop5,et_prop6,et_prop7,et_prop8;
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


    Boolean _enable1=false,_enable2=false,_enable3=false,_enable4=false,_enable5=false,_enable6=false,_enable7=false,_enable8=false;
    SqliteTOjson sqliteTOjson=new SqliteTOjson(this);
    Query query=new Query(this);
    NetState net=new NetState(this);
    FieldDataBase fieldDataBase=new FieldDataBase();
    DateTime dt=new DateTime();
    List<Integer> valueid = new ArrayList<>();
    List<String> valuetext=new ArrayList<>();
    List<Integer> propertyid;
    List<String> namevalue;
    List<String> nameProperty;
    List<Integer> typeProperty;

    MessageDialog messageDialog=new MessageDialog(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        initViews();

        GetNameCity();
        GetNameCollection();

        //Radio Group

        //tv_product_price.addTextChangedListener(new MoneyTextWatcher(tv_product_price));
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
                Log.i("Sp_subset",Sp_subset.getSelectedItem().toString());
               getnameproperty(Sp_subset.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        HTTPGetProductPropertyJson httpGetProductPropertyJson=new HTTPGetProductPropertyJson(this);
        httpGetProductPropertyJson.execute();

        HTTPGetProductValueJson httpGetProductValueJson=new HTTPGetProductValueJson(this);
        httpGetProductValueJson.execute();

        HTTPGetProductSubsetPropertyJson httpGetProductSubsetPropertyJson=new HTTPGetProductSubsetPropertyJson(this);
        httpGetProductSubsetPropertyJson.execute();
    }

    public void product_save(View view)
    {
     /* try
      {*/
           name=tv_product_name.getText().toString();
           price=Double.parseDouble(tv_product_price.getText().toString());
           tell=tv_product_tell.getText().toString();
           email=tv_product_email.getText().toString();
           descripction=tv_product_desc.getText().toString();
           address=tv_product_address.getText().toString();
           subsetid=query.getsubsetProductID(Sp_subset.getSelectedItem().toString());
           areaid= query.getAreaID(tv_product_area.getText().toString());
           Log.i("areaid",String.valueOf(query.getAreaID(tv_product_area.getText().toString())));
           Log.i("areaname",String.valueOf(tv_product_area.getText().toString()));



          if(net.checkInternetConnection())
          {

              if(tv_product_name.getText().length()==0)
              {
                  messageDialog.ShowMessage("پیام","نام کالا را وارد کنید","باشه","false");
              }
              else if(tv_product_price.getText().length()==0){
                  messageDialog.ShowMessage("پیام","قیمت را وارد کنید","باشه","false");
              }
              else if(tv_product_price.getText().toString().substring(0,1).equals("0")){
                  messageDialog.ShowMessage("پیام","قیمت وارد شده صحیح نیست","باشه","false");
              }
              else if(subsetid==0){
                  messageDialog.ShowMessage("پیام","زیر گروه را انتخاب کنید","باشه","false");
              }
              else if(tv_product_email.getText().equals("") && tv_product_tell.getText().equals("")){
                  messageDialog.ShowMessage("پیام","ایمیل یا شماره تلفن را وارد کنید","باشه","false");
              }
              else  if(areaid==0){
                  messageDialog.ShowMessage("پیام","منطقه و شهر خود را انتخاب کنید","باشه","false");
              }

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


                  json = sqliteTOjson.ProductTOjson(query.getMemberId(), name, dt.Now(), property, price, latitude, longtiude, adaptive, descripction, tell, mobile, address, email, subsetid, areaid, valuetext, valueid, propertyid);
                  HTTPPostProductJson httpPostProductJson = new HTTPPostProductJson(this);
                  httpPostProductJson.SetProduct_Json(json);
                  httpPostProductJson.execute();
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

    public void initViews(){

        //
        tv_product_name=(EditText)findViewById(R.id.add_product_name);
        tv_product_price=(EditText)findViewById(R.id.add_product_price);
        tv_product_tell=(EditText)findViewById(R.id.add_product_tell);
        tv_product_email=(EditText)findViewById(R.id.add_product_email);
        tv_product_desc=(EditText)findViewById(R.id.add_product_desc);
        tv_product_address=(EditText)findViewById(R.id.add_product_address);
        tv_product_city=(AutoCompleteTextView)findViewById(R.id.ac_product_city);
        tv_product_area=(AutoCompleteTextView)findViewById(R.id.ac_product_area);
        Sp_collection = (Spinner)findViewById(R.id.sp_collection);
        Sp_subset = (Spinner)findViewById(R.id.sp_Subset);
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
       // cb_adaptive_product=(CheckBox)findViewById(R.id.chk_tavafoq);
        radioGroup=(RadioGroup)findViewById(R.id.radio_price);


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
            Log.e("ExceptionCity", e.toString());
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



    public void GetNameCollection()
    {
       // try {
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
        typeProperty=new ArrayList<Integer>();

        try {
        Cursor allrows  = db.select_SubsetProperty_Product(query.getsubsetProductID(namesubset));
        if (allrows.moveToFirst()) {
            do {
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
                    typeProperty.add(rows.getInt(1));
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
                            if (typeProperty.get(i)==2){
                                et_prop1.setInputType(InputType.TYPE_CLASS_NUMBER);
                            }
                            break;
                        case 1:
                            float2.setVisibility(View.VISIBLE);
                            et_prop2.setHint(nameProperty.get(i));
                            if (typeProperty.get(i)==2){
                                et_prop2.setInputType(InputType.TYPE_CLASS_NUMBER);
                            }
                            break;
                        case 2:
                            float3.setVisibility(View.VISIBLE);
                            et_prop3.setHint(nameProperty.get(i));
                            if (typeProperty.get(i)==2){
                                et_prop3.setInputType(InputType.TYPE_CLASS_NUMBER);
                            }
                            break;
                        case 3:
                            float4.setVisibility(View.VISIBLE);
                            et_prop4.setHint(nameProperty.get(i));
                            if (typeProperty.get(i)==2){
                                et_prop4.setInputType(InputType.TYPE_CLASS_NUMBER);
                            }
                            break;
                        case 4:
                            float5.setVisibility(View.VISIBLE);
                            et_prop5.setHint(nameProperty.get(i));
                            if (typeProperty.get(i)==2){
                                et_prop5.setInputType(InputType.TYPE_CLASS_NUMBER);
                            }
                            break;
                        case 5:
                            float6.setVisibility(View.VISIBLE);
                            et_prop6.setHint(nameProperty.get(i));
                            if (typeProperty.get(i)==2){
                                et_prop6.setInputType(InputType.TYPE_CLASS_NUMBER);
                            }
                            break;
                        case 6:
                            float7.setVisibility(View.VISIBLE);
                            et_prop7.setHint(nameProperty.get(i));
                            if (typeProperty.get(i)==2){
                                et_prop7.setInputType(InputType.TYPE_CLASS_NUMBER);
                            }
                            break;
                        case 7:
                            float8.setVisibility(View.VISIBLE);
                            et_prop8.setHint(nameProperty.get(i));
                            if (typeProperty.get(i)==2){
                                et_prop8.setInputType(InputType.TYPE_CLASS_NUMBER);
                            }
                            break;
                    }
                }

            }
        }
        catch (Exception e){}

        return nameProperty;
    }

}
