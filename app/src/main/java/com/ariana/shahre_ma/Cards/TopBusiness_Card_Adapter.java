package com.ariana.shahre_ma.Cards;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.DateBaseSqlite.SelectDataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.job_details.Job_details;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TopBusiness_Card_Adapter extends RecyclerView.Adapter<TopBusiness_Card_Adapter.ViewHolder> {

    List<TopBusiness_Item> mItems;
    TopBusiness_Item  nature;
    FieldClass fc=new FieldClass();
    Context context;
    Query query;
    public TopBusiness_Card_Adapter(Context context) {
        super();
        this.context=context;
        mItems = new ArrayList<TopBusiness_Item>();
        query=new Query(context);

        SelectDataBaseSqlite db=new SelectDataBaseSqlite(context);
        Cursor rows=db.select_AllBusinessTops();

        if(rows.moveToFirst()) {
            do {
                Log.i("id if",String.valueOf(rows.getInt(0)));
                nature = new TopBusiness_Item();
                nature.setName(rows.getString(1));
                nature.setDes(rows.getString(8));
                nature.setSubsetId(rows.getInt(14));
                nature.setId(rows.getInt(0));
                nature.setImageName(rows.getString(31));
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
        viewHolder.tvDesNature.setTag(nature.getId());
        viewHolder.imgThumbnail.setImageResource(nature.getThumbnail());
        viewHolder.tvSubset.setText(query.getsubsetName(nature.getSubsetId()));

        try {
            String image_url_1;
            if (nature.getImageName().equals("null") || nature.getImageName().equals("") || nature.getImageName().equals(null) || nature.getImageName() == null) {
                image_url_1 = "http://www.shahrma.com/image/business/" + nature.getSubsetId() + ".jpg";
                Picasso.with(context).load(image_url_1).placeholder(R.drawable.img_not_found).into(viewHolder.imgThumbnail);

            } else {
                image_url_1 = "http://www.shahrma.com/image/business/" + nature.getImageName();
                Picasso.with(context).load(image_url_1).placeholder(R.drawable.img_not_found).into(viewHolder.imgThumbnail);
            }
        }catch (Exception e){}
    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

   public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imgThumbnail;
        public TextView tvNature;
        public TextView tvDesNature;
        public TextView tvSubset;

        public ViewHolder(View itemView) {
            super(itemView);

            imgThumbnail = (ImageView)itemView.findViewById(R.id.img_thumbnail);
            tvNature = (TextView)itemView.findViewById(R.id.tv_nature);
            tvDesNature = (TextView)itemView.findViewById(R.id.tv_des_nature);
            tvSubset=(TextView)itemView.findViewById(R.id.tv_subset);
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    fc.SetMarket_Business(tvNature.getText().toString());
                    fc.SetBusiness_Id(Integer.valueOf(String.valueOf(tvDesNature.getTag())));
                    Log.i("id", String.valueOf(tvDesNature.getTag()));
                    fc.SetBusinessTops(true);
                    Intent i=new Intent(context, Job_details.class);
                    context.startActivity(i);

                }
            });

        }
    }
}