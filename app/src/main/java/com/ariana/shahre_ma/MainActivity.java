package com.ariana.shahre_ma;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ariana.shahre_ma.Bookmarks.BookMark;
import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.MyBusiness.My_Business;
import com.ariana.shahre_ma.MyCity.My_city;
import com.ariana.shahre_ma.MyInterest.My_Interest;
import com.ariana.shahre_ma.MyProfile.Log_In;
import com.ariana.shahre_ma.MyProfile.My_Profile;
import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.Notification.Activity_notify;
import com.ariana.shahre_ma.Service.TimeSetReceiver;
import com.ariana.shahre_ma.Settings.Setting;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetTopsBusinessJson;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.software.shell.fab.ActionButton;

import github.chenupt.dragtoplayout.DragTopLayout;


public class MainActivity extends AppCompatActivity {

    FragmentPagerAdapter adapterViewPager;
    public static SliderLayout slider;
    DragTopLayout top;
    ActionButton Action;
    NetState net = new NetState(this);
    Query query = new Query(this);
    private static final int PROFILE_SETTING = 1;
    private AccountHeader headerResult = null;
    private Drawer result = null;
    private WindowManager mWindowManager;
    private ImageView mImgFloatingView;
    DataBaseSqlite db=new DataBaseSqlite(this);
    IntentFilter ii;
    TimeSetReceiver tsr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //findViewsAndConfigure();

       /*String url= "http://uplod.ir/tpy6oft0407u/app-debug.apk.htm";
        HTTPGetUpdate update=new HTTPGetUpdate(this);
        update.execute(url);*/

        top = (DragTopLayout) findViewById(R.id.top);
        slider = (SliderLayout) findViewById(R.id.slider);

        top.setTouchMode(true);
        top.setOverDrag(false);

        ii=new IntentFilter("android.intent.action.TIME_TICK");
        tsr=new TimeSetReceiver();
        registerReceiver(tsr,ii);

        setup();

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-event-name"));

        if (net.checkInternetConnection() == false) {
            Toast.makeText(getApplication(), "شبکه اینترنت قطع می باشد", Toast.LENGTH_LONG).show();
        } else {

           /* HTTPGetCollectionJson httpcoll = new HTTPGetCollectionJson(this);
            httpcoll.execute();

            HTTPGetSubsetJson httpsubset = new HTTPGetSubsetJson(this);
            httpsubset.execute();


            HTTPGetCityJson httpcity = new HTTPGetCityJson(this);
            httpcity.execute();*/

           /*  HTTPGetAdvertismentJson httpGetAdvertismentJson=new HTTPGetAdvertismentJson(this);
            httpGetAdvertismentJson.SetAdvertisment(68);
            httpGetAdvertismentJson.execute();*/


           HTTPGetTopsBusinessJson httpGetTopsBusinessJson=new HTTPGetTopsBusinessJson(this);
            httpGetTopsBusinessJson.SetTopBusiness(68);
            httpGetTopsBusinessJson.execute();
        }

        ViewPager vpPager = (ViewPager) findViewById(R.id.viewPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        vpPager.setCurrentItem(1);
        fab();
        try {
            DataBaseSqlite db = new DataBaseSqlite(this);
            Cursor allrows = db.select_Member();
            allrows.moveToNext();
            if (allrows.getString(6).equals(null)) {
                //Action.show();
            } else {
                //  Action.dismiss();
            }


        } catch (Exception e) {
        }

        Image_slider();
        navigation();

    }

    public void fab_click(View v) {
        Intent i = new Intent(getApplicationContext(), Log_In.class);
        startActivity(i);
    }

    public void fab() {


        Action = (ActionButton) findViewById(R.id.action_button);
        Action.setButtonColor(getResources().getColor(R.color.fab_material_blue_500));
        Action.setImageDrawable(getResources().getDrawable(R.drawable.fab_plus_icon));


    }


    private void Image_slider() {

        String imag[]=new String[2];
        Integer i=0;
        DataBaseSqlite db=new DataBaseSqlite(this);
        Cursor rows=db.select_Advertisment();

        if(rows.moveToFirst())
        {
            do
            {
                //imag[i]=rows.getString(1);
                Log.i("imag",rows.getString(1));
               // i++;
            }while (rows.moveToNext());
        }


        final TextSliderView textSliderView = new TextSliderView(this);
        textSliderView
                .description("چهار باغ")
               .image("http://www.shahrma.com/app/Advertisment/");
        slider.addSlider(textSliderView);

        TextSliderView textSliderView2 = new TextSliderView(this);
        textSliderView2
                .description("هفت خان")
                .image("http://www.shahrma.com/app/Advertisment/");
        slider.addSlider(textSliderView2);


        TextSliderView textSliderView3 = new TextSliderView(this);
        textSliderView3
                .description("تیراژه")
                .image("http://www.shahrma.com/app/Advertisment/");
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

                    return Frag_main_Top_Business.newInstance(1, "new");
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return Frag_main_Top_discount.newInstance(2, "Page # 2");
                case 2: // Fragment # 1 - This will show SecondFragment
                    return Frag_main_search.newInstance(3, "Page # 3");
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "مشاغل برتر";
                case 1:
                    return "تخفیف های برتر";
                case 2:
                    return "جستجو";

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

        String uName = "شهرما";
        try {
            DataBaseSqlite dbs = new DataBaseSqlite(this);
            Cursor allrows = dbs.select_Member_Name();
            allrows.moveToNext();
            uName = allrows.getString(0);
            allrows.close();
        } catch (Exception e) {
            //Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }
        //drawer


        final IProfile profile = new ProfileDrawerItem().withName(uName).withEmail("کرج").withTextColor(R.color.red).withIcon(getResources().getDrawable(R.mipmap.profile3));

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.mipmap.header)
                .addProfiles(profile)
                .withSelectionListEnabledForSingleProfile(false)
                .withHeightDp(150)
                .withTranslucentStatusBar(true)

                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {


                    @Override
                    public boolean onProfileChanged(View view, IProfile iProfile, boolean b) {
                        return false;
                    }
                })

