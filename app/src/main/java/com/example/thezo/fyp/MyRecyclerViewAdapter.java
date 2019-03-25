package com.example.thezo.fyp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

//    private ArrayList<String> messages_AL = new ArrayList<>();
    private ArrayList<SingleMessage> singleMessagesAL;

    private Context mContext;
//    ArrayList<String> messages_AL,
    public MyRecyclerViewAdapter(Context mContext,  ArrayList<SingleMessage> singleMessagesAL) {
        this.singleMessagesAL = singleMessagesAL;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =  LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_message, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {


        if(singleMessagesAL.get(i).getSender().equals("server")){

            viewHolder.message_left.setText(singleMessagesAL.get(i).getMessage());
            viewHolder.message_right.setVisibility(LinearLayout.GONE);
        }else{
            viewHolder.message_right.setText(singleMessagesAL.get(i).getMessage());
            viewHolder.message_left.setVisibility(LinearLayout.GONE);

        }
//        if(messages_AL.get(i).equals("Test2")){
//
//            viewHolder.message_left.setText(messages_AL.get(i));
//            viewHolder.message_right.setVisibility(LinearLayout.GONE);
//        }else{
//            viewHolder.message_right.setText(messages_AL.get(i));
//            viewHolder.message_left.setVisibility(LinearLayout.GONE);
//
//        }
    }

    @Override
    public int getItemCount() {
        return singleMessagesAL.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout parentLayout;
        TextView message_left;
        TextView message_right;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            message_left = itemView.findViewById(R.id.message_body_left);
            message_right = itemView.findViewById(R.id.message_body_right);
            parentLayout = itemView.findViewById(R.id.message_relativeLayout);
        }
    }
}