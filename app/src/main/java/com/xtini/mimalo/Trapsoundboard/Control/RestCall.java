package com.xtini.mimalo.Trapsoundboard.Control;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class RestCall {
    private static final String BASE_URL = "https://trapsoundboard777.firebaseio.com/";
    private static AsyncHttpClient client = new AsyncHttpClient();
    private OnResultReceived onResultReceived;

    public static void get(String url, final OnResultReceived onResultReceived){
        client.get(getUrl(url), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    String response = new String(responseBody);
                    onResultReceived.onSuccess(response);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                onResultReceived.onFailure(statusCode);
            }
        });
    }

    private static String getUrl(String relative_url){
        return BASE_URL + relative_url;
    }

    public interface OnResultReceived{
         void onSuccess(String response);

         void onFailure(int statusCode);
    }
}
