package com.ariana.shahre_ma.Cards;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ariana.shahre_ma.R;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    List<Jobs_item> mItems;

    public CardAdapter() {

        super();
        mItems = new ArrayList<Jobs_item>();
        Jobs_item nature = new Jobs_item();
        nature.setName("پوشاک کودکان");
        nature.setDes("یه فروشگاه خوب و عالی که هم جنساش خوبه هم قیمتاش ارزونه");
        nature.setThumbnail(R.drawable.pooshak);

        mItems.add(nature);





        nature = new Jobs_item();
        nature.setName("رستوران");
        nature.setDes("غذاهاش عالیه... حتما برید.");
        nature.setThumbnail(R.drawable.haftkhan);
        mItems.add(nature);


        nature = new Jobs_item();
        nature.setName("تیراژه");
        nature.setDes("پر از کفشای جور واجور، واسه هر سلیقه ای...");
        nature.setThumbnail(R.drawable.rest_tirajhe);
        mItems.add(nature);


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
        Jobs_item nature = mItems.get(i);
        viewHolder.tvNature.setText(nature.getName());
        viewHolder.tvDesNature.setText(nature.getDes());
        viewHolder.imgThumbnail.setImageResource(nature.getThumbnail());
    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imgThumbnail;
        public TextView tvNature;
        public TextView tvDesNature;

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView)itemView.findViewById(R.id.img_thumbnail);
            tvNature = (TextView)itemView.findViewById(R.id.tv_nature);
            tvDesNature = (TextView)itemView.findViewById(R.id.tv_des_nature);
        }
    }
}