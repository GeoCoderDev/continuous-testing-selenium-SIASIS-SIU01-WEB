package steps;
import Pages.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class LoginSiasis {

    private final LoginPage loginPage;

    public LoginSiasis() {
        this.loginPage = new LoginPage(Hooks.driver);
    }

    private String rolSeleccionado;

    @Given("Estoy en la página de login")
    public void navegarLogin() {
        loginPage.navigateToLoginPage();
    }

    @Given("Selecciono el rol {string}")
    public void seleccionoElRol(String rol) {
        this.rolSeleccionado = rol;
        loginPage.selectRole(rol);
    }

    @And("Ingreso mi nombre de usuario y contraseña")
    public void ingresoCredenciales() {
        loginPage.enterCredentials(rolSeleccionado);
    }

    @Then("Accedo al sistema como {string}")
    public void accedoAlSistemaComo(String rolSeleccionado) {
        loginPage.clickLoginButton();
    }

}
