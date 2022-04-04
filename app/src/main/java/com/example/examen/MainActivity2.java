package com.example.examen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    private TextView txtName, txtToken;
    private Bundle bundle;
    private SharedPreferences localStorage;
    private SharedPreferences.Editor localStorageEditor;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        txtName=findViewById(R.id.textName);
        txtToken=findViewById(R.id.textToken);
        button = findViewById(R.id.button);
        bundle = getIntent().getExtras();
        localStorage = getSharedPreferences("settings", MODE_PRIVATE);
        localStorageEditor = localStorage.edit();
        if (bundle != null) {
            txtName.setText(bundle.getString("name"));
            txtToken.setText(bundle.getString("token"));
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

    }
    private void logout(){
        localStorageEditor.putString("token", "");
        localStorageEditor.putString("login", "");
        localStorageEditor.commit();
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
    }
}