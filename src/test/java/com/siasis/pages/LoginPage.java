package com.siasis.pages;

import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;
    private final Dotenv dotenv;

    // Localizadores simplificados
    private final By welcomeMessage = By.xpath("//h1[text()='¡Hola!']");
    private final By usernameField = By.name("Nombre_Usuario");
    private final By passwordField = By.name("Contraseña");
    private final By loginButton = By.xpath("//button[text()='Ingresar']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.dotenv = Dotenv.configure().ignoreIfMissing().load();
    }

    public void navigateToLoginPage() {
        String url = dotenv.get("DEV_SIASIS_URL", "https://siasis-dev.vercel.app/login");
        driver.get(url);

        // Espera simple para que cargue la página
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
    }

    public void selectRole(String rol) {
        By rolSelector = By.xpath("//span[text()='" + rol + "']");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement roleElement = wait.until(ExpectedConditions.elementToBeClickable(rolSelector));
        roleElement.click();

        // Espera a que aparezcan los campos de login
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
    }

    public void enterCredentials(String role) {
        String formattedRole = role.toUpperCase().replaceAll("[^A-Za-z0-9]", "_");
        String username = dotenv.get(formattedRole + "_USERNAME", "admin");
        String password = dotenv.get(formattedRole + "_PASSWORD", "123456");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement userField = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        userField.clear();
        userField.sendKeys(username);

        WebElement passField = driver.findElement(passwordField);
        passField.clear();
        passField.sendKeys(password);
    }

    public void clickLoginButton() {
        WebElement loginBtn = driver.findElement(loginButton);
        loginBtn.click();
    }

    public void verifySuccessfulLogin() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeMessage));
    }
}