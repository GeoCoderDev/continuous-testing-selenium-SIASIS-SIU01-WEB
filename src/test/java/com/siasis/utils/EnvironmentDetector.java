package com.siasis.utils;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Utilidad para detectar el entorno actual desde el archivo .env
 * Usado por Maven para determinar dónde copiar los reportes
 */
public class EnvironmentDetector {

    public static void main(String[] args) {
        String environment = detectEnvironment();
        System.out.println(environment);
    }

    /**
     * Detecta el entorno actual usando la misma lógica que ConfigManager
     */
    public static String detectEnvironment() {
        try {
            // Cargar archivo .env
            Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

            // Obtener la variable ENTORNO (buscar en sistema primero, luego en .env)
            String entorno = getEnvironmentVariable("ENTORNO", dotenv, "D");

            // Mapear D -> dev, C -> cert
            return mapEnvironment(entorno.toUpperCase());

        } catch (Exception e) {
            System.err.println("Error al detectar entorno: " + e.getMessage());
            return "dev"; // Por defecto
        }
    }

    /**
     * Obtiene una variable de entorno con fallback a .env
     */
    private static String getEnvironmentVariable(String varName, Dotenv dotenv, String defaultValue) {
        // Primero buscar en variables de entorno del sistema
        String value = System.getenv(varName);

        if (value != null && !value.trim().isEmpty()) {
            return value;
        }

        // Si no está en el sistema, buscar en archivo .env
        if (dotenv != null) {
            value = dotenv.get(varName);

            if (value != null && !value.trim().isEmpty()) {
                return value;
            }
        }

        // Usar valor por defecto
        return defaultValue;
    }

    /**
     * Mapea las variables de entorno a nombres de carpetas
     */
    private static String mapEnvironment(String env) {
        switch (env) {
            case "D":
                return "dev";
            case "C":
                return "cert";
            default:
                return "dev";
        }
    }

    /**
     * Método de utilidad para debugging
     */
    public static void printEnvironmentInfo() {
        try {
            Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

            String entornoFromSystem = System.getenv("ENTORNO");
            String entornoFromEnv = dotenv != null ? dotenv.get("ENTORNO") : null;
            String detectedEnvironment = detectEnvironment();

            System.out.println("=== DETECCIÓN DE ENTORNO ===");
            System.out.println("Sistema: " + (entornoFromSystem != null ? entornoFromSystem : "No encontrado"));
            System.out.println(".env: " + (entornoFromEnv != null ? entornoFromEnv : "No encontrado"));
            System.out.println("Detectado: " + detectedEnvironment);
            System.out.println("============================");

        } catch (Exception e) {
            System.err.println("Error al mostrar información de entorno: " + e.getMessage());
        }
    }
}