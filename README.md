# Teste Essia

### gerenciamento-arquivo-virtual
Um sistema para gerenciar diretórios e arquivos, com funcionalidades como criação de diretórios, adição de arquivos e gerenciamento de subdiretórios, utilizando uma API estruturada com front-end em Vue.js e back-end em Spring Boot
Este projeto foi desenvolvido como parte do processo seletivo para a vaga de desenvolvedor Java na Essia. O repositório completo pode ser acessado [aqui](https://github.com/DiegoDBLe/gerenciamento-arquivo-virtual.git).

# Gerenciamento de Arquivo Virtual
## Objetivo do Desafio
O desafio consistiu em criar um sistema simples e eficiente para gerenciar diretórios e arquivos virtualmente. O projeto oferece uma interface interativa para criação, edição, exclusão e visualização de diretórios e seus arquivos, bem como a estruturação em subdiretórios. 

## Índice

- [Funcionalidades](#funcionalidades)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Como Executar o Projeto](#como-executar-o-projeto)
- [Testes Unitários](#testes-unitários)
- [Testes de API com Postman](#testes-de-api-com-postman)
  
## Funcionalidades

O sistema oferece as seguintes funcionalidades:

- **Gerenciamento de Diretórios:**
  - Criação de novos diretórios.
  - Edição e renomeação de diretórios existentes.
  - Exclusão de diretórios vazios ou com subdiretórios.

- **Gerenciamento de Subdiretórios:**
  - Criação de subdiretórios dentro de diretórios.
  - Edição de nomes de subdiretórios.
  - Exclusão de subdiretórios de forma hierárquica.

- **Gerenciamento de Arquivos:**
  - Criação de arquivos em diretórios e subdiretórios.
  - Edição e renomeação de arquivos existentes.
  - Exclusão de arquivos de diretórios e subdiretórios.

- **Interface Simples:**
  - Modo de visualização para navegação pelos diretórios e subdiretórios.
  - Modo de gerenciamento para criação e edição de arquivos e diretórios.

 - Exibição dos Diretórios 
<img width="682" alt="image" src="https://github.com/user-attachments/assets/eb4563a1-6868-47df-8bf9-1b6476ea2584">

- Gerenciamento dos Dirétorios
- <img width="593" alt="image" src="https://github.com/user-attachments/assets/30f843c8-f80a-4642-9e80-0ca0006cf3fa">

## Testes Unitários
- O projeto inclui uma série de testes unitários que garantem a funcionalidade e a integridade do sistema. Esses testes cobrem principalmente as operações de criação, edição e exclusão de diretórios, subdiretórios e arquivos, verificando também cenários de erro e exceções.

## Funcionalidades Testadas
- Criação de diretórios: Garante que novos diretórios sejam corretamente salvos no banco de dados.
- Listagem de diretórios: Verifica se a listagem de todos os diretórios está funcionando conforme esperado.
- Edição de subdiretórios: Garante que subdiretórios podem ser editados e renomeados.
- Exclusão de subdiretórios: Verifica se subdiretórios podem ser excluídos, incluindo casos onde o subdiretório não existe.
- Validação de exclusão: Garante que exceções são lançadas quando diretórios ou subdiretórios não são encontrados.
- Listagem vazia de diretórios: Verifica se o sistema lida corretamente com a ausência de diretórios.

## Testes de API com Postman
- As APIs do sistema foram testadas utilizando o Postman. Aqui estão algumas das principais requisições usadas para testar as funcionalidades:

### Testando Criação de Diretório
- Método: POST
URL: http://localhost:8080/api/diretorios
- Corpo da Requisição:
json
Copiar código
{
  "nome": "Novo Diretório"
}
### Testando Listagem de Diretórios
- Método: GET
URL: http://localhost:8080/api/diretorios

### Testando Criação de Subdiretório
- Método: POST
URL: http://localhost:8080/api/diretorios/{id}/subdiretorios

- Corpo da Requisição:
json
Copiar código
{
  "nome": "Novo Subdiretório"
}

### Testando Exclusão de Diretório
Método: DELETE
URL: http://localhost:8080/api/diretorios/{id}
Para mais detalhes sobre os testes de API, os arquivos do Postman podem ser importados diretamente no aplicativo para facilitar os testes.

## Tecnologias Utilizadas

O projeto foi desenvolvido utilizando as seguintes tecnologias:

### Back-end
- **Java 17**: Linguagem de programação usada no desenvolvimento do back-end.
- **Spring Boot**: Framework para criação da API REST.
- **Spring Data JPA**: Usado para persistência de dados.
- **H2**: Banco de dados relacional utilizado.
- **Maven**: Gerenciamento de dependências e automação do build.

### Front-end
- **Vue.js**: Framework JavaScript para criação de interfaces de usuário.
- **Axios**: Biblioteca para realizar requisições HTTP entre front-end e back-end.
- **PrimeVue**: Biblioteca de componentes para Vue.js, usada para construir a interface.
- **SweetAlert2**: Utilizado para exibir alertas amigáveis para o usuário.

### Pré-requisitos

Para rodar este projeto, você precisa ter instalado:

- **Java 17**
- **Maven**
- **Node.js** e **npm**
- **H2**

### Como Executar o Projeto

1. Clone o repositório:

```bash
git clone https://github.com/DiegoDBLe/gerenciamento-arquivo-virtual
cd gerenciamento-arquivo-virtual
mvn spring-boot:run
Navegue até o diretório do front-end e instale as dependências:
npm install
Para rodar os testes unitários:
mvn test´´´
Para o front-end, navegue até o diretório do projeto front-end, instale as dependências e inicie o servidor:
cd frontend
npm install
npm run serve

