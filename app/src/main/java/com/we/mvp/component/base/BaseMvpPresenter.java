package com.we.mvp.component.base;

/**
 * Created to :
 *
 * @author cc.wang
 * @date 2020/4/29
 */
public abstract class BaseMvpPresenter<V,M> {

    protected V mView;

    protected M mModel;

    public BaseMvpPresenter(V mView) {
        this.mView = mView;
    }

    public void attachView(V v,M m){
        mView = v;
        mModel = m;
    }


}
