
# 📘 -csw25-grupof-sarc-spring

## 🧾 Descrição do Projeto

Este projeto utiliza o **Keycloak** como servidor de autenticação e autorização, integrado com o backend desenvolvido em **Spring Boot**. O Keycloak gerencia usuários, roles e permissões, enquanto o backend fornece APIs protegidas por autenticação JWT.

---

## ✅ Pré-requisitos

Certifique-se de ter os seguintes itens instalados:

- [x] **Docker** e **Docker Compose**
- [x] **Java 17+**
- [x] **Maven**

---

## ⚙️ Configuração do Keycloak

O Keycloak está configurado para rodar em um contêiner Docker. Ele utiliza um arquivo de exportação (`sarc-realm.json`) para configurar o realm, roles e usuários.

### 🚀 Passos para configurar o Keycloak

1. **Subir o Keycloak com Docker Compose**  
   Na raiz do projeto, execute:

   ```bash
   docker compose up --build
   ```

   Isso iniciará o Keycloak na porta `8080`.

2. **Acessar o Keycloak**  
   - URL: [http://localhost:8080](http://localhost:8080)  
   - Usuário administrador: `admin`  
   - Senha: `admin`

3. **Importação automática do Realm**  
   O realm `sarc-realm` será automaticamente importado ao iniciar o contêiner. Ele contém as configurações de roles e permissões necessárias para o projeto.

#### 🔐 Configurações do Realm

- **Nome do Realm**: `sarc-realm`
- **Roles**:
  - `ADMIN`
  - `PROFESSOR`
  - `ALUNO`
  - `COORDENADOR`

- **Usuários de exemplo**:

| Usuário   | Email                | Senha     | Role      |
|-----------|----------------------|-----------|-----------|
| admin     | admin@pucrs.br       | admin123  | ADMIN     |
| professor | professor@pucrs.br   | prof123   | PROFESSOR |
| aluno     | aluno@pucrs.br       | aluno123  | ALUNO     |
| aluno     | aluno@pucrs.br       | aluno123  | ALUNO     |

---

| Usuário   | Email                | Senha     | Role      |
|-----------|----------------------|-----------|-----------|
| andresilva     | andresilva@edu.pucrs.br       | 123456  | ALUNO     |
| edgar dos santos| edgardossantos@edu.pucrs.br   | 123456   | ADMIN |
| maria eduarda     | mariaeduarda@edu.pucrs.br       | 123456  | COORDENADOR     |
| john     | john@edu.pucrs.br       | 123456  | PROFESSOR     |

## ▶️ Executando o Backend

### 1. Compilar o projeto

Execute o Maven:

```bash
./mvnw clean install
```

### 2. Subir o backend

Após compilar, execute:

```bash
./mvnw spring-boot:run
```

- O backend estará disponível em: [http://localhost:8081](http://localhost:8081)

---

## 🔐 Autenticação e Requisições

### 1. Login e Obtenção do Token

Para acessar as APIs protegidas, você precisa fazer login no Keycloak e obter um token JWT.

- **Endpoint**: `POST /api/auth/login`  
- **URL completa**: `http://localhost:8081/api/auth/login`

**Body:**
```json
{
  "username": "john@edu.pucrs.br",
  "password": "123456"
}
```

**Resposta:**  
Retorna o token JWT no corpo da resposta para ser utilizado nas requisições subsequentes de acesso às APIs( por exemplo nesse caso é um professor acessando a API)

---

### 2. Usando o Token para Acessar APIs

Inclua o token JWT no cabeçalho `Authorization` das requisições:

**Exemplo de Requisição Protegida:**

- **Endpoint**: `GET /api/admin/some-resource`  
- **URL**: `http://localhost:8081/api/admin/some-resource`

**Cabeçalho:**
```
Authorization: Bearer SEU_TOKEN_AQUI
```

---

## 🛠️ Comandos Úteis

- **Parar os contêineres Docker**:

```bash
docker-compose down
```

- **Verificar logs do Keycloak**:

```bash
docker-compose logs keycloak
```

- **Reiniciar o Keycloak**:

```bash
docker-compose restart keycloak
```

---

## 📁 Estrutura do Projeto

- **Keycloak**: Configurado no arquivo `docker-compose.yml` e utiliza o arquivo de exportação `keycloak-export/sarc-realm.json`.
- **Backend**: APIs protegidas por autenticação JWT, configuradas no arquivo `SecurityConfig.java`.

---

## 📚 Referências

- [Keycloak Documentation](https://www.keycloak.org/documentation.html)
- [Spring Security OAuth2](https://spring.io/projects/spring-security)
