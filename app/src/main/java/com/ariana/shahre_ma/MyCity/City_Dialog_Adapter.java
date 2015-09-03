package com.ariana.shahre_ma.MyCity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ariana.shahre_ma.R;
import com.victor.loading.rotate.RotateLoading;

import java.util.ArrayList;

/**
 * Created by ariana2 on 9/8/2015...
 */

    public class City_Dialog_Adapter extends ArrayAdapter<City_Dialog_Items> {

        public  TextView labelView;
        RotateLoading loading;
        private final Context context;
        private final ArrayList<City_Dialog_Items> itemsArrayList;

        public City_Dialog_Adapter(Context context, ArrayList<City_Dialog_Items> itemsArrayList) {
            super(context, R.layout.bookmark_row, itemsArrayList);
            this.context = context;
            this.itemsArrayList = itemsArrayList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // 1. Create inflater
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 2. Get rowView from inflater
            View rowView = inflater.inflate(R.layout.city_dialog_row, parent, false);
            // 3. Get the two text view from the rowView
            labelView = (TextView) rowView.findViewById(R.id.subset_name);
            loading= (RotateLoading) rowView.findViewById(R.id.loading);
            loading.start();
            // 4. Set the text for textView
            labelView.setText(itemsArrayList.get(position).getTitle());

            // 5. retrn rowView
            return rowView;
        }
    }