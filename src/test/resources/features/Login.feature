Feature: Inicio de Sesión en el sistema SIASIS
  Como usuario del sistema SIASIS
  Quiero poder iniciar sesión con diferentes roles
  Para acceder a las funcionalidades correspondientes a mi rol

  Background:
    Given Estoy en la página de login

  @SIASIS-TC-1 @happy @login @auth @regresion @smoke @SIU01 @todos-los-roles
  Scenario Outline: Inicio de sesion al sistema con el rol <rol>
    Given Selecciono el rol <rol>
    And Ingreso mi nombre de usuario y contraseña
    Then Accedo al sistema como <rol>

    @SIASIS-TC-2 @directivo @personal-colegio @API01 @RDP02
    Examples:
      | rol       |
      | DIRECTIVO |

    @SIASIS-TC-3 @profesor-primaria @personal-colegio @API01 @RDP02
    Examples:
      | rol               |
      | PROFESOR_PRIMARIA |

    @SIASIS-TC-4 @auxiliar @personal-colegio @API01 @RDP02
    Examples:
      | rol      |
      | AUXILIAR |

    @SIASIS-TC-5 @profesor-secundaria @personal-colegio @API01 @RDP02
    Examples:
      | rol                 |
      | PROFESOR_SECUNDARIA |

    @SIASIS-TC-6 @tutor @personal-colegio @API01 @RDP02
    Examples:
      | rol   |
      | TUTOR |

    @SIASIS-TC-7 @personal-administrativo @personal-colegio @API01 @RDP02
    Examples:
      | rol                     |
      | PERSONAL_ADMINISTRATIVO |




