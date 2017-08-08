package com.divyanshgoenka.pccwcodingchallenge.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.divyanshgoenka.pccwcodingchallenge.R;
import com.divyanshgoenka.pccwcodingchallenge.adapter.RepoListAdapter;
import com.divyanshgoenka.pccwcodingchallenge.android.util.BundleUtils;
import com.divyanshgoenka.pccwcodingchallenge.android.util.FormatUtils;
import com.divyanshgoenka.pccwcodingchallenge.model.Repo;
import com.divyanshgoenka.pccwcodingchallenge.model.User;
import com.divyanshgoenka.pccwcodingchallenge.presenter.UserRepoListPresenter;
import com.divyanshgoenka.pccwcodingchallenge.util.Constants;
import com.divyanshgoenka.pccwcodingchallenge.view.UserListView;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;
import com.mugen.attachers.BaseAttacher;
import com.squareup.picasso.Picasso;

import net.steamcrafted.loadtoast.LoadToast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by divyanshgoenka on 05/08/17.
 */

public class UserRepoListActivity extends AppCompatActivity implements MugenCallbacks, UserListView, SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = "UserRepoListActivity";
    UserRepoListPresenter userRepoListPresenter = new UserRepoListPresenter();

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    BaseAttacher attacher;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.no_repo_view)
    View noReposView;

    LoadToast loadToast;

    TitleViewHolder titleViewHolder = new TitleViewHolder();
    private User user;

    @Override
    public void onRefresh() {
        userRepoListPresenter.onRefresh();
    }

    class TitleViewHolder {
        @BindView(R.id.user_name_textview)
        TextView userNameTextView;
        @BindView(R.id.user_avatar_image_view)
        ImageView userAvatarImageView;
        @BindView(R.id.user_repo_count_text_view)
        TextView userRepoCountTextView;
    }

    View titleView;

    final ArrayList<Repo> repos = new ArrayList<>();
    private RepoListAdapter repoListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repo_list_activity);
        ButterKnife.bind(this);
        setupViews(savedInstanceState);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Constants.KEYS_ITEM_LIST, repos);
        outState.putSerializable(Constants.KEYS_USER, user);
        outState.putString(Constants.KEYS_USER_ID_KEY,userRepoListPresenter.getUsername());
        outState.putInt(Constants.KEYS_CURRENT_PAGE,userRepoListPresenter.getCurrentPage());
        outState.putInt(Constants.KEYS_NO_OF_PAGES,userRepoListPresenter.getNumberOfPages());

    }

    private void setupViews(Bundle savedInstanceState) {
        swipeRefreshLayout.setOnRefreshListener(this);
        Bundle bundle = savedInstanceState == null ? getIntent().getExtras() : savedInstanceState;
        String username = BundleUtils.getString(bundle,Constants.KEYS_USER_ID_KEY,Constants.DEFAULT_USER_ID);
        int currentPage = BundleUtils.getInt(bundle,Constants.KEYS_CURRENT_PAGE,Constants.ZERO);
        int noOfPages = BundleUtils.getInt(bundle,Constants.KEYS_NO_OF_PAGES,Constants.ZERO);
        user = (User) BundleUtils.getSerializable(bundle,Constants.KEYS_USER,null);
        repos.addAll((ArrayList<Repo>)BundleUtils.getSerializable(bundle,Constants.KEYS_ITEM_LIST,new ArrayList<>()));
        userRepoListPresenter.onCreate(username,currentPage,noOfPages);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.title_view_layout);
        ButterKnife.bind(titleViewHolder, getSupportActionBar().getCustomView());

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
     */
    public void register() {
        userRepoListPresenter.register(this);

    }

    /**
     * Method to contain all observer and BroadcastReceiver unregistrations (called in onPause)
     */
    public void unregister() {
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
        if (user != null) {
            this.user = user;
            titleViewHolder.userNameTextView.setText(user.getName());
            Picasso.with(this).load(user.getAvatarUrl()).into(titleViewHolder.userAvatarImageView);
            titleViewHolder.userRepoCountTextView.setText(String.format(getString(R.string.num_repositores), user.getPublicRepos()));
        }
    }

    @Override
    public void addToList(List<Repo> repos) {
        swipeRefreshLayout.setRefreshing(false);
        recyclerView.setVisibility(View.VISIBLE);
        noReposView.setVisibility(View.GONE);
        this.repos.addAll(repos);
        if (repoListAdapter == null) {
            canShowData();
        } else {
            repoListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(Throwable e) {
        new MaterialDialog.Builder(this)
                .title(FormatUtils.titleForException(e))
                .content(FormatUtils.contentForException(e))
                .positiveText(R.string.okay)
                .show();
        if(loadToast!=null)
            loadToast.error();
    }

    @Override
    public void showNoReposForUser() {
        recyclerView.setVisibility(View.GONE);
        noReposView.setVisibility(View.VISIBLE);
    }

    @Override
    public void clearList() {
        repos.clear();
        if(repoListAdapter!=null)
            repoListAdapter.notifyDataSetChanged();
    }

    @Override
    public void setLoadingState(boolean isLoading, Boolean success) {
        swipeRefreshLayout.setRefreshing(isLoading);
        if (isLoading) {
            loadToast = new LoadToast(this).setText(getString(R.string.loading)).setTranslationY(getTranslationYForToast()).show();
        } else if (loadToast != null && success != null) {
            if (success)
                loadToast.success();
            else
                loadToast.error();
            loadToast=null;
        }

    }

    @Override
    public boolean canShowData() {
        Log.e(TAG,"in canShowData, repos.size is "+repos.size()+" user is "+user);
        if(repos.size()<1 || user == null) return false;
        showUserInfo(user);
        if(repoListAdapter==null)
        {
            repoListAdapter = new RepoListAdapter(this.repos);
            recyclerView.setAdapter(repoListAdapter);
            attacher = Mugen.with(recyclerView, this);
            attacher.loadMoreOffset(Constants.LOAD_MORE_OFFSET);
            attacher.start();

        }
        return true;
    }

    private int getTranslationYForToast() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int ht = displaymetrics.heightPixels;
        int spaceToLeave = getResources().getDimensionPixelSize(R.dimen.load_toast_total_margin);
        return ht-spaceToLeave;
    }

}
