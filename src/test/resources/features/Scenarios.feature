Feature: Login al sistema SIAIS
  Background:
    Given Estoy en la página de login

  @loginRoles
  Scenario Outline: Ingreso de login a SIASIS con el rol <rol>
    Given Selecciono el rol <rol>
    And Ingreso mi nombre de usuario y contraseña
    Then Accedo al sistema como <rol>
    Examples:
      | rol |
      | "Directivo"                     |
      | "Profesor (Primaria)"           |
      | "Auxiliar"                      |
      | "Profesor/Tutor (Secundaria)"   |
      | "Otro"                          |

  @editarPersonalDirectivo
  Scenario: Editar perfil con el rol Directivo
    Given Selecciono el rol "Directivo"
    And Ingreso mi nombre de usuario y contraseña
    Then Accedo al sistema como "Directivo"
    When Estoy en la página de edición de perfil
    And Edito mis datos personales con los valores aleatorios
    Then Verifico que se han guardado los cambios
    And Restauro los datos originales
    Then verifico que los datos originales son correctos

