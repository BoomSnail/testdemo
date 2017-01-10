package com.xiaoshihua.thinkpad.democnode.ui.listener;

import android.content.DialogInterface;

import retrofit2.Call;

/**
 * Created by ThinkPad on 2016/8/6.
 */
public class DialogCancelCallListener implements DialogInterface.OnCancelListener {

    private final Call call;

    public DialogCancelCallListener(Call call) {
        this.call = call;
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {
        if (!call.isCanceled()) {
            call.cancel();
        }
    }
}
