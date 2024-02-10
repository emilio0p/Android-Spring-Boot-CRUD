package com.vedruna.ordunapenaev2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    public EditText txtUser;
    public EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtUser = findViewById(R.id.txtUser);
        txtPassword = findViewById(R.id.txtPassword);
    }

    public void iniciarSesion(View view) {
        String user = txtUser.getText().toString();
        String pass = txtPassword.getText().toString();
        if (user.equals("1") && pass.equals("1")){
            Toast.makeText(getApplicationContext(), "Inicio de sesión exitoso!",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, FragmentsActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos!",
                    Toast.LENGTH_SHORT).show();
            txtUser.setText("");
            txtPassword.setText("");
        }
    }
}