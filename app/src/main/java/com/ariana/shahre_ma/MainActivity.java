package com.ariana.shahre_ma;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ariana.shahre_ma.Bazarche.Bazarche;
import com.ariana.shahre_ma.Bookmarks.BookMark;
import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.MyBusiness.My_Business;
import com.ariana.shahre_ma.MyCity.My_city;
import com.ariana.shahre_ma.MyInterest.My_Interest;
import com.ariana.shahre_ma.MyProfile.Log_In;
import com.ariana.shahre_ma.MyProfile.My_Profile;
import com.ariana.shahre_ma.NearMe.NearMeActivity;
import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.Notification.Activity_notify;
import com.ariana.shahre_ma.Service.TimeSetReceiver;
import com.ariana.shahre_ma.Settings.KeySettings;
import com.ariana.shahre_ma.Settings.Setting;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetAdvertismentJson;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetTopsBusinessJson;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.github.amlcurran.showcaseview.OnShowcaseEventListener;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
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
import jonathanfinerty.once.Once;


public class MainActivity extends ActionBarActivity {

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
    KeySettings setting=new KeySettings(this);
    IntentFilter ii;
    TimeSetReceiver tsr;
    Toolbar toolbar;
    DataBaseSqlite db;



    @Override
    protected void onResume() {
        super.onResume();


        navigation();
        try {
            DataBaseSqlite db = new DataBaseSqlite(this);
            Cursor allrows = db.select_Member();
            allrows.moveToNext();
            if (allrows.getString(6).equals(null))
            {
                Action.setVisibility(View.VISIBLE);
            }
            else
            {
                Action.setVisibility(View.INVISIBLE);
            }

        } catch (Exception e) {
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         db=new DataBaseSqlite(this);


    try {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            fab();


            top = (DragTopLayout) findViewById(R.id.top);
            slider = (SliderLayout) findViewById(R.id.slider);

            top.setTouchMode(true);
            top.setOverDrag(false);

            ii = new IntentFilter("android.intent.action.TIME_TICK");
            tsr = new TimeSetReceiver();
            registerReceiver(tsr, ii);

            //setup();
            LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("custom-event-name"));

            if (net.checkInternetConnection() == false) {
                Toast.makeText(getApplication(), "شبکه اینترنت قطع می باشد", Toast.LENGTH_LONG).show();
            } else {
                  /*  String url= "http://uplod.ir/tpy6oft0407u/app-debug.apk.htm";
                    HTTPGetUpdate update=new HTTPGetUpdate(this);
                    update.execute(url);
                    */

                HTTPGetAdvertismentJson httpGetAdvertismentJson = new HTTPGetAdvertismentJson(this);
                httpGetAdvertismentJson.SetAdvertisment(query.getCityId(setting.getCityName()));
                httpGetAdvertismentJson.execute();


                HTTPGetTopsBusinessJson httpGetTopsBusinessJson = new HTTPGetTopsBusinessJson(this);
                httpGetTopsBusinessJson.SetTopBusiness(query.getCityId(setting.getCityName()));
                httpGetTopsBusinessJson.execute();
            }


            ViewPager vpPager = (ViewPager) findViewById(R.id.viewPager);
            adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
            vpPager.setAdapter(adapterViewPager);
            vpPager.setCurrentItem(2);


            Image_slider();


            String showWhatsNew = "showHelp";

            if (!Once.beenDone(Once.THIS_APP_INSTALL, showWhatsNew)) {
                //help1();
                CityDialog cityDialog = new CityDialog(this);
                cityDialog.show();
                Once.markDone(showWhatsNew);
            }
    }
    catch (Exception e){}

    }

    public void fab_click(View v) {
        Intent i = new Intent(getApplicationContext(), Log_In.class);
       //i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(i);
        finish();
    }

    public void fab() {


        Action = (ActionButton) findViewById(R.id.action_button);
        Action.setImageDrawable(getResources().getDrawable(R.drawable.fab_plus_icon));


    }


