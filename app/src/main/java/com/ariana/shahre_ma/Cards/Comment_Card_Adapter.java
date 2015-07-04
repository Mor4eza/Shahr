package com.ariana.shahre_ma.Cards;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServiceGet.HTTPSendLikeURL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Comment_Card_Adapter extends RecyclerView.Adapter<Comment_Card_Adapter.ViewHolder> {

    List<Comment_Card_items> mItems;
   FieldClass fc=new FieldClass();
Query query;
    private  static Context context;

    public Comment_Card_Adapter(Context context) {

        super();

          this.context=context;


        mItems = new ArrayList<Comment_Card_items>();
        query=new Query(this.context);

            Comment_Card_items nature = new Comment_Card_items();
            nature.setmUser("");
            nature.setmDate("");
            nature.setmComm("  بدون نظر "  + "\n" +"اولین نظر را شما بفرستید");
            mItems.add(nature);


      try {

          DataBaseSqlite mydb = new DataBaseSqlite(context);
            Cursor allrows = mydb.select_opinion();


            if (allrows.moveToFirst()) {

                mItems = new ArrayList<Comment_Card_items>();
                do {


                    nature = new Comment_Card_items();
                    nature.setmUser(allrows.getString(6));
                    nature.setmDate(allrows.getString(2));
                    nature.setmComm(allrows.getString(1));
                    nature.setmlike(allrows.getInt(4));
                    nature.setmdisslike(allrows.getInt(5));
                    mItems.add(nature);

                } while (allrows.moveToNext());
            }
        }
        catch (Exception e){
            Log.i("ExceptionLikeandDisLike",e.toString());
        }


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.comment_cards, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Comment_Card_items nature = mItems.get(i);
        viewHolder.tvuser.setText(nature.getmUser());
        viewHolder.tvdate.setText(nature.getmDate());
        viewHolder.tvcomm.setText(nature.getmComm());

        viewHolder.tvlike.setText(String.valueOf(nature.getmlike()));
        viewHolder.tvdisslike.setText(String.valueOf(nature.getmdisslike()));

    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView tvuser;
        public TextView tvdate;
        public TextView tvcomm;
        public TextView tvlike;
        public TextView tvdisslike;
        public ImageButton btn_like;
        public ImageButton btn_disslike;


        public ViewHolder(View itemView) {
            super(itemView);
            //itemView.setOnClickListener(this);
            tvuser = (TextView)itemView.findViewById(R.id.tv_user);
            tvdate = (TextView)itemView.findViewById(R.id.tv_date);
            tvcomm = (TextView)itemView.findViewById(R.id.tv_comment);
            tvlike =(TextView)itemView.findViewById(R.id.txt_like);
            tvdisslike =(TextView)itemView.findViewById(R.id.txt_disslike);
            btn_like = (ImageButton)itemView.findViewById(R.id.btn_like);
            btn_disslike = (ImageButton)itemView.findViewById(R.id.btn_disslike);

            btn_like.setOnClickListener(this);
            btn_disslike.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if(v==btn_like){
                int Likes=0;

                HTTPSendLikeURL httplike=new HTTPSendLikeURL(context);
                httplike.SetLike(true);
                httplike.SetMemberid(1);
                httplike.Setopinionid(getIdOpinion());
                httplike.execute();
           /*     Likes++;
            tvlike.setText(String.valueOf(Likes));*/
            }

            else
            {

                HTTPSendLikeURL httplike=new HTTPSendLikeURL(context);

                httplike.SetLike(false);
                httplike.SetMemberid(1);
                httplike.Setopinionid(getIdOpinion());
                httplike.execute();

                //Log.i("DissLike", "onClick " + getPosition() + " " + tvcomm.getText().toString());
            }

        }

        private Integer getIdMember() {
            Integer Result =0;

            try {
                DataBaseSqlite dbs = new DataBaseSqlite(context);
                Cursor allrows = dbs.select_Member();
                allrows.moveToFirst();
                Result = allrows.getInt(0);
                allrows.close();
            }
            catch (Exception e)
            {
                Log.i("GETidMember",e.toString());
            }

            return Result;
        }

        private Integer getIdOpinion() {
            Integer Result =0;

            try {
                DataBaseSqlite dbs = new DataBaseSqlite(context);
                Cursor allrows = dbs.select_opinion();
                allrows.moveToFirst();
                Result = allrows.getInt(0);
                allrows.close();
            }
            catch (Exception e)
            {
                Log.i("GetidOpinion",e.toString());
            }

            return Result;
        }
    }


     class HTTPSendLikeURL1 extends AsyncTask<String, Void, Integer> {

        private String[] blogTitles;
        private static final String TAG = "Http Connection";
        private  String mesage;

        Boolean like;
        Integer opinionid;
        Integer memberid;

        Context context;

        public HTTPSendLikeURL1(Context context)
        {
            this.context=context;
        }

        public void Setopinionid(Integer opinionid)
        {
            this.opinionid=opinionid;
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
            String url="http://test.shahrma.com/api/ApiTakeLike?opinionId="+opinionid+"&memberId="+memberid+"&value="+like;
            Log.i("URL",url);
            return url;

        }
        @Override
        protected Integer doInBackground(String... params) {
            InputStream inputStream = null;

            Integer result = 0;
            try {

                HttpGet httpGet = new HttpGet(params[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse httpResponse = httpclient.execute(httpGet);
                HttpEntity entity = httpResponse.getEntity();
                InputStream webs = entity.getContent();
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(webs, "UTF-8"), 8);
                    mesage = (reader.readLine());

                    webs.close();
                } catch (Exception e) {
                    Log.e("Error in conversion: ", e.toString());

                }
                int statusCode = httpResponse.getStatusLine().getStatusCode();

                /* 200 represents HTTP OK */
                if (statusCode == 200) {

                    /* receive response as inputStream */
                    inputStream = httpResponse.getEntity().getContent();

                    String response = convertInputStreamToString(inputStream);

                    parseResult(response);

                    result = 1; // Successful

                } else {
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                Log.i(TAG, e.getLocalizedMessage());
                Log.i("Exception", e.toString());
            }

            return result; //"Failed to fetch data!";
        }


        @Override
        protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */
            //  Toast.makeText(getApplicationContext(),mesage,Toast.LENGTH_LONG).show();

            if (result == 1) {
                Log.i("mesage ", mesage);
                //arrayAdapter = new ArrayAdapter(AreaActivity.this, android.R.layout.simple_list_item_1, blogTitles);

                //listView.setAdapter(arrayAdapter);
            } else {
                Log.e(TAG, "Failed to fetch data!");
            }
        }


        private String convertInputStreamToString(InputStream inputStream) throws IOException {

            String line = "";
            String result = "";
            try
            {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));



                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }

            /* Close Stream */
                if (null != inputStream) {
                    inputStream.close();
                }


            }
            catch (Exception e){}
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
                e.printStackTrace();
            }
        }
    }
}