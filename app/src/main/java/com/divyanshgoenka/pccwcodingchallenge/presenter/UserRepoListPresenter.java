package com.divyanshgoenka.pccwcodingchallenge.presenter;

import com.divyanshgoenka.pccwcodingchallenge.view.UserListView;

/**
 * Created by divyanshgoenka on 06/08/17.
 */

public class UserRepoListPresenter {
    private UserListView mainActivtyView;

    public void register(UserListView userListView) {
        this.mainActivtyView = userListView;
    }

    public void unregister() {
        this.mainActivtyView = null;
    }

    public void onResume() {
        startFetching();
    }

    /**
     * TODO Can be changed to use cache in the Observable
     * */
    private void startFetching() {

    }
}
