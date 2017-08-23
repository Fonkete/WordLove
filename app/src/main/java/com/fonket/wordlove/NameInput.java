package com.fonket.wordlove;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by felip on 21-08-2017.
 */

public class NameInput extends AppCompatActivity {

    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setContentView(R.layout.names_layout);
        Button saveBtn = (Button) findViewById(R.id.saveBtn);
        builder = new AlertDialog.Builder(this);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText currentEt = (EditText) findViewById(R.id.currentEt);
                EditText coupleEt = (EditText) findViewById(R.id.coupleEt);
                String fname = currentEt.getText().toString();
                String sname = coupleEt.getText().toString();
                Requests requests = new Interceptors().commonPostInterceptor();
                Call<Match> call = requests.getPercentagebyname(fname, sname);
                call.enqueue(new Callback<Match>() {
                    @Override
                    public void onResponse(Call<Match> call, Response<Match> response) {
                        Log.d("responde.code()", String.valueOf(response.code()));
                        Log.d("succesful", String.valueOf(response.isSuccessful()));
                    }
                    @Override
                    public void onFailure(Call<Match> call, Throwable t) {
                        Toast.makeText(NameInput.this, "carita triste", Toast.LENGTH_SHORT).show();
                    }
                });
                new BackgroungMatch().execute(fname, sname);
            }
        });
    }

    private class BackgroungMatch extends Getpercentage {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(NameInput.this);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @Override
        protected void onPostExecute(Match wrapper) {
            progressDialog.dismiss();
            if (wrapper != null) {
                String percentage = String.valueOf(wrapper.getPercentage());
                Log.d("RESULTADO", percentage);
                String result = String.valueOf(wrapper.getResult());
                Log.d("RESULTADO", result);
                //Mandar a la mainactivity
                builder.setMessage("Percentage: " + percentage + "%" + " Result: " + result).setTitle("Result");
                AlertDialog dialog = builder.create();
                dialog.show();
            }

        }
    }
}
