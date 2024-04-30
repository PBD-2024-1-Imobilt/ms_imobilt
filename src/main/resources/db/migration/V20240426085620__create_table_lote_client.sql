CREATE TABLE lote_client(
    id SERIAL PRIMARY KEY NOT NULL,
    client_id INT REFERENCES client (id) NOT NULL,
    lote_id INT REFERENCES lote (id) NOT NULL,
    type VARCHAR(20) NOT NULL,
    create_at TIMESTAMP NOT NULL
);