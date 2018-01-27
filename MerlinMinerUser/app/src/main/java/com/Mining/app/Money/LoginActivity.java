package com.Mining.app.Money;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Arrays;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    Button login;
    EditText username, password;
    ProgressBar progressBar;
    private String un, contra, db, ip;
    private boolean isSuccess = false;
    private Connection con;
    private String z;
    public String email, pass;
    ImageButton sendIpbtn;
    EditText editText;
    TextView recovery;
    LoginButton loginButton;
    CallbackManager callbackManager;
    ConstraintLayout loginLayout, recoverPassword;
    boolean back =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialiseElements();
        loginButton = (LoginButton) findViewById(R.id.facebook);

        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email"));
        callbackManager = CallbackManager.Factory.create();


        ip = "62.43.194.93";

        db = "database";

        un = "root";
        contra = "c4rl0s";
        isSuccess = false;

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernam = username.getText().toString();
                String passwordd = password.getText().toString();
                CheckLogin checkLogin = new CheckLogin();
                checkLogin.execute(usernam, passwordd);
            }
        });

        recovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recoverPassword.setVisibility(View.VISIBLE);
                loginLayout.setVisibility(View.INVISIBLE);
                back=true;
            }
        });

        sendIpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ip = editText.getText().toString();
                String usernam = username.getText().toString();
                String passwordd = password.getText().toString();
                CheckLogin checkLogin = new CheckLogin();
                checkLogin.execute(usernam, passwordd);
            }
        });



        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {

                String accessToken = loginResult.getAccessToken().getToken();
                Log.i("accessToken", accessToken);
                login.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.INVISIBLE);
                password.setVisibility(View.INVISIBLE);
                username.setVisibility(View.INVISIBLE);

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.i("LoginActivity", response.toString());
                        Bundle bFacebookData = getFacebookData(object);
                        facebookStart(bFacebookData);

                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email"); // Parámetros que pedimos a facebook
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                System.out.println("onCancel");
            }

            @Override
            public void onError(FacebookException exception) {
                System.out.println("onError");
                Log.v("LoginActivity", exception.getCause().toString());
            }
        });
    }


    private Bundle getFacebookData(JSONObject object) {

        try {
            Bundle bundle = new Bundle();
            String id = object.getString("id");


            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("emailed", object.getString("email"));

            return bundle;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void goMainStream() {
        Intent intent =new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
        | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    private void initialiseElements() {
        recovery = (TextView) findViewById(R.id.recovery);
        loginLayout = (ConstraintLayout) findViewById(R.id.loginLayout);
        recoverPassword = (ConstraintLayout) findViewById(R.id.recoverPassword);
        login = (Button) findViewById(R.id.login);
        username = (EditText) findViewById(R.id.emailInput);
        password = (EditText) findViewById(R.id.passInput);
        progressBar = (ProgressBar) findViewById(R.id.bar);
        sendIpbtn = (ImageButton) findViewById(R.id.sendIPbtn);
        editText = (EditText) findViewById(R.id.editText3);
    }

    @Override
    public void onBackPressed() {
        if (back){
            loginLayout.setVisibility(View.VISIBLE);
            recoverPassword.setVisibility(View.INVISIBLE);
            back=false;
        }else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void goToFirstActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("miner", email);
        startActivity(intent);

    }

    public void facebookStart(Bundle bundle) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    public void skipToFirstActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public class CheckLogin extends AsyncTask<String, String, String> {
        private String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        private String DB_URL = "jdbc:mysql://62.43.194.93/database";
        private String USER = "root";
        private String PASS = "c4rl0s";

        //---------------DO BEFORE LOGIN---------
        @Override
        protected void onPreExecute() {
            login.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.INVISIBLE);

        }

        //---------------DO AFTER LOGIN----------
        @Override
        protected void onPostExecute(String r) {
            Toast Toasta = Toast.makeText
                    (getApplicationContext(), z,
                            Toast.LENGTH_SHORT);
            Toasta.show();
            View v = new View(LoginActivity.this);
            if (isSuccess == true) {
                try {
                    goToFirstActivity(v);
                } catch (Exception e) {
                    Toast myToast = Toast.makeText
                            (getApplicationContext(), e.getMessage(),
                                    Toast.LENGTH_LONG);
                    myToast.show();
                }
            } else {
                login.setVisibility(View.VISIBLE);
                login.setEnabled(true);
            }
            progressBar.setVisibility(View.GONE);
        }

        //---------------DO WHILE LOGIN----------
        @Override
        protected String doInBackground(String... args) {
            email = String.valueOf(username.getText());
            String passwordd = String.valueOf(password.getText());
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
                    z = "Please type your credentials to access";
                else {
                    try {
                        if (con == null) {
                            z = "Check your Internet access!";
                        } else {
                            String query = "select email,pass from tableta where email = '" + email + "' and pass = '" + passwordd + "'";
                            ResultSet rs = st.executeQuery(query);
                            if (rs.next()) {
                                z = "Connected";
                                isSuccess = true;
                            } else {
                                z = "Invalid Password";
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