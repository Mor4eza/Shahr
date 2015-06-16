package com.ariana.shahre_ma.Cards;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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




      try {

          DataBaseSqlite mydb = new DataBaseSqlite(context);
            Cursor allrows = mydb.select_opinion_BusinessId(fc.GetBusiness_Id());


            if (allrows.moveToFirst()) {

                mItems = new ArrayList<Comment_Card_items>();
                do {


                    Comment_Card_items nature = new Comment_Card_items();
                    nature.setmUser("مرتضی");
                    nature.setmDate(allrows.getString(2));
                    nature.setmComm(allrows.getString(1));

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
    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvuser;
        public TextView tvdate;
        public TextView tvcomm;

        public ViewHolder(View itemView) {
            super(itemView);
            tvuser = (TextView)itemView.findViewById(R.id.tv_user);
            tvdate = (TextView)itemView.findViewById(R.id.tv_date);
            tvcomm = (TextView)itemView.findViewById(R.id.tv_comment);
        }
    }
}