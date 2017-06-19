package com.example.msi.zpi_interfejs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void uruchom1(View v) {
        Intent myIntent = new Intent(this, Trasa.class);
        startActivity(myIntent);
    }

    public void uruchom2(View v) {
        Intent myIntent = new Intent(this, Historia.class);
        startActivity(myIntent);
    }

    public void uruchom3(View v) {
    }

    public void uruchom4(View v) {
    }
}