    private void Image_slider() {

        String imag[]=new String[3];
        Integer i=0;

        Cursor rows=db.select_Advertisment();

        if(rows.moveToFirst())
        {
            do
            {
                imag[i]=rows.getString(1);
                Log.i("imag" + i, rows.getString(1));
                i++;
            }while (rows.moveToNext());
        }


        final TextSliderView textSliderView = new TextSliderView(this);
        textSliderView
                .description("")
                .image("http://www.shahrma.com/app/Advertisment/" + imag[0]);
        slider.addSlider(textSliderView);

        TextSliderView textSliderView2 = new TextSliderView(this);
        textSliderView2
                .description("")
                .image("http://www.shahrma.com/app/Advertisment/" + imag[1]);
        slider.addSlider(textSliderView2);


        TextSliderView textSliderView3 = new TextSliderView(this);
        textSliderView3
                .description("")
                .image("http://www.shahrma.com/app/Advertisment/" + imag[2]);
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

    public void navigation() {
        KeySettings settings=new KeySettings(this);
        String uName = "شهرما";
        try {
            DataBaseSqlite dbs = new DataBaseSqlite(this);
            Cursor allrows = dbs.select_Member_Name();
            allrows.moveToNext();
            uName = allrows.getString(0);
            allrows.close();
        } catch (Exception e) {

        }



        final IProfile profile = new ProfileDrawerItem().withName(uName).withEmail(settings.getCityName());

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.mipmap.header)
                .addProfiles(profile)
                .withProfileImagesVisible(false)
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
                .withDrawerWidthDp(260)
                .withToolbar(toolbar)
                .withShowDrawerOnFirstLaunch(true)
                .withDisplayBelowToolbar(false)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withAccountHeader(headerResult)
                .withAnimateDrawerItems(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.Works).withIcon(FontAwesome.Icon.faw_money),
                        new PrimaryDrawerItem().withName(R.string.Near).withIcon(FontAwesome.Icon.faw_map_marker),
                        new PrimaryDrawerItem().withName(R.string.title_activity_bazarche).withIcon(FontAwesome.Icon.faw_shopping_cart),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.My_business).withIcon(FontAwesome.Icon.faw_briefcase),
                        new SecondaryDrawerItem().withName(R.string.My_Account).withIcon(FontAwesome.Icon.faw_user),
                        new SecondaryDrawerItem().withName(R.string.My_Fav).withIcon(FontAwesome.Icon.faw_bookmark),
                        new SecondaryDrawerItem().withName(R.string.My_city).withIcon(FontAwesome.Icon.faw_building),
                        new SecondaryDrawerItem().withName(R.string.My_Intrest).withIcon(FontAwesome.Icon.faw_heart),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.action_settings).withIcon(FontAwesome.Icon.faw_gears),
                        new SecondaryDrawerItem().withName(R.string.aboutUs).withIcon(FontAwesome.Icon.faw_info_circle),
                        new DividerDrawerItem()
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        if (drawerItem instanceof Nameable) {

                        }
                        if (position == 0) {

                            Intent i = new Intent(getApplicationContext(), Jobs.class);
                            startActivity(i);

                        }
                        if (position == 1) {

                            try {
                                int v = getPackageManager().getPackageInfo("com.google.android.gms", 0).versionCode;
                                Log.i("Vesion",String.valueOf(v));
                            // Showing status
                            if (v >=6587000) {
                                Intent i = new Intent(getApplicationContext(), NearMeActivity.class);
                                startActivity(i);
                            } else {
                                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                                alertDialog.setTitle("هشدار");
                                alertDialog.setMessage("نسخه Google Play Service  شما قدیمی می باشد. لطفا بروز رسانی کنید");
                                alertDialog.setButton("خب", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                alertDialog.show();
                            }

                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }

                        }

                        if (position == 2) {

                            Intent i = new Intent(getApplicationContext(), Bazarche.class);
                            startActivity(i);

                        }

                        if (position == 4) {

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
                                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                                    alertDialog.setTitle("وارد شوید");
                                    alertDialog.setMessage("وارد حساب کاربری خود شوید و از تمامی امکانات برنامه استفاده کنید.");
                                    alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent i = new Intent(getApplicationContext(), Log_In.class);
                                            startActivity(i);
                                            finish();
                                        }
                                    });

                                    alertDialog.setButton2("بعدا", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                                    alertDialog.show();

                                }
                            }

                        }
                        if (position == 5) {

                            try {
                                DataBaseSqlite db = new DataBaseSqlite(MainActivity.this);
                                Cursor cursor = db.select_Member();
                                cursor.moveToFirst();
                                Log.i("memberID", String.valueOf(cursor.getInt(0)));
                                if (cursor.getInt(0) > 0) {
                                    Intent i = new Intent(getApplicationContext(), My_Profile.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "وارد حساب خود نشده اید...!", Toast.LENGTH_LONG).show();

                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "وارد حساب خود نشده اید...!", Toast.LENGTH_LONG).show();
                            }
                        }
                        if (position ==6) {

                            try {
                                DataBaseSqlite db = new DataBaseSqlite(MainActivity.this);
                                Cursor cursor = db.select_Member();
                                cursor.moveToFirst();
                                Log.i("memberID", String.valueOf(cursor.getInt(0)));
                                if (cursor.getInt(0) > 0) {
                                    Intent i = new Intent(getApplicationContext(), BookMark.class);
                                    startActivity(i);
                                } else {
                                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                                    alertDialog.setTitle("وارد شوید");
                                    alertDialog.setMessage("وارد حساب کاربری خود شوید و از تمامی امکانات برنامه استفاده کنید.");
                                    alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent i = new Intent(getApplicationContext(), Log_In.class);
                                            startActivity(i);
                                            finish();
                                        }
                                    });

                                    alertDialog.setButton2("بعدا", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });

                                    // Showing Alert Message
                                    alertDialog.show();

                                }
                            } catch (Exception e) {
                                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                                alertDialog.setTitle("وارد شوید");
                                alertDialog.setMessage("وارد حساب کاربری خود شوید و از تمامی امکانات برنامه استفاده کنید.");
                                alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent i = new Intent(getApplicationContext(), Log_In.class);
                                        startActivity(i);
                                        finish();
                                    }
                                });

                                alertDialog.setButton2("بعدا", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                alertDialog.show();
                            }

                        }
                        if (position == 7) {
                            Intent i = new Intent(getApplicationContext(), My_city.class);
                            startActivity(i);
                        }
                        if (position == 8) {
                            if (query.getMemberId() > 0) // get member
                            {
                                Intent i = new Intent(getApplicationContext(), My_Interest.class);
                                startActivity(i);
                            } else {
                                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                                alertDialog.setTitle("وارد شوید");
                                alertDialog.setMessage("وارد حساب کاربری خود شوید و از تمامی امکانات برنامه استفاده کنید.");
                                alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent i = new Intent(getApplicationContext(), Log_In.class);
                                        startActivity(i);
                                        finish();
                                    }
                                });

                                alertDialog.setButton2("بعدا", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                alertDialog.show();
                            }
                        }
                        if (position == 10) {

                            Intent i = new Intent(getApplicationContext(), Setting.class);
                            startActivity(i);
                        }
                        if (position == 11) {
                            Intent i = new Intent(getApplicationContext(), AboutUs.class);
                            startActivity(i);
                        }
                        return false;
                    }
                }).build();


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
            slider.removeAllSliders();
            Image_slider();
            Log.d("receiver", "Got message: " + message);
        }

    };


    public void help1(){
        ViewTarget Hdiscount=new ViewTarget(R.id.toolbar,this);
        RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lps.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lps.addRule(RelativeLayout.CENTER_IN_PARENT);
        int margin = ((Number) (getResources().getDisplayMetrics().density * 80)).intValue();
        lps.setMargins(margin, margin, 10, margin);

        ShowcaseView sv=new ShowcaseView.Builder(this)

                .setContentTitle("به شهرما خوش آمدید")
                .setContentText("برای استفاده از امکانت برنامه از این منو استفاده کنید")
                .setStyle(R.style.CustomShowcaseTheme)
                .build();
        sv.setButtonText("بعدی");
        sv.setButtonPosition(lps);
        sv.setOnShowcaseEventListener(new OnShowcaseEventListener() {
            @Override
            public void onShowcaseViewHide(ShowcaseView showcaseView) {
                help2();
            }

            @Override
            public void onShowcaseViewDidHide(ShowcaseView showcaseView) {

            }

            @Override
            public void onShowcaseViewShow(ShowcaseView showcaseView) {

            }
        });
    }
    public void help2(){
        ViewTarget Hdiscount=new ViewTarget(R.id.notification,this);

        RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lps.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lps.addRule(RelativeLayout.CENTER_IN_PARENT);
        int margin = ((Number) (getResources().getDisplayMetrics().density * 80)).intValue();
        lps.setMargins(margin, margin, 10, margin);

        ShowcaseView sv=new ShowcaseView.Builder(this)
                //.setTarget(new ActionViewTarget(this, ActionViewTarget.Type.HOME))
                .setTarget(Hdiscount)
                .setContentTitle("اعلانات")
                .setContentText("برای نمایش آخرین اعلانات از این دکمه استفاده کنید")
                .setStyle(R.style.CustomShowcaseTheme)
                .build();
        sv.setButtonText("بعدی");
        sv.setButtonPosition(lps);
        sv.setOnShowcaseEventListener(new OnShowcaseEventListener() {
            @Override
            public void onShowcaseViewHide(ShowcaseView showcaseView) {
                help3();
            }

            @Override
            public void onShowcaseViewDidHide(ShowcaseView showcaseView) {

            }

            @Override
            public void onShowcaseViewShow(ShowcaseView showcaseView) {

            }
        });
    }
    public void help3(){
        ViewTarget Hdiscount=new ViewTarget(R.id.action_button,this);

        RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lps.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lps.addRule(RelativeLayout.CENTER_IN_PARENT);
        int margin = ((Number) (getResources().getDisplayMetrics().density * 80)).intValue();
        lps.setMargins(margin, margin, 10, margin);

        ShowcaseView sv=new ShowcaseView.Builder(this)
                // .setTarget(new ActionViewTarget(this, ActionViewTarget.Type.HOME))
                .setTarget(Hdiscount)
                .setContentTitle("ورود کاربر")
                .setContentText("برای وارد شدن به برنامه یا ثبت نام از این دکمه استفاده کنید")
                .setStyle(R.style.CustomShowcaseTheme)
                .build();
        sv.setButtonText("خب");
        sv.setButtonPosition(lps);

    }
}
