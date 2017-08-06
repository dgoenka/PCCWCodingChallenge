package com.divyanshgoenka.pccwcodingchallenge.retrofit;

import com.divyanshgoenka.pccwcodingchallenge.model.SearchResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by divyanshgoenka on 03/08/17.
 */

public interface GitHubApi {
    @GET("search/repositories")
    Observable<SearchResult> searchFor(@Query("q") String query);
}
