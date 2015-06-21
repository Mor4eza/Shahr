package com.ariana.shahre_ma.job_details;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ariana.shahre_ma.Cards.Comment_Card_Adapter;
import com.ariana.shahre_ma.Date.CalendarTool;
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
        RecyclerView mRecyclerView;
        RecyclerView.LayoutManager mLayoutManager;
        RecyclerView.Adapter Comment_adapter;

        CalendarTool ct=new CalendarTool();
        FieldClass fc = new FieldClass();
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

            final Button btnsend = (Button)rootView.findViewById(R.id.bnt_send);

            btnsend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    txtComm = (EditText) rootView.findViewById(R.id.txt_comm);
                    if(ns.checkInternetConnection()==false) {
                        Toast.makeText(getActivity(),"شبکه اینترنت قطع می باشد",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                            try {
                                _json = (json.getOpinionTOjson(txtComm.getText().toString(), ct.getIranianDate(),62,186));
                                fc.SetOpinion_Description(txtComm.getText().toString());
                                fc.SetOpinion_Date(ct.getIranianDate().toString());
                                fc.SetOpinion_MemberName("");
                                fc.SetOpinion_Erja(fc.GetBusiness_SubsetIdb());

                                HTTPPostOpinionJson sendPost1 = new HTTPPostOpinionJson(getActivity());
                                sendPost1.SetOpinion_Json(_json);
                                sendPost1.execute();
                                txtComm.setText("");
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
            } catch (Exception e) {
            }


            return rootView;
        }













    }
}
