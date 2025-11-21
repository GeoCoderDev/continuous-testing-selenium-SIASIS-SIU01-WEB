Feature: Edicion de Datos Personales por Rol
  Como usuario del sistema SIASIS
  Quiero poder modificar mis datos personales
  Para mantener actualizada mi informacion en el sistema

  Background:
    Given Estoy en la página de login

  @test1
  Scenario: Directivo edita sus propios datos personales
    Given Selecciono el rol DIRECTIVO
    And Ingreso mi nombre de usuario y contraseña
    Then Accedo al sistema como DIRECTIVO
    When doy click en el apartado de Registro de Personal
    And selecciono un profesor de primaria
    And doy click en el boton de Buscar
    Then se muestra una tabla con las asistencias del personal seleccionado

