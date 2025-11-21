package com.siasis.stepDefinitions;

import com.siasis.steps.DatosPersonalesSteps;
import com.siasis.core.DriverManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DatosPersonalesStepDefinitions {
    private final DatosPersonalesSteps datosPersonalesSteps;

    public DatosPersonalesStepDefinitions() {
        this.datosPersonalesSteps = new DatosPersonalesSteps(DriverManager.getDriver());
    }

    @When("Estoy en la página de edición de perfil")
    public void estoyEnLaPaginaDeEdicionDePerfil() {
        try {
            datosPersonalesSteps.navigateToEditProfile();
            DriverManager.screenShot();
        } catch (AssertionError error) {
            DriverManager.screenShot();
            throw new AssertionError("Error al navegar a la página de edición de perfil. " + error.getMessage());
        }
    }

    @When("Edite mi DNI con el valor {string}")
    public void editeMiDNIConElValor(String dni) {
        try {
            datosPersonalesSteps.editDNI(dni);
            DriverManager.screenShot();
        } catch (AssertionError error) {
            DriverManager.screenShot();
            throw new AssertionError("Error al editar DNI con valor: " + dni + ". " + error.getMessage());
        }
    }

    @When("Edite mis nombres con el valor {string}")
    public void editeMisNombresConElValor(String nombres) {
        try {
            datosPersonalesSteps.editNames(nombres);
            DriverManager.screenShot();
        } catch (AssertionError error) {
            DriverManager.screenShot();
            throw new AssertionError("Error al editar nombres con valor: " + nombres + ". " + error.getMessage());
        }
    }

    @When("Edite mis apellidos con el valor {string}")
    public void editeMisApellidosConElValor(String apellidos) {
        try {
            datosPersonalesSteps.editLastNames(apellidos);
            DriverManager.screenShot();
        } catch (AssertionError error) {
            DriverManager.screenShot();
            throw new AssertionError("Error al editar apellidos con valor: " + apellidos + ". " + error.getMessage());
        }
    }

    @When("Edite mi celular con el valor {string}")
    public void editeMiCelularConElValor(String celular) {
        try {
            datosPersonalesSteps.editPhone(celular);
            DriverManager.screenShot();
        } catch (AssertionError error) {
            DriverManager.screenShot();
            throw new AssertionError("Error al editar celular con valor: " + celular + ". " + error.getMessage());
        }
    }

    @When("Selecciono el género {string}")
    public void seleccionoElGenero(String genero) {
        try {
            datosPersonalesSteps.selectGender(genero);
            DriverManager.screenShot();
        } catch (AssertionError error) {
            DriverManager.screenShot();
            throw new AssertionError("Error al seleccionar género: " + genero + ". " + error.getMessage());
        }
    }

    @When("Edito mis datos personales con los valores aleatorios")
    public void editoMisDatosPersonalesConLosValoresAleatorios() {
        try {
            datosPersonalesSteps.editPersonalDataWithRandomValues();
            DriverManager.screenShot();
        } catch (AssertionError error) {
            DriverManager.screenShot();
            throw new AssertionError("Error al editar datos personales con valores aleatorios. " + error.getMessage());
        }
    }

    @Then("Verifico que se han guardado los cambios")
    public void verificoQueSeHanGuardadoLosCambios() {
        try {
            datosPersonalesSteps.saveChanges();
            datosPersonalesSteps.verifyChangesSaved();
            DriverManager.screenShot();
        } catch (AssertionError error) {
            DriverManager.screenShot();
            throw new AssertionError("Error al verificar que los cambios se guardaron. " + error.getMessage());
        }
    }

    @And("Restauro los datos originales")
    public void restauroLosDatosOriginales() {
        try {
            datosPersonalesSteps.restoreOriginalData();
            DriverManager.screenShot();
        } catch (AssertionError error) {
            DriverManager.screenShot();
            throw new AssertionError("Error al restaurar los datos originales. " + error.getMessage());
        }
    }

    @Then("verifico que los datos originales son correctos")
    public void verificoQueLosDatosOriginalesSonCorrectos() {
        try {
            datosPersonalesSteps.saveChanges();
            datosPersonalesSteps.verifyOriginalDataRestored();
            DriverManager.screenShot();
        } catch (AssertionError error) {
            DriverManager.screenShot();
            throw new AssertionError("Error al verificar que los datos originales son correctos. " + error.getMessage());
        }
    }

    @When("doy click en el apartado de Registro de Personal")
    public void doyClickEnElApartadoDeRegistroDePersonal() {
        try {
            datosPersonalesSteps.clickRegistroPersonal();
            DriverManager.screenShot();
        } catch (AssertionError error) {
            DriverManager.screenShot();
            throw new AssertionError("Error al hacer click en el apartado de Registro de Personal. " + error.getMessage());
        }
    }

    @And("selecciono un profesor de primaria")
    public void seleccionoUnProfesorDePrimaria() {
        try {
            datosPersonalesSteps.seleccionarProfesorDePrimaria();
            DriverManager.screenShot();
            
        } catch (AssertionError error) {
            DriverManager.screenShot();
            throw new AssertionError("Error al seleccionar profesor de primaria. " + error.getMessage());
        }
        


    }

        @Then("se muestra una tabla con las asistencias del personal seleccionado")
        public void seMuestraUnaTablaConLasAsistenciasDelPersonalSeleccionado() {
            try {
                datosPersonalesSteps.validarTablaAsistenciasVisible();
                DriverManager.screenShot();
            } catch (AssertionError error) {
                DriverManager.screenShot();
                throw new AssertionError("No se muestra la tabla de asistencias esperada. " + error.getMessage());
            }
        }

        @And("doy click en el boton de Buscar")
        public void doyClickEnElBotonDeBuscar() {
            try {
                datosPersonalesSteps.clickBotonBuscar();
                DriverManager.screenShot();
            } catch (AssertionError error) {
                DriverManager.screenShot();
                throw new AssertionError("Error al hacer click en el botón Buscar. " + error.getMessage());
            }
        }
}