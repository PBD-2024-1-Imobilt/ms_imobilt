CREATE TABLE Lote (
    id SERIAL PRIMARY KEY NOT NULL,
    enterprize_id INT REFERENCES enterprize (id) NOT NULL,
    lote VARCHAR(20) NOT NULL,
    block VARCHAR(20) NOT NULL
);