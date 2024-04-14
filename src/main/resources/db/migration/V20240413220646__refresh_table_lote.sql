TRUNCATE lote;

ALTER SEQUENCE public.lote_id_seq RESTART 1;

INSERT INTO lote (enterprise_id, block, lote)
VALUES (1, '24', '24'),
(1, '24', '12'),
(2, '24', '24');