package com.vedruna.ordunapenaev2;

/**
 * Clase que representa a un usuario.
 */
public class User {
    private Long id; // Identificador único del usuario
    private String nick; // Nombre de usuario
    private String contrasena; // Contraseña del usuario

    /**
     * Constructor de la clase User.
     *
     * @param id          El identificador único del usuario.
     * @param nick        El nombre de usuario.
     * @param contrasena  La contraseña del usuario.
     */
    public User(Long id, String nick, String contrasena) {
        this.id = id;
        this.nick = nick;
        this.contrasena = contrasena;
    }

    /**
     * Obtiene el identificador único del usuario.
     *
     * @return El identificador único del usuario.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único del usuario.
     *
     * @param id El identificador único del usuario.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de usuario.
     *
     * @return El nombre de usuario.
     */
    public String getNick() {
        return nick;
    }

    /**
     * Establece el nombre de usuario.
     *
     * @param nick El nombre de usuario.
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return La contraseña del usuario.
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param contrasena La contraseña del usuario.
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
