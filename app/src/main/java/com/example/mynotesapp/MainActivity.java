package com.example.mynotesapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.idAdd);

        // utilizo lambda para utlizar java 8
        fab.setOnClickListener(view ->
                startActivity(new Intent(this,EditorActivity.class))
                );









    }
}
