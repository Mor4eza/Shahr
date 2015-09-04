package com.ariana.shahre_ma.MyBusiness;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ariana.shahre_ma.Date.CalendarTool;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.DateBaseSqlite.SelectDataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetDisCountJson;
import com.ariana.shahre_ma.WebServiceSend.HTTPDeleteDisCountURL;

import java.util.ArrayList;

public class Discount extends ActionBarActivity {

    discount_Adapter adapter;
    FieldClass fc=new FieldClass();
    Query query =new Query(this);
    public static  ListView listView;
    TextView tv_id;
    CalendarTool ct=new CalendarTool();
    CalendarTool ct1=new CalendarTool();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);


        tv_id=(TextView)findViewById(R.id.tv_dis_id);
         adapter = new discount_Adapter(this, generateData());
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);
        final int count = adapter.getCount();

 /*       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    fc.SetId_DisCount(adapter.getItem(position).GetId());
                    Discount_Dialog dialog = new Discount_Dialog(Discount.this);
                    dialog.show();
                }catch (Exception e){

                }
            }
        });*/
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog alertDialog = new AlertDialog.Builder(Discount.this).create();
                alertDialog.setTitle("حذف!");
                alertDialog.setMessage("آیا میخواهید این تخفیف را حذف کنید؟!");
                alertDialog.setButton("آره", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            HTTPDeleteDisCountURL httpDeleteDisCountURL = new HTTPDeleteDisCountURL(Discount.this);
                            httpDeleteDisCountURL.SetDisCount(adapter.getItem(position).GetId());
                            httpDeleteDisCountURL.execute();
                        }
                        catch (Exception e){}
                    }
                });

                alertDialog.setButton2("نه", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();

                return false;
            }
        });

        try
        {
            // Getting DisCount Member
            HTTPGetDisCountJson httpGetDisCountJson = new HTTPGetDisCountJson(this);
            httpGetDisCountJson.seturl_DisCount(fc.GetBusiness_Id());// set Business id
            httpGetDisCountJson.execute();// run
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "کاربری وارد نشده", Toast.LENGTH_LONG).show();
        }

    }

    public ArrayList<discount_item> generateData()
    {
        ArrayList<discount_item> items = new ArrayList<discount_item>();
        SelectDataBaseSqlite sdb = new SelectDataBaseSqlite(this);
        try {
            Cursor rows = sdb.select_DisCountMember(fc.GetBusiness_Id());
            if (rows.moveToFirst()) {
                do {
                    ct.setGregorianDate(Integer.valueOf(rows.getString(3).substring(0, 4)),Integer.valueOf(rows.getString(3).substring(5, 7)),Integer.valueOf(rows.getString(3).substring(8, 10)));
                    ct1.setGregorianDate(Integer.valueOf(rows.getString(4).substring(0, 4)), Integer.valueOf(rows.getString(4).substring(5, 7)), Integer.valueOf(rows.getString(4).substring(8, 10)));
                    items.add(new discount_item(" % " + rows.getString(6), rows.getString(5),ct1.getIranianDate(), rows.getString(1), ct.getIranianDate(), rows.getInt(0)));

                } while (rows.moveToNext());
            }

        }
        catch (Exception e){}

        return items;
    }

    public void add_discount(View v)
    {
        fc.SetId_DisCount(0);
        Discount_Dialog dialog=new Discount_Dialog(this);
        dialog.show();

    }

}



