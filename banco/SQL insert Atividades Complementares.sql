insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Participação em Eventos acadêmicos, científicos, culturais sociais (Congressos, seminários, palestras, semana universitária, conferencia, jornada, fórum etc.)', null, null, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Participação em Eventos acadêmicos, científicos, culturais sociais com atividade de apresentação de trabalho', 10, 10, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Participação em Eventos acadêmicos, científicos, culturais sociais como organizador', null, null, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Participação Eventos acadêmicos, científicos, culturais sociais com atividade de minicurso', null, null, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Ministrante de oficinas e ou de cursos vinculados aos projetos da Universidade', null, null, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Participação Eventos acadêmicos, científicos, culturais sociais com apresentação de pôster', 10, 10, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Estágios extracurriculares', null, null, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Representação estudantil', 12, 8, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Matrícula e aprovação em disciplinas de cursos superior não prevista na matriz curricular do curso', 40, 10, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Mostra Cultural', 20, 10, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Exposição artística cultural', 20, 10, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Apresentações: musicais e ou teatrais', 20, 10, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Organização de eventos artísticos culturais', 10, 10, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Bolsa Permanência', 30, 10, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Bolsa Ações Extensionistas', 30, 10, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Bolsa Desenvolvimento Institucional', 30, 10, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Bolsa Mobilidade Nacional', 30, 10, 1);

ALTER TABLE atividade_complementar ALTER COLU	MN descricao_ac TYPE varchar(200) USING descricao_ac::varchar(200)