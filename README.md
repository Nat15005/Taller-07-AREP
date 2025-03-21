# 📌 Tarea de Microservicios - Twitter API

## Descripción

Este proyecto implementa una API para permitir a los usuarios realizar publicaciones, similar a Twitter. La aplicación fue inicialmente desarrollada como un monolito en Spring Boot y luego se refactorizó en una arquitectura basada en microservicios con AWS Lambda.

---

## Características

✅ Registro y autenticación de usuarios.

✅ Publicación de posts.

✅ Almacenamiento de posts en un stream único.

✅ Interfaz web en JavaScript desplegada en AWS S3.

✅ Seguridad implementada con JWT usando Firebase Authentication.

✅ Arquitectura basada en microservicios desplegada en AWS Lambda.

---

## Arquitectura

El sistema está compuesto por los siguientes componentes:

1. **Monolito Spring Boot**:
    - Inicialmente, el sistema se desarrolló como un monolito que maneja las entidades `Usuario`, `Post` y `Stream`.
    - El monolito se despliega en un servidor local o en un servicio como AWS Elastic Beanstalk.
2. **Microservicios**:
    - El monolito se divide en tres microservicios independientes:
        - **Servicio de Usuarios**: Maneja la creación y autenticación de usuarios.
        - **Servicio de Posts**: Permite a los usuarios publicar posts de 140 caracteres.
        - **Servicio de Stream**: Mantiene un hilo único de todos los posts publicados.
    - Cada microservicio se despliega en **AWS Lambda** y se comunica a través de **API Gateway**.
3. **Aplicación Web**:
    - Una aplicación web desarrollada en **JavaScript** que consume los servicios desplegados en AWS Lambda.
    - La aplicación se despliega en **Amazon S3** y está disponible públicamente en internet.
4. **Seguridad**:
    - Se implementa autenticación y autorización usando **JWT** (JSON Web Tokens) con **AWS Cognito** o Firebase.

**Diagrama de Arquitectura:**

