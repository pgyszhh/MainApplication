package com.example.szhh.mainapplication.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.szhh.mainapplication.R;

/**
 * @ Created by szhh on 2016/8/3.
 * @ Date   : 2016/8/3.
 * @ Auther    : suzhiheng
 * @ Description    :TODDO
 */

public class FragmentTwo extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment2,container,false);
    }
}
