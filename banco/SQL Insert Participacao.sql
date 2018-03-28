insert into participacao(atividade_complementar_id_atividade, aluno_id_aluno, id_participacao, 
certificado_part, coordenador_ac_id_admin, status, data_validacao_ac, nome_ac_part,
data_inicio_ac_part, ch_cadastrada_part, ch_validada_part, local_ac_part, tipo_ac_part) 
values('1', '1', '1', 'teste', '1', 'teste', '2018-02-25', 'teste', '12-02-2018', '50', '30', 'gyn', 'presencial')

insert into coordenador_ac(nome_admin, login, senha) values ('Joilson', 'joilson.brito', '1234')

insert into aluno(cpf, nome, senha, email, matricula, ano_ingresso, permissao, coordenador_ac_id_admin) values ('12345678901', 'Viviane', 'teste', 'fulano@gamil.com', '12345678', '2017', 'true', '1')

insert into modalidade(nome_mod) values ('modalidade teste')

insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('teste1', '30', '10', '1')