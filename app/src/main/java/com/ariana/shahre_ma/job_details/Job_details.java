package com.ariana.shahre_ma.job_details;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ariana.shahre_ma.Date.CalendarTool;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServiceSend.HTTPSendBookMarkURL;
import com.ariana.shahre_ma.WebServiceGet.SqliteTOjson;
import com.ariana.shahre_ma.WebServicePost.HTTPPostMemberJson;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.Locale;

public class Job_details extends ActionBarActivity implements ActionBar.TabListener {


    Query query;
    CalendarTool ct=new CalendarTool();
    FieldClass fc = new FieldClass();
    HTTPPostMemberJson sendPost;
    SqliteTOjson json = new SqliteTOjson(this);
    String _json;
    SectionsPagerAdapter mSectionsPagerAdapter;
    MenuItem fav;


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);
        setTitle(fc.GetMarket_Business());
        query=new Query(this);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);



        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }



    }




    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());

        if (tab.getPosition()==2){
       map();
      // route();

        }else if(tab.getPosition()==1){



        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position){

                case 0:
                    return new job_details_1.PlaceholderFragment();
                case 1:
                    return new job_details_discount.PlaceholderFragment();
               case 2:
                    return new Job_details_map.PlaceholderFragment();
                case 3:
                    return new Job_details_comment.PlaceholderFragment();

            }
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.det1).toUpperCase(l);
                case 1:
                    return getString(R.string.det2).toUpperCase(l);
                case 2:
                    return getString(R.string.det3).toUpperCase(l);
                case 3:
                    return getString(R.string.det4).toUpperCase(l);
            }
            return null;
        }
    }


    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_job_details, container, false);
            return rootView;
        }
    }



   public void map(){
        ResourceProxy resourceProxy;
        MapView map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        map.getController().setZoom(15);
        map.getController().animateTo(new GeoPoint(Double.parseDouble(fc.GetLatitude_Business()),Double.parseDouble(fc.GetLongtiude_Business())));

        ArrayList<OverlayItem> overlays = new ArrayList<OverlayItem>();
        overlays.add(new OverlayItem("اندیشه", "داده پردازان آریانا", new GeoPoint(Double.parseDouble(fc.GetLatitude_Business()),Double.parseDouble(fc.GetLongtiude_Business()))));
        Drawable marker=this.getResources().getDrawable(R.drawable.marker);

        resourceProxy = new DefaultResourceProxyImpl(getApplicationContext());
        ItemizedIconOverlay myLocationOverlay;
        myLocationOverlay = new ItemizedIconOverlay<OverlayItem>(overlays,marker,null, resourceProxy);
        map.getOverlays().add(myLocationOverlay);




    }
  /* public void route(){

        MapView map = (MapView) findViewById(R.id.map);
        RoadManager roadManager = new OSRMRoadManager();
        GeoPoint startPoint= new GeoPoint(35.6876661, 51.0195912);
        ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>();
        waypoints.add(startPoint);
        GeoPoint endPoint = new GeoPoint(35.683703,51.1139485);
        waypoints.add(endPoint);

        Road road = roadManager.getRoad(waypoints);

        Polyline roadOverlay = RoadManager.buildRoadOverlay(road, this);
        roadOverlay.setColor(this.getResources().getColor(R.color.material_blue_grey_900));
        map.getOverlays().add(roadOverlay);
        map.invalidate();

    }
*/

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.

      getMenuInflater().inflate(R.menu.menu_job_details, menu);
      fav=menu.findItem(R.id.action_Fav);
      return true;
  }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_Fav) {

            if(query.getMemberId()>0) {
                HTTPSendBookMarkURL httpbookmark = new HTTPSendBookMarkURL(this);
                httpbookmark.SetBusinessid(fc.GetBusiness_Id());
                Log.i("getBusinessId", String.valueOf(fc.GetBusiness_Id()));
                httpbookmark.SetMemberid(query.getMemberId());
                Log.i("getMemberId", String.valueOf(query.getMemberId()));
                httpbookmark.execute();
                fav.setIcon(R.drawable.abc_btn_rating_star_on_mtrl_alpha);
            }
            else
            {
                Toast.makeText(getApplicationContext(),"کاربری وارد نشده است",Toast.LENGTH_LONG).show();
            }
            Toast.makeText(getApplication(),String.valueOf(query.getMemberId()),Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
            this.finish();
            super.onBackPressed();

    }



}