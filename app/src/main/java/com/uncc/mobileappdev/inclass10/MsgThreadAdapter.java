package com.uncc.mobileappdev.inclass10;



import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by CHINU on 3/10/2018.
 */


public class MsgThreadAdapter extends RecyclerView.Adapter<MsgThreadAdapter.ViewHolder> {
    ArrayList<Topics> mData;

    public MsgThreadAdapter(ArrayList<Topics> mData) {
        this.mData = mData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_thread_recycleview, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Topics tp = mData.get(position);
       holder.textViewTopic.setText(tp.topic);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTopic;
        Topics tp;
        String strtp;

        // each data item is just a string in this case

        public ViewHolder(View itemView) {
            super(itemView);
            this.tp = tp;
            textViewTopic= (TextView) itemView.findViewById(R.id.MessageThread);
            strtp = textViewTopic.toString();

            itemView.findViewById(R.id.imageButtonDelete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Demo", "Delete Button Clicked! " + strtp);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Demo", "Clicked! " + strtp);
                }
            });

        }
    }


}


