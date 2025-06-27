Feature: Login al sistema SIASIS
  Como usuario del sistema SIASIS
  Quiero poder iniciar sesión con diferentes roles
  Para acceder a las funcionalidades correspondientes a mi rol

  Background:
    Given Estoy en la página de login

  @SIASIS-TC-1 @Login @SmokeTest
  Scenario Outline: Ingreso de login a SIASIS con el rol <rol>
    Given Selecciono el rol <rol>
    And Ingreso mi nombre de usuario y contraseña
    Then Accedo al sistema como <rol>

    Examples:
      | rol                     |
      | DIRECTIVO               |
      | PROFESOR_PRIMARIA       |
      | AUXILIAR                |
      | PROFESOR_SECUNDARIA     |
      | TUTOR                   |
      | PERSONAL_ADMINISTRATIVO |