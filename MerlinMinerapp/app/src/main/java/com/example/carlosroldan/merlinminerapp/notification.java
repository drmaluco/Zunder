package com.example.carlosroldan.merlinminerapp;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class notification extends Activity {
    TextView name, address, amount, tiempoMinado, GPUname, speed, paymentType;
    private int ID;
    private DataBase dataBase;
    private String paymentTypeString, addressString, nameString, email;
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        initialiseElements();

        Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();
        detectBundle(bundle);

        dataBase = new DataBase();
        dataBase.getInfo(String.valueOf(ID));

        setInfo();

    }

    private void initialiseElements() {
        name = (TextView) findViewById(R.id.nameToShow);
        address = (TextView) findViewById(R.id.addressToShow);
        amount = (TextView) findViewById(R.id.cantidadToShow);
        tiempoMinado = (TextView) findViewById(R.id.textView33);
        GPUname = (TextView) findViewById(R.id.textView34);
        speed = (TextView) findViewById(R.id.textView35);
        paymentType = (TextView) findViewById(R.id.paymentTypeToShow);
        back = (ImageButton) findViewById(R.id.back);
    }

    private void setInfo() {
        amount.setText(dataBase.getMoney() + dataBase.getCurrency());
        tiempoMinado.setText(dataBase.getTime());
        GPUname.setText(dataBase.getUserGPU());
        name.setText(nameString);
        getSpeed();
        if (paymentTypeString.toLowerCase().equals("dgb")) {
            address.setText(addressString);
            paymentType.setText("DigiByte");

        } else {
            address.setText(email);
            paymentType.setText("PayPal");
        }
    }

    public void getSpeed() {
        if (dataBase.getUserGPU().toLowerCase().contains("amd")) {
            if (dataBase.getUserGPU().contains("580")) {
                speed.setText("30 Mh/s");
            } else if (dataBase.getUserGPU().contains("570")) {
                speed.setText("28 Mh/s");
            } else if (dataBase.getUserGPU().contains("480")) {
                speed.setText("28 Mh/s");
            } else if (dataBase.getUserGPU().contains("470")) {
                speed.setText("24 Mh/s");
            } else if (dataBase.getUserGPU().contains("R9")) {
                speed.setText("18 Mh/s");
            } else {
                speed.setText("7.7 Mh/s");
            }
        } else if ((dataBase.getUserGPU().toLowerCase().contains("nvidia"))) {
            if (dataBase.getUserGPU().contains("1080")) {
                speed.setText("79 Mh/s");
            } else if (dataBase.getUserGPU().contains("1070")) {
                speed.setText("51 Mh/s");
                ;
            } else if (dataBase.getUserGPU().contains("1060")) {
                speed.setText("32 Mh/s");
            } else if (dataBase.getUserGPU().contains("1050")) {
                speed.setText("18 Mh/s");
            } else {
                speed.setText("7.7 Mh/s");
            }
        } else {
            speed.setText("GPU no reconocida");
        }
    }

    private void detectBundle(Bundle bundle) {
        if (bundle != null) {
            ID = (int) bundle.getInt("id");
            paymentTypeString = (String) bundle.get("paymentType");
            addressString = (String) bundle.getString("address");
            nameString = (String) bundle.getString("name");
            email = (String) bundle.getString("email");
        }
    }
}
