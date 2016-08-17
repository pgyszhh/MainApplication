package com.example.szhh.mainapplication.Events;

/**
 * @ Created by szhh on 2016/8/8.
 * @ Date   : 2016/8/8.
 * @ Auther    : suzhiheng
 * @ Description    :TODDO
 */

public class GetMsg {
    private int num1,num2;
    public GetMsg(int num1,int num2) {
        this.num1=num1;
        this.num2=num2;
    }

    public int getResult(){
        return mCh();
    }
    public int mCh(){
        return num2*num1;
    }
}
