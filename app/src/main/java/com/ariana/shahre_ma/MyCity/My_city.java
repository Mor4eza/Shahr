package com.ariana.shahre_ma.MyCity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetBusinessJson;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetBusinessJsonArray;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.software.shell.fab.ActionButton;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



public class My_city extends ActionBarActivity implements TotalListener{





    Query query;
    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> laptopCollection;
    ExpandableListView expListView;
    int lastExpandedPosition = -1;

    Integer Id_co;
    Integer Collection_ID_subset;

    FieldClass fc=new FieldClass();

    Spinner Sp_City ;
    String cityname="";

    HTTPGetBusinessJson httpbusin;
    NetState ns;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_city);
        query=new Query(this);

        Sp_City = (Spinner) findViewById(R.id.sp_city);

        httpbusin=new HTTPGetBusinessJson(this);
        ns=new NetState(this);

        createCollection();

        SpinnerSetUp();
        fab();

        expListView = (ExpandableListView) findViewById(R.id.expand_my_city);

        ExpandListAdapter adapter = new ExpandListAdapter(this);
        adapter.setmListener(this);
        expListView.setAdapter(adapter);


        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    expListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });


        Sp_City.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
             cityname= Sp_City.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }



    private void createCollection() {

        Boolean f=true;

        try {

            SQLiteDatabase mydb = openOrCreateDatabase(fc.GetDataBaseName(), Context.MODE_PRIVATE, null);
            Cursor allrows_Collection = mydb.rawQuery("SELECT * FROM " + fc.GetTableNameCollection(), null);
            Cursor allrows_Subset = mydb.rawQuery("SELECT * FROM " + fc.GetTableNameSubset(), null);
            groupList = new ArrayList<String>();


            String laptop="";
            laptopCollection = new LinkedHashMap<String, List<String>>();

            if (allrows_Collection.moveToFirst()) {
                do {
                    childList = new ArrayList<String>();
                    Id_co = allrows_Collection.getInt(0);

                    groupList.add(allrows_Collection.getString(1));
                    laptop=allrows_Collection.getString(1);
                    if (allrows_Subset.moveToFirst())
                    {
                        do {

                            Collection_ID_subset = allrows_Subset.getInt(2);


                            if (Collection_ID_subset == Id_co)
                            {
                                childList.add(allrows_Subset.getString(1));
                            }

                        } while (allrows_Subset.moveToNext());

                    }

                    laptopCollection.put(laptop,childList);

                } while (allrows_Collection.moveToNext());
            }

            mydb.close();
        }
        catch (Exception e){ Toast.makeText(getBaseContext(),e.toString(), Toast.LENGTH_LONG).show();}



    }


    private void setGroupIndicatorToRight() {
		/* Get the screen width */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        expListView.setIndicatorBounds(width - getDipsFromPixel(100), width
                - getDipsFromPixel(5));
    }

    // Convert pixel to dip
    public int getDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 5f);
    }




    void SpinnerSetUp(){

        DataBaseSqlite db=new DataBaseSqlite(this);
        Cursor allrows=db.select_AllCity();


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
       Integer Result = 0;
        Integer i=0;
        DataBaseSqlite db=new DataBaseSqlite(this);
        List<String> listurl=new ArrayList<String>();
        String url[]=new String[fc.GetNameSubset().size()];
        for(String name:fc.GetNameSubset()){

            Cursor cursor=db.select_SubsetId(name);
            cursor.moveToFirst();
            Result=cursor.getInt(0);
            url[i]="http://test.shahrma.com/api/ApiGiveBusiness?subsetId="+Result+"&cityid="+query.getCityId(cityname);
            listurl.add("http://test.shahrma.com/api/ApiGiveBusiness?subsetId=" + Result + "&cityid="+query.getCityId(cityname));
            Log.i("", "http://test.shahrma.com/api/ApiGiveBusiness?subsetId=" + Result + "&cityid="+query.getCityId(cityname));
            i++;
        }

        if(cityname.equals(""))
        {
            Toast.makeText(getApplicationContext(),"شهر مورد نظر خود را انتخاب کنید ",Toast.LENGTH_LONG).show();
        }
        else
        {
            HTTPGetBusinessJsonArray business = new HTTPGetBusinessJsonArray(this);
            business.execute(url);
        }


        Toast.makeText(getApplicationContext(),"download",Toast.LENGTH_LONG).show();

      Download_dialog dialog=new Download_dialog(this);
        dialog.show();

    }

    public void fab () {
        ActionButton Action = (ActionButton) findViewById(R.id.download_fab);
        Action.setButtonColor(getResources().getColor(R.color.fab_material_blue_500));
        Action.setImageDrawable(getResources().getDrawable(R.drawable.download));
    }

    @Override
    public void onTotalChanged(int sum) {

    }

    @Override
    public void expandGroupEvent(int groupPosition, boolean isExpanded) {
        if(isExpanded)
            expListView.collapseGroup(groupPosition);
        else
            expListView.expandGroup(groupPosition);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_city, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        ViewTarget Spinner=new ViewTarget(R.id.sp_city,this);
        //noinspection SimplifiableIfStatement
        RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lps.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        int margin = ((Number) (getResources().getDisplayMetrics().density * 12)).intValue();
        lps.setMargins(margin, margin, margin, margin);

        if (id == R.id.help) {

        ShowcaseView  sv=new ShowcaseView.Builder(this)
                   // .setTarget(new ActionViewTarget(this, ActionViewTarget.Type.HOME))
                    //.setTarget(Spinner)
                    .setContentTitle("انتخاب شهر")
                    .setContentText("از این قسمت شهر مورد نظر خود را برای دانلود انتخاب کنید...")
                    .setStyle(R.style.CustomShowcaseTheme)
                    .build();
                    sv.setButtonPosition(lps);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
