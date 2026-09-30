-- CREATE DATABASE poo_exercicios;

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
