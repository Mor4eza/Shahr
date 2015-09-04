package com.ariana.shahre_ma.Cards;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.DateBaseSqlite.SelectDataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.job_details.Job_details;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ariana2 on 8/2/2015.
 */
public class TopDiscount_Card_Adapter extends RecyclerView.Adapter<TopDiscount_Card_Adapter.ViewHolder> {

    List<TopDiscount_Item> mItems;
    TopDiscount_Item nature;
    Context context;
    FieldClass fc=new FieldClass();
    Query query;
    public TopDiscount_Card_Adapter(Context context) {

        super();
        this.context=context;
        fc=new FieldClass();
        mItems = new ArrayList<TopDiscount_Item>();
        query=new Query(context);

        SelectDataBaseSqlite db=new SelectDataBaseSqlite(context);
        Cursor rows=db.select_AllBusinessDisCount();

        if(rows.moveToFirst()) {
            do {

                nature = new TopDiscount_Item();
                nature.setName(rows.getString(1));
                nature.setDes(rows.getString(8));
                nature.setId(rows.getInt(0));
                nature.setSubsetId(rows.getInt(14));
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
        TopDiscount_Item nature = mItems.get(i);
        viewHolder.tvNature.setText(nature.getName());
        viewHolder.tvDesNature.setText(nature.getDes());
        viewHolder.tvDesNature.setTag(nature.getId());
        viewHolder.imgThumbnail.setImageResource(nature.getThumbnail());
        viewHolder.tvSubset.setText(query.getsubsetName(nature.getSubsetId()));
        String image_url_1;
        image_url_1 = "http://www.shahrma.com/image/business/" +nature.getSubsetId()+".jpg";
        Picasso.with(context).load(image_url_1).placeholder(R.drawable.img_not_found).into(viewHolder.imgThumbnail);
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
                    fc.SetBusinessDisCountTops(true);
                    Intent i =new Intent(context,Job_details.class);
                    context.startActivity(i);


                }
            });

        }
    }

}
