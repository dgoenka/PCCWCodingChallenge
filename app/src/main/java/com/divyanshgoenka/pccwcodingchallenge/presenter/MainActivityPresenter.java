package com.divyanshgoenka.pccwcodingchallenge.presenter;

import com.divyanshgoenka.pccwcodingchallenge.view.MainActivityView;

/**
 * Created by divyanshgoenka on 06/08/17.
 */

public class MainActivityPresenter {
    private MainActivityView mainActivtyView;

    public void register(MainActivityView mainActivityView) {
        this.mainActivtyView = mainActivityView;
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
