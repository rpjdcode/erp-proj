# erp-proj

Aplicación de escritorio JavaFX 21, desarrollada principalmente para ser usada en Windows. Al final de su desarrollo se valorará la comprobación de compatibilidades con sistemas linux

----------

## **Entorno de desarrollo**

- Eclipse IDE for Java Developers 2024-03
- JDK 21
- Maven 3.9.6
- SceneBuilder
- Windows 10

---------

## **Tecnologías**

| Tecnología       | Versión     | Descripción                                           |
| ---------------- | ----------- | ----------------------------------------------------- |
| Java SDK         | 21          | Desarrollo de aplicaciones Java                       |
| Maven            | 3.9.6       | Gestión y automatización de construcción de proyecto  |
| JavaFX           | 21          | Framework de desarrollo de aplicaciones de escritorio |
| Spring Framework | 6.1.14      | Inyección de dependencias                             |
| Hibernate        | 6.5.3.Final | Mapeo de objetos a modelo relacional para Java        |
| MySQL            | 8.0.26      | Base de datos relacional                              |
| SceneBuilder     | 21.0.0      | Desarrollo de interfaces gráficas en FXML             |
| NSIS             | 3.09        | Generación de instalador universal para Windows       |

--------------

## **Plugins**

| Nombre de plugin        | Grupo                               | Versión | Finalidad                                                    |
| ----------------------- | ----------------------------------- | ------- | ------------------------------------------------------------ |
| maven-compiler-plugin   | org.apache.maven.plugins            | 3.13.0  | Compilador del proyecto durante fase de empaquetado          |
| maven-jar-plugin        | org.apache.maven.plugins            | 3.4.2   | Empaquetado de aplicación en JAR                             |
| maven-dependency-plugin | org.apache.maven.plugins            | 3.8.0   | Empaquetado de dependencias del proyecto                     |
| maven-shade-plugin      | org.apache.maven.plugins            | 3.6.0   | Empaquetado de dependencias en un mismo JAR                  |
| launch4j-maven-plugin   | com.akathist.maven.plugins.launch4j | 2.5.2   | Generación de ejecutable de la aplicación                    |
| exec-maven-plugin       | org.codehaus.mojo                   | 3.0.0   | Ejecución de comandos durante fase de empaquetado, utilizado para la invocación del script de NSIS que generará el instalador en base a los recursos compilados del proyecto |

---------

## **Librerías de terceros**

| Librería    | Grupo             | Versión | Finalidad                                                    |
| ----------- | ----------------- | ------- | ------------------------------------------------------------ |
| ValidatorFX | net.synedra       | 0.5.1   | Validadores para controles de JavaFX                         |
| TilesFX     | eu.hansolo        | 21.0.9  | Componentes estéticos para representar información al estilo card |
| CSSFX       | fr.brouillard.oss | 11.5.1  | Depuración y modificación de CSS durante tiempo de ejecución de la aplicación |
| ControlsFX  | org.controlsfx    | 11.2.1  | Componentes de control complejos para JavaFX                 |

------------

## **Construcción, compilación y empaquetado del proyecto**

Debe apuntar a la ruta raíz donde se encuentra el `pom.xml` del proyecto, abrir un terminal y ejecutar el comando típico de compilación de Maven:

```shell
mvn clean install
```

![mvn_clean_install](.\wiki\imgs\mvn_clean_install.JPG)

Una vez ejecutada la compilación correctamente, en el directorio `target/` del proyecto encontrará todo lo necesario para ejecutar la aplicación.

![image-20241207160455468](.\wiki\imgs\target_content_sample.jpg)

-------------

## **Ejecución de la aplicación**

Existen diversas formas de ejecutar la aplicación:

- Clonar el repositorio, importar el proyecto Maven dentro de su `workspace` y ejecutar la clase `main` del proyecto (`es.rpjd.app.Main`).

  - En caso de querer aplicar una configuración orientada al desarrollo, deberá indicar el parametro `dev` como argumento del programa. De esta manera, la aplicación al ser ejecutada guardará y buscará sus archivos requeridos en un directorio `/dev`, como por ejemplo:

    - Archivo de configuración de la aplicación (elección de idioma/tema, ajustes preferidos por el usuario)
    - Archivos de propiedades personalizadas: Archivos empleados por la aplicación para el guardado de propiedades que son utilizadas por el usuario

    ![dev_execution_sample_2](.\wiki\imgs\dev_execution_sample_2.JPG)

- Ejecutar `HostManager-<version>-shaded.jar` desde cualquier ubicación. Este JAR contiene el empaquetado de clases del proyecto y dependencias en un mismo `.jar`
- Ejecutar `HostManager-<version>.jar` si se encuentra el directorio `/lib` a su mismo nivel. Podría ejecutarlo desde `target/` sin ningún problema, en caso de querer ejecutarlo en otro directorio debe asegurarse de copiar a su vez el directorio `/lib`
- Ejecutar el instalador `HostManagerInstaller_<version>.exe`, instala y ejecutar la aplicación (modo producción)