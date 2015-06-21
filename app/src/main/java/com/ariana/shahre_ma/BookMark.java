package com.ariana.shahre_ma;

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
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.WebServiceGet.SqliteTOjson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class BookMark extends ActionBarActivity {
    ListView lv;
    SqliteTOjson sqltojson =new SqliteTOjson(this);
    FieldClass fc = new FieldClass();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark);

        lv=(ListView) findViewById(R.id.lvbookmark);
        final ListView t = (ListView)findViewById(R.id.lvbookmark);
        t.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selected = (String) lv.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),selected,Toast.LENGTH_LONG).show();

                fc.SetMarket_Business(selected.toString());

            }
        });
        bookmark();

    }



    public void bookmark()
    {
        try
        {

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
                Cursor row = db.select_business_NameMarket(allrows.getInt(1));
                row.moveToNext();
                item.add(row.getString(0));

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

        Toast.makeText(getApplicationContext(),install().toString(),Toast.LENGTH_LONG).show();
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

    public String install(){
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
        } catch (FileNotFoundException e) {
            Log.v("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.v("login activity", "Can not read file: " + e.toString());
        }

        return ret;

    }
}
