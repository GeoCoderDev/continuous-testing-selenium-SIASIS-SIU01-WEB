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

        public boolean isTablaAsistenciasVisible() {
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
                WebElement tabla = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table.w-full")));
                // Validar encabezados
                String[] expectedHeaders = {
                    "Fecha",
                    "Entrada Programada",
                    "Entrada Real",
                    "Diferencia Entrada",
                    "Estado Entrada",
                    "Salida Programada",
                    "Salida Real",
                    "Diferencia Salida",
                    "Estado Salida"
                };
                for (int i = 1; i <= expectedHeaders.length; i++) {
                    WebElement th = tabla.findElement(By.xpath(".//thead//th[" + i + "]"));
                    if (!th.getText().trim().equalsIgnoreCase(expectedHeaders[i-1])) {
                        throw new AssertionError("Encabezado incorrecto en la columna " + i + ": " + th.getText());
                    }
                }
                return true;
            } catch (Exception e) {
                return false;
            }
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

    public void clickRegistroPersonal() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            WebElement registroSpan = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Registros']"))
            );
            registroSpan.click();
            System.out.println("✅ Click en el apartado de Registro de Personal realizado");
        } catch (Exception e) {
            throw new RuntimeException("No se pudo hacer click en el apartado de Registro de Personal: " + e.getMessage());
        }
    }

    public void seleccionarTipoPersonal(String tipo) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement select = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[option[contains(text(),'Seleccionar tipo de personal')]]")));
        select.click();
        WebElement option = select.findElement(By.xpath(".//option[@value='PP']"));
        option.click();
        System.out.println("✅ Tipo de personal seleccionado: Profesor de Primaria");
    }

    public void clickSeleccionarProfesorDePrimaria() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement div = wait.until(ExpectedConditions.elementToBeClickable(
            By.id("SIASIS-SDU_Seccion-Consulta-Registros-Mensuales-Personal-Eventos-Prioritarios")
        ));
        div.click();
        System.out.println("✅ Click en div Seleccionar Profesor de Primaria por id");
    }

    public void seleccionarPrimerProfesorDeLista() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement primerLi = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//ul/li[1]")));
        primerLi.click();
        System.out.println("✅ Primer profesor de la lista seleccionado");
    }

    public void seleccionarMesAleatorioMayorAJunio() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement selectMes = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//select[option[contains(text(),'Seleccionar mes')]]")));
        // Opciones de mes superiores a junio (Julio=7, Agosto=8, ... Noviembre=11)
        int[] meses = {7,8,9,10,11};
        int mesAleatorio = meses[(int)(Math.random()*meses.length)];
        // Usar JavaScript para seleccionar el mes y disparar el evento change
        String script = "arguments[0].value='" + mesAleatorio + "'; arguments[0].dispatchEvent(new Event('change', { bubbles: true }));";
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(script, selectMes);
        System.out.println("✅ Mes seleccionado aleatoriamente (JS): " + mesAleatorio);
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
    }

    public void clickBotonBuscar() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        // Buscar el botón solo por texto
        WebElement botonBuscar = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//button[@type='button']")
        ));
        // Esperar hasta que esté habilitado usando JavaScript
        for (int i = 0; i < 20; i++) {
            if (botonBuscar.isEnabled()) {
                botonBuscar.click();
                System.out.println("✅ Click en el botón Buscar");
                return;
            }
            try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        }
        throw new RuntimeException("El botón Buscar nunca se habilitó");
    }
}