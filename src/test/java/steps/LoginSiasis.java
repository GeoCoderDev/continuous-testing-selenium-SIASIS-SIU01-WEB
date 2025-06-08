package steps;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginSiasis {
    private Dotenv dotenv;
    private String rolSeleccionado;
    WebDriver driver;

    private WebElement esperarElementoVisible(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    @Before
    public void setup() {
        dotenv = Dotenv.load();
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Given("Estoy en la página de login")
    public void estoyEnLaPáginaDeLogin() {
        String url = dotenv.get("DEV_SIASIS_URL");
        driver.get(url);
    }

    @When("Selecciono el rol {string}")
    public void seleccionoElRol(String arg) {
        this.rolSeleccionado = arg;
        WebElement rol = driver.findElement(By.xpath("//span[text()='" + arg + "']"));
        rol.click();
    }

    @And("Ingreso mi nombre de usuario y contraseña")
    public void ingresoMiNombreDeUsuarioYContraseña() {
        String rolFormateado = rolSeleccionado.toUpperCase().replaceAll("[^A-Za-z0-9]", "_");
        String username = dotenv.get(rolFormateado + "_USERNAME");
        String password = dotenv.get(rolFormateado + "_PASSWORD");
        System.out.println("Clave de entorno: " + rolFormateado + "_USERNAME");

        if (username != null && password != null) {
            WebElement usernameField = esperarElementoVisible(By.name("Nombre_Usuario"));
            WebElement passwordField = driver.findElement(By.name("Contraseña"));
            usernameField.sendKeys(username);
            passwordField.sendKeys(password);
        } else {
            System.out.println("Credenciales no encontradas para el rol: " + rolSeleccionado);
        }
    }

    @Then("Accedo al sistema como {string}")
    public void accedoAlSistemaComo(String rol) {
        WebElement botonIngresar = driver.findElement(By.xpath("//button[text()='Ingresar']"));
        botonIngresar.click();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
