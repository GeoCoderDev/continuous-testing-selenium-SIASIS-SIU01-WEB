package steps;
import Pages.infoPersonalPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;
import java.util.Map;

public class editarInfoPersonal {

    private infoPersonalPage infoPersonal;

    public editarInfoPersonal() {
        infoPersonal = new infoPersonalPage(Hooks.driver);
    }

    @When("Estoy en la página de edición de perfil")
    public void estoyEnLaPaginaDeEdicionDeMiPerfil() {
        infoPersonal.navigateMisDatos();
    }

    @When("Edite mi DNI con el valor {string}")
    public void editeMiDNIConElValor(String dni) {
        infoPersonal.editarDNI(dni);
    }

    @When("Edite mis nombres con el valor {string}")
    public void editeMisNombresConElValor(String nombres) {
        infoPersonal.editarNombres(nombres);
    }

    @When("Edite mis apellidos con el valor {string}")
    public void editeMisApellidosConElValor(String apellidos) {
        infoPersonal.editarApellidos(apellidos);
    }

    @When("Edite mi celular con el valor {string}")
    public void editeMiCelularConElValor(String celular) {
        infoPersonal.editarCelular(celular);
    }

    @When("Selecciono el género {string}")
    public void seleccionoElGenero(String genero) {
        infoPersonal.seleccionarGenero(genero);
    }

    @When("Edito mis datos personales con los valores:")
    public void editoMisDatosPersonalesConLosValores(DataTable dataTable) {
        Map<String, String> datos = dataTable.asMap(String.class, String.class);
        infoPersonal.editarNombres(datos.get("Nombre"));
        infoPersonal.editarApellidos(datos.get("Apellidos"));
        infoPersonal.seleccionarGenero(datos.get("Género"));
        infoPersonal.editarCelular(datos.get("Celular"));
        infoPersonal.guardarCambios();
    }

    @Then("Mi perfil se actualiza correctamente para el rol {string}")
    public void miPerfilSeActualizaCorrectamenteParaElRol(String arg0) {
    }
}