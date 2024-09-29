Feature: User API

  @all @user-register
  Scenario: Should register a new user
    Given I have the data to register a new user
    When I make a POST request to the URL "https://serverest.dev/usuarios"
    Then The response status code should be 201 to a new user

  @all @user-not-registered
  Scenario: Should query registered user
    Given I do not have a registered user but wish to register
    When I make a GET request to the URL "https://serverest.dev/usuarios"
    Then The response status code should be 200 to registered user

  @all @user-non-existent
  Scenario: Should query non-registered user
    Given I do not have a registered user "XPTO"
    When I make a GET request to the URL "https://serverest.dev/usuarios"
    Then The response status code should be 400 to non-registered user

  @wiremock @user-register-wiremock
  Scenario: Should register a new user wiremock
    Given I have the data to register a new user wiremock
    When I make a POST request to the URL "http://localhost:8080/usuarios" wiremock
    Then The response status code should be 201 to a new user wiremock