package com.ariana.shahre_ma.MyInterest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.Jobs_List;
import com.ariana.shahre_ma.ListExpand.Continent;
import com.ariana.shahre_ma.ListExpand.Country;
import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetBusinessJson;

import java.util.ArrayList;


/**
 * Created by ariana2 on 7/5/2015.
 */
public class Interest_Adapter extends BaseExpandableListAdapter {


    Integer count=0;
    private Context context;
    private ArrayList<Continent> continentList;
    private ArrayList<Continent> originalList;
    private FieldClass fc=new FieldClass();
    public Interest_Adapter(Context context, ArrayList<Continent> continentList) {
        this.context = context;
        this.continentList = new ArrayList<Continent>();
        this.continentList.addAll(continentList);
        this.originalList = new ArrayList<Continent>();
        this.originalList.addAll(continentList);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<Country> countryList = continentList.get(groupPosition).getCountryList();
        return countryList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View view, ViewGroup parent) {

        final Country country = (Country) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.my_interest_child, null);
        }
        final ImageView star=(ImageView)view.findViewById(R.id.add_interest);
        final TextView name = (TextView) view.findViewById(R.id.interest_child);
        name.setText(country.getName().trim());

      star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star.setTag(country.getName());
                Toast.makeText(context,star.getTag().toString(),Toast.LENGTH_LONG).show();
                if (star.getTag()==country.getName())
                {
                    star.setImageDrawable(context.getResources().getDrawable(R.drawable.abc_btn_rating_star_on_mtrl_alpha));
                }
            }
        });

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Toast.makeText(context, country.getName(), Toast.LENGTH_LONG).show();
                star.setTag(country.getName().toString());
                if (star.getTag().toString().equals(country.getName()))
                {
                    star.setImageDrawable(context.getResources().getDrawable(R.drawable.abc_btn_rating_star_on_mtrl_alpha));
                    DataBaseSqlite db=new DataBaseSqlite(context);
                    Query query=new Query(context);
                    db.Add_Interest(query.getsubsetID(star.getTag().toString()),query.getMemberId());
                }
            }

        });


        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        ArrayList<Country> countryList = continentList.get(groupPosition).getCountryList();
        return countryList.size();

    }

    @Override
    public Object getGroup(int groupPosition) {
        return continentList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return continentList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isLastChild, View view,
                             ViewGroup parent) {

        Continent continent = (Continent) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.group_item, null);
        }

        TextView heading = (TextView) view.findViewById(R.id.laptop1);
        heading.setText(continent.getName().trim());

        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void filterData(String query){

        query = query.toLowerCase();
        Log.v("MyListAdapter", String.valueOf(continentList.size()));
        continentList.clear();
        //originalList.clear();

        if(query.isEmpty()){
            continentList.addAll(originalList);
        }
        else {

            for(Continent continent: originalList){

                ArrayList<Country> countryList = continent.getCountryList();
                ArrayList<Country> newList = new ArrayList<Country>();
                for(Country country: countryList){
                    if(country.getName().toLowerCase().contains(query)){
                        newList.add(country);
                        notifyDataSetChanged();
                    }
                }
                if(newList.size() > 0){
                    Continent nContinent = new Continent(continent.getName(),newList,33);
                    continentList.add(nContinent);
                }
            }
        }

        Log.v("MyListAdapter", String.valueOf(continentList.size()));
        notifyDataSetChanged();

    }
}
