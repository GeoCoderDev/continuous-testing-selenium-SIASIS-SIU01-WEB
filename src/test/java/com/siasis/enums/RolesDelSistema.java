package com.siasis.enums;

/**
 * Enum que define todos los roles disponibles en el sistema SIASIS
 * Este enum es independiente del texto mostrado en el frontend
 */
public enum RolesDelSistema {
    DIRECTIVO("Directivo", "DIRECTIVO"),
    PROFESOR_PRIMARIA("Profesor (Primaria)", "PROFESOR_PRIMARIA"),
    AUXILIAR("Auxiliar", "AUXILIAR"),
    PROFESOR_SECUNDARIA("Profesor/Tutor (Secundaria)", "PROFESOR_SECUNDARIA"),
    TUTOR("Profesor/Tutor (Secundaria)", "TUTOR"),
    PERSONAL_ADMINISTRATIVO("Otro", "PERSONAL_ADMINISTRATIVO"),
    RESPONSABLE("Responsable (Padre/Apoderado)", "RESPONSABLE"); // Para futuro uso

    private final String textoBotonFrontend;
    private final String claveConfiguracion;

    RolesDelSistema(String textoBotonFrontend, String claveConfiguracion) {
        this.textoBotonFrontend = textoBotonFrontend;
        this.claveConfiguracion = claveConfiguracion;
    }

    /**
     * Obtiene el texto que aparece en el botón del frontend
     */
    public String getTextoBotonFrontend() {
        return textoBotonFrontend;
    }

    /**
     * Obtiene la clave usada para buscar credenciales en la configuración
     */
    public String getClaveConfiguracion() {
        return claveConfiguracion;
    }

    /**
     * Busca un rol por el texto del botón del frontend
     */
    public static RolesDelSistema fromTextoBoton(String textoBoton) {
        for (RolesDelSistema rol : values()) {
            if (rol.getTextoBotonFrontend().equals(textoBoton)) {
                return rol;
            }
        }
        throw new IllegalArgumentException("No se encontró rol para el texto del botón: " + textoBoton);
    }

    /**
     * Busca un rol por su nombre (para usar en features de Cucumber)
     */
    public static RolesDelSistema fromNombre(String nombre) {
        try {
            return valueOf(nombre.toUpperCase().replace(" ", "_"));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("No se encontró rol para el nombre: " + nombre);
        }
    }

    /**
     * Obtiene una descripción legible del rol
     */
    public String getDescripcion() {
        switch (this) {
            case DIRECTIVO:
                return "Directivo (Directora/Subdirectora)";
            case PROFESOR_PRIMARIA:
                return "Profesor de Primaria";
            case AUXILIAR:
                return "Auxiliar";
            case PROFESOR_SECUNDARIA:
                return "Profesor de Secundaria";
            case TUTOR:
                return "Tutor de Secundaria";
            case PERSONAL_ADMINISTRATIVO:
                return "Personal Administrativo";
            case RESPONSABLE:
                return "Responsable (Padre/Apoderado)";
            default:
                return name();
        }
    }

    @Override
    public String toString() {
        return getDescripcion();
    }
}