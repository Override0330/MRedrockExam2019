package com.override0330.android.redrock.mredrockexam2019.httprequsethelper;

public class Request {
    private String url;
    private String method;
    private int connectTimeout;
    private int readTimeout;
    private String [] key;
    private String [] value;
    private Callback callback;
    private String contentType;

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public String[] getKey() {
        return key;
    }

    public String[] getValue() {
        return value;
    }

    public Callback getCallback() {
        return callback;
    }

    public String getContentType() {
        return contentType;
    }

    //自定义的回调接口
    public void setCallback(Callback callback) {
        this.callback = callback;
    }


    /**
     * 建造者模式
     * @param Builder
     */

    //建造者模式下的构造器构造器
    public Request(Builder Builder){
        this.url = Builder.url;
        this.method = Builder.method;
        this.connectTimeout = Builder.connectTimeout;
        this.readTimeout = Builder.readTimeout;
        this.key = Builder.key;
        this.value = Builder.value;
        this.contentType = Builder.contentType;
    }

    //建造者模式内部类
    public static class Builder{
        private String url;
        private String method = "GET";
        private int connectTimeout = 5000;
        private int readTimeout = 5000;
        private String [] key = null;
        private String [] value = null;
        private String contentType = "application/x-www-form-urlencoded";


        public Builder(String url) {
            this.url = url;
        }

        public Builder setMethod(String method){
            this.method = method;
            return this;
        }

        public Builder setConnetTimeout(int connectTimeout){
            this.connectTimeout = connectTimeout;
            return this;
        }

        public Builder setReadTimeout(int readTimeout){
            this.readTimeout = readTimeout;
            return this;
        }

        public Builder setKeyArray(String [] key){
            this.key = key;
            return this;
        }

        public Builder setValueArray(String [] value){
            this.value = value;
            return this;
        }

        public Builder setContentType(String contentType){
            this.contentType = contentType;
            return this;
        }

        public Request build(){
            return new Request(this);
        }

    }



}

