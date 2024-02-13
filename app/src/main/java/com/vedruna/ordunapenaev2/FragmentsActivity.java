package com.vedruna.ordunapenaev2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Actividad principal que gestiona los fragmentos de la aplicación.
 */
public class FragmentsActivity extends AppCompatActivity {

    /**
     * Método llamado cuando la actividad está siendo creada.
     * @param savedInstanceState El estado previamente guardado de la actividad, si lo hay.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments);

        // Obtener la referencia al componente BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Establecer el ítem seleccionado por defecto como el fragmento de inicio
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

        // Obtener el controlador de navegación del NavHostFragment
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().
                findFragmentById(R.id.nav_host_fragment);

        NavController navController = navHostFragment.getNavController();

        // Asignar un listener para la selección de ítems en el BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener(item -> {
            // Navegar al fragmento correspondiente dependiendo del ítem seleccionado
            if (item.getItemId() == R.id.navigation_home) {
                navController.navigate(R.id.homeFragment);
            } else if (item.getItemId() == R.id.navigation_create) {
                navController.navigate(R.id.addElementFragment);
            } else if (item.getItemId() == R.id.navigation_modify) {
                navController.navigate(R.id.modifyElementFragment);
            } else if (item.getItemId() == R.id.navigation_delete){
                navController.navigate(R.id.deleteElementFragment);
            } else if (item.getItemId() == (R.id.navigation_cerrar_sesion)){
                navController.navigate(R.id.signOutFragment);
            }
            return true;
        });
    }
}
