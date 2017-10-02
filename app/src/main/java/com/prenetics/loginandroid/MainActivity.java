package com.prenetics.loginandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.prenetics.loginpresenterandroid.presenter.ILoginMvpPresenter;
import com.prenetics.loginpresenterandroid.presenter.LoginPresenter;
import com.prenetics.loginpresenterandroid.view.ILoginMvpView;

public class MainActivity extends AppCompatActivity implements ILoginMvpView, View.OnClickListener,View.OnFocusChangeListener {
    private EditText editUser;
    private EditText editPass;
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
        editUser =(EditText) findViewById(R.id.login_username);
        editPass = (EditText) findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.login_submit);
        progressBar = (ProgressBar) findViewById(R.id.login_progressbar);
        editUserBg = findViewById(R.id.login_username_background);
        editPassBg = findViewById(R.id.login_password_background);
    }

    @Override
    public void setListener() {
        btnLogin.setOnClickListener(this);
        editUser.setOnFocusChangeListener(this);
        editPass.setOnFocusChangeListener(this);
        btnLogin.setOnFocusChangeListener(this);
    }

    @Override
    public void showWaitingCursor() {
        if (null == progressBar) return;
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideWaitingCursor() {
        if (null == progressBar) return;
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_submit: {
                mLoginMvpPresenter.onLogin();
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
    public void clearFocus() {
        editPass.clearFocus();
        editUser.clearFocus();
        btnLogin.requestFocus();
    }
}
