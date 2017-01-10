package com.xiaoshihua.thinkpad.democnode.utils;

import android.content.Context;
import android.content.res.TypedArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 *
 * Created by ThinkPad on 2016/8/5.
 */
public class ResUtils {
    public static int getStatusBarHeight(Context context) {
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return resId > 0 ? context.getResources().getDimensionPixelSize(resId) : 0;
    }

    public static int getThemeAttrColor(Context context, int attr) {
        TypedArray array = context.obtainStyledAttributes(null, new int[]{attr});
        try {
            return array.getColor(0, 0);
        } finally {
            array.recycle();
        }
    }


    public static String getRawString(Context context, int resId) {
        InputStream inputStream = context.getResources().openRawResource(resId);
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            bufferedReader.close();
            return stringBuilder.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
