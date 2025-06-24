INSERT INTO users (id, username, email) VALUES
('fe18a691-e7f0-4c5c-964f-273382a34598','john','john@edu.pucrs.br'), -- Professor 1
('0c13c1a7-8dbe-447f-bd1c-2ae0e7a931fc','roberto','roberto@edu.pucrs.br'), -- Professor 2
('83e75657-dc1a-4656-879a-254e69862639','edgar dos santos','edgardossantos@edu.pucrs.br'), -- Aluno 1
('79353a90-fd24-41de-b85d-9ff6ec3f5b10','andresilva','andresilva@edu.pucrs.br'), -- Aluno 2
('3e111e87-bd14-4918-9594-1af9f4437b0b','maria eduarda','mariaeduarda@edu.pucrs.br'), -- Coordenador (ou outro papel que não professor)
('300de63f-b5f4-499f-aeb0-b8d23eeea80b','vitor','vitor@edu.pucrs.br'), -- ALUNO (conforme sua especificação)
('7799d77f-3286-4364-a83b-c797a32de6e9','eduardo','eduardo@edu.pucrs.br'), -- Aluno 3
('55ba0910-db17-4763-bf2a-9978e09e2835','gabriel','gabriel@edu.pucrs.br'); -- Adicionado ';' aqui


-- Inserindo dados na tabela 'disciplina' (Adicionando mais disciplinas)
INSERT INTO disciplina ( codigo, nome, creditos, semestre, programa) VALUES
( 'CS101', 'Introdução à Computação', 4, '2025/1', 'Programa de introdução à computação.'),
( 'CS102', 'Estruturas de Dados', 4, '2025/1', 'Programa de estruturas de dados.'),
( 'CS103', 'Banco de Dados', 4, '2025/1', 'Programa de banco de dados.'),
( 'CS104', 'Engenharia de Software', 4, '2025/1', 'Programa de engenharia de software.'),
( 'CS105', 'Redes de Computadores', 4, '2025/1', 'Programa de redes de computadores.'),
( 'MA201', 'Cálculo I', 6, '2025/1', 'Fundamentos de cálculo diferencial e integral.'),
( 'PH101', 'Física Geral', 4, '2025/1', 'Introdução aos conceitos básicos de física.'),
( 'CS106', 'Inteligência Artificial', 6, '2025/2', 'Fundamentos de IA e aprendizado de máquina.'),
( 'EL301', 'Eletrônica Digital', 4, '2025/2', 'Circuitos lógicos e sistemas digitais.'); -- Adicionado ';' aqui


-- Inserindo dados na tabela 'predio' (Adicionando mais prédios)
INSERT INTO predio ( nome, rua, numero, complemento, bairro, cidade, uf, cep) VALUES
( 'Prédio A', 'Rua Principal', '100', 'Bloco A', 'Centro', 'Porto Alegre', 'RS', '90000-000'),
( 'Prédio B', 'Rua Secundária', '200', 'Bloco B', 'Centro', 'Porto Alegre', 'RS', '90001-000'),
( 'Prédio C', 'Rua Terciária', '300', 'Bloco C', 'Centro', 'Porto Alegre', 'RS', '90002-000'),
( 'Prédio D', 'Rua Quaternária', '400', 'Bloco D', 'Centro', 'Porto Alegre', 'RS', '90003-000'),
( 'Prédio E', 'Rua Quinária', '500', 'Bloco E', 'Centro', 'Porto Alegre', 'RS', '90004-000'),
( 'Prédio F', 'Avenida Central', '600', 'Anexo', 'Boa Vista', 'Porto Alegre', 'RS', '90005-000'),
( 'Prédio G', 'Praça da Liberdade', '75', NULL, 'Cidade Baixa', 'Porto Alegre', 'RS', '90006-000'); -- Adicionado ';' aqui


