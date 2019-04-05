package com.example.samhi.firebasedemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TextViewAdapter extends RecyclerView.Adapter<TextViewAdapter.TextViewHolder> {

   private List<TextViewModel> list ;
    private Context mContext ;

    public TextViewAdapter(List<TextViewModel> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public TextViewAdapter.TextViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.filter_list_layout, viewGroup , false);
        return  new TextViewHolder (view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull TextViewAdapter.TextViewHolder textViewHolder, int i) {

              textViewHolder.textView.setText(list.get(i).getString());
    }

    @Override
    public int getItemCount() {
        return list.size()>0 ?  list.size() : 0 ;
    }

    static public class TextViewHolder extends RecyclerView.ViewHolder{

        TextView textView ;

        public TextViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.filter_id);
        }
    }
}
