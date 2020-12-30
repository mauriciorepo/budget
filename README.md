#

<h1 align="center">
   <a href="">Budget Backend</a>
</h1>
<p align="center">An example of a financial project with endpoints built in restfull java and Spring Boot</p>

[![Build Status](https://travis-ci.org/mauriciorepo/budget.svg?branch=master)](https://travis-ci.org/mauriciorepo/budget)
[![codecov](https://codecov.io/gh/mauriciorepo/budget/branch/master/graph/badge.svg)](https://codecov.io/gh/mauriciorepo/budget)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/e4d9fff4fe28429ebe53b207b661520b)](https://www.codacy.com/gh/mauriciorepo/budget/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=mauriciorepo/budget&amp;utm_campaign=Badge_Grade)
[![GitHub issues](https://img.shields.io/github/issues/mauriciorepo/budget)](https://github.com/mauriciorepo/budget/issues)
[![GitHub forks](https://img.shields.io/github/forks/mauriciorepo/budget)](https://github.com/mauriciorepo/budget/network)
[![GitHub stars](https://img.shields.io/github/stars/mauriciorepo/budget)](https://github.com/mauriciorepo/budget/stargazers)

<h4 align="center">
  🚧  Budget backend project ...  builit in 🚧
</h4>

###Tabela de conteúdos

<!--ts-->
*   [About](#About)
*   [Features](#Features)
*   [Demo application](#Demo-application)
*   [Getting started](#Getting-started)
    *   [Start](#Start)
    *   [Requirements](#Requirements)     
*   [Tecnologies](#Tecnologies)
<!--te-->

### About

    This is an application where you can make simple budgets and relate them with companines.
    With our application, you can easily organize budgets and produce reports.

### Features
* [x]   Persist User
* [X]   Authentication User
* [X]   Authorization User
* [ ]   Delete User
* [x]   Persist Company
* [x]   Find company by id
* [X]   Find all companies
* [X]   Update company
* [ ]   Delete company
* [x]   Persist Order Service
* [x]   Find Order Service by id
* [x]   Find Order Service by id company
* [X]   Update Order Service

### Demo application

show some gif

### Getting started

 #### Start
To get for development, we need to clone the project repo using some terminal like git bash and then use the following code:
- `cd path of your preference`
- `git clone https://github.com/mauriciorepo/budget`

- `cd budget`

#### Requirements
* [JDK 8: Required to run a java project](https://www.oracle.com/br/java/technologies/javase/javase-jdk8-downloads.html)

* [Maven 3.5.3: Required to do a build Java project](http://mirror.nbtelecom.com.br/apache/maven/maven-3/3.5.3/binaries/apache-maven-3.5.3-bin.zip)

After that, you can import project to Eclipse, Intellij or Vscode
* [Eclipse: for development the project](http://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/oxygen3a)
* [Intellij for development the project](https://www.jetbrains.com/)
* [Vscode for development the project](https://code.visualstudio.com/)

### Tecnologies

The following tools were used on construction project:

* [Java 8](https://www.java.com/pt-BR/download/manual.jsp)
* [Spring](https://spring.io/)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Lombok](https://projectlombok.org/)
* [JUnit 5](https://junit.org/junit5/)
* [Oauth2.0](https://oauth.net/2/)
* [JWT](https://jwt.io/)
* [Swagger](https://swagger.io/)
* [Jacoco](https://www.jacoco.org/jacoco/trunk/index.html)
  
**Find company by id**
----
Returns json data about a single company.

* **URL**

  /api/companies/:id

* **CURL**

  curl -X GET --header 'Accept: application/json' 'http://localhost:8080/api/companies/1

* **Method:**

  `GET`

*  **URL Params**

   **Required:**

   `id=[long]`

* **Data Params**

  None

* **Success Response:**

    * **Code:** 200 <br />
      **Content:** 
      ```
      {
      "account": "string",
      "cellphone": "string",
      "contactName": "string",
      "country": "string",
      "email": "string",
      "id": 0,
      "localization": "string",
      "name": "string",
      "neighborhood": "string",
      "registrationDate": "string",
      "situation": true,
      "stateAbbrev": "string",
      "stateRegistration": "string",
      "telephone": "string",
      "telephone2": "string"
      }
      ```

* **Error Response:**

    * **Code:** 404 NOT FOUND <br />
      **Content:** 
      ```
      {
      "errors": [
      null
      ]
      }
      ```

  OR

    * **Code:** 401 UNAUTHORIZED <br />
      **Content:** 
      ```
      {
      "error": "invalid_token",
      "error_description": "Access token expired: eyJh...............wErbQM5cR6SWp8"
      }
      ```
    OR
    * **Code** 401 UNAUTHORIZED <br />
      **Content:** 
      ```
      {
        "error": "invalid_token",
        "error_description": "Cannot convert access token to JSON"
      }
      ```
* **Sample Call:**

  ```
  http://localhost:8080/api/companies/2
  ```
* **Response Headers**
```
{
  "cache-control": "no-cache, no-store, max-age=0, must-revalidate",
  "connection": "keep-alive",
  "content-type": "application/json",
  "date": "Wed, 30 Dec 2020 00:02:17 GMT",
  "expires": "0",
  "keep-alive": "timeout=60",
  "pragma": "no-cache",
  "transfer-encoding": "chunked",
  "vary": "Origin, Access-Control-Request-Method, Access-Control-Request-Headers",
  "x-content-type-options": "nosniff",
  "x-xss-protection": "1; mode=block"
}
```

