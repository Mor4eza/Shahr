package com.ariana.shahre_ma.MyBusiness;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
//    KeySettings setting=new KeySettings(getApplicationContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);


        tv_id=(TextView)findViewById(R.id.tv_dis_id);
         adapter = new discount_Adapter(this, generateData());
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);
        final int count = adapter.getCount();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    fc.SetId_DisCount(adapter.getItem(position).GetId());
                    Discount_Dialog dialog = new Discount_Dialog(Discount.this);
                    dialog.show();
                }catch (Exception e){

                }
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog alertDialog = new AlertDialog.Builder(Discount.this).create();
                alertDialog.setTitle("حذف!");
                alertDialog.setMessage("آیا میخواهید این تخفیف را حذف کنید؟!");
                alertDialog.setButton("آره", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        HTTPDeleteDisCountURL httpDeleteDisCountURL=new HTTPDeleteDisCountURL(Discount.this);
                        httpDeleteDisCountURL.SetDisCount(adapter.getItem(position).GetId());
                        httpDeleteDisCountURL.execute();
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
        SelectDataBaseSqlite sdb=new SelectDataBaseSqlite(this);
            Cursor rows = sdb.select_DisCountMember(fc.GetBusiness_Id());
            if (rows.moveToFirst()) {
                do {
                    items.add(new discount_item(" % " + rows.getString(6), rows.getString(5),rows.getString(4), rows.getString(1),rows.getString(3), rows.getInt(0)));

                } while (rows.moveToNext());
            }


        return items;
    }

    public void add_discount(View v)
    {
        /*String date="";
        String day="";
        String month="";
        String year="";


        SelectDataBaseSqlite sdb=new SelectDataBaseSqlite(this);
        Cursor rows = sdb.select_AllDisCountMember();
        if(rows.getCount()>0) {
            if (rows.moveToFirst()) {
                do {
                    date = rows.getString(4);
                } while (rows.moveToNext());
            }
        }
        else
        {
            Log.i("getCount","0");
            Discount_Dialog dialog=new Discount_Dialog(this);
            dialog.show();
            System.out.println("Date1 is before Date2");
        }


            try{

                year=date.substring(0, 4);
                month=date.substring(5, 7);
                day=date.substring(8, 10);

                Log.i("tarikh", ct.MiladiToMiladi(ct.getGregorianDate()));
                Log.i("tarikh1", year+"/"+month+"/"+day);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                Date date1 = sdf.parse(year+"/"+month+"/"+day);

                Date date2 = sdf.parse(ct.MiladiToMiladi(ct.getGregorianDate()));

                System.out.println(sdf.format("tarikh end"+ date1));
                System.out.println(sdf.format(ct.MiladiToMiladi(date2.toString())));

                if(date2.compareTo(date1)>0)
                {
                    Snackbar.make(listView, "تا زمانی که اتمام تاریخ فعلی به پایان نرسیده نمی توانید تخفیف جدید ثبت کنید", Snackbar.LENGTH_LONG).show();
                }
                else if(date1.compareTo(date2)<0)
                {
                    Discount_Dialog dialog=new Discount_Dialog(this);
                    dialog.show();
                    System.out.println("Date1 is before Date2");
                }
                else if(date1.compareTo(date2)==0)
                {
                    Snackbar.make(listView, "تا زمانی که اتمام تاریخ فعلی به پایان نرسیده نمی توانید تخفیف جدید ثبت کنید", Snackbar.LENGTH_LONG).show();
                }
                else
                {
                    Snackbar.make(listView, "تا زمانی که اتمام تاریخ فعلی به پایان نرسیده نمی توانید تخفیف جدید ثبت کنید", Snackbar.LENGTH_LONG).show();
                }

            }catch(Exception ex){
               Log.i("exceptioncopmaredate",ex.toString());
            }
*/

        Discount_Dialog dialog=new Discount_Dialog(this);
        dialog.show();

    }

}



