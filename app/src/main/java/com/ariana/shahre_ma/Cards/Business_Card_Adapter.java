package com.ariana.shahre_ma.Cards;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.R;

import java.util.ArrayList;
import java.util.List;

public class Business_Card_Adapter extends RecyclerView.Adapter<Business_Card_Adapter.ViewHolder> {

    List<Business_Card_Items> mItems;
    FieldClass fc=new FieldClass();
    private  static Context context;
    Business_Card_Items nature;


    public Business_Card_Adapter(Context context) {
        super();
        this.context=context;

            DataBaseSqlite db = new DataBaseSqlite(context);
            Cursor rows = db.select_AllBusinessId(fc.GetBusiness_Id());
            mItems = new ArrayList<Business_Card_Items>();



        if(rows.moveToFirst())
        {
            do {

                nature = new Business_Card_Items();
                nature.setName(rows.getString(1));
                nature.setmAddress(rows.getString(7));
                nature.setThumbnail(R.drawable.pooshak);
                nature.setRate(rows.getFloat(19));
                mItems.add(nature);

            }while (rows.moveToNext());
        }



    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.business_cards, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Business_Card_Items nature = mItems.get(i);
        viewHolder.tvNature.setText(nature.getName());
        viewHolder.tvDesNature.setText(nature.getmAddress());
        viewHolder.imgThumbnail.setImageResource(nature.getThumbnail());
        viewHolder.Rates.setRating((float)nature.getRate());
    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imgThumbnail;
        public TextView tvNature;
        public TextView tvDesNature;
        public RatingBar Rates;
        public ViewHolder(View itemView) {
            super(itemView);

            imgThumbnail = (ImageView)itemView.findViewById(R.id.my_business_image);
            tvNature = (TextView)itemView.findViewById(R.id.my_business_title);
            tvDesNature = (TextView)itemView.findViewById(R.id.my_business_address);
            Rates = (RatingBar)itemView.findViewById(R.id.my_business_rate);
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {

                    Log.i("ON_______CLICK",tvNature.getText().toString());


                }
            });

        }
    }
}