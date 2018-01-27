package com.example.carlosroldan.merlinminerapp;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Bug extends AppCompatActivity {
    TextView name, mail, tiempoMinado, bugText;
    private int ID;
    private DataBase dataBase;
    private String nameString, email;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bug);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        initialiseElemnts();

        Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();
        detectBundle(bundle);

        dataBase = new DataBase();
        dataBase.getInfo(String.valueOf(ID));

        setInfo();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToHome(view);
            }
        });
    }

    public void goToHome(View view) {
        Intent intent = new Intent(this, firstActivity.class);
        startActivity(intent);
    }

    private void initialiseElemnts() {
        tiempoMinado = (TextView) findViewById(R.id.timeMining);
        name = (TextView) findViewById(R.id.nameToShow);
        mail = (TextView) findViewById(R.id.userMail);
        bugText = (TextView) findViewById(R.id.bugText);
        back = (ImageButton) findViewById(R.id.backii);
    }

    private void detectBundle(Bundle bundle) {
        if (bundle != null) {
            ID = (int) bundle.getInt("id");
            nameString = (String) bundle.getString("name");
            email = (String) bundle.getString("email");
        }
    }

    private void setInfo() {
        tiempoMinado.setText(dataBase.getTime());
        name.setText(nameString);
        mail.setText(email);
        bugText.setText(dataBase.getBug());
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, firstActivity.class);
        startActivity(intent);    }
}
