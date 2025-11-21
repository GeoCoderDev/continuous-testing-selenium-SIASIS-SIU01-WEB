package com.siasis.steps;

import com.siasis.pages.LoginPage;
import com.siasis.enums.RolesDelSistema;
import org.openqa.selenium.WebDriver;

public class LoginSteps {
    private final LoginPage loginPage;
    private RolesDelSistema selectedRole;
    private Exception lastException;

    public LoginSteps(WebDriver driver) {
        this.loginPage = new LoginPage(driver);
    }

    public void navigateToLoginPage() {
        try {
            loginPage.navigateToLoginPage();
        } catch (Exception e) {
            throw new RuntimeException("Error al navegar a la página de login: " + e.getMessage(), e);
        }
    }

    /**
     * Selecciona un rol usando el enum del sistema
     */
    public void selectRole(RolesDelSistema role) {
        try {
            this.selectedRole = role;
            loginPage.selectRole(role);
            System.out.println("✅ Rol seleccionado: " + role.getDescripcion());
        } catch (Exception e) {
            throw new RuntimeException("Error al seleccionar el rol " + role.getDescripcion() + ": " + e.getMessage(), e);
        }
    }

    /**
     * Selecciona un rol usando string (convierte a enum)
     */
    public void selectRole(String roleString) {
        try {
            RolesDelSistema role = RolesDelSistema.valueOf(roleString.toUpperCase());
            selectRole(role);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Rol no válido: '" + roleString + "'. Roles disponibles: " +
                    java.util.Arrays.toString(RolesDelSistema.values()), e);
        }
    }

    /**
     * Intenta seleccionar un rol inexistente para testing de errores
     */
    public void selectInvalidRole(String invalidRole) {
        try {
            // Intentamos crear un rol inexistente
            RolesDelSistema.valueOf(invalidRole.toUpperCase());
            throw new RuntimeException("El rol '" + invalidRole + "' debería ser inválido pero fue encontrado");
        } catch (IllegalArgumentException e) {
            // Este es el comportamiento esperado
            this.lastException = e;
            System.out.println("✅ Rol inválido detectado correctamente: " + invalidRole);
        }
    }

    public void enterCredentials() {
        if (selectedRole == null) {
            throw new IllegalStateException("No se ha seleccionado ningún rol. Llamar selectRole() primero.");
        }

        try {
            loginPage.enterCredentials(selectedRole);
            System.out.println("✅ Credenciales ingresadas para rol: " + selectedRole.getDescripcion());
        } catch (Exception e) {
            throw new RuntimeException("Error al ingresar credenciales para el rol " +
                    selectedRole.getDescripcion() + ": " + e.getMessage(), e);
        }
    }

    public void clickLoginButton() {
        try {
            loginPage.clickLoginButton();
            System.out.println("✅ Botón de login clickeado");
        } catch (Exception e) {
            throw new RuntimeException("Error al hacer click en el botón de login: " + e.getMessage(), e);
        }
    }

    public void verifySuccessfulLogin() {
        try {
            loginPage.verifySuccessfulLogin();
            System.out.println("✅ Login verificado exitosamente para rol: " +
                    (selectedRole != null ? selectedRole.getDescripcion() : "desconocido"));
        } catch (Exception e) {
            throw new RuntimeException("Error en la verificación de login exitoso: " + e.getMessage(), e);
        }
    }

    /**
     * Realiza el proceso completo de login
     */
    public void performCompleteLogin(RolesDelSistema role) {
        selectRole(role);
        enterCredentials();
        clickLoginButton();
        verifySuccessfulLogin();
    }

    /**
     * Realiza el proceso completo de login con string
     */
    public void performCompleteLogin(String roleString) {
        RolesDelSistema role = RolesDelSistema.valueOf(roleString.toUpperCase());
        performCompleteLogin(role);
    }

    /**
     * Valida que la configuración esté correcta
     */
    public boolean validateConfiguration() {
        return loginPage.validateConfiguration();
    }

    /**
     * Obtiene el rol actualmente seleccionado
     */
    public RolesDelSistema getSelectedRole() {
        return selectedRole;
    }

    /**
     * Obtiene la última excepción capturada (para testing de errores)
     */
    public Exception getLastException() {
        return lastException;
    }

    /**
     * Verifica que se haya capturado un error apropiado
     */
    public void verifyErrorHandling() {
        if (lastException == null) {
            throw new RuntimeException("Se esperaba un error pero no se capturó ninguno");
        }

        if (!(lastException instanceof IllegalArgumentException)) {
            throw new RuntimeException("Se esperaba IllegalArgumentException pero se obtuvo: " +
                    lastException.getClass().getSimpleName());
        }

        System.out.println("✅ Manejo de errores verificado correctamente");
    }

    /**
     * Método para debug - muestra configuración actual
     */
    public void printCurrentConfig() {
        loginPage.printCurrentConfig();
    }

    /**
     * Muestra todos los roles disponibles
     */
    public void printAvailableRoles() {
        LoginPage.printAvailableRoles();
    }

    public void enterCredentialsDirectivo(String username, String password) {
        try {
            loginPage.enterCredentialsDirectivo(username, password);
            System.out.println("✅ Credenciales ingresadas para DIRECTIVO: " + username);
        } catch (Exception e) {
            throw new RuntimeException("Error al ingresar credenciales para DIRECTIVO: " + e.getMessage(), e);
        }
    }
}