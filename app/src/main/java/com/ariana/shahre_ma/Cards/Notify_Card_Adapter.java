package com.ariana.shahre_ma.Cards;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ariana.shahre_ma.Date.DateTime;
import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.MyInterest.My_Interest;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.Settings.KeySettings;
import com.ariana.shahre_ma.job_details.Job_details;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ariana2 on 6/24/2015.
 */
public class Notify_Card_Adapter  extends RecyclerView.Adapter<Notify_Card_Adapter.ViewHolder> {

    List<Notify_Card_Items> mItems;
    Notify_Card_Items nci;
    FieldClass fc=new FieldClass();
    KeySettings setting;
    DateTime t=new DateTime();
    private  static Context context;


    public Notify_Card_Adapter(Context context) {
        super();
        this.context=context;
        setting=new KeySettings(context);
        Query query=new Query(context);
        Integer i=0;
        mItems = new ArrayList<Notify_Card_Items>();



        //خالی بودن اعلانات
        nci = new Notify_Card_Items();
        nci.setNdate("");
        nci.setNdetail("اعلاناتی برای نمایش وجود ندارد برای ارسال اعلانات به شما علاقه مندی های خود را ثبت کنید");
        nci.setNmarket("");
        nci.setNId(0);
        nci.setNotiyId(0);
        nci.setNewTag("ثبت علاقه مندی ها");
        mItems.add(nci);

        DataBaseSqlite db=new DataBaseSqlite(context);
        Cursor rowalls=db.select_AllNotificaton();

        if(rowalls.moveToFirst())
        {
            mItems = new ArrayList<Notify_Card_Items>();
            do
            {
              //  try {
                     nci = new Notify_Card_Items();

                    nci.setNdate(rowalls.getString(5));
                    nci.setNdetail(rowalls.getString(4));
                    nci.setNmarket(query.getNameBusiness(rowalls.getInt(2)));
                    nci.setNId(rowalls.getInt(2));
                    nci.setNotiyId(rowalls.getInt(0));


                            //Show tag New
                        if(query.getShowNotification(rowalls.getInt(0))>0)
                            nci.setNewTag("");
                            else
                            nci.setNewTag("جدید");


                        mItems.add(nci);



                i++;
            }while (rowalls.moveToNext());
        }



    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.notify_cards, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Notify_Card_Items nature = mItems.get(i);
        viewHolder.tvdate.setText(nature.getNdate());
        viewHolder.tvmarket.setText(nature.getNmarket());
        viewHolder.tvdetail.setText(nature.getNdetail());
        viewHolder.tvdetail.setTag(nature.getNotiyId());
        viewHolder.tvmarket.setTag(nature.getNId());
        viewHolder.newTag.setText(nature.getNewTag());
    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvdate;
        public TextView tvmarket;
        public TextView tvdetail;
        public TextView newTag;
        public ViewHolder(View itemView) {
            super(itemView);

            tvdate = (TextView)itemView.findViewById(R.id.tv_notify_date);
            tvmarket = (TextView)itemView.findViewById(R.id.tv_notify_market);
            tvdetail = (TextView)itemView.findViewById(R.id.tv_notify_detail);
            newTag = (TextView)itemView.findViewById(R.id.tv_newtag);
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {

                    Log.i("ON_______CLICK", tvmarket.getText().toString());

                    if((Integer)tvmarket.getTag()==0)
                    {
                        Intent intent=new Intent(context, My_Interest.class);
                        context.startActivity(intent);
                    }
                    else {
                        fc.SetShowNotification(true);
                        fc.SetShowNotificationId((Integer) tvdetail.getTag());
                        fc.SetMarket_Business(tvmarket.getText().toString());
                        fc.SetBusiness_Id((Integer) tvmarket.getTag());
                        Intent i = new Intent(context, Job_details.class);
                        context.startActivity(i);
                    }

                }
            });



        }
    }



}
