package com.ariana.shahre_ma.Cards;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
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
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.Settings.KeySettings;
import com.ariana.shahre_ma.job_details.Job_details;
import com.neno0o.lighttextviewlib.LightTextView;

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
        Query query=new Query(context);
        setting=new KeySettings(context);

Integer i=0;
        mItems = new ArrayList<Notify_Card_Items>();
        DataBaseSqlite db=new DataBaseSqlite(context);
        Cursor rowalls=db.select_AllNotificaton();

        if(rowalls.moveToFirst())
        {
            mItems = new ArrayList<Notify_Card_Items>();
            do
            {
              //  try {
                    nci = new Notify_Card_Items();
                    Log.i("PMtime", String.valueOf(setting.getPMtime()));
                    Log.i("Time", t.Time());
                    Log.i("Boolean", String.valueOf(rowalls.getString(3)));



                        if(Boolean.parseBoolean(rowalls.getString(3))==false){
                        nci.setNdate(rowalls.getString(5));
                        nci.setNdetail(rowalls.getString(4));

                        Log.i("erja", String.valueOf(rowalls.getInt(2)));
                        nci.setNmarket(query.getNameBusiness(rowalls.getInt(2)));
                        nci.setNId(rowalls.getInt(2));
                            nci.setNotiyId(rowalls.getInt(0));
                        mItems.add(nci);
                        }
                        else
                        {
                           nci.setNdate(rowalls.getString(5));
                            nci.setNdetail(rowalls.getString(4));
                            nci.setNmarket(query.getNameBusiness(rowalls.getInt(2)));
                            nci.setNId(rowalls.getInt(2));
                            nci.setNotiyId(rowalls.getInt(0));
                            mItems.add(nci);
                        }

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
        viewHolder.newTag.setText("جدید");
        viewHolder.newTag.setPosition(LightTextView.Position.LEFT_CORNER);
        viewHolder.newTag.setCurrentView(viewHolder.tvmarket);
    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvdate;
        public TextView tvmarket;
        public TextView tvdetail;
        public LightTextView newTag;
        public ViewHolder(View itemView) {
            super(itemView);

            tvdate = (TextView)itemView.findViewById(R.id.tv_notify_date);
            tvmarket = (TextView)itemView.findViewById(R.id.tv_notify_market);
            tvdetail = (TextView)itemView.findViewById(R.id.tv_notify_detail);
            newTag=new LightTextView(context);
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {


                    fc.SetShowNotification(true);//send true
                    fc.SetShowNotificationId((Integer) tvdetail.getTag());//send field id Notification
                    fc.SetMarket_Business(tvmarket.getText().toString());//send Name Market
                    fc.SetBusiness_Id((Integer)tvmarket.getTag());//send Id business
                    Intent i = new Intent(context, Job_details.class);
                    context.startActivity(i);


                }
            });

        }
    }

}
