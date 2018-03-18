ALTER TABLE participacao ALTER COLUMN certificado_part TYPE bytea USING certificado_part::bytea

ALTER TABLE modalidade ALTER COLUMN nome_mod TYPE varchar(100) USING nome_mod::varchar(100)