package com.upt.cti.myapplication;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/*
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
*/
public class MainActivity extends AppCompatActivity {
    EditText email, pass;
    Button register, login;
    ProgressDialog progressDialog;
    static String[] mailRetinut = new String[10];
    static String[] parolaRetinuta = new String[10];

    //ConnectionClass connectionClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        register = (Button) findViewById(R.id.register);
        login = (Button) findViewById(R.id.login);


        //connectionClass = new ConnectionClass();
        mailRetinut[0] = new String("a@gmail.com");
        parolaRetinuta[0] = new String("1234");

        progressDialog = new ProgressDialog(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Doregister doregister = new Doregister();
                doregister.execute("");
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dologin dologin = new Dologin();
                dologin.execute();
            }
        });
    }

    public class Doregister extends AsyncTask<String, String, String> {

        String emailstr = email.getText().toString();
        String passstr = pass.getText().toString();
        String z = "";
        boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            if ( emailstr.trim().equals("") || passstr.trim().equals(""))
                z = "Please enter all fields....";
            else {
                for(int i = 0; i < 10; i++){
                    if(emailstr.equals(mailRetinut[i])){
                        z = "E-mail already registered.";
                        break;
                    }
                    else{
                        if(mailRetinut[i] == null && parolaRetinuta[i] == null){
                            mailRetinut[i] = new String(emailstr);
                            parolaRetinuta[i] = new String(passstr);
                            isSuccess = true;
                            z = "Register successful";
                            break;
                        }
                    }
                }
                /*try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Please check your internet connection";
                    } else {

                        String query = "insert into login_info values('" + emailstr + "','" + passstr + "')";

                        Statement stmt = con.createStatement();
                        stmt.executeUpdate(query);

                        z = "Register successfull";
                        isSuccess = true;


                        con.close();
                    }
                } catch (Exception ex) {
                    isSuccess = false;
                    z = "Exceptions" + ex;
                }
                */
            }
            return z;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getBaseContext(), "" + z, Toast.LENGTH_LONG).show();

            if (isSuccess) {
                startActivity(new Intent(MainActivity.this, MainPanel.class));

            }
            progressDialog.hide();
        }
    }

    private class Dologin extends AsyncTask<String, String, String> {
        String emailstr = email.getText().toString();
        String passstr = pass.getText().toString();
        String z = "";
        boolean isSuccess = false;

        //String em, password;

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading...");
            progressDialog.show();

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            if (emailstr.trim().equals("") || passstr.trim().equals(""))
                z = "Please enter all fields....";
            else {
                for(int i = 0; i < 10; i++) {
                    if (emailstr.equals(mailRetinut[i])){
                        if(passstr.equals(parolaRetinuta[i])){
                            isSuccess = true;
                            z = "Login successful.";
                            break;
                        }
                    }
                    else{
                        z = "Incorrect e-mail address or password";
                    }
                }

                /*try {

                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Please check your internet connection";
                    } else {

                        String query = " select * from login_info where email='" + emailstr + "' and password = '" + passstr + "'";

                        Statement stmt = con.createStatement();
                        stmt.executeUpdate(query);

                        ResultSet rs = stmt.executeQuery(query);

                        while (rs.next())

                        {
                            em = rs.getString(1);
                            password = rs.getString(2);


                            if ( em.equals(emailstr) && password.equals(passstr)) {

                                isSuccess = true;
                                z = "Login successfull";

                            } else

                                isSuccess = false;
                        }
                    }
                } catch (Exception ex) {
                    isSuccess = false;
                    z = "Exceptions" + ex;
                }
                */
            }
            return z;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getBaseContext(), "" + z, Toast.LENGTH_LONG).show();

            if (isSuccess) {

                Intent intent = new Intent(MainActivity.this, MainPanel.class);

                startActivity(intent);
            }
            progressDialog.hide();

        }
    }

/*
    private class ConnectionClass {


        @SuppressLint("NewApi")
        public Connection CONN() {
            //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            //StrictMode.setThreadPolicy(policy);
            Connection conn = null;
            //String ConnURL = null;

            try {
                    String classs = "com.mysql.jdbc.Driver";

                    String url = "jdbc:mysql://192.168.0.45:80/phpmyadmin/arduinousers";
                    String un = "user";
                    String password = "pass";
                    Class.forName(classs);
                    conn = DriverManager.getConnection(url, un, password);
                    //conn = DriverManager.getConnection(ConnURL);

            } catch (SQLException se) {
                Log.e("ERROR", se.getMessage());

                //System.out.println(se.getMessage());
            }  catch (Exception e) {
                Log.e("ERROR", e.getMessage());
                //System.out.println(e.getMessage());
            }
            return conn;
        }
    }
    */
}




