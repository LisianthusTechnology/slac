CREATE DATABASE slac

CREATE TABLE coordenador_ac (
  id_admin SERIAL NOT NULL,
  nome_admin VARCHAR(40) NOT NULL,
  login VARCHAR(20) NOT NULL,
  senha VARCHAR(8) NOT NULL,
  PRIMARY KEY (id_admin));

CREATE TABLE aluno (
  id_aluno SERIAL NOT NULL,
  cpf BIGINT NOT NULL,
  nome VARCHAR(40) NOT NULL,
  senha VARCHAR(8) NOT NULL,
  email VARCHAR(30) NOT NULL,
  matricula INTEGER NOT NULL,
  ano_ingresso INTEGER NOT NULL,
  permissao BOOLEAN NOT NULL,
  coordenador_ac_id_admin INTEGER NULL,
  PRIMARY KEY (id_aluno),
  CONSTRAINT fk_aluno_coordenador_ac FOREIGN KEY (coordenador_ac_id_admin) REFERENCES coordenador_ac (id_admin)
  );
  
CREATE TABLE modalidade (
  id_mod SERIAL NOT NULL,
  nome_mod VARCHAR(50) NOT NULL,
  PRIMARY KEY (id_mod));
  
CREATE TABLE atividade_complementar (
  id_atividade SERIAL NOT NULL,
  descricao_ac VARCHAR(100) NOT NULL,
  ch_max_ac INTEGER NULL,
  ch_min_ac INTEGER NULL,
  modalidade_id_mod INTEGER NOT NULL,
  PRIMARY KEY (id_atividade),
  CONSTRAINT fk_atividade_complementar_modalidade FOREIGN KEY (modalidade_id_mod) REFERENCES modalidade (id_mod)
  );

  CREATE TABLE participacao (
  atividade_complementar_id_atividade INTEGER NOT NULL,
  aluno_id_aluno INTEGER NOT NULL,
  id_participacao SERIAL NOT NULL,
  certificado_part VARCHAR(70) NOT NULL,
  coordenador_ac_id_admin INTEGER NULL,
  status VARCHAR(25) NOT NULL,
  data_validacao_ac DATE NULL,
  nome_ac_part VARCHAR(50) NOT NULL,
  data_inicio_ac_part DATE NOT NULL,
  ch_cadastrada_part INTEGER NOT NULL,
  ch_validada_part INTEGER NOT NULL,
  local_ac_part VARCHAR(60) NOT NULL,
  tipo_ac_part VARCHAR(15) NOT NULL,
  PRIMARY KEY (atividade_complementar_id_atividade, aluno_id_aluno, id_participacao),
  CONSTRAINT fk_atividade_complementar_has_aluno_atividade_complementar
    FOREIGN KEY (atividade_complementar_id_atividade)
    REFERENCES atividade_complementar (id_atividade),
  CONSTRAINT fk_atividade_complementar_has_aluno_aluno
    FOREIGN KEY (aluno_id_aluno)
    REFERENCES aluno (id_aluno),
  CONSTRAINT fk_atividade_complementar_has_aluno_coordenador_ac
    FOREIGN KEY (coordenador_ac_id_admin)
    REFERENCES coordenador_ac (id_admin));
    