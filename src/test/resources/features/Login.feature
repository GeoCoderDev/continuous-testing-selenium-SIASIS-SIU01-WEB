Feature: Inicio de Sesión en el sistema SIASIS
  Como usuario del sistema SIASIS
  Quiero poder iniciar sesión con diferentes roles
  Para acceder a las funcionalidades correspondientes a mi rol

  Background:
    Given Estoy en la página de login

  @SIASIS-TC-1 @happy @login @auth @regresion @smoke @SIU01 @API01 @API02 @RDP02 @RDP03 @todos-los-roles
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

    @directivo
    Examples:
      | rol       |
      | DIRECTIVO |

    @profesor-primaria
    Examples:
      | rol               |
      | PROFESOR_PRIMARIA |

    @auxiliar
    Examples:
      | rol      |
      | AUXILIAR |

    @profesor-secundaria
    Examples:
      | rol                 |
      | PROFESOR_SECUNDARIA |

    @tutor
    Examples:
      | rol   |
      | TUTOR |

    @tutor
    Examples:
      | rol   |
      | TUTOR |

    @personal-administrativo
    Examples:
      | rol                     |
      | PERSONAL_ADMINISTRATIVO |




