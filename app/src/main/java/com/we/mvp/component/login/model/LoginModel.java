package com.we.mvp.component.login.model;


import com.we.mvp.component.login.presenter.ILoginPresenter;
import com.we.mvp.annotation.MvpProvider;

/**
 * Created to :
 *
 * @author cc.wang
 * @date 2020/5/6
 */
@MvpProvider()
public class LoginModel implements ILoginModel {

    public ILoginPresenter mLoginPresenter;

    @Override
    public void loginByWeChat() {
        mLoginPresenter.loginResult("牛皮啊!");
    }
}
