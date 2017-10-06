package com.prenetics.loginandroid;

import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.prenetics.loginpresenterandroid.model.data.request.LoginData;
import com.prenetics.loginpresenterandroid.networks.BaseUrl;
import com.prenetics.loginpresenterandroid.presenter.ILoginMvpPresenter;
import com.prenetics.loginpresenterandroid.presenter.LoginPresenter;
import com.prenetics.loginpresenterandroid.utils.LoginUtils;
import com.prenetics.loginpresenterandroid.view.ILoginMvpView;

public class MainActivity extends AppCompatActivity implements ILoginMvpView {
    private ILoginMvpPresenter mLoginMvpPresenter;
    private EditText editAccount;
    private EditText editPwd;
    private View btnLogin, btnLogout;
    private View editUserBg;
    private View editPassBg;
    private ProgressBar progressBar;
    
    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.login_submit: {
                    if (null == editPwd || null == editAccount || TextUtils.isEmpty(editPwd.getText().toString()) || TextUtils.isEmpty(editAccount.getText().toString())) {
                        showToast("Input Error");
                        break;
                    }

                    mLoginMvpPresenter.login(new LoginData.Builder(
                                    Globals.getInstance(),
                                    editAccount.getText().toString(),
                                    editPwd.getText().toString(),
                                    Settings.Secure.getString(Globals.getInstance().getContentResolver(), Settings.Secure.ANDROID_ID),
                                    "fitlife",
                                    BaseUrl.PRODUCTION).build());
                    break;
                }

                case R.id.logout_submit: {
                    LoginUtils.clearLoginData(Globals.getInstance());
                    setupLoginForm();
                    break;
                }
            }
        }
    };
    
    private View.OnFocusChangeListener mFocusChangeListener = new View.OnFocusChangeListener() {
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
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoginMvpPresenter = new LoginPresenter(this);
        initUiComponent();
    }

    private void initUiComponent() {
        editAccount =(EditText) findViewById(R.id.login_username);
        editPwd = (EditText) findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.login_submit);
        btnLogout = findViewById(R.id.logout_submit);
        progressBar = (ProgressBar) findViewById(R.id.login_progressbar);
        editUserBg = findViewById(R.id.login_username_background);
        editPassBg = findViewById(R.id.login_password_background);

        editAccount.setOnFocusChangeListener(mFocusChangeListener);
        editPwd.setOnFocusChangeListener(mFocusChangeListener);
        btnLogin.setOnClickListener(mClickListener);
        btnLogout.setOnClickListener(mClickListener);
        setupLoginForm();
    }

    protected void onDestroy() {
        super.onDestroy();
        mLoginMvpPresenter.onDestroy();
        mLoginMvpPresenter = null;
    }

    public void showWaitingCursor() {
        if (null == progressBar) return;
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideWaitingCursor() {
        if (null == progressBar) return;
        progressBar.setVisibility(View.GONE);
    }

    public void showToast(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
        Log.e("jason", this.getClass().getSimpleName());
    }

    @Override
    public void onLoginStart() {
        editPwd.clearFocus();
        editAccount.clearFocus();
        btnLogin.requestFocus();
        showWaitingCursor();
    }

    @Override
    public void navigateToHome() {
        hideWaitingCursor();
        showToast("Login Success");
        setupLoginForm();
    }

    @Override
    public void loginFailed() {
        setupLoginForm();
        hideWaitingCursor();
        showToast("Login failed");
    }

    private void setupLoginForm() {
        if (LoginUtils.isLogin(Globals.getInstance())) {
            findViewById(R.id.login_form).setVisibility(View.INVISIBLE);
            btnLogout.setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.login_form).setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.INVISIBLE);
        }
    }
}
