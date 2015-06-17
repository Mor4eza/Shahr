package com.ariana.shahre_ma.Cards;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.R;

import java.util.ArrayList;
import java.util.List;

public class Comment_Card_Adapter extends RecyclerView.Adapter<Comment_Card_Adapter.ViewHolder> {

    List<Comment_Card_items> mItems;
   FieldClass fc=new FieldClass();

    private  static Context context;

    public Comment_Card_Adapter(Context context) {

        super();

          this.context=context;


        mItems = new ArrayList<Comment_Card_items>();


            Comment_Card_items nature = new Comment_Card_items();
            nature.setmUser("مرتضی");
            nature.setmDate("111");
            nature.setmComm("11");

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
                   // nature.setmlike(allrows.getInt(4));
                 //   nature.setmdisslike(allrows.getInt(5));
                    mItems.add(nature);

                } while (allrows.moveToNext());
            }
        }
        catch (Exception e){}


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
       // viewHolder.tvlike.setText(nature.getmlike());
       // viewHolder.tvdisslike.setText(nature.getmdisslike());

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
                Likes++;
            tvlike.setText(String.valueOf(Likes));
            }else{
                Log.i("DissLike", "onClick " + getPosition() + " " + tvcomm.getText().toString());
            }

        }
    }

}