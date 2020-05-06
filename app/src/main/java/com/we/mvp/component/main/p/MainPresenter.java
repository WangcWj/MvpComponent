package com.we.mvp.component.main.p;


import com.we.mvp.annotation.MvpModel;
import com.we.mvp.annotation.MvpProvider;
import com.we.mvp.component.login.model.ILoginModel;
import com.we.mvp.component.login.presenter.ILoginPresenter;
import com.we.mvp.component.login.view.ILoginVIew;
import com.we.mvp.component.main.IMainVIew;
import com.we.mvp.component.main.m.IMainModel;

/**
 * Created to :
 *
 * @author cc.wang
 * @date 2020/5/6
 */
@MvpProvider()
public class MainPresenter implements IMainPresenter {

    @MvpModel(modelName = "IMainPresenter",modelFieldName = "mainPresenter")
    public IMainModel mainModel;

    public IMainVIew mainVIew;


    @Override
    public void loginByWeChat() {
        mainModel.loginByWeChat();
    }

    @Override
    public void loginResult(String result) {
        mainVIew.loginResult(result);
    }
}
