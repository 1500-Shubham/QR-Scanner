package com.shubhamk1500.qrreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.startapp.sdk.ads.banner.Mrec;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Result extends AppCompatActivity {
TextView url;
Button save;
Mrec mrec;
    Calendar c;
    String formattedDate;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StartAppSDK.init(this, "206000786", true);
        StartAppAd.disableSplash();
        setContentView(R.layout.activity_result);

        save=findViewById(R.id.button);

        mrec=findViewById(R.id.mrec);
        mrec.loadAd();

        url=findViewById(R.id.result_url);
        url.setText(getIntent().getStringExtra("fresult"));

        sharedPreferences=this.getSharedPreferences("com.shubhamk1500.qrreader", Context.MODE_PRIVATE);

        c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss a  dd-MM-yyyy ");
        formattedDate = df.format(c.getTime());

    }

    public void gobrowser(View view){

        String linko="";
        if(url.getText().toString().contains("http")){
            linko=url.getText().toString();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(linko));
            startActivity(i);

        }
        else {
            if(url.getText().toString().contains("qrco")){
                linko="https://"+url.getText().toString();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(linko));
                startActivity(i);

            }
            else {
                Toast.makeText(this, "Invalid URL (Try Again)", Toast.LENGTH_SHORT).show();

            }

        }
    }

    public void savesql(View view){
        //yaha pura sql mein store hoga
        String dateo=formattedDate;
        String linko="";
        if(url.getText().toString().contains("http")){
            linko=url.getText().toString();
            sharedPreferences.edit().putString(dateo,linko).apply();
            save.setEnabled(false);
            save.setBackgroundColor(Color.parseColor("#808080"));
            Toast.makeText(this, "Saved Bingo!!", Toast.LENGTH_SHORT).show();

        }
        else {
            if(url.getText().toString().contains("qrco")){
                linko="https://"+url.getText().toString();
                sharedPreferences.edit().putString(dateo,linko).apply();
                save.setEnabled(false);
                save.setBackgroundColor(Color.parseColor("#808080"));
                Toast.makeText(this, "Saved Bingo!!", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Invalid Url", Toast.LENGTH_SHORT).show();
                save.setEnabled(false);
                save.setBackgroundColor(Color.parseColor("#808080"));

            }

        }




    }
    public void sharelink(View view){
        Intent i = new Intent(android.content.Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(android.content.Intent.EXTRA_TEXT, url.getText().toString());
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }
}
