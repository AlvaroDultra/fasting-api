# 🍽️ Fasting API - Sistema de Jejum Intermitente

<div align="center">

![Java](https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white)

**API REST completa para gerenciamento de jejum intermitente com rastreamento inteligente, metas semanais, notificações e gamificação.**

[📖 Documentação](#-documentação) • [🚀 Instalação](#-instalação) • [📡 Endpoints](#-endpoints) • [🧪 Testes](#-testes)

</div>

---

## 📋 Sobre o Projeto

A **Fasting API** é uma solução completa para acompanhamento de jejum intermitente, desenvolvida com **Java 21** e **Spring Boot 3.2.0**. O sistema oferece recursos avançados de rastreamento, análise de dados e gamificação para motivar usuários a manter a consistência em seus jejuns.

### ✨ Principais Funcionalidades

#### 🔐 **Autenticação e Segurança**
- Autenticação JWT com tokens de 24 horas
- Senhas criptografadas com BCrypt
- Proteção de endpoints por autorização

#### 👤 **Gerenciamento de Usuários**
- CRUD completo de usuários
- Sistema de níveis e progressão (5 níveis)
- Sistema de XP por conquistas
- Perfil personalizado com protocolo padrão

#### 🍽️ **Controle de Jejuns**
- Início e fim de jejuns com timestamps
- Cálculo automático de duração
- Suporte a múltiplos protocolos (12/12, 14/10, 16/8, 18/6, 20/4, 24h, 36h, Flex)
- Validação de metas atingidas
- Histórico completo de jejuns
- Jejum ativo com cronômetro em tempo real:
  - Tempo decorrido
  - Tempo restante
  - Porcentagem de conclusão
  - Previsão de término

#### 🎯 **Metas Semanais**
- Criação automática de metas semanais
- Acompanhamento de progresso
- Sistema de selos de desempenho:
  - 🥇 Semana Perfeita (100% da meta)
  - 🥈 Semana Boa (70%+)
  - 🥉 Semana de Recomeço (mínimo 1 jejum)
  - 🔥 Semana Longa (jejum 24h+)
  - 📊 Em Progresso
- Estatísticas detalhadas (jejuns concluídos, horas totais, porcentagem)

#### 🔔 **Sistema de Notificações**
- Notificações inteligentes baseadas em eventos:
  - Metade do jejum concluída
  - Falta 1 hora para a meta
  - Meta alcançada
  - Jejum estendido
  - Usuário inativo
- Gerenciamento de leitura de notificações
- Múltiplos canais (EMAIL, PUSH, WEBHOOK)

#### 💡 **Dicas Personalizadas**
- 14 dicas pré-cadastradas
- 5 categorias temáticas:
  - **Consistência**: Dicas para manter regularidade
  - **Hidratação**: Lembretes de hidratação
  - **Protocolo**: Sugestões de protocolos
  - **Horário**: Otimização de horários
  - **Desempenho**: Melhorias de performance
- Sistema de dicas aleatórias por categoria
- Histórico de dicas enviadas com feedback

#### 📊 **Relatórios e Estatísticas**
- Análise de desempenho semanal e mensal
- Gráficos de progresso
- Médias e totalizações
- Identificação de padrões

---

## 🛠️ Tecnologias Utilizadas

### Core
- **Java 21** - Linguagem de programação
- **Spring Boot 3.2.0** - Framework principal
- **Maven** - Gerenciador de dependências

### Spring Ecosystem
- **Spring Data JPA** - Persistência de dados
- **Spring Security** - Segurança e autenticação
- **Spring Web** - APIs RESTful
- **Spring Validation** - Validação de dados

### Database
- **PostgreSQL 16** - Banco de dados relacional
- **Hibernate** - ORM (Object-Relational Mapping)

### Security
- **JWT (jjwt 0.12.3)** - JSON Web Tokens
- **BCrypt** - Hash de senhas

### Utilities
- **Lombok** - Redução de boilerplate
- **Jackson** - Serialização JSON

---

## 📦 Pré-requisitos

Antes de começar, certifique-se de ter instalado:

- **Java 21 ou superior** ([Download](https://www.oracle.com/java/technologies/downloads/))
- **Maven 3.6+** ([Download](https://maven.apache.org/download.cgi))
- **PostgreSQL 16** ([Download](https://www.postgresql.org/download/))
- **Git** ([Download](https://git-scm.com/downloads))

---

## 🚀 Instalação e Execução

### 1️⃣ Clonar o Repositório
```bash
git clone https://github.com/seu-usuario/fasting-api.git
cd fasting-api
```

### 2️⃣ Configurar o Banco de Dados

#### Iniciar o PostgreSQL
```bash
# Ubuntu/Debian
sudo systemctl start postgresql

# macOS (Homebrew)
brew services start postgresql
```

#### Criar o Banco de Dados
```bash
sudo -u postgres psql
```

Execute os seguintes comandos SQL:
```sql
-- Criar usuário
CREATE USER fasting_user WITH PASSWORD 'fasting_pass123';

-- Criar banco de dados
CREATE DATABASE fasting_db OWNER fasting_user;

-- Conceder privilégios
GRANT ALL PRIVILEGES ON DATABASE fasting_db TO fasting_user;

-- Conectar ao banco
\c fasting_db

-- Conceder permissões no schema
GRANT ALL ON SCHEMA public TO fasting_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO fasting_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO fasting_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO fasting_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO fasting_user;

-- Sair
\q
```

### 3️⃣ Configurar Variáveis de Ambiente (Opcional)

O arquivo `application.properties` já está configurado com valores padrão. Se preferir, você pode criar um arquivo `application-prod.properties` ou usar variáveis de ambiente:
```bash
export DB_URL=jdbc:postgresql://localhost:5432/fasting_db
export DB_USERNAME=fasting_user
export DB_PASSWORD=fasting_pass123
export JWT_SECRET=sua_chave_secreta_aqui
```

### 4️⃣ Compilar o Projeto
```bash
./mvnw clean install
```

### 5️⃣ Executar a Aplicação
```bash
./mvnw spring-boot:run
```

A API estará disponível em: **http://localhost:8080**

### 6️⃣ Verificar se está Funcionando
```bash
curl http://localhost:8080/api/auth/login
```

Se retornar uma mensagem de erro HTTP 405 (método não permitido), significa que a API está rodando corretamente!

---

## 📡 Endpoints da API

### 🔐 Autenticação (Público)

| Método | Endpoint | Descrição | Body |
|--------|----------|-----------|------|
| `POST` | `/api/auth/registrar` | Registrar novo usuário | `{ nome, email, senha, protocoloPadrao }` |
| `POST` | `/api/auth/login` | Fazer login | `{ email, senha }` |

**Exemplo de Registro:**
```bash
curl -X POST http://localhost:8080/api/auth/registrar \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "email": "joao@email.com",
    "senha": "senha123",
    "protocoloPadrao": "JEJUM_16_8"
  }'
```

**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "tipo": "Bearer",
  "usuario": {
    "id": 1,
    "nome": "João Silva",
    "email": "joao@email.com",
    "protocoloPadrao": "JEJUM_16_8",
    "nivelUsuario": 1,
    "xpTotal": 0
  }
}
```

---

### 👤 Usuários (Autenticado)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/api/usuarios` | Listar todos os usuários |
| `GET` | `/api/usuarios/{id}` | Buscar usuário por ID |
| `GET` | `/api/usuarios/email/{email}` | Buscar usuário por email |
| `PUT` | `/api/usuarios/{id}` | Atualizar usuário |
| `DELETE` | `/api/usuarios/{id}` | Desativar usuário |
| `POST` | `/api/usuarios/{id}/xp?quantidade={xp}` | Adicionar XP |

**Exemplo (com autenticação):**
```bash
curl -X GET http://localhost:8080/api/usuarios/1 \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

---

### 🍽️ Jejuns (Autenticado)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/api/jejuns/iniciar/{usuarioId}` | Iniciar novo jejum |
| `PUT` | `/api/jejuns/finalizar/{jejumId}` | Finalizar jejum |
| `PUT` | `/api/jejuns/cancelar/{jejumId}` | Cancelar jejum |
| `GET` | `/api/jejuns/ativo/{usuarioId}` | Buscar jejum ativo |
| `GET` | `/api/jejuns/usuario/{usuarioId}` | Listar todos os jejuns |
| `GET` | `/api/jejuns/usuario/{usuarioId}/periodo` | Jejuns por período |

**Exemplo - Iniciar Jejum:**
```bash
curl -X POST http://localhost:8080/api/jejuns/iniciar/1 \
  -H "Authorization: Bearer SEU_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "inicio": "2025-11-14T20:00:00",
    "metaHoras": 16,
    "protocolo": "JEJUM_16_8",
    "observacoes": "Meu primeiro jejum"
  }'
```

---

### 🎯 Metas Semanais (Autenticado)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/api/metas-semanais/usuario/{usuarioId}` | Criar nova meta |
| `GET` | `/api/metas-semanais/usuario/{usuarioId}/atual` | Buscar meta da semana atual |
| `PUT` | `/api/metas-semanais/{metaId}/atualizar-progresso` | Atualizar progresso |
| `GET` | `/api/metas-semanais/usuario/{usuarioId}` | Listar todas as metas |
| `GET` | `/api/metas-semanais/usuario/{usuarioId}/recentes` | Metas recentes |

---

### 🔔 Notificações (Autenticado)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/api/notificacoes/usuario/{usuarioId}` | Listar todas |
| `GET` | `/api/notificacoes/usuario/{usuarioId}/nao-lidas` | Listar não lidas |
| `PUT` | `/api/notificacoes/{id}/marcar-lida` | Marcar como lida |
| `PUT` | `/api/notificacoes/usuario/{usuarioId}/marcar-todas-lidas` | Marcar todas |

---

### 💡 Dicas (Autenticado)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/api/dicas` | Listar todas as dicas |
| `GET` | `/api/dicas/aleatoria/usuario/{id}?categoria={cat}` | Dica aleatória |
| `GET` | `/api/dicas/categoria/{categoria}` | Dicas por categoria |

**Categorias disponíveis:**
- `CONSISTENCIA`
- `HIDRATACAO`
- `PROTOCOLO`
- `HORARIO`
- `DESEMPENHO`

---

## 🧪 Testes Automatizados

### Executar Script de Testes

O projeto inclui um script bash completo para testar todos os endpoints:
```bash
chmod +x test-complete-api-fixed.sh
./test-complete-api-fixed.sh
```

### Resultado Esperado
```
✅ Autenticação (Registro + Login)
✅ Usuários (CRUD + XP)
✅ Jejuns (Iniciar, Finalizar, Listar)
✅ Metas Semanais (Buscar, Atualizar Progresso)
✅ Notificações (Criar, Listar, Marcar)
✅ Dicas (Listar, Aleatória, Por Categoria)

🎉 API FUNCIONANDO 100% PERFEITAMENTE!
```

### Importar Collection no Insomnia/Postman

O arquivo `fasting-api-insomnia.json` contém todos os endpoints configurados. Para importar:

1. Abra o Insomnia ou Postman
2. Clique em **Import**
3. Selecione o arquivo `fasting-api-insomnia.json`
4. Configure o token JWT nas variáveis de ambiente

---

## 📊 Modelo de Dados

### Entidades Principais
```
Usuario
├── id (Long)
├── nome (String)
├── email (String - único)
├── senha (String - hash BCrypt)
├── protocoloPadrao (Enum)
├── nivelUsuario (Integer 1-5)
├── xpTotal (Integer)
└── ativo (Boolean)

Jejum
├── id (Long)
├── usuario (Usuario)
├── inicio (LocalDateTime)
├── fim (LocalDateTime)
├── status (ATIVO, CONCLUIDO, CANCELADO)
├── duracaoHoras (Double)
├── metaHoras (Integer)
├── protocolo (Enum)
├── metaAtingida (Boolean)
└── observacoes (String)

MetaSemanal
├── id (Long)
├── usuario (Usuario)
├── semanaInicio (LocalDate)
├── semanaFim (LocalDate)
├── metaDias (Integer)
├── metaHorasTotal (Integer)
├── protocoloAlvo (Enum)
├── jejunsConcluidos (Integer)
├── horasTotais (Double)
├── metaCumprida (Boolean)
└── seloSemana (String)
```

---

## 📁 Estrutura do Projeto
```
fasting-api/
├── src/
│   ├── main/
│   │   ├── java/com/fastingapp/api/
│   │   │   ├── config/              # Configurações (Security, JWT, CORS)
│   │   │   ├── controller/          # Controllers REST
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   ├── exception/           # Exception Handlers
│   │   │   ├── model/
│   │   │   │   ├── entity/          # Entidades JPA
│   │   │   │   └── enums/           # Enumerações
│   │   │   ├── repository/          # Repositories JPA
│   │   │   └── service/             # Lógica de Negócio
│   │   └── resources/
│   │       └── application.properties
│   └── test/                        # Testes unitários
├── test-complete-api-fixed.sh       # Script de testes
├── fasting-api-insomnia.json        # Collection Insomnia
├── .gitignore
├── pom.xml
└── README.md
```

---

## 🔒 Segurança

- **Senhas**: Criptografadas com BCrypt (salt automático)
- **Tokens JWT**: Expira em 24 horas
- **Endpoints**: Protegidos por autenticação (exceto `/api/auth/**`)
- **CORS**: Configurado para aceitar requisições de qualquer origem (configurável)
- **SQL Injection**: Prevenido pelo uso de JPA/Hibernate
- **XSS**: Headers de segurança configurados

---

## 🚀 Deploy

### Heroku
```bash
# Criar app no Heroku
heroku create fasting-api

# Adicionar PostgreSQL
heroku addons:create heroku-postgresql:essential-0

# Configurar variáveis
heroku config:set JWT_SECRET=sua_chave_secreta

# Deploy
git push heroku main
```

### Railway

1. Conecte seu repositório no [Railway](https://railway.app)
2. Adicione PostgreSQL como serviço
3. Configure as variáveis de ambiente
4. Deploy automático

### Docker (Futuro)
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

---

## 📈 Roadmap

- [ ] Testes unitários (JUnit + Mockito)
- [ ] Documentação Swagger/OpenAPI
- [ ] Docker Compose
- [ ] CI/CD com GitHub Actions
- [ ] Integração com serviço de email
- [ ] Push notifications
- [ ] API de estatísticas avançadas
- [ ] Exportação de relatórios (PDF/Excel)
- [ ] Integração com wearables

---

## 🤝 Contribuindo

Contribuições são bem-vindas! Para contribuir:

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/MinhaFeature`)
3. Commit suas mudanças (`git commit -m 'Adiciona MinhaFeature'`)
4. Push para a branch (`git push origin feature/MinhaFeature`)
5. Abra um Pull Request

---

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## 👨‍💻 Autor

**Seu Nome**
- GitHub: [@seu-usuario](https://github.com/seu-usuario)
- LinkedIn: [Seu Nome](https://linkedin.com/in/seu-perfil)
- Email: seu@email.com

---

## 🙏 Agradecimentos

- Comunidade Spring Boot
- Stack Overflow
- Documentação oficial do Spring

---

<div align="center">

**⭐ Se este projeto foi útil, deixe uma estrela!**

Desenvolvido com ☕ e ❤️

</div>
