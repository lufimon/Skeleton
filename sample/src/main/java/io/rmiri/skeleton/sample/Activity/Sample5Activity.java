package io.rmiri.skeleton.sample.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.ArrayList;

import io.rmiri.skeleton.Master.SkeletonConfig;
import io.rmiri.skeleton.sample.Adapter.AdapterSample5;
import io.rmiri.skeleton.sample.Data.DataObject;
import io.rmiri.skeleton.sample.Data.GeneratesDataFake;
import io.rmiri.skeleton.sample.R;


public class Sample5Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterSample5 adapterSample5;
    private ArrayList<DataObject> dataObjects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_5);


        //toolbar
        ((Toolbar) findViewById(R.id.toolbar)).setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //initial recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);



         ViewTreeObserver vto = recyclerView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                // initial SkeletonDetail and set in adapter
                SkeletonConfig skeletonConfig = new SkeletonConfig().build();
                skeletonConfig.setRecyclerViewHeight(2000);
                Log.i("+++++++++++++++____++++++", "onCreateViewHolder " + skeletonConfig.getRecyclerViewHeight());


                //set adapter in recyclerView
                adapterSample5 = new AdapterSample5(getApplicationContext(), dataObjects, skeletonConfig);
                recyclerView.setAdapter(adapterSample5);


                ViewTreeObserver obs = recyclerView.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
            }
        });
        
        

        //after 5 second get data fake
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dataObjects = new GeneratesDataFake().genarateDataFake();
                adapterSample5.addMoreDataAndSkeletonFinish(dataObjects);
            }
        }, 1200);
    }


}