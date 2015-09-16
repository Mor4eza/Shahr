package com.ariana.shahre_ma.Bazarche.WebServiceGet;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ariana.shahre_ma.DateBaseSqlite.AddDataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.DeleteDataBaseSqlite;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ariana on 9/6/2015.
 */
public class HTTPGetProductSubsetPropertyJson extends AsyncTask<String,Void,Integer>
{
    private static final String url_subsetproduct="http://test.shahrma.com/api/ApiGiveProductSubsetProperties";

    Integer errorCode=0;
    Integer Id[];
    Integer productsubsetid[];
    Integer propertyid[];
    Integer len;


    private static Context context;
    /**
     *
     * @param c
     */
    public HTTPGetProductSubsetPropertyJson(Context c) {
        context = c;
    }

    /**
     *
     * @param args
     * @return
     */
    protected Integer doInBackground(String... args) {
        Integer result=0;
        try
        {
            InputStream jsonStream = getStreamFromURL(url_subsetproduct, "GET");
            String jsonString = streamToString(jsonStream);
            parseJSON(jsonString);
            result=1;
        } catch (Exception e) {
            result=0;

        }
        return result;


    }

    @Override
    protected void onPostExecute(Integer result) {
        try {
            if(result==1)
            {
                    if(len>0) {

                        AddDataBaseSqlite adb = new AddDataBaseSqlite(context);
                        DeleteDataBaseSqlite ddb=new DeleteDataBaseSqlite(context);
                        adb.delete_SubsetProperty_Product();

                        for (int i = 0; i < len; i++) {
                            ddb.Add_SubsetProperty(Id[i], productsubsetid[i],propertyid[i]);

                        }
                    }

            }
            else
            {

            }
        } catch (Exception e)
        {

        }
    }


    void parseJSON(String JSONString) {
        try {

            JSONArray areas = new JSONArray(JSONString);
            Log.i("ProductSubsetProperty", JSONString);

            Id=new Integer[areas.length()];
            productsubsetid=new Integer[areas.length()];
            propertyid=new Integer[areas.length()];
            len=areas.length();
            for (int i = 0; i < areas.length(); i++) {

                JSONObject area = areas.getJSONObject(i);
                Id[i]=area.getInt("Id");
                productsubsetid[i]=area.getInt("ProductSubsetId");
                propertyid[i] = area.getInt("PropertyId");


               /*     selectId.add(area.getInt("Id"));
                    selectName.add(area.getString("Name"));
                    selectCollectionId.add(area.getInt("CollectionId"));*/
            }

                /*fieldDataBase.setId_Subset(selectId);
                fieldDataBase.setName_Subset(selectName);
                fieldDataBase.setCollectionId_Subset(selectCollectionId);*/


        } catch (JSONException e) {
        }
    }


    /**
     *
     * @param urlString
     * @param method
     * @return
     */
    InputStream getStreamFromURL(String urlString, String method) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setReadTimeout(10000);
            huc.setConnectTimeout(15000);
            huc.setRequestMethod(method);

            huc.setDoInput(true);

            huc.connect();
            errorCode=huc.getResponseCode();
            return huc.getInputStream();
        } catch (Exception e) {
            return null;
        }

    }

    /**
     *
     * @param is
     * @return
     */
    String streamToString(InputStream is) {
        String result = "";
        String line = null;

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                result = line + "\n";
            }
        } catch (Exception e) {
        }


        return result;
    }
}
