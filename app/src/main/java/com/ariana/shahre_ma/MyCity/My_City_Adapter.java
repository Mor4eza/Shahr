package com.ariana.shahre_ma.MyCity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.ariana.shahre_ma.R;

import java.util.ArrayList;
import java.util.List;

public class My_City_Adapter extends ArrayAdapter<My_City_Items> {
        private final boolean[] mCheckedState;
        private final Context context;
        private final ArrayList<My_City_Items> itemsArrayList;
        public static List<String> selectedsubset=new ArrayList<String>();

    public My_City_Adapter(Context context, ArrayList<My_City_Items> itemsArrayList) {

            super(context, R.layout.my_city_child, itemsArrayList);
            this.context = context;
            this.itemsArrayList = itemsArrayList;
            mCheckedState = new boolean[itemsArrayList.size()];
            Log.i("Size", String.valueOf(itemsArrayList.size()));
        }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

            // 1. Create inflater
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.my_city_row, parent, false);

            final CheckBox labelView = (CheckBox) rowView.findViewById(R.id.city_child);

            labelView.setText(itemsArrayList.get(position).getTitle());



            labelView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        Log.i("selectedsubset",selectedsubset.toString());
                    }
                    else{
                        Log.i("selectedsubset", selectedsubset.toString());
                    }
                }
            });
            labelView.setChecked(mCheckedState[position]);

            return rowView;
        }
}
