package com.fonket.wordlove;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by felip on 12-08-2017.
 */

public interface Requests {

    @GET("getPercentage")
    Call<Match> getPercentagebyname(@Query("fname") String fname, @Query("sname") String sname);
}
