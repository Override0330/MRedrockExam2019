package com.override0330.android.redrock.mredrockexam2019.net.httprequsethelper;

public interface Callback {
    void onResponse(String response);
    void onFailed(Throwable t);
}
