package com.we.mvp.component.main.m;


import com.we.mvp.annotation.MvpProvider;
import com.we.mvp.component.login.model.ILoginModel;
import com.we.mvp.component.login.presenter.ILoginPresenter;
import com.we.mvp.component.main.p.IMainPresenter;

/**
 * Created to :
 *
 * @author cc.wang
 * @date 2020/5/6
 */
@MvpProvider()
public class MainModel implements IMainModel {

    public IMainPresenter mainPresenter;

    @Override
    public void loginByWeChat() {
        mainPresenter.loginResult("真是牛皮");
    }
}
