insert into participacao(atividade_complementar_id_atividade, aluno_id_aluno, id_participacao, 
certificado_part, coordenador_ac_id_admin, status, data_validacao_ac, nome_ac_part,
data_inicio_ac_part, ch_cadastrada_part, ch_validada_part, local_ac_part, tipo_ac_part) 
values('1', '1', '1', 'teste', '1', 'teste', '2018-02-25', 'teste', '12-02-2018', '50', '30', 'gyn', 'presencial')

insert into coordenador_ac(nome_admin, login, senha) values ('Joilson', 'joilson.brito', '1234')

insert into aluno(cpf, nome, senha, email, matricula, ano_ingresso, permissao, coordenador_ac_id_admin) values ('12345678901', 'Viviane', 'teste', 'fulano@gamil.com', '12345678', '2017', 'true', '1')

insert into modalidade(nome_mod) values ('modalidade teste')
insert into modalidade(nome_mod) values ('Atividades de forma��o social, humana e cultural')
insert into modalidade(nome_mod) values ('Atividades de cunnho comunit�rio e de interesse coletivo')
insert into modalidade(nome_mod) values ('Atividades de ensino, de forma��o profissional, pesquisa, inicia��o cient�fica e tecnol�gica')



insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('teste1', '30', '10', '1');
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Participa��o em Eventos acad�micos, cient�ficos, culturais sociais (Congressos, semin�rios, palestras, semana universit�ria, conferencia, jornada, f�rum etc.)', null, null, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Participa��o em Eventos acad�micos, cient�ficos, culturais sociais com atividade de apresenta��o de trabalho', 10, 10, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Participa��o em Eventos acad�micos, cient�ficos, culturais sociais como organizador', null, null, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Participa��o Eventos acad�micos, cient�ficos, culturais sociais com atividade de minicurso', null, null, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Ministrante de oficinas e ou de cursos vinculados aos projetos da Universidade', null, null, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Participa��o Eventos acad�micos, cient�ficos, culturais sociais com apresenta��o de p�ster', 10, 10, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Est�gios extracurriculares', null, null, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Representa��o estudantil', 8, 12, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Matr�cula e aprova��o em disciplinas de cursos superior n�o prevista na matriz curricular do curso', 10, 40, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Mostra Cultural', 10, 20, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Exposi��o art�stica cultural', 10, 20, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Apresenta��es: musicais e ou teatrais', 10, 20, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Organiza��o de eventos art�sticos culturais', 10, 10, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Bolsa Perman�ncia', 10, 30, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Bolsa A��es Extensionistas', 10, 30, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Bolsa Desenvolvimento Institucional', 10, 30, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Bolsa Mobilidade Nacional', 10, 30, 1);
