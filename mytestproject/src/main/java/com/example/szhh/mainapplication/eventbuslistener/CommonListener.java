package com.example.szhh.mainapplication.eventbuslistener;

/**
 * @ Created by szhh on 2016/8/14.
 * @ Date   : 2016/8/14.
 * @ Auther    : suzhiheng
 * @ Description    :TODDO
 */

public class CommonListener {
    private int what;
    private int arg1;
    private int arg2;
    private String msg;
    private Object obj;

    public CommonListener(int what, Object obj, int arg2, int arg1, String msg) {
        this.what = what;
        this.obj = obj;
        this.arg2 = arg2;
        this.arg1 = arg1;
        this.msg = msg;
    }

    public int getWhat() {
        return what;
    }

    public void setWhat(int what) {
        this.what = what;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getArg2() {
        return arg2;
    }

    public void setArg2(int arg2) {
        this.arg2 = arg2;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public int getArg1() {
        return arg1;
    }

    public void setArg1(int arg1) {
        this.arg1 = arg1;
    }
}
