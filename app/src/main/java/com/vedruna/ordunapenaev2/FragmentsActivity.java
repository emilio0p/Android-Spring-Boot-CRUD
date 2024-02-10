package com.vedruna.ordunapenaev2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FragmentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();


        bottomNavigationView.setOnItemSelectedListener(item -> {

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