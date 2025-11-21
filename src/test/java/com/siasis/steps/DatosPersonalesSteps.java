package com.siasis.steps;

import com.siasis.pages.DatosPersonalesPage;
import net.datafaker.Faker;
import org.openqa.selenium.WebDriver;

public class DatosPersonalesSteps {
    private final DatosPersonalesPage datosPersonalesPage;
    private final Faker faker;
    private String lastEditedDNI;

    // Datos originales para restaurar
    private static final String ORIGINAL_NAME = "Elena Serafina";
    private static final String ORIGINAL_LASTNAME = "Cullanco Espilco";
    private static final String ORIGINAL_PHONE = "989729659";
    private static final String ORIGINAL_GENDER = "Femenino";
    private static final String ORIGINAL_DNI = "15430124";

    public DatosPersonalesSteps(WebDriver driver) {
        this.datosPersonalesPage = new DatosPersonalesPage(driver);
        this.faker = new Faker();
    }

        public void validarTablaAsistenciasVisible() {
            try {
                boolean tablaVisible = datosPersonalesPage.isTablaAsistenciasVisible();
                if (!tablaVisible) {
                    throw new AssertionError("La tabla de asistencias no está visible o no tiene los encabezados esperados.");
                }
                System.out.println("✅ Tabla de asistencias visible y correcta");
            } catch (Exception e) {
                throw new AssertionError("Error al validar la tabla de asistencias: " + e.getMessage());
            }
        }

    public void navigateToEditProfile() {
        try {
            datosPersonalesPage.navigateToEditProfile();
        } catch (Exception e) {
            throw new AssertionError("No se pudo navegar a la página de edición de perfil: " + e.getMessage());
        }
    }

    public void editDNI(String dni) {
        try {
            datosPersonalesPage.editDNI(dni);
            this.lastEditedDNI = dni;
        } catch (Exception e) {
            throw new AssertionError("Error al editar DNI: " + e.getMessage());
        }
    }

    public void editNames(String nombres) {
        try {
            datosPersonalesPage.editNames(nombres);
        } catch (Exception e) {
            throw new AssertionError("Error al editar nombres: " + e.getMessage());
        }
    }

    public void editLastNames(String apellidos) {
        try {
            datosPersonalesPage.editLastNames(apellidos);
        } catch (Exception e) {
            throw new AssertionError("Error al editar apellidos: " + e.getMessage());
        }
    }

    public void editPhone(String celular) {
        try {
            datosPersonalesPage.editPhone(celular);
        } catch (Exception e) {
            throw new AssertionError("Error al editar celular: " + e.getMessage());
        }
    }

    public void selectGender(String genero) {
        try {
            datosPersonalesPage.selectGender(genero);
        } catch (Exception e) {
            throw new AssertionError("Error al seleccionar género: " + e.getMessage());
        }
    }

    public void editPersonalDataWithRandomValues() {
        try {
            String nombreFaker = faker.name().firstName();
            String apellidoFaker = faker.name().lastName();
            String celularFaker = "9" + faker.number().digits(8);
            String dniFaker = faker.number().digits(8);
            String generoFaker = faker.options().option("Masculino", "Femenino");

            editNames(nombreFaker);
            editDNI(dniFaker);
            editLastNames(apellidoFaker);
            selectGender(generoFaker);
            editPhone(celularFaker);

            System.out.println("Datos aleatorios generados:");
            System.out.println("Nombre: " + nombreFaker);
            System.out.println("Apellido: " + apellidoFaker);
            System.out.println("DNI: " + dniFaker);
            System.out.println("Género: " + generoFaker);
            System.out.println("Celular: " + celularFaker);

        } catch (Exception e) {
            throw new AssertionError("Error al editar datos personales con valores aleatorios: " + e.getMessage());
        }
    }

    public void saveChanges() {
        try {
            datosPersonalesPage.saveChanges();
        } catch (Exception e) {
            throw new AssertionError("Error al guardar cambios: " + e.getMessage());
        }
    }

    public void verifyChangesSaved() {
        try {
            if (lastEditedDNI != null) {
                boolean isVerified = datosPersonalesPage.verifyDNIDisplayed(lastEditedDNI);
                if (!isVerified) {
                    throw new AssertionError("Los cambios no se guardaron correctamente. DNI esperado: " + lastEditedDNI);
                }
                System.out.println("✅ Cambios guardados correctamente. DNI verificado: " + lastEditedDNI);
            }
        } catch (Exception e) {
            throw new AssertionError("Error al verificar que los cambios se guardaron: " + e.getMessage());
        }
    }

