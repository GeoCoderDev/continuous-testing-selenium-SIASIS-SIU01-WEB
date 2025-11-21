package com.siasis.stepDefinitions;

import com.siasis.steps.LoginSteps;
import com.siasis.enums.RolesDelSistema;
import com.siasis.core.DriverManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;

public class LoginStepDefinitions {
    private final LoginSteps loginSteps;

    public LoginStepDefinitions() {
        this.loginSteps = new LoginSteps(DriverManager.getDriver());
    }

    @Given("Estoy en la página de login")
    public void estoyEnLaPaginaDeLogin() {
        try {
            System.out.println("🚀 Iniciando navegación a página de login");
            loginSteps.navigateToLoginPage();
            DriverManager.screenShot();
            System.out.println("✅ Navegación completada exitosamente");
        } catch (Exception error) {
            System.err.println("❌ Error durante la navegación: " + error.getMessage());
            DriverManager.screenShot();
            throw new AssertionError("Error al navegar a la página de login: " + error.getMessage(), error);
        }
    }

    @Given("Selecciono el rol {word}")
    public void seleccionoElRol(String rolString) {
        try {
            System.out.println("🎭 Seleccionando rol del sistema: " + rolString);

            // Convertir string a enum y validar
            RolesDelSistema rol;
            try {
                rol = RolesDelSistema.valueOf(rolString.toUpperCase());
            } catch (IllegalArgumentException e) {
                loginSteps.printAvailableRoles();
                throw new AssertionError("Rol no válido: '" + rolString + "'. Ver roles disponibles arriba.", e);
            }

            loginSteps.selectRole(rol);
            // Si el rol es DIRECTIVO, ingresar credenciales fijas aquí
            if (rol == RolesDelSistema.DIRECTIVO) {
                loginSteps.enterCredentialsDirectivo("director.asuncion8", "15430124");
            }
            DriverManager.screenShot();
            System.out.println("✅ Rol seleccionado: " + rol.getDescripcion());

        } catch (AssertionError error) {
            DriverManager.screenShot();
            throw error;
        } catch (Exception error) {
            System.err.println("❌ Error inesperado al seleccionar rol: " + error.getMessage());
            DriverManager.screenShot();
            throw new AssertionError("Error inesperado al seleccionar el rol: " + rolString + ". " + error.getMessage(), error);
        }
    }

    @And("Ingreso mi nombre de usuario y contraseña")
    public void ingresoMiNombreDeUsuarioYContrasena() {
        try {
            System.out.println("🔐 Ingresando credenciales del usuario");

            RolesDelSistema rolActual = loginSteps.getSelectedRole();
            if (rolActual == null) {
                throw new AssertionError("No se ha seleccionado ningún rol antes de ingresar credenciales");
            }

            // Si el rol es DIRECTIVO, no sobrescribir credenciales
            if (rolActual == RolesDelSistema.DIRECTIVO) {
                System.out.println("⚠️  Credenciales de DIRECTIVO ya ingresadas, se omite sobrescritura.");
            } else {
                loginSteps.enterCredentials();
                System.out.println("✅ Credenciales ingresadas para rol: " + rolActual.getDescripcion());
            }
            DriverManager.screenShot();

        } catch (AssertionError error) {
            DriverManager.screenShot();
            throw error;
        } catch (Exception error) {
            System.err.println("❌ Error al ingresar credenciales: " + error.getMessage());
            DriverManager.screenShot();
            throw new AssertionError("Error al ingresar credenciales: " + error.getMessage(), error);
        }
    }

