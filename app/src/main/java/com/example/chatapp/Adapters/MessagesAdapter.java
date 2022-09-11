package com.example.chatapp.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatapp.Models.Message;
import com.example.chatapp.R;
import com.example.chatapp.databinding.ItemReceiveBinding;
import com.example.chatapp.databinding.ItemSendBinding;
import com.github.pgreze.reactions.ReactionPopup;
import com.github.pgreze.reactions.ReactionsConfig;
import com.github.pgreze.reactions.ReactionsConfigBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;

public class MessagesAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Message> messages;

    final int ITEM_SEND = 1;
    final int ITEM_RECEIVE = 2;

    String senderRoom;
    String receiverRoom;
    

    public MessagesAdapter(Context context , ArrayList<Message> messages, String senderRoom , String receiverRoom){
        this.context = context;
        this.messages = messages;
        this.senderRoom = senderRoom;
        this.receiverRoom = receiverRoom;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        if(viewType == ITEM_SEND){
            View view = LayoutInflater.from(context).inflate(R.layout.item_send,parent,false);
            return new SendViewHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_receive,parent,false);
            return new ReceiverViewHolder(view);
        }

    }


    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        if(FirebaseAuth.getInstance().getUid().equals(message.getSenderId())){
            return ITEM_SEND;
        }else {
            return ITEM_RECEIVE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull  RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);
//        int[] reactions = new int[]{
//                R.drawable.ic_fb_like,
//                R.drawable.ic_fb_love,
//                R.drawable.ic_fb_laugh,
//                R.drawable.ic_fb_wow,
//                R.drawable.ic_fb_sad,
//                R.drawable.ic_fb_angry
//        };
//        ReactionsConfig config = new ReactionsConfigBuilder(context)
//                .withReactions(reactions)
//                .build();
//
//        ReactionPopup popup = new ReactionPopup(context, config, (pos) -> {
//            if(holder.getClass() == SendViewHolder.class) {
//                SendViewHolder viewHolder = (SendViewHolder) holder;
//                viewHolder.binding.feeling.setImageResource(reactions[pos]);
//                viewHolder.binding.feeling.setVisibility(View.VISIBLE);
//            }else {
//                ReceiverViewHolder viewHolder = (ReceiverViewHolder) holder;
//                viewHolder.binding.feeling.setImageResource(reactions[pos]);
//                viewHolder.binding.feeling.setVisibility(View.VISIBLE);
//            }
//
//            message.setFeeling(pos);
//
//            FirebaseDatabase.getInstance().getReference()
//                    .child("chats")
//                    .child(senderRoom)
//                    .child(message.getMessageId())
//                    .setValue(message);
//
//            FirebaseDatabase.getInstance().getReference()
//                    .child("chats")
//                    .child(receiverRoom)
//                    .child(message.getMessageId())
//                    .setValue(message);
//
//            return true; // true is closing popup, false is requesting a new selection
//        });

        if(holder.getClass() == SendViewHolder.class){
            SendViewHolder viewHolder = (SendViewHolder)holder;

            if(message.getMessage().equals("photo")){
                viewHolder.binding.image.setVisibility(View.VISIBLE);
                viewHolder.binding.messege.setVisibility(View.GONE);
                Glide.with(context)
                        .load(message.getImageUrl())
                        .placeholder(R.drawable.placeholder)
                        .into(viewHolder.binding.image);
            }

            viewHolder.binding.messege.setText(message.getMessage());

//            if(message.getFeeling() >= 0) {
//               //message.setFeeling(reactions[message.getFeeling()]);
//                viewHolder.binding.feeling.setImageResource(reactions[message.getFeeling()]);
//                viewHolder.binding.feeling.setVisibility(View.VISIBLE);
//            }else {
//                viewHolder.binding.feeling.setVisibility(View.GONE);
//            }
//
//
//            viewHolder.binding.messege.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    popup.onTouch(v, event);
//                    return false;
//                }
//            });

        }else {
            ReceiverViewHolder viewHolder = (ReceiverViewHolder)holder;

            if(message.getMessage().equals("photo")){
                viewHolder.binding.image.setVisibility(View.VISIBLE);
                viewHolder.binding.messege.setVisibility(View.GONE);
                Glide.with(context)
                        .load(message.getImageUrl())
                        .placeholder(R.drawable.placeholder)
                        .into(viewHolder.binding.image);
            }

            viewHolder.binding.messege.setText(message.getMessage());

//            if(message.getFeeling() >= 0) {
//                //message.setFeeling(reactions[message.getFeeling()]);
//                viewHolder.binding.feeling.setImageResource(reactions[message.getFeeling()]);
//                viewHolder.binding.feeling.setVisibility(View.VISIBLE);
//            }else {
//                viewHolder.binding.feeling.setVisibility(View.GONE);
//            }
//
//            viewHolder.binding.messege.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    popup.onTouch(v, event);
//                    return false;
//                }
//            });
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class SendViewHolder extends RecyclerView.ViewHolder{

        ItemSendBinding binding;
        public SendViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemSendBinding.bind(itemView);
        }
    }
    public static class ReceiverViewHolder extends RecyclerView.ViewHolder{
        ItemReceiveBinding binding;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemReceiveBinding.bind(itemView);
        }
    }
}
