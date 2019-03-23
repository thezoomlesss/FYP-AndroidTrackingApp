package com.example.thezo.fyp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> messages_AL = new ArrayList<>();
    private Context mContext;

    public MyRecyclerViewAdapter(Context mContext, ArrayList<String> messages_AL) {
        this.messages_AL = messages_AL;
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
        if(messages_AL.get(i).equals("Test2")){

            viewHolder.message_left.setText(messages_AL.get(i));
            viewHolder.message_right.setVisibility(LinearLayout.GONE);
        }else{
            viewHolder.message_right.setText(messages_AL.get(i));
            viewHolder.message_left.setVisibility(LinearLayout.GONE);

        }
    }

    @Override
    public int getItemCount() {
        return messages_AL.size();
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