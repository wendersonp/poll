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

1. Com o docker propriamente instalado, baixe o arquivo [compose.yml](https://github.com/wendersonp/poll/releases)
e salve em uma pasta de sua preferência
2. Rode o seguinte comando através do terminal no diretório com o arquivo salvo
```
docker-compose -f compose.yaml up -d
```
3. Com isto, as ferramentas já devem estar rodando normalmente

### Executando a aplicação

1. Baixe o [binário da release](https://github.com/wendersonp/poll/releases)
Pelo link ou através da página de releases no repositório
2. Após ter executado e criado os conteineres com as ferramentas necessárias, 
, ter instalado a Runtime do Java, execute a aplicação com o seguinte comando:

```
java -jar poll-<versao>.jar
```

### Configurando a fila no RabbitMQ

1. Subindo a aplicação, é possível constatar que uma _exchange_ foi criada. Porém, para receber os resultados da votação,
é preciso configurar uma fila que ira ser ligada a exchange. Para isso, acesse o endereço (http://localhost:15672) e entre na página de gerenciamento
do RabbitMQ, e logue com as credenciais de username: **guest**, senha: **guest**, como aparece na imagem a seguir.

![rabbit-login.PNG](img%2Frabbit-login.PNG)

2. Acesse a página de filas com o nome _"Queues and Streams"_, preencha o formulário para criar a fila em _Add a new queue_, coloque o nome da fila
que preferir no campo _Name_ e clique em _Add queue_, a imagem a seguir serve como exemplo.

![rabbit-create-queue.PNG](img%2Frabbit-create-queue.PNG)

3. Este proximo passo consiste em ligar a fila ao exchange que a aplicação criou automaticamente, para isso, va em _exchanges_ no canto superior da tela
e clique em _vote-report_.

![rabbit-exchanges.PNG](img%2Frabbit-exchanges.PNG)

4. Na próxima tela, preencha o formulário em _Add binding from this exchange_
Selecione, _to queue_ na primeira opção, coloque o nome da fila que você criou, no campo a direita.
Além disso, em _Routing key_, coloque o valor **#**, para indicar que a mensagem deve ir para todas as filas
conectadas. A página deve ficar como na imagem a seguir.

![rabbit-bind-queue.PNG](img%2Frabbit-bind-queue.PNG)

5. Clique em _bind_ e a fila estará conectada com a exchange, com isso, sua fila deve receber as mensagens com os resultados
das votações.

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

### Visualizando o resultado na fila do RabbitMQ

1. Para visualizar o resultado quando este for recebido na fila, va para a página de gerenciamento do RabbitMQ, como já explicado anteriormente, 
clique no nome da sua fila criada na página _Queues and Streams_.

2. Va em _Get messages_, selecione _Nack messages requeue true_ caso você queira ler o resultado, mas queira que ele permaneça na fila.
   Se você quer ler o resultado e elimina-lo da fila, selecione _Automatic ack_. Por ultimo, para conseguir ler a mensagem, confirme no botão
   _Get message(s)_. Como na imagem a seguir:

![rabbit-result.PNG](img%2Frabbit-result.PNG)

3. Com isso, você conseguiu ler a mensagem de resultado da votação com sucesso. Lembre-se de realizar uma votação
   antes de ler a mensagem.


## Versão

A versão do projeto é controlada por versionamento semântico, com a criação de releases no github
A versão da API é controlada por um contrato definido com um campo no header da requisição,
exemplos do versionamento da API e como utilizar estão disponíveis na documentação.

## Autores

- Wenderson, *arquiteto de software e desenvolvedor*, [wendersonp](https://github.com/wendersonp)


