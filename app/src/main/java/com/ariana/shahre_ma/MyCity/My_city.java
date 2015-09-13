package com.ariana.shahre_ma.MyCity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.DateBaseSqlite.SelectDataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.MessageDialog;
import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.Settings.KeySettings;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetBusinessJson;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetBusinessJsonArray;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.software.shell.fab.ActionButton;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;
import java.util.List;



public class My_city extends ActionBarActivity{





    Query query;
    ListView myList;

    FieldClass fc=new FieldClass();

    Spinner Sp_City ;
    String cityname="";
    public static Download_dialog myDialog;
    public static HTTPGetBusinessJsonArray business;
    HTTPGetBusinessJson httpbusin;
    NetState ns;
    KeySettings setting =new KeySettings(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_city);
        query=new Query(this);
        Sp_City = (Spinner) findViewById(R.id.sp_city);
        httpbusin=new HTTPGetBusinessJson(this);
        ns=new NetState(this);
        SpinnerSetUp();
        fab();
        myList = (ListView) findViewById(R.id.my_city_list);
        My_City_Adapter adapter = new My_City_Adapter(this, generateData());
        myList.setAdapter(adapter);


        Sp_City.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cityname = Sp_City.getSelectedItem().toString();
                setting.saveCityName(cityname);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }




    void SpinnerSetUp(){

        SelectDataBaseSqlite sdb=new SelectDataBaseSqlite(this);
        Cursor allrows=sdb.select_AllCity();


        Sp_City.setPrompt("انتخاب شهر:");
        List<String> list = new ArrayList<String>();
        if(allrows.moveToFirst())
        {
            do
            {
                list.add(allrows.getString(1));

            }while (allrows.moveToNext());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Sp_City.setAdapter(dataAdapter);

    }

    public void download(View v){
        // download multiple Business
      //  try{

            if(ns.checkInternetConnection()) {
                Integer Result = 0;
                Integer i = 0;
                List<String> listurl = new ArrayList<String>();
                String url[] = new String[fc.GetNameSubset().size()];
                for (String name : fc.GetNameSubset()) {
                    url[i] = "http://test.shahrma.com/api/ApiGiveBusiness?subsetId=" + name + "&cityid=" + query.getCityId(cityname);
                    listurl.add("http://test.shahrma.com/api/ApiGiveBusiness?subsetId=" + name + "&cityid=" + query.getCityId(cityname));
                    Log.i("", "http://test.shahrma.com/api/ApiGiveBusiness?subsetId=" + name + "&cityid=" + query.getCityId(cityname));
                    i++;
                }

                if (cityname.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "شهر انتخاب نشده است", Toast.LENGTH_LONG).show();
                }
                else if(listurl.size() == 0)
                {
                    Toast.makeText(getApplicationContext(), "زیر مجموعه انتخاب نشده است", Toast.LENGTH_LONG).show();
                }
                else
                {
                    business = new HTTPGetBusinessJsonArray(this);
                    business.execute(url);
                    myDialog = new Download_dialog(this);
                    myDialog.show();
                }
            }else{
                MessageDialog messageDialog=new MessageDialog(this);
                messageDialog.ShowMessage("هشدار","اینترنت قطع می باشد","باشه","false");
            }

     /*   }catch (Exception e){

        }*/

    }

    public void fab () {
        ActionButton Action = (ActionButton) findViewById(R.id.download_fab);
        Action.setImageDrawable(getResources().getDrawable(R.drawable.download));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my_city, menu);

        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        ViewTarget Spinner=new ViewTarget(R.id.sp_city,this);
        //noinspection SimplifiableIfStatement
        RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lps.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        lps.addRule(RelativeLayout.CENTER_IN_PARENT);
        int margin = ((Number) (getResources().getDisplayMetrics().density * 55)).intValue();
        lps.setMargins(margin, margin, margin, margin);

        if (id == R.id.help) {

        ShowcaseView  sv=new ShowcaseView.Builder(this)
                   // .setTarget(new ActionViewTarget(this, ActionViewTarget.Type.HOME))
                    .setTarget(Spinner)
                    .setContentTitle("انتخاب شهر")
                    .setContentText("از این قسمت شهر مورد نظر خود را برای دانلود انتخاب کنید...")
                    .setStyle(R.style.CustomShowcaseTheme)
                    .build();
                    sv.setButtonPosition(lps);
            return true;
        }



        return super.onOptionsItemSelected(item);
    }
    public ArrayList<My_City_Items> generateData(){
        DataBaseSqlite db = new DataBaseSqlite(this);
        Cursor rows = db.select_Collection();
        java.util.ArrayList<My_City_Items> items = new ArrayList<My_City_Items>();
        if (rows.moveToFirst()) {
            do {
                items.add(new My_City_Items(rows.getString(1),rows.getInt(0)));

            } while (rows.moveToNext());
        }

        return items;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fc.GetNameSubset().clear();
        Log.i("Subset",String.valueOf(fc.GetNameSubset().size()));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        fc.GetNameSubset().clear();
        Log.i("Subset",String.valueOf(fc.GetNameSubset().size()));
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
