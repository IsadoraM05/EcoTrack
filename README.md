# EcoTrack — Sistema de Gestão de Coleta Seletiva

## 1. Sobre o projeto

O **EcoTrack** é uma aplicação backend desenvolvida em Java com Spring Boot, voltada à gestão de processos relacionados à coleta seletiva e à sustentabilidade.

O sistema contempla o gerenciamento de lixeiras, localizações, coletas, alertas e usuários, utilizando uma API REST integrada a um banco de dados Oracle.

Nesta etapa do projeto, foi implementada uma infraestrutura de **Integração Contínua e Entrega Contínua (CI/CD)**, utilizando GitHub Actions e Docker para automatizar a compilação, os testes e a implantação da aplicação em ambientes separados.

## 2. Tecnologias utilizadas

| Tecnologia | Finalidade |
|---|---|
| Java 21 | Linguagem de programação |
| Spring Boot 3.2.1 | Desenvolvimento da API |
| Spring Security | Segurança e controle de acesso |
| Spring Data JPA / Hibernate | Persistência de dados |
| Oracle Database 23 Free | Banco de dados relacional |
| Flyway | Versionamento e migração do banco |
| Maven | Gerenciamento de dependências e build |
| Docker | Containerização |
| Docker Compose | Orquestração dos containers |
| GitHub Actions | Automação do pipeline CI/CD |
| GitHub Self-hosted Runner | Execução dos deploys no ambiente local |

## 3. Arquitetura da aplicação

O EcoTrack utiliza uma arquitetura composta por uma API Spring Boot e um banco de dados Oracle, executados em containers Docker.

Foram configurados três ambientes independentes:

| Ambiente | Porta da API | Finalidade |
|---|---|---|
| Desenvolvimento | 8080 | Desenvolvimento e testes locais |
| Staging | 8081 | Homologação e validação antes da produção |
| Produção | 8082 | Ambiente final de implantação |

Cada ambiente possui sua própria configuração, rede Docker e armazenamento persistente do banco Oracle.

Os bancos de staging e produção não precisam ter suas portas publicadas no computador, pois são acessados internamente pelas respectivas APIs através das redes Docker.

## 4. Estrutura do projeto

```text
EcoTrack/
├── .github/
│   └── workflows/
│       ├── ci.yml
│       └── test-runner.yml
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   │       ├── application.properties
│   │       └── db/migration/
│   └── test/
├── .dockerignore
├── .gitignore
├── Dockerfile
├── docker-compose.yml
├── docker-compose.staging.yml
├── docker-compose.production.yml
├── .env.example
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

## 5. Pré-requisitos

Para executar o projeto localmente, são necessários:

- Git
- Docker Desktop com Docker Compose
- Java 21 e Maven, caso a aplicação seja executada fora do Docker
- Acesso ao repositório do EcoTrack

**Repositório:** https://github.com/IsadoraM05/EcoTrack

## 6. Como executar o projeto

### 6.1. Clonar o repositório

```bash
git clone https://github.com/IsadoraM05/EcoTrack.git
cd EcoTrack
```

### 6.2. Configurar as variáveis de ambiente

Crie um arquivo `.env` na raiz do projeto, utilizando `.env.example` como referência.

As variáveis necessárias incluem:

```dotenv
ORACLE_PASSWORD=<senha_administrativa>
DB_PASSWORD=<senha_da_aplicacao>
```

As senhas devem ser definidas localmente. Os arquivos `.env` não devem ser enviados ao GitHub.

### 6.3. Iniciar o ambiente de desenvolvimento

```bash
docker compose up -d --build
```

Após a inicialização, a API estará disponível em:

http://localhost:8080

### 6.4. Verificar os containers

```bash
docker compose ps
```

### 6.5. Consultar os logs

```bash
docker compose logs --tail=100 ecotrack-api
```

### 6.6. Executar os testes

No Windows, com Java 21 configurado:

```powershell
.\mvnw.cmd test
```

Os testes também são executados automaticamente pelo GitHub Actions.

## 7. Ambientes de staging e produção

Os ambientes de staging e produção utilizam arquivos Docker Compose próprios.

### Staging

```powershell
docker compose -p ecotrack-staging `
  --env-file .env.staging `
  -f docker-compose.staging.yml `
  up -d --build
```

API: http://localhost:8081

### Produção

```powershell
docker compose -p ecotrack-production `
  --env-file .env.production `
  -f docker-compose.production.yml `
  up -d --build
