package com.xiaoshihua.thinkpad.democnode.ui.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.xiaoshihua.thinkpad.democnode.R;
import com.xiaoshihua.thinkpad.democnode.ui.base.StatusBarActivity;
import com.xiaoshihua.thinkpad.democnode.ui.listener.NavigationFinishClickListener;
import com.xiaoshihua.thinkpad.democnode.utils.Navigator;

import org.joda.time.DateTime;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CrashLogActivity extends StatusBarActivity implements Toolbar.OnMenuItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_info)
    TextView tvInfo;

    private String crashLog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash_log);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
        toolbar.inflateMenu(R.menu.crash_log);
        toolbar.setOnMenuItemClickListener(this);

        //接受异常对象
        Intent intent = getIntent();
        Throwable e = (Throwable) intent.getSerializableExtra("e");

        //构建字符串
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("生产厂商：\n");
        stringBuilder.append(Build.MANUFACTURER).append("\n\n");
        stringBuilder.append("手机型号：\n");
        stringBuilder.append(Build.MODEL).append("\n\n");
        stringBuilder.append("系统版本：\n");
        stringBuilder.append(Build.VERSION.RELEASE).append("\n\n");
        stringBuilder.append("异常时间：\n");
        stringBuilder.append(new DateTime()).append("\n\n");
        stringBuilder.append("异常类型：\n");
        stringBuilder.append(e.getClass().getName()).append("\n\n");
        stringBuilder.append("异常信息：\n");
        stringBuilder.append(e.getMessage()).append("\n\n");
        stringBuilder.append("异常堆栈：\n");

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        e.printStackTrace();

        Throwable cause = e.getCause();
        while (cause != null){
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }

        printWriter.close();
        stringBuilder.append(writer.toString());
        crashLog = stringBuilder.toString();

        //显示信息
        tvInfo.setText(crashLog);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_send:
                Navigator.openEmail(
                        this,
                        "takwolf@foxmail.com",
                        "来自 CNodeMD-" + AboutActivity.VERSION_TEXT + " 的客户端崩溃日志",
                        crashLog
                );
                return true;
        }
        return false;
    }
}
