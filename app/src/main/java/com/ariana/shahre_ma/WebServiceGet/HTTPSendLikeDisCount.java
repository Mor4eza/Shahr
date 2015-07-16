package com.ariana.shahre_ma.WebServiceGet;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.job_details.job_details_discount;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by ariana on 7/12/2015.
 */
public class HTTPSendLikeDisCount extends AsyncTask<String,Void,Boolean>
{
    ProgressDialog pd;
    String mesage;
    Context context;
    Boolean like;
    Integer discountid;
    Integer memberid;
    Integer businessid;

    Integer likecount=0;
    Integer dislikecount=0;

    public HTTPSendLikeDisCount(Context context){this.context=context;}
    public void SetBusinessid(Integer businessid)
    {
        this.businessid=businessid;
    }
    public void SetDiscountid(Integer discountid)
    {
        this.discountid=discountid;
    }
    public void SetMemberid(Integer memberid)
    {
        this.memberid=memberid;
    }



    public void SetLike(Boolean like)
    {
        this.like=like;
    }

    public String GetURL()
    {
        String url="http://test.shahrma.com/api/ApiTakeLike?erjaId="+discountid+"&erjaType=1&memberId="+memberid+"&value="+like;
        Log.i("URLLikeDisCount", url);
        return url;

    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd=new ProgressDialog(context);
        pd.setMessage("ثبت...");
        pd.setCancelable(false);
        pd.show();
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try
        {
            HttpGet httpget=new HttpGet(GetURL());
            HttpClient httpClient=new DefaultHttpClient();
            HttpResponse httpResponse=httpClient.execute(httpget);

            int status=httpResponse.getStatusLine().getStatusCode();
            if(status==200)
            {
                HttpEntity en=httpResponse.getEntity();
                String data= EntityUtils.toString(en);
                 mesage = data;
                return true;
            }


        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    private void parsJSON(String json)
    {
        try {

            JSONObject jsonObject = new JSONObject(json);
            likecount=jsonObject.getInt("LikeCount");
            dislikecount=jsonObject.getInt("DisLikeCount");

        }
        catch (Exception e){

        }
    }
    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        DataBaseSqlite db=new DataBaseSqlite(context);
        Log.i("JSONlikeDisCount",mesage);
        if(aBoolean==true)
        {
            db.delete_LikeDisCount(memberid, discountid, businessid);
            parsJSON(mesage);


                db.Update_DisCount(discountid,businessid,likecount,dislikecount);
                db.Add_LikeDisCount(like, memberid, discountid, businessid);


            job_details_discount.PlaceholderFragment.tv_like.setText(likecount.toString());
            job_details_discount.PlaceholderFragment.tv_unlike.setText(dislikecount.toString());
            pd.dismiss();

        }
        else
        {
            pd.dismiss();
        }
    }
}
