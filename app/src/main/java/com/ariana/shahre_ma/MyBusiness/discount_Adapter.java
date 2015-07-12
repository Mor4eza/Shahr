package com.ariana.shahre_ma.MyBusiness;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ariana.shahre_ma.R;

import java.util.ArrayList;

/**
 * Created by ariana2 on 7/12/2015.
 */
public class discount_Adapter extends ArrayAdapter<discount_item> {

    private final Context context;
    private final ArrayList<discount_item> itemsArrayList;

    public discount_Adapter(Context context, ArrayList<discount_item> itemsArrayList) {

        super(context, R.layout.discount_row, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.discount_row, parent, false);

        // 3. Get the two text view from the rowView
        TextView labelView = (TextView) rowView.findViewById(R.id.label);
        TextView valueView = (TextView) rowView.findViewById(R.id.value);
        TextView dateView =  (TextView) rowView.findViewById(R.id.expire_date);

        // 4. Set the text for textView
        labelView.setText(itemsArrayList.get(position).getTitle());
        valueView.setText(itemsArrayList.get(position).getDescription());
        dateView.setText(itemsArrayList.get(position).getExpireDate());
        // 5. retrn rowView
        return rowView;
    }
}
