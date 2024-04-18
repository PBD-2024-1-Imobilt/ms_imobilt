ALTER TABLE lote RENAME COLUMN lote to description;

TRUNCATE TABLE lote;

ALTER TABLE lote ADD COLUMN block_id INT REFERENCES block (id) NOT NULL;

ALTER TABLE lote DROP COLUMN enterprise_id;