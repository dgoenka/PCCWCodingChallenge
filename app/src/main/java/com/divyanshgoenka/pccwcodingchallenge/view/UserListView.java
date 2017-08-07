package com.divyanshgoenka.pccwcodingchallenge.view;

import com.divyanshgoenka.pccwcodingchallenge.model.Repo;
import com.divyanshgoenka.pccwcodingchallenge.model.User;

import java.util.List;

/**
 * Created by divyanshgoenka on 06/08/17.
 */

public interface UserListView {
    void showUserInfo(User user);

    void addToList(List<Repo> repos);

    void onError(Throwable e);

    void showNoReposForUser();

    void clearList();

    void setLoadingState(boolean isLoading, Boolean success);
}
