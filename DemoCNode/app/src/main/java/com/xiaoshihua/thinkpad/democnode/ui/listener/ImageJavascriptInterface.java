package com.xiaoshihua.thinkpad.democnode.ui.listener;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.xiaoshihua.thinkpad.democnode.ui.activities.ImagePreviewActivity;

/**
 *
 * Created by ThinkPad on 2016/8/7.
 */
public final class ImageJavascriptInterface {

    private volatile static ImageJavascriptInterface singleton;

    public static ImageJavascriptInterface getSingleton(Context context){
        if (singleton == null) {
            synchronized (ImageJavascriptInterface.class){
                if (singleton == null){
                    singleton = new ImageJavascriptInterface(context);
                }
            }
        }
        return singleton;
    }

    public static final String NAME = "imageBridge";

    private final  Context context;

    public ImageJavascriptInterface(Context context) {
        this.context = context.getApplicationContext();
    }

    @JavascriptInterface
    public void openImage(String imageUrl){
        ImagePreviewActivity.start(context,imageUrl);
    }
}
