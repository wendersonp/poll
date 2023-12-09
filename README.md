# Sistema de votação de cooperativas

Esta é a aplicação para um sistema simples de votação para cooperativas, onde é possível registrar
os membros da cooperativa, criar pautas e iniciar uma votação pra cada pauta, cada votação fica aberta por um determinado tempo
escolhido pelo operador do sistema, e cada membro da cooperativa pode submeter seu voto para a pauta aberta, apenas uma única vez.
Após a votação ser encerrada, o resultado é computado e o sistema envia por uma fila o resultado da votação, que também pode ser acessado
ao consultar a pauta.

A documentação pode ser acessada através [deste link](https://documenter.getpostman.com/view/7562211/2s9YkgEkmV)

## Requisitos

Para rodar a aplicação, é necessário ter instalado no sistema: 

- Docker
- Docker-Compose
- Java

## Instalação

### Baixando e configurando as ferramentas necessárias

1. Com o docker propriamente instalado, baixe o arquivo [compose.yml](https://github.com/wendersonp/poll/releases/download/v1.0.0-alpha/compose.yaml)
e salve em uma pasta de sua preferência
2. Rode o seguinte comando através do terminal no diretório com o arquivo salvo
```
docker-compose -f compose.yaml up -d
```
3. Com isto, as ferramentas já devem estar rodando normalmente

### Executando a aplicação

1. Baixe o [binário da release](https://github.com/wendersonp/poll/releases/download/v1.0.0-alpha/poll-1.0.0.jar)
Pelo link ou através da página de releases no repositório
2. Após ter executado e criado os conteineres com as ferramentas necessárias, 
, ter instalado a Runtime do Java, execute a aplicação com o seguinte comando:

```
java -jar poll-<versao>.jar
```
### Testando se tá tudo certo

Acesse o endereço a seguir no navegador para conferir se a aplicação está rodando corretamente

```
http://localhost:8081/swagger-ui/index.html
```
Caso tudo tenha dado certo, a página referida acima já ser acessada e utilizada
para gerenciar a aplicação. 

A pagina em questão foi produzida com ajuda do Swagger, caso queira acessar os
endpoints da aplicação  através de outro aplicativo, obtenha a especificação
OpenAPI 3.0 da aplicação através de:

```
http://localhost:8081/v3/api-docs
```

Ou baixe a coleção do Postman pela documentação fornecida no início deste readme

## Versão

A versão do projeto é controlada por versionamento semântico, com a criação de releases no github
A versão da API é controlada por um contrato definido com um campo no header da requisição,
exemplos do versionamento da API e como utilizar estão disponíveis na documentação.

## Autores

- Wenderson, *arquiteto de software e desenvolvedor*, [wendersonp](https://github.com/wendersonp)


