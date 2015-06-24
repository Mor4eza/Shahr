package com.ariana.shahre_ma.Cards;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ariana.shahre_ma.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ariana2 on 6/24/2015.
 */
public class Notify_Card_Adapter  extends RecyclerView.Adapter<Notify_Card_Adapter.ViewHolder> {

    List<Notify_Card_Items> mItems;


    private  static Context context;

    public Notify_Card_Adapter(Context context) {
        super();
        this.context=context;

        mItems = new ArrayList<Notify_Card_Items>();

        Notify_Card_Items Notification = new Notify_Card_Items();
        Notification.setNdate("1394");
        Notification.setNdetail("یه فروشگاه خوب و عالی که هم جنساش خوبه هم قیمتاش ارزونه");
        Notification.setNmarket("1فروشگاه");
        mItems.add(Notification);

        Notification=new Notify_Card_Items();
        Notification.setNdate("1394");
        Notification.setNdetail("یه فروشگاه خوب و عالی که هم جنساش خوبه هم قیمتاش ارزونه");
        Notification.setNmarket("2فروشگاه");
        mItems.add(Notification);

        Notification=new Notify_Card_Items();
        Notification.setNdate("1394");
        Notification.setNdetail("یه فروشگاه خوب و عالی که هم جنساش خوبه هم قیمتاش ارزونه");
        Notification.setNmarket("3فروشگاه");
        mItems.add(Notification);

        Notification=new Notify_Card_Items();
        Notification.setNdate("1394");
        Notification.setNdetail("یه فروشگاه خوب و عالی که هم جنساش خوبه هم قیمتاش ارزونه");
        Notification.setNmarket("4فروشگاه");
        mItems.add(Notification);



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
    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvdate;
        public TextView tvmarket;
        public TextView tvdetail;

        public ViewHolder(View itemView) {
            super(itemView);

            tvdate = (TextView)itemView.findViewById(R.id.tv_notify_date);
            tvmarket = (TextView)itemView.findViewById(R.id.tv_notify_market);
            tvdetail = (TextView)itemView.findViewById(R.id.tv_notify_detail);
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {

                    Log.i("ON_______CLICK", tvmarket.getText().toString());


                }
            });

        }
    }
}
