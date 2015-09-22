package com.ariana.shahre_ma.WebServiceGet;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.DateBaseSqlite.SelectDataBaseSqlite;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by ariana2 on 6/5/2015.
 */
public class SqliteTOjson {

    private static Context context;


    public  SqliteTOjson(Context context)
    {
        this.context=context;
    }

    // convert Member to json
    public String getSqliteTOjson(String j_name, String j_email, String j_mobile, Integer j_age, Boolean j_sex, String j_username, String j_password, Integer j_cityid) {
        String field_Json = "";
        try {
            Query query=new Query(context);
            // JSONobject get key/value convert to json
            JSONObject rowObject = new JSONObject();
            rowObject.put("Id",query.getMemberId());
            rowObject.put("Name", j_name);
            rowObject.put("Mobile", j_mobile);
            rowObject.put("Email", j_email);
            rowObject.put("Age", j_age);
            rowObject.put("Sex", j_sex);
            rowObject.put("UserName", j_username);
            rowObject.put("Password", j_password);
            rowObject.put("CityId", j_cityid);
            field_Json = rowObject.toString();

        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }
        return field_Json;

    }


    // convert opinion to json
    public String getImageUploadTOjson(Integer businessid,Integer type,String image) {
        String field_Json = "";
        try {

            // JSONobject get key/value convert to json
            JSONObject rowObject = new JSONObject();
            rowObject.put("Id",businessid);
            rowObject.put("Type", type);
            rowObject.put("Image", image);


            field_Json = rowObject.toString();


        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }

        return field_Json;
    }


    // convert opinion to json
    public String getOpinionTOjson(String description, String date, Integer memberId, Integer erja) {
        String field_Json = "";
        try {

            // JSONobject get key/value convert to json
            JSONObject rowObject = new JSONObject();
            rowObject.put("Id",1);
            rowObject.put("Description", description);
            rowObject.put("Date", date);
            rowObject.put("memberId", memberId);
            rowObject.put("OpinionType", 1);
            rowObject.put("ErJa", erja);

            field_Json = rowObject.toString();


        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }

        return field_Json;
    }


    // convert DisCount to json
    public String getDisCountTOjson(Integer id, String text,String image,String startdate,String expirationdate,String description,String percent, Integer businessid) {
        String field_Json = "";
        try {

            // JSONobject get key/value convert to json
            JSONObject rowObject = new JSONObject();
            rowObject.put("Id",id);
            rowObject.put("Text", text);
            rowObject.put("Image", image);
            rowObject.put("Startdate", startdate);
            rowObject.put("ExpirationDate", expirationdate);
            rowObject.put("Description", description);
            rowObject.put("Percent", percent);
            rowObject.put("BusinessId",businessid);

            field_Json = rowObject.toString();

        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }

        return field_Json;
    }

    public  String getBusinessTOjson(Integer id,String market,String phone,String mobile,String fax,String email,String businessowner,String address,String description,String startdate,String expirationdate,String subset,Integer subsetid,Double latitude,Double longitude,Integer areaid,String area,String user,Integer memberid,Integer field1,Integer field2,Integer field3,Integer field4,Integer field5,Integer field6,Integer field7)
    {
        String Result_json="";
        try{
            JSONObject jsonobject=new JSONObject();


            jsonobject.put("Id",String.valueOf(id));
            jsonobject.put("Market",market);
            jsonobject.put("Phone",phone);
            jsonobject.put("Mobile",mobile);
            jsonobject.put("Fax",fax);
            jsonobject.put("Email",email);
            jsonobject.put("BusinessOwner",businessowner);
            jsonobject.put("Address",address);
            jsonobject.put("Description",description);
            jsonobject.put("Startdate",startdate);
            jsonobject.put("ExpirationDate",expirationdate);
            jsonobject.put("Inactive",true);
            /*jsonobject.put("Subset",JSONObject.NULL);*/
            jsonobject.put("SubsetId",subsetid);
            jsonobject.put("Longitude",String.valueOf(longitude));
            jsonobject.put("Latitude",String.valueOf(latitude));
            jsonobject.put("AreaId",areaid);
           /* jsonobject.put("Area",JSONObject.NULL);
            jsonobject.put("User",JSONObject.NULL);*/
            jsonobject.put("UserId",1);
            jsonobject.put("MemberId",memberid);
            jsonobject.put("Field1",String.valueOf(field1));
            jsonobject.put("Field2",String.valueOf(field2));
            jsonobject.put("Field3",String.valueOf(field3));
            jsonobject.put("Field4",String.valueOf(field4));
            jsonobject.put("Field5",String.valueOf(field5));
            jsonobject.put("Field6",String.valueOf(field6));
            jsonobject.put("Field7",String.valueOf(field7));


            Result_json=jsonobject.toString();
        }
        catch (Exception e)
        {

        }
        return Result_json;
    }

