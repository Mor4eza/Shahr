package com.ariana.shahre_ma.WebServiceGet;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by ariana2 on 6/5/2015.
 */
public class SqliteTOjson {

    private static Context context;


    public  SqliteTOjson(Context context)
    {
        this.context=context;
    }
    /*public void getSqliteTOjson() {
        try {


            String myPath = DATABASE_NAME;// Set path to your database

            String myTable = TABLE_NAME_Business;//Set name of your table
            SQLiteDatabase myDataBase = openOrCreateDatabase(myPath, Context.MODE_PRIVATE,null);
            //SQLiteDatabase myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

            String searchQuery = "SELECT  * FROM " + myTable;
            Cursor cursor = myDataBase.rawQuery(searchQuery, null);

            JSONArray resultSet = new JSONArray();

            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {

                int totalColumn = cursor.getColumnCount();
                JSONObject rowObject = new JSONObject();

                for (int i = 0; i < totalColumn; i++) {
                    if (cursor.getColumnName(i) != null) {
                        try {
                            if (cursor.getString(i) != null) {
                                //  Log.d("TAG_NAME", cursor.getString(i) );
                                if(i==12)
                                    rowObject.put(cursor.getColumnName(i),Boolean.parseBoolean(cursor.getString(i)));
                                else if(i==14)
                                    rowObject.put(cursor.getColumnName(i), cursor.getInt(i));
                                else if(i==17)
                                    rowObject.put(cursor.getColumnName(i), cursor.getInt(i));
                                else if(i==20)
                                    rowObject.put(cursor.getColumnName(i), cursor.getInt(i));
                                else
                                    rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                            } else {
                                rowObject.put(cursor.getColumnName(i),"");
                            }
                        } catch (Exception e) {
                            //  Log.d("TAG_NAME", e.getMessage()  );
                        }
                    }
                }
                resultSet.put(rowObject);
                ja.put(rowObject);
                cursor.moveToNext();
            }
            cursor.close();
            Sqlite_Json=resultSet.toString();
            EditText txtMarket=(EditText) findViewById(R.id.txtMarket);
            // txtMarket.setText(Sqlite_Json.toString());
            // Log.d("TAG_NAME", resultSet.toString() );
            //Toast.makeText(getApplicationContext(), resultSet.toString(), Toast.LENGTH_LONG).show();
        }
        catch (Exception e){Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();}


    }*/


    // convert Member to json
    public String getSqliteTOjson(String j_name, String j_email, String j_mobile, Integer j_age, Boolean j_sex, String j_username, String j_password, Integer j_cityid) {
        String field_Json = "";
        try {

            // JSONobject get key/value convert to json
            JSONObject rowObject = new JSONObject();
            rowObject.put("Id", 1);
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
    public String getOpinionTOjson(String description, String date, Integer memberId, Integer erja) {
        String field_Json = "";
        try {

            // JSONobject get key/value convert to json
            JSONObject rowObject = new JSONObject();
            rowObject.put("Id", 1);
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


    public  String getBusinessTOjson(Integer id,String market,String phone,String mobile,String fax,String email,String businessowner,String address,String description,String startdate,String expirationdate,String inactive,String subset,Integer subsetid,String longitude,String latitude,Integer areaid,String area,String user,Integer field1,Integer field2,Integer field3,Integer field4,Integer field5,Integer field6,Integer field7)
    {
        String Result_json="";
        try{
            JSONObject jsonobject=new JSONObject();
            jsonobject.put("Id",id);
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
            jsonobject.put("Inactive",inactive);
            jsonobject.put("Subset",subset);
            jsonobject.put("SubsetId",subsetid);
            jsonobject.put("Longitude",longitude);
            jsonobject.put("Latitude",latitude);
            jsonobject.put("AreaId",areaid);
            jsonobject.put("Area",area);
            jsonobject.put("User",user);
            jsonobject.put("Field1",field1);
            jsonobject.put("Field2",field2);
            jsonobject.put("Field3",field3);
            jsonobject.put("Field4",field4);
            jsonobject.put("Field5",field5);
            jsonobject.put("Field6",field6);
            jsonobject.put("Field7",field7);

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
        DataBaseSqlite db = new DataBaseSqlite(context);
        Cursor cursor = db.select_Interest();
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
