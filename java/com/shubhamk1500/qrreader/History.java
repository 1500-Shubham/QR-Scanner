package com.shubhamk1500.qrreader;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.startapp.sdk.ads.banner.Banner;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;

import java.util.ArrayList;
import java.util.Map;


public class History extends Fragment {
    SharedPreferences sharedPreferences;
    Button delete;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<String> date=new ArrayList<>();
    ArrayList<String> link=new ArrayList<>();

    Banner banner2;


    TextView developer;
    public History() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        StartAppSDK.init(getActivity(), "206000786", true);
        StartAppAd.disableSplash();
        View v= inflater.inflate(R.layout.fragment_history, container, false);
        banner2=v.findViewById(R.id.banner2);
        banner2.loadAd();

        sharedPreferences=getActivity().getSharedPreferences("com.shubhamk1500.qrreader", Context.MODE_PRIVATE);
        adddatelink();
        developer=v.findViewById(R.id.about_developer);
        delete=v.findViewById(R.id.delete);

        developer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent i =new Intent(getActivity(),AboutDeveloper.class);
            startActivity(i);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Context context=getActivity();
             AlertDialog.Builder alert=new AlertDialog.Builder(context);
                alert.setCancelable(false);
                alert.setTitle("Delete All")
                        .setMessage("Are You Sure You Want To Delete All QR_Links? ")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                date.clear();
                                link.clear();
                                sharedPreferences.edit().clear().commit();
                                mAdapter.notifyDataSetChanged();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        });

        mRecyclerView =v.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new ExampleAdapter(date,link);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        banner2.loadAd();
        return v;
    }

    public void adddatelink(){

        Log.d("MapValues",sharedPreferences.getAll().toString());
        Map<String,?> keys = sharedPreferences.getAll();

        date.clear();
        link.clear();

        for(Map.Entry<String,?> entry : keys.entrySet()){
            date.add(entry.getKey());
           link.add(entry.getValue().toString());
            Log.d("MapValues",entry.getKey()+" "+entry.getValue());

        }

        banner2.loadAd();
    }

}
