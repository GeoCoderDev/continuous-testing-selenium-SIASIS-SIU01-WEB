Feature: Inicio de Sesión en el sistema SIASIS
  Como usuario del sistema SIASIS
  Quiero poder iniciar sesión con diferentes roles
  Para acceder a las funcionalidades correspondientes a mi rol

  Background:
    Given Estoy en la página de login

  @SIASIS-TC-1 @happy @login @auth @regresion @smoke @SIU01 @API02 @RDP03 @todos-los-roles
  Scenario Outline: Ingreso de login a SIASIS con el rol <rol>
    Given Selecciono el rol <rol>
    And Ingreso mi nombre de usuario y contraseña
    Then Accedo al sistema como <rol>

    @SIASIS-TC-3 @directivo @API01 @RDP02
    Examples:
      | rol       |
      | DIRECTIVO |

    @SIASIS-TC-4 @profesor-primaria @API01 @RDP02
    Examples:
      | rol               |
      | PROFESOR_PRIMARIA |

    @SIASIS-TC-5 @auxiliar @API01 @RDP02
    Examples:
      | rol      |
      | AUXILIAR |

    @SIASIS-TC-6 @profesor-secundaria @API01 @RDP02
    Examples:
      | rol                 |
      | PROFESOR_SECUNDARIA |

    @SIASIS-TC-7 @tutor @API01 @RDP02
    Examples:
      | rol   |
      | TUTOR |

    @SIASIS-TC-8 @personal-administrativo @API01 @RDP02
    Examples:
      | rol                     |
      | PERSONAL_ADMINISTRATIVO |




