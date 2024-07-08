# server-rest-integration-tests
This project created to ServerRest API automation tests using Java, Maven, Cucumber, Restassured, Stackspot AI.

## Dependencies

#### Install the items

1. IDE: IntelliJ (versão 2023.2.5)
2. Plugin IDE: Stackspot AI (versão 1.3.0), Cucumber-Java (232.8660.142), Gherkin (232.8660.142). 
3. Linguagem: Java (versão 11.0.20). 
4. Dependência: Maven (versão 3.9.5)
5. Bibliotecas: Restassured (4.3.3), Cucumber-Java (6.10.4), Cucumber-JUnit (6.10.4), JUnit (4.3.2), Faker (1.02).


#### To run tests

```
mvn test
```

```
Generate an example for a Rest API POST test using Java 11, Maven, Rest Assured, and Cucumber
example:

Feature: Test POST API endpoint

Scenario: Post new data to the API
Given I set POST API endpoint
When I send POST HTTP request
Then I receive valid HTTP response code 201

```