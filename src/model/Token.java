package model;

/**
 * Created by kail on 2017/4/27.
 */
public class Token {


    /**
     * access_token : ACCESS_TOKEN
     * expires_in : 7200
     * errcode : 40013
     * errmsg : invalid appid
     */

    private String access_token="";
    private int expires_in;
    private int errcode;
    private String errmsg="";

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