    public  String getBusinessTOjsonArray(Integer id,Integer memberId,String market,String phone,String mobile,String fax,String email,String businessowner,String address,String description,String startdate,String expirationdate,String subset,Integer subsetid,Double latitude,Double longitude,Integer areaid,String area,String user,Integer memberid,String field1)
    {
        String Result_json="";
        try{
            JSONObject jsonobject=new JSONObject();

            jsonobject.put("Id",String.valueOf(id));
            jsonobject.put("MemberId",memberId);
            jsonobject.put("Market",market);
            jsonobject.put("Phone",phone);
            jsonobject.put("Mobile",mobile);
            jsonobject.put("Fax",fax);
            jsonobject.put("Email",email);
            jsonobject.put("BusinessOwner",businessowner);
            jsonobject.put("Address",address);
            jsonobject.put("Description",description);
            jsonobject.put("Startdate",startdate);
            jsonobject.put("ExpirationDate",expirationdate);
            jsonobject.put("Inactive",true);
            /*jsonobject.put("Subset",JSONObject.NULL);*/
            jsonobject.put("SubsetId",subsetid);
            jsonobject.put("Longitude",String.valueOf(longitude));
            jsonobject.put("Latitude",String.valueOf(latitude));
            jsonobject.put("AreaId",areaid);
           /* jsonobject.put("Area",JSONObject.NULL);
            jsonobject.put("User",JSONObject.NULL);*/
            jsonobject.put("MemberId",memberid);

            jsonobject.put("UserId",1);
            jsonobject.put("FieldText",String.valueOf(field1));

            Result_json=jsonobject.toString();
        }
        catch (Exception e)
        {

        }
        return Result_json;
    }
    // convert Interest to json


    public String getSqliteInterestTOjson() {

        String Sqlite_Json="";
        SelectDataBaseSqlite sdb = new SelectDataBaseSqlite(context);
        Cursor cursor = sdb.select_Interest();
        JSONArray resultSet = new JSONArray();


        try {

            //Log.i("SQLITEtoJSON",String.valueOf(cursor.getInt(1)));
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {

                int totalColumn = cursor.getColumnCount();
                JSONObject rowObject = new JSONObject();

                for (int i = 0; i < totalColumn; i++)
                {
                    if (cursor.getColumnName(i) != null)
                    {
                        try
                        {
                                rowObject.put(cursor.getColumnName(i), cursor.getInt(i));
                        } catch (Exception e) {
                            //  Log.d("TAG_NAME", e.getMessage()  );
                        }
                    }
                }
                resultSet.put(rowObject);
                // ja.put(rowObject);
                cursor.moveToNext();
            }
            cursor.close();
            Sqlite_Json = resultSet.toString();


            // Log.d("TAG_NAME", resultSet.toString() );
            //Toast.makeText(getApplicationContext(), resultSet.toString(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.i("SQLITEtoJSON",e.toString());
        }


         return Sqlite_Json;
    }


    public String getSqliteBookmarkTOjson() {
        String Sqlite_Json="";
        DataBaseSqlite db = new DataBaseSqlite(context);
        Cursor cursor = db.select_bookmark();
        JSONArray resultSet = new JSONArray();
        try {

            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {

                int totalColumn = cursor.getColumnCount();
                JSONObject rowObject = new JSONObject();

                for (int i = 0; i < totalColumn; i++)
                {
                    if (cursor.getColumnName(i) != null)
                    {
                        try {


                            rowObject.put(cursor.getColumnName(i), cursor.getInt(i));




                        } catch (JSONException e) {
                             Log.d("TAG_NAME", e.getMessage()  );
                        }
                    }
                }
                resultSet.put(rowObject);
                // ja.put(rowObject);
                cursor.moveToNext();
            }
            cursor.close();
            Sqlite_Json = resultSet.toString();
            writeToFile(Sqlite_Json);
        } catch (Exception e) {
            Log.i("SQLITEtoJSON",e.toString());
        }

        return Sqlite_Json;
    }

