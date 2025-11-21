Feature: Edicion de Datos Personales por Rol
  Como usuario del sistema SIASIS
  Quiero poder modificar mis datos personales
  Para mantener actualizada mi informacion en el sistema

  Background:
    Given Estoy en la página de login

  @test1
  Scenario Outline: Validar que el rol de DIRECTIVO puede ver los registros de asistencia del personal
    Given Selecciono el rol DIRECTIVO
    And Ingreso mi nombre de usuario y contraseña
    Then Accedo al sistema como DIRECTIVO
    When doy click en el apartado de Registro de Personal
    And selecciono "<tipo_personal>"
    And completo el campo de búsqueda con datos válidos del personal
    And doy click en el boton de Buscar
    Then se muestra una tabla con las asistencias del personal seleccionado

    Examples:
      | tipo_personal           |
      | Profesor de Primaria    |
      #| Profesor de Secundaria  |
      #| Auxiliar                |
      #| Personal Administrativo |

