package com.zb.pojo;

import java.io.Serializable;

public class ClassTask implements Serializable {
    private String id;
    private String update_time;
    private String mq_exchange;
    private String mq_routingkey;
    private String request_body;
    private Integer version;
    private Integer count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getMq_exchange() {
        return mq_exchange;
    }

    public void setMq_exchange(String mq_exchange) {
        this.mq_exchange = mq_exchange;
    }

    public String getMq_routingkey() {
        return mq_routingkey;
    }

    public void setMq_routingkey(String mq_routingkey) {
        this.mq_routingkey = mq_routingkey;
    }

    public String getRequest_body() {
        return request_body;
    }

    public void setRequest_body(String request_body) {
        this.request_body = request_body;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
