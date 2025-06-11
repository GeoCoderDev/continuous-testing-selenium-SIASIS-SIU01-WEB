package steps;
import Pages.infoPersonalPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.datafaker.Faker;


public class editarInfoPersonal {
    private Faker faker;
    private infoPersonalPage infoPersonal;

    public editarInfoPersonal() {
        infoPersonal = new infoPersonalPage(Hooks.driver);
        faker = new Faker();
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

    @When("Edito mis datos personales con los valores aleatorios")
    public void editoMisDatosPersonalesConLosValores() {
        String nombreFaker = faker.name().firstName();
        String apellidoFaker = faker.name().lastName();
        String celularFaker = "9" + faker.number().digits(8);
        String dniFaker = faker.number().digits(8);
        String generoFaker = faker.options().option("Masculino", "Femenino");

        infoPersonal.editarNombres(nombreFaker);
        infoPersonal.editarDNI(dniFaker);
        infoPersonal.editarApellidos(apellidoFaker);
        infoPersonal.seleccionarGenero(generoFaker);
        infoPersonal.editarCelular(celularFaker);

    }

    @Then("Verifico que se han guardado los cambios")
    public void verificoQueSeHanGuardadoLosCambios() {
        infoPersonal.guardarCambios();
    }


    @And("Restauro los datos originales")
    public void restauroLosDatosOriginales() {
        infoPersonal.againMisDatos();
        final String nombre = "Elena Serafina";
        final  String apellido = "Cullanco Espilco";
        final  String numero = "989729659";
        final  String genero = "Femenino";
        final  String dni = "15430124";

        infoPersonal.editarNombres(nombre);
        infoPersonal.editarDNI(dni);
        infoPersonal.editarApellidos(apellido);
        infoPersonal.seleccionarGenero(genero);
        infoPersonal.editarCelular(numero);

    }

    @Then("verifico que los datos originales son correctos")
    public void verificoQueLosDatosOriginalesSonCorrectos() {
        infoPersonal.guardarCambios();

    }
}