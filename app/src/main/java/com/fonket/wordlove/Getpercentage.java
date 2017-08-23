package com.fonket.wordlove;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by felip on 17-08-2017.
 */

public class Getpercentage extends AsyncTask<String, String, Match> {

    @Override
    protected Match doInBackground(String... params) {
        Requests requests = new Interceptors().commonPostInterceptor();
        Call<Match> call = requests.getPercentagebyname(params[0], params[1]);

            try {
                Response<Match> response = call.execute();
                if (200 == response.code() && response.isSuccessful()) {
                    Log.d("RESPONSE", "FUNCIONO");
                    return response.body();
                } else {
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }


        }
    }







