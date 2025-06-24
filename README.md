# Continuous Testing Selenium SIASIS-SIU01-WEB

Este es el repositorio de un proyecto de automatización web utilizando **Selenium**.

## Instrucciones para trabajar localmente

Para trabajar con este proyecto en tu entorno local, debes seguir estos pasos:

1. **Configurar el archivo `.env`**

   Es necesario agregar un archivo llamado `.env` en la raíz del proyecto. Este archivo debe contener las siguientes variables de entorno:

   ```env
   DEV_SIASIS_URL="ruta_del_proyecto"
   DIRECTIVO_USERNAME="usuario_del_directivo"
   DIRECTIVO_PASSWORD="contraseña_del_directivo"
   PROFESOR__PRIMARIA__USERNAME="usuario_profesor_primaria"
   PROFESOR__PRIMARIA__PASSWORD="contraseña_profesor_primaria"
   PROFESOR_TUTOR__SECUNDARIA__USERNAME="usuario_profesor_tutor_secundaria"
   PROFESOR_TUTOR__SECUNDARIA__PASSWORD="contraseña_profesor_tutor_secundaria"
   AUXILIAR_USERNAME="usuario_auxiliar"
   AUXILIAR_PASSWORD="contraseña_auxiliar"
   OTRO_USERNAME="usuario_otro"
   OTRO_PASSWORD="contraseña_otro"

2. **Ejecutar los tests**

   Para ejecutar los tests, utiliza el siguiente comando:

   ```bash
   mvn clean test
   ```

   Si deseas ejecutar un test específico

  ```bash
  mvn clean test "-Dcucumber.filter.tags=@Tags"

