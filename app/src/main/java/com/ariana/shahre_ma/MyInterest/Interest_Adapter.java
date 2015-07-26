package com.ariana.shahre_ma.MyInterest;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.MyCity.TotalListener;
import com.ariana.shahre_ma.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ABHISHEK on 5/12/2015.
 */
public class Interest_Adapter extends BaseExpandableListAdapter {

    private ArrayList<ArrayList<String>> mGroupList = new ArrayList<>();

    /*
     *  Raw Data
     */


    String[] subset ;
    String[] collection ;

    Integer[] id_collection;
    Integer[] id_subset;
    FieldClass fc=new FieldClass();
    private List<String> selectedsubset=new ArrayList<String>();
    Context mContext;
    ViewHolder holder;
    ArrayList<ArrayList<Boolean>> selectedChildCheckBoxStates = new ArrayList<>();
    ArrayList<Boolean> selectedParentCheckBoxesState = new ArrayList<>();
    TotalListener mListener;

    public void setmListener(TotalListener mListener) {
        this.mListener = mListener;
    }

    public void setmGroupList(ArrayList<ArrayList<String>> mGroupList) {
        this.mGroupList = mGroupList;
    }

    class ViewHolder {
        public CheckBox groupName;
        public TextView dummyTextView; // View to expand or shrink the list
        public CheckBox childCheckBox;
    }

    /**
     * Constructor
     * @param context
     */
    public Interest_Adapter(Context context) {
        mContext = context;


        DataBaseSqlite db=new DataBaseSqlite(mContext);

        Cursor collection_count=db.select_Collection();

        Cursor subset_count=db.select_Subset();

        Integer ij=0;


        collection=new String[collection_count.getCount()];
        id_collection=new Integer[collection_count.getCount()];


        if(collection_count.moveToFirst())
        {
            do
            {
                id_collection[ij]=collection_count.getInt(0);
                collection[ij]=collection_count.getString(1);

                ij++;
            }while (collection_count.moveToNext());
        }
//**************************************************************************************
        Integer ii=0;
        subset=new String[subset_count.getCount()];
        id_subset=new Integer[subset_count.getCount()];

        if(subset_count.moveToFirst())
        {
            do
            {

                subset[ii]=subset_count.getString(1);
                id_subset[ii]=subset_count.getInt(2);
                ii++;
            }while (subset_count.moveToNext());
        }

        //******************************************************************************
        //Add raw data into Group List Array
        for (int i = 0; i < collection_count.getCount(); i++) {
            ArrayList<String> prices = new ArrayList<>();
            for (int j = 0; j < subset_count.getCount(); j++) {
                if(id_collection[i]==id_subset[j])
                    prices.add(subset[j]);
            }
            mGroupList.add(i, prices);
        }

        //initialize default check states of checkboxes
        initCheckStates(false);
    }



