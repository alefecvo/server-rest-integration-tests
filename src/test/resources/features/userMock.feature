Feature: User API ServerRest Wiremock

  @wiremock @user-register-wiremock
  Scenario: Should register a new user wiremock
    Given I have the data to register a new user wiremock
    When I make a POST request to the URL "http://localhost:8080/usuarios" wiremock
    Then The response status code should be 201 to a new user wiremock