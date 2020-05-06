package com.we.mvp.component.base;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public abstract class BaseMvpActivity<M, V, P extends BaseMvpPresenter<V, M>> extends AppCompatActivity {

    protected P mPresenter;

    protected abstract P createPresenter();

    protected abstract M createModel(P p);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        if (null == mPresenter) {
            throw new NullPointerException(this + "# mPresenter is null !");
        }
        M model = createModel(mPresenter);
        if (null == mPresenter) {
            throw new NullPointerException(this + "# model is null !");
        }
        mPresenter.attachView((V) this, model);
    }
}
