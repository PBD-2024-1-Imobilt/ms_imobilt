CREATE TABLE Block (
    id SERIAL PRIMARY KEY NOT NULL,
    description VARCHAR(100) NOT NULL,
    enterprise_id INT REFERENCES enterprise (id) NOT NULL
);
