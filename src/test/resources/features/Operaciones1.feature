Feature: Login al sistema SIAIS

  Scenario Outline: Ingreso de login a SIASIS con el rol <rol>
    Given Estoy en la página de login
    When Selecciono el rol <rol>
    And Ingreso mi nombre de usuario y contraseña
    Then Accedo al sistema como <rol>
    Examples:
      | rol |
      | "Directivo"                     |
      | "Profesor (Primaria)"           |
      | "Auxiliar"                      |
      | "Profesor/Tutor (Secundaria)"   |
      | "Otro"                          |


