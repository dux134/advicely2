package com.example.root.advicely;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.root.advicely.utils.AppVariable;

public class SelectYear extends AppCompatActivity {

    private Button firstYear , secondYear ,thirdYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_year);

        firstYear = (Button) findViewById(R.id.first_year);
        secondYear = (Button) findViewById(R.id.second_year);
        thirdYear = (Button) findViewById(R.id.third_year);

        firstYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppVariable.selectYear = "1st";
                finalFunction();
            }
        });

        secondYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppVariable.selectYear = "2nd";
                finalFunction();
            }
        });

        thirdYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppVariable.selectYear = "3rd";
                finalFunction();
            }
        });

    }

    public void finalFunction() {

        AppVariable.prefManager.setEnrolledCourse(AppVariable.selectCourse);
        AppVariable.prefManager.setEnrolledYear(AppVariable.selectYear);
        AppVariable.completeCourse = AppVariable.selectCourse+AppVariable.selectYear.charAt(0);
        AppVariable.prefManager.setCurentlyEnrolledCourse(AppVariable.completeCourse);
        AppVariable.prefManager.setFirstTimeLaunch(false);

        startActivity(new Intent(SelectYear.this,SplashScreen.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SelectYear.this,SelectCourse.class));
        finish();
    }
}
