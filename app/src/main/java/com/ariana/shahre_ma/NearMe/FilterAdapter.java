package com.ariana.shahre_ma.NearMe;

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

/**
 * Created by ariana2 on 7/12/2015.
 */
public class FilterAdapter extends ArrayAdapter<FilterItems> {

    private final Context context;
    private final ArrayList<FilterItems> itemsArrayList;
    public static List<String> selectedsubset=new ArrayList<String>();
    public FilterAdapter(Context context, ArrayList<FilterItems> itemsArrayList) {

        super(context, R.layout.filter_dialog_layout, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.sticky_child, parent, false);

        // 3. Get the two text view from the rowView
        final CheckBox labelView = (CheckBox) rowView.findViewById(R.id.st_child);

       // TextView Tv_id =  (TextView) rowView.findViewById(R.id.tv_dis_id);

        // 4. Set the text for textView
        labelView.setText(itemsArrayList.get(position).getTitle());

       // Tv_id.setText(String.valueOf(itemsArrayList.get(position).GetId()));
        // 5. retrn rowView


        labelView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    selectedsubset.add(labelView.getText().toString());
                    Log.i("selectedsubset",selectedsubset.toString());
                }
                else{
                    selectedsubset.remove(labelView.getText().toString());
                    Log.i("selectedsubset", selectedsubset.toString());
                }
            }
        });

        return rowView;
    }
}
