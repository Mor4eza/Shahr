package com.ariana.shahre_ma;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetBookMarkJson;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetCityJson;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetSubsetJson;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.software.shell.fab.ActionButton;

import github.chenupt.dragtoplayout.DragTopLayout;


public class MainActivity extends ActionBarActivity {
    FragmentPagerAdapter adapterViewPager;
    SliderLayout slider;
    DragTopLayout top;

    Query query=new Query(this);
    private static final int PROFILE_SETTING = 1;
    private AccountHeader.Result headerResult = null;
    private Drawer.Result result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //findViewsAndConfigure();

        NetState ns=new NetState(this);
        if(ns.checkInternetConnection()==false) {
            Toast.makeText(getApplication(),"شبکه اینترنت قطع می باشد",Toast.LENGTH_LONG).show();
        }
        else {

            HTTPGetBookMarkJson b=new HTTPGetBookMarkJson(this);
            b.SetUrl_MemberId(query.getMemberId());
            b.execute();

           HTTPGetSubsetJson httpsubset = new HTTPGetSubsetJson(this);
            httpsubset.execute();


            HTTPGetCityJson httpcity = new HTTPGetCityJson(this);
            httpcity.execute();


       /*    HTTPGetCollectionJson httpcoll = new HTTPGetCollectionJson(this);
            httpcoll.execute();*/
        }


        ViewPager vpPager = (ViewPager) findViewById(R.id.viewPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);

        Image_slider();
        navigation();
        fab();
       // Hints();
    }

    public void fab_click(View v){
        Intent i = new Intent(getApplicationContext(),Log_In.class);
        startActivity(i);
    }

    public void fab (){


        ActionButton Action = (ActionButton)findViewById(R.id.action_button);
        Action.setButtonColor(getResources().getColor(R.color.fab_material_blue_500));
        Action.setImageDrawable(getResources().getDrawable(R.drawable.fab_plus_icon));



    }






    private void Image_slider(){
        top =(DragTopLayout)findViewById(R.id.top);

        slider = (SliderLayout)findViewById(R.id.slider);

        TextSliderView textSliderView = new TextSliderView(this);
        textSliderView
                .description("چهار باغ")
                .image(R.drawable.charbagh);
        slider.addSlider(textSliderView);


        TextSliderView textSliderView2 = new TextSliderView(this);
        textSliderView2
                .description("هفت خان")
                .image(R.drawable.haftkhan);
        slider.addSlider(textSliderView2);


        TextSliderView textSliderView3 = new TextSliderView(this);
        textSliderView3
                .description("تیراژه")
                .image(R.drawable.rest_tirajhe);
        slider.addSlider(textSliderView3);

        slider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);


    }


    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment

                    return Frag_main_1.newInstance(1,"new");
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return Frag_main_1.newInstance(2, "Page # 2");
                case 2: // Fragment # 1 - This will show SecondFragment
                    return Frag_main_1.newInstance(3, "Page # 3");
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "مشاغل برتر";
                case 1:
                    return "تخفیف های برتر";
                case 2:
                return "برترین های بازار";

            }
          return null;
        }
    }



    /* private void findViewsAndConfigure() {
         ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
         viewPager.setOffscreenPageLimit(4);

         ArrayList<TestFragment> fragments = new ArrayList<TestFragment>();
         //fragments.add(new job_details_1());
         fragments.add(TestFragment.newInstance("پیشنهادات هفته", "#ffffff"));
         fragments.add(TestFragment.newInstance("اطراف من", "#ffffff"));
         fragments.add(TestFragment.newInstance("تخفیف ها", "#ffffff"));



         viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments));

     }*/
    public void navigation() {

        String uName="شهرما";
        try {
            DataBaseSqlite dbs = new DataBaseSqlite(this);
            Cursor allrows = dbs.select_Member_Name();
            allrows.moveToNext();
            uName = allrows.getString(0);
            allrows.close();
        }
        catch (Exception e) {
            //Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }
        //drawer


        final IProfile profile = new ProfileDrawerItem().withName(uName).withIcon(getResources().getDrawable(R.mipmap.profile3));


        headerResult = new AccountHeader()
                .withActivity(this)
                .withHeaderBackground(R.mipmap.header)
                .addProfiles(profile)
                .withSelectionListEnabledForSingleProfile(false)
                .withHeightDp(150)
                .withTranslucentStatusBar(true)

                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {

                    @Override

                    public void onProfileChanged(View view, IProfile profile) {

                    }
                })

                        //.withSavedInstance( savedInstanceState)
                .build();
        ////////////////////////
        Drawer drawer = new Drawer();
        drawer.withActivity(this);
        drawer.withTranslucentStatusBar(true);
        drawer.withHeaderDivider(true);

        drawer.withActionBarDrawerToggleAnimated(true);
        drawer.withAccountHeader(headerResult);
        drawer.addDrawerItems(
                new PrimaryDrawerItem().withName(R.string.Works).withIcon(FontAwesome.Icon.faw_money),
                new PrimaryDrawerItem().withName(R.string.Off).withIcon(FontAwesome.Icon.faw_coffee),
                new PrimaryDrawerItem().withName(R.string.Near).withIcon(FontAwesome.Icon.faw_map_marker),

                new SectionDrawerItem().withName(R.string.My_city),
                new SecondaryDrawerItem().withName(R.string.My_Account).withIcon(FontAwesome.Icon.faw_user),
                new SecondaryDrawerItem().withName(R.string.My_Fav).withIcon(FontAwesome.Icon.faw_heart_o),
                new SecondaryDrawerItem().withName(R.string.My_city).withIcon(FontAwesome.Icon.faw_building),

                new SectionDrawerItem().withName("برنامه"),
                new SecondaryDrawerItem().withName(R.string.action_settings).withIcon(FontAwesome.Icon.faw_gears),
                new DividerDrawerItem()
        );
        Drawer drawer1 = drawer.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                if (drawerItem instanceof Nameable) {
                    Toast.makeText(MainActivity.this, MainActivity.this.getString(((Nameable) drawerItem).getNameRes()), Toast.LENGTH_SHORT).show();


                }
                if (position == 1) {

                    Intent i=new Intent(getApplicationContext(),Jobs.class);
                    startActivity(i);
                }
                if (position==6){
                    Intent i=new Intent(getApplicationContext(),BookMark.class);
                    startActivity(i);
                }
            }
        });
        drawer.build();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

}

