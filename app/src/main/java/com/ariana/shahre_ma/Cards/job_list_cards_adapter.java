package com.ariana.shahre_ma.Cards;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.Settings.KeySettings;
import com.ariana.shahre_ma.job_details.Job_details;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ariana2 on 6/8/2015.
 */
public class job_list_cards_adapter extends RecyclerView.Adapter<job_list_cards_adapter.ViewHolder> {


    List<Job_lists_card_item> mItems;
    Job_lists_card_item nature;
    FieldClass fc=new FieldClass();
    KeySettings setting;
    Cursor allrows;
private  static Context context;


    public  job_list_cards_adapter(Context context)
    {
        super();
      this.context=context;


        setting=new KeySettings(context);
        mItems = new ArrayList<Job_lists_card_item>();


            Job_lists_card_item nature = new Job_lists_card_item();
            nature.setName("بدون اطلاعات");
            nature.setDes("");
            nature.setThumbnail(R.drawable.pooshak);
            nature.setRate(2.5);

            mItems.add(nature);

        DataBaseSqlite mydb = new DataBaseSqlite(context);

        if(setting.getSearchBusiness()==false) {
             allrows = mydb.select_AllBusiness(fc.GetBusiness_SubsetIdb());
        }
        else if(setting.getSortBusiness()==true)
        {
            allrows = mydb.select_SortRateBusiness(fc.GetBusiness_SubsetIdb());
            setting.saveSortBusiness(false); //No Sort
        }
        else
        {
             allrows = mydb.SearchBusiness(fc.GetMarket_Business(), fc.GetBusiness_SubsetIdb());
            setting.saveSearchBusiness(false); //No Search
        }

        try {


                if (allrows.moveToFirst()) {
                    mItems = new ArrayList<Job_lists_card_item>();
                    do {


                        nature = new Job_lists_card_item();
                        nature.setName(allrows.getString(1));
                        nature.setDes(allrows.getString(8));
                        nature.setThumbnail(R.drawable.pooshak);
                        nature.setRate(allrows.getDouble(29));
                        nature.setmId(allrows.getInt(0));

                        mItems.add(nature);

                    } while (allrows.moveToNext());
                }
        }
        catch (Exception e){}

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
        viewHolder.rates.setTag(nature.getmId());
    }

    @Override
    public int getItemCount() {


            return mItems.size();

    }



    public void onClick_image( View view,RecyclerView vie) {
   
        int itemPosition = vie.getChildPosition(view);

        String item =String.valueOf(itemPosition);
       // Toast.makeText(mContext, getp, Toast.LENGTH_LONG).show();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        public ImageView imgThumbnail;
        public TextView tvNature;
        public TextView tvDesNature;
        public RatingBar rates;
        FieldClass fc=new FieldClass();

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView)itemView.findViewById(R.id.img_market);
            tvNature = (TextView)itemView.findViewById(R.id.tv_title);
            tvDesNature = (TextView)itemView.findViewById(R.id.tv_address);
            rates = (RatingBar)itemView.findViewById(R.id.rates);

            imgThumbnail.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
         /*   fc.SetMarket_Business(tvNature.getText().toString());
            fc.SetAddress_Business(tvDesNature.getText().toString());*/

            fc.SetBusiness_Id((Integer)rates.getTag());
            Intent i =new Intent(context,Job_details.class);
            context.startActivity(i);
        }
    }


}
