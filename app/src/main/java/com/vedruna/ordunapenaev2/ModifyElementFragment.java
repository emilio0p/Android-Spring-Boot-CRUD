package com.vedruna.ordunapenaev2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ModifyElementFragment extends Fragment {

     static int idPro;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // Obtener los argumentos pasados al fragmento
        Bundle args = getArguments();
        if (args != null) {
            // Acceder a los valores de los argumentos
            String argumentoNombre = args.getString("nombre_proyecto");
            String argDesc = args.getString("desc_proyecto");
            String argImg = args.getString("img_proyecto");
            EditText nombrePro = view.findViewById(R.id.txtIdProDelete);
            EditText descPro = view.findViewById(R.id.txtDescProEdit);
            EditText imgPro = view.findViewById(R.id.txtImgProEdit);
            nombrePro.setText(argumentoNombre);
            descPro.setText(argDesc);
            imgPro.setText(argImg);
            idPro = args.getInt("id_proyecto");

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_modify_element, container, false);
        EditText txtNombre = view.findViewById(R.id.txtIdProDelete);
        EditText txtDesc = view.findViewById(R.id.txtDescProEdit);
        EditText txtImg = view.findViewById(R.id.txtImgProEdit);

        Button btnEditar = view.findViewById(R.id.btnEditar);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiService apiService = ApiClient.getClient().create(ApiService.class);
                Project p = new Project(idPro, txtNombre.getText().toString(),
                        txtDesc.getText().toString(), txtImg.getText().toString());
                Call<Void> call = apiService.editPro(idPro, p);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Proyecto editado correctamente",
                                    Toast.LENGTH_SHORT).show();
                            NavController navController = Navigation.
                                    findNavController(requireActivity(), R.id.nav_host_fragment);
                            navController.navigate(R.id.homeFragment);
                            BottomNavigationView bottomNavigationView = getActivity().
                                    findViewById(R.id.bottomNavigationView);
                            bottomNavigationView.setSelectedItemId(R.id.navigation_home);
                        } else {
                            Toast.makeText(getContext(), "Error al editar el proyecto",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }


                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                    }


                });
            }

            });

                return view;
            }
        }