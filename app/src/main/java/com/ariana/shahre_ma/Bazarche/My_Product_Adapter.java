package com.ariana.shahre_ma.Bazarche;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.Fields.FieldDataBase;
import com.ariana.shahre_ma.R;
import com.github.alexkolpa.fabtoolbar.FabToolbar;

import java.util.ArrayList;
import java.util.List;

public class My_Product_Adapter extends RecyclerView.Adapter<My_Product_Adapter.ViewHolder> {

    List<My_Product_Items> mItems;
    FieldClass fc=new FieldClass();
    private  static Context context;
    My_Product_Items nature;
    FieldDataBase fieldDataBase=new FieldDataBase();
    Query query=new Query(context);

    public My_Product_Adapter(Context context) {
        super();
        this.context=context;

        mItems = new ArrayList<My_Product_Items>();

        nature = new My_Product_Items();

               /* nature = new My_Product_Items();

                nature.setId(1);
                nature.setName("تست");
                nature.setmPrice("10000");
                nature.setThumbnail(R.drawable.pooshak1);
                mItems.add(nature);*/

        for(int i=0;i<fieldDataBase.getName_Product().size();i++)
        {
            nature = new My_Product_Items();

            nature.setId(fieldDataBase.getId_Product().get(i));
            nature.setmI(i);
            nature.setName(fieldDataBase.getName_Product().get(i));
            nature.setmPrice(fieldDataBase.getprice_Product().get(i));
            nature.setThumbnail(R.drawable.pooshak1);
            mItems.add(nature);
        }



    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.product_cards, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        My_Product_Items nature = mItems.get(i);
        viewHolder.tvNature.setText(nature.getName());
        viewHolder.tvNature.setTag(nature.getId());
        viewHolder.Price.setText(String.valueOf(nature.getmPrice()));
        viewHolder.Price.setTag(nature.getmI());
       // viewHolder.tvDesNature.setText(nature.getmAddress());
        viewHolder.img1.setImageResource(nature.getThumbnail());
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
        public ImageView img_pic;
        public TextView tvNature;
        public TextView Price;
        public FabToolbar fab;
        public Button menu1;
        public Button menu2;
        public Button menu3;
        public Button menu4;
        Uri currImageURI;
        My_products my_business=new My_products();

        public ViewHolder(View itemView) {
            super(itemView);

            img1 = (ImageView)itemView.findViewById(R.id.my_product_image1);
            tvNature = (TextView)itemView.findViewById(R.id.my_product_title);
            img_edit = (ImageView)itemView.findViewById(R.id.btn_edit_product);
            img_pic = (ImageView)itemView.findViewById(R.id.btn_change_image);
            Price = (TextView)itemView.findViewById(R.id.my_product_price);
            fab=(FabToolbar)itemView.findViewById(R.id.product_toolbar);
            menu1=(Button)itemView.findViewById(R.id.p_overflow1);
            menu2=(Button)itemView.findViewById(R.id.p_overflow2);
            menu3=(Button)itemView.findViewById(R.id.p_overflow3);
            menu4=(Button)itemView.findViewById(R.id.p_overflow4);


            fab.setButtonIcon(context.getResources().getDrawable(R.drawable.abc_ic_menu_moreoverflow_mtrl_alpha));

            menu1.setOnClickListener(this);
            menu2.setOnClickListener(this);
            menu3.setOnClickListener(this);
            menu4.setOnClickListener(this);

            img_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fab.hide();
                    Intent i = new Intent(context,Edit_Product.class);
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

                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.i("ON_______CLICK", tvNature.getTag().toString());


                    fc.SetProductId(Integer.valueOf(String.valueOf(tvNature.getTag())));
                    fc.SetNumber(Integer.valueOf(String.valueOf(Price.getTag())));
                    Intent i=new Intent(context, Edit_Product.class);
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
        public void selectImageFromGallery()
        {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            ((Activity)context).startActivityForResult(Intent.createChooser(intent,"انتخاب کنید"), 1);
        }




    }
}