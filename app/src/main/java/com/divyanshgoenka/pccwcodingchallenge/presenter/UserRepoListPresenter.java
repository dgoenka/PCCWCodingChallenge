package com.divyanshgoenka.pccwcodingchallenge.presenter;

import android.os.Bundle;

import com.divyanshgoenka.pccwcodingchallenge.model.Repo;
import com.divyanshgoenka.pccwcodingchallenge.model.User;
import com.divyanshgoenka.pccwcodingchallenge.retrofit.GitHubApi;
import com.divyanshgoenka.pccwcodingchallenge.retrofit.ServiceGenerator;
import com.divyanshgoenka.pccwcodingchallenge.util.Constants;
import com.divyanshgoenka.pccwcodingchallenge.view.UserListView;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by divyanshgoenka on 06/08/17.
 */

public class UserRepoListPresenter {
    private UserListView mainActivtyView;

    String username;

    private GitHubApi gitHubApi = ServiceGenerator.createService(Constants.API_URL, GitHubApi.class);

    private boolean isLoading;

    int number_of_pages =0;
    int currentPage = 0;


    Observer<User> userObserver = new Observer<User>(){
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(User user) {

            if(mainActivtyView!=null){
                mainActivtyView.showUserInfo(user);
            }

            if(user!=null && user.getPublicRepos()>0)
            {
                number_of_pages = (int) Math.ceil(user.getPublicRepos()/Constants.NO_ITEMS_PER_PAGE);
                fetchRepos(++currentPage);
            }else if(user!=null && user.getPublicRepos() == 0){
                mainActivtyView.showNoReposForUser();
            }
        }

        @Override
        public void onError(Throwable e) {
            mainActivtyView.onError(e);
        }

        @Override
        public void onComplete() {

        }
    };
    private Observer<List<Repo>> reposObserverable = new Observer<List<Repo>>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(List<Repo> repos) {
            if(mainActivtyView!=null)
                mainActivtyView.addToList(repos);

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {
            setLoading(false);
        }
    };

    public void register(UserListView userListView) {
        this.mainActivtyView = userListView;
    }

    public void unregister() {
        this.mainActivtyView = null;
    }

    public void onResume() {
        startFetching();
    }


    private void startFetching() {
        setLoading(true);
        fetchTitle();
    }

    private void continueFetching() {
        setLoading(true);
        fetchRepos(++currentPage);
    }

    private void fetchRepos(int pageNumber) {
        /// TODO Can be changed to use cache in the Observable
        gitHubApi.getUserRepos(username).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(reposObserverable);
    }

    private void fetchTitle() {
        /// TODO Can be changed to use cache in the Observable
        gitHubApi.getUser(username).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(userObserver);
    }


    public boolean isLoading() {
        return isLoading;
    }

    public synchronized void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public void onCreate(Bundle bundle) {
        username = bundle.getString(Constants.KEYS_USER_ID_KEY, Constants.DEFAULT_USER_ID);
    }

    public void onLoadMore() {
        continueFetching();
    }



    public boolean hasLoadedAllItems() {
        return false;
    }
}
