package com.siasis.core;

import org.openqa.selenium.WebDriver;
import com.siasis.hooks.Hooks;

public class DriverManager {

    /**
     * Obtiene la instancia actual del WebDriver desde Hooks
     * @return WebDriver instance o null si no está inicializado
     */
    public static WebDriver getDriver() {
        return Hooks.getDriver();
    }

    /**
     * Captura una screenshot y la adjunta al escenario actual
     * Método de utilidad para usar en stepDefinitions
     */
    public static void screenShot() {
        Hooks.takeScreenshot();
    }

    /**
     * Verifica si el driver está disponible
     * @return true si el driver está inicializado, false en caso contrario
     */
    public static boolean isDriverAvailable() {
        return getDriver() != null;
    }

    /**
     * Obtiene el título de la página actual
     * @return título de la página o mensaje de error si no hay driver
     */
    public static String getCurrentPageTitle() {
        WebDriver driver = getDriver();
        if (driver != null) {
            return driver.getTitle();
        }
        return "No hay driver disponible";
    }

    /**
     * Obtiene la URL actual
     * @return URL actual o mensaje de error si no hay driver
     */
    public static String getCurrentUrl() {
        WebDriver driver = getDriver();
        if (driver != null) {
            return driver.getCurrentUrl();
        }
        return "No hay driver disponible";
    }
}