package com.siasis.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.github.cdimascio.dotenv.Dotenv;

public class ConfigManager {
    private static ConfigManager instance;
    private final Config config;
    private final String environment;
    private final String configEnvironment;
    private final Dotenv dotenv;

    private ConfigManager() {
        // Cargar archivo .env primero
        this.dotenv = Dotenv.configure().ignoreIfMissing().load();

        // Obtener la variable de entorno ENTORNO (buscar en sistema primero, luego en .env)
        this.environment = getEnvironmentVariable("ENTORNO", "D").toUpperCase();

        // Mapear D -> dev, C -> cert
        this.configEnvironment = mapEnvironment(this.environment);

        // Cargar el archivo de configuración
        this.config = ConfigFactory.load("selenium.conf");

        System.out.println("🔧 ConfigManager inicializado:");
        System.out.println("   Entorno detectado: " + this.environment +
                " (usando config: " + this.configEnvironment + ")");
        System.out.println("   Archivo .env: " + (dotenv != null ? "✅ Cargado" : "❌ No encontrado"));
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    /**
     * Mapea las variables de entorno del sistema a las configuraciones del archivo
     */
    private String mapEnvironment(String env) {
        switch (env) {
            case "D":
                return "dev";
            case "C":
                return "cert";
            default:
                System.err.println("Entorno '" + env + "' no reconocido. Usando 'dev' por defecto.");
                return "dev";
        }
    }

    /**
     * Obtiene la URL de SIASIS según el entorno configurado
     */
    public String getSiasisUrl() {
        try {
            return config.getString("entornos." + configEnvironment + ".url_siasis");
        } catch (Exception e) {
            System.err.println("Error al obtener URL para entorno '" + configEnvironment + "': " + e.getMessage());
            // URL por defecto en caso de error
            return "https://siasis-dev.vercel.app";
        }
    }

    /**
     * Obtiene el nombre de la variable de entorno para username según el rol
     */
    private String getUsernameEnvVarName(String role) {
        try {
            String formattedRole = role.toUpperCase().replaceAll("[^A-Za-z0-9]", "_");
            String key = "entornos." + configEnvironment + ".nombres_variables_entorno_para_credenciales." +
                    formattedRole + ".username";
            return config.getString(key);
        } catch (Exception e) {
            System.err.println("Error al obtener nombre de variable de entorno para username del rol '" +
                    role + "' en entorno '" + configEnvironment + "': " + e.getMessage());
            return null;
        }
    }

    /**
     * Obtiene el nombre de la variable de entorno para password según el rol
     */
    private String getPasswordEnvVarName(String role) {
        try {
            String formattedRole = role.toUpperCase().replaceAll("[^A-Za-z0-9]", "_");
            String key = "entornos." + configEnvironment + ".nombres_variables_entorno_para_credenciales." +
                    formattedRole + ".password";
            return config.getString(key);
        } catch (Exception e) {
            System.err.println("Error al obtener nombre de variable de entorno para password del rol '" +
                    role + "' en entorno '" + configEnvironment + "': " + e.getMessage());
            return null;
        }
    }

    /**
     * Obtiene el valor de una variable, buscando primero en variables de entorno del sistema,
     * luego en archivo .env como fallback
     */
    private String getEnvironmentVariable(String varName) {
        // Primero buscar en variables de entorno del sistema
        String value = System.getenv(varName);

        if (value != null && !value.trim().isEmpty()) {
            System.out.println("🌍 Variable '" + varName + "' encontrada en variables de entorno del sistema");
            return value;
        }

        // Si no está en el sistema, buscar en archivo .env
        if (dotenv != null) {
            value = dotenv.get(varName);

            if (value != null && !value.trim().isEmpty()) {
                System.out.println("📁 Variable '" + varName + "' encontrada en archivo .env");
                return value;
            }
        }

        System.err.println("❌ Variable '" + varName + "' no encontrada ni en sistema ni en .env");
        return null;
    }

    /**
     * Obtiene el valor de una variable con valor por defecto
     */
    private String getEnvironmentVariable(String varName, String defaultValue) {
        String value = getEnvironmentVariable(varName);

        if (value != null) {
            return value;
        }

        System.out.println("🔧 Usando valor por defecto para '" + varName + "': " + defaultValue);
        return defaultValue;
    }

    /**
     * Obtiene credenciales para un rol específico según el entorno
     * Lee el nombre de la variable de entorno del .conf y luego obtiene el valor de esa variable
     */
    public String getUsername(String role) {
        String envVarName = getUsernameEnvVarName(role);
        if (envVarName == null) {
            System.err.println("No se pudo obtener el nombre de la variable de entorno para username del rol: " + role);
            return "admin"; // Valor por defecto
        }

        String username = getEnvironmentVariable(envVarName);
        if (username == null) {
            System.err.println("Variable de entorno '" + envVarName + "' no encontrada para rol: " + role);
            return "admin"; // Valor por defecto
        }

        System.out.println("👤 Username obtenido para rol '" + role + "' desde variable: " + envVarName);
        return username;
    }

    /**
     * Obtiene contraseña para un rol específico según el entorno
     * Lee el nombre de la variable de entorno del .conf y luego obtiene el valor de esa variable
     */
    public String getPassword(String role) {
        String envVarName = getPasswordEnvVarName(role);
        if (envVarName == null) {
            System.err.println("No se pudo obtener el nombre de la variable de entorno para password del rol: " + role);
            return "123456"; // Valor por defecto
        }

        String password = getEnvironmentVariable(envVarName);
        if (password == null) {
            System.err.println("Variable de entorno '" + envVarName + "' no encontrada para rol: " + role);
            return "123456"; // Valor por defecto
        }

        System.out.println("🔐 Password obtenido para rol '" + role + "' desde variable: " + envVarName);
        return password;
    }

    /**
     * Obtiene el entorno actual
     */
    public String getCurrentEnvironment() {
        return this.environment;
    }

    /**
     * Obtiene el entorno de configuración actual
     */
    public String getCurrentConfigEnvironment() {
        return this.configEnvironment;
    }

    /**
     * Método para debug - muestra toda la configuración cargada
     */
    public void printConfig() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("📋 CONFIGURACIÓN ACTUAL DEL SISTEMA");
        System.out.println("=".repeat(50));
        System.out.println("🌍 Entorno sistema: " + environment);
        System.out.println("⚙️  Entorno config: " + configEnvironment);
        System.out.println("🌐 URL SIASIS: " + getSiasisUrl());
        System.out.println("📁 Archivo .env: " + (dotenv != null ? "Cargado" : "No disponible"));

        // Mostrar fuente de la variable ENTORNO
        String entornoFromSystem = System.getenv("ENTORNO");
        String entornoFromEnv = dotenv != null ? dotenv.get("ENTORNO") : null;

        System.out.println("\n🔍 Detección de variable ENTORNO:");
        if (entornoFromSystem != null) {
            System.out.println("   🌍 Sistema: " + entornoFromSystem + " ✅ (usado)");
        } else {
            System.out.println("   🌍 Sistema: No encontrada");
        }

        if (entornoFromEnv != null) {
            System.out.println("   📁 .env: " + entornoFromEnv + (entornoFromSystem == null ? " ✅ (usado)" : ""));
        } else {
            System.out.println("   📁 .env: No encontrada");
        }

        if (entornoFromSystem == null && entornoFromEnv == null) {
            System.out.println("   🔧 Por defecto: D ✅ (usado)");
        }

        // Mostrar todas las variables de entorno esperadas
        System.out.println("\n📝 VARIABLES DE ENTORNO ESPERADAS:");
        System.out.println("-".repeat(50));
        String[] roles = {"DIRECTIVO", "AUXILIAR", "PROFESOR_PRIMARIA", "PROFESOR_SECUNDARIA",
                "TUTOR", "RESPONSABLE", "PERSONAL_ADMINISTRATIVO"};

        for (String role : roles) {
            String usernameVar = getUsernameEnvVarName(role);
            String passwordVar = getPasswordEnvVarName(role);

            System.out.println("\n🎭 " + role + ":");

            if (usernameVar != null) {
                String usernameValue = getEnvironmentVariable(usernameVar);
                String usernameSource = getVariableSource(usernameVar);
                System.out.println("   👤 Username: " + usernameVar + " = " +
                        (usernameValue != null ? usernameValue + " " + usernameSource : "❌ NO ENCONTRADA"));
            }

            if (passwordVar != null) {
                String passwordValue = getEnvironmentVariable(passwordVar);
                String passwordSource = getVariableSource(passwordVar);
                System.out.println("   🔐 Password: " + passwordVar + " = " +
                        (passwordValue != null ? "***CONFIGURADA*** " + passwordSource : "❌ NO ENCONTRADA"));
            }
        }

        System.out.println("\n" + "=".repeat(50));
    }

