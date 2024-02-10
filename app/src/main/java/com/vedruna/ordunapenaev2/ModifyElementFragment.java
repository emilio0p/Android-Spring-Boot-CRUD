package com.vedruna.ordunapenaev2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class ModifyElementFragment extends Fragment {

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

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_modify_element, container, false);
    }
}