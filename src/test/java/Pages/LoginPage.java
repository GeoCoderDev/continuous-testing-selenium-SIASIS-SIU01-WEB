package Pages;
import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    final private WebDriver driver;
    final private Dotenv dotenv;

    final private By usernameField = By.name("Nombre_Usuario");
    final private By passwordField = By.name("Contraseña");
    final private By loginButton = By.xpath("//button[text()='Ingresar']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.dotenv = Dotenv.load();
    }

    public void navigateToLoginPage() {
        String url = dotenv.get("DEV_SIASIS_URL");
        driver.get(url);
    }

    public void selectRole(String rol) {
        By rolSelector = By.xpath("//span[text()='" + rol + "']");  // Aquí construimos dinámicamente el XPath
        WebElement roleElement = driver.findElement(rolSelector);
        roleElement.click();
    }

    public void enterCredentials(String role) {
        String formattedRole = role.toUpperCase().replaceAll("[^A-Za-z0-9]", "_");
        String username = dotenv.get(formattedRole + "_USERNAME");
        String password = dotenv.get(formattedRole + "_PASSWORD");

        if (username != null && password != null) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField)).sendKeys(username);
            driver.findElement(passwordField).sendKeys(password);
        } else {
            System.out.println("Credenciales no encontradas para el rol: " + role);
        }
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }
}
