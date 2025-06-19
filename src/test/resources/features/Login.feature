Feature: Login al sistema SIAIS
  Background:
    Given Estoy en la página de login

  @Login
  Scenario Outline: Ingreso de login a SIASIS con el rol <rol>
    Given Selecciono el rol <rol>
    And Ingreso mi nombre de usuario y contraseña
    Then Accedo al sistema como <rol>
    Examples:
      | rol                           |
      | "Directivo"                   |
      | "Profesor (Primaria)"         |
      | "Auxiliar"                    |
      | "Profesor/Tutor (Secundaria)" |
      | "Otro"                        |
