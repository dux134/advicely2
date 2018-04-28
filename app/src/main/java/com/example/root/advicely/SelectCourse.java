package com.example.root.advicely;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.root.advicely.utils.AppVariable;

import static com.example.root.advicely.utils.AppVariable.prefManager;

public class SelectCourse extends AppCompatActivity {

    private Button bsc;
    private Button bcom;
    private Button ba;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_course);

        bsc = (Button)findViewById(R.id.bsc_button);
        bsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppVariable.selectCourse = "bsc";
                startActivity(new Intent(SelectCourse.this,SelectYear.class));
                finish();
            }
        });

        bcom = (Button)findViewById(R.id.bcom_button);
        bcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppVariable.selectCourse = "bcom";
                startActivity(new Intent(SelectCourse.this,SelectYear.class));
                finish();
            }
        });

        ba = (Button)findViewById(R.id.ba_button);
        ba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppVariable.selectCourse = "ba";
                startActivity(new Intent(SelectCourse.this,SelectYear.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (prefManager.isFirstTimeLaunch()) {
//            Toast.makeText(getApplicationContext(),"hi",Toast.LENGTH_SHORT).show();
            System.exit(0);
        } else {
            startActivity(new Intent(SelectCourse.this,NavigationDrawer.class));
            finish();
        }
    }
}
