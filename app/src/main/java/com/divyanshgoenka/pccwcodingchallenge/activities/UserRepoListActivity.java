package com.divyanshgoenka.pccwcodingchallenge.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.divyanshgoenka.pccwcodingchallenge.model.Repo;
import com.divyanshgoenka.pccwcodingchallenge.model.User;
import com.divyanshgoenka.pccwcodingchallenge.presenter.UserRepoListPresenter;
import com.divyanshgoenka.pccwcodingchallenge.util.Constants;
import com.divyanshgoenka.pccwcodingchallenge.view.UserListView;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;
import com.mugen.attachers.BaseAttacher;

import java.util.List;

/**
 * Created by divyanshgoenka on 05/08/17.
 */

public class UserRepoListActivity extends AppCompatActivity implements MugenCallbacks,UserListView {
    RecyclerView recyclerView;
    BaseAttacher attacher;
    UserRepoListPresenter userRepoListPresenter = new UserRepoListPresenter();
    private View noReposView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = new RecyclerView(this);
        setContentView(recyclerView);
        attacher = Mugen.with(recyclerView,this).start();
        attacher.loadMoreOffset(Constants.LOAD_MORE_OFFSET);
        userRepoListPresenter.onCreate(savedInstanceState==null?getIntent().getExtras():savedInstanceState);
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
        userRepoListPresenter.onLoadMore();
    }

    @Override
    public boolean isLoading() {
        return userRepoListPresenter.isLoading();
    }

    @Override
    public boolean hasLoadedAllItems() {
        return userRepoListPresenter.hasLoadedAllItems();
    }

    @Override
    public void showUserInfo(User user) {

    }

    @Override
    public void addToList(List<Repo> repos) {
        recyclerView.setVisibility(View.GONE);
        noReposView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void showNoReposForUser() {
        recyclerView.setVisibility(View.GONE);
        noReposView.setVisibility(View.VISIBLE);
    }
}
