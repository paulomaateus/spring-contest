# Contest - Microserviço de Processos Judiciais

Este é um microserviço desenvolvido com Spring Boot e Maven para gerenciar processos judiciais. A aplicação permite a criação, listagem e exclusão de processos, além de adicionar réus a um processo. O banco de dados utilizado é o PostgreSQL e a integração com o banco é gerenciada pelo Liquibase. Os testes são realizados com JUnit.

## Tecnologias Utilizadas

- Spring Boot
- Maven 3.9.9
- JDK 21
- PostgreSQL 14
- Liquibase
- JUnit

## Endpoints da API

### Processos Judiciais

#### Criar um Processo Judicial

- **Método:** POST
- **Endpoint:** /processos
- **Corpo da Requisição:**
  
  ```json
  {
    "numero": "123456",
    "descricao": "Descrição do Processo",
    "status": "Ativo"
  }
  ```

#### Excluir um Processo Judicial

- **Método:** DELETE
- **Endpoint:** /processos/{numero}
- **Parâmetros:**
  - `numero` - Número do processo a ser excluído.

#### Listar Processos Judiciais

- **Método:** GET
- **Endpoint:** /processos
- **Parâmetros:**
  - `page` - Número da página (opcional, padrão: 0)
  - `size` - Tamanho da página (opcional, padrão: 10)

### Réus

#### Adicionar um Réu a um Processo

- **Método:** POST
- **Endpoint:** /reu
- **Corpo da Requisição:**
  
  ```json
  {
    "nome": "Nome do Réu",
    "cpf": "123.456.789-00",
    "numeroProcesso": "123456"
  }
  ```

## Executando a Aplicação

### Usando Docker Compose

Para executar a aplicação com Docker Compose, siga os passos abaixo:

1. Certifique-se de ter o Docker e o Docker Compose instalados.
2. Clone o repositório:
   
   ```bash
   git clone git@github.com:paulomaateus/spring-contest.git
   cd spring-contest
   ```

3. Execute o Docker Compose:
   
   ```bash
   docker-compose up
   ```

   Isso irá iniciar os containers para o PostgreSQL e a aplicação. A aplicação estará disponível em http://localhost:8080.

### Usando Maven

Para executar a aplicação localmente utilizando Maven, siga os passos abaixo:

1. Certifique-se de ter o Maven 3.9.9 e JDK 21 instalados.
2. Clone o repositório:
   
   ```bash
   git clone git@github.com:paulomaateus/spring-contest.git
   cd spring-contest
   ```

3. Compile e execute a aplicação:
   
   ```bash
   mvn clean spring-boot:run
   ```

   A aplicação estará disponível em http://localhost:8080.

## Estrutura do Projeto

- `src/main/java` - Código fonte da aplicação.
- `src/main/resources` - Arquivos de configuração, incluindo `application.properties`.
- `src/test/java` - Testes da aplicação.
- `Dockerfile` - Arquivo para construir a imagem Docker da aplicação.
- `docker-compose.yml` - Arquivo de configuração para o Docker Compose.

