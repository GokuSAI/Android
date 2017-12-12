package com.example.saikrishna.bmi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt = (Button)findViewById(R.id.calculate);


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText wt = (EditText)findViewById(R.id.weightInput);
                Double weight = Double.parseDouble(wt.getText().toString());
                EditText ht1 = (EditText)findViewById(R.id.heightFeet);
                EditText ht2 = (EditText)findViewById(R.id.heightInches);
                Double feet = Double.parseDouble(ht1.getText().toString());
                Double inches = Double.parseDouble(ht2.getText().toString());
                Double fina = (feet * 12 ) + inches;
                Double BMI = (weight/(fina * fina)) * 703;
                TextView t1 = (TextView)findViewById(R.id.Display1);
                t1.setText(String.format("%.2f",BMI));
                TextView t2 = (TextView)findViewById(R.id.Display2);

                if(BMI <= 18.5){
                    t2.setText(" You are underweight");
                }
                else if ( BMI > 18.5 && BMI < 24.9){
                    t2.setText("you are normal weight");

                }
                else if (BMI > 25 && BMI < 29.9){
                    t2.setText("you are over weight");

                }
                else if ( BMI >= 30){
                    t2.setText("you are obese");
                }
                Toast.makeText(MainActivity.this, " BMI Calculated", Toast.LENGTH_SHORT).show();

            }
        });



    }

}
