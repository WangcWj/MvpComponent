package com.we.mvp.component.login.presenter;


import com.we.mvp.component.login.model.ILoginModel;

/**
 * Created to :
 *
 * @author cc.wang
 * @date 2020/5/6
 */
public interface ILoginPresenter extends ILoginModel {

    void loginResult(String result);
}