    public void restoreOriginalData() {
        try {
            datosPersonalesPage.clickEditButton();
            editNames(ORIGINAL_NAME);
            editDNI(ORIGINAL_DNI);
            editLastNames(ORIGINAL_LASTNAME);
            selectGender(ORIGINAL_GENDER);
            editPhone(ORIGINAL_PHONE);

            System.out.println("Datos originales restaurados:");
            System.out.println("Nombre: " + ORIGINAL_NAME);
            System.out.println("Apellido: " + ORIGINAL_LASTNAME);
            System.out.println("DNI: " + ORIGINAL_DNI);
            System.out.println("Género: " + ORIGINAL_GENDER);
            System.out.println("Celular: " + ORIGINAL_PHONE);

        } catch (Exception e) {
            throw new AssertionError("Error al restaurar datos originales: " + e.getMessage());
        }
    }

    public void verifyOriginalDataRestored() {
        try {
            boolean isVerified = datosPersonalesPage.verifyDNIDisplayed(ORIGINAL_DNI);
            if (!isVerified) {
                throw new AssertionError("Los datos originales no se restauraron correctamente. DNI esperado: " + ORIGINAL_DNI);
            }
            System.out.println("✅ Datos originales restaurados correctamente. DNI verificado: " + ORIGINAL_DNI);

        } catch (Exception e) {
            throw new AssertionError("Error al verificar que los datos originales son correctos: " + e.getMessage());
        }
    }

    public void clickRegistroPersonal() {
        try {
            datosPersonalesPage.clickRegistroPersonal();
        } catch (Exception e) {
            throw new AssertionError("No se pudo hacer click en el apartado de Registro de Personal: " + e.getMessage());
        }
    }

    public void seleccionarProfesorDePrimaria() {
        try {
            // 1. Seleccionar "Profesor de Primaria" en el select de tipo de personal
            datosPersonalesPage.seleccionarTipoPersonal("Profesor de Primaria");
            // 2. Click en el div de "Seleccionar Profesor de Primaria"
            datosPersonalesPage.clickSeleccionarProfesorDePrimaria();
            // 3. Seleccionar el primer elemento de la lista
            datosPersonalesPage.seleccionarPrimerProfesorDeLista();
            // 4. Seleccionar aleatoriamente un mes superior a junio
            datosPersonalesPage.seleccionarMesAleatorioMayorAJunio();

            // 5. Click en el botón de Buscar
        } catch (Exception e) {
            throw new AssertionError("Error en la selección de profesor de primaria y mes: " + e.getMessage());
        }
    }

    public void clickBotonBuscar() {
        try {
            datosPersonalesPage.clickBotonBuscar();
            datosPersonalesPage.clickBotonBuscar();
        } catch (Exception e) {
            throw new AssertionError("Error al hacer click en el botón Buscar: " + e.getMessage());
        }
    }

    public void seleccionarTipoPersonal(String tipoPersonal) {
        try {
            datosPersonalesPage.seleccionarTipoPersonal(tipoPersonal);
        } catch (Exception e) {
            throw new AssertionError("Error al seleccionar tipo de personal: " + tipoPersonal + ". " + e.getMessage());
        }
    }

    public void completarBusquedaPersonal(String tipoPersonal) {
        try {
            // Seleccionar el div correcto según el tipo de personal
            String textoDiv = "";
            switch (tipoPersonal) {
                case "Profesor de Primaria":
                    textoDiv = "Seleccionar Profesor de Primaria";
                    break;
                case "Profesor de Secundaria":
                    textoDiv = "Seleccionar Profesor de Secundaria";
                    break;
                case "Auxiliar":
                    textoDiv = "Seleccionar Auxiliar";
                    break;
                case "Personal Administrativo":
                    textoDiv = "Seleccionar Personal Administrativo";
                    break;
                default:
                    throw new AssertionError("Tipo de personal no soportado: " + tipoPersonal);
            }
            datosPersonalesPage.clickDivSeleccionarPersonalPorTexto(textoDiv);
            datosPersonalesPage.seleccionarPrimerProfesorDeLista();
            datosPersonalesPage.seleccionarMesAleatorioMayorAJunio();
        } catch (Exception e) {
            throw new AssertionError("Error al completar el campo de búsqueda del personal: " + e.getMessage());
        }
    }
}