Feature: Edicion de Datos Personales por Rol
  Como usuario con rol de Directivo
  Quiero poder consultar las asistencia de cualquier personal del colegio
  Para poder evaluar el desempeño del personal del colegio

  Background:
    Given Estoy en la página de login

  @SIASIS-TC-8 @happy @edicion-datos-personales-propios @SIU01 @API01 @RDP02 @directivo
  Scenario: Directivo edita sus propios datos personales
    Given Selecciono el rol DIRECTIVO
    And Ingreso mi nombre de usuario y contraseña
    Then Accedo al sistema como DIRECTIVO
    When Estoy en la página de edición de perfil
    And Edito mis datos personales con los valores aleatorios
    Then Verifico que se han guardado los cambios
    And Restauro los datos originales
    Then verifico que los datos originales son correctos
