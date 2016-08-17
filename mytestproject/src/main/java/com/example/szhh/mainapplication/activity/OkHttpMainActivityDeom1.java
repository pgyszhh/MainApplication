package com.example.szhh.mainapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.szhh.mainapplication.R;
import com.example.szhh.mainapplication.entity.ShopEntity;
import com.example.szhh.mainapplication.eventbuslistener.CommonListener;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;

public class OkHttpMainActivityDeom1 extends AppCompatActivity {
    private TextView msg;
    private Button bt;
    private OkHttpClient mOkhttpClient=new OkHttpClient();
    private ImageView img;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http_main_deom1);
        initViews();
        initEvents();
    }

    private void initEvents() {
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startInternetConnection();
                //getJSON();
            }
        });
    }

    private void initViews() {
        EventBus.getDefault().register(this);
        msg = (TextView) findViewById(R.id.msg);
        bt = (Button) findViewById(R.id.bt);
        //img = (ImageView) findViewById(R.id.img);

    }


    public void getJSON(){
        if(mOkhttpClient==null){
            mOkhttpClient=new OkHttpClient();
        }
        final Request request=new Request.Builder().url("http://101.200.183.103:8888/shop/topic?page=1&pageNum=5").build();
        Call myCall=mOkhttpClient.newCall(request);
        myCall.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()){
                    Gson gson=new Gson();
                    ShopEntity entity=gson.fromJson(response.body().charStream(),ShopEntity.class);
                    EventBus.getDefault().post(new CommonListener(0,entity,0,0,null));
                }else {
                    EventBus.getDefault().post(new CommonListener(1,null,0,0,"解析失败"));
                }
            }
        });
    }
    public void startInternetConnection(){
        if(mOkhttpClient==null){
            mOkhttpClient=new OkHttpClient();
        }
        final StringBuffer bf=new StringBuffer();
        final Request request=new Request.Builder().url("http://www.baidu.com").build();
        Call myCall=mOkhttpClient.newCall(request);
        myCall.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                bf.append("body------->:\n"+request.body().toString()+"\r\n").append("headers------->:\n"+request.headers().toString()+"\r\n")
                        .append("httpUrl------->:\n"+request.httpUrl().toString()+"\r\n").append(""+request.method()+"\r\n")
                        .append("urlString------->:\n"+request.urlString()+"\r\n").append("e------->:\n"+e.toString()+"\r\n");
                Headers headers=request.headers();
                for(int i=0;i<headers.size();i++){
                    bf.append("第"+i+"个头（header）"+headers.name(i)+"\r\n");
                }
                //CommonListener com=new CommonListener(1,null,0,0,bf.toString());
                EventBus.getDefault().post(new CommonListener(1,null,0,0,bf.toString()));
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful())
                bf.append("body------->:\r\n"+response.body().toString()+"\r\n").append("headers------->:\n"+response.headers().toString()+"\r\n")
                        .append("networkResponse------->:\n"+response.networkResponse()+"\r\n").append("isSuccessful------->:\n"+response.isSuccessful()+"\r\n")
                        .append("code------->:\n"+response.code()+"\r\n");
                Headers headers=response.headers();
                for(int i=0;i<headers.size();i++){
                    bf.append("第"+i+"个头（header）"+headers.name(i)+"\r\n");
                }
                EventBus.getDefault().post(new CommonListener(0,null,0,0,bf.toString()));
            }
        });
    }

   @Subscribe(threadMode = ThreadMode.MAIN)
    public void initDatas(CommonListener common){
       if(common.getWhat()==1){
           msg.setText("失败"+common.getMsg());
           msg.setText("失败"+common.getMsg());
       }else if(common.getWhat()==0){
           msg.setText("成功"+common.getMsg());
       }
/*ShopEntity shopEntity= (ShopEntity) common.getObj();
       ImageLoader.getInstance().displayImage(shopEntity.getTopic().get(0).getPic(),img);*/

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
