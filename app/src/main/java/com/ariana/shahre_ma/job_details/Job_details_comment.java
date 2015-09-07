package com.ariana.shahre_ma.job_details;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.ariana.shahre_ma.Cards.Comment_Card_Adapter;
import com.ariana.shahre_ma.Date.CalendarTool;
import com.ariana.shahre_ma.Date.DateTime;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServiceGet.SqliteTOjson;
import com.ariana.shahre_ma.WebServicePost.HTTPPostMemberJson;
import com.ariana.shahre_ma.WebServicePost.HTTPPostOpinionJson;

public class Job_details_comment extends ActionBarActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details_comment);
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
        public static RecyclerView mRecyclerView;
        RecyclerView.LayoutManager mLayoutManager;
        public static RecyclerView.Adapter Comment_adapter;

        CalendarTool ct=new CalendarTool();
        FieldClass fc = new FieldClass();
        DateTime dt=new DateTime();
        HTTPPostMemberJson sendPost;
        SqliteTOjson json ;
        String _json;
        Query query;
        EditText txtComm;
        NetState ns;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_job_details_comment, container, false);

            ns=new NetState(getActivity());

            json = new SqliteTOjson(getActivity());
            query=new Query(getActivity());

            final FloatingActionButton btnsend = (FloatingActionButton)rootView.findViewById(R.id.bnt_send);
            txtComm = (EditText) rootView.findViewById(R.id.txt_comm);
            btnsend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ns.checkInternetConnection() == false) {
                        Toast.makeText(getActivity(), "شبکه اینترنت قطع می باشد", Toast.LENGTH_LONG).show();
                    } else {
                        try {
                            if (query.getMemberId() > 0)
                            {
                                Log.i("getMemberId", String.valueOf(query.getMemberId()));
                                _json = (json.getOpinionTOjson(txtComm.getText().toString(),ct.getGregorianDate()+dt.Time(), query.getMemberId(), fc.GetBusiness_Id()));
                                fc.SetOpinion_Description(txtComm.getText().toString());
                                fc.SetOpinion_Date(ct.getGregorianDate()+dt.Time());
                                fc.SetOpinion_MemberName("");
                                fc.SetOpinion_Erja(fc.GetBusiness_SubsetIdb());

                                HTTPPostOpinionJson sendPost1 = new HTTPPostOpinionJson(getActivity());
                                sendPost1.SetOpinion_Json(_json);
                                sendPost1.execute();
                                txtComm.setText("");
                            } else
                            {
                                Toast.makeText(getActivity(), "کاربری وارد نشد است", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            });


           try {
                mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_comments);
                mRecyclerView.setHasFixedSize(true);

                mLayoutManager = new LinearLayoutManager(getActivity());
                mRecyclerView.setLayoutManager(mLayoutManager);

                Comment_adapter = new Comment_Card_Adapter(getActivity());
                mRecyclerView.setAdapter(Comment_adapter);
               Comment_adapter.notifyDataSetChanged();
            } catch (Exception e) {
            }


            return rootView;
        }

    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        this.finish();
        super.onBackPressed();

    }
}
