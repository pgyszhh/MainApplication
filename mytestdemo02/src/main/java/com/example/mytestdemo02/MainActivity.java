package com.example.mytestdemo02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zhy.autolayout.AutoLayoutActivity;

public class MainActivity extends AutoLayoutActivity {
    private Button bt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        bt = (Button) findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startIntents(com.example.mytestdemo02.view.MainActivity.class);
            }
        });
    }

    public <T>void startIntents(Class<T> intentClass){
        Intent i=new Intent(this,intentClass);
        startActivity(i);
    }
}
