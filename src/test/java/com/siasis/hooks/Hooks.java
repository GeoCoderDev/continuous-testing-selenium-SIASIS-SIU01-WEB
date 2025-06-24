package com.siasis.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Hooks {
    public static WebDriver driver;
    private static Scenario scenario;

    @Before
    public void setUp(Scenario scenario) {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        // Detectar si estamos en GitHub Actions
        boolean isGitHubActions = isRunningInGitHubActions();

        if (isGitHubActions) {
            System.out.println("🤖 Detectado entorno GitHub Actions - Configurando modo headless");

            // Configuraciones específicas para GitHub Actions (CI/CD)
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--user-data-dir=/tmp/chrome-test-profile-" + System.currentTimeMillis());
            options.addArguments("--disable-web-security");
            options.addArguments("--disable-features=VizDisplayCompositor");
            options.addArguments("--disable-extensions");
            options.addArguments("--no-first-run");
            options.addArguments("--disable-default-apps");
            options.addArguments("--disable-background-timer-throttling");
            options.addArguments("--disable-backgrounding-occluded-windows");
            options.addArguments("--disable-renderer-backgrounding");
            options.addArguments("--window-size=1920,1080");

        } else {
            System.out.println("💻 Detectado entorno local - Configurando modo visual");

            // Configuraciones para entorno local (tu configuración original)
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--remote-allow-origins=*");
        }

        driver = new ChromeDriver(options);

        // Solo maximizar en local, en GitHub Actions usar tamaño fijo
        if (!isGitHubActions) {
            driver.manage().window().maximize();
        }

        Hooks.scenario = scenario;
        System.out.println("✅ Iniciando escenario: " + scenario.getName());
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed() && driver != null) {
            takeScreenshot();
        }

        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    // Método estático para obtener el driver (necesario para DriverManager)
    public static WebDriver getDriver() {
        return driver;
    }

    public static void takeScreenshot() {
        if (driver != null && scenario != null) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Screenshot");
        }
    }

    /**
     * Detecta si el test se está ejecutando en GitHub Actions
     * @return true si está en GitHub Actions, false si es local
     */
    private boolean isRunningInGitHubActions() {
        // Opción 1: Variable específica que estableceremos en el YML
        String ciHeadless = System.getenv("CI_HEADLESS_MODE");
        if (ciHeadless != null) {
            return Boolean.parseBoolean(ciHeadless);
        }

        // Opción 2: Variables nativas de GitHub Actions (fallback)
        String githubActions = System.getenv("GITHUB_ACTIONS");
        String ci = System.getenv("CI");

        return "true".equals(githubActions) || "true".equals(ci);
    }
}