```

API: http://localhost:8082

Os arquivos `.env.staging` e `.env.production` devem conter credenciais distintas e permanecer fora do controle de versão.

Os ambientes utilizam volumes persistentes, permitindo preservar os dados do Oracle durante a recriação dos containers.

**Atenção:** não utilizar `docker compose down -v` sem intenção explícita de remover os volumes e os dados persistidos.

## 8. Pipeline de CI/CD

O pipeline está configurado no arquivo:

`.github/workflows/ci.yml`

A automação utiliza GitHub Actions e possui quatro jobs executados em sequência.

### 8.1. Build e testes

- Checkout do código-fonte.
- Configuração do Java 21.
- Inicialização de um serviço Oracle para os testes.
- Execução do Maven com `clean verify`.
- Publicação dos relatórios de testes como artefatos.

### 8.2. Construção da imagem Docker

- Configuração do Docker Buildx.
- Construção da imagem da aplicação.
- Validação do processo de containerização.

Nesta etapa, a imagem é construída para validação, sem publicação em um registry externo.

### 8.3. Deploy em staging

Após o sucesso das etapas anteriores:

- O GitHub Actions encaminha o job ao runner Windows.
- O runner executa o Docker Compose.
- A aplicação é atualizada no ambiente de staging.
- O pipeline verifica se a API responde na porta 8081.

### 8.4. Deploy em produção

Após o sucesso de staging:

- O runner executa o Docker Compose de produção.
- A aplicação é atualizada na porta 8082.
- O pipeline verifica a resposta HTTP da API.

Os deploys são executados automaticamente em pushes na branch `main`, após a conclusão bem-sucedida dos jobs anteriores.

## 9. GitHub Actions Self-hosted Runner

Para realizar os deploys locais, foi configurado um runner Windows x64 conectado ao repositório.

**Nome:** `ecotrack-windows-runner`

O runner executa os comandos Docker no computador responsável pelos ambientes.

Para receber os jobs, o computador precisa estar ligado, com o Docker Desktop em funcionamento e o runner conectado ao GitHub.

Os arquivos de configuração sensíveis são mantidos localmente em `C:\ecotrack-deploy`, fora do repositório.

## 10. Banco de dados e persistência

O EcoTrack utiliza Oracle Database 23 Free.

A inicialização e o versionamento do esquema são gerenciados pelo Flyway, com seis migrations aplicadas durante a configuração dos ambientes.

Os volumes Docker preservam os dados independentemente do ciclo de vida dos containers.

Cada ambiente possui seu próprio banco de dados e volume persistente.

## 11. Testes e validações realizadas

Durante a implementação, foram realizadas as seguintes verificações:

- Compilação do projeto com Java 21.
- Execução dos testes automatizados com Maven.
- Construção da imagem Docker.
- Inicialização e validação dos containers Oracle.
- Conexão da API com o banco de dados.
- Aplicação das seis migrations do Flyway.
- Implantação manual em staging e produção.
- Integração do runner Windows com GitHub Actions.
- Execução automatizada dos deploys em staging e produção.

### Resultado do pipeline

O pipeline completo foi executado com sucesso, apresentando:

| Job | Resultado |
|---|---|
| Build e testes | Sucesso |
| Construir imagem Docker | Sucesso |
| Deploy Staging | Sucesso |
| Deploy Production | Sucesso |

**Duração total da execução registrada:** 4 minutos e 6 segundos.

A verificação HTTP atual utiliza a resposta `401 Unauthorized` da rota protegida `/` como confirmação básica de que o servidor está respondendo. Ela não substitui testes funcionais ou uma verificação completa de saúde da aplicação.

## 12. Segurança

As principais medidas adotadas incluem:

- Uso de arquivos de ambiente para credenciais.
- Exclusão de arquivos `.env` do versionamento Git.
- Separação de bancos e redes Docker por ambiente.
- Utilização de Spring Security.
- Deploys condicionados ao sucesso das etapas anteriores.
- Restrição dos jobs de deploy aos pushes na branch `main`.

O runner local possui acesso à infraestrutura Docker, por isso a proteção da branch e dos workflows é importante para evitar a execução de código não autorizado.

## 13. Limitações e melhorias futuras

Como evolução do projeto, podem ser implementadas:

- Spring Boot Actuator para health checks completos.
- Publicação de imagens versionadas em um container registry.
- Promoção da mesma imagem Docker entre staging e produção.
- Implantação em infraestrutura de nuvem.
- Testes de integração e testes funcionais adicionais.
- Estratégias de rollback automatizado.
- Monitoramento, observabilidade e alertas.
- Gerenciamento centralizado de secrets.

Atualmente, staging e produção são ambientes Docker locais, dependentes da disponibilidade do computador que executa o runner.



