# erp-proj

----------

## Índice

1. [Detalles del proyecto](#detalles-del-proyecto)

   1.1. [Entorno de desarrollo](#entorno-de-desarrollo)

   1.2. [Tecnologías](#tecnologías)

   1.3. [Plugins](#plugins)

   1.4. [Librerías de terceros](#librerías-de-terceros)

2. [Requisitos](#requisitos)

   2.1. [Software](#software)

   2.2. [Variables de entorno](#software)

   2.3. [Generación de certificado autofirmado y Java KeyStore](#generación-de-certificado-autofirmado-y-java-keystore)

3. [Compilación y empaquetado](#compilación-y-empaquetado)

4. [Ejecución de la aplicación](#ejecución-de-la-aplicación)

## Detalles del proyecto

### **Entorno de desarrollo**

- Eclipse IDE for Java Developers 2024-03
- JDK 21
- Maven 3.9.6
- SceneBuilder
- Jaspersoft Studio
- Windows SDK 10.0.26100.0

### **Tecnologías**

| Tecnología       | Versión      | Descripción                                                  |
| ---------------- | ------------ | ------------------------------------------------------------ |
| Java SDK         | 21           | Desarrollo de aplicaciones Java                              |
| Maven            | 3.9.6        | Gestión y automatización de construcción de proyecto         |
| JavaFX           | 21           | Framework de desarrollo de aplicaciones de escritorio        |
| Spring Framework | 6.1.14       | Inyección de dependencias                                    |
| Hibernate        | 6.5.3.Final  | Mapeo de objetos a modelo relacional para Java               |
| MySQL            | 8.0.40       | Base de datos relacional                                     |
| SceneBuilder     | 21.0.0       | Desarrollo de interfaces gráficas en FXML                    |
| NSIS             | 3.09         | Generación de instalador universal para Windows              |
| Windows SDK      | 10.0.26100.0 | Herramientas para desarrollo de aplicaciones en sistemas Windows |

### **Plugins**

| Nombre de plugin        | Grupo                               | Versión | Finalidad                                             |
| ----------------------- | ----------------------------------- | ------- | ----------------------------------------------------- |
| maven-compiler-plugin   | org.apache.maven.plugins            | 3.13.0  | Compilador del proyecto durante fase de empaquetado   |
| maven-jar-plugin        | org.apache.maven.plugins            | 3.4.2   | Empaquetado de aplicación en JAR                      |
| maven-dependency-plugin | org.apache.maven.plugins            | 3.8.0   | Empaquetado de dependencias del proyecto              |
| maven-shade-plugin      | org.apache.maven.plugins            | 3.6.0   | Empaquetado de dependencias en un mismo JAR           |
| launch4j-maven-plugin   | com.akathist.maven.plugins.launch4j | 2.5.2   | Generación de ejecutable de la aplicación             |
| exec-maven-plugin       | org.codehaus.mojo                   | 3.0.0   | Ejecución de comandos del sistema durante empaquetado |

### **Librerías de terceros**

| Librería    | Grupo             | Versión | Finalidad                                                    |
| ----------- | ----------------- | ------- | ------------------------------------------------------------ |
| ValidatorFX | net.synedra       | 0.5.1   | Validadores para controles de JavaFX                         |
| TilesFX     | eu.hansolo        | 21.0.9  | Componentes estéticos para representar información al estilo card |
| CSSFX       | fr.brouillard.oss | 11.5.1  | Depuración y modificación de CSS durante tiempo de ejecución de la aplicación |
| ControlsFX  | org.controlsfx    | 11.2.1  | Componentes de control complejos para JavaFX                 |

------------

## Requisitos

### Software

- Descargar e instalar [Java Development Kit 21](https://www.oracle.com/es/java/technologies/downloads/) o superior.
- Descargar [MySQL 8.0.40 Installer](https://dev.mysql.com/downloads/installer/) e instalar las siguientes herramientas: `MySQL Workbench, MySQL Shell, MySQL Server, MySQL Router, MySQL Documentation`
- Descargar e instalar [SceneBuilder](https://gluonhq.com/products/scene-builder/#download) para la creación y modificación de vistas de JavaFX (archivos `.fxml`)
- Descargar e instalar [Jaspersoft Studio](https://community.jaspersoft.com/download-jaspersoft/download-jaspersoft/) para la edición de plantillas de informe (archivos `.jrxml`)
- Descargar e instalar el conjunto de herramientas de desarrollo para aplicaciones de escritorio Windows: [Windows SDK](https://developer.microsoft.com/en-us/windows/downloads/windows-sdk/)
  - Permitirá la inclusión de firma digital en el ejecutable generado para la aplicación

### Variables de entorno

Las siguientes variables de entorno son requeridas para la configuración actual del proyecto

| Variable | Adiciones                                                    | Objetivo                                                     |
| :------- | :----------------------------------------------------------- | :----------------------------------------------------------- |
| Path     | Ruta de Windows SDK `C:\Program Files (x86)\Windows Kits\10\bin\10.0.26100.0\x64` | Permitir a Maven utilizar la herramienta `signtool.exe`  para la firma del ejecutable que se genera durante la compilación del proyecto |

### Generación de certificado autofirmado y Java KeyStore

Se hará uso de la herramienta `keytool` incluida en el JDK para la generación de un certificado autofirmado.

Con el siguiente comando, generamos un par de claves (pública y privada) , establece algoritmo RSA con una longitud de 2048 bits y validez de 1 año y las almacenamos en un archivo de almacén de claves (`keystore`) en formato `.jks` (Java KeyStore)

```shell
keytool -genkeypair -alias hostmanagerkey -keyalg RSA -keysize 2048 -validity 365 -keystore hostmanager_keystore.jks
```

Exportación del certificado a .cer, para que pueda ser compartido sin la clave privada para establecer confianza con los sistemas

```shell
keytool -export -alias hostmanagerkey -file hostmanager.cer -keystore hostmanager_keystore.jks
```

-----

## Compilación y empaquetado

Debe apuntar a la ruta raíz donde se encuentra el `pom.xml` del proyecto, abrir un terminal y ejecutar el comando de Maven:

```shell
mvn clean install
```

El ciclo de construcción del proyecto Maven se compone de las siguientes fases:

1. Fase de compilación: Se compilan todas los recursos que componen el proyecto
2. Fase de empaquetado de aplicación: Se especifican detalles tales como el prefijo de `classpath` a utilizar en el ejecutable final, almacenado de dependencias en directorio `/lib`, empaquetado conjunto de todos los recursos que componen el proyecto en un único `.jar` (`HostManager-<version>-shaded.jar`) y empaquetado ligero de los archivos fuentes del proyecto, que será la aplicación final.
3. Fase de generación de ejecutable: Durante esta fase se genera un `.exe` que apunta al `.jar` de aplicación final que sólo contiene los recursos del proyecto, sin las librerías empaquetadas.  Se especifica un  archivo de manifiesto `.manifest` para que el ejecutable final solicite permisos de administrador al intentar ejecutarlo
4. Fase de firma de ejecutable: El ejecutable generado anteriormente es firmado con el certificado almacenado en el Java KeyStore (`.jks`) del proyecto, el cual es un certificado autofirmado. Este certificado autofirmado es recomendable que lo genere por su cuenta en su sistema para el desarrollo.
5. Fase de generación de instalador: Se reunen los recursos procesados por las fases anteriores y se ejecuta un script `.nsi` para la generación de un instalador universal para Windows y permitir la distribución de la aplicación de forma sencilla.

Una vez ejecutada la compilación correctamente, en el directorio `target/` del proyecto encontrarás todos los recursos.

-------------

## **Ejecución de la aplicación**

Existen diversas formas de ejecutar la aplicación:

- Clonar el repositorio, importar el proyecto Maven dentro de su `workspace` y ejecutar la clase `main` del proyecto (`es.rpjd.app.Main`).

  - En caso de querer aplicar una configuración orientada al desarrollo, deberá indicar el parametro `dev` como argumento del programa. De esta manera, la aplicación al ser ejecutada guardará y buscará sus archivos requeridos en un directorio `/dev`, como por ejemplo:

    - Archivo de configuración de la aplicación (elección de idioma/tema, ajustes preferidos por el usuario)
    - Archivos de propiedades personalizadas: Archivos empleados por la aplicación para el guardado de propiedades que son utilizadas por el usuario

    ![dev_execution_sample_2](/wiki/imgs/dev_execution_sample_2.JPG)

- Ejecutar `HostManager-<version>-shaded.jar` desde cualquier ubicación. Este JAR contiene el empaquetado de clases del proyecto y dependencias en un mismo `.jar`
- Ejecutar `HostManager-<version>.jar` si se encuentra el directorio `/lib` a su mismo nivel. Podría ejecutarlo desde `target/` sin ningún problema, en caso de querer ejecutarlo en otro directorio debe asegurarse de copiar a su vez el directorio `/lib`
- Ejecutar el instalador `HostManagerInstaller_<version>.exe`, instala y ejecutar la aplicación (modo producción)





