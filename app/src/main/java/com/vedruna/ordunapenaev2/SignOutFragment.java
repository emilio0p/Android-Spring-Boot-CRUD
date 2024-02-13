package com.vedruna.ordunapenaev2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Fragmento para cerrar sesión.
 */
public class SignOutFragment extends Fragment {

    GoogleSignInOptions gso;

    GoogleSignInClient gsc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_out, container, false);

        Button btnYes = view.findViewById(R.id.btnYesSignOut);
        Button btnNo = view.findViewById(R.id.btnNoSignOut);

        // Configuración de las opciones de inicio de sesión de Google
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Cliente de inicio de sesión de Google
        gsc = GoogleSignIn.getClient(getContext(), gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());

        // Acción al hacer clic en "Sí" para cerrar sesión
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut(); // Cerrar sesión de Google

                // Redirigir al usuario a la pantalla de inicio de sesión
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }
        });

        // Acción al hacer clic en "No" para cancelar el cierre de sesión
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar de regreso al fragmento de inicio
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.homeFragment);
                BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
                bottomNavigationView.setSelectedItemId(R.id.navigation_home);
            }
        });

        return view;
    }

    // Método para cerrar sesión de Google
    void signOut(){
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Cierre de sesión exitoso
                    Toast.makeText(getContext(), "Se ha cerrado sesión correctamente", Toast.LENGTH_SHORT).show();
                    // Ahora puedes realizar cualquier acción adicional después del cierre de sesión
                } else {
                    // Error al cerrar sesión
                    Toast.makeText(getContext(), "No se pudo cerrar sesión. Inténtalo de nuevo", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
