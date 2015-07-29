package com.ariana.shahre_ma.Bookmarks;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetBookMarkJson;
import com.ariana.shahre_ma.WebServiceGet.SqliteTOjson;
import com.ariana.shahre_ma.job_details.Job_details;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class BookMark extends ActionBarActivity {



    Integer BusinessID[];
    public static ListView lv;
    SqliteTOjson sqltojson =new SqliteTOjson(this);
    Query query=new Query(this);
    FieldClass fc = new FieldClass();
    NetState ns=new NetState(this);
    Integer len=0;
    Integer BusinessId[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark);
        lv=(ListView) findViewById(R.id.lvbookmark);

        final BookmarkAdapter adapter = new BookmarkAdapter(this, generateData());
        lv.setAdapter(adapter);
       // bookmark();//Load data from the database in the list view
        if(ns.checkInternetConnection())
        {
            //The user Getting bookmark
            HTTPGetBookMarkJson b = new HTTPGetBookMarkJson(this);
            b.SetUrl_MemberId(query.getMemberId());
            b.execute();
        }



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fc.SetMarket_Business(adapter.getItem(position).getTitle().toString());
                fc.SetBusiness_Id(adapter.getItem(position).GetId());
                Intent i=new Intent(getApplicationContext(), Job_details.class);
                startActivity(i);
            }
        });

    }



    public void bookmark()
    {
        try
        {
        lv=(ListView) findViewById(R.id.lvbookmark);
           ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getbookmark());

        lv.setAdapter(adapter);

        }
        catch (Exception e) {
        }
    }

    private List<String> getbookmark() {
        List<String> item=new ArrayList<String>();
        DataBaseSqlite db=new DataBaseSqlite(this);
        Cursor allrows = db.select_bookmark();


        if(allrows.moveToFirst())
        {
            do
            {
                Log.i("BusinessIdBookmark",String.valueOf(allrows.getInt(1)));
                Cursor row = db.select_business_NameMarket(allrows.getInt(1));
                row.moveToNext();
                item.add(row.getString(0));
                Log.i("nameBookmark", row.getString(0));

            }while (allrows.moveToNext());

        }
        allrows.close();


        return item;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_mark, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.share) {
            Toast.makeText(getApplication(), "اشتراک گذاری", Toast.LENGTH_LONG).show();
            shareData();
            return true;
        }else if(id == R.id.install){
            install();

        }


        return super.onOptionsItemSelected(item);
    }

    public void shareData() {
        SqliteTOjson sqliteTOjson = new SqliteTOjson(this);
        sqliteTOjson.getSqliteBookmarkTOjson();
        File root = android.os.Environment.getExternalStorageDirectory();
        File dir = new File (root.getAbsolutePath() + "/myFolder");
        String fileName = "bookmarks.book";
        File file = new File(dir, fileName);
        Uri outputFileUri = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setPackage("com.android.bluetooth");
        intent.setType("text/*");
        intent.putExtra(Intent.EXTRA_STREAM, outputFileUri);
        startActivity(Intent.createChooser(intent, "ارسال با بلوتوث"));
    }

    public void install(){
        String ret = "";
        File root = android.os.Environment.getExternalStorageDirectory();
        File dir = new File(root.getAbsolutePath() + "/myFolder");
        File file = new File(dir, "bookmarks.book");

        try {

            FileInputStream inputStream = new FileInputStream(file);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }

            SaveBookmarJsonToSqlite(ret);
        } catch (FileNotFoundException e) {
            Log.v("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.v("login activity", "Can not read file: " + e.toString());
        }




    }

    public void SaveBookmarJsonToSqlite(String json1)
    {

        try {
            JSONArray array=new JSONArray(json1);
            BusinessID=new Integer[array.length()];
            len=array.length();
            for (int i = 0; i < array.length(); i++) {
                JSONObject json =array.getJSONObject(i);
                BusinessID[i] = json.getInt("BusinessId");
            }

            DataBaseSqlite db=new DataBaseSqlite(this);
            for (int i = 0; i <len; i++) {
                db.Add_bookmark(BusinessID[i],query.getMemberId());
            }
        }
        catch (Exception e){}
    }
    public ArrayList<Bookmark_Item> generateData(){
        ArrayList<Bookmark_Item> items = new ArrayList<Bookmark_Item>();

        DataBaseSqlite db=new DataBaseSqlite(this);
        Cursor rows=db.select_bookmark();
        if(rows.moveToNext())
        {
            do {
                items.add(new Bookmark_Item(query.getNameBusiness(rows.getInt(1)),rows.getInt(1)));
            }while (rows.moveToNext());
        }


        return items;
    }

}
