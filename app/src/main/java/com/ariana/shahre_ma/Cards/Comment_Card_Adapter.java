package com.ariana.shahre_ma.Cards;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
                    nature.settag(allrows.getInt(0));
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
        viewHolder.tvcomm.setTag(String.valueOf(nature.gettag()));
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


                if(query.getMemberId()>0) {
                    if(v==btn_like) {
                        int Likes = 0;
                        HTTPSendLikeURL httplike = new HTTPSendLikeURL(context);
                        httplike.SetLike(true);
                        httplike.SetMemberid(query.getMemberId());
                        httplike.Setopinionid(Integer.parseInt((String) tvcomm.getTag()));
                        httplike.execute();
                    }
                    else
                    {
                        HTTPSendLikeURL httplike=new HTTPSendLikeURL(context);
                        httplike.SetLike(false);
                        httplike.SetMemberid(query.getMemberId());
                        httplike.Setopinionid(Integer.parseInt((String) tvcomm.getTag()));
                        httplike.execute();
                    }
                }




            else
            {

                Toast.makeText(context.getApplicationContext(), "کاربری وارد نشده است", Toast.LENGTH_LONG).show();
            }

        }


    }

}