ALTER TABLE participacao ALTER COLUMN certificado_part TYPE bytea USING certificado_part::bytea

ALTER TABLE modalidade ALTER COLUMN nome_mod TYPE varchar(100) USING nome_mod::varchar(100)

<
ALTER TABLE participacao ALTER COLUMN certificado_part TYPE varchar(200) 

ALTER TABLE aluno ALTER COLUMN matricula TYPE bigint

ALTER TABLE aluno DROP COLUMN coordenador_ac_id_admin 