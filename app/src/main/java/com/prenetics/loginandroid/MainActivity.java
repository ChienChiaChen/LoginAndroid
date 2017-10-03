package com.prenetics.loginandroid;

import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.prenetics.loginpresenterandroid.model.data.request.LoginData;
import com.prenetics.loginpresenterandroid.presenter.ILoginMvpPresenter;
import com.prenetics.loginpresenterandroid.presenter.LoginPresenter;
import com.prenetics.loginpresenterandroid.view.ILoginMvpView;

public class MainActivity extends AppCompatActivity implements ILoginMvpView, View.OnClickListener,View.OnFocusChangeListener {
    private EditText editAccount;
    private EditText editPwd;
    private View btnLogin;
    private View editUserBg;
    private View editPassBg;
    private ProgressBar progressBar;

    private ILoginMvpPresenter mLoginMvpPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoginMvpPresenter = new LoginPresenter(this);
        mLoginMvpPresenter.onCreate();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLoginMvpPresenter.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLoginMvpPresenter.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLoginMvpPresenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginMvpPresenter.onDestroy();
        mLoginMvpPresenter = null;
    }

    @Override
    public void findView() {
        editAccount =(EditText) findViewById(R.id.login_username);
        editPwd = (EditText) findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.login_submit);
        progressBar = (ProgressBar) findViewById(R.id.login_progressbar);
        editUserBg = findViewById(R.id.login_username_background);
        editPassBg = findViewById(R.id.login_password_background);
    }

    @Override
    public void setListener() {
        btnLogin.setOnClickListener(this);
        editAccount.setOnFocusChangeListener(this);
        editPwd.setOnFocusChangeListener(this);
        btnLogin.setOnFocusChangeListener(this);
    }

    public void showWaitingCursor() {
        if (null == progressBar) return;
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideWaitingCursor() {
        if (null == progressBar) return;
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_submit: {
                mLoginMvpPresenter.onLogin(new LoginData.Builder()
                                    .setAccount(editAccount.getText().toString())
                                    .setPassword(editPwd.getText().toString())
                                    .setUid(Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID))
                                    .setProduct("fitlifee")
                                    .build());
                break;
            }
        }
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        editUserBg.setActivated(false);
        editPassBg.setActivated(false);
        switch (view.getId()) {
            case R.id.login_username: {
                editUserBg.setActivated(hasFocus);
                break;
            }
            case R.id.login_password: {
                editPassBg.setActivated(hasFocus);
                break;
            }
        }
    }

    @Override
    public void startLogin() {
        editPwd.clearFocus();
        editAccount.clearFocus();
        btnLogin.requestFocus();
        showWaitingCursor();
    }

    @Override
    public void endLogin() {
        hideWaitingCursor();
    }

    @Override
    public void onLoginSuccess() {
        hideWaitingCursor();
        showToast("Success");
    }

    @Override
    public void onLoginFail() {
        hideWaitingCursor();
        showToast("Fail");
    }

    public void showToast(String toast){
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }
}
