package com.example.admin.newspars;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    AsyncHttpClient client;
    JSONObject jobj,jobj1,jobj2,jobj3,jobj4;
    JSONArray jarray1,jarray2,jarray3;
    RequestParams params;
    LayoutInflater inflater;
    RecyclerView recyclerView;

    ArrayList<String>titlearray;
    ArrayList<String>catarray;
    ArrayList<String>datearray;
    ArrayList<String>imagearray;

    VerticalAdapter vadapter;
    LinearLayoutManager llmanager;

    String url="https://thecity247.com/api/get_posts/";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        client=new AsyncHttpClient();
        params=new RequestParams();

        recyclerView=findViewById(R.id.recyclerviewxml);

        titlearray=new ArrayList<String>();
        catarray=new ArrayList<String>();
        datearray=new ArrayList<String>();
        imagearray=new ArrayList<String>();

        Log.e("innn","out");

        client.post(url,params,new AsyncHttpResponseHandler(){

            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                try {
                    Log.e("innn","in");
                    jobj = new JSONObject(content);
                    if (jobj.getString("status").equals("ok")) {
                        jarray1 = jobj.getJSONArray("posts");

                        for (int i = 0; i < jarray1.length(); i++)
                        {
                            jobj1 = jarray1.getJSONObject(i);
                            titlearray.add(jobj1.getString("title"));
                            datearray.add(jobj1.getString("date"));

                            jarray2 = jobj1.getJSONArray("categories");

                            for (int j = 0; j < jarray2.length(); j++)
                            {
                                jobj3 = jarray2.getJSONObject(j);
                                catarray.add(jobj3.getString("title"));
                            }

                                jarray3 = jobj1.getJSONArray("attachments");
                            //attachments comes within post array 

                                for (int k = 0; k < jarray3.length(); k++)
                                {
                                    jobj4 = jarray3.getJSONObject(k);
                                    imagearray.add(jobj4.getString("url"));
                                }


                        }

                        vadapter=new VerticalAdapter(titlearray);

                        llmanager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);


                        recyclerView.setAdapter(vadapter);
                        recyclerView.setLayoutManager(llmanager);

                    }


                } catch (Exception e) {

                }
            }
        });

        }
        public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.MyViewHolder>
        {
        private List<String>vlist;

        VerticalAdapter(List<String>vlist){
            this.vlist=titlearray;
        }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View itemview=inflater.from(viewGroup.getContext()).inflate(R.layout.news,viewGroup,false);
                return new MyViewHolder(itemview);

            }

            @Override
            public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
            myViewHolder.titler.setText(titlearray.get(i));
            myViewHolder.dater.setText(datearray.get(i));
            myViewHolder.catr.setText(catarray.get(i));
                Picasso.with(getApplicationContext()).load(imagearray.get(i)).into(myViewHolder.imager);

            }

            @Override
            public int getItemCount() {
                return titlearray.size();
            }

            class MyViewHolder extends RecyclerView.ViewHolder{

            TextView titler,catr,dater;
            ImageView imager;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                titler=itemView.findViewById(R.id.titlexml);
                catr=itemView.findViewById(R.id.catxml);
                dater=itemView.findViewById(R.id.datexml);
                imager=itemView.findViewById(R.id.imagexml);
            }
        }



        }

        }
