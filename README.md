# 📘 -csw25-grupof-sarc-spring

## 🧾 Descrição do Projeto

Este projeto utiliza o **Keycloak** como servidor de autenticação e autorização, integrado com o backend desenvolvido em **Spring Boot**. O Keycloak gerencia usuários, roles e permissões, enquanto o backend fornece APIs protegidas por autenticação JWT.

---

## ✅ Pré-requisitos

Certifique-se de ter os seguintes itens instalados:

- [x] **Docker** e **Docker Compose**
- [x] **Java 17+**
- [x] **Maven**
- [x] **Terraform** (para infraestrutura AWS)

---

## ⚙️ Configuração do Keycloak

O Keycloak está configurado para rodar em um contêiner Docker. Ele utiliza um arquivo de exportação (`sarc-realm.json`) para configurar o realm, roles e usuários.

### 🚀 Passos para configurar o Keycloak

1. **Subir o Keycloak com Docker Compose**

   ```bash
   docker compose up --build
   ```

   Isso iniciará o Keycloak na porta `8080`.

2. **Acessar o Keycloak**
   - URL: [http://localhost:8080](http://localhost:8080)
   - Usuário administrador: `admin`
   - Senha: `admin`

3. **Importação automática do Realm**

   O realm `sarc-realm` será automaticamente importado ao iniciar o contêiner.

#### 🔐 Configurações do Realm

- **Nome do Realm**: `sarc-realm`
- **Roles**:
  - `ADMIN`
  - `PROFESSOR`
  - `ALUNO`
  - `COORDENADOR`

#### Usuários de exemplo

| Usuário         | Email                        | Senha   | Role        |
|-----------------|------------------------------|---------|-------------|
| admin           | admin@pucrs.br               | admin123| ADMIN       |
| professor       | professor@pucrs.br           | prof123 | PROFESSOR   |
| aluno           | aluno@pucrs.br               | aluno123| ALUNO       |
| andresilva      | andresilva@edu.pucrs.br      | 123456  | ALUNO       |
| edgar dos santos| edgardossantos@edu.pucrs.br  | 123456  | ADMIN       |
| maria eduarda   | mariaeduarda@edu.pucrs.br    | 123456  | COORDENADOR |
| john            | john@edu.pucrs.br            | 123456  | PROFESSOR   |

---

## ▶️ Executando o Backend

### 1. Compilar o projeto

```bash
./mvnw clean install
```

### 2. Subir o backend

```bash
./mvnw spring-boot:run
```

- Backend: [http://localhost:8081](http://localhost:8081)
- Swagger UI: [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)

---

## 🔐 Autenticação e Requisições

### 1. Login e Obtenção do Token

**POST** `/api/auth/login`  
URL completa: `http://localhost:8081/api/auth/login`

**Body:**

```json
{
  "username": "john@edu.pucrs.br",
  "password": "123456"
}
```

**Resposta:**
Token JWT que deve ser usado nas próximas requisições.

### 2. Requisição com Token

**Exemplo**:

```http
GET /api/admin/some-resource
Authorization: Bearer SEU_TOKEN_AQUI
```

---

## 📦 Infraestrutura AWS (Terraform)

O deploy é feito na AWS usando ECS Fargate e ECR.

### Recursos Criados:

- `aws_ecr_repository`: Repositório Docker
- `aws_ecs_cluster`: Cluster ECS Fargate
- `aws_security_group`: Acesso HTTP (porta 8080)
- `aws_ecs_task_definition`: Definição da task com imagem Docker
- `aws_ecs_service`: Serviço ECS com balanceamento e IP público

### Variáveis Necessárias:

- `var.vpc_id`: ID da VPC
- `var.subnet_id`: Subnet pública
- `var.execution_role_arn`: Role com permissões para ECS

---

## 🚀 CI/CD com GitHub Actions

### 📋 Pipeline `.github/workflows/ci-cd.yml`

#### Fases:

1. **Build e Testes**
   - Compila o projeto com Maven
   - Executa testes unitários

2. **Deploy**
   - Autentica na AWS
   - Constrói e envia imagem para o ECR
   - Renderiza nova Task Definition
   - Atualiza o serviço ECS automaticamente

#### Secrets Necessárias no GitHub:

- `AWS_ACCESS_KEY_ID`
- `AWS_SECRET_ACCESS_KEY`

---

## 📁 Estrutura do Projeto

- `docker-compose.yml`: Infraestrutura local (Keycloak)
- `keycloak-export/`: Realm e configurações
- `requests/`: Requisições Postman
- `terraform/`: Infraestrutura AWS com Terraform
- `src/`: Código fonte do backend
- `SecurityConfig.java`: Configuração JWT + Keycloak

---

## 🛠️ Comandos Úteis

- **Parar Docker**: `docker-compose down`
- **Logs Keycloak**: `docker-compose logs keycloak`
- **Reiniciar Keycloak**: `docker-compose restart keycloak`

### Exportar Realm Keycloak:

```bash
docker exec -it keycloak /bin/bash
/opt/keycloak/bin/kc.sh export --dir /opt/keycloak/data/export --realm sarc --users realm_file
docker cp keycloak:/opt/keycloak/data/export/sarc-realm.json ./keycloak-export/
```

---


# Entrega 3 FRONTEND
Siga as orientações do README do frontend para configurar o ambiente.

```bash 
cd .. 
cd frontend
git clone https://github.com/andredame/csw-front-react-tailwind.git
```

## 📚 Referências

- [Keycloak Documentation](https://www.keycloak.org/documentation.html)
- [Spring Security OAuth2](https://spring.io/projects/spring-security)
- [Terraform AWS Provider](https://registry.terraform.io/providers/hashicorp/aws/latest/docs)
- [GitHub Actions for ECS](https://github.com/aws-actions/amazon-ecs-deploy-task-definition)
