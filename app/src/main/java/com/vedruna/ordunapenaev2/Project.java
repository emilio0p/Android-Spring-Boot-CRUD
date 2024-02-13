package com.vedruna.ordunapenaev2;

/**
 * Clase que representa un proyecto.
 */
public class Project {
    // Atributos de la clase Project
    private int id;
    private String nombre;
    private String descripcion;
    private String imagen;

    /**
     * Constructor de la clase Project.
     * @param id El ID del proyecto.
     * @param nombre El nombre del proyecto.
     * @param descripcion La descripción del proyecto.
     * @param imagen La URL de la imagen del proyecto.
     */
    public Project(int id, String nombre, String descripcion, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    /**
     * Método para obtener el ID del proyecto.
     * @return El ID del proyecto.
     */
    public int getId() {
        return id;
    }

    /**
     * Método para obtener el nombre del proyecto.
     * @return El nombre del proyecto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método para obtener la descripción del proyecto.
     * @return La descripción del proyecto.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Método para obtener la URL de la imagen del proyecto.
     * @return La URL de la imagen del proyecto.
     */
    public String getImagen() {
        return imagen;
    }
}
