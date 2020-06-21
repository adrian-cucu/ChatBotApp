package com.aidm.chatbotapp;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ChatBot
        implements Response.Listener<String>, Response.ErrorListener {
    private static final String TAG = "ChatBot";
    private static final String LINK = "https://console.dialogflow.com/api-client/demo/embedded/%s/demoQuery?q=%s&sessionId=%s";

    private Context mContext;

    private RequestQueue mRequestQueue;
    private Response.Listener<String> mResponseListener;
    private Response.ErrorListener mErrorResponseListener;

    private String mBotID;
    private String mSessionID;

    public ChatBot(Context context, String botID, String sessionID,
               Response.Listener<String> respListener, Response.ErrorListener errorListener) {
        mContext = context;
        mBotID = botID;
        mSessionID = sessionID;
        mRequestQueue = Volley.newRequestQueue(mContext);

        mResponseListener = respListener;
        mErrorResponseListener = errorListener;
    }

    public void sendMessage(String message) {
        String url = String.format(LINK, mBotID, message, mSessionID);
        Log.d(TAG, url);
        StringRequest stringRequest =
                new StringRequest(Request.Method.GET, url, this, this);
        mRequestQueue.add(stringRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mErrorResponseListener.onErrorResponse(error);
    }

    @Override
    public void onResponse(String response) {
        try {
            JSONObject json = new JSONObject(response);

            JSONObject res = json.getJSONObject("result");
            if (res == null) {
                mErrorResponseListener.onErrorResponse(new VolleyError("ChatBot did not respond!"));
                return;
            }
            JSONObject fulfillment = res.getJSONObject("fulfillment");
            if (fulfillment == null) {
                mErrorResponseListener.onErrorResponse(new VolleyError("ChatBot did not respond!"));
                return;
            }
            String botResponse = fulfillment.getString("speech");
            mResponseListener.onResponse(botResponse);

        } catch (JSONException e) {
            mErrorResponseListener.onErrorResponse(new VolleyError(e.getMessage()));
            Log.e(TAG, e.getMessage());
        }
    }
}
