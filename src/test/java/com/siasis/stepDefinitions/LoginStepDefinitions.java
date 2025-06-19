package com.siasis.stepDefinitions;

import com.siasis.steps.LoginSteps;
import com.siasis.core.DriverManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class LoginStepDefinitions {
    private final LoginSteps loginSteps;

    public LoginStepDefinitions() {
        this.loginSteps = new LoginSteps(DriverManager.getDriver());
    }

    @Given("Estoy en la página de login")
    public void estoyEnLaPaginaDeLogin() {
        try {
            loginSteps.navigateToLoginPage();
            DriverManager.screenShot();
        } catch (AssertionError error) {
            DriverManager.screenShot();
            throw error;
        }
    }

    @Given("Selecciono el rol {string}")
    public void seleccionoElRol(String rol) {
        try {
            loginSteps.selectRole(rol);
            DriverManager.screenShot();
        } catch (AssertionError error) {
            DriverManager.screenShot();
            throw new AssertionError("Error al seleccionar el rol: " + rol + ". " + error.getMessage());
        }
    }

    @And("Ingreso mi nombre de usuario y contraseña")
    public void ingresoMiNombreDeUsuarioYContrasena() {
        try {
            loginSteps.enterCredentials();
            DriverManager.screenShot();
        } catch (AssertionError error) {
            DriverManager.screenShot();
            throw new AssertionError("Error al ingresar credenciales. " + error.getMessage());
        }
    }

    @Then("Accedo al sistema como {string}")
    public void accedoAlSistemaComo(String rol) {
        try {
            loginSteps.clickLoginButton();
            loginSteps.verifySuccessfulLogin();
            DriverManager.screenShot();
        } catch (AssertionError error) {
            DriverManager.screenShot();
            throw new AssertionError("Error al acceder al sistema como " + rol + ". " + error.getMessage());
        }
    }
}