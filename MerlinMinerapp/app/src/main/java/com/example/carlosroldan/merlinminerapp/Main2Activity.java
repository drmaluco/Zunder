package com.example.carlosroldan.merlinminerapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    private String userName1, userName2, userName3, userName4, userName5, userName6, userName, messageContent,
            anyMessage, userName7;
    private int ID1, ID2, ID3, ID4, ID5, ID6, ID7, id;
    private TextView name;
    private DataBase dataBase;
    public EditText editText = new EditText(Main2Activity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }
}
