package com.ariana.shahre_ma.WebServiceGet;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONObject;

/**
 * Created by ariana2 on 6/5/2015.
 */
public class SqliteTOjson
{

    private  static Context context;

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
    public String getSqliteTOjson(String j_name,String j_email,String j_mobile,Integer j_age,Boolean j_sex,String j_username,String j_password,Integer j_cityid) {
        String field_Json="";
        try {

          // JSONobject get key/value convert to json
            JSONObject rowObject = new JSONObject();
                                    rowObject.put("Id",1);
                                    rowObject.put("Name",j_name);
                                    rowObject.put("Mobile",j_mobile);
                                    rowObject.put("Email",j_email);
                                    rowObject.put("Age",j_age);
                                    rowObject.put("Sex",j_sex);
                                    rowObject.put("UserName",j_username);
                                    rowObject.put("Password",j_password);
                                    rowObject.put("CityId",j_cityid);
            field_Json=rowObject.toString();

        }
        catch (Exception e){
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();}
       return  field_Json;

    }

    // convert opinion to json
    public String getOpinionTOjson( String description,String date,Integer opiniontype,Integer erja) {
        String field_Json="";
        try {

            // JSONobject get key/value convert to json
            JSONObject rowObject = new JSONObject();
            rowObject.put("Id",1);
            rowObject.put("Description",description);
            rowObject.put("Date",date);
            rowObject.put("OpinionType",opiniontype);
            rowObject.put("ErJa",erja);

            field_Json=rowObject.toString();

        }
        catch (Exception e){
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();}
        return  field_Json;

    }

}
