package com.example.carlosroldan.merlinminerapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.TimerTask;

public class firstActivity extends AppCompatActivity {
    DataBase database;
    TextView name1, name2, name3, name4, name5, name6, name7,
            addNewEvent, actualizar, updateForce, diff, dbUser, dbPass, dbUsertoShow, dbPassToShow;
    String userEmail, userName1, userName2, userName3,
            userName4, userName5, userName6, userName7, name, key;
    int ID1, ID2, ID3, ID4, ID5, ID6, ID7;
    TextView amountOfUsers, titulo;
    ScrollView panel2, panel1;
    Switch offline, forceUpdate;
    Button button1, button2, button3, button4,
            button5, button6, button7, addEvent, addDiff, sendDiff;
    ImageButton send, update, sendEvent, manageCash, info,
            eur, gbp, usd, updateContent, viewDatabase, updateteForceInit, newUpdateInit;
    Button okButton;
    FloatingActionButton sett;
    ImageView logo;
    EditText inputDiff, inputEvent, usdInput, eurInput, gbpInput;
    boolean valid = false, readyForEur, readyForUSD, readyForGBP, back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitst);
        addElements();
        scrollButtonsAction();
        if (android.os.Build.VERSION.SDK_INT > 9) { // habilita database
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Intent intent = getIntent(); //crea intent para pasar la info del mail
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            userEmail = (String) bundle.get("email");
        }
        database = new DataBase(); //genera base de datos
        try {
            database.usersOnline(); //load de usuarios online
            database.usersOffline(); //load de usuarios offline
            database.loadCommon(); //load de valores de la base de datos common
            database.checkMerlinAvailability(); //comprueba si Merlin esta disponible
        } catch (SQLException e) {
            e.printStackTrace();
        }
        amountOfUsers.setText(String.valueOf(database.getAmountofNames())); //recibe la cantidad de usuarios conectados online
        getUsers();//recibe la cantidad de usuarios de un maximo de 7
        linkNamesToTextViews(); //fija las nombres de los usuarios a los labels
        setOnlineNames(); //fija los los labels al FX layout
        buttonAction(); //Action Listener de los principales botones
        restructure(); //restructura los name label de los usuarios que no estan conectados
        //------------------------------ACTION LISTENERS--------------------------------------
        settingSetUp(); //configuracion visual de ajustes
        homeSetUp(); //configuracion visual de inicio
        forceUpdateAction(); //Action Listener de forzar actualizacion de Merlin Miner
        diffAction(); //Action Listener sobre ajuste de dificultad
        addEventAction(); //Action Listener sobre a;adir un evento nuevo
        sendEventAction(); //Action Listener sobre enviar el evento a;adido
        manageCashAction(); //Action Listeners sobre las opciones de ajuste al cambiar el valor de DGB en EUR, GBP, USD
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeSetUp();
            }
        }); //si tocan al logo van al inicio
        OfflineSwitchAction();//Action Listener sobre el switch de Offline a Online

        java.util.Timer taskTimer = new java.util.Timer(true); //cada 5 segundos realiza
        TimerTask task = new MyTimerTask(database, this); //busqueda de notificaciones
        taskTimer.scheduleAtFixedRate(task, 5000, 3000);
    }

    private void forceUpdateAction() {
        if (database.isMerlinAvailable()){
            forceUpdate.setChecked(true);
        }
        forceUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!forceUpdate.isChecked()) {
                    database.setMerlinAvailable(false);
                    try {
                        database.updateMerlinAvailable();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    database.setEventAvailable(true);
                    Toast myToast = Toast.makeText
                            (getApplicationContext(), "Merlin ha sido desactivado",
                                    Toast.LENGTH_SHORT);
                    myToast.show();
                }else{
                    database.setMerlinAvailable(true);
                    try {
                        database.updateMerlinAvailable();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    database.setEventAvailable(true);
                    Toast myToast = Toast.makeText
                            (getApplicationContext(), "Merlin ha sido activado",
                                    Toast.LENGTH_SHORT);
                    myToast.show();
                }
            }

        });
    }

    private void OfflineSwitchAction() {
        offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (offline.isChecked()) {
                    titulo.setText("Usuarios Offline");
                    amountOfUsers.setText(String.valueOf(database.getAmountNamesOFF()));
                    getUsersOff();
                    setOnlineNames();
                    showItAll();
                } else if (!offline.isChecked()) {
                    titulo.setText("Usuarios Online");
                    amountOfUsers.setText(String.valueOf(database.getAmountofNames()));

                    userName1 = null;
                    userName2 = null;
                    userName3 = null;
                    userName4 = null;
                    userName5 = null;
                    userName6 = null;
                    userName7 = null;

                    getUsers();
                    setOnlineNames();
                    restructure();
                }
            }
        });
    }

    private void sendEventAction() {
        sendEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.setEvent(String.valueOf(inputEvent.getText()));
                database.setEventAvailable(true);
                try {
                    database.updateEventAvailable();
                    database.updateEvent();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Toast myToast = Toast.makeText
                        (getApplicationContext(), "Nuevo evento añadido",
                                Toast.LENGTH_SHORT);
                myToast.show();

                inputEvent.setText("");
                inputEvent.setHint("eg: Facebook Event");
                inputEvent.setVisibility(View.INVISIBLE);
                sendEvent.setVisibility(View.INVISIBLE);
                updateteForceInit.setEnabled(true);
                addDiff.setEnabled(true);
                updateteForceInit.setEnabled(true);
            }
        });
    }

    private void addEventAction() {
        inputEvent.setHint(database.getEvent());
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputEvent.getVisibility() == View.INVISIBLE) {
                    inputEvent.setVisibility(View.VISIBLE);
                    sendEvent.setVisibility(View.VISIBLE);
                    panel2.setVisibility(View.INVISIBLE);
                    titulo.setText("Añadir Evento");
                    back = true;
                } else if (inputEvent.getVisibility() == View.VISIBLE) {
                    inputEvent.setVisibility(View.INVISIBLE);
                    sendEvent.setVisibility(View.INVISIBLE);
                    logo.setVisibility(View.VISIBLE);
                    titulo.setVisibility(View.VISIBLE);
                    update.setVisibility(View.VISIBLE);
                    addEvent.setVisibility(View.VISIBLE);
                    forceUpdate.setVisibility(View.VISIBLE);
                    info.setVisibility(View.VISIBLE);
                    manageCash.setVisibility(View.VISIBLE);
                    updateContent.setVisibility(View.VISIBLE);
                    sett.setVisibility(View.VISIBLE);
                    updateForce.setVisibility(View.VISIBLE);
                    actualizar.setVisibility(View.VISIBLE);
                    diff.setVisibility(View.VISIBLE);
                    titulo.setText("Ajustes");
                    addNewEvent.setVisibility(View.VISIBLE);
                    addDiff.setVisibility(View.VISIBLE);
                    addEvent.setVisibility(View.VISIBLE);
                    panel2.setVisibility(View.VISIBLE);
                    addDiff.setVisibility(View.VISIBLE);
                    newUpdateInit.setVisibility(View.VISIBLE);
                    updateteForceInit.setVisibility(View.VISIBLE);
                }

            }
        });
        inputEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update.setVisibility(View.INVISIBLE);
                forceUpdate.setVisibility(View.INVISIBLE);
                logo.setVisibility(View.INVISIBLE);
                info.setVisibility(View.INVISIBLE);
                manageCash.setVisibility(View.INVISIBLE);
                updateContent.setVisibility(View.INVISIBLE);
                sett.setVisibility(View.INVISIBLE);
                updateForce.setVisibility(View.INVISIBLE);
                actualizar.setVisibility(View.INVISIBLE);
                diff.setVisibility(View.INVISIBLE);
                titulo.setText("Nuevo Evento");
                addNewEvent.setVisibility(View.INVISIBLE);
                addDiff.setVisibility(View.INVISIBLE);
                addEvent.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void diffAction() {
        inputDiff.setHint(String.valueOf(database.getDGBdiff()));
        addDiff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputDiff.getVisibility() == View.INVISIBLE) {
                    inputDiff.setVisibility(View.VISIBLE);
                    send.setVisibility(View.VISIBLE);
                    logo.setVisibility(View.INVISIBLE);
                    newUpdateInit.setVisibility(View.VISIBLE);
                    updateteForceInit.setVisibility(View.VISIBLE);
                    addEvent.setVisibility(View.VISIBLE);
                } else if (inputDiff.getVisibility() == View.VISIBLE) {
                    inputDiff.setVisibility(View.INVISIBLE);
                    logo.setVisibility(View.VISIBLE);
                    send.setVisibility(View.INVISIBLE);
                    titulo.setVisibility(View.VISIBLE);
                    update.setVisibility(View.VISIBLE);
                    addEvent.setVisibility(View.VISIBLE);
                    forceUpdate.setVisibility(View.VISIBLE);
                    info.setVisibility(View.VISIBLE);
                    manageCash.setVisibility(View.VISIBLE);
                    updateContent.setVisibility(View.VISIBLE);
                    sett.setVisibility(View.VISIBLE);
                    updateForce.setVisibility(View.VISIBLE);
                    addNewEvent.setVisibility(View.VISIBLE);
                    actualizar.setVisibility(View.VISIBLE);
                    logo.setVisibility(View.VISIBLE);
                    addDiff.setVisibility(View.VISIBLE);
                    titulo.setText("Ajustes");
                    newUpdateInit.setVisibility(View.VISIBLE);
                    updateteForceInit.setVisibility(View.VISIBLE);
                    addEvent.setVisibility(View.VISIBLE);
                }
            }
        });
        inputDiff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update.setVisibility(View.INVISIBLE);
                addEvent.setVisibility(View.INVISIBLE);
                forceUpdate.setVisibility(View.INVISIBLE);
                logo.setVisibility(View.INVISIBLE);
                info.setVisibility(View.INVISIBLE);
                manageCash.setVisibility(View.INVISIBLE);
                updateContent.setVisibility(View.INVISIBLE);
                sett.setVisibility(View.INVISIBLE);
                updateForce.setVisibility(View.INVISIBLE);
                addNewEvent.setVisibility(View.INVISIBLE);
                actualizar.setVisibility(View.INVISIBLE);
                addDiff.setVisibility(View.INVISIBLE);
                titulo.setText("Ajustar Dificultad");
                diff.setVisibility(View.INVISIBLE);
                newUpdateInit.setVisibility(View.INVISIBLE);
                updateteForceInit.setVisibility(View.INVISIBLE);
                back = true;
            }
        });
    }

    private void settingSetUp() {
        sett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (panel1.getVisibility() == View.VISIBLE) {
                    settingsHome();

                } else if (panel1.getVisibility() == View.INVISIBLE) {
                    homeSetUp();
                }
            }
        });
    }

    private void settingsHome() {
        titulo.setText("Ajustes");
        amountOfUsers.setVisibility(View.INVISIBLE);
        panel1.setVisibility(View.INVISIBLE);
        panel2.setVisibility(View.VISIBLE);
        forceUpdate.setVisibility(View.INVISIBLE);
        inputEvent.setVisibility(View.VISIBLE);
        sendEvent.setVisibility(View.VISIBLE);
        info.setVisibility(View.VISIBLE);
        manageCash.setVisibility(View.VISIBLE);
        update.setVisibility(View.INVISIBLE);
        inputEvent.setVisibility(View.INVISIBLE);
        sendEvent.setVisibility(View.INVISIBLE);
        offline.setVisibility(View.INVISIBLE);
        inputDiff.setVisibility(View.INVISIBLE);
        updateContent.setVisibility(View.VISIBLE);
        viewDatabase.setVisibility(View.INVISIBLE);
        dbPass.setVisibility(View.INVISIBLE);
        dbPassToShow.setVisibility(View.INVISIBLE);
        dbUser.setVisibility(View.INVISIBLE);
        dbUsertoShow.setVisibility(View.INVISIBLE);
        actualizar.setVisibility(View.VISIBLE);
        newUpdateInit.setVisibility(View.VISIBLE);
        updateteForceInit.setVisibility(View.VISIBLE);
        updateteForceInit.setEnabled(true);
        newUpdateInit.setEnabled(true);
        addDiff.setEnabled(true);
        addEvent.setEnabled(true);
        logo.setVisibility(View.VISIBLE);
        send.setVisibility(View.INVISIBLE);
    }

    private void settingsVerBaseDatos() {
        titulo.setText("Base de Datos");
        dbPassToShow.setTextIsSelectable(true);
        dbUsertoShow.setTextIsSelectable(true);
        okButton.setVisibility(View.VISIBLE);
        amountOfUsers.setVisibility(View.GONE);
        panel2.setVisibility(View.INVISIBLE);
        dbPass.setVisibility(View.VISIBLE);
        dbPassToShow.setVisibility(View.VISIBLE);
        dbUser.setVisibility(View.VISIBLE);
        dbUsertoShow.setVisibility(View.VISIBLE);
        viewDatabase.setVisibility(View.INVISIBLE);
        eurInput.setVisibility(View.INVISIBLE);
        gbpInput.setVisibility(View.INVISIBLE);
        usdInput.setVisibility(View.INVISIBLE);
        eur.setVisibility(View.INVISIBLE);
        usd.setVisibility(View.INVISIBLE);
        gbp.setVisibility(View.INVISIBLE);


    }

    private void homeSetUp() {
        titulo.setText("Usuarios Online");
        titulo.setVisibility(View.VISIBLE);
        amountOfUsers.setVisibility(View.VISIBLE);
        panel1.setVisibility(View.VISIBLE);
        panel2.setVisibility(View.INVISIBLE);
        sendEvent.setVisibility(View.INVISIBLE);
        inputDiff.setVisibility(View.INVISIBLE);
        update.setVisibility(View.INVISIBLE);
        inputEvent.setVisibility(View.INVISIBLE);
        sendEvent.setVisibility(View.INVISIBLE);
        offline.setVisibility(View.VISIBLE);
        info.setVisibility(View.INVISIBLE);
        manageCash.setVisibility(View.INVISIBLE);
        gbp.setVisibility(View.INVISIBLE);
        eur.setVisibility(View.INVISIBLE);
        usd.setVisibility(View.INVISIBLE);
        eurInput.setVisibility(View.GONE);
        gbpInput.setVisibility(View.GONE);
        usdInput.setVisibility(View.GONE);
        updateContent.setVisibility(View.INVISIBLE);
        send.setVisibility(View.INVISIBLE);
        dbPass.setVisibility(View.INVISIBLE);
        dbPassToShow.setVisibility(View.INVISIBLE);
        dbUser.setVisibility(View.INVISIBLE);
        dbUsertoShow.setVisibility(View.INVISIBLE);
        viewDatabase.setVisibility(View.INVISIBLE);
        okButton.setVisibility(View.INVISIBLE);
    }

    private void addElements() {
        panel2 = (ScrollView) findViewById(R.id.panel2);
        panel1 = (ScrollView) findViewById(R.id.scrollView2);
        button1 = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button3);
        button3 = (Button) findViewById(R.id.button4);
        button4 = (Button) findViewById(R.id.button2);
        button5 = (Button) findViewById(R.id.button6);
        button6 = (Button) findViewById(R.id.button5);
        button7 = (Button) findViewById(R.id.button9);
        send = (ImageButton) findViewById(R.id.sendDGB);
        update = (ImageButton) findViewById(R.id.updateButton);
        addEvent = (Button) findViewById(R.id.addEvent);
        addDiff = (Button) findViewById(R.id.addDifficulty);
        forceUpdate = (Switch) findViewById(R.id.forceUpdate);
        sendDiff = (Button) findViewById(R.id.sendDiff);
        sendEvent = (ImageButton) findViewById(R.id.sendEvent);
        logo = (ImageView) findViewById(R.id.logo);
        inputDiff = (EditText) findViewById(R.id.inputDiff);
        inputEvent = (EditText) findViewById(R.id.inputEventText);
        offline = (Switch) findViewById(R.id.switch4);
        titulo = (TextView) findViewById(R.id.textView13);
        amountOfUsers = (TextView) findViewById(R.id.textView);
        manageCash = (ImageButton) findViewById(R.id.manageCash);
        info = (ImageButton) findViewById(R.id.info);
        usdInput = (EditText) findViewById(R.id.usdInput);
        eurInput = (EditText) findViewById(R.id.eurInput);
        gbpInput = (EditText) findViewById(R.id.gbpInput);
        eur = (ImageButton) findViewById(R.id.eur);
        gbp = (ImageButton) findViewById(R.id.gbp);
        usd = (ImageButton) findViewById(R.id.usd);
        updateContent = (ImageButton) findViewById(R.id.updateContent);
        diff = (TextView) findViewById(R.id.diff);
        actualizar = (TextView) findViewById(R.id.actualizar);
        addNewEvent = (TextView) findViewById(R.id.addnewevent);
        updateForce = (TextView) findViewById(R.id.updatetoforce);
        sett = (FloatingActionButton) findViewById(R.id.ajustes);
        viewDatabase = (ImageButton) findViewById(R.id.viewDatabase);
        dbPassToShow = (TextView) findViewById(R.id.textView3);
        dbPass = (TextView) findViewById(R.id.textView8);
        dbUsertoShow = (TextView) findViewById(R.id.textView6);
        dbUser = (TextView) findViewById(R.id.textView5);
        okButton = (Button) findViewById(R.id.button7);
        newUpdateInit = (ImageButton) findViewById(R.id.updateInit);
        updateteForceInit = (ImageButton) findViewById(R.id.forceUpdateToInit);

    }

    private void manageCashAction() {
        eurInput.setHint(database.getDGBpriceEUR());
        gbpInput.setHint(database.getDGBpriceGBP());
        usdInput.setHint(database.getDGBpriceUSD());
        manageCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                okButton.setVisibility(View.INVISIBLE);
                amountOfUsers.setVisibility(View.GONE);
                panel2.setVisibility(View.INVISIBLE);
                dbPass.setVisibility(View.INVISIBLE);
                dbPassToShow.setVisibility(View.INVISIBLE);
                dbUser.setVisibility(View.INVISIBLE);
                dbUsertoShow.setVisibility(View.INVISIBLE);
                viewDatabase.setVisibility(View.INVISIBLE);
                if (eur.getVisibility() == View.INVISIBLE) {
                    titulo.setText("Ajustar Moneda");
                    eur.setVisibility(View.VISIBLE);
                    gbp.setVisibility(View.VISIBLE);
                    usd.setVisibility(View.VISIBLE);
                    inputDiff.setVisibility(View.GONE);
                    sendDiff.setVisibility(View.GONE);
                    panel2.setVisibility(View.GONE);
                    offline.setVisibility(View.GONE);
                    amountOfUsers.setVisibility(View.GONE);
                } else {
                    titulo.setText("Ajustes");
                    eur.setVisibility(View.INVISIBLE);
                    gbp.setVisibility(View.INVISIBLE);
                    usd.setVisibility(View.INVISIBLE);
                    inputDiff.setVisibility(View.VISIBLE);
                    sendDiff.setVisibility(View.VISIBLE);
                    panel2.setVisibility(View.VISIBLE);
                    offline.setVisibility(View.VISIBLE);
                    amountOfUsers.setVisibility(View.VISIBLE);
                }
            }
        });

        eur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (eurInput.getVisibility() != View.VISIBLE) {
                    eurInput.setVisibility(View.VISIBLE);
                    readyForEur = true;
                    usd.setEnabled(false);
                    gbp.setEnabled(false);
                    send.setVisibility(View.VISIBLE);
                    logo.setVisibility(View.INVISIBLE);
                } else {
                    eurInput.setVisibility(View.GONE);
                    readyForEur = false;
                    usd.setEnabled(true);
                    gbp.setEnabled(true);
                    send.setVisibility(View.INVISIBLE);
                    logo.setVisibility(View.VISIBLE);

                }
            }
        });

        gbp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gbpInput.getVisibility() != View.VISIBLE) {
                    gbpInput.setVisibility(View.VISIBLE);
                    readyForGBP = true;
                    usd.setEnabled(false);
                    eur.setEnabled(false);
                    send.setVisibility(View.VISIBLE);
                    logo.setVisibility(View.INVISIBLE);

                } else {
                    gbpInput.setVisibility(View.GONE);
                    readyForGBP = false;
                    usd.setEnabled(true);
                    eur.setEnabled(true);
                    send.setVisibility(View.INVISIBLE);
                    logo.setVisibility(View.VISIBLE);

                }
            }
        });

        usd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (usdInput.getVisibility() != View.VISIBLE) {
                    usdInput.setVisibility(View.VISIBLE);
                    readyForUSD = true;
                    eur.setEnabled(false);
                    gbp.setEnabled(false);
                    send.setVisibility(View.VISIBLE);
                    logo.setVisibility(View.INVISIBLE);

                } else {
                    usdInput.setVisibility(View.GONE);
                    readyForUSD = false;
                    eur.setEnabled(true);
                    gbp.setEnabled(true);
                    send.setVisibility(View.INVISIBLE);
                    logo.setVisibility(View.VISIBLE);

                }
            }
        });

    }

    private void scrollButtonsAction() {
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                move(view, userName1, "name1", "id1", ID1);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                move(view, userName2, "name2", "id2", ID2);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                move(view, userName3, "name3", "id3", ID3);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                move(view, userName4, "name4", "id4", ID4);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                move(view, userName5, "name5", "id5", ID5);
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                move(view, userName6, "name6", "id6", ID6);
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                move(view, userName7, "name7", "id7", ID7);
            }
        });
    }

    private void buttonAction() {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputDiff.getVisibility() == View.VISIBLE) {
                    valid = true;
                    if (valid) {
                        database.setDGBdiff(Double.valueOf(String.valueOf(inputDiff.getText())));
                        try {
                            database.updateDGBdiff();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        Toast Toastx = Toast.makeText
                                (getApplicationContext(), "Dificultad cambiada",
                                        Toast.LENGTH_SHORT);
                        Toastx.show();
                        inputDiff.setText("");
                        inputDiff.setHint(String.valueOf(database.getDGBdiff()));
                        inputDiff.setVisibility(View.INVISIBLE);
                        logo.setVisibility(View.VISIBLE);
                        titulo.setVisibility(View.VISIBLE);
                        update.setVisibility(View.VISIBLE);
                        addEvent.setVisibility(View.VISIBLE);
                        forceUpdate.setVisibility(View.VISIBLE);
                        info.setVisibility(View.VISIBLE);
                        manageCash.setVisibility(View.VISIBLE);
                        updateContent.setVisibility(View.VISIBLE);
                        sett.setVisibility(View.VISIBLE);
                        updateForce.setVisibility(View.VISIBLE);
                        addNewEvent.setVisibility(View.VISIBLE);
                        actualizar.setVisibility(View.VISIBLE);
                        send.setVisibility(View.INVISIBLE);
                        settingsHome();
                        update.setVisibility(View.VISIBLE);
                        newUpdateInit.setVisibility(View.VISIBLE);
                        updateteForceInit.setVisibility(View.VISIBLE);
                        addDiff.setVisibility(View.VISIBLE);
                        diff.setVisibility(View.VISIBLE);

                    }
                    valid = false;
                }

                if (readyForEur == true) {
                    database.setDGBpriceEUR(eurInput.getText().toString());
                    try {
                        database.updateDGBpriceEUR();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    Toast myToast = Toast.makeText
                            (getApplicationContext(), "Precio de DGB actualizado en Euros",
                                    Toast.LENGTH_SHORT);
                    myToast.show();
                    readyForEur = false;
                    eurInput.setVisibility(View.INVISIBLE);
                    gbp.setEnabled(true);
                    usd.setEnabled(true);
                    eurInput.setText("");
                    eurInput.setHint(database.getDGBpriceEUR());
                }
                if (readyForGBP) {
                    database.setDGBpriceGBP(gbpInput.getText().toString());
                    try {
                        database.updateDGBpriceGBP();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    Toast myToast = Toast.makeText
                            (getApplicationContext(), "Precio de DGB actualizado en Libras",
                                    Toast.LENGTH_SHORT);
                    myToast.show();
                    readyForGBP = false;
                    gbpInput.setVisibility(View.INVISIBLE);
                    eur.setEnabled(true);
                    usd.setEnabled(true);
                    gbpInput.setText("");
                    gbpInput.setHint(database.getDGBpriceGBP());
                }
                if (readyForUSD) {
                    database.setDGBpriceUSD(usdInput.getText().toString());
                    try {
                        database.updateDGBpriceUSD();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    Toast myToast = Toast.makeText
                            (getApplicationContext(), "Precio de DGB actualizado en Dólares",
                                    Toast.LENGTH_SHORT);
                    myToast.show();
                    readyForUSD = false;
                    usdInput.setVisibility(View.INVISIBLE);
                    gbp.setEnabled(true);
                    eur.setEnabled(true);
                    usdInput.setText("");
                    usdInput.setHint(database.getDGBpriceUSD());
                }
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.setAnyUpdate(true);
                database.updateAnyUpdate();
                Toast myToast = Toast.makeText
                        (getApplicationContext(), "Nueva Actualización añadida",
                                Toast.LENGTH_SHORT);
                myToast.show();
                logo.setVisibility(View.VISIBLE);
                send.setVisibility(View.INVISIBLE);
                updateteForceInit.setVisibility(View.VISIBLE);
                addDiff.setVisibility(View.VISIBLE);
                addEvent.setVisibility(View.VISIBLE);
                inputDiff.setVisibility(View.INVISIBLE);

            }
        });

        updateContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUsers();
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewDatabase.getVisibility() != View.VISIBLE ||
                        okButton.getVisibility() != View.VISIBLE) {
                    settingsVerBaseDatos();
                } else {
                    titulo.setText("Ajustes");
                    amountOfUsers.setVisibility(View.VISIBLE);
                    panel2.setVisibility(View.VISIBLE);
                    dbPass.setVisibility(View.INVISIBLE);
                    dbPassToShow.setVisibility(View.INVISIBLE);
                    dbUser.setVisibility(View.INVISIBLE);
                    dbUsertoShow.setVisibility(View.INVISIBLE);
                    okButton.setVisibility(View.INVISIBLE);
                    viewDatabase.setVisibility(View.INVISIBLE);

                }
            }
        });

        viewDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataBaseInfo(view);
            }
        });
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewDatabase.setVisibility(View.VISIBLE);
                okButton.setVisibility(View.INVISIBLE);
            }
        });

        updateteForceInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateteForceInit.setVisibility(View.INVISIBLE);
                forceUpdate.setVisibility(View.VISIBLE);
                newUpdateInit.setVisibility(View.VISIBLE);
                addDiff.setVisibility(View.VISIBLE);
                addEvent.setVisibility(View.VISIBLE);
                inputDiff.setVisibility(View.INVISIBLE);
                logo.setVisibility(View.VISIBLE);
                send.setVisibility(View.INVISIBLE);
            }
        });

        newUpdateInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newUpdateInit.setVisibility(View.INVISIBLE);
                update.setVisibility(View.VISIBLE);
                updateteForceInit.setVisibility(View.VISIBLE);
                addDiff.setVisibility(View.VISIBLE);
                addEvent.setVisibility(View.VISIBLE);
                inputDiff.setVisibility(View.INVISIBLE);
                logo.setVisibility(View.VISIBLE);
                send.setVisibility(View.INVISIBLE);
            }
        });

        updateForce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void updateUsers() {
        try {
            name1.setText("");
            name2.setText("");
            name3.setText("");
            name4.setText("");
            name5.setText("");
            name6.setText("");
            name7.setText("");

            database.removeListId();
            database.removeListIdOff();
            database.removeListNames();
            database.removeListNamesOff();

            database.loadCommon();
            database.usersOnline();
            database.usersOffline();
            homeSetUp();
            setOnlineNames();
            restructure();

            Toast myToast = Toast.makeText
                    (getApplicationContext(), "Actualizado",
                            Toast.LENGTH_SHORT);
            myToast.show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void move(View view, String name, String key, String id, int ID) {
        Intent intent = new Intent(this, sendMessage.class);
        intent.putExtra(key, name);
        intent.putExtra(id, ID);
        startActivity(intent);
    }

    private void showItAll() {
        name1.setVisibility(View.VISIBLE);
        button1.setVisibility(View.VISIBLE);

        name2.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);

        name3.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);

        name4.setVisibility(View.VISIBLE);
        button4.setVisibility(View.VISIBLE);

        name5.setVisibility(View.VISIBLE);
        button5.setVisibility(View.VISIBLE);

        name6.setVisibility(View.VISIBLE);
        button6.setVisibility(View.VISIBLE);

        name7.setVisibility(View.VISIBLE);
        button7.setVisibility(View.VISIBLE);

    }

    private void restructure() {

        if (name1.getText().equals("")) {
            name1.setVisibility(View.INVISIBLE);
            button1.setVisibility(View.INVISIBLE);
        }
        if (name2.getText().equals("")) {
            name2.setVisibility(View.INVISIBLE);
            button2.setVisibility(View.INVISIBLE);
        }
        if (name3.getText().equals("")) {
            name3.setVisibility(View.INVISIBLE);
            button3.setVisibility(View.INVISIBLE);
        }
        if (name4.getText().equals("")) {
            name4.setVisibility(View.INVISIBLE);
            button4.setVisibility(View.INVISIBLE);
        }
        if (name5.getText().equals("")) {
            name5.setVisibility(View.INVISIBLE);
            button5.setVisibility(View.INVISIBLE);
        }
        if (name6.getText().equals("")) {
            name6.setVisibility(View.INVISIBLE);
            button6.setVisibility(View.INVISIBLE);
        }
        if (name7.getText().equals("")) {
            name7.setVisibility(View.INVISIBLE);
            button7.setVisibility(View.INVISIBLE);
        }
    }

    private void setOnlineNames() {
        name1.setText(userName1);
        name2.setText(userName2);
        name3.setText(userName3);
        name4.setText(userName4);
        name5.setText(userName5);
        name6.setText(userName6);
        name7.setText(userName7);
    }

    private void linkNamesToTextViews() {
        name1 = (TextView) findViewById(R.id.name1);
        name2 = (TextView) findViewById(R.id.name2);
        name3 = (TextView) findViewById(R.id.name3);
        name4 = (TextView) findViewById(R.id.name4);
        name5 = (TextView) findViewById(R.id.name5);
        name6 = (TextView) findViewById(R.id.name6);
        name7 = (TextView) findViewById(R.id.name7);
    }

    public void dataBaseInfo(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://79.145.45.185/" +
                "phpmyadmin/sql.php?db=database&table=tableta&printview=1&sql_query=SELECT+%2A" +
                "+FROM+%60tableta%60+ORDER+BY+%60language%60+DESC++&token=c4f08cd3547e97fd" +
                "41ca8ef51ef9ff14"));
        startActivity(browserIntent);
    }

    public void getUsers() {
        int count = 0;
        for (String name : database.getListNames()) {
            count++;
            if (name.equals("") || name.equals(null)) {
                name = "Sin Nombre";
            }
            if (count == 1) {
                userName1 = name;
            }
            if (count == 2) {
                userName2 = name;
            }
            if (count == 3) {
                userName3 = name;
            }
            if (count == 4) {
                userName4 = name;
            }
            if (count == 5) {
                userName5 = name;
            }
            if (count == 6) {
                userName6 = name;
            }
            if (count == 7) {
                userName7 = name;
            }
        }
        count = 0;
        for (int id : database.getListID()) {
            count++;
            if (count == 1) {
                ID1 = id;
            }
            if (count == 2) {
                ID2 = id;
            }
            if (count == 3) {
                ID3 = id;
            }
            if (count == 4) {
                ID4 = id;
            }
            if (count == 5) {
                ID5 = id;
            }
            if (count == 6) {
                ID6 = id;
            }
            if (count == 7) {
                ID7 = id;
            }
        }
    }

    public void getUsersOff() {
        int count = 0;
        for (String name : database.getListNamesOff()) {
            count++;
            if (name.equals("") || name.equals(null)) {
                name = "Sin Nombre";
            }
            if (count == 1) {
                userName1 = name;
            }
            if (count == 2) {
                userName2 = name;
            }
            if (count == 3) {
                userName3 = name;
            }
            if (count == 4) {
                userName4 = name;
            }
            if (count == 5) {
                userName5 = name;
            }
            if (count == 6) {
                userName6 = name;
            }
            if (count == 7) {
                userName7 = name;
            }
        }
        count = 0;
        for (int id : database.getListOff()) {
            count++;
            if (count == 1) {
                ID1 = id;
            }
            if (count == 2) {
                ID2 = id;
            }
            if (count == 3) {
                ID3 = id;
            }
            if (count == 4) {
                ID4 = id;
            }
            if (count == 5) {
                ID5 = id;
            }
            if (count == 6) {
                ID6 = id;
            }
            if (count == 7) {
                ID7 = id;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (back) {
            inputDiff.setHint(String.valueOf(database.getDGBdiff()));
            inputDiff.setVisibility(View.INVISIBLE);
            logo.setVisibility(View.VISIBLE);
            titulo.setVisibility(View.VISIBLE);
            update.setVisibility(View.VISIBLE);
            addEvent.setVisibility(View.VISIBLE);
            forceUpdate.setVisibility(View.VISIBLE);
            info.setVisibility(View.VISIBLE);
            manageCash.setVisibility(View.VISIBLE);
            updateContent.setVisibility(View.VISIBLE);
            sett.setVisibility(View.VISIBLE);
            updateForce.setVisibility(View.VISIBLE);
            addNewEvent.setVisibility(View.VISIBLE);
            actualizar.setVisibility(View.VISIBLE);
            send.setVisibility(View.INVISIBLE);
            logo.setVisibility(View.VISIBLE);
            addDiff.setVisibility(View.VISIBLE);
            diff.setVisibility(View.VISIBLE);
            settingsHome();
            back = false;
        }
        if (viewDatabase.getVisibility() == View.VISIBLE) {
            viewDatabase.setVisibility(View.INVISIBLE);
        }
    }
}