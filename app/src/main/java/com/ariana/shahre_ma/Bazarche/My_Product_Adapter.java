package com.ariana.shahre_ma.Bazarche;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.Fields.FieldDataBase;
import com.ariana.shahre_ma.R;

import java.util.ArrayList;
import java.util.List;

public class My_Product_Adapter extends RecyclerView.Adapter<My_Product_Adapter.ViewHolder> {

    List<My_Product_Items> mItems;
    FieldClass fc=new FieldClass();
    private  static Context context;
    My_Product_Items nature;
    FieldDataBase fieldDataBase=new FieldDataBase();
    Query query=new Query(context);

    public My_Product_Adapter(Context context) {
        super();
        this.context=context;

        mItems = new ArrayList<My_Product_Items>();

        nature = new My_Product_Items();
        for(int i=0;i<fieldDataBase.getName_Product().size();i++)
        {
            nature = new My_Product_Items();

            nature.setId(fieldDataBase.getId_Product().get(i));
            nature.setmI(i);
            nature.setName(fieldDataBase.getName_Product().get(i));
            nature.setmPrice(fieldDataBase.getprice_Product().get(i));
            nature.setThumbnail(R.drawable.pooshak);
            mItems.add(nature);
        }



    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.product_cards, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        My_Product_Items nature = mItems.get(i);
        viewHolder.tvNature.setText(nature.getName());
        viewHolder.tvNature.setTag(nature.getId());
        viewHolder.Price.setText(String.valueOf(nature.getmPrice()));
        viewHolder.Price.setTag(nature.getmI());
       // viewHolder.tvDesNature.setText(nature.getmAddress());
        viewHolder.img1.setImageResource(nature.getThumbnail());
    }


    @Override
    public int getItemCount() {

        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView img1;
        public TextView tvNature;
        public TextView Price;

        public ViewHolder(View itemView) {
            super(itemView);

            img1 = (ImageView)itemView.findViewById(R.id.my_product_image1);
            tvNature = (TextView)itemView.findViewById(R.id.my_product_title);
            Price = (TextView)itemView.findViewById(R.id.my_product_price);

            img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fc.SetProductId(Integer.valueOf(String.valueOf(tvNature.getTag())));
                    fc.SetNumber(Integer.valueOf(String.valueOf(Price.getTag())));
                    Intent i = new Intent(context, product_Details.class);
                    context.startActivity(i);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("ON_______CLICK", tvNature.getTag().toString());
                    fc.SetProductId(Integer.valueOf(String.valueOf(tvNature.getTag())));
                    fc.SetNumber(Integer.valueOf(String.valueOf(Price.getTag())));
                    Intent i = new Intent(context, product_Details.class);
                    context.startActivity(i);
                }
            });
        }
    }
}