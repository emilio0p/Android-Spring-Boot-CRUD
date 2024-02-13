package com.vedruna.ordunapenaev2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adaptador para mostrar proyectos en un RecyclerView.
 */
public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {

    private List<Project> projectList;
    private Context context;

    /**
     * Constructor del adaptador.
     * @param projectList Lista de proyectos a mostrar.
     */
    public ProjectAdapter(List<Project> projectList) {
        this.projectList = projectList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_project, parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Project project = projectList.get(position);

        // Configuración de los elementos de la vista del proyecto
        holder.textViewIdProyecto.setText("ISPI: " + project.getId());
        holder.textViewNombre.setText(project.getNombre());
        holder.textViewDescripcion.setText(project.getDescripcion());

        // Carga de la imagen utilizando Picasso
        Picasso.get().load(project.getImagen())
                .placeholder(R.drawable.ic_delete)
                .error(R.drawable.ic_delete)
                .transform(new RoundRectTransformation(30,0))
                .into(holder.imageView);

        // Configuración de los listeners de los botones de edición y eliminación
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Project project = projectList.get(position);
                // Obtención del NavController desde el View raíz del fragmento actual
                NavController navController = Navigation.findNavController(v);
                // Creación de un Bundle para los argumentos y agregar los valores necesarios
                Bundle args = new Bundle();
                args.putInt("id_proyecto", project.getId());
                args.putString("nombre_proyecto", project.getNombre());
                args.putString("desc_proyecto", project.getDescripcion());
                args.putString("img_proyecto", project.getImagen());
                // Navegación al destino deseado junto con los argumentos
                navController.navigate(R.id.modifyElementFragment, args);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Project project = projectList.get(position);
                // Obtención del NavController desde el View raíz del fragmento actual
                NavController navController = Navigation.findNavController(v);
                // Creación de un Bundle para los argumentos y agregar los valores necesarios
                Bundle args = new Bundle();
                args.putInt("id_proyecto", project.getId());
                // Navegación al destino deseado junto con los argumentos
                navController.navigate(R.id.deleteElementFragment, args);
            }
        });
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    /**
     * Clase ViewHolder para contener los elementos de la vista de un proyecto.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewIdProyecto;
        TextView textViewNombre;
        TextView textViewDescripcion;
        ImageView imageView;
        Button btnEdit;
        Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewIdProyecto = itemView.findViewById(R.id.textViewIdProyecto);
            textViewNombre = itemView.findViewById(R.id.textViewNombre);
            textViewDescripcion = itemView.findViewById(R.id.textViewDescripcion);
            imageView = itemView.findViewById(R.id.imageView);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
