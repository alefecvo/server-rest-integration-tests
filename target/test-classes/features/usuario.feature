Feature: Usuário API

  @todos @usuario-cadastrar
  Scenario: Deve cadastrar novo usuário
    Given Eu tenho os dados para cadastrar um novo usuario
    When Eu faço um pedido POST para a URL "https://serverest.dev/usuarios"
    Then O status do código de resposta deve ser 201

  @todos @usuario-nao-cadastrado
  Scenario: Deve consultar usuário cadastrado
    Given Eu não tenho um usuario cadastrado mas desejo cadastrar
    When Eu faço um pedido GET para a URL "https://serverest.dev/usuarios"
    Then O status do código de resposta deve ser de 200

  @todos @usuario-nao-existente
  Scenario: Deve consultar usuário não cadastrado
    Given Eu não tenho um usuario cadastrado "XPTO"
    When Eu faço um pedido GET para a URL "https://serverest.dev/usuarios"
    Then O status do código de resposta deverá ser de 400
