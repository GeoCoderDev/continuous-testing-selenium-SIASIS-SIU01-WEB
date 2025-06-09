package steps;
import Pages.LoginPage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class LoginSiasis {
    private WebDriver driver;
    private LoginPage loginPage;
    private String rolSeleccionado;

    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        loginPage = new LoginPage(driver);
    }

    @Given("Estoy en la página de login")
    public void estoyEnLaPáginaDeLogin() {
        loginPage.navigateToLoginPage();
    }

    @When("Selecciono el rol {string}")
    public void seleccionoElRol(String rol) {
        this.rolSeleccionado = rol;
        loginPage.selectRole(rol);
    }

    @And("Ingreso mi nombre de usuario y contraseña")
    public void ingresoMiNombreDeUsuarioYContraseña() {
        loginPage.enterCredentials(rolSeleccionado);
    }

    @Then("Accedo al sistema como {string}")
    public void accedoAlSistemaComo() {
        loginPage.clickLoginButton();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
