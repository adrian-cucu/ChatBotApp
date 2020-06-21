package com.aidm.chatbotapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.github.library.bubbleview.BubbleTextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private ArrayList<ChatModel> mListChatModels;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public CustomAdapter(Context context) {
        mListChatModels = new ArrayList<ChatModel>();
        mContext = context;
        mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void add(ChatModel chat) {
        mListChatModels.add(chat);
    }

    @Override
    public int getCount() {
        return mListChatModels.size();
    }

    @Override
    public Object getItem(int position) {
        return mListChatModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null)  {
            if (mListChatModels.get(position).isSend()) {
                view = mLayoutInflater.inflate(R.layout.list_item_message_send, null);
            } else {
                view = mLayoutInflater.inflate(R.layout.list_item_message_recv, null);
            }
            BubbleTextView text_message = (BubbleTextView)view.findViewById(R.id.text_message);
            text_message.setText(mListChatModels.get(position).getMessage());
        }
        return view;
    }
}