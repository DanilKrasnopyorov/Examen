package com.example.examen;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.examen.network.ApiHandler;
import com.example.examen.network.models.PolicyResponse;
import com.example.examen.network.service.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText edtEmail, edtPassword;
    private ApiService apiService = ApiHandler.getInstance().getService();
    private Button button;
    private String email;
    private String password;
    private SharedPreferences localStorage;
    private SharedPreferences.Editor localStorageEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtEmail=findViewById(R.id.TextEmail);
        edtPassword=findViewById(R.id.TextPassword);
        localStorage = getSharedPreferences("settings", MODE_PRIVATE);
        localStorageEditor = localStorage.edit();
        button=findViewById(R.id.BtnEnter);
        checkAuth();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPolicyInfo(edtEmail.getText().toString(), edtPassword.getText().toString());
            }
        });
    }

    public void loginUser(View view) {
        password = edtPassword.getText().toString();
        email = edtEmail.getText().toString();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setCancelable(true)
                .setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
        AlertDialog alertDialog;
        if(email.length() == 0) {
            alertDialogBuilder
                    .setMessage("Поле Email не должно быть пустым!");
            alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            return;
        }
        if(password.length() == 0) {
            alertDialogBuilder
                    .setMessage("Поле Пароль не должно быть пустым!");
            alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            alertDialogBuilder
                    .setMessage("Поле Email заполнено неверно!");
            alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            return;
        }
        getPolicyInfo(email, password);
    }
    private void getPolicyInfo(String email, String password) {
        AsyncTask.execute(()->{
            apiService.doLogin(email, password).enqueue(new Callback<PolicyResponse>() {
                @Override
                public void onResponse(Call<PolicyResponse> call, Response<PolicyResponse> response) {
                    if (response.isSuccessful()) {
                        localStorageEditor.putString("token", response.body().getDataResponse().getToken());
                        localStorageEditor.putString("login", email);
                        localStorageEditor.commit();
                        Intent intent = new Intent(LoginActivity.this,MainActivity2.class);
                        intent.putExtra("name", response.body().getDataResponse().getName());
                        intent.putExtra("token", response.body().getDataResponse().getToken());
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<PolicyResponse> call, Throwable t) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("Отсутствует интернет соединение")
                            .setMessage(t.getLocalizedMessage())
                            .setNegativeButton("Окей", null)
                            .create()
                            .show();
                }
            });
        });
    }
    private void checkAuth(){
        String login = localStorage.getString("login", "");
        if(!login.equals("")){
            edtEmail.setText(login);
        }
    }

}