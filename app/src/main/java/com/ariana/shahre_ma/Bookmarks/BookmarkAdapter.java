package com.ariana.shahre_ma.Bookmarks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ariana.shahre_ma.R;

import java.util.ArrayList;

/**
 * Created by ariana2 on 7/26/2015.
 */
public class BookmarkAdapter extends ArrayAdapter<Bookmark_Item> {

    private final Context context;
    private final ArrayList<Bookmark_Item> itemsArrayList;

    public BookmarkAdapter(Context context, ArrayList<Bookmark_Item> itemsArrayList) {

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
        View rowView = inflater.inflate(R.layout.bookmark_row, parent, false);

        // 3. Get the two text view from the rowView
        TextView labelView = (TextView) rowView.findViewById(R.id.business_name);
        TextView Tv_id =  (TextView) rowView.findViewById(R.id.bookmark_id);

        // 4. Set the text for textView
        labelView.setText(itemsArrayList.get(position).getTitle());
        Tv_id.setText(String.valueOf(itemsArrayList.get(position).GetId()));
        // 5. retrn rowView
        return rowView;
    }
}
