package com.shubhamk1500.qrreader;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    SharedPreferences sharedPreferences;

    private ArrayList<String> date;
    Context context;
    private ArrayList<String> link;

    public ExampleAdapter(ArrayList<String> example,ArrayList<String> example2)
    {date=example;
    link=example2;

    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public TextView date,link;
        public Button share,delete;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);

            date=itemView.findViewById(R.id.date);
            link=itemView.findViewById(R.id.link);
            share=itemView.findViewById(R.id.share);
            delete=itemView.findViewById(R.id.delete);

        }
    }

    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.example_list, parent, false);
        ExampleViewHolder evh=new ExampleViewHolder(v);
        context=parent.getContext();
        return evh;}

    @Override
    public void onBindViewHolder(@NonNull ExampleAdapter.ExampleViewHolder holder, final int position) {
        final String currentdate=date.get(position);
        final String currentlink=link.get(position);

        sharedPreferences=context.getSharedPreferences("com.shubhamk1500.qrreader", Context.MODE_PRIVATE);

        holder.date.setText(currentdate);
        holder.link.setText(currentlink);

        holder.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(currentlink));
                context.startActivity(i);
            }
        });

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(android.content.Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(android.content.Intent.EXTRA_TEXT, currentlink);
               context.startActivity(i);

            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert=new AlertDialog.Builder(context);
                alert.setCancelable(false);
                alert.setTitle("Delete This QR_Link")
                        .setMessage("Are You Sure ? ")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sharedPreferences.edit().remove(currentdate).commit();
                                date.remove(position);
                                link.remove(position);
                                notifyDataSetChanged();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return date.size();
    }
}
