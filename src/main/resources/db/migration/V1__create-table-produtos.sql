CREATE TABLE produtos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255),
    url_imagem VARCHAR(255),
    preco  DOUBLE PRECISION,
    descricao TEXT
);