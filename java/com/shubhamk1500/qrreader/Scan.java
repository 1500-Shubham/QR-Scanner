package com.shubhamk1500.qrreader;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.startapp.sdk.ads.banner.Banner;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;

import java.io.IOException;
import java.util.Objects;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Scan extends Fragment  {


    CodeScanner mCodeScanner;
    CodeScannerView scannerView;
    Banner banner;

    public Scan() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accesscamera();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Activity activity = getActivity();
        StartAppSDK.init(getActivity(), "206000786", true);
        StartAppAd.disableSplash();
        View root=inflater.inflate(R.layout.fragment_scan, container, false);

        banner=root.findViewById(R.id.banner);
        banner.loadAd();
        // scannerView=v.findViewById(R.id.ZXingScannerView);    //scannerView=new ZXingScannerView(getActivity());

        scannerView = root.findViewById(R.id.scannerview);
        mCodeScanner = new CodeScanner(activity, scannerView);

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(getActivity(), com.shubhamk1500.qrreader.Result.class);
                        i.putExtra("fresult",result.toString());
                        startActivity(i);
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
        banner.loadAd();
        return root;
    }




    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
        banner.loadAd();
    }

    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
        banner.loadAd();
    }


    public  void accesscamera(){

        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){

        }
        else{
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},1);
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){

        }else{
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},1);

        }

    }




}