                        //.withSavedInstance( savedInstanceState)
                .build();
        ////////////////////////
        result = new DrawerBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(false)
                .withHeaderDivider(true)
                .withDisplayBelowToolbar(false)
                .withActionBarDrawerToggle(false)
                .withActionBarDrawerToggleAnimated(false)
                .withAccountHeader(headerResult)
                .withAnimateDrawerItems(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.Works).withIcon(FontAwesome.Icon.faw_money),
                        new PrimaryDrawerItem().withName(R.string.Off).withIcon(FontAwesome.Icon.faw_coffee),
                        new PrimaryDrawerItem().withName(R.string.My_business).withIcon(FontAwesome.Icon.faw_briefcase),
                        new DividerDrawerItem(),
                        //  new SectionDrawerItem().withName(""),
                        new SecondaryDrawerItem().withName(R.string.My_Account).withIcon(FontAwesome.Icon.faw_user),
                        new SecondaryDrawerItem().withName(R.string.My_Fav).withIcon(FontAwesome.Icon.faw_bookmark),
                        new SecondaryDrawerItem().withName(R.string.My_city).withIcon(FontAwesome.Icon.faw_building),
                        new SecondaryDrawerItem().withName(R.string.My_Intrest).withIcon(FontAwesome.Icon.faw_heart),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.action_settings).withIcon(FontAwesome.Icon.faw_gears),
                        new DividerDrawerItem()
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        if (drawerItem instanceof Nameable) {
                            Toast.makeText(MainActivity.this, MainActivity.this.getString(((Nameable) drawerItem).getNameRes()), Toast.LENGTH_SHORT).show();


                        }
                        if (position == 0) {

                            Intent i = new Intent(getApplicationContext(), Jobs.class);
                            startActivity(i);

                        }
                        if (position == 1) {

                            Intent i = new Intent(getApplicationContext(), SplashActivity.class);
                            startActivity(i);

                        }
                        if (position == 2) {

                            if (net.checkInternetConnection() == false) {
                                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                                alertDialog.setTitle("هشدار");
                                alertDialog.setMessage("اینترنت قطع می باشد");
                                alertDialog.setButton("خب", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                alertDialog.show();
                            } else {

                                if (query.getMemberId() > 0) // get member
                                {
                                    // Getting Business Member
                                    Intent i = new Intent(MainActivity.this, My_Business.class);
                                    startActivity(i);


                                } else {
                                    Toast.makeText(getApplicationContext(), "کاربری وارد نشده", Toast.LENGTH_SHORT).show();

                                }
                            }

                        }
                        if (position == 4) {

                            try {
                                DataBaseSqlite db = new DataBaseSqlite(MainActivity.this);
                                Cursor cursor = db.select_Member();
                                cursor.moveToFirst();
                                Log.i("memberID", String.valueOf(cursor.getInt(0)));
                                if (cursor.getInt(0) > 0) {
                                    Intent i = new Intent(getApplicationContext(), My_Profile.class);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(getApplicationContext(), "وارد حساب خود نشده اید...!", Toast.LENGTH_LONG).show();

                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "وارد حساب خود نشده اید...!", Toast.LENGTH_LONG).show();
                            }
                        }
                        if (position == 5) {
                            Intent i = new Intent(getApplicationContext(), BookMark.class);
                            startActivity(i);
                        }
                        if (position == 6) {

                            Intent i = new Intent(getApplicationContext(), My_city.class);
                            startActivity(i);
                        }
                        if (position == 7) {

                            Intent i = new Intent(getApplicationContext(), My_Interest.class);
                            startActivity(i);
                        }
                        if (position == 9) {

                            Intent i = new Intent(getApplicationContext(), Setting.class);
                            startActivity(i);
                        }
                        return false;
                    }
                }).build();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //  getSupportActionBar().setIcon(R.drawable.ic_action_menu);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity, menu);

        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (result.isDrawerOpen())
                    result.closeDrawer();
                else {
                    result.openDrawer();
                }
                return true;
            case R.id.notification:
                    Intent i=new Intent(getApplicationContext(), Activity_notify.class);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    void setup() {


        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mImgFloatingView = new ImageView(this);
        mImgFloatingView.setImageResource(R.drawable.floating_search);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_CHANGED,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.height = 200;
        params.width = 250;
        params.x = 100;
        params.y = 220;
        mWindowManager.addView(mImgFloatingView, params);

        mImgFloatingView.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            int clicked;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();


                        clicked = 1;
                        return false;

                    case MotionEvent.ACTION_UP:

                        if (clicked == 1) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("جستجو...");

                            final EditText input = new EditText(MainActivity.this);
                            input.setInputType(InputType.TYPE_CLASS_TEXT);
                            builder.setView(input);
                            builder.show();


                        } else {
                        return false;
                        }
                        return false;
                    case MotionEvent.ACTION_MOVE:
                        params.x = initialX + (int) (event.getRawX() - initialTouchX );
                        params.y = initialY + (int) (event.getRawY() - initialTouchY );
                        mWindowManager.updateViewLayout(mImgFloatingView, params);
                        clicked = 0;
                        return true;
                }
                return false;
            }
        });


        TranslateAnimation animation = new TranslateAnimation(0, 200, 0, 300);

        mImgFloatingView.startAnimation(animation);
    }
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            Log.d("receiver", "Got message: " + message);
        }
    };
}
