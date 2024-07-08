# MyTiim Backend 🎯

_CIDENET_   
_Edición: 2023_

<img src="https://github.com/saulolo/MyTiim/blob/20162e7f5c5cb3c17f138399a5f0129e48ae88d9/Portada.png" width="300px" 
alt="Portada de la App">



## Tabla de Contenidos 📚

- [Comenzando 🚀](#comenzando-)
    - [Pre-requisitos 📋](#pre-requisitos-)
    - [Instalación 🔧](#instalación-)
        - [Requisitos Previos](#requisitos-previos)
        - [Clonar el Repositorio](#clonar-el-repositorio)
    - [Despliegue 📦](#despliegue-)
        - [Despliegue Local 🏠](#despliegue-local-)
- [Construido con 🛠️](#construido-con-)
- [Contribuyendo 🤝](#contribuyendo-)
    - [Reglas para Enviar Solicitudes de Extracción (Pull Requests)](#reglas-para-enviar-solicitudes-de-extracción-pull-requests-)
    - [Informar Problemas](#informar-problemas)
    - [Comunicación](#comunicación)
    - [¿Dónde obtener ayuda?](#dónde-obtener-ayuda)
- [Autor ✒️](#autor-)
- [Licencia 📄](#licencia-)
- [Expresiones de Gratitud 🎁](#expresiones-de-gratitud-)
- [Créditos 📜](#créditos-)


## Comenzando 🚀

_Descripción de **MyTiim Backend**_

MyTiim es una plataforma de gestión y seguimiento de objetivos personales y de equipo desarrollada por Cidenet SAS. Esta aplicación permite a los usuarios establecer, monitorear y alcanzar sus metas de manera efectiva y eficiente.

### Pre-requisitos 📋

_Este proyecto requiere ciertos pre-requisitos para su ejecución:_

#### Java Development Kit (JDK)
Debes tener instalado Java Development Kit (JDK) en tu sistema. Este proyecto requiere JDK 17 o una versión superior.
Puedes descargar y configurar JDK desde el sitio oficial de Oracle o OpenJDK:

- [Oracle JDK](https://www.oracle.com/java/technologies/downloads/)
- [OpenJDK](https://openjdk.java.net/)

#### Git
Git es necesario para clonar el repositorio del proyecto y gestionar versiones del código fuente. Puedes descargar Git desde:

- [git-scm.com](https://git-scm.com/).

#### PostgreSQL
Se utiliza PostgreSQL como sistema de gestión de bases de datos. Asegúrate de tener PostgreSQL instalado y configurado para poder ejecutar el proyecto.
- **PostgreSQL:** [postgresql.org](https://www.postgresql.org/).

#### Spring Boot
Spring Boot es utilizado como framework para la construcción de aplicaciones Java. Más información en:
- **Spring Boot:** [spring.io/projects/spring-boot](https://spring.io/projects/spring-boot).

### Instalación 🔧

A continuación, se describen los pasos para configurar y ejecutar este proyecto Java en tu entorno de desarrollo.

#### Requisitos Previos
Antes de comenzar, asegúrate de tener los siguientes requisitos previos en tu sistema:

- **Java Development Kit (JDK):** Debes tener instalado Java Development Kit (JDK) en tu sistema.
  Para verificar si Java está instalado, puedes abrir una terminal y ejecutar el siguiente comando:

   ```shell
   java -version

#### Clonar el Repositorio

Para comenzar, clona este repositorio en tu máquina local usando Git:

```shell
git clone https://github.com/saulolo/MyTiim.git
```

**Nota:**  Asegúrate de que tu sistema tenga configuradas las variables de entorno JAVA_HOME y PATH para que apunten a tu instalación de JDK.


## Despliegue 📦

En esta sección, se proporcionan instrucciones y notas adicionales sobre cómo llevar tu proyecto a un entorno de producción o cómo desplegarlo para su uso.

### Despliegue Local 🏠

Si deseas ejecutar tu proyecto en tu propio entorno local para pruebas o desarrollo, sigue estos pasos generales:

1. **Requisitos Previos**: Asegúrate de que los requisitos previos del proyecto, como JDK y otras dependencias, estén instalados en tu máquina.

2. **Clonación del Repositorio**: Si aún no has clonado el repositorio, sigue las instrucciones en la sección "Clonar el Repositorio" de la [sección de instalación](#clonar-el-repositorio) para obtener una copia local del proyecto.

3. **Configuración de Variables de Entorno**: Asegúrate de que las variables de entorno necesarias, como `JAVA_HOME`, estén configuradas correctamente.

4. **Compilación y Ejecución**: Sigue las instrucciones de la [sección de instalación](#compilación-y-ejecución) para compilar y ejecutar el proyecto.


## Configuración de Variables de Entorno 🌍

Este proyecto utiliza variables de entorno para la configuración de la base de datos. Deberás configurar las siguientes variables de entorno en tu sistema:

- `DATABASE_URL`: La URL de tu base de datos PostgreSQL.
- `DATABASE_USERNAME`: El nombre de usuario de tu base de datos.
- `DATABASE_PASSWORD`: La contraseña de tu base de datos.

Puedes configurar estas variables de entorno en tu sistema operativo o en tu IDE si lo soporta. También puedes crear un archivo `.env` en la raíz de tu proyecto y definir las variables de entorno allí. Asegúrate de no subir este archivo a tu repositorio de código para proteger tus credenciales de base de datos.

## Ejecución de Comandos de Verificación de Código 🛠️

Este proyecto utiliza varias herramientas para verificar la calidad del código. Aquí te dejo cómo puedes ejecutar cada una de ellas:

- **Checkstyle:** Para ejecutar Checkstyle, puedes usar el siguiente comando en tu terminal:

  ```shell
  mvn checkstyle:check -Pci

- **Spotbugs:** Para ejecutar Spotbugs, puedes usar el siguiente comando en tu terminal:

  ```shell
  mvn -Pci spotbugs:check 

- **Pmd:** Para ejecutar Pmd, puedes usar el siguiente comando en tu terminal:

  ```shell
  mvn pmd:check

## Contribuyendo 🖇️

¡Gracias por tu interés en contribuir a este proyecto! Aquí hay algunas reglas y pautas para enviar contribuciones:

### Reglas para Enviar Solicitudes de Extracción (Pull Requests) 🔀

1. Antes de enviar una solicitud de extracción, asegúrate de que tu código esté bien probado y sea compatible con la rama principal.

2. Sigue un estilo de código consistente.

3. Si tu contribución agrega nuevas características, proporciona documentación clara sobre cómo utilizar esas características.

### Informar Problemas 🐞

Si encuentras errores o problemas en el proyecto, por favor, abre un issue para informarnos. Proporciona detalles claros sobre el problema, incluyendo pasos para reproducirlo.

### Comunicación 💬

Me encanta escuchar tus ideas y responder tus preguntas. Siempre puedes [contactarme](https://www.linkedin.com/in/saul-echeverri-duque/) para discutir el proyecto o plantear cualquier pregunta que tengas.

¡Esperamos que te unas a nuestra comunidad de contribuyentes y esperamos tus valiosas contribuciones!



### ¿Dónde obtener ayuda? 🆘

Si tienes preguntas o necesitas ayuda durante el proceso de contribución, no dudes en [contactarme](https://www.linkedin.com/in/saul-echeverri-duque/) o abrir un issue para obtener asistencia.

¡Espero trabajar contigo en este proyecto y agradecemos tu ayuda para mejorarlo!



## Autor ✒️

¡Hola! Soy Saul Echeverri Duque 👨‍💻 , el creador y desarrollador de este proyecto. Permíteme compartir un poco sobre mi formación y experiencia:

### Formación Académica 📚

- 🎓 Graduado en Tecnología en Análisis y Desarrollo de Software e Ingenieria de Alimentos de profesión por la 
  Universidad de Antioquia y el SENA en Colombia.

### Trayectoria Profesional 💼

- 👨‍💻 Cuento con casi dos años de experiencia laboral (a Hoy Julio de 2024) en el campo del desarrollo de software.
- 🌟 Durante mi trayectoria, he tenido el privilegio de trabajar en diversos proyectos tecnológicos, donde he aplicado mis conocimientos en programación y análisis.
- 🏢 Actualmente, formo parte de [Cidenet](https://cidenet.com.co/), una empresa de software en Medellín, Colombia, donde sigo creciendo profesionalmente y contribuyendo al mundo de la tecnología.

### Pasión por la Programación 🚀

- 💻 Mi viaje en el mundo de la programación comenzó en el 2021, y desde entonces, he estado inmerso en el emocionante universo del desarrollo de software.
- 📚 Uno de mis mayores intereses y áreas de enfoque es Java, y este proyecto es el resultado de mi deseo de compartir conocimientos y experiencias relacionadas con este lenguaje.
- 🤝 Estoy emocionado de colaborar y aprender junto a otros entusiastas de Java.

Estoy agradecido por la oportunidad de compartir este proyecto contigo y espero que te sea útil en tu propio camino de aprendizaje y desarrollo. Si tienes alguna pregunta, sugerencia o simplemente quieres charlar sobre tecnología, no dudes en ponerte en contacto conmigo. ¡Disfruta explorando el mundo de Java!

¡Gracias por ser parte de este proyecto! 😊


## Licencia 📄

Este proyecto, MyTiim, es desarrollado por Cidenet SAS y está destinado a la gestión y seguimiento de objetivos personales y de equipo. La utilización del código y los contenidos de este proyecto se realiza en concordancia con los derechos de uso permitidos por los autores y la empresa.

**Nota Importante**: Este proyecto no se distribuye bajo una licencia de código abierto estándar, ya que está destinado principalmente para fines personales y educativos. Si deseas utilizar o distribuir el contenido de este proyecto más allá de los fines permitidos por Cidenet, asegúrate de obtener los permisos necesarios de la empresa.

Es importante respetar los derechos de autor y las restricciones legales asociadas con el contenido desarrollado por Cidenet.


## Expresiones de Gratitud 🎁

Queremos expresar nuestro más sincero agradecimiento a todo el equipo de [Cidenet](https://cidenet.com.co/) por 
su  dedicación, apoyo y compromiso en la creación y desarrollo del proyecto MyTiim.

Si encuentras este proyecto útil y te gustaría expresar tu gratitud de alguna manera, aquí hay algunas opciones:

* Comenta a otros sobre este proyecto 📢: Comparte este proyecto con tus amigos, colegas o en tus redes sociales para que otros también puedan beneficiarse de él.

* Invita una cerveza 🍺 o un café ☕ a alguien del equipo: Siéntete libre de mostrar tu aprecio por el esfuerzo del autor o del único miembro del equipo (yo) comprándoles una bebida virtual.

* Da las gracias públicamente 🤓: Puedes expresar tu agradecimiento públicamente en el repositorio del proyecto, en los comentarios, o incluso en tu blog personal si lo deseas.

* **Dona a través de una cuenta bancaria** 💰: Si prefieres hacer una donación en efectivo o mediante transferencia bancaria, puedes hacerlo a la siguiente cuenta de ahorros en Bancolombia: 2301-5881-151. Tu generosidad será muy apreciada y contribuirá al mantenimiento y mejora de este proyecto.

¡Gracias por ser parte de este viaje de aprendizaje y desarrollo!



---
## Créditos 📜

Este proyecto fue desarrollado con ❤️ por [Cidenet](https://cidenet.com.co/) 😊.

Si tienes preguntas, comentarios o sugerencias, no dudes en ponerte en contacto conmigo:

- GitHub: [Saul Hernando Echeverri Duque](https://github.com/saulolo) 🌐
- Correo Electrónico: saulolo@gmail.com 📧
- LinkedIn: [Saul Echeverri](https://www.linkedin.com/in/saul-echeverri-duque/) 💼
