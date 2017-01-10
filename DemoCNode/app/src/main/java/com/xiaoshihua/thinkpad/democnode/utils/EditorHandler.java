package com.xiaoshihua.thinkpad.democnode.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.xiaoshihua.thinkpad.democnode.R;
import com.xiaoshihua.thinkpad.democnode.model.storage.SettingShared;


import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ThinkPad on 2016/8/8.
 */
public class EditorHandler {
    private final Activity activity;
    private final InputMethodManager imm;

    private final EditText editText;

    public EditorHandler(Activity activity, View editBar, EditText editText) {
        this.activity = activity;
        this.editText = editText;
        imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        ButterKnife.bind(this,editBar);
    }

    @OnClick({R.id.btn_format_bold,R.id.btn_format_italic,
        R.id.btn_format_quote,R.id.btn_format_list_bulleted,
        R.id.btn_format_list_numbered,R.id.btn_insert_code,
        R.id.btn_insert_link,R.id.btn_insert_photo,
    R.id.btn_preview})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_format_bold://加粗
                editText.requestFocus();
                editText.getText().insert(editText.getSelectionEnd(),"**string**");
                editText.setSelection(editText.getSelectionEnd() - 8,editText.getSelectionEnd() - 2);
                imm.showSoftInput(editText,0);
                break;
            case R.id.btn_format_italic:
                //倾斜
                editText.requestFocus();
                editText.getText().insert(editText.getSelectionEnd(),"*string*");
                editText.setSelection(editText.getSelectionEnd() - 7,editText.getSelectionEnd() - 1);
                imm.showSoftInput(editText,0);
                break;
            case R.id.btn_format_quote:
                //引用
                editText.requestFocus();
                editText.getText().insert(editText.getSelectionEnd(),"\n\n> ");
                editText.setSelection(editText.getSelectionEnd());
                break;
            case R.id.btn_format_list_bulleted:
                //无序列表
                editText.requestFocus();
                editText.getText().insert(editText.getSelectionEnd(),"\n\n- ");
                editText.setSelection(editText.getSelectionEnd());
                break;
            case R.id.btn_format_list_numbered:
                //有序列表 算法需要优化
                editText.requestFocus();
                //查找向上最近一个\n
                for (int i = editText.getSelectionEnd() - 1; i >= 0; i--) {
                    char c = editText.getText().charAt(i);
                    if (c == '\n') {
                        try {
                            int dex = Integer.parseInt(editText.getText().charAt(i+ 1) + "");
                            if (editText.getText().charAt(i + 2) == '.' && editText.getText()
                                    .charAt(i + 3) == ' ')  {
                                editText.getText().insert(editText.getSelectionEnd(),"\n\n"+(dex + 1) + ".");
                                return;
                            }
                        }catch (Exception e){

                        }
                    }

                }

                //没找到
                editText.getText().insert(editText.getSelectionEnd(),"\n\n1. ");
                editText.setSelection(editText.getSelectionEnd());
            case R.id.btn_insert_code:

                //插入代码
                editText.requestFocus();
                editText.getText().insert(editText.getSelectionEnd(), "\n\n```\n\n```\n ");
                editText.setSelection(editText.getSelectionEnd() - 6);
                break;
            case R.id.btn_insert_link:

                //插入链接
                AlertDialogUtils.createBuilderWithAutoTheme(activity)
                        .setIcon(R.drawable.ic_insert_code_grey600_24dp)
                        .setTitle(R.string.add_link)
                        .setView(R.layout.dialog_tool_insert_link)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                EditText editTitle = ButterKnife.findById((Dialog) dialogInterface,R.id.edt_title);
                                EditText editLink = ButterKnife.findById((Dialog) dialogInterface,R.id.edt_link);
                                String insertText = "[" + editTitle.getText() + "](" + editLink.getText() + ")";
                                editText.requestFocus();
                                editText.getText().insert(editText.getSelectionEnd(),insertText);
                            }
                        })
                        .setNegativeButton(R.string.cancel,null)
                        .show();
                break;
            case R.id.btn_insert_photo:
                editText.requestFocus();
                editText.getText().insert(editText.getSelectionEnd(), " ![Image](http://resource) ");
                editText.setSelection(editText.getSelectionEnd() - 10,editText.getSelectionEnd() - 2);
                imm.showSoftInput(editText,0);
                break;
            case R.id.btn_preview:
                String content = editText.getText().toString();
                if (SettingShared.isEnableTopicSign(activity)) {
                    content = "\n\n" + SettingShared.getTopicSignContent(activity);
                }
                // TODO: 2016/8/9
                break;
            default:
                break;
        }
    }



}
