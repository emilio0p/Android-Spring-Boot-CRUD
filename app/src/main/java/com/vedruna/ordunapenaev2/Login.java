package com.vedruna.ordunapenaev2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class Login extends AppCompatActivity {

    public EditText txtUser;
    public EditText txtPassword;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    Button googleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtUser = findViewById(R.id.txtUser);
        txtPassword = findViewById(R.id.txtPassword);

        googleBtn = findViewById(R.id.btnLoginGoogle);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);


        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                navigateToSecondActivity();
                Toast.makeText(getApplicationContext(), "Inicio de sesión exitoso",
                        Toast.LENGTH_SHORT).show();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Algo ha salido mal",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void navigateToSecondActivity() {
        finish();
        Intent intent = new Intent(Login.this, FragmentsActivity.class);
        startActivity(intent);
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