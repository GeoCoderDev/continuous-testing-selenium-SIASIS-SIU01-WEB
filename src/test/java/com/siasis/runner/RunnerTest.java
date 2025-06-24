package com.siasis.runner;

import com.siasis.utils.EnvironmentDetector;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.siasis.stepDefinitions", "com.siasis.hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/index.html",
                "json:target/cucumber/cucumber.json",
                // Los reportes adicionales por entorno se generan automáticamente via Maven
        },
        monochrome = true
)
public class RunnerTest {

        // Bloque estático para mostrar información del entorno al inicio
        static {
                try {
                        String environment = EnvironmentDetector.detectEnvironment();
                        String environmentVar = System.getenv("ENTORNO");
                        String environmentFromEnv = environmentVar != null ? environmentVar : "D (por defecto)";

                        System.out.println("\n" + "=".repeat(60));
                        System.out.println("🚀 INICIANDO EJECUCIÓN DE TESTS - SIASIS");
                        System.out.println("=".repeat(60));
                        System.out.println("🌍 Variable ENTORNO: " + environmentFromEnv);
                        System.out.println("📁 Entorno detectado: " + environment.toUpperCase());
                        System.out.println("📊 Reportes se generarán en:");
                        System.out.println("   📁 target/ (siempre)");
                        System.out.println("   📁 reports/" + environment + "/ (copia automática)");
                        System.out.println("🎯 Tags de ejecución: " + System.getProperty("cucumber.filter.tags", "Todos"));
                        System.out.println("=".repeat(60) + "\n");

                } catch (Exception e) {
                        System.err.println("⚠️ Error al detectar entorno: " + e.getMessage());
                }
        }
}