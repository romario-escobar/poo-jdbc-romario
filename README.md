<div align="center">

# UNILASALLE-RJ
## CENTRO UNIVERSITÁRIO LA SALLE DO RIO DE JANEIRO
### CURSO DE BACHARELADO EM SISTEMAS DE INFORMAÇÃO
#### PROGRAMAÇÃO ORIENTADA A OBJETOS
##### G1 - TRABALHO PRÁTICO
###### JDBC e PostgreSQL

![Java](https://img.shields.io/badge/Java-8%2B-orange?logo=openjdk&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-JDBC-336791?logo=postgresql&logoColor=white)

</div>

---

## 👤 Identificação

| | |
|---|---|
| **Aluno** | Romário Escobar de Souza |
| **Professor** | Alexandre Neves Louzada |
| **Tema 14** | Catálogo de Filmes e Gêneros Cinematográficos (`GeneroFilme` 1:N `Filme`) |

---

## 📋 Instruções do trabalho

Projeto do *Caderno de Projetos Práticos: POO em Java com Associação entre Classes, JDBC e PostgreSQL*. Cada estudante implementa um estudo de caso individual com associação 1:N entre duas classes, com os seguintes requisitos mínimos:

1. **ConnectionFactory** conectando ao PostgreSQL com o driver `org.postgresql.Driver`.
2. **Duas classes de modelo** com relacionamento 1:N (a classe do lado "N" possui uma referência ao objeto do lado "1").
3. **Duas classes DAO** com CRUD completo (`salvar`, `buscarPorId`, `listarTodos`, `atualizar`, `deletar`), incluindo ao menos uma consulta com `INNER JOIN` entre as duas tabelas.
4. **Tratamento de exceções** com `try-catch` para `SQLException`.
5. **Classe executável (Main)** com menu interativo no console, permitindo inserção, listagem com dados associados, atualização e remoção.

Arquitetura em camadas: `model`, `dao`, `util` e `app`. Uso de `PreparedStatement`, `ResultSet` e try-with-resources.

---

## 🎬 Estudo de caso

**Tema:** Catálogo de Filmes e Gêneros Cinematográficos

**Enunciado:** Uma rede de cinema necessita organizar os títulos cinematográficos exibidos em suas salas de acordo com o gênero predominante (ex.: Ação, Ficção Científica, Animação), permitindo buscas estruturadas por categoria.

**Associação:** Um gênero cinematográfico (ex.: Ficção Científica, Animação, Drama) reúne diversos filmes. Cada filme possui seu gênero principal.

| Classe | Lado | Atributos principais |
|---|---|---|
| `GeneroFilme` | 1 | id (int), descricao (String), classificacaoIndicativa (String) |
| `Filme` | N | id (int), tituloOriginal (String), tituloTraduzido (String), duracaoMinutos (int), anoLancamento (int), genero (GeneroFilme) |

---

## 🗂️ Estrutura do projeto

```
poo-jdbc-romario/
├── lib/postgresql-42.7.13.jar
├── sql/
│   ├── script.sql
│   └── backup.sql
├── src/
│   ├── app/    Main
│   ├── dao/    GeneroFilmeDAO, FilmeDAO
│   ├── model/  GeneroFilme, Filme
│   └── util/   ConnectionFactory
├── .gitignore
└── README.md
```

---

## 🚀 Como executar

1. Ter o JDK e o PostgreSQL instalados.
2. Criar o banco: `CREATE DATABASE poo_exercicios;`
3. Executar o script SQL abaixo (ou `sql/script.sql`) no banco `poo_exercicios`.
4. Ajustar usuário e senha em `src/util/ConnectionFactory.java`.
5. O driver JDBC está em `lib/postgresql-42.7.13.jar`. Caso não esteja no repositório, baixe em https://jdbc.postgresql.org e coloque na pasta `lib/`.
6. Compilar e executar, na raiz do projeto:

```
javac -cp lib/postgresql-42.7.13.jar -d bin src/model/*.java src/util/*.java src/dao/*.java src/app/*.java
java -cp bin:lib/postgresql-42.7.13.jar app.Main
```

> 💡 No Windows, use `;` no lugar de `:` no classpath. No VSCode, basta ter o jar em `lib/` (aparece em *Referenced Libraries*) e executar o `Main`.

### 🖥️ Menu do sistema

O `Main` abre um menu interativo no console:

| Opção | Ação |
|---|---|
| 1 a 4 | Cadastrar, listar, atualizar e remover **gêneros** |
| 5 | Cadastrar filme |
| 6 | Listar filmes com seus gêneros (`INNER JOIN`) |
| 7 | Buscar filme por ID |
| 8 e 9 | Atualizar e remover **filmes** |
| 0 | Sair |

Para limpar as tabelas entre os testes:

```sql
TRUNCATE filme, genero_filme RESTART IDENTITY CASCADE;
```

---

## 🛢️ Script SQL

```sql
CREATE TABLE genero_filme (
    id SERIAL PRIMARY KEY,
    descricao VARCHAR(80) NOT NULL,
    classificacao_indicativa VARCHAR(10) NOT NULL
);

CREATE TABLE filme (
    id SERIAL PRIMARY KEY,
    titulo_original VARCHAR(150) NOT NULL,
    titulo_traduzido VARCHAR(150),
    duracao_minutos INT NOT NULL,
    ano_lancamento INT NOT NULL,
    genero_id INT NOT NULL REFERENCES genero_filme(id) ON DELETE RESTRICT
);
```

---

## 💾 Banco de dados pronto (opcional)

O arquivo `sql/backup.sql` contém a estrutura das tabelas e alguns dados de exemplo. Para restaurá-lo:

1. Criar o banco vazio:

```
psql -U postgres -c "CREATE DATABASE poo_exercicios;"
```

2. Restaurar o backup no banco criado:

```
psql -U postgres -d poo_exercicios -f sql/backup.sql
```

> ⚠️ Use este arquivo **no lugar** do `script.sql`, nunca os dois juntos, para não tentar criar as tabelas duas vezes.