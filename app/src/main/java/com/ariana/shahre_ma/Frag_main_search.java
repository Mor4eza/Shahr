package com.ariana.shahre_ma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.Fields.FieldDataBusiness;
import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetOnlineSearchJson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ariana2 on 7/16/2015.
 */
public class Frag_main_search extends Fragment
    {
    private String title;
    private int page;
    private Button btnSearch;
    private TextView txtWhat;
    private TextView txtWhere;
    String selectedWord[]=new String[]{"","","","",""};
    FieldClass fc=new FieldClass();
    FieldDataBusiness fdb=new FieldDataBusiness();
    Cursor rows_Business;
    Cursor rows_Collection;
    Cursor rows_Subset;
    Cursor rows_FieldActivity;
    int length = 0;



    private List<Integer>  selectId=new ArrayList<>();
    private  List<String>  selectLongtiude=new ArrayList<>();
    private  List<String>  selectLatitude=new ArrayList<>();
    private  List<Double>  selectRate=new ArrayList<>();
    private  List<String>  selectPhone=new ArrayList<String>();
    private  List<String>  selectMobile=new ArrayList<String>();
    private  List<String>  selectAddress=new ArrayList<>();
    private  List<String>  selectMarketName=new ArrayList<String>();

    // newInstance constructor for creating fragment with arguments
    public static Frag_main_search newInstance(int page, String title) {
        Frag_main_search fragmentFirst = new Frag_main_search();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_main_search, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.tvLabel);
        tvLabel.setText(page + " -- " + title);
        btnSearch=(Button)view.findViewById(R.id.btn_search);
        txtWhat=(EditText)view.findViewById(R.id.et_search_what);
        txtWhere=(EditText)view.findViewById(R.id.et_search_where);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetState ns= new NetState(getActivity());
                DataBaseSqlite db=new DataBaseSqlite(getActivity());

                int i=0;
                selectedWord[0]="";
                selectedWord[1]="";
                selectedWord[2]="";
                selectedWord[3]="";
                selectedWord[4]="";
                int startSelection = txtWhat.getSelectionStart();


                for(String currentWord : txtWhat.getText().toString().split(" ")) {

                    if(i<=4)
                    {
                          selectedWord[i] = currentWord;
                          i++;
                    }
                    Log.i("length",String.valueOf(length));

                }


                //جستجو کامل متن در نام مشاغل و آدرس
                    rows_Business=db.select_BusinessSearch(txtWhat.getText().toString());
                         Log.i("BusinessgetCount",String.valueOf(rows_Business.getCount()));
                       if(rows_Business.getCount()>0)
                       {
                           Log.i("Businessget","on");
                           if(rows_Business.moveToFirst())
                           {
                               do
                               {
                                   selectAddress.add(rows_Business.getString(8));
                                   selectMarketName.add(rows_Business.getString(1));
                                   selectPhone.add(rows_Business.getString(3));
                                   selectMobile.add(rows_Business.getString(4));
                                   selectId.add(rows_Business.getInt(0));
                                   selectLatitude.add(rows_Business.getString(16));
                                   selectLongtiude.add(rows_Business.getString(15));
                                   selectRate.add(rows_Business.getDouble(30));

                               }while (rows_Business.moveToNext());


                           }

                           //نمایش به کاربر

                           fdb.SetIdBusiness(selectId);
                           fdb.SetLatitudeBusiness(selectLatitude);
                           fdb.SetLongtiudeBusiness(selectLongtiude);
                           fdb.SetRateBusiness(selectRate);
                           fdb.SetAddressBusiness(selectAddress);
                           fdb.SetMarketBusiness(selectMarketName);
                           fdb.SetPhoneBusiness(selectPhone);
                           fdb.SetMobileBusiness(selectMobile);

                           fc.SetSearchOffline(true);
                           Intent intent = new Intent(getActivity(), Jobs_List.class);
                           getActivity().startActivity(intent);
                       }
                       else
                       {
                           //جستجو کلمه اول در مجموعه
                           rows_Collection=db.select_Collection(selectedWord[0]);
                           Log.i("CollectiongetCount",String.valueOf(rows_Collection.getCount()));
                           if(rows_Collection.getCount()>0)
                           {
                               Log.i("Collection","on");
                               rows_Collection.moveToFirst();
                               //جستجوآی دی مجوعه در زیر مجموعه
                               rows_Subset=db.select_SubsetId(rows_Collection.getInt(0));
                               Log.i("SubsetgetCount",String.valueOf(rows_Subset.getCount()));
                               rows_Subset.moveToFirst();
                               //جستجو آی دی زیر مجموعه و کلمات دیگر در مشاغل
                               rows_Business=db.select_BusinessSearch(selectedWord[1],selectedWord[2],selectedWord[2],selectedWord[3],selectedWord[4],rows_Subset.getInt(0));
                               if(rows_Business.moveToFirst())
                               {
                                   do
                                   {
                                       selectAddress.add(rows_Business.getString(8));
                                       selectMarketName.add(rows_Business.getString(1));
                                       selectPhone.add(rows_Business.getString(3));
                                       selectMobile.add(rows_Business.getString(4));
                                       selectId.add(rows_Business.getInt(0));
                                       selectLatitude.add(rows_Business.getString(16));
                                       selectLongtiude.add(rows_Business.getString(15));
                                       selectRate.add(rows_Business.getDouble(30));

                                   }while (rows_Business.moveToNext());


                               }
                               //نمایش به کاربر

                               fdb.SetIdBusiness(selectId);
                               fdb.SetLatitudeBusiness(selectLatitude);
                               fdb.SetLongtiudeBusiness(selectLongtiude);
                               fdb.SetRateBusiness(selectRate);
                               fdb.SetAddressBusiness(selectAddress);
                               fdb.SetMarketBusiness(selectMarketName);
                               fdb.SetPhoneBusiness(selectPhone);
                               fdb.SetMobileBusiness(selectMobile);

                               fc.SetSearchOffline(true);
                               Intent intent = new Intent(getActivity(), Jobs_List.class);
                               getActivity().startActivity(intent);
                           }
                           else
                           {
                               //جستجو کلمه اول در زیرمجموعه
                               rows_Subset=db.select_SubsetId(selectedWord[0]);
                               Log.i("SubsetgetCount",String.valueOf(rows_Subset.getCount()));
                               if(rows_Subset.getCount()>0)
                               {
                                   Log.i("Subsetget","on");
                                   rows_Subset.moveToFirst();
                                   //جستجو آی دی زیر مجموعه و کلمات دیگر در مشاغل
                                   rows_Business=db.select_BusinessSearch(selectedWord[1],selectedWord[1],selectedWord[2],selectedWord[3],selectedWord[4],rows_Subset.getInt(0));
                                   if(rows_Business.moveToFirst())
                                   {
                                       do
                                       {
                                           selectAddress.add(rows_Business.getString(8));
                                           selectMarketName.add(rows_Business.getString(1));
                                           selectPhone.add(rows_Business.getString(3));
                                           selectMobile.add(rows_Business.getString(4));
                                           selectId.add(rows_Business.getInt(0));
                                           selectLatitude.add(rows_Business.getString(16));
                                           selectLongtiude.add(rows_Business.getString(15));
                                           selectRate.add(rows_Business.getDouble(30));

                                       }while (rows_Business.moveToNext());


                                   }
                                   //نمایش به کاربر

                                   fdb.SetIdBusiness(selectId);
                                   fdb.SetLatitudeBusiness(selectLatitude);
                                   fdb.SetLongtiudeBusiness(selectLongtiude);
                                   fdb.SetRateBusiness(selectRate);
                                   fdb.SetAddressBusiness(selectAddress);
                                   fdb.SetMarketBusiness(selectMarketName);
                                   fdb.SetPhoneBusiness(selectPhone);
                                   fdb.SetMobileBusiness(selectMobile);


                                   fc.SetSearchOffline(true);
                                   Intent intent = new Intent(getActivity(), Jobs_List.class);
                                   getActivity().startActivity(intent);
                               }
                               else
                               {

                               }
                           }
                       }




            }
        });

        return view;
    }
}
