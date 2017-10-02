package com.prenetics.loginandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.prenetics.loginpresenterandroid.presenter.ILoginMvpPresenter;
import com.prenetics.loginpresenterandroid.presenter.LoginPresenter;
import com.prenetics.loginpresenterandroid.view.ILoginMvpView;

public class MainActivity extends AppCompatActivity implements ILoginMvpView, View.OnClickListener {

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
        mLoginMvpPresenter.onStop();
    }

    @Override
    public void findView() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void showWaitingCursor() {

    }

    @Override
    public void hideWaitingCursor() {

    }

    @Override
    public void onClick(View view) {

    }
}
