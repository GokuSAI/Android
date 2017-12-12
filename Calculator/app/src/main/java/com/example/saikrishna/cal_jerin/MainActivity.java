package com.example.saikrishna.cal_jerin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;


public class MainActivity extends Activity implements View.OnClickListener {
    String number="0.0";
    String sign = "=";
    boolean dotted = false;
    boolean operatorPressed = false;
    boolean isResultSet = false;
    Double first=0.0,second,result = 0.0;
    boolean checkSign = false;
    BigDecimal finalRes;
    DecimalFormat format = new DecimalFormat("0E0");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button one = (Button)findViewById(R.id.button7);
        one.setOnClickListener(this);
        Button two = (Button)findViewById(R.id.button8);
        two.setOnClickListener(this);
        Button three = (Button)findViewById(R.id.button9);
        three.setOnClickListener(this);
        Button four = (Button)findViewById(R.id.button4);
        four.setOnClickListener(this);
        Button five = (Button)findViewById(R.id.button5);
        five.setOnClickListener(this);
        Button six = (Button)findViewById(R.id.button6);
        six.setOnClickListener(this);
        Button seven = (Button)findViewById(R.id.button);
        seven.setOnClickListener(this);
        Button eight = (Button)findViewById(R.id.button2);
        eight.setOnClickListener(this);
        Button nine = (Button)findViewById(R.id.button3);
        nine.setOnClickListener(this);
        Button zero = (Button)findViewById(R.id.button11);
        zero.setOnClickListener(this);
        Button clear = (Button)findViewById(R.id.button17);
        clear.setOnClickListener(this);
        Button dot = (Button)findViewById(R.id.button10);
        dot.setOnClickListener(this);
        Button plus = (Button)findViewById(R.id.button16);
        plus.setOnClickListener(this);
        Button sub = (Button)findViewById(R.id.button14);
        sub.setOnClickListener(this);
        Button mul = (Button)findViewById(R.id.button15);
        mul.setOnClickListener(this);
        Button div = (Button)findViewById(R.id.button13);
        div.setOnClickListener(this);
        Button equal = (Button)findViewById(R.id.button12);
        equal.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        TextView res = (TextView) findViewById(R.id.textView3);

        switch (v.getId()) {

            case R.id.button7:
                res.setText(getNumber("1"));
                break;

            case R.id.button8:
                res.setText(getNumber("2"));
                break;

            case R.id.button9:
                res.setText(getNumber("3"));
                break;

            case R.id.button4:
                res.setText(getNumber("4"));
                break;

            case R.id.button5:
                res.setText(getNumber("5"));
                break;

            case R.id.button6:
                res.setText(getNumber("6"));
                break;

            case R.id.button:
                res.setText(getNumber("7"));
                break;

            case R.id.button2:
                res.setText(getNumber("8"));
                break;

            case R.id.button3:
                res.setText(getNumber("9"));
                break;

            case R.id.button11:
                res.setText(getNumber("0"));
                break;

            case R.id.button10:
                if(!dotted){
                    if(number.equals("0.0"))
                        number="";
                    number += ".";
                    dotted = true;
                }
                res.setText(number);
                break;

            case R.id.button13:
                sign = "/";
                if(!isResultSet && !checkSign)
                    result = Double.parseDouble(number);
                if(number.equals("0.0")){
                    finalRes = new BigDecimal(result);
                    format.setMaximumFractionDigits(6);
                    if(finalRes.toString().length()>14){
                        res.setText(String.valueOf(format.format(finalRes)));
                    }
                    else
                        res.setText(String.valueOf(finalRes.toString()));
                }
                else {
                    res.setText(number);
                }
                first = result;
                checkSign = true;
                number="0.0";
                dotted = false;
                operatorPressed = true;
                break;

            case R.id.button14:
                sign = "-";
                if(!isResultSet && !checkSign)
                    result = Double.parseDouble(number);
                if(number.equals("0.0")){
                    finalRes = new BigDecimal(result);
                    format.setMaximumFractionDigits(6);
                    if(finalRes.toString().length()>14){
                        res.setText(String.valueOf(format.format(finalRes)));
                    }
                    else
                        res.setText(String.valueOf(finalRes.toString()));
                }
                else {
                    res.setText(number);

                }
                first = result;
                checkSign = true;
                number = "0.0";
                dotted = false;
                operatorPressed = true;
                break;

            case R.id.button15:
                sign = "*";
                if(!isResultSet && !checkSign)
                    result = Double.parseDouble(number);
                if(number.equals("0.0")) {
                    finalRes = new BigDecimal(result);
                    format.setMaximumFractionDigits(6);
                    if(finalRes.toString().length()>14){
                        res.setText(String.valueOf(format.format(finalRes)));
                    }
                    else
                        res.setText(String.valueOf(finalRes.toString()));
                }
                else
                    res.setText(number);
                first = result;
                checkSign = true;
                number = "0.0";
                dotted = false;
                operatorPressed = true;
                break;

            case R.id.button16:
                sign = "+";
                if(!isResultSet && !checkSign)
                    result = Double.parseDouble(number);
                if(number.equals("0.0")){
                    finalRes = new BigDecimal(result);
                    format.setMaximumFractionDigits(6);
                    if(finalRes.toString().length()>14){
                        res.setText(String.valueOf(format.format(finalRes)));
                    }
                    else
                        res.setText(String.valueOf(finalRes.toString()));
                }
                else
                    res.setText(number);
                first = result;
                checkSign = true;
                number = "0.0";
                dotted = false;
                operatorPressed = true;
                break;

            case R.id.button17:
                number = "0.0";
                result = Double.parseDouble(number);
                first = Double.parseDouble(number);
                second = Double.parseDouble(number);
                isResultSet = false;
                dotted = false;
                operatorPressed = false;
                checkSign = false;
                res.setText("0");
                break;

            case R.id.button12:
                dotted = false;
                operatorPressed = false;
                checkSign = false;
                if(number.equals("."))
                    res.setText(".");
                else
                    second = Double.parseDouble(number);
                if(sign.equals("+")){
                    result = first+second;
                    isResultSet = true;
                }
                else if(sign.equals("-")){
                    result = first-second;
                    isResultSet = true;
                }
                else if(sign.equals("/")){
                    if(number.equals("0")) {
                        res.setText(R.string.divideby0);
                        break;
                    }
                    else {
                        result = first / second;
                        isResultSet = true;
                    }
                }
                else if(sign.equals("*")){
                    result = first*second;
                    isResultSet = true;
                }
                sign = "=";
                if(number.equals("."))
                    res.setText(".");
                if(result<0.0) {
                    res.setText(result.toString());
                    number="0.0";
                }
                else if(first==0.0 && result>0.0){
                    res.setText(number);
                    result = Double.parseDouble(number);
                }
                else{
                    number = "0.0";
                }
                finalRes = new BigDecimal(result);
                format.setMaximumFractionDigits(6);
                if(finalRes.toString().length()>14){
                    res.setText(String.valueOf(format.format(finalRes)));
                }
                else
                    res.setText(String.valueOf(finalRes.toString()));

                break;

            default:
                break;
        }
    }

    //To check that the length does not exceed the given limit of 14 excluding the "."
    public boolean isLengthSatisfied(String number){
        return (number.contains(".")&&number.length()<15 || !number.contains(".")&&number.length()<14);
    }

    public String getNumber(String digit){
        if(number.equals("0.0"))
            number = "";
        if(isLengthSatisfied(number))
            number += digit;
        isResultSet = false;
        return number;
    }
}