-- Inserindo dados na tabela 'sala' (Adicionando mais salas e vinculando a novos prédios)
INSERT INTO sala (nome, capacidade, andar, predio_id) VALUES
('Sala 101', 40, '1º Andar', 1),
('Sala 102', 35, '1º Andar', 1),
('Sala 201', 30, '2º Andar', 2),
('Sala 202', 25, '2º Andar', 2),
('Sala 301', 20, '3º Andar', 3),
('Laboratório 1', 20, 'Térreo', 4),
('Auditório Principal', 150, '1º Andar', 4),
('Sala de Reuniões', 10, '2º Andar', 5),
('Sala de Aula 401', 50, '4º Andar', 1),
('Lab Química', 25, 'Térreo', 6),
('Sala de Estudo 1', 15, '1º Andar', 7),
('Sala de Aula 501', 30, '5º Andar', 1); -- Adicionado ';' aqui


-- Inserindo dados na tabela 'turma' (Adicionando mais turmas, atribuindo a John e Roberto)
INSERT INTO turma ( numero, disciplina_id, semestre, professor_id, horario, vagas) VALUES
( 'T01', 1, '2025/1', 'fe18a691-e7f0-4c5c-964f-273382a34598', 'Segunda 10:00-12:00', 30), -- John
( 'T02', 2, '2025/1', 'fe18a691-e7f0-4c5c-964f-273382a34598', 'Terça 14:00-16:00', 25), -- John
( 'T03', 3, '2025/1', '0c13c1a7-8dbe-447f-bd1c-2ae0e7a931fc', 'Quarta 08:00-10:00', 20), -- Roberto
( 'T04', 4, '2025/1', 'fe18a691-e7f0-4c5c-964f-273382a34598', 'Quinta 10:00-12:00', 35), -- John
( 'T05', 5, '2025/1', '0c13c1a7-8dbe-447f-bd1c-2ae0e7a931fc', 'Sexta 14:00-16:00', 40), -- Roberto
( 'T06', 6, '2025/1', 'fe18a691-e7f0-4c5c-964f-273382a34598', 'Segunda 08:00-10:00', 28), -- John (Cálculo I)
( 'T07', 7, '2025/1', '0c13c1a7-8dbe-447f-bd1c-2ae0e7a931fc', 'Terça 10:00-12:00', 22), -- Roberto (Física Geral)
( 'T08', 8, '2025/2', 'fe18a691-e7f0-4c5c-964f-273382a34598', 'Quarta 14:00-16:00', 30), -- John (IA)
( 'T09', 9, '2025/2', '0c13c1a7-8dbe-447f-bd1c-2ae0e7a931fc', 'Quinta 08:00-10:00', 25); -- Adicionado ';' aqui


-- Inserindo dados na tabela 'turma_aluno' (Adicionando alunos a turmas, incluindo Vitor)
INSERT INTO turma_aluno (turma_id, aluno_id) VALUES
(1, '79353a90-fd24-41de-b85d-9ff6ec3f5b10'), -- Turma T01, Aluno Andresilva
(1, '300de63f-b5f4-499f-aeb0-b8d23eeea80b'), -- Turma T01, Aluno Vitor
(2, '55ba0910-db17-4763-bf2a-9978e09e2835'), -- Turma T02, Aluno Gabriel
(3, '7799d77f-3286-4364-a83b-c797a32de6e9'), -- Turma T03, Aluno Eduardo
(3, '300de63f-b5f4-499f-aeb0-b8d23eeea80b'), -- Turma T03, Aluno Vitor
(4, '79353a90-fd24-41de-b85d-9ff6ec3f5b10'), -- Turma T04, Aluno Andresilva
(7, '300de63f-b5f4-499f-aeb0-b8d23eeea80b'), -- Turma T07, Aluno Vitor
(8, '7799d77f-3286-4364-a83b-c797a32de6e9'); -- Adicionado ';' aqui


