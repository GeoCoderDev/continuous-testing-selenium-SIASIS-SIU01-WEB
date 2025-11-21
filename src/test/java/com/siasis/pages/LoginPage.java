package com.siasis.pages;

import com.siasis.config.ConfigManager;
import com.siasis.enums.RolesDelSistema;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;
    private final ConfigManager configManager;
    private RolesDelSistema currentRole;

    // Localizadores
    private final By welcomeMessage = By.xpath("//h1[text()='¡Hola!']");
    private final By usernameField = By.name("Nombre_Usuario");
    private final By passwordField = By.name("Contraseña");
    private final By loginButton = By.xpath("//button[text()='Ingresar']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.configManager = ConfigManager.getInstance();

        // Log para debug
        System.out.println("🔧 LoginPage inicializada para entorno: " +
                configManager.getCurrentEnvironment() + " (config: " +
                configManager.getCurrentConfigEnvironment() + ")");
    }

    public void navigateToLoginPage() {
        String url = configManager.getSiasisUrl() + "/login";
        System.out.println("🌐 Navegando a: " + url);
        driver.get(url);

        // Espera simple para que cargue la página
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        System.out.println("✅ Página de login cargada correctamente");
    }

    /**
     * Selecciona un rol usando el enum del sistema
     */
    public void selectRole(RolesDelSistema rol) {
        this.currentRole = rol;
        String textoBoton = rol.getTextoBotonFrontend();

        System.out.println("🎭 Seleccionando rol: " + rol.getDescripcion() +
                " (botón: '" + textoBoton + "')");

        By rolSelector = By.xpath("//span[text()='" + textoBoton + "']");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            WebElement roleElement = wait.until(ExpectedConditions.elementToBeClickable(rolSelector));
            roleElement.click();

            // Espera a que aparezcan los campos de login
            wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
            System.out.println("✅ Rol seleccionado y campos de login visibles");

        } catch (Exception e) {
            throw new RuntimeException("❌ Error al seleccionar rol '" + rol.getDescripcion() +
                    "' con texto de botón '" + textoBoton + "': " + e.getMessage());
        }
    }

    /**
     * Selecciona un rol usando texto (para compatibilidad con Cucumber string)
     * @deprecated Usar selectRole(RolesDelSistema) en su lugar
     */
    @Deprecated
    public void selectRole(String rolTexto) {
        try {
            RolesDelSistema rol = RolesDelSistema.fromNombre(rolTexto);
            selectRole(rol);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("❌ Rol no válido: '" + rolTexto + "'. " +
                    "Roles disponibles: " + java.util.Arrays.toString(RolesDelSistema.values()));
        }
    }

    /**
     * Ingresa credenciales para el rol actual
     */
    public void enterCredentials() {
        if (currentRole == null) {
            throw new IllegalStateException("❌ No se ha seleccionado ningún rol. Llamar selectRole() primero.");
        }

        enterCredentials(currentRole);
    }

    /**
     * Ingresa credenciales para un rol específico
     */
    public void enterCredentials(RolesDelSistema rol) {
        String claveConfig = rol.getClaveConfiguracion();
        String username = configManager.getUsername(claveConfig);
        String password = configManager.getPassword(claveConfig);

        System.out.println("🔐 Ingresando credenciales para rol: " + rol.getDescripcion());
        System.out.println("📝 Clave configuración: " + claveConfig);
        System.out.println("👤 Username: " + username);

        // Verificar si se están usando credenciales por defecto
        if (username.equals("admin") || password.equals("123456")) {
            System.err.println("⚠️  ADVERTENCIA: Se están usando credenciales por defecto para el rol: " +
                    rol.getDescripcion());
            System.err.println("   Esto podría indicar que las variables de entorno no están configuradas correctamente.");
            System.err.println("   Variable esperada para username: " + claveConfig + "_USERNAME_" +
                    configManager.getCurrentEnvironment().toUpperCase());
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            WebElement userField = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
            userField.clear();
            userField.sendKeys(username);

            WebElement passField = driver.findElement(passwordField);
            passField.clear();
            passField.sendKeys(password);

            System.out.println("✅ Credenciales ingresadas correctamente");

        } catch (Exception e) {
            throw new RuntimeException("❌ Error al ingresar credenciales para rol '" +
                    rol.getDescripcion() + "': " + e.getMessage());
        }
    }

    /**
     * Ingresa credenciales para un rol específico (versión string para compatibilidad)
     * @deprecated Usar enterCredentials(RolesDelSistema) en su lugar
     */
    @Deprecated
    public void enterCredentials(String rolTexto) {
        try {
            RolesDelSistema rol = RolesDelSistema.fromNombre(rolTexto);
            enterCredentials(rol);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("❌ Rol no válido: '" + rolTexto + "'. " + e.getMessage());
        }
    }

    public void clickLoginButton() {
        try {
            WebElement loginBtn = driver.findElement(loginButton);
            loginBtn.click();
            System.out.println("🔄 Botón de login clickeado");
        } catch (Exception e) {
            throw new RuntimeException("❌ Error al hacer click en el botón de login: " + e.getMessage());
        }
    }

    public void verifySuccessfulLogin() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeMessage));
            System.out.println("✅ Login exitoso - Mensaje de bienvenida visible");
        } catch (Exception e) {
            throw new RuntimeException("❌ Error en la verificación de login exitoso: " + e.getMessage());
        }
    }

    /**
     * Método de conveniencia para hacer login completo con enum
     */
    public void performLogin(RolesDelSistema rol) {
        System.out.println("\n🚀 Iniciando proceso de login para rol: " + rol.getDescripcion());

        selectRole(rol);
        enterCredentials(rol);
        clickLoginButton();
        verifySuccessfulLogin();

        System.out.println("✅ Login completado exitosamente para rol: " + rol.getDescripcion() +
                " en entorno: " + configManager.getCurrentEnvironment());
    }

    /**
     * Método de conveniencia para hacer login completo con string (para compatibilidad)
     * @deprecated Usar performLogin(RolesDelSistema) en su lugar
     */
    @Deprecated
    public void performLogin(String rolTexto) {
        try {
            RolesDelSistema rol = RolesDelSistema.fromNombre(rolTexto);
            performLogin(rol);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("❌ Rol no válido: '" + rolTexto + "'. " + e.getMessage());
        }
    }

    /**
     * Obtiene el rol actualmente seleccionado
     */
    public RolesDelSistema getCurrentRole() {
        return currentRole;
    }

    /**
     * Método para debug - muestra la configuración current
     */
    public void printCurrentConfig() {
        configManager.printConfig();
    }

    /**
     * Valida que todas las variables de entorno estén configuradas antes de ejecutar tests
     */
    public boolean validateConfiguration() {
        System.out.println("\n🔍 Validando configuración antes de ejecutar tests...");
        boolean isValid = configManager.validateEnvironmentVariables();

        if (isValid) {
            System.out.println("✅ Todas las variables de entorno están configuradas correctamente");
        } else {
            System.err.println("❌ Faltan variables de entorno. Revisa la configuración.");
        }

        return isValid;
    }

    /**
     * Muestra todos los roles disponibles
     */
    public static void printAvailableRoles() {
        System.out.println("\n📋 Roles disponibles en el sistema:");
        for (RolesDelSistema rol : RolesDelSistema.values()) {
            System.out.println("  - " + rol.name() + ": " + rol.getDescripcion() +
                    " (botón: '" + rol.getTextoBotonFrontend() + "')");
        }
    }

    public void enterCredentialsDirectivo(String username, String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            WebElement userField = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
            userField.clear();
            userField.sendKeys(username);

            WebElement passField = driver.findElement(passwordField);
            passField.clear();
            passField.sendKeys(password);

            System.out.println("✅ Credenciales ingresadas correctamente para DIRECTIVO");
        } catch (Exception e) {
            throw new RuntimeException("❌ Error al ingresar credenciales para DIRECTIVO: " + e.getMessage());
        }
    }
}