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

ALTER TABLE atividade_complementar ALTER COLU	MN descricao_ac TYPE varchar(200) USING descricao_ac::varchar(200)