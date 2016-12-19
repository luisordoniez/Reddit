package com.reddit.luisordoniez.reddit.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.reddit.luisordoniez.reddit.R;
import com.reddit.luisordoniez.reddit.utils.Constants;

public class AcercaDe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);
    }

    public void close(View v){
        this.finish();
    }
}
