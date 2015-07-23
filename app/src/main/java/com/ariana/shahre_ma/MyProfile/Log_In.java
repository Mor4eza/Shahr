package com.ariana.shahre_ma.MyProfile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ariana.shahre_ma.ImageDownload.ImageLoader;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetCityJson;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetLoginJson;

import java.net.URLEncoder;


public class Log_In extends ActionBarActivity {


    EditText username;
    EditText password;
    TextView error;
    private ImageLoader imgLoader;
    String mesage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log__in);

        username = (EditText) findViewById(R.id.et_username);
        password = (EditText) findViewById(R.id.et_password);
        error = (TextView) findViewById(R.id.tverror);

        HTTPGetCityJson httpcity = new HTTPGetCityJson(this);
        httpcity.execute();
     /*   try {
            DataBaseSqlite db = new DataBaseSqlite(this);
            Cursor allrows = db.select_Member();
            allrows.moveToNext();
            username.setText(allrows.getString(6));
            password.setText(allrows.getString(7));

        } catch (Exception e) {
        }*/

        ImageView headimage=(ImageView) findViewById(R.id.imagelogin);
        imgLoader=new ImageLoader(this);

        String image_url_1 = "https://www.taxitronic.org/emeter/img/Login-background.png";
        imgLoader.DisplayImage(image_url_1, headimage);
    }

    public void register(View v) {

        Intent i = new Intent(getApplicationContext(), Sign_Up.class);
        startActivity(i);

    }
    public void forget(View v){
    Forget_Dialog dialog=new Forget_Dialog(this);
        dialog.show();


    }

    public void Click_Login(View v) {


        //Code Login
        if (username.getText().toString() == null || password.getText().toString() == null) {

            error.setText("نام کاربری و رمز عبور را وارد کنید");
        } else {
            try {
                    String nameuser = URLEncoder.encode(username.getText().toString(), "UTF-8");
                    String passworduser = URLEncoder.encode(password.getText().toString(), "UTF-8");
                    final String url = "http://test.shahrma.com/api/ApiGiveMembersPermission?userName=" + nameuser + "&Password=" + passworduser;
                     new HTTPGetLoginJson(this).execute(url);

            } catch (Exception e)
            {
                Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_LONG).show();
            }
        }

    }


  /*  class HTTPGetLoginJson extends AsyncTask<String, Void, Integer> {


        Integer ID = 0;
        FieldClass fc = new FieldClass();
        private String[] blogTitles;
        Context context;

        public HTTPGetLoginJson(Context context) {
            this.context = context;

        }


        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Integer doInBackground(String... params) {
            InputStream inputStream = null;
            Integer result = 0;
            try {
                *//* create Apache HttpClient *//*
                HttpClient httpclient = new DefaultHttpClient();

                *//* HttpGet Method *//*

                // String paramString = URLEncodedUtils.format(params, "utf-8");
                String sss = URLDecoder.decode(params[0], "UTF-8");

                HttpGet httpGet = new HttpGet(params[0]);




                *//* optional request header *//*
                httpGet.setHeader("Content-Type", "application/json");


                *//* optional request header *//*
                httpGet.setHeader("Accept", "application/json");

                *//* Make http request call *//*
                HttpResponse httpResponse = httpclient.execute(httpGet);


                //mesage=httpResponse.getStatusLine().toString();

                HttpEntity entity = httpResponse.getEntity();
                InputStream webs = entity.getContent();
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(webs, "UTF-8"), 8);
                    mesage = reader.readLine();
                   // Toast.makeText(context, mesage, Toast.LENGTH_LONG).show();
                    webs.close();
                    onPostExecute(1);
                } catch (Exception e) {

                }
                int statusCode = httpResponse.getStatusLine().getStatusCode();

                *//* 200 represents HTTP OK *//*
                if (statusCode == 200) {

                    *//* receive response as inputStream *//*
                    inputStream = httpResponse.getEntity().getContent();

                    String response = convertInputStreamToString(inputStream);

                    parseResult(response);

                    result = 1; // Successful

                } else {
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {

            }


            return result; //"Failed to fetch data!";
        }



        protected void onPostExecute(Integer result) {

            try {
                JSONObject area = new JSONObject(mesage);
                DataBaseSqlite dbs = new DataBaseSqlite(context);
                fc.SetMember_Age(area.getInt("Age"));
                fc.SetMember_CityId(1);
                fc.SetMember_Email(area.getString("Email"));
                ID = area.getInt("Id");
                fc.SetMember_Mobile(area.getString("Mobile"));
                fc.SetMember_Name(area.getString("Name"));
                fc.SetMember_Password(area.getString("Password"));
                fc.SetMember_UserName(area.getString("UserName"));
                fc.SetMember_Sex(area.getBoolean("Sex"));






                if (ID >= 0) {
                    dbs.Add_member(ID, fc.GetMember_Name(), fc.GetMember_Email(), fc.GetMember_Mobile(), fc.GetMember_Age(), fc.GetMember_Sex(), fc.GetMember_UserName(), fc.GetMember_Password(), fc.GetMember_CityId());
                    Log_In.this.finish();
                    Intent i=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);

                } else {
                    Toast.makeText(getApplicationContext(), "کاربر ساخته نشد دوباره امتحان کنید", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
            }

        }


        private String convertInputStreamToString(InputStream inputStream) throws IOException {

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            String result = "";

            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }

            *//* Close Stream *//*
            if (null != inputStream) {
                inputStream.close();
            }

            return result;
        }

        private void parseResult(String result) {

            try {
                JSONObject response = new JSONObject(result);

                JSONArray posts = response.optJSONArray("posts");

                blogTitles = new String[posts.length()];

                for (int i = 0; i < posts.length(); i++) {
                    JSONObject post = posts.optJSONObject(i);
                    String title = post.optString("title");

                    blogTitles[i] = title;
                }

            } catch (JSONException e) {
                // e.printStackTrace();
            }
        }

    }*/
}
