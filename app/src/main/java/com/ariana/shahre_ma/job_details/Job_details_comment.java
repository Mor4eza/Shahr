package com.ariana.shahre_ma.job_details;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ariana.shahre_ma.Cards.Comment_Card_Adapter;
import com.ariana.shahre_ma.Date.CalendarTool;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetOpinionJson;
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
        RecyclerView mRecyclerView;
        RecyclerView.LayoutManager mLayoutManager;
        RecyclerView.Adapter Comment_adapter;

        CalendarTool ct=new CalendarTool();
        FieldClass fc = new FieldClass();
        HTTPPostMemberJson sendPost;
        SqliteTOjson json = new SqliteTOjson();
        String _json;

        EditText txtComm;


        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_job_details_comment, container, false);


            HTTPGetOpinionJson httponion=new HTTPGetOpinionJson(getActivity());
            httponion.seturl_opinion(186);
            httponion.execute();

            final Button btnsend = (Button)rootView.findViewById(R.id.bnt_send);

            btnsend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                txtComm=(EditText) rootView.findViewById(R.id.txt_comm);

                    _json = (json.getOpinionTOjson(txtComm.getText().toString(),ct.getIranianDate(),1,186));
                    fc.SetOpinion_Description(txtComm.getText().toString());
                    fc.SetOpinion_Date(ct.getIranianDate().toString());
                    fc.SetOpinion_OpinionType(1);
                    fc.SetOpinion_Erja(fc.GetBusiness_SubsetIdb());

                    HTTPPostOpinionJson sendPost1 = new HTTPPostOpinionJson(getActivity());
                    sendPost1.SetOpinion_Json(_json);
                    sendPost1.execute();
                }
            });

           try {
                mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_comments);
                mRecyclerView.setHasFixedSize(true);

                mLayoutManager = new LinearLayoutManager(getActivity());
                mRecyclerView.setLayoutManager(mLayoutManager);

                Comment_adapter = new Comment_Card_Adapter(getActivity());
                mRecyclerView.setAdapter(Comment_adapter);
            } catch (Exception e) {
            }

            return rootView;
        }
        public void onClick(View v) {

             Toast.makeText(getActivity(), "clicked", Toast.LENGTH_LONG).show();

/*
            _json = (json.getOpinionTOjson(Aname,Aemail,1,186));
            fc.SetOpinion_Description(Aname);
            fc.SetOpinion_Date(ct.getIranianDate().toString());
            fc.SetOpinion_OpinionType(1);
            fc.SetOpinion_Erja(fc.GetBusiness_SubsetIdb());

            HTTPPostOpinionJson sendPost1 = new HTTPPostOpinionJson(this);
            sendPost1.SetOpinion_Json(_json);
            sendPost1.execute();*/
        }












    }
}
