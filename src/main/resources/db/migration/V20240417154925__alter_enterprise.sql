ALTER TABLE enterprise RENAME COLUMN title to description;

ALTER TABLE enterprise ALTER COLUMN description TYPE VARCHAR(255);

