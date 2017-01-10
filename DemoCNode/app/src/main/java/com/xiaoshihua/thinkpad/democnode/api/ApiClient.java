package com.xiaoshihua.thinkpad.democnode.api;

import com.xiaoshihua.thinkpad.democnode.model.modelUtil.EntityUtils;
import com.xiaoshihua.thinkpad.democnode.model.modelUtil.HttpUtils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ThinkPad on 2016/8/5.
 */
public class ApiClient {

    public static ApiService service = new Retrofit.Builder()
            .baseUrl(ApiDefine.API_BASE_URL)
            .client(HttpUtils.client)
            .addConverterFactory(GsonConverterFactory.create(EntityUtils.gson))
            .build()
            .create(ApiService.class);
}
