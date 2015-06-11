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

public class Comment_Card_Adapter extends RecyclerView.Adapter<Comment_Card_Adapter.ViewHolder> {

    List<Comment_Card_items> mItems;

    public Comment_Card_Adapter() {

        super();

        mItems = new ArrayList<Comment_Card_items>();



        Comment_Card_items nature = new Comment_Card_items();
        nature.setmUser("mori");
        nature.setmComm("خیلی خوبه");
        nature.setmDate("1394/3/15");
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
        Comment_Card_items nature = mItems.get(i);
        viewHolder.tvuser.setText(nature.getmUser());
        viewHolder.tvdate.setText(nature.getmDate());
        viewHolder.tvcomm.setText(nature.getmComm());
    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvuser;
        public TextView tvdate;
        public TextView tvcomm;

        public ViewHolder(View itemView) {
            super(itemView);
            tvuser = (TextView)itemView.findViewById(R.id.tv_user);
            tvdate = (TextView)itemView.findViewById(R.id.tv_date);
            tvcomm = (TextView)itemView.findViewById(R.id.tv_comment);
        }
    }
}