package com.ariana.shahre_ma.Cards;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by ariana2 on 6/8/2015.
 */
public class job_list_cards_adapter extends RecyclerView.Adapter<job_list_cards_adapter.ViewHolder>  {

    private static final String TABLE_NAME_BUSINESS = "business";
    private static final String DATABASE_NAME = "DBshahrma.db";
    private static final int DATABASE_VERSION = 1;

    List<Job_lists_card_item> mItems;
private  static Context context;


    public  job_list_cards_adapter(Context context) {
      this.context=context;
    }

    public job_list_cards_adapter() {

        super();



       DataBaseSqlite mydb= new DataBaseSqlite(context);
        Cursor allrows= mydb.select_business();



       if (allrows.moveToFirst()) {
            do {


                mItems = new ArrayList<Job_lists_card_item>();
                Job_lists_card_item nature = new Job_lists_card_item();
                nature.setName(allrows.getString(1));
                nature.setDes(allrows.getString(9));
                nature.setThumbnail(R.drawable.pooshak);
                nature.setRate(2.5);
                mItems.add(nature);

            } while (allrows.moveToNext());
        }

        mydb.close();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.job_lists_card, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Job_lists_card_item nature = mItems.get(i);
        viewHolder.tvNature.setText(nature.getName());
        viewHolder.tvDesNature.setText(nature.getDes());
        viewHolder.imgThumbnail.setImageResource(nature.getThumbnail());
        viewHolder.rates.setRating((float) nature.getRate());
    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imgThumbnail;
        public TextView tvNature;
        public TextView tvDesNature;
        public RatingBar rates;

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView)itemView.findViewById(R.id.img_market);
            tvNature = (TextView)itemView.findViewById(R.id.tv_title);
            tvDesNature = (TextView)itemView.findViewById(R.id.tv_address);
            rates = (RatingBar)itemView.findViewById(R.id.rates);
        }

    }
}