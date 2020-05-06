package com.we.mvp.component.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.we.mvp.WeMvpFrame;
import com.we.mvp.annotation.MvpPresenter;
import com.we.mvp.component.R;
import com.we.mvp.component.login.view.LoginActivity;
import com.we.mvp.component.main.p.IMainPresenter;

public class MainActivity extends AppCompatActivity implements IMainVIew {

    @MvpPresenter(viewName = "IMainVIew", viewFieldName = "mainVIew")
    public IMainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WeMvpFrame.register(this);
        setContentView(R.layout.activity_main);
        mainPresenter.loginByWeChat();
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void loginResult(String result) {
        Log.e("cc.wang", "MainActivity.loginResult ==== " + result);
    }
}
