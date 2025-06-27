Feature: Edicion de Datos Personales por Rol
  Background:
    Given Estoy en la página de login

  @SIASIS-TC-2 @EdicionDatosPersonales @Directivo
  Scenario: Editar perfil con el rol Directivo
    Given Selecciono el rol DIRECTIVO
    And Ingreso mi nombre de usuario y contraseña
    Then Accedo al sistema como DIRECTIVO
    When Estoy en la página de edición de perfil
    And Edito mis datos personales con los valores aleatorios
    Then Verifico que se han guardado los cambios
    And Restauro los datos originales
    Then verifico que los datos originales son correctos

