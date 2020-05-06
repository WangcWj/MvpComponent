package com.we.mvp.component.base;

/**
 * Created to :
 *
 * @author cc.wang
 * @date 2020/4/29
 */
public abstract class BaseMvpModel<P> {

    protected P mPresenter;

    public BaseMvpModel(P mPresenter) {
        this.mPresenter = mPresenter;
    }
}
