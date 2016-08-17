package com.example.szhh.mainapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.szhh.mainapplication.Events.GetMsg;
import com.example.szhh.mainapplication.activity.OkHttpMainActivityDeom1;
import com.example.szhh.mainapplication.view.FragmentOne;
import com.example.szhh.mainapplication.view.FragmentThree;
import com.example.szhh.mainapplication.view.FragmentTwo;
import com.example.szhh.mainapplication.view.ShareScreenShotN;
import com.example.szhh.mainapplication.view.UtilFor95598;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.CertificatePinner;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;

import dalvik.annotation.TestTarget;
import okio.BufferedSink;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private FrameLayout mfg;
    private Button menu1;
    private Button menu2;
    private Button menu3;
    private FragmentManager mManager;
    private FragmentTransaction mTransaction;
    private FragmentOne fone = null;
    private FragmentTwo ftwo = null;
    private FragmentThree fthree = null;
    private EventBus eventBusm = EventBus.getDefault();
    private Button mButton;
    private TextView mTextView;
    private OkHttpClient mOkhttp=new OkHttpClient();
    private String url="http://blog.csdn.net/lmj623565791/article/details/47911083";
    private Request mRequest=new Request.Builder().url(url).build();
    private Call mCall=mOkhttp.newCall(mRequest);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.okhttpone);
        //initViews();
        initView();

    }

    private void initView() {
        EventBus.getDefault().register(this);
        mButton= (Button) this.findViewById(R.id.bt);
        mTextView= (TextView) this.findViewById(R.id.tv);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*UtilFor95598.getScreentoShareN(MainActivity.this);
                toOtherActivity(ShareScreenShotN.class);*/
                toOtherActivity(OkHttpMainActivityDeom1.class);
            }
        });
    }



    protected <T> void toOtherActivity(Class<?> cls) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.setClass(getBaseContext(), cls);
        startActivity(intent);
    }



    private void initDefaultViews() {
        mManager = this.getSupportFragmentManager();
        mTransaction = mManager.beginTransaction();
        mTransaction.replace(R.id.mfg, fone);
        mTransaction.commit();
        System.out.println("-----------" + fone);
    }

    @Override
    public void onClick(View view) {
        mTransaction = mManager.beginTransaction();
        int id = view.getId();
        switch (id) {
            case R.id.menu1:
                if (fone == null) {
                    fone = new FragmentOne();
                }
                mTransaction.replace(R.id.mfg, fone);
                break;
            case R.id.menu2:
                if (ftwo == null) {
                    ftwo = new FragmentTwo();
                }
                mTransaction.replace(R.id.mfg, ftwo);
                break;
            case R.id.menu3:
                if (fthree == null) {
                    fthree = new FragmentThree();
                }
                mTransaction.replace(R.id.mfg, fthree);
                break;


        }
        mTransaction.commit();
    }

    @Subscribe
    public void onEventMainThread(GetMsg event) {
        Toast.makeText(this, "5秒后页面将会发生切换", Toast.LENGTH_LONG).show();
        setCurrentPage();


    }

    /*@Subscribe(threadMode = ThreadMode.POSTING)
   *//* @Subscribe(threadMode = ThreadMode.ASYNC)
    @Subscribe(threadMode = ThreadMode.MAIN)
    @Subscribe(threadMode = ThreadMode.BACKGROUND)*//*
    public void onEventMainThread(String event) {
        Toast.makeText(this, "" + event, Toast.LENGTH_LONG).show();


    }
*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void setCurrentPage() {
        //SystemClock.sleep(5000);
        Toast.makeText(this, "正在切换", Toast.LENGTH_LONG).show();
        mTransaction = mManager.beginTransaction();
        mTransaction.replace(R.id.mfg, fthree);
        mTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.menu_one:
                Toast.makeText(this,"menu_one",Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_two:
                Toast.makeText(this,"menu_two",Toast.LENGTH_LONG).show();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }


    public void demo1() throws IOException {
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request=new Request.Builder().url("").build();
        Response call=okHttpClient.newCall(request).execute();

    }


    public void testGet() throws IOException {
        OkHttpClient okHttpClient=new OkHttpClient();
        Request build = new Request.Builder()
                .url("https://raw.github.com/square/okhttp/master/README.md")
                .build();
        Response response = okHttpClient.newCall(build)
                .execute();
        System.out.println(response);
        //Cache cache=new Cache();

        RequestBody request=new MultipartBuilder().type(MultipartBuilder.FORM).addPart(Headers.of("",""),RequestBody.create(MediaType.parse(""),"")).build();

    }
}
