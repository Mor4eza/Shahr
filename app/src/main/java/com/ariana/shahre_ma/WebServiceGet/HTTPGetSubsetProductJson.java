package com.ariana.shahre_ma.WebServiceGet;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldDataBase;
import com.ariana.shahre_ma.Settings.KeySettings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ariana on 8/18/2015.
 */
public class HTTPGetSubsetProductJson   extends AsyncTask<String,Void,Integer>
{
    private static final String url_subsetproduct="http://test.shahrma.com/api/apigiveProductsubset";

    FieldDataBase fieldDataBase=new FieldDataBase();
    List<Integer> selectId =new ArrayList<>();
    List<String>  selectName =new ArrayList<>();
    List<Integer>  selectCollectionId =new ArrayList<>();

    Integer len;
    private static Context context;
        /**
         *
         * @param c
         */
        public HTTPGetSubsetProductJson(Context c) {
            context = c;
        }

        /**
         *
         * @param args
         * @return
         */
        protected Integer doInBackground(String... args) {
            Integer result=0;
            try {


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
                if(result==1) {




                }
                else
                {

                }
            } catch (Exception e) {

            }
        }


        void parseJSON(String JSONString) {
            try {

                JSONArray areas = new JSONArray(JSONString);
                Log.i("JSONsubset", JSONString);

                len=areas.length();
                for (int i = 0; i < areas.length(); i++) {

                    JSONObject area = areas.getJSONObject(i);

                    selectId.add(area.getInt("Id"));
                    selectName.add(area.getString("Name"));
                    selectCollectionId.add(area.getInt("CollectionId"));
                }
                fieldDataBase.setId_Subset(selectId);
                fieldDataBase.setName_Subset(selectName);
                fieldDataBase.setCollectionId_Subset(selectCollectionId);
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
