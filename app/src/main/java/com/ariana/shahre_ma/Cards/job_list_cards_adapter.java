package com.ariana.shahre_ma.Cards;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
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

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.Fields.FieldDataBusiness;
import com.ariana.shahre_ma.ImageDownload.ImageLoader;
import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.Settings.KeySettings;
import com.ariana.shahre_ma.job_details.Job_details;
import com.neno0o.lighttextviewlib.LightTextView;

import java.io.File;
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
    Boolean search=true;
    Cursor allrows;
    ImageLoader imgLoader;
private  static Context context;


    public  job_list_cards_adapter(Context context)
    {
        super();
      this.context=context;
      Query query=new Query(context);
        imgLoader=new ImageLoader(context);
      FieldDataBusiness fdb=new FieldDataBusiness();
      NetState ns=new NetState(context);
        setting=new KeySettings(context);
        mItems = new ArrayList<Job_lists_card_item>();


        DataBaseSqlite mydb = new DataBaseSqlite(context);
        Integer cityid=0;
        cityid=query.getCityId(setting.getCityName());

        if(setting.getSearchBusiness()==false)
        {
             allrows = mydb.select_AllBusiness(fc.GetBusiness_SubsetIdb(),cityid);
             search=false;
        }
        else
        {
            allrows = mydb.SearchBusiness(fc.GetMarket_Business(), fc.GetBusiness_SubsetIdb());
            setting.saveSearchBusiness(false); //No Search
            search=false;
        }

         if(setting.getSortBusiness().equals("rate"))
        {
            allrows = mydb.select_SortRateBusiness(fc.GetBusiness_SubsetIdb());
            setting.saveSortBusiness("0"); //No Sort
            search=false;
            Log.i("rate",setting.getSortBusiness());
        }  else if(setting.getSortBusiness().equals("name"))
        {
            allrows = mydb.select_SortNameBusiness(fc.GetBusiness_SubsetIdb());
            setting.saveSortBusiness("0"); //No Sort
            search=false;
            Log.i("name", setting.getSortBusiness());
        }  else if(setting.getSortBusiness().equals("date")) {
             allrows = mydb.select_SortDateBusiness(fc.GetBusiness_SubsetIdb());
             setting.saveSortBusiness("0"); //No Sort
             search=false;
            Log.i("date", setting.getSortBusiness());
        }

        try {
            if(fc.GetSearchOffline())
                {
                    fc.SetSearchOffline(false);
                    mItems = new ArrayList<Job_lists_card_item>();

                    for (int i = 0; i < fdb.GetMarketBusiness().size(); i++)
                    {

                        nature = new Job_lists_card_item();
                        nature.setName(fdb.GetMarketBusiness().get(i));
                        nature.setDes(fdb.GetAddressBusiness().get(i));
                        nature.setThumbnail(R.drawable.pooshak);

                        nature.setRate(fdb.GetRateBusiness().get(i));
                        nature.setmId(fdb.GetIdBusiness().get(i));


                        if(fdb.GetMobileBusiness().get(i).length()==0)
                        {
                            nature.setTell(fdb.GetPhoneBusiness().get(i));
                        }
                        else if(fdb.GetPhoneBusiness().get(i).equals("1"))
                        {
                            nature.setTell("");
                        }
                        else
                        {
                            nature.setTell(fdb.GetMobileBusiness().get(i));
                        }

                        mItems.add(nature);
                        notifyDataSetChanged();


                    }
                }
            else
                {
                    if(ns.checkInternetConnection() && search)
                    {

                        mItems = new ArrayList<Job_lists_card_item>();

                        for (int i = 0; i < fdb.GetMarketBusiness().size(); i++)
                        {

                            nature = new Job_lists_card_item();
                            nature.setName(fdb.GetMarketBusiness().get(i));
                            nature.setDes(fdb.GetAddressBusiness().get(i));
                            nature.setThumbnail(R.drawable.pooshak);
                            nature.setRate(fdb.GetRateBusiness().get(i));
                            nature.setmId(fdb.GetIdBusiness().get(i));


                            if(fdb.GetMobileBusiness().get(i).length()==0)
                            {
                                //nature.setTell(fdb.GetPhoneBusiness().get(i).substring(0,fdb.GetPhoneBusiness().get(i).indexOf("-")));
                                nature.setTell(fdb.GetPhoneBusiness().get(i));

                            }
                            else if(fdb.GetPhoneBusiness().get(i).equals("1"))
                            {
                                nature.setTell("");
                            }
                            else
                            {
                                nature.setTell(fdb.GetMobileBusiness().get(i));
                            }

                            mItems.add(nature);
                            notifyDataSetChanged();
                        }
                    }
                    else
                    {

                        if (allrows.moveToFirst())
                        {
                            mItems = new ArrayList<Job_lists_card_item>();
                            do {


                                nature = new Job_lists_card_item();
                                nature.setName(allrows.getString(1));
                                nature.setDes(allrows.getString(8));
                                nature.setThumbnail(R.drawable.pooshak);
                                Log.i("Rate",String.valueOf(allrows.getDouble(30)));
                                nature.setRate(allrows.getDouble(30));
                                nature.setmId(allrows.getInt(0));






                                if (allrows.getString(3).equals(""))//value phone null
                                {
                                    nature.setTell(allrows.getString(4));
                                }
                                else if(allrows.getString(3).equals("1"))//value phone 1
                                {
                                    nature.setTell("");
                                }
                                else
                                {
                                    //nature.setTell(allrows.getString(3).substring(0,allrows.getString(3).indexOf("-")));
                                    nature.setTell(allrows.getString(3));


                                }
                                mItems.add(nature);

                            } while (allrows.moveToNext());
                        }

                    }

                }
            fdb.ClearAll();
            if(setting.getCacheImage()==false)
            {
                //Delete image
                File dir = new File(android.os.Environment.getExternalStorageDirectory(), "myFolder/image_folder");
                if (dir.isDirectory()) {
                    String[] children = dir.list();
                    for (int i = 0; i < children.length; i++) {
                        Log.i("NameImage", new File(dir, children[i]).getName());
                        new File(dir, children[i]).delete();
                    }
                }

            }

            }
       catch(Exception e)
        {
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

        Job_lists_card_item nature = mItems.get(i);

        viewHolder.tvNature.setText(nature.getName());
        viewHolder.tvDesNature.setText(nature.getDes());
      //  viewHolder.imgThumbnail.setImageResource(nature.getThumbnail());

       String image_url_1 = "http://reallifeglobal.com/wp-content/uploads/2014/08/business-english-world.jpg?467a33";
       imgLoader.DisplayImage(image_url_1, viewHolder.imgThumbnail);


        viewHolder.rates.setRating((float) nature.getRate());
        viewHolder.rates.setTag(nature.getmId());
        if ( viewHolder.tvTell.getText().toString().equals("1")){
            viewHolder.tvTell.setText("");
        }else {
            viewHolder.tvTell.setText(nature.getTell());
            viewHolder.tvTell.setPaintFlags(viewHolder.tvTell.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            }
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
        public TextView tvTell;
        public CardView cards;
        public LightTextView newTag;
        FieldClass fc=new FieldClass();

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView)itemView.findViewById(R.id.img_market);
            tvNature = (TextView)itemView.findViewById(R.id.tv_title);
            tvDesNature = (TextView)itemView.findViewById(R.id.tv_address);
            rates = (RatingBar)itemView.findViewById(R.id.rates);
            tvTell=(TextView)itemView.findViewById(R.id.tv_tell);
            cards=(CardView)itemView.findViewById(R.id.cards);
            imgThumbnail.setOnClickListener(this);
            tvNature.setOnClickListener(this);
            cards.setOnClickListener(this);
            newTag=new LightTextView(context);

            tvTell.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + tvTell.getText().toString()));
                    context.startActivity(intent);
                }
            });

        }


        @Override
        public void onClick(View v) {
            fc.SetMarket_Business(tvNature.getText().toString());
           // fc.SetAddress_Business(tvDesNature.getText().toString());*/

            fc.SetBusiness_Id((Integer)rates.getTag());
            Intent i =new Intent(context,Job_details.class);
            context.startActivity(i);
        }
    }


}
