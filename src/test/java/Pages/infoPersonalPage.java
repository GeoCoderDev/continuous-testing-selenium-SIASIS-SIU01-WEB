package Pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class infoPersonalPage {
    private final WebDriver driver;
    private final By bttnEditarDatos = By.xpath("//button[@title='Guarda tu cambios']");
    private final By inputDNI = By.name("DNI");
    private final By inputNombres = By.name("Nombres");
    private final By inputApellidos = By.name("Apellidos");
    private final By inputCelular = By.name("Celular");
    private final By optionGenero = By.cssSelector("select[name='Genero']");
    private final By bttnGuardarCambios = By.xpath("//button[text()='Guardar Cambios']");
    private  String dni;

    public infoPersonalPage(WebDriver driver) {
        this.driver = driver;
    }


    public void navigateMisDatos() {
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl != null) {
            String misDatosUrl = currentUrl + "/mis-datos";
            driver.get(misDatosUrl);
        } else {
            System.out.println("No se ha realizado el login correctamente.");
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement editarDatos = wait.until(ExpectedConditions.visibilityOfElementLocated(bttnEditarDatos));
        editarDatos.click();

    }

    public void againMisDatos(){
        WebElement editarDatos = driver.findElement(bttnEditarDatos);
        editarDatos.click();
    }

    public void editarDNI(String dni){
        WebElement dniField = driver.findElement(inputDNI);
        dniField.clear();
        dniField.sendKeys(dni);
        this.dni = dni;
    }

    public void editarNombres(String nombres){

        WebElement nombresField = driver.findElement(inputNombres);
        nombresField.clear();
        nombresField.sendKeys(nombres);
    }

    public void editarApellidos(String apellidos){
        WebElement apellidosField = driver.findElement(inputApellidos);
        apellidosField.clear();
        apellidosField.sendKeys(apellidos);
    }

    public void editarCelular(String celular){
        WebElement celularField = driver.findElement(inputCelular);
        celularField.clear();
        celularField.sendKeys(celular);
    }

    public void seleccionarGenero(String genero){
        WebElement generoDropdown = driver.findElement(optionGenero);
        generoDropdown.click();
        WebElement generoOption = driver.findElement(By.xpath("//option[text()='" + genero + "']"));
        generoOption.click();
    }

    public void guardarCambios(){
        WebElement guardarButton = driver.findElement(bttnGuardarCambios);
        guardarButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement verificarDatos = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), '" + dni + "')]")));
        if (verificarDatos != null) {
            System.out.println("Cambio de DNI guardado correctamente.");
        } else {
            System.out.println("Error al guardar el cambio de DNI.");
        }
    }


}
