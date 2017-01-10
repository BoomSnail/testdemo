package com.xiaoshihua.thinkpad.democnode.model.bean;

import android.support.annotation.StringRes;

import com.xiaoshihua.thinkpad.democnode.R;

/**
 *
 * Created by ThinkPad on 2016/8/5.
 */
public enum  TabType {

    all(R.string.tab_all),

    good(R.string.tab_good),

    share(R.string.tab_share),

    ask(R.string.tab_ask),

    job(R.string.tab_job);

    @StringRes
    private int nameId;

    TabType(@StringRes int nameId){
        this.nameId = nameId;
    }

    @StringRes
    public int getNameId(){
        return nameId;
    }


}
