package com.xiaoshihua.thinkpad.democnode.ui.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.xiaoshihua.thinkpad.democnode.R;
import com.xiaoshihua.thinkpad.democnode.model.bean.Result;
import com.xiaoshihua.thinkpad.democnode.model.storage.LoginShared;
import com.xiaoshihua.thinkpad.democnode.ui.base.FullLayoutActivity;
import com.xiaoshihua.thinkpad.democnode.ui.listener.DialogCancelCallListener;
import com.xiaoshihua.thinkpad.democnode.ui.listener.NavigationFinishClickListener;
import com.xiaoshihua.thinkpad.democnode.utils.AlertDialogUtils;
import com.xiaoshihua.thinkpad.democnode.utils.DisplayUtils;
import com.xiaoshihua.thinkpad.democnode.utils.FormatUtils;
import com.xiaoshihua.thinkpad.democnode.ui.dialog.ProgressDialog;
import com.xiaoshihua.thinkpad.democnode.utils.ToastUtils;
import com.xiaoshihua.thinkpad.democnode.view.ILoginView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import presenter.contract.ILoginPresenter;
import presenter.implement.LoginPresenter;
import retrofit2.Call;

public class LoginActivity extends FullLayoutActivity implements ILoginView {

    public static final int REQUEST_LOGIN = FormatUtils.createRequestCode();

    @BindView(R.id.adapt_status_bar)
    View adaptStatusBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_access_token)
    MaterialEditText edtAccessToken;

    private ProgressDialog progressDialog;
    private ILoginPresenter iLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        DisplayUtils.adaptStatusBar(this, adaptStatusBar);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));

        progressDialog = ProgressDialog.createWithAutoTheme(this);
        progressDialog.setMessage(R.string.logging_in_$_);

        iLoginPresenter = new LoginPresenter(this, this);
    }

    @Override
    public void onAccessTokenFormatError() {
        edtAccessToken.setError(getString(R.string.access_token_format_error));
        edtAccessToken.requestFocus();
    }

    @Override
    public void onLoginStart(Call<Result.Login> call) {
        progressDialog.setOnCancelListener(new DialogCancelCallListener(call));
        progressDialog.show();
    }

    @Override
    public boolean onLoginResultOk(String accessToken, Result.Login login) {
        LoginShared.login(this,accessToken,login);
        ToastUtils.getInstance(this).show(R.string.login_success);
        setResult(RESULT_OK);
        finish();
        return false;
    }

    @Override
    public boolean onLoginResultErrorAuth(Result.Error error) {
        edtAccessToken.setError(getString(R.string.access_token_format_error));
        edtAccessToken.requestFocus();
        return false;
    }

    @Override
    public void LoginFinish() {
        progressDialog.setOnCancelListener(null);
        progressDialog.dismiss();
    }

    @OnClick({R.id.btn_qrcode, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_qrcode:
                QRCodeActivity.startForResultWithPermissionCheck(this);
                break;
            case R.id.btn_login:
                iLoginPresenter.loginAsyncTask(edtAccessToken.getText().toString().trim());
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == QRCodeActivity.PERMISSION_REQUEST_QRCODE) {
            QRCodeActivity.StartForResultWithPermissionHandle(this);
        }
    }

    public static void startForResult(Activity activity) {
        activity.startActivityForResult(new Intent(activity,LoginActivity.class),REQUEST_LOGIN);
    }

    public static boolean startForResultWithAccessTokenCheck(final Activity activity) {

        if (TextUtils.isEmpty(LoginShared.getAccessToken(activity))) {
            AlertDialogUtils.createBuilderWithAutoTheme(activity)
                    .setMessage(R.string.need_login_tip)
                    .setPositiveButton(R.string.login, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startForResult(activity);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .show();
            return false;
        }else {
            return true;
        }
    }
}
