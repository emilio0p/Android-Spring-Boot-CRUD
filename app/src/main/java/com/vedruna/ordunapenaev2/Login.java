package com.vedruna.ordunapenaev2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Actividad para la pantalla de inicio de sesión.
 */
public class Login extends AppCompatActivity {

    // Campos de texto para el usuario y la contraseña
    public EditText txtUser;
    public EditText txtPassword;

    // Configuración para iniciar sesión con Google
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    Button googleBtn;

    /**
     * Método llamado cuando la actividad está siendo creada.
     * @param savedInstanceState El estado previamente guardado de la actividad, si lo hay.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Obtener referencias a los elementos de la interfaz de usuario
        txtUser = findViewById(R.id.txtUser);
        txtPassword = findViewById(R.id.txtPassword);
        googleBtn = findViewById(R.id.btnLoginGoogle);

        // Configuración de inicio de sesión con Google
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);

        // Asignar un listener al botón de inicio de sesión con Google
        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    /**
     * Método para iniciar sesión con Google.
     */
    private void signIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
    }

    /**
     * Método llamado cuando se completa una actividad iniciada para obtener resultados.
     * @param requestCode El código de solicitud que se pasó al método startActivityForResult().
     * @param resultCode Un código que indica el resultado de la operación.
     * @param data Un intent que puede contener los datos de resultado.
     */
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

    /**
     * Método para navegar a la segunda actividad después del inicio de sesión con Google.
     */
    private void navigateToSecondActivity() {
        finish();
        Intent intent = new Intent(Login.this, FragmentsActivity.class);
        startActivity(intent);
    }

    /**
     * Método llamado cuando se hace clic en el botón de inicio de sesión.
     * @param view La vista que ha sido clicada.
     */
    public void iniciarSesion(View view) {
        String user = txtUser.getText().toString();
        String pass = txtPassword.getText().toString();

        if (user.isEmpty() || pass.isEmpty()){
            Toast.makeText(getApplicationContext(), "Debes rellenar todos los campos!",
                    Toast.LENGTH_SHORT).show();
        } else {
            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            User u = new User(0L,user,pass);
            Call<Boolean> call = apiService.login(u);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()){
                        Boolean respuesta = response.body();
                        if (Boolean.TRUE.equals(respuesta)){
                            navigateToSecondActivity();
                        } else {
                            Toast.makeText(getApplicationContext(), "Credenciales incorrectas!",
                                    Toast.LENGTH_SHORT).show();
                            txtUser.setText("");
                            txtPassword.setText("");
                        }
                    }

                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Algo no ha salido bien...",
                            Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
