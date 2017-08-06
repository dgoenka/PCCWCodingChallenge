package com.divyanshgoenka.pccwcodingchallenge.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.divyanshgoenka.pccwcodingchallenge.presenter.UserRepoListPresenter;
import com.divyanshgoenka.pccwcodingchallenge.view.UserListView;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;
import com.mugen.attachers.BaseAttacher;

/**
 * Created by divyanshgoenka on 05/08/17.
 */

public class UserRepoListActivity extends AppCompatActivity implements MugenCallbacks,UserListView {
    RecyclerView recyclerView;
    BaseAttacher attacher;
    UserRepoListPresenter userRepoListPresenter = new UserRepoListPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = new RecyclerView(this);
        attacher = Mugen.with(recyclerView,this).start();
        setContentView(recyclerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        register();
        userRepoListPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregister();
    }

    /**
     * Method to contain all observer and BroadcastReceiver registrations (called in onResume)
     *
     * */
    public void register(){
        userRepoListPresenter.register(this);

    }

    /**
     * Method to contain all observer and BroadcastReceiver unregistrations (called in onPause)
     *
     * */
    public void unregister(){
        userRepoListPresenter.unregister();

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public boolean isLoading() {
        return false;
    }

    @Override
    public boolean hasLoadedAllItems() {
        return false;
    }
}
