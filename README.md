# 📌 Tarea de Microservicios - Twitter API

## Description

This project implements an API to allow users to make posts, similar to Twitter. The application was initially developed as a monolith in Spring Boot and later refactored into a microservices architecture using AWS Lambda.
---

## Features

✅ User registration and authentication.

✅ Posting messages.

✅ Storing posts in a single stream.

✅ Web interface in JavaScript deployed on AWS S3.

✅ Security implemented with JWT using Firebase Authentication.

✅ Microservices-based architecture deployed on AWS Lambda.

---

## Architecture

The system consists of the following components:

1. **Spring Boot Monolith**:
    - Initially, the system was developed as a monolith handling the entities User, Post, and Stream.
    - The monolith is deployed on a local server or a service like AWS Elastic Beanstalk.
2. **Microservices**:
    - The monolith is divided into three independent microservices:
        - **User Service**: Handles user creation and authentication.
        - **Post Service**: Allows users to publish 140-character posts.
        - **Stream Service**: Maintains a unique thread of all published posts.
    - Each microservice is deployed on **AWS Lambda** and communicates via **API Gateway**.
3. **Web Application**:
    - A web application developed in **JavaScript** that consumes the services deployed on AWS Lambda.
    - The application is deployed on **Amazon S3** and is publicly available on the internet.
4. **Security**:
    - Authentication and authorization are implemented using **JWT** (JSON Web Tokens) with **AWS Cognito** or Firebase.

**Diagrama de Arquitectura:**

![Taller07AREP drawio](https://github.com/user-attachments/assets/3253f048-bc8f-47d8-b515-b4f77a9c85da)

---
## **Entidades del Sistema**

The system is based on three main entities:

1. **Usuario**:
    - Represents a registered user on the platform.
    - Attributes: `id`, `username`, `email`, `password`.
2. **Post**:
    - Represents a 140-character message published by a user.
    - Attributes: `id`, `content`, `userId`, `streamId`.
3. **Stream**:
    - Represents a unique thread that contains all published posts.
    - Attributes: `id`, `posts`.
      
---

## Demonstration Videos of Development Steps

1. Design an API and create a Spring monolith that allows users to make 140-character posts and register them in a unique post stream (similar to Twitter). Consider three entities: User, Stream, and Post.
  
  https://github.com/user-attachments/assets/57da2eca-0929-4f1f-b73e-9149bddee055
  
  https://github.com/user-attachments/assets/ed760576-c960-4249-b9c3-3d5ab1de2c7c
  
  https://github.com/user-attachments/assets/e8ebf0a0-009c-49dc-8699-1caef0796fce


2. Create a JavaScript application to use the service. Deploy the application on S3. Ensure it is accessible over the internet.
  
  https://github.com/user-attachments/assets/abb897c4-138e-4e92-9444-7668533e3013

3. Test the web application.

4. Add security using JWT with AWS Cognito or another technology.

  https://github.com/user-attachments/assets/1b8e6998-51c8-401f-815d-d8d3eb695b92

6. Separate the monolith into three independent microservices using Lambda.
7. Deploy the service on AWS Lambda.

---

## **Monolith Deployment**

The monolith was developed using **Spring Boot** and initially deployed on a local server or  **AWS EC2**. Below are the steps to deploy the monolith:

1. **Clone the repository**:
    
    ```bash
    git clone https://github.com/Nat15005/Taller-07-AREP.git
    cd Taller-07-AREP
    ```
    
2. **Build and run the monolith**:
    
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```
    
3. **Access the API**:
    - The API will be available at `http://localhost:8080`.

---

## **Deployment on AWS Lambda**

The microservices were deployed on **AWS Lambda** using the **aws-serverless-java-container** adapter. Below are the steps to deploy a microservice:

1. **Package the microservice**:
    
    ```bash
    mvn clean package
    ```
    
2. **Upload the JAR file to AWS Lambda**:
    - Upload the generated JAR file (`target/microservicio.jar`) to AWS Lambda.
3. **Configure the handlerr**:
    - Set up the (`Handler`) to point to the main class of the microservice (for example, `arep.twitter.UserLambdaHandler`).
4. **Configure API Gateway**:
    - Create an API Gateway to expose the microservice's endpoints..

---

## **Microservices Separation**

The monolith was divided into three independent microservices:

1. **User Service**:
    - Handles user creation and authentication.
    - Deployed on AWS Lambda.
2. **Post Service**:
    - Allows users to publish 140-character posts.
    - Deployed on AWS Lambda.
3. **Stream Service**:
    - Maintains a unique thread of all published posts.
    - Deployed on AWS Lambda.

Each microservice communicates via **API Gateway** and uses **AWS Lambda** to execute business logic.

---

## **Web Application**

The web application was developed in **JavaScript** and deployed on **Amazon S3**. Below are the steps to deploy the application:

1. **Upload static files to S3**:
    - Upload the generated files (`build/`) to an S3 bucket.
2. **Enable public access**:
    - Configure the S3 bucket to be publicly accessible.
3. **Acceder a la aplicación**:
    - The application will be available at the URL provided by S3 (e.g., `http://tu-bucket.s3-website-us-east-1.amazonaws.com`).

---

## **Testing**

Unit and integration tests were conducted for each system component. Below are the types of tests performed:

1. **Unit Tests**:
    - **JUnit 5** and **Mockito** were used to test individual classes and methods.
2. **Integration Tests**:
    - The integration between microservices and the web application was tested.
3. **Load Tests**:
    - **Apache JMeter** was used to simulate multiple users and verify system performance.

---

## **Security**

Security was implemented using **JWT** (JSON Web Tokens) with **AWS Cognito** or Firebase. Below are the steps to configure security:

1. **Set up AWS Cognito**:
    - Create a User Pool in AWS Cognito.
    - Configure the necessary clients and permissions.
2. **Integrate JWT into Spring Boot**:
    - Configure Spring Security to validate JWT tokens.
3. **Protect API endpoints**:
    - Secure API endpoints using annotations like`@PreAuthorize`.
---

##  Testing

Unit and integration tests were performed to verify system functionality.

![image](https://github.com/user-attachments/assets/29bedff8-3293-41e3-b395-57db6f522614)

```bash

mvn test

```

![image](https://github.com/user-attachments/assets/6be09131-77fa-4828-b2ad-d3323b3f5b09)

---

## Microservices Demonstration Video

---

## 👨‍💻 Development Team

- **Ana Maria Duran Burgos** - [GitHub](https://github.com/AnaDuranB)
- **Laura Natalia Rojas Robayo** - [GitHub](https://github.com/Nat15005)
- **Yeferson Estiben Mesa Vargas** - [GitHub](https://github.com/JffMv)
