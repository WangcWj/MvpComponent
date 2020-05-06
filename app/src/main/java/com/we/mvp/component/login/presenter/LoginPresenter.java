package com.we.mvp.component.login.presenter;


import com.we.mvp.component.login.model.ILoginModel;
import com.we.mvp.component.login.view.ILoginVIew;
import com.we.mvp.annotation.MvpModel;
import com.we.mvp.annotation.MvpProvider;

/**
 * Created to :
 *
 * @author cc.wang
 * @date 2020/5/6
 */
@MvpProvider()
public class LoginPresenter implements ILoginPresenter {

    @MvpModel(modelName = "ILoginPresenter",modelFieldName = "mLoginPresenter")
    public ILoginModel mLoginModel;

    public ILoginVIew mLoginView;


    @Override
    public void loginByWeChat() {
        mLoginModel.loginByWeChat();
    }

    @Override
    public void loginResult(String result) {
        mLoginView.loginResult(result);
    }
}
