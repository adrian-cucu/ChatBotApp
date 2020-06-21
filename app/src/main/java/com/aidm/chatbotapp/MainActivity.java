package com.aidm.chatbotapp;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class MainActivity
        extends AppCompatActivity
        implements Response.Listener<String>, Response.ErrorListener {
    private static final String TAG = "ChatBotMainActivity";
    private static final String SESSION_ID = UUID.randomUUID().toString();
    private static final String BOT_ID = "4df469f5-a560-405d-9edf-ec062e64e3c0";

    ChatBot mBot;

    ListView mListViewMessages;
    CustomAdapter mListAdapter;

    EditText mMessageText;
    FloatingActionButton mSendButton;

    @Override
    public void onResponse(String response) {
        Log.d(TAG, response);

        mListViewMessages.setAdapter(mListAdapter);
        mListAdapter.add(new ChatModel(response, false));
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), "ChatBot error", Toast.LENGTH_LONG).show();
        Log.getStackTraceString(error);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListViewMessages = (ListView)findViewById(R.id.list_of_message);
        mMessageText = (EditText)findViewById(R.id.user_message);
        mSendButton = (FloatingActionButton)findViewById(R.id.fab);

        mListAdapter = new CustomAdapter(getApplicationContext());
        mListViewMessages.setAdapter(mListAdapter);

        Log.d(TAG, "ChatBot ID=" + BOT_ID);
        Log.d(TAG, "ChatBot sessID=" + SESSION_ID);
        mBot = new ChatBot(getApplicationContext(), BOT_ID, SESSION_ID, this, this);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListViewMessages.setAdapter(mListAdapter);
                mListAdapter.add(new ChatModel(mMessageText.getText().toString(), true));
                mListAdapter.notifyDataSetChanged();
                Log.d(TAG, "Send: " + mMessageText.getText().toString());

                mBot.sendMessage(mMessageText.getText().toString());
                mMessageText.setText("");
            }
        });
    }
}
