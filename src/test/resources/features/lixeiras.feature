
# language: pt

Funcionalidade: Gerenciamento de lixeiras inteligentes
  Como administrador do EcoTrack
  Quero gerenciar lixeiras inteligentes
  Para garantir o controle adequado dos resíduos

  Cenário: Cadastrar uma lixeira com sucesso
    Dado que existe uma localização cadastrada
    E que possuo dados válidos de uma lixeira
    Quando envio uma requisição para cadastrar a lixeira
    Então a API deve retornar o status 200
    E a resposta deve conter os dados da lixeira cadastrada
    E o JSON deve respeitar o contrato definido

  Cenário: Tentar cadastrar uma lixeira com capacidade inválida
    Dado que existe uma localização cadastrada
    E que possuo uma lixeira com capacidade negativa
    Quando envio uma requisição para cadastrar a lixeira
    Então a API deve retornar o status 400

  Cenário: Consultar uma lixeira inexistente
    Dado que não existe uma lixeira com o identificador informado
    Quando envio uma requisição para consultar essa lixeira
    Então a API deve retornar o status 404