-- Inserindo dados na tabela 'aula' (Adicionando mais aulas para diversas turmas e salas)
-- Datas em formato 'YYYY-MM-DD'
INSERT INTO aula ( turma_id, data, sala_id, descricao, periodo) VALUES
-- Aulas existentes
( 1, '2025-05-11', 1, 'Aula de Introdução à Computação - Parte 1','JK'),
( 1, '2025-05-11', 2, 'Aula de Introdução à Computação - Exercícios','LM'),
( 2, '2025-05-12', 1, 'Aula de Estruturas de Dados - Introdução','JK'),
( 2, '2025-05-12', 2, 'Aula de Estruturas de Dados - Algoritmos','JK'),
( 3, '2025-05-13', 3, 'Aula de Banco de Dados - Modelagem','LM'),
( 4, '2025-05-14', 4, 'Aula de Engenharia de Software - Ciclos de Vida','LM'),
( 5, '2025-05-15', 5, 'Aula de Redes de Computadores - Camada Física','LM'),
( 1, '2025-05-16', 1, 'Aula de Introdução à Computação - Revisão','JK'),
( 2, '2025-05-17', 2, 'Aula de Estruturas de Dados - Árvores','JK'),
( 3, '2025-05-18', 3, 'Aula de Banco de Dados - SQL Básico','LM'),
( 4, '2025-05-19', 4, 'Aula de Engenharia de Software - Agile','LM'),
( 5, '2025-05-20', 5, 'Aula de Redes de Computadores - Topologias','LM'),
-- Novas Aulas
( 6, '2025-05-26', 9, 'Aula de Cálculo I - Limites','AB'), -- Turma T06, Sala 401
( 6, '2025-05-28', 9, 'Aula de Cálculo I - Derivadas','CD'),
( 7, '2025-05-27', 6, 'Aula de Física Geral - Leis de Newton','JK'), -- Turma T07, Laboratório 1
( 7, '2025-05-29', 6, 'Aula de Física Geral - Energia','LM'),
( 8, '2025-06-03', 12, 'Aula de IA - Redes Neurais','NP'), -- Turma T08, Sala 501
( 8, '2025-06-05', 12, 'Aula de IA - Machine Learning','NP'),
( 9, '2025-06-04', 10, 'Aula de Eletrônica Digital - Portas Lógicas','AB'), -- Turma T09, Lab Química
( 9, '2025-06-06', 10, 'Aula de Eletrônica Digital - Flip-Flops','CD'),
( 1, '2025-06-01', 1, 'Aula extra de Computação','JK'), -- Turma T01, Sala 101, Data futura
( 2, '2025-06-02', 2, 'Revisão de Estruturas de Dados','LM'); -- Adicionado ';' aqui


-- Inserindo dados na tabela 'tipo_recurso' (Adicionando mais tipos de recurso)
INSERT INTO tipo_recurso (nome) VALUES
( 'Projetor'),
( 'Computador'),
( 'Quadro Branco'),
( 'Cadeiras Extras'),
( 'Microfone'),
( 'Lousa Interativa'),
( 'Impressora 3D'),
( 'Kit Robótica'),
( 'Câmera Filmadora'); -- Adicionado ';' aqui


-- Inserindo dados na tabela 'recurso' (Adicionando mais recursos e variando o status)
INSERT INTO recurso (status, id_tipo_recurso) VALUES
( 'DISPONIVEL', 1), -- Projetor 1
( 'DISPONIVEL', 2), -- Computador 1
( 'EM_MANUTENCAO', 3), -- Quadro Branco 1
( 'DISPONIVEL', 4), -- Cadeiras Extras 1
( 'DISPONIVEL', 5), -- Microfone 1
( 'DISPONIVEL', 1), -- Projetor 2
( 'EM_MANUTENCAO', 2), -- Computador 2 (em manutenção)
( 'DISPONIVEL', 6), -- Lousa Interativa 1
( 'DISPONIVEL', 7), -- Impressora 3D 1
( 'DISPONIVEL', 8), -- Kit Robótica 1
( 'DISPONIVEL', 9); -- Adicionado ';' aqui


-- Inserindo dados na tabela 'reserva' (Adicionando mais reservas)
INSERT INTO reserva ( aula_id, recurso_id) VALUES
( 1, 1), -- Aula 1, Projetor 1
( 2, 2), -- Aula 2, Computador 1
( 3, 4), -- Aula 3, Cadeiras Extras 1 (Recurso 3 está EM_MANUTENCAO)
( 4, 5), -- Aula 4, Microfone 1
( 5, 6), -- Aula 5, Projetor 2
( 13, 8), -- Aula 13 (Cálculo I), Lousa Interativa 1
( 15, 10),-- Aula 15 (Física Geral), Kit Robótica 1
( 17, 9); -- Adicionado ';' aqui