package com.we.mvp.component.login.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.we.mvp.WeMvpFrame;
import com.we.mvp.annotation.MvpPresenter;
import com.we.mvp.component.R;
import com.google.android.gms.plus.PlusOneButton;
import com.we.mvp.component.login.presenter.ILoginPresenter;

/**
 * A fragment with a Google +1 button.
 */
public class PlusOneFragment extends Fragment implements ILoginVIew {

    @MvpPresenter(viewName = "ILoginVIew", viewFieldName = "mLoginView")
    public ILoginPresenter mLoginPresenter;

    public PlusOneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_plus_one, container, false);
        WeMvpFrame.register(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLoginPresenter.loginByWeChat();
    }

    @Override
    public void loginResult(String result) {
        Log.e("cc.wang", "PlusOneFragment.loginResult." + result);
    }
}
