package com.ariana.shahre_ma.Search;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ariana.shahre_ma.Cards.Job_lists_card_item;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.DateBaseSqlite.SelectDataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.Fields.FieldDataBusiness;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.Settings.KeySettings;
import com.ariana.shahre_ma.job_details.Job_details;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder> {

    List<SearchItems> mItems;
    SearchItems  nature;
    FieldClass fc=new FieldClass();
    FieldDataBusiness fdb=new FieldDataBusiness();
    Context context;
    SelectDataBaseSqlite sdb;
    KeySettings setting;
    Cursor allrows;
    Boolean search=true;
    Query query;

    public SearchListAdapter(Context context) {
        super();
        this.context=context;
         sdb=new SelectDataBaseSqlite(context);
         query=new Query(context);
            mItems = new ArrayList<SearchItems>();
        try {
            SelectDataBaseSqlite sdb = new SelectDataBaseSqlite(context);
            Integer cityid=0;
            setting=new KeySettings(context);
            cityid=query.getCityId(setting.getCityName());

            if(setting.getSearchBusiness())
            {
                Log.i("GetMarket_Business()",fc.GetMarket_Business());
                allrows = sdb.select_TableSearch(fc.GetMarket_Business());
                setting.saveSearchBusiness(false); //No Search
                search=false;
            }

            if(setting.getSortBusiness().equals("rate"))
            {
                allrows = sdb.select_TableSearchSortRate();
                setting.saveSortBusiness("0"); //No Sort
                search=false;
                Log.i("rate",setting.getSortBusiness());
            }  else if(setting.getSortBusiness().equals("name"))
            {
                allrows = sdb.select_TableSearchSortName();
                setting.saveSortBusiness("0"); //No Sort
                search=false;
                Log.i("name", setting.getSortBusiness());
            }  else if(setting.getSortBusiness().equals("date")) {
                allrows = sdb.select_TableSearchSortId();
                setting.saveSortBusiness("0"); //No Sort
                search=false;
                Log.i("date", setting.getSortBusiness());
            }

            if(!search) {
                Log.i("count",String.valueOf(allrows.getCount()));
                if(allrows.moveToFirst())
                {

                do {
                    nature = new SearchItems();

                    nature.setName(allrows.getString(1));
                    nature.setDes(allrows.getString(8));
                    nature.setSubsetId(allrows.getInt(14));
                    nature.setmRateCount(allrows.getInt(29));
                    nature.setRate(allrows.getDouble(30));
                    nature.setmId(allrows.getInt(0));
                    nature.setNameImage(allrows.getString(31));


                    if (allrows.getString(3).equals("") || allrows.getString(3).equals("null"))//value phone null
                    {
                        nature.setTell(allrows.getString(4));
                    } else if (allrows.getString(3).equals("1"))//value phone 1
                    {
                        nature.setTell("");
                    } else {
                        //nature.setTell(allrows.getString(3).substring(0,allrows.getString(3).indexOf("-")));
                        nature.setTell(allrows.getString(3));

                        mItems.add(nature);
                        notifyDataSetChanged();
                    }
                }while (allrows.moveToNext());

                }

            }
            else
            {
                if (fc.GetSearchOffline()) {
                    Log.i("SearchOffline1", "strat1");
                    for (int i = 0; i < fdb.GetMarketBusiness().size(); i++) {
                        Log.i("SearchOffline", String.valueOf(fdb.GetMarketBusiness().size()));
                        nature = new SearchItems();
                        nature.setName(fdb.GetMarketBusiness().get(i));
                        nature.setDes(fdb.GetAddressBusiness().get(i));
                        nature.setSubsetId(fdb.GetSubsetId().get(i));
                        // nature.setDisCount(fdb.GetDisCountId().get(i));
                        nature.setmRateCount(fdb.GetRateCount().get(i));
                        nature.setRate(fdb.GetRateBusiness().get(i));
                        nature.setmId(fdb.GetIdBusiness().get(i));
                        nature.setNameImage(fdb.GetSrc().get(i));


                        if (fdb.GetMobileBusiness().get(i).length() == 0 || fdb.GetMobileBusiness().get(i).equals("") || fdb.GetMobileBusiness().get(i).equals(null) || fdb.GetMobileBusiness().get(i).equals("null")) {
                            nature.setTell(fdb.GetPhoneBusiness().get(i));
                        } else {
                            nature.setTell(fdb.GetMobileBusiness().get(i));
                        }
                        if (nature.getTell().length() < 2) {
                            nature.setTell("");
                        }

                        mItems.add(nature);
                        notifyDataSetChanged();


                    }
                    fc.SetSearchOffline(false);
                } else {
                    Log.i("checkInternetConnection", "strat");
                    mItems = new ArrayList<SearchItems>();

                    for (int i = 0; i < fdb.GetMarketBusiness().size(); i++) {
                        nature = new SearchItems();
                        nature.setName(fdb.GetMarketBusiness().get(i));
                        nature.setDes(fdb.GetAddressBusiness().get(i));
                        nature.setSubsetId(fdb.GetSubsetId().get(i));
                        nature.setDisCount(fdb.GetDisCountId().get(i));
                        nature.setRate(fdb.GetRateBusiness().get(i));
                        nature.setmId(fdb.GetIdBusiness().get(i));
                        nature.setmRateCount(fdb.GetRateCount().get(i));
                        nature.setNameImage(fdb.GetSrc().get(i));


                        if (fdb.GetMobileBusiness().get(i).length() == 0 || fdb.GetMobileBusiness().get(i).equals("") || fdb.GetMobileBusiness().get(i).equals(null) || fdb.GetMobileBusiness().get(i).equals("null")) {
                            nature.setTell(fdb.GetPhoneBusiness().get(i));
                        } else {
                            nature.setTell(fdb.GetMobileBusiness().get(i));
                        }
                        if (nature.getTell().length() < 2) {
                            nature.setTell("");
                        }

                        mItems.add(nature);
                        notifyDataSetChanged();
                    }
                }
            }
       }
        catch (Exception e)
        {
            Log.d("searachListAdapter",e.toString());
        }


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
        SearchItems nature = mItems.get(i);
        viewHolder.tvNature.setText(nature.getName());
        viewHolder.tvDesNature.setText(nature.getDes());
        viewHolder.tvDesNature.setTag(nature.getmId());
        viewHolder.imgThumbnail.setImageResource(nature.getThumbnail());
        viewHolder.rates.setRating((float) nature.getRate());
        viewHolder.rates.setTag(nature.getmId());
        viewHolder.tvTell.setText(nature.getTell());
        viewHolder.tvRateCount.setText("/" + nature.getmRateCount().toString());
        String image_url_1;
        if (nature.getNameImage().equals("null")||nature.getNameImage().equals("")||nature.getNameImage().equals(null)||nature.getNameImage()==null){
            Log.i("SubsetId",nature.getSubsetId().toString());
            image_url_1 = "http://www.shahrma.com/image/business/" +nature.getSubsetId()+".jpg";
            Picasso.with(context).load(image_url_1).placeholder(R.drawable.img_not_found).into(viewHolder.imgThumbnail);

        }
        else
        {
            image_url_1 = "http://www.shahrma.com/image/business/" + nature.getNameImage();
            Picasso.with(context).load(image_url_1).placeholder(R.drawable.img_not_found).into(viewHolder.imgThumbnail);
        }
    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        public ImageView imgThumbnail;
        public TextView tvNature;
        public TextView tvDesNature;
        public RatingBar rates;
        public TextView tvTell;
        public CardView cards;
        public TextView tvRateCount;
        public ViewHolder(View itemView) {
            super(itemView);

            imgThumbnail = (ImageView) itemView.findViewById(R.id.img_market);
            tvRateCount=(TextView) itemView.findViewById(R.id.tv_rateCount);
            tvNature = (TextView) itemView.findViewById(R.id.tv_title);
            tvDesNature = (TextView) itemView.findViewById(R.id.tv_address);
            rates = (RatingBar) itemView.findViewById(R.id.rates);
            tvTell = (TextView) itemView.findViewById(R.id.tv_tell);
            cards = (CardView) itemView.findViewById(R.id.cards);
            imgThumbnail.setOnClickListener(this);
            tvNature.setOnClickListener(this);
            cards.setOnClickListener(this);

            tvTell.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tvTell.getText().toString()));
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View v) {
            fc.SetMarket_Business(tvNature.getText().toString());
            // fc.SetAddress_Business(tvDesNature.getText().toString());*/

            fc.SetBusiness_Id((Integer) rates.getTag());
            Intent i =new Intent(context,Job_details.class);
            context.startActivity(i);
        }
        }
    }
