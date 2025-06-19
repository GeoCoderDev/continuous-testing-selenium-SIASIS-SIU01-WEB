package com.siasis.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class DatosPersonalesPage {
    private final WebDriver driver;
    private String lastDNI;

    // Localizadores simplificados
    private final By editButton = By.xpath("//button[@title='Guarda tu cambios']");
    private final By dniInput = By.name("DNI");
    private final By namesInput = By.name("Nombres");
    private final By lastNamesInput = By.name("Apellidos");
    private final By phoneInput = By.name("Celular");
    private final By genderSelect = By.cssSelector("select[name='Genero']");
    private final By saveButton = By.xpath("//button[text()='Guardar Cambios']");

    public DatosPersonalesPage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateToEditProfile() {
        String currentUrl = driver.getCurrentUrl();
        String misDatosUrl = currentUrl + "/mis-datos";
        driver.get(misDatosUrl);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement editBtn = wait.until(ExpectedConditions.elementToBeClickable(editButton));
        editBtn.click();
    }

    public void clickEditButton() {
        WebElement editBtn = driver.findElement(editButton);
        editBtn.click();
    }

    public void editDNI(String dni) {
        WebElement dniField = driver.findElement(dniInput);
        dniField.clear();
        dniField.sendKeys(dni);
        this.lastDNI = dni;
    }

    public void editNames(String nombres) {
        WebElement namesField = driver.findElement(namesInput);
        namesField.clear();
        namesField.sendKeys(nombres);
    }

    public void editLastNames(String apellidos) {
        WebElement lastNamesField = driver.findElement(lastNamesInput);
        lastNamesField.clear();
        lastNamesField.sendKeys(apellidos);
    }

    public void editPhone(String celular) {
        WebElement phoneField = driver.findElement(phoneInput);
        phoneField.clear();
        phoneField.sendKeys(celular);
    }

    public void selectGender(String genero) {
        WebElement genderDropdown = driver.findElement(genderSelect);
        genderDropdown.click();
        WebElement generoOption = driver.findElement(By.xpath("//option[text()='" + genero + "']"));
        generoOption.click();
    }

    public void saveChanges() {
        WebElement saveBtn = driver.findElement(saveButton);
        saveBtn.click();

        if (lastDNI != null) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//span[contains(text(), '" + lastDNI + "')]")
            ));
        }
    }

    public boolean verifyDNIDisplayed(String expectedDNI) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            By dniDisplayLocator = By.xpath("//span[contains(text(), '" + expectedDNI + "')]");
            WebElement dniElement = wait.until(ExpectedConditions.visibilityOfElementLocated(dniDisplayLocator));
            return dniElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}