    // convert Prooduct to json
    public String ProductTOjson(Integer memberid,String name,String datetime, String property, Double price, Double latitude, Double longtiude, Boolean adaptive, String description,String phone,String mobile,String address,String email,Integer subsetid, Integer areaid,List<String> valuetext,List<Integer> valueid,List<Integer> propertyid) {
        String field_Json = "";
        try {

            // JSONobject get key/value convert to json
            JSONObject rowObject = new JSONObject();

            JSONArray array=new JSONArray();

            rowObject.put("MemberId",memberid);
            rowObject.put("Name", name);
            rowObject.put("DateTime", datetime);
            rowObject.put("Property", property);
            rowObject.put("Price", price);
            rowObject.put("Latitude", latitude);
            rowObject.put("Longtiude", longtiude);
            rowObject.put("Adaptive", adaptive);
            rowObject.put("Description", description);
            rowObject.put("Phone", phone);
            rowObject.put("Mobile", mobile);
            rowObject.put("Address", address);
            rowObject.put("Email", email);
            rowObject.put("ProductSubsetId", subsetid);
            rowObject.put("AreaId", areaid);

            Log.i("valueSize", String.valueOf(valueid.size()));
            Log.i("valuestextsize", String.valueOf(valuetext.size()));
            for(int i=0;i<propertyid.size();i++)
            {
                JSONObject json = new JSONObject();
                json.put("PropertyId", propertyid.get(i));
                json.put("ProductId", 0);
                if(valueid.get(i).equals(0)) {
                    json.put("Value", valuetext.get(i));
                    Log.i("valuetext", String.valueOf(valuetext.get(i)));
                }
                else {
                    json.put("Value", valueid.get(i));
                }
                array.put(json);

            }
            Log.i("jsonSendProduct",array.toString());
            rowObject.put("ProductProperties",array);
           // array.put(rowObject);

            field_Json = rowObject.toString();

        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }
        return field_Json;

    }
    //Filter TO JSon
    public String FilterTOjson(String search,Integer CityId, Double price1,Double price2, Boolean adaptive,Integer subsetid, Integer areaid,List<String> valuetext,List<Integer> valueid,List<Integer> propertyid) {
        String field_Json = "";
        try {

            // JSONobject get key/value convert to json
            JSONObject rowObject = new JSONObject();

            JSONArray array=new JSONArray();

            rowObject.put("Search", search);
            rowObject.put("Price1", price1);
            rowObject.put("Price2", price2);
            rowObject.put("Adaptive", adaptive);
            rowObject.put("ProductSubsetId", subsetid);
            rowObject.put("AreaId", areaid);
            rowObject.put("CityId", CityId);
            Log.i("valueSize", String.valueOf(valueid.size()));
            Log.i("valuestextsize", String.valueOf(valuetext.size()));
            for(int i=0;i<propertyid.size();i++)
            {
                JSONObject json = new JSONObject();
                json.put("PropertyId", propertyid.get(i));
                json.put("ProductId", 0);
                if(valueid.get(i).equals(0)) {
                    json.put("Value", valuetext.get(i));
                    Log.i("valuetext", String.valueOf(valuetext.get(i)));
                }
                else {
                    json.put("Value", valueid.get(i));
                }
                array.put(json);

            }
            Log.i("jsonSendProduct",array.toString());
            rowObject.put("ProductProperties",array);
            // array.put(rowObject);

            field_Json = rowObject.toString();

        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }
        return field_Json;

    }




    private void writeToFile(String Json) {
        File root = android.os.Environment.getExternalStorageDirectory();

        File dir = new File(root.getAbsolutePath() + "/myFolder");
        dir.mkdirs(); // build directory
        File file = new File(dir, "bookmarks.book");

        try {
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(f);

            pw.println(Json);

            pw.flush();
            pw.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