    /**
     * Called to initialize the default check states of items
     *
     * @param defaultState : false
     */
    private void initCheckStates(boolean defaultState) {
        for (int i = 0; i < mGroupList.size(); i++) {
            selectedParentCheckBoxesState.add(i, defaultState);
            ArrayList<Boolean> childStates = new ArrayList<>();
            for (int j = 0; j < mGroupList.get(i).size(); j++) {
                childStates.add(defaultState);
            }

            selectedChildCheckBoxStates.add(i, childStates);
        }
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mGroupList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mGroupList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroupList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return mGroupList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_layout, null);
            holder = new ViewHolder();
            holder.groupName = (CheckBox) convertView.findViewById(R.id.group_chk_box);
            holder.dummyTextView = (TextView) convertView.findViewById(R.id.dummy_txt_view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

       /* Integer i=0;
          // get data of database
         DataBaseSqlite db=new DataBaseSqlite(mContext);
         Cursor rows=db.select_Collection();
         collection=new String[rows.getCount()];
         id_collection=new Integer[rows.getCount()];

         if(rows.moveToFirst())
         {
             do
             {
                 Log.i("Id_C",String.valueOf(rows.getInt(0)));
                 id_collection[i]=rows.getInt(0);
                 collection[i]=rows.getString(1);

                 i++;
             }while (rows.moveToNext());
         }*/

        holder.dummyTextView.setText(collection[groupPosition]);

        if (selectedParentCheckBoxesState.size() <= groupPosition) {
            selectedParentCheckBoxesState.add(groupPosition, false);
        } else {
            holder.groupName.setChecked(selectedParentCheckBoxesState.get(groupPosition));
        }


        holder.groupName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                //Callback to expansion of group item
                if (!isExpanded)
                    mListener.expandGroupEvent(groupPosition, isExpanded);

                boolean state = selectedParentCheckBoxesState.get(groupPosition);
                Log.d("TAG", "STATE = " + state);
                selectedParentCheckBoxesState.remove(groupPosition);
                selectedParentCheckBoxesState.add(groupPosition, state ? false : true);

                for (int i = 0; i < mGroupList.get(groupPosition).size(); i++) {

                    selectedChildCheckBoxStates.get(groupPosition).remove(i);
                    selectedChildCheckBoxStates.get(groupPosition).add(i, state ? false : true);
                }
                notifyDataSetChanged();
                //showTotal(groupPosition);
            }
        });


        //callback to expand or shrink list view from dummy text click
        holder.dummyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                //Callback to expansion of group item
                mListener.expandGroupEvent(groupPosition, isExpanded);
            }
        });

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

       final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_layout, null);
            holder = new ViewHolder();
            holder.childCheckBox = (CheckBox) convertView.findViewById(R.id.child_check_box);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }




        holder.childCheckBox.setText(mGroupList.get(groupPosition).get(childPosition));



        if (selectedChildCheckBoxStates.size() <= groupPosition) {
            ArrayList<Boolean> childState = new ArrayList<>();
            for (int i = 0; i < mGroupList.get(groupPosition).size(); i++) {
                if (childState.size() > childPosition)
                    childState.add(childPosition, false);
                else
                    childState.add(false);
            }
            if (selectedChildCheckBoxStates.size() > groupPosition) {
                selectedChildCheckBoxStates.add(groupPosition, childState);
            } else
                selectedChildCheckBoxStates.add(childState);
        } else {
            holder.childCheckBox.setChecked(selectedChildCheckBoxStates.get(groupPosition).get(childPosition));
        }
        final String selected = (String) getChild(groupPosition, childPosition);
        holder.childCheckBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean state = selectedChildCheckBoxStates.get(groupPosition).get(childPosition);
                selectedChildCheckBoxStates.get(groupPosition).remove(childPosition);
                selectedChildCheckBoxStates.get(groupPosition).add(childPosition, state ? false : true);

                // showTotal(groupPosition);
                if (holder.childCheckBox.isChecked()){
                    selectedsubset.add(selected);
                    fc.SetNameSubset(selectedsubset);
                    Toast.makeText(mContext, selectedsubset.toString(), Toast.LENGTH_LONG).show();
                }else{
                    selectedsubset.remove(selected);
                    fc.SetNameSubset(selectedsubset);
                    Toast.makeText(mContext, selectedsubset.toString(), Toast.LENGTH_LONG).show();

                }

            }
        });
        return convertView;
    }

    /**
     * Called to reflect the sum of checked prices
     *
     * @param groupPosition : group position of list
     */
/*    private void showTotal(int groupPosition) {
        //Below code is to get the sum of checked prices
        int sum = 0;
        for (int j = 0; j < selectedChildCheckBoxStates.size(); j++) {
            Log.d("TAG", "J = " + j);
            for (int i = 0; i < selectedChildCheckBoxStates.get(groupPosition).size(); i++) {
                Log.d("TAG", "I = " + i);

                if (selectedChildCheckBoxStates.get(j).get(i)) {
                  //  sum += Integer.parseInt(mGroupList.get(j).get(i));
                }
            }
        }
        mListener.onTotalChanged(sum);
    }*/

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}