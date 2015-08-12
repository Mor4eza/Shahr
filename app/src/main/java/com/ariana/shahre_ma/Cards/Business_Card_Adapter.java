package com.ariana.shahre_ma.Cards;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.Fields.FieldDataBusiness;
import com.ariana.shahre_ma.MyBusiness.Discount;
import com.ariana.shahre_ma.MyBusiness.Edit_business;
import com.ariana.shahre_ma.MyBusiness.My_Business;
import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.job_details.Job_details;
import com.github.alexkolpa.fabtoolbar.FabToolbar;

import java.util.ArrayList;
import java.util.List;

public class Business_Card_Adapter extends RecyclerView.Adapter<Business_Card_Adapter.ViewHolder> {

    List<Business_Card_Items> mItems;
    FieldClass fc=new FieldClass();
    private  static Context context;
    Business_Card_Items nature;
    NetState net;
    FieldDataBusiness fdb=new FieldDataBusiness();

    public Business_Card_Adapter(Context context) {
        super();
        this.context=context;

        net=new NetState(context);
            DataBaseSqlite db = new DataBaseSqlite(context);

            mItems = new ArrayList<Business_Card_Items>();

        nature = new Business_Card_Items();

        if(net.checkInternetConnection())
        {
            for (int i = 0; i < fdb.GetMarketBusiness().size(); i++)
            {

                nature = new Business_Card_Items();

                nature.setId(fdb.GetIdBusiness().get(i));
                nature.setName(fdb.GetMarketBusiness().get(i));
                nature.setmAddress(fdb.GetAddressBusiness().get(i));
                nature.setThumbnail(R.drawable.pooshak1);
                nature.setRate(fdb.GetRateBusiness().get(i));
                mItems.add(nature);

            }
        }
        else
        {

            Cursor rows = db.select_AllBusinessId(fc.GetBusiness_Id());
            if (rows.moveToFirst()) {
                do {

                    nature = new Business_Card_Items();
                    nature.setId(rows.getInt(0));
                    nature.setName(rows.getString(1));
                    nature.setmAddress(rows.getString(7));
                    nature.setThumbnail(R.drawable.pooshak1);
                    nature.setRate(rows.getFloat(19));
                    mItems.add(nature);

                } while (rows.moveToNext());
            }

        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.business_cards, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Business_Card_Items nature = mItems.get(i);
        viewHolder.tvNature.setText(nature.getName());
        viewHolder.tvNature.setTag(nature.getId());
       // viewHolder.tvDesNature.setText(nature.getmAddress());
        viewHolder.img1.setImageResource(nature.getThumbnail());
        viewHolder.Rates.setRating((float)nature.getRate());
    }


    @Override
    public int getItemCount() {

        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView img1;
        public ImageView img2;
        public ImageView img3;
        public ImageView img4;
        public ImageView img_edit;
        public ImageView img_discount;
        public ImageView img_pic;
        public TextView tvNature;
        public RatingBar Rates;
        public FabToolbar fab;
        public Button menu1;
        public Button menu2;
        public Button menu3;
        public Button menu4;
        Uri currImageURI;
        My_Business my_business=new My_Business();
        public ViewHolder(View itemView) {
            super(itemView);

            img1 = (ImageView)itemView.findViewById(R.id.my_business_image1);
            tvNature = (TextView)itemView.findViewById(R.id.my_business_title);
            img_edit = (ImageView)itemView.findViewById(R.id.btn_edit_business);
            img_discount = (ImageView)itemView.findViewById(R.id.btn_discount);
            img_pic = (ImageView)itemView.findViewById(R.id.btn_change);
            Rates = (RatingBar)itemView.findViewById(R.id.my_business_rate);
            fab=(FabToolbar)itemView.findViewById(R.id.fab_toolbar);
            menu1=(Button)itemView.findViewById(R.id.overflow1);
            menu2=(Button)itemView.findViewById(R.id.overflow2);
            menu3=(Button)itemView.findViewById(R.id.overflow3);
            menu4=(Button)itemView.findViewById(R.id.overflow4);


            fab.setButtonIcon(context.getResources().getDrawable(R.drawable.abc_ic_menu_moreoverflow_mtrl_alpha));

            menu1.setOnClickListener(this);
            menu2.setOnClickListener(this);
            menu3.setOnClickListener(this);
            menu4.setOnClickListener(this);

            img_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fab.hide();
                    Log.i("Idbusiness",String.valueOf(tvNature.getTag()));
                    fc.SetBusiness_Id(Integer.valueOf(String.valueOf(tvNature.getTag())));
                    Intent i = new Intent(context, Edit_business.class);
                    context.startActivity(i);
                    Log.i("clicked", tvNature.getText().toString());
                }
            });

            img_discount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fab.hide();
                    Intent i = new Intent(context, Discount.class);
                    context.startActivity(i);
                    Log.i("clicked", tvNature.getText().toString());
                }
            });

            img_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fab.hide();
                    if (menu1.getVisibility()==View.INVISIBLE){

                        menu1.setVisibility(View.VISIBLE);
                        menu2.setVisibility(View.VISIBLE);
                        menu3.setVisibility(View.VISIBLE);
                        menu4.setVisibility(View.VISIBLE);
                    }else{
                        menu1.setVisibility(View.INVISIBLE);
                        menu2.setVisibility(View.INVISIBLE);
                        menu3.setVisibility(View.INVISIBLE);
                        menu4.setVisibility(View.INVISIBLE);
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    Log.i("ON_______CLICK", tvNature.getText().toString());
                    fab.hide();

                }
            });
            img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fc.SetBusiness_Id(Integer.valueOf(String.valueOf(tvNature.getTag())));
                    Intent i=new Intent(context, Job_details.class);
                    context.startActivity(i);
                }
            });

        }

        @Override
        public void onClick(final View v) {
                v.setTag(String.valueOf(v.getId()));
                PopupMenu popup = new PopupMenu(context, v);
                popup.getMenuInflater().inflate(R.menu.image_popup, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(context, "Clicked : " + item.getTitle() + v.getTag(), Toast.LENGTH_SHORT).show();


                        menu1.setVisibility(View.INVISIBLE);
                        menu2.setVisibility(View.INVISIBLE);
                        menu3.setVisibility(View.INVISIBLE);
                        menu4.setVisibility(View.INVISIBLE);

                        if (item.getTitle().equals("دوربین"))
                            //my_business.openCamera();
                        openCamera();
                        else if (item.getTitle().equals("گالری"))
                            selectImageFromGallery();

                        else if (item.getTitle().equals("حذف"))
                            Log.i("", "");
                        return true;
                    }
                });
                popup.show();//showing popup menu

        }
        public void openCamera() {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, currImageURI);
            ((Activity)context).startActivityForResult(intent, 100);

        }
        public void selectImageFromGallery() {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            ((Activity)context).startActivityForResult(Intent.createChooser(intent,"انتخاب کنید"), 1);
        }

    }
}