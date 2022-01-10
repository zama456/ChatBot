package com.zama.example.chatbot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatRvAdapter extends RecyclerView.Adapter {
    private ArrayList<ChatsModel> chatsModelArrayList;
    private Context context;

    public ChatRvAdapter(ArrayList<ChatsModel> chatsModelArrayList, Context context) {
        this.chatsModelArrayList = chatsModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_msg_rv_item, parent,false);
                return new UserViewHolder(view);

            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_msg_rv_item, parent,false);
                return new BotViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatsModel chatsModel = chatsModelArrayList.get(position);
        switch (chatsModel.getSender()){
            case "user":
                ((UserViewHolder)holder).tvUsermsg.setText(chatsModel.getMessage());
               break;
            case "bot":
                ((BotViewHolder)holder).tvBotmsg.setText(chatsModel.getMessage());
               break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (chatsModelArrayList.get(position).getSender()) {
            case "user":
                return 0;
            case"bot":
                return 1;
            default:
                return -1;

        }
    }

    @Override
    public int getItemCount() {
        return chatsModelArrayList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsermsg;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsermsg = itemView.findViewById(R.id.tvUsermsg);
        }
    }

    public static class BotViewHolder extends RecyclerView.ViewHolder {
        TextView tvBotmsg;

        public BotViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBotmsg = itemView.findViewById(R.id.tvBotmsg);
        }
    }
}
