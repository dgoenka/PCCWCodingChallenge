package com.divyanshgoenka.pccwcodingchallenge.retrofit;

import com.divyanshgoenka.pccwcodingchallenge.model.Repo;
import com.divyanshgoenka.pccwcodingchallenge.model.User;
import com.divyanshgoenka.pccwcodingchallenge.util.Constants;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by divyanshgoenka on 03/08/17.
 */

public interface GitHubApi {

    @GET("users/{user_id}")
    Observable<User>  getUser(@Path("user_id") String userId);

    @GET("users/{user_id}/repos?count="+ Constants.NO_ITEMS_PER_PAGE)
    Observable<List<Repo>>  getUserRepos(@Path("user_id") String userId);
}
