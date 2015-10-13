package com.ariana.shahre_ma.MyCity;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.ariana.shahre_ma.DateBaseSqlite.SelectDataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class My_City_Adapter extends ArrayAdapter<My_City_Items> {
        private final boolean[] mCheckedState;
        private final Context context;
        private final ArrayList<My_City_Items> itemsArrayList;
        public static List<String> selectedsubset=new ArrayList<String>();
        FieldClass fc=new FieldClass();
    public My_City_Adapter(Context context, ArrayList<My_City_Items> itemsArrayList) {

            super(context, R.layout.my_city_child, itemsArrayList);
            this.context = context;
            this.itemsArrayList = itemsArrayList;
            mCheckedState = new boolean[itemsArrayList.size()];
        }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

            // 1. Create inflater
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.my_city_row, parent, false);

            final CheckBox labelView = (CheckBox) rowView.findViewById(R.id.city_child);
            final ImageView img = (ImageView)rowView.findViewById(R.id.img_city);
            labelView.setText(itemsArrayList.get(position).getTitle());

        Picasso.with(context).load("http://www.shahrma.com/app/img/collection_icon/" +itemsArrayList.get(position).GetId()+ ".png").into(img);


        labelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SelectDataBaseSqlite sdb = new SelectDataBaseSqlite(context);
                Cursor rows = sdb.select_Subset(itemsArrayList.get(position).GetId());
                if (rows.moveToFirst()) {
                    if (labelView.isChecked()) {
                        mCheckedState[position] = true;
                        do{
                            selectedsubset.add(String.valueOf(rows.getInt(0)));

                        }while (rows.moveToNext());

                        fc.SetNameSubset(selectedsubset);
                    } else {
                        mCheckedState[position] = false;
                        do{
                            selectedsubset.remove(String.valueOf(rows.getInt(0)));

                        }while (rows.moveToNext());
                        fc.SetNameSubset(selectedsubset);
                    }
                }
            }
        });

        labelView.setChecked(mCheckedState[position]);
            return rowView;
        }
}
