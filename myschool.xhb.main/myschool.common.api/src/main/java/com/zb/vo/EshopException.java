package com.zb.vo;

/**
 * @author kaja
 * 自定义异常
 */
public class EshopException  extends Exception{
    private String code;



    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3082579710268911612L;

    public EshopException(String msg){
        super(msg); code = "500";
    }

    public EshopException(String msg,String code){
        super(msg); this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}