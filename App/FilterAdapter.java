package com.example.samhi.firebasedemo;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterViewHolder> {


    private List<FilterModel> mUserlist;
    private Context mContext  ;
    public  String mImg;



    public FilterAdapter(List<FilterModel> mUserlist, Context mContext, String mImg) {
        this.mUserlist = mUserlist;
        this.mContext = mContext;
        this.mImg = mImg;

    }

    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
           switch(mImg) {
               case "restaurants":
                                    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.filterpage_layout, viewGroup, false);
                                                        return new FilterViewHolder(view);

               case "travel":
               case "taxi":
               case "services":
               case "medicare":
           }
             return null;
    }
    @Override
    public void onBindViewHolder(@NonNull FilterViewHolder filterViewHolder, int i) {
                        filterViewHolder.imageView.setImageResource(mUserlist.get(i).getImageView());
                        filterViewHolder.textView.setText(mUserlist.get(i).getTextView());
                        filterViewHolder.opening.setText(mUserlist.get(i).getOpening());
                        filterViewHolder.closing.setText(mUserlist.get(i).getClosing());
                        filterViewHolder.cuisine.setText(mUserlist.get(i).getCuisine());


    }

    @Override
    public int getItemCount() {
        return  ( mUserlist.size() >  0 ?  mUserlist.size() : 0 ) ;
    }

    static public  class FilterViewHolder  extends RecyclerView.ViewHolder{


        ImageView imageView ;
        TextView textView;
        TextView opening;
        TextView closing;
        TextView cuisine;

        public FilterViewHolder(@NonNull View itemView) {
            super(itemView);


            imageView = itemView.findViewById(R.id.cardimage);
            textView = itemView.findViewById(R.id.cardname);
            opening =itemView.findViewById(R.id.tvopening1);
            closing =itemView.findViewById(R.id.tvclosing1);
            cuisine =itemView.findViewById(R.id.tvcuisine1);
        }
    }
}
