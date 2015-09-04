package com.ariana.shahre_ma.WebServicePost;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.ariana.shahre_ma.Date.CalendarTool;
import com.ariana.shahre_ma.DateBaseSqlite.AddDataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.DeleteDataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.MyBusiness.Discount;
import com.ariana.shahre_ma.MyBusiness.discount_Adapter;
import com.ariana.shahre_ma.MyBusiness.discount_item;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetOneBusinessJson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.util.ArrayList;

/**
 * Created by ariana on 7/11/2015.
 */
public class HTTPPostDisCount extends AsyncTask<String,Void,Integer> {

    String urlDisCount="http://test.shahrma.com/api/ApiTakeDisCount";
    FieldClass fc=new FieldClass();
    ProgressDialog pd;
    Context context;
    String mesage;
    String jsonstring;
    Integer errorCode=0;

    public HTTPPostDisCount(Context context)
    {
        this.context=context;
    }

    /**
     *
     * @param json
     */
    public void SetDisCountJson(String json)
    {
         jsonstring = json;

    }

    private String GetJson()
    {
        return jsonstring;
    }


    /**
     *
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(context);
        pd.setMessage("در حال ثبت...");
        pd.setCancelable(false);
        pd.show();
    }

    /**
     *
     * @param params
     * @return
     */
    @Override
    protected Integer doInBackground(String... params) {
        Integer result=0;
        try
        {
            HttpClient httpClient=new DefaultHttpClient();
            HttpContext httpContext=new BasicHttpContext();
            HttpPost httpPost=new HttpPost(urlDisCount);
            StringEntity se=new StringEntity(GetJson(),"UTF-8");

            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            HttpResponse httpResponse=httpClient.execute(httpPost,httpContext);
            HttpEntity entity=httpResponse.getEntity();
            InputStream webs=entity.getContent();
            try
            {
                BufferedReader reader=new BufferedReader(new InputStreamReader(webs,"UTF-8"),8);
                mesage=(reader.readLine());
                webs.close();
                result=1;
            }
            catch (Exception e)
            {
                result=0;
                Log.e("Error in conversion", e.toString());
            }
        }
        catch (Exception e)
        {
            result=0;
        }
        return result;
    }



    /**
     *
     * @param result
     */
    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        if(result==1)
        {
            Log.i("JsonDisCount",GetJson());
            try {
                AddDataBaseSqlite adb = new AddDataBaseSqlite(context);
                DeleteDataBaseSqlite ddb=new DeleteDataBaseSqlite(context);
               // adb.Add_DisCount(discountid[i], discounttext[i], discountimage[i], discountstartdate[i], discountexpirationdate[i], discountdescription[i], discountpercent[i], discountbusinessid[i], likediscount[i], dislikediscount[i]);
                adb.Add_DisCountMember(Integer.parseInt(mesage), fc.GetText_DisCount(), fc.GetImage_DisCount(), fc.GetStartDate_DisCount(), fc.GetExpirationDate_DisCount(), fc.GetDescription_DisCount(), fc.GetPercent_DisCount(), fc.GetBusinessId_DisCount());
                pd.dismiss();


                Discount dis=new Discount();
                discount_Adapter adapter = new discount_Adapter(context,generateData());
                Discount.listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
            catch (Exception e)
            {
                pd.dismiss();
            }
        }
        else
        {
            pd.dismiss();

        }


    }

    public ArrayList<discount_item> generateData()
    {
        CalendarTool ct=new CalendarTool();
        CalendarTool ct1=new CalendarTool();
        ArrayList<discount_item> items = new ArrayList<discount_item>();
        DataBaseSqlite db = new DataBaseSqlite(context);


        Cursor rows = db.select_DisCountMember(fc.GetBusiness_Id());
        if (rows.moveToFirst()) {
            do {
                ct.setGregorianDate(Integer.valueOf(rows.getString(3).substring(0, 4)),Integer.valueOf(rows.getString(3).substring(5, 7)),Integer.valueOf(rows.getString(3).substring(8, 10)));
                ct1.setGregorianDate(Integer.valueOf(rows.getString(4).substring(0, 4)),Integer.valueOf(rows.getString(4).substring(5, 7)),Integer.valueOf(rows.getString(4).substring(8, 10)));
                items.add(new discount_item(" % " + rows.getString(6), rows.getString(5),ct1.getIranianDate(), rows.getString(1),ct.getIranianDate(), rows.getInt(0)));

            } while (rows.moveToNext());
        }


        return items;
    }
}
