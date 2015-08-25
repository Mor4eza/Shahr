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
import android.widget.RatingBar;
import android.widget.TextView;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.Fields.FieldDataBusiness;
import com.ariana.shahre_ma.MyBusiness.Discount;
import com.ariana.shahre_ma.MyBusiness.Edit_business;
import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.job_details.Job_details;
import com.github.alexkolpa.fabtoolbar.FabToolbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Business_Card_Adapter extends RecyclerView.Adapter<Business_Card_Adapter.ViewHolder> {

    List<Business_Card_Items> mItems;
    FieldClass fc=new FieldClass();
    private  static Context context;
    Business_Card_Items nature;
    NetState net;
    FieldDataBusiness fdb=new FieldDataBusiness();

    public Business_Card_Adapter(Context context) {
        super();
        this.context=context;

        net=new NetState(context);
            DataBaseSqlite db = new DataBaseSqlite(context);

            mItems = new ArrayList<Business_Card_Items>();

        nature = new Business_Card_Items();

        if(net.checkInternetConnection())
        {
            for (int i = 0; i < fdb.GetMarketBusiness().size(); i++)
            {

                nature = new Business_Card_Items();

                nature.setId(fdb.GetIdBusiness().get(i));
                nature.setName(fdb.GetMarketBusiness().get(i));
                nature.setmAddress(fdb.GetAddressBusiness().get(i));
                nature.setThumbnail(R.drawable.pooshak1);
                nature.setRate(fdb.GetRateBusiness().get(i));
                nature.setNameImage(fdb.GetSrc().get(i));
                mItems.add(nature);

            }
        }
        else
        {

            Cursor rows = db.select_AllBusinessId(fc.GetBusiness_Id());

            if (rows.moveToFirst()) {
                do {

                    nature = new Business_Card_Items();
                    nature.setId(rows.getInt(0));
                    nature.setName(rows.getString(1));
                    nature.setmAddress(rows.getString(7));
                    nature.setThumbnail(R.drawable.pooshak1);
                    nature.setRate(rows.getFloat(19));
                    mItems.add(nature);

                } while (rows.moveToNext());
            }




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
        viewHolder.tvNature.setTag(nature.getId());
       // viewHolder.tvDesNature.setText(nature.getmAddress());
        viewHolder.Rates.setRating((float) nature.getRate());
        Log.i("image",nature.getNameImage());
        String image_url_1 = "http://www.shahrma.com/image/business/" + nature.getNameImage();
        Picasso.with(context).load(image_url_1).placeholder(R.drawable.img_not_found).into(viewHolder.img1);

    }


    @Override
    public int getItemCount() {

        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView img1;
        public ImageView img_edit;
        public ImageView img_discount;
        public ImageView img_pic;
        public TextView tvNature;
        public RatingBar Rates;
        public FabToolbar fab;
        public ViewHolder(View itemView) {
            super(itemView);

            img1 = (ImageView)itemView.findViewById(R.id.my_business_image1);
            tvNature = (TextView)itemView.findViewById(R.id.my_business_title);
            img_edit = (ImageView)itemView.findViewById(R.id.btn_edit_business);
            img_discount = (ImageView)itemView.findViewById(R.id.btn_discount);
            Rates = (RatingBar)itemView.findViewById(R.id.my_business_rate);
            fab=(FabToolbar)itemView.findViewById(R.id.fab_toolbar);



            fab.setButtonIcon(context.getResources().getDrawable(R.drawable.abc_ic_menu_moreoverflow_mtrl_alpha));

            img_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fab.hide();
                    Log.i("Idbusiness",String.valueOf(tvNature.getTag()));
                    fc.SetBusiness_Id(Integer.valueOf(String.valueOf(tvNature.getTag())));
                    Intent i = new Intent(context, Edit_business.class);
                    context.startActivity(i);
                    Log.i("clicked", tvNature.getText().toString());
                }
            });

            img_discount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fab.hide();
                    Intent i = new Intent(context, Discount.class);
                    context.startActivity(i);
                    Log.i("clicked", tvNature.getText().toString());
                }
            });



            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    Log.i("ON_______CLICK", tvNature.getText().toString());
                    fab.hide();

                }
            });
            img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fc.SetMarket_Business(tvNature.getText().toString());
                    fc.SetBusiness_Id(Integer.valueOf(String.valueOf(tvNature.getTag())));
                    Intent i=new Intent(context, Job_details.class);
                    context.startActivity(i);
                }
            });

        }

    }
}