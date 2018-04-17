insert into participacao(atividade_complementar_id_atividade, aluno_id_aluno, id_participacao, 
certificado_part, coordenador_ac_id_admin, status, data_validacao_ac, nome_ac_part,
data_inicio_ac_part, ch_cadastrada_part, ch_validada_part, local_ac_part, tipo_ac_part) 
values('1', '1', '1', 'teste', '1', 'teste', '2018-02-25', 'teste', '12-02-2018', '50', '30', 'gyn', 'presencial')

insert into coordenador_ac(nome_admin, login, senha) values ('Joilson', 'joilson.brito', '1234')

insert into aluno(cpf, nome, senha, email, matricula, ano_ingresso, permissao, coordenador_ac_id_admin) values ('12345678901', 'Viviane', 'teste', 'fulano@gamil.com', '12345678', '2017', 'true', '1')

insert into modalidade(nome_mod) values ('modalidade teste')
insert into modalidade(nome_mod) values ('Atividades de formação social, humana e cultural')
insert into modalidade(nome_mod) values ('Atividades de cunnho comunitário e de interesse coletivo')
insert into modalidade(nome_mod) values ('Atividades de ensino, de formação profissional, pesquisa, iniciação científica e tecnológica')



insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('teste1', '30', '10', '1');
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Participação em Eventos acadêmicos, científicos, culturais sociais (Congressos, seminários, palestras, semana universitária, conferencia, jornada, fórum etc.)', null, null, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Participação em Eventos acadêmicos, científicos, culturais sociais com atividade de apresentação de trabalho', 10, 10, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Participação em Eventos acadêmicos, científicos, culturais sociais como organizador', null, null, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Participação Eventos acadêmicos, científicos, culturais sociais com atividade de minicurso', null, null, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Ministrante de oficinas e ou de cursos vinculados aos projetos da Universidade', null, null, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Participação Eventos acadêmicos, científicos, culturais sociais com apresentação de pôster', 10, 10, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Estágios extracurriculares', null, null, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Representação estudantil', 8, 12, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Matrícula e aprovação em disciplinas de cursos superior não prevista na matriz curricular do curso', 10, 40, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Mostra Cultural', 10, 20, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Exposição artística cultural', 10, 20, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Apresentações: musicais e ou teatrais', 10, 20, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Organização de eventos artísticos culturais', 10, 10, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Bolsa Permanência', 10, 30, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Bolsa Ações Extensionistas', 10, 30, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Bolsa Desenvolvimento Institucional', 10, 30, 1);
insert into atividade_complementar(descricao_ac, ch_max_ac, ch_min_ac, modalidade_id_mod) values ('Bolsa Mobilidade Nacional', 10, 30, 1);
