package com.aidm.chatbotapp;

public class ChatModel {
    private String mMessage;
    private boolean mIsSend;

    public ChatModel(String message, boolean isSend) {
        mMessage = message;
        mIsSend = isSend;
    }

    public String getMessage() {
        return mMessage;
    }

    public boolean isSend() {
        return mIsSend;
    }
}