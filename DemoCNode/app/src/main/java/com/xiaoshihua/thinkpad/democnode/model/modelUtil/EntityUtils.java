package com.xiaoshihua.thinkpad.democnode.model.modelUtil;

import com.google.gson.Gson;

import com.google.gson.GsonBuilder;

import org.joda.time.DateTime;

/**
 * Created by ThinkPad on 2016/8/5.
 */
public class EntityUtils {

    public static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(DateTime.class,//为了解析时间
                    new DateTypeAdapter())
            .create();
}
