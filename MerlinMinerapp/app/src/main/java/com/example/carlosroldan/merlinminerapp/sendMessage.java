package com.example.carlosroldan.merlinminerapp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class sendMessage extends AppCompatActivity {
    private String userName1, userName2, userName3, userName4, userName5, userName6, messageContent,
            anyMessage, userName7;
    private int ID1, ID2, ID3, ID4, ID5, ID6, ID7, id;
    TextView name, pass, email, money, tiempoMinado, language, GPUname, speed, titulo, textToDelete;
    EditText inputMoney, messageInput;
    private DataBase dataBase;
    private boolean valid = false;
    ImageButton quit, add, send, back, bin, eypo;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        initialiseElements();

        final ImageButton button = (ImageButton) findViewById(R.id.imageButton);
       messageInput = (EditText) findViewById(R.id.messageInput);

        Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();

        detectBundle(bundle);
        detectUserName(bundle);
        detectID(bundle);
        setUpSettings();
        dataBase = new DataBase();
        dataBase.getInfo(String.valueOf(id));
        setInfo();
        userAction();
        sendMessageAction(button, messageInput);
    }

    private void initialiseElements() {
        name = (TextView) findViewById(R.id.textView10);
        pass = (TextView) findViewById(R.id.textView26);
        email = (TextView) findViewById(R.id.textView22);
        money = (TextView) findViewById(R.id.textView25);
        tiempoMinado = (TextView) findViewById(R.id.textView24);
        language = (TextView) findViewById(R.id.textView27);
        GPUname = (TextView) findViewById(R.id.textView23);
        speed = (TextView) findViewById(R.id.textView20);
        quit = (ImageButton) findViewById(R.id.quit);
        add = (ImageButton) findViewById(R.id.more);
        send = (ImageButton) findViewById(R.id.sendbtn);
        back = (ImageButton) findViewById(R.id.backd);
        titulo = (TextView) findViewById(R.id.titulopers);
        inputMoney = (EditText) findViewById(R.id.moneyInput);
        relativeLayout = (RelativeLayout) findViewById(R.id.relative);
        textToDelete = (TextView) findViewById(R.id.delete);
        bin = (ImageButton) findViewById(R.id.bin);
        eypo = (ImageButton) findViewById(R.id.eypo);

    }

    private void sendMessageAction(ImageButton button, final EditText editText) {
        button.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                titulo.setText("Enviar Mensaje");
                titulo.setVisibility(View.VISIBLE);
                back.setVisibility(View.VISIBLE);
                send.setVisibility(View.VISIBLE);
                relativeLayout.setVisibility(View.INVISIBLE);
                messageInput.setVisibility(View.VISIBLE);
                valid=true;
                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (valid) {
                            if (editText.getText().equals("") || editText == null) {
                                Toast myToast = Toast.makeText
                                        (getApplicationContext(), "Debes escribir algo primero para" +
                                                        "poder enviarlo",
                                                Toast.LENGTH_SHORT);
                                myToast.show();
                            } else {
                                messageContent = editText.getText().toString();
                                editText.setText("");

                                Toast myToast = Toast.makeText
                                        (getApplicationContext(), "Enviando Mensaje",
                                                Toast.LENGTH_SHORT);
                                myToast.show();
                                dataBase.sendMessage(messageContent, anyMessage, id);
                                dataBase.setReaded(true);
                                try {
                                    dataBase.updateReaded();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                Toast Toasta = Toast.makeText
                                        (getApplicationContext(), "Mensaje Enviado",
                                                Toast.LENGTH_SHORT);
                                Toasta.show();
                                setUpSettings();
                            }
                        }

                    }
                });
            }
        });

        eypo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               try {
                    dataBase.add1Euro();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Toast Toasta = Toast.makeText
                        (getApplicationContext(), "1 Euro añadido",
                                Toast.LENGTH_SHORT);
                Toasta.show();
                setUpSettings();
            }
        });
    }

    private void userAction() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputMoney.getVisibility() != View.VISIBLE) {
                    titulo.setText("Ingresar 1 Euro");
                    eypo.setVisibility(View.VISIBLE);
                    titulo.setVisibility(View.VISIBLE);
                    relativeLayout.setVisibility(View.INVISIBLE);
                    back.setVisibility(View.VISIBLE);
                    bin.setVisibility(View.INVISIBLE);
                    textToDelete.setVisibility(View.INVISIBLE);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpSettings();
            }
        });

        bin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    dataBase.deleteAccount();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Toast myToast = Toast.makeText
                        (getApplicationContext(), "Usuario Borrado",
                                Toast.LENGTH_SHORT);
                myToast.show();

                Home(view);

            }

        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titulo.setVisibility(View.VISIBLE);
                back.setVisibility(View.VISIBLE);
                textToDelete.setVisibility(View.VISIBLE);
                titulo.setText("Borrar Usuario");
                textToDelete.setText("Está usted seguro que desea borrar " +
                        "la cuenta de " + dataBase.getUser() + "?");
                bin.setVisibility(View.VISIBLE);
                relativeLayout.setVisibility(View.INVISIBLE);

            }
        });
    }

    private void setUpSettings() {
        inputMoney.setVisibility(View.INVISIBLE);
        titulo.setVisibility(View.INVISIBLE);
        relativeLayout.setVisibility(View.VISIBLE);
        send.setVisibility(View.INVISIBLE);
        back.setVisibility(View.INVISIBLE);
        bin.setVisibility(View.INVISIBLE);
        textToDelete.setVisibility(View.INVISIBLE);
        messageInput.setVisibility(View.INVISIBLE);
        eypo.setVisibility(View.INVISIBLE);
    }

    private void detectBundle(Bundle bundle) {
        if (bundle != null) {
            userName1 = (String) bundle.get("name1");
            userName2 = (String) bundle.get("name2");
            userName3 = (String) bundle.get("name3");
            userName4 = (String) bundle.get("name4");
            userName5 = (String) bundle.get("name5");
            userName6 = (String) bundle.get("name6");
            userName7 = (String) bundle.get("name7");

            ID1 = (int) bundle.getInt("id1");
            ID2 = (int) bundle.getInt("id2");
            ID3 = (int) bundle.getInt("id3");
            ID4 = (int) bundle.getInt("id4");
            ID5 = (int) bundle.getInt("id5");
            ID6 = (int) bundle.getInt("id6");
            ID7 = (int) bundle.getInt("id7");
        }

    }

    private void setInfo() {
        pass.setText(dataBase.getPass());
        email.setText(dataBase.getUserEmail());
        money.setText(dataBase.getMoney() + dataBase.getCurrency());
        tiempoMinado.setText(dataBase.getTime());
        language.setText(dataBase.getLanguage());
        GPUname.setText(dataBase.getUserGPU());
        getSpeed();
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

    private void detectUserName(Bundle bundle) {
        if (bundle.containsKey("name1")) {
            name.setText(userName1);
        }
        if (bundle.containsKey("name2")) {
            name.setText(userName2);
        }
        if (bundle.containsKey("name3")) {
            name.setText(userName3);
        }
        if (bundle.containsKey("name4")) {
            name.setText(userName4);
        }
        if (bundle.containsKey("name5")) {
            name.setText(userName5);
        }
        if (bundle.containsKey("name6")) {
            name.setText(userName6);
        }
        if (bundle.containsKey("name7")) {
            name.setText(userName7);
        }
    }

    private void detectID(Bundle bundle) {
        if (bundle.containsKey("id1")) {
            id = ID1;
        }
        if (bundle.containsKey("id2")) {
            id = ID2;
        }
        if (bundle.containsKey("id3")) {
            id = ID3;
        }
        if (bundle.containsKey("id4")) {
            id = ID4;
        }
        if (bundle.containsKey("id5")) {
            id = ID5;
        }
        if (bundle.containsKey("id6")) {
            id = ID6;
        }
        if (bundle.containsKey("id7")) {
            id = ID7;
        }
    }

    public void Home(View view) {
        Intent intent = new Intent(this, firstActivity.class);
        startActivity(intent);
    }

}
