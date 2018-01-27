package com.example.carlosroldan.merlinminerapp;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends ActionBarActivity {
    private Button login;
    private EditText username, password;
    private ProgressBar progressBar;
    private String un, contra, db, ip;
    private boolean isSuccess = false;
    private Connection con;
    private String z;
    public String email;
    ImageButton button;
    ImageButton button2;
    EditText editText;
    ImageButton access;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = new ImageButton(this);
        button2 = new ImageButton(this);
        editText = new EditText(this);
        login = (Button) findViewById(R.id.sendDiff);
        username = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        button = (ImageButton) findViewById(R.id.imageButton3);
        button2 = (ImageButton) findViewById(R.id.imageButton4);
        editText = (EditText) findViewById(R.id.editText3);
        access = (ImageButton) findViewById(R.id.imageButton2);

        ip = "79.152.184.21";

        db = "database";

        un = "root";
        contra = "c4rl0s";
        isSuccess = false;

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setEnabled(false);
                String usernam = username.getText().toString();
                String passwordd = password.getText().toString();
                CheckLogin checkLogin = new CheckLogin();
                checkLogin.execute(usernam, passwordd);

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setVisibility(View.VISIBLE);
                button2.setVisibility(View.VISIBLE);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ip = editText.getText().toString();
                String usernam = username.getText().toString();
                String passwordd = password.getText().toString();
                CheckLogin checkLogin = new CheckLogin();
                checkLogin.execute(usernam, passwordd);
            }
        });

        access.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernam = "merlin@gmail.com";
                String passwordd = "merlin";
                CheckLogin checkLogin = new CheckLogin();
                checkLogin.execute(usernam, passwordd);

            }
        });
    }



    public void goToFirstActivity(View view) {
        Intent intent = new Intent(this, firstActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);

    }

    public void skip(View view){
        Intent intent = new Intent(this, firstActivity.class);
        startActivity(intent);
    }

    public class CheckLogin extends AsyncTask<String, String, String> {
        private String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        private String DB_URL = "jdbc:mysql://"+ip+"/database";
        private String USER = "root";
        private String PASS = "c4rl0s";
        //---------------DO BEFORE LOGIN---------
        @Override
        protected void onPreExecute() {

            progressBar.setVisibility(View.VISIBLE);
        }

        //---------------DO AFTER LOGIN----------
        @Override
        protected void onPostExecute(String r) {
            Toast Toasta = Toast.makeText
                    (getApplicationContext(), z,
                            Toast.LENGTH_SHORT);
            Toasta.show();
            View v = new View(MainActivity.this);
            if (isSuccess == true) {
                try {
                    goToFirstActivity(v);
                } catch (Exception e) {
                    Toast myToast = Toast.makeText
                            (getApplicationContext(), e.getMessage(),
                                    Toast.LENGTH_LONG);
                    myToast.show();
                }
            }else {
                login.setEnabled(true);
            }
            progressBar.setVisibility(View.GONE);

        }

        //---------------DO WHILE LOGIN----------
        @Override
        protected String doInBackground(String... args) {
           /* email = String.valueOf(username.getText());
            String passwordd = String.valueOf(password.getText());*/
            email = "merlin@gmail.com";
            String passwordd ="merlin";

            Statement st = null;
            try {
                Class.forName(JDBC_DRIVER);
                try {
                    con = DriverManager.getConnection(DB_URL, USER, PASS);

                    st = con.createStatement();

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                if (email.trim().equals("") && passwordd.trim().equals(""))
                    z = "Porfavor escribe tus datos para acceder";
                else {
                    try {
                        if (con == null) {
                            z = "Comprueba tu conexión a Internet!";
                        } else {
                            // Change below query according to your own database.
                            String query = "select email,pass from tableta where email = '" + email + "'and pass = '" + passwordd + "'";
                            ResultSet rs = st.executeQuery(query);
                            if (rs.next()) {
                                z = "Conectado";
                                isSuccess = true;

                            } else {
                                z = "Credenciales inválidos";
                                isSuccess = false;
                            }
                        }
                    } catch (Exception ex) {
                        isSuccess = false;
                        z = ex.getMessage();

                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return z;
        }

    }
}
