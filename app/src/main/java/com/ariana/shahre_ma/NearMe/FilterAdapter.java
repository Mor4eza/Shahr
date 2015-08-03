package com.ariana.shahre_ma.NearMe;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.R;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by ariana2 on 8/2/2015.
 */
public class FilterAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    private String[] countries;
    private LayoutInflater inflater;
    Context context;
    Integer i=0;
    public FilterAdapter(Context context) {
        this.context=context;
        inflater = LayoutInflater.from(context);

        DataBaseSqlite db=new DataBaseSqlite(context);
        Cursor rows=db.select_Subset();
        countries=new String[rows.getCount()];
        if(rows.moveToFirst())
        {
            do{
                countries[i] =rows.getString(1) ;
                i++;
            }while (rows.moveToNext());
        }

    }

    @Override
    public int getCount() {
        return countries.length;
    }

    @Override
    public Object getItem(int position) {
        return countries[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.sticky_child, parent, false);
            holder.text = (CheckBox) convertView.findViewById(R.id.st_child);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text.setText(countries[position]);

        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.sticky_header, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.st_head);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        //set header text as first char in name

        String headerText = "" + countries[position].subSequence(0, 1).charAt(0);

        DataBaseSqlite db=new DataBaseSqlite(context);
        Cursor rows=db.select_Collection();

     /*   if(rows.moveToFirst())
        {
            do
            {
                holder.text.setText(rows.getString(1));

            }while (rows.moveToNext());
        }*/

        holder.text.setText(headerText);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        //return the first character of the country as ID because this is what headers are based upon
        return countries[position].subSequence(0, 1).charAt(0);
    }

    class HeaderViewHolder {
        TextView text;
    }

    class ViewHolder {
        CheckBox text;
    }

}