![Taller07AREP drawio](https://github.com/user-attachments/assets/3253f048-bc8f-47d8-b515-b4f77a9c85da)

---
## **Entidades del Sistema**

El sistema está basado en tres entidades principales:

1. **Usuario**:
    - Representa a un usuario registrado en la plataforma.
    - Atributos: `id`, `username`, `email`, `password`.
2. **Post**:
    - Representa un mensaje de 140 caracteres publicado por un usuario.
    - Atributos: `id`, `content`, `userId`, `streamId`.
3. **Stream**:
    - Representa un hilo único que contiene todos los posts publicados.
    - Atributos: `id`, `posts`.
      
---

## Videos Demostrativos de los Pasos Solicitados para el Desarrollo

1. Diseñe un API y cree un monolito Spring que permita a los usuarios hacer posts de 140 caracteres e ir registrandolos en un stream único de posts (a la Twitter). Piense en tres entidades Usuario, hilo(stream), posts.
  
  https://github.com/user-attachments/assets/57da2eca-0929-4f1f-b73e-9149bddee055
  
  https://github.com/user-attachments/assets/ed760576-c960-4249-b9c3-3d5ab1de2c7c
  
  https://github.com/user-attachments/assets/e8ebf0a0-009c-49dc-8699-1caef0796fce


2. Cree un aplicación JS para usar el servicio. Depliegue la aplicación en S3. Asegúrese que esté disponible sobre internet.
  
  https://github.com/user-attachments/assets/abb897c4-138e-4e92-9444-7668533e3013

3. Pruebe la aplicación Web

4. Agregue seguridad usando JWT con el servicio cognito de AWS o otra tecnología.

  https://github.com/user-attachments/assets/1b8e6998-51c8-401f-815d-d8d3eb695b92

6. Separe el monolito en tres microservicios independientes usando lambda.
7. Despliegue el servicio en AWS lambda

---

## **Despliegue del Monolito**

El monolito se desarrolló utilizando **Spring Boot** y se desplegó inicialmente en un servidor local o en **AWS Elastic Beanstalk**. A continuación, se detallan los pasos para desplegar el monolito:

1. **Clonar el repositorio**:
    
    ```bash
    git clone https://github.com/Nat15005/Taller-07-AREP.git
    cd Taller-07-AREP
    ```
    
2. **Compilar y ejecutar el monolito**:
    
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```
    
3. **Acceder a la API**:
    - La API estará disponible en `http://localhost:8080`.

---

## **Despliegue en AWS Lambda**

Los microservicios se desplegaron en **AWS Lambda** utilizando el adaptador **aws-serverless-java-container**. A continuación, se detallan los pasos para desplegar un microservicio:

1. **Empaquetar el microservicio**:
    
    ```bash
    mvn clean package
    ```
    
2. **Subir el archivo JAR a AWS Lambda**:
    - Sube el archivo JAR generado (`target/microservicio.jar`) a AWS Lambda.
3. **Configurar el manejador**:
    - Configura el manejador (`Handler`) para que apunte a la clase principal del microservicio (por ejemplo, `arep.twitter.UserLambdaHandler`).
4. **Configurar API Gateway**:
    - Crea un API Gateway para exponer los endpoints del microservicio.

---

## **Separación en Microservicios**

El monolito se dividió en tres microservicios independientes:

1. **Servicio de Usuarios**:
    - Maneja la creación y autenticación de usuarios.
    - Desplegado en AWS Lambda.
2. **Servicio de Posts**:
    - Permite a los usuarios publicar posts de 140 caracteres.
    - Desplegado en AWS Lambda.
3. **Servicio de Stream**:
    - Mantiene un hilo único de todos los posts publicados.
    - Desplegado en AWS Lambda.

Cada microservicio se comunica a través de **API Gateway** y utiliza **AWS Lambda** para ejecutar la lógica de negocio.

---

## **Aplicación Web**

La aplicación web se desarrolló en **JavaScript** y se desplegó en **Amazon S3**. A continuación, se detallan los pasos para desplegar la aplicación:

1. **Subir los archivos estáticos S3**:
    - Sube los archivos generados (`build/`) a un bucket de S3.
2. **Habilitar el acceso público**:
    - Configura el bucket de S3 para que sea accesible públicamente.
3. **Acceder a la aplicación**:
    - La aplicación estará disponible en la URL proporcionada por S3 (por ejemplo, `http://tu-bucket.s3-website-us-east-1.amazonaws.com`).

---

## **Pruebas**

Se realizaron pruebas unitarias y de integración para cada componente del sistema. A continuación, se detallan los tipos de pruebas realizadas:

1. **Pruebas Unitarias**:
    - Se utilizó **JUnit 5** y **Mockito** para probar las clases y métodos individuales.
2. **Pruebas de Integración**:
    - Se probó la integración entre los microservicios y la aplicación web.
3. **Pruebas de Carga**:
    - Se utilizó **Apache JMeter** para simular múltiples usuarios y verificar el rendimiento del sistema.

---

## **Seguridad**

Se implementó seguridad utilizando **JWT** (JSON Web Tokens) con **AWS Cognito** o Firebase. A continuación, se detallan los pasos para configurar la seguridad:

1. **Configurar AWS Cognito**:
    - Crea un User Pool en AWS Cognito.
    - Configura los clientes y permisos necesarios.
2. **Integrar JWT en Spring Boot**:
    - Configura Spring Security para validar los tokens JWT.
3. **Proteger los endpoints**:
    - Asegura los endpoints de la API utilizando anotaciones como `@PreAuthorize`.
---

##  Pruebas

Se realizaron pruebas unitarias y de integración para verificar el correcto funcionamiento del sistema. 

![image](https://github.com/user-attachments/assets/29bedff8-3293-41e3-b395-57db6f522614)

```bash

mvn test

```

![image](https://github.com/user-attachments/assets/6be09131-77fa-4828-b2ad-d3323b3f5b09)

---

## Video de Demostración Microservicios

---

## 👨‍💻 Equipo de Desarrollo

- **Ana Maria Duran Burgos** - [GitHub](https://github.com/AnaDuranB)
- **Laura Natalia Rojas Robayo** - [GitHub](https://github.com/Nat15005)
- **Yeferson Estiben Mesa Vargas** - [GitHub](https://github.com/JffMv)