    /**
     * Determina la fuente de una variable (sistema o .env)
     */
    private String getVariableSource(String varName) {
        String systemValue = System.getenv(varName);
        if (systemValue != null && !systemValue.trim().isEmpty()) {
            return "🌍";
        }

        if (dotenv != null) {
            String envValue = dotenv.get(varName);
            if (envValue != null && !envValue.trim().isEmpty()) {
                return "📁";
            }
        }

        return "❌";
    }

    /**
     * Verifica si todas las variables de entorno necesarias están configuradas
     */
    public boolean validateEnvironmentVariables() {
        boolean allValid = true;
        String[] roles = {"DIRECTIVO", "AUXILIAR", "PROFESOR_PRIMARIA", "PROFESOR_SECUNDARIA",
                "TUTOR", "RESPONSABLE", "PERSONAL_ADMINISTRATIVO"};

        System.out.println("\n🔍 VALIDANDO VARIABLES DE ENTORNO");
        System.out.println("=".repeat(40));

        for (String role : roles) {
            String usernameVar = getUsernameEnvVarName(role);
            String passwordVar = getPasswordEnvVarName(role);

            if (usernameVar == null || passwordVar == null) {
                System.err.println("❌ ERROR: No se pudieron obtener los nombres de variables para el rol: " + role);
                allValid = false;
                continue;
            }

            String usernameValue = getEnvironmentVariable(usernameVar);
            String passwordValue = getEnvironmentVariable(passwordVar);

            if (usernameValue == null) {
                System.err.println("❌ ERROR: Variable faltante: " + usernameVar);
                allValid = false;
            }

            if (passwordValue == null) {
                System.err.println("❌ ERROR: Variable faltante: " + passwordVar);
                allValid = false;
            }

            if (usernameValue != null && passwordValue != null) {
                String usernameSource = getVariableSource(usernameVar);
                String passwordSource = getVariableSource(passwordVar);
                System.out.println("✅ " + role + ": Variables configuradas " + usernameSource + passwordSource);
            }
        }

        System.out.println("=".repeat(40));
        return allValid;
    }
}