    @Then("Accedo al sistema como {word}")
    public void accedoAlSistemaComo(String rolString) {
        try {
            System.out.println("🔄 Iniciando proceso de login para rol: " + rolString);

            // Validar que el rol coincida con el seleccionado
            RolesDelSistema rolEsperado;
            try {
                rolEsperado = RolesDelSistema.valueOf(rolString.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new AssertionError("Rol no válido en verificación: '" + rolString + "'", e);
            }

            RolesDelSistema rolActual = loginSteps.getSelectedRole();
            if (!rolEsperado.equals(rolActual)) {
                throw new AssertionError("El rol esperado (" + rolEsperado.getDescripcion() +
                        ") no coincide con el rol seleccionado (" +
                        (rolActual != null ? rolActual.getDescripcion() : "ninguno") + ")");
            }

            loginSteps.clickLoginButton();
            loginSteps.verifySuccessfulLogin();
            DriverManager.screenShot();

            System.out.println("✅ Login completado exitosamente para rol: " + rolEsperado.getDescripcion());

        } catch (AssertionError error) {
            DriverManager.screenShot();
            throw error;
        } catch (Exception error) {
            System.err.println("❌ Error durante el login: " + error.getMessage());
            DriverManager.screenShot();
            throw new AssertionError("Error al acceder al sistema como " + rolString + ": " + error.getMessage(), error);
        }
    }

    @Then("Todas las variables de entorno están configuradas correctamente")
    public void todasLasVariablesDeEntornoEstanConfiguradasCorrectamente() {
        try {
            System.out.println("🔍 Validando configuración de variables de entorno");

            boolean configuracionValida = loginSteps.validateConfiguration();
            DriverManager.screenShot();

            if (!configuracionValida) {
                loginSteps.printCurrentConfig();
                throw new AssertionError("Las variables de entorno no están configuradas correctamente. " +
                        "Revisa los logs arriba para ver qué variables faltan.");
            }

            System.out.println("✅ Todas las variables de entorno están configuradas correctamente");

        } catch (AssertionError error) {
            DriverManager.screenShot();
            throw error;
        } catch (Exception error) {
            System.err.println("❌ Error durante validación de configuración: " + error.getMessage());
            DriverManager.screenShot();
            throw new AssertionError("Error al validar configuración: " + error.getMessage(), error);
        }
    }

    @Given("Intento seleccionar un rol inexistente")
    public void intentoSeleccionarUnRolInexistente() {
        try {
            System.out.println("🧪 Probando manejo de rol inexistente");

            String rolInexistente = "ROL_QUE_NO_EXISTE";
            loginSteps.selectInvalidRole(rolInexistente);
            DriverManager.screenShot();

            System.out.println("✅ Manejo de rol inexistente probado");

        } catch (Exception error) {
            System.err.println("❌ Error inesperado durante prueba de rol inexistente: " + error.getMessage());
            DriverManager.screenShot();
            throw new AssertionError("Error inesperado durante prueba de rol inexistente: " + error.getMessage(), error);
        }
    }

    @Then("El sistema muestra un error apropiado")
    public void elSistemaMuestraUnErrorApropiado() {
        try {
            System.out.println("🔍 Verificando que se haya manejado el error correctamente");

            loginSteps.verifyErrorHandling();
            DriverManager.screenShot();

            System.out.println("✅ Manejo de errores verificado correctamente");

        } catch (Exception error) {
            System.err.println("❌ Error durante verificación de manejo de errores: " + error.getMessage());
            DriverManager.screenShot();
            throw new AssertionError("Error durante verificación de manejo de errores: " + error.getMessage(), error);
        }
    }

    // Step definitions adicionales para casos especiales

    @Given("Estoy en la página de login y valido la configuración")
    public void estoyEnLaPaginaDeLoginYValidoLaConfiguracion() {
        estoyEnLaPaginaDeLogin();
        todasLasVariablesDeEntornoEstanConfiguradasCorrectamente();
    }

    @Then("Puedo ver todos los roles disponibles en el sistema")
    public void puedoVerTodosLosRolesDisponiblesEnElSistema() {
        try {
            System.out.println("📋 Mostrando todos los roles disponibles:");
            loginSteps.printAvailableRoles();
            DriverManager.screenShot();

            // Verificar que todos los roles estén definidos
            RolesDelSistema[] roles = RolesDelSistema.values();
            Assertions.assertTrue(roles.length > 0, "Debe haber al menos un rol definido");

            for (RolesDelSistema rol : roles) {
                Assertions.assertNotNull(rol.getDescripcion(), "Cada rol debe tener una descripción");
                Assertions.assertNotNull(rol.getTextoBotonFrontend(), "Cada rol debe tener texto de botón");
                Assertions.assertNotNull(rol.getClaveConfiguracion(), "Cada rol debe tener clave de configuración");
            }

            System.out.println("✅ Todos los roles están correctamente definidos");

        } catch (Exception error) {
            System.err.println("❌ Error al verificar roles: " + error.getMessage());
            DriverManager.screenShot();
            throw new AssertionError("Error al verificar roles disponibles: " + error.getMessage(), error);
        }
    }
}