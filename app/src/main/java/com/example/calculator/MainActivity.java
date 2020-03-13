package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText result;
    private EditText newno;
    private TextView dispOp;
    private static final String TEXT="";
    private static final String o1="Operand 1";
    private Double op1 = null;
    private String pendingOp = "=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.result);
        newno = findViewById(R.id.newno);
        dispOp = findViewById(R.id.operation);

        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);
        Button buttonDot = findViewById(R.id.buttonDot);
        Button buttonNeg=findViewById(R.id.buttonNeg);
        Button buttonClr=findViewById(R.id.buttonClr);

        Button buttonEquals = findViewById(R.id.buttonEquals);
        Button buttonDivide = findViewById(R.id.buttonDivide);
        Button buttonMultiply = findViewById(R.id.buttonMultiply);
        Button buttonMinus = findViewById(R.id.buttonSubtract);
        Button buttonPlus = findViewById(R.id.buttonAdd);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String x=b.getText().toString();
                newno.append(x);
            }
        };
        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        buttonDot.setOnClickListener(listener);

        buttonNeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (newno.getText().toString().length()==0)
                        newno.setText("-");
                    else {
                        try{
                            Double doubleVal=Double.valueOf(newno.getText().toString());
                            doubleVal*=-1;
                            newno.setText(doubleVal.toString());
                        }catch (NumberFormatException e)
                        {
                            newno.setText("");
                        }
                    }
            }
        });

        buttonClr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newno.setText("");
                dispOp.setText("");
                result.setText("");
                op1=null;
                pendingOp="";
            }
        });

        View.OnClickListener opListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String op = b.getText().toString();
                String value = newno.getText().toString();
                try {
                    Double doublevalue = Double.valueOf(value);
                    performOp(doublevalue, op);
                } catch (NumberFormatException e) {
                    newno.setText("");
                }
                pendingOp = op;
                dispOp.setText(pendingOp);
            }
        };
        buttonEquals.setOnClickListener(opListener);
        buttonDivide.setOnClickListener(opListener);
        buttonMultiply.setOnClickListener(opListener);
        buttonMinus.setOnClickListener(opListener);
        buttonPlus.setOnClickListener(opListener);
    }

    private void performOp(Double value, String op) {
        if (null == op1)
            op1 = value;
        else {
            if (pendingOp.equals("="))
                pendingOp = op;
            switch (pendingOp) {
                case "=":
                    op1 = value;
                    break;
                case "/":
                    if (value == 0)
                        op1 = 0.0;
                    else
                        op1 /= value;
                    break;
                case "*":
                    op1 *= value;
                    break;
                case "+":
                    op1 += value;
                    break;
                case "-":
                    op1 -= value;
                    break;
            }
        }
        result.setText(op1.toString());
        newno.setText("");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TEXT, pendingOp);
        if(op1!=null)
            outState.putDouble(o1,op1);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        dispOp.setText(savedInstanceState.getString(TEXT));
        op1=savedInstanceState.getDouble(o1);
        pendingOp=savedInstanceState.getString(TEXT);
    }
}
