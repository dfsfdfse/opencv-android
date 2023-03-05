package org.realcool.base.msg;

public class BaseMsg {
    public static final int FINISH = 1;

    public static final int FAIL = 2;

    private int code;

    private String msg;

    private Object data;

    public BaseMsg() {
        this.code = FINISH;
    }

    public BaseMsg(int code) {
        this.code = code;
    }

    public BaseMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseMsg(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public BaseMsg(int code, String msg, Object data) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static BaseMsg newInstance(int code, Object data){
        return new BaseMsg(code, data);
    }

    public int getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
