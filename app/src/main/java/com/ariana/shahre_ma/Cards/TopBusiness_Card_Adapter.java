package com.ariana.shahre_ma.Cards;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.R;

import java.util.ArrayList;
import java.util.List;

public class TopBusiness_Card_Adapter extends RecyclerView.Adapter<TopBusiness_Card_Adapter.ViewHolder> {

    List<TopBusiness_Item> mItems;
    TopBusiness_Item  nature;
    Context context;
    public TopBusiness_Card_Adapter(Context context) {

        super();

        mItems = new ArrayList<TopBusiness_Item>();






        DataBaseSqlite db=new DataBaseSqlite(context);
        Cursor rows=db.select_AllBusinessTops();

        if(rows.moveToFirst()) {
            do {

                nature = new TopBusiness_Item();
                nature.setName(rows.getString(1));
                nature.setDes(rows.getString(8));
                nature.setThumbnail(R.drawable.pooshak);
                mItems.add(nature);
            }while (rows.moveToNext());
        }




    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_view, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        TopBusiness_Item nature = mItems.get(i);
        viewHolder.tvNature.setText(nature.getName());
        viewHolder.tvDesNature.setText(nature.getDes());
        viewHolder.imgThumbnail.setImageResource(nature.getThumbnail());
    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

   public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imgThumbnail;
        public TextView tvNature;
        public TextView tvDesNature;

        public ViewHolder(View itemView) {
            super(itemView);

            imgThumbnail = (ImageView)itemView.findViewById(R.id.img_thumbnail);
            tvNature = (TextView)itemView.findViewById(R.id.tv_nature);
            tvDesNature = (TextView)itemView.findViewById(R.id.tv_des_nature);
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {

                    Log.i("ON_______CLICK",tvNature.getText().toString());


                }
            });

        }
    }
}