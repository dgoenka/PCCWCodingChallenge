package com.divyanshgoenka.pccwcodingchallenge.presenter;

import android.util.Log;

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
    private static final String TAG = "UserRepoListPresenter";
    private UserListView mainActivtyView;

    String username;

    private GitHubApi gitHubApi = ServiceGenerator.createService(Constants.API_URL, GitHubApi.class);

    private boolean isLoading;

    private int numberOfPages = 0;
    private int currentPage = 0;

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    Observer<User> userObserver = new Observer<User>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(User user) {

            if (mainActivtyView != null) {
                mainActivtyView.showUserInfo(user);
            }

            if (user != null && user.getPublicRepos() > 0) {
                numberOfPages = (int) Math.ceil((double) user.getPublicRepos() / (double) Constants.NO_ITEMS_PER_PAGE);
                fetchRepos(++currentPage);
            } else if (user != null && user.getPublicRepos() == 0) {
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
            if (currentPage == 1)
                mainActivtyView.clearList();
            if (mainActivtyView != null)
                mainActivtyView.addToList(repos);
            setLoading(false, true);

        }

        @Override
        public void onError(Throwable e) {
            mainActivtyView.onError(e);
            setLoading(false, false);

        }

        @Override
        public void onComplete() {

        }
    };

    public void register(UserListView userListView) {
        this.mainActivtyView = userListView;
    }

    public void unregister() {
        this.mainActivtyView = null;
    }

    public void onResume() {
        Log.e(TAG,"in UserRepoListPresenter.onResume");
        if (!mainActivtyView.canShowData()) {
            startFetching();
            Log.e(TAG,"in UserRepoListPresenter.onResume, starting fetch");
        }else{
            Log.e(TAG,"in UserRepoListPresenter.onResume, used fetched data");
        }
    }


    private void startFetching() {
        currentPage = 0;
        setLoading(true, null);
        fetchTitle();
    }

    private void continueFetching() {
        setLoading(true, null);
        fetchRepos(++currentPage);
    }

    private void fetchRepos(int pageNumber) {
        /// TODO Can be changed to use cache in the Observable
        Log.e(TAG, "in fetchRepos, fetching page number: " + pageNumber + " numberOfPages is " + numberOfPages);
        gitHubApi.getUserRepos(username, pageNumber).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(reposObserverable);
    }

    private void fetchTitle() {
        /// TODO Can be changed to use cache in the Observable
        gitHubApi.getUser(username).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(userObserver);
    }


    public boolean isLoading() {
        return isLoading;
    }

    public synchronized void setLoading(boolean isLoading, Boolean success) {
        this.isLoading = isLoading;
        mainActivtyView.setLoadingState(isLoading, success);

    }

    public void onCreate(String username, int currentPage, int numberOfPages) {
        this.username = username;
        this.currentPage = currentPage;
        this.numberOfPages = numberOfPages;
    }

    public void onLoadMore() {
        continueFetching();
    }


    public boolean hasLoadedAllItems() {
        return currentPage >= numberOfPages;
    }

    public void onRefresh() {
        startFetching();
    }

    public String getUsername() {
        return username;
    }
}
