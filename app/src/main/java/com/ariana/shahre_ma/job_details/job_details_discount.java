package com.ariana.shahre_ma.job_details;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ariana.shahre_ma.Date.CalendarTool;
import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.DateBaseSqlite.SelectDataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServiceSend.HTTPSendLikeDisCount;


public class job_details_discount extends FragmentActivity {




    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details_discount);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }


    }




    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {


        FieldClass fc=new FieldClass();

        Integer discountid=0;
        RelativeLayout like;
        RelativeLayout dislike;
        public static TextView tv_like;
        public static TextView tv_unlike;
        TextView tv_dis_percent;
        TextView tv_dis_name;
        TextView tv_dis_start;
        TextView tv_dis_end;
        TextView tv_dis_desc;
        TransitionDrawable drawable_like;
        TransitionDrawable drawable_unlike;
        RelativeLayout relativeLayout;
        ImageView imageView;

        CalendarTool ct=new CalendarTool();

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_job_details_discount, container, false);
            relativeLayout=(RelativeLayout)rootView.findViewById(R.id.relativ_percent);
            imageView=(ImageView)rootView.findViewById(R.id.img_discount);
            like=(RelativeLayout)rootView.findViewById(R.id.like_button);
            dislike=(RelativeLayout)rootView.findViewById(R.id.dislike_button);
            tv_like=(TextView) rootView.findViewById(R.id.tv_discount_like);
            tv_unlike=(TextView) rootView.findViewById(R.id.tv_discount_unlike);
            tv_dis_percent=(TextView) rootView.findViewById(R.id.tv_dis_percent);
            tv_dis_name=(TextView) rootView.findViewById(R.id.tv_dis_name);
            tv_dis_start=(TextView) rootView.findViewById(R.id.tv_dis_start);
            tv_dis_end=(TextView) rootView.findViewById(R.id.tv_dis_end);
            tv_dis_desc=(TextView) rootView.findViewById(R.id.tv_dis_desc);


            try {
                SelectDataBaseSqlite db = new SelectDataBaseSqlite(getActivity());
                Cursor cursor = db.select_DisCount(fc.GetBusiness_Id());
//                Log.i("IDdiscount",String.valueOf(cursor.getInt(0)));
                if(cursor.getCount()<=0)
                {
                    tv_dis_percent.setVisibility(View.INVISIBLE);
                    tv_like.setVisibility(View.INVISIBLE);
                    tv_unlike.setVisibility(View.INVISIBLE);
                    tv_dis_start.setVisibility(View.INVISIBLE);
                    tv_dis_end.setVisibility(View.INVISIBLE);
                    tv_dis_desc.setVisibility(View.INVISIBLE);
                    like.setVisibility(View.INVISIBLE);
                    dislike.setVisibility(View.INVISIBLE);
                    relativeLayout.setVisibility(View.INVISIBLE);
                    tv_dis_name.setText("تخفیفی برای این کسب و کار ثبت نشده است.");
                    tv_dis_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                    tv_dis_name.setTextColor(Color.GRAY);
                    tv_dis_name.setGravity(Gravity.CENTER);

                }
                else
                {
                    cursor.moveToFirst();
                    discountid = cursor.getInt(0);
                    tv_dis_percent.setText(cursor.getString(6));
                    tv_dis_name.setText(cursor.getString(1));
                    tv_dis_start.setText(cursor.getString(3));
                    tv_dis_end.setText(cursor.getString(4));
                    tv_dis_desc.setText(cursor.getString(5));

                    tv_like.setText(String.valueOf(cursor.getInt(8)));
                    tv_unlike.setText(String.valueOf(cursor.getInt(9)));
                }
            }catch (Exception e)
            {

            }


            drawable_like = (TransitionDrawable) like.getBackground();
            drawable_unlike = (TransitionDrawable) dislike.getBackground();

            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawable_like.startTransition(500);
                    like.setEnabled(false);
                    dislike.setEnabled(true);
                    drawable_unlike.reverseTransition(500);
                    NetState ns=new NetState(getActivity());
                    Query query = new Query(getActivity());

                    // send like To DisCount
                    if (ns.checkInternetConnection()) {
                        if (query.getMemberId() > 0) {
                            HTTPSendLikeDisCount httpSendLikeDisCount = new HTTPSendLikeDisCount(getActivity());
                            httpSendLikeDisCount.SetDiscountid(discountid);
                            httpSendLikeDisCount.SetBusinessid(fc.GetBusiness_Id());
                            httpSendLikeDisCount.SetMemberid(query.getMemberId());

                            httpSendLikeDisCount.SetLike(true);
                            httpSendLikeDisCount.execute();

                        }
                        else
                        {

                        }
                    }
                    else
                    {
                        // Toast not net
                    }
                }
            });

            dislike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawable_unlike.startTransition(500);
                    dislike.setEnabled(false);
                    like.setEnabled(true);
                    drawable_like.reverseTransition(500);
                    NetState ns = new NetState(getActivity()); // class state network
                    Query query = new Query(getActivity());

                    // send Dislike To DisCount
                    if (ns.checkInternetConnection()) {
                        if (query.getMemberId() > 0) {
                            HTTPSendLikeDisCount httpSendLikeDisCount = new HTTPSendLikeDisCount(getActivity());
                            httpSendLikeDisCount.SetDiscountid(discountid);
                            httpSendLikeDisCount.SetBusinessid(fc.GetBusiness_Id());
                            httpSendLikeDisCount.SetMemberid(query.getMemberId());

                            httpSendLikeDisCount.SetLike(false);
                            httpSendLikeDisCount.execute();
                        } else {

                        }
                    } else {

                    }
                }
            });
            return rootView;
        }

    }
}
