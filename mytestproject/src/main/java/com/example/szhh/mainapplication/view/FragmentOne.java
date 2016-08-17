package com.example.szhh.mainapplication.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.szhh.mainapplication.Events.GetMsg;
import com.example.szhh.mainapplication.R;
;import org.greenrobot.eventbus.EventBus;

/**
 * @ Created by szhh on 2016/8/3.
 * @ Date   : 2016/8/3.
 * @ Auther    : suzhiheng
 * @ Description    :TODDO
 */

public class FragmentOne extends Fragment {
    EventBus eventBus= EventBus.getDefault();
    private Button mButtonm;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //eventBus.register(this);
        View view=inflater.inflate(R.layout.fragment1,container,false);
        mButtonm= (Button) view.findViewById(R.id.posts);
        mButtonm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // eventBus.post(new GetMsg(100,200));
                String mss="wogglllg";
                eventBus.post(mss);
            }
        });
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //super.onCreateOptionsMenu(menu, inflater);
        //inflater.inflate();
    }
}
