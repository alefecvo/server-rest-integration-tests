Feature: Usuário API

  Scenario: Deve cadastrar novo usuário
    Given Eu tenho os dados para cadastrar um novo usuario
    When Eu faço um pedido POST para a URL "https://serverest.dev/usuarios"
    Then O status do código de resposta deve ser 201

  Scenario: Deve consultar usuário cadastrado
    Given Eu tenho um usuario cadastrado
    When Eu faço um pedido GET para a URL "https://serverest.dev/usuarios"
    Then O status do código de resposta deve ser de 200