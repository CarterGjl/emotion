package com.zejian.emotionkeyboard.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zejian.emotionkeyboard.R;
import com.zejian.emotionkeyboard.utils.ChatMsgType;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MoreOptionAdapter extends RecyclerView.Adapter<MoreOptionAdapter.OptionViewHolder> {

    private  List<ChatMsgType> mChatMsgTypes;

    public MoreOptionAdapter(List<ChatMsgType> chatMsgTypes) {
        mChatMsgTypes = chatMsgTypes;
    }

    @NonNull
    @Override
    public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OptionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_production_mode_griditem_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OptionViewHolder holder, int position) {
        holder.bindData(mChatMsgTypes.get(position));
    }

    @Override
    public int getItemCount() {
        return mChatMsgTypes.size();
    }

    class OptionViewHolder extends RecyclerView.ViewHolder {
        private ImageView mCustomProductionModeGridviewImageview;
        private TextView mCustomProductionModeGridviewTextview;
        OptionViewHolder(@NonNull View itemView) {
            super(itemView);


            mCustomProductionModeGridviewImageview = itemView.findViewById(R.id.custom_production_mode_gridview_imageview);
            mCustomProductionModeGridviewTextview = itemView.findViewById(R.id.custom_production_mode_gridview_textview);

        }
        public void bindData(ChatMsgType chatMsgType){
            mCustomProductionModeGridviewImageview.setImageResource(chatMsgType.getRid());
            mCustomProductionModeGridviewTextview.setText(chatMsgType.getContent());
        }
    }


}
