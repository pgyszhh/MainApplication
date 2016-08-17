package com.example.szhh.mainapplication.myclass;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;

import java.io.IOException;

/**
 * @ Created by szhh on 2016/8/13.
 * @ Date   : 2016/8/13.
 * @ Auther    : suzhiheng
 * @ Description    :TODDO
 */

public class MyDemo01 {

    public void initDemo1(){
        OkHttpClient client=new OkHttpClient();
        RequestBody requestBody=new FormEncodingBuilder().add("","").add("","").build();
        Request request=new Request.Builder().url("").post(requestBody).build();
        Call myCall=client.newCall(request);
        myCall.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

            }
        });
        // new FormEncodingBuilder()
    }

    public void initDemo02(){
        OkHttpClient client=new OkHttpClient();
        MediaType mediaType=MediaType.parse("");
        RequestBody body=RequestBody.create(mediaType, JSONArray.class.toString());
        Request request=new Request.Builder().post(body).url("").build();
        Call myCall=client.newCall(request);
        myCall.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

            }
        });
    }

    public void initDemo03(){

    }
}
