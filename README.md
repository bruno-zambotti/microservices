# Microserviços com Docker Swarm e Portainer
**Descrição:** Este projeto visa demonstrar o funcionamento de microserviços orquestrados pelo Docker Swarm e Portainer.

## Design Patterns:
```
Microservice Architecture
├── Application Patterns
│   ├── Decomposition
│   │   └── Decompose by subdomain
│   ├── Data Patterns
│   │   └── Database Architecture
│   │       └── Database per Service
│   └── Testing
│       └── Service Component Test
├── Application Infrastructure Patterns
│   ├── Communication Style
│   │   ├── Remote Procedure Invocation
│   │   └── Messaging
│   ├── Reliability
│   │   └── Circuit Breaker
│   └── Observability
│       ├── Health check API
│       └── Distributed Logging Tracing
└── Infrastructure Patterns
    └──Deployment
       └── Service-per-Container
``` 

## Exemplo de Funcionamento 
![](example.gif)

## Funcionalidades:

### Gerenciamento de clientes

- Consultar todos os clientes

- Consultar um cliente específico  

- Cadastrar um cliente 

- Alterar informações de um cliente 

- Excluir um cliente 

### Gerenciamento de pedidos

- Consultar todos os pedidos 
   
- Consultar um pedido específico  

- Realizar um pedido 

- Alterar um pedido 

- Excluir um pedido 

### Gerenciamento de produtos

- Consultar todos os produtos 

- Consultar um produto específica  

- Cadastro de produtos 

- Alterar um produto  

- Excluir um produto 

### Gerenciamento de estoque 

- Consultar o estoque 

- Consultar um produto do estoque  

- Alterar um produto do estoque 

- Adicionar um produto ao estoque 

- Excluir um produto do estoque  

## Outras informações

### Executando o RabbitMQ:
Para o perfeito funcionamento da solução proposta com a implementação do Circuit Breaker, deve-se subir o container do docker localmente antes das aplicações, conforme os comandos abaixo:
```
docker pull rabbitmq:management

docker run -d --name rabbitmq \
 -p 5672:5672 \
 -p 15672:15672 \
 --restart=always \
 --hostname rabbitmq-master \
 -v /docker/rabbitmq/data:/var/lib/rabbitmq \
 rabbitmq:management

docker exec -it rabbitmq /bin/bash
```
Caso ocorra algum problema na criação da fila pelas aplicações que a utilizam, crie a mesma manualmente com o comando a seguir:
```
rabbitmqadmin declare queue name=Queue durable=true
```
### Acessando Actuator:
Dentre diversas informações, caso deseje validar se a aplicação está em execução acesse a seguinte url de exemplo: http://localhost:8080/actuator/health.

### Testando a aplicação via Swagger:
Para realizar o teste da aplicação via interface gráfica do swagger, você deve subir a aplicação desejada via docker-compose, ou diretamente por meio de uma IDE como Eclipse ou IntelliJ, e acessar a seguinte url de exemplo: http://localhost:8080/swagger-ui.html.

### Testando a aplicação via Postman:
Para testar os métodos da aplicação via Postman siga os passos a seguir:
1. Realize a instalação do [programa](https://www.getpostman.com/downloads/).
2. Após a instalação abra o aplicativo.
3. Após o aplicativo abrir, siga as seguintes instruções:

    3.1. Clique em **File** no menu de opções.
  
    3.2. Em seguida clique em **Import**.
  
    3.3. Tenha certeza de que a aba **Import File** está selecionada na janela que abrir, caso não esteja selecione-a.
  
    3.4. Clique no botão **Choose Files** na janela que será aberta.
  
    3.5. Escolha o arquivo ***"microservices.postman_collection.json"*** presente na raiz do projeto.

4. Agora é só subir a aplicação e realizar as chamadas desejadas.

### Subindo clusterizado com Play-with-docker
![](docker_swarm.gif)
Para subir as aplicações de forma clusterizada com Docker Swarm realize os passos a seguir:
1. Criar a rede que vai ser utilizada pelos contêineres:
``` 
docker network create -d overlay net 
``` 

2. Realizar o download para um dos managers dos arquivos docker-compose de cada um dos serviços: 
``` 
curl -L https://downloads.portainer.io/portainer-agent-stack.yml -o portainer-agent-stack.yml 

curl -L https://raw.githubusercontent.com/bruno-zambotti/microservices/master/customer-service/docker-compose.yml -o customer-service.yml

curl -L https://raw.githubusercontent.com/bruno-zambotti/microservices/master/inventory-service/docker-compose.yml -o inventory-service.yml

curl -L https://raw.githubusercontent.com/bruno-zambotti/microservices/master/products-service/docker-compose.yml -o products-service.yml

curl -L https://raw.githubusercontent.com/bruno-zambotti/microservices/master/order-service/docker-compose.yml -o order-service.yml

curl -L https://raw.githubusercontent.com/bruno-zambotti/microservices/master/visualizer-docker-compose.yml -o visualizer.yml
```
3. Executar o Stack de cada aplicação no manager em que realizou o download dos arquivos de configuração:
```
docker stack deploy --compose-file=visualizer.yml visualizer

docker stack deploy --compose-file=customer-service.yml customer

docker stack deploy --compose-file=inventory-service.yml inventory

docker stack deploy --compose-file=products-service.yml products

docker stack deploy --compose-file=order-service.yml order

docker stack deploy --compose-file=portainer-agent-stack.yml portainer
```

4. Para visualizar todas as aplicações que subiram execute o seguinte comando:
```
 docker stack ls
```

5. Para visualizar cada aplicação individualmente:
```
docker stack ps visualizer
docker stack ps portainer
docker stack ps order
docker stack ps products
docker stack ps inventory
docker stack ps customer
```

6. Para aumentar ou diminuir o número de replicas de um serviço informe confome o exemplo abaixo
```
docker service scale products_db=2
docker service scale products_api=4
```

#### Fluxo recomendado para teste:
Para realizar um teste integrado e completo, um dos fluxos recomendados é o seguinte:

1. Cadastre um produto por meio da request do postman *"createProduct"* passando a categoria criada anteriormente como um dos elementos do corpo da requisição.

2. Adicione o produto criado e a quantidade desejada no estoque por meio da request do postman *"createInventoryItemByProductId"*.

3. Cadastre um novo cliente por meio da request do postman *"createCustomer"*.

4. Gere um pedido, informando o cliente e o produto criados anteriormente, por meio da request do postman *"createOrder"*.

5. Consulte o pedido, por meio da request do postman *"getOrdersById"*, informando o id gerado no passo anterior.

6. Consulte o estoque pelo produto que foi associado ao pedido para verificar que o estoque foi sensibilizado com a efetivação do pedido por meio da request do postman *"getInventoryByProductId"*.

### Tabelas de domínio:

#### Tipo do endereço:

| CÓDIGO 	| DESCRIÇÃO   	|
|--------	|-------------	|
| 1      	| RESIDENCIAL 	|
| 2      	| COMERCIAL   	|
| 3      	| ENTREGA     	|
| 4      	| OUTROS      	|

#### Situação do Pedido:

| CÓDIGO 	| DESCRIÇÃO            	|
|--------	|----------------------	|
| 0      	| NOVO                 	|
| 1      	| AGUARDANDO PAGAMENTO 	|
| 2      	| EM SEPARAÇÃO         	|
| 3      	| EM DESLOCAMENTO      	|
| 4      	| ENTREGUE             	|