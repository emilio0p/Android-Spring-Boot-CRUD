package com.vedruna.ordunapenaev2;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SignOutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_out, container, false);

        Button btnYes = view.findViewById(R.id.btnYesSignOut);
        Button btnNo = view.findViewById(R.id.btnNoSignOut);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "Has cerrado sesión!",
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

                // Ahora puedes utilizar el NavController como desees
                // Por ejemplo, navegar a un destino específico
                navController.navigate(R.id.homeFragment);
                BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
                bottomNavigationView.setSelectedItemId(R.id.navigation_home);


            }
        });


        return view;
    }
}