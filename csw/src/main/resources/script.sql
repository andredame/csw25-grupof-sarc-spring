INSERT INTO users (id, username, email) VALUES ('fe18a691-e7f0-4c5c-964f-273382a34598','john','john@edu.pucrs.br');
INSERT INTO users (id, username, email) VALUES ('83e75657-dc1a-4656-879a-254e69862639','edgar dos santos','edgardossantos@edu.pucrs.br');
INSERT INTO users (id, username, email) VALUES ('79353a90-fd24-41de-b85d-9ff6ec3f5b10','andresilva','andresilva@edu.pucrs.br');
INSERT INTO users (id, username, email) VALUES ('3e111e87-bd14-4918-9594-1af9f4437b0b','maria eduarda','mariaeduarda@edu.pucrs.br');


-- Inserindo dados na tabela 'disciplina'
INSERT INTO disciplina ( codigo, nome, creditos, semestre, programa) VALUES 
( 'CS101', 'Introdução à Computação', 4, '2025/1', 'Programa de introdução à computação.'),
( 'CS102', 'Estruturas de Dados', 4, '2025/1', 'Programa de estruturas de dados.'),
( 'CS103', 'Banco de Dados', 4, '2025/1', 'Programa de banco de dados.'),
( 'CS104', 'Engenharia de Software', 4, '2025/1', 'Programa de engenharia de software.'),
( 'CS105', 'Redes de Computadores', 4, '2025/1', 'Programa de redes de computadores.');

-- Inserindo dados na tabela 'turma'
INSERT INTO turma ( numero, disciplina_id, semestre, professor_id, horario, vagas) VALUES 
( 'T01', 1, '2025/1', 'fe18a691-e7f0-4c5c-964f-273382a34598', 'Segunda 10:00-12:00', 30),
( 'T02', 2, '2025/1', 'fe18a691-e7f0-4c5c-964f-273382a34598', 'Terça 14:00-16:00', 25),
( 'T03', 3, '2025/1', 'fe18a691-e7f0-4c5c-964f-273382a34598', 'Quarta 08:00-10:00', 20),
( 'T04', 4, '2025/1', 'fe18a691-e7f0-4c5c-964f-273382a34598', 'Quinta 10:00-12:00', 35),
( 'T05', 5, '2025/1', 'fe18a691-e7f0-4c5c-964f-273382a34598', 'Sexta 14:00-16:00', 40);
-- Inserindo dados na tabela 'predio'
INSERT INTO predio ( nome, rua, numero, complemento, bairro, cidade, uf, cep) VALUES 
( 'Prédio A', 'Rua Principal', '100', 'Bloco A', 'Centro', 'Porto Alegre', 'RS', '90000-000'),
( 'Prédio B', 'Rua Secundária', '200', 'Bloco B', 'Centro', 'Porto Alegre', 'RS', '90001-000'),
( 'Prédio C', 'Rua Terciária', '300', 'Bloco C', 'Centro', 'Porto Alegre', 'RS', '90002-000'),
( 'Prédio D', 'Rua Quaternária', '400', 'Bloco D', 'Centro', 'Porto Alegre', 'RS', '90003-000'),
( 'Prédio E', 'Rua Quinária', '500', 'Bloco E', 'Centro', 'Porto Alegre', 'RS', '90004-000');

-- Inserindo dados na tabela 'sala'
INSERT INTO sala (nome, capacidade, andar, predio_id) VALUES 
('Sala 101', 40, '1º Andar', 1),
('Sala 102', 35, '1º Andar', 1),
('Sala 201', 30, '2º Andar', 2),
('Sala 202', 25, '2º Andar', 2),
('Sala 301', 20, '3º Andar', 3);

-- Inserindo dados na tabela 'aula'
INSERT INTO aula ( turma_id, data, sala_id) VALUES 
( 1, '2025-05-11', 1),
( 2, '2025-05-12', 2),
( 3, '2025-05-13', 3),
( 4, '2025-05-14', 4),
( 5, '2025-05-15', 5);

-- Inserindo dados na tabela 'recurso'
-- Inserindo dados na tabela 'tipo_recurso'
INSERT INTO tipo_recurso (nome) VALUES 
( 'Projetor'),
( 'Computador'),
( 'Quadro Branco'),
( 'Cadeiras Extras'),
( 'Microfone');

INSERT INTO recurso (status, id_tipo_recurso) VALUES 
( 'DISPONIVEL', 1),
( 'RESERVADO', 2),
( 'EM_MANUTENCAO', 3),
( 'DISPONIVEL', 4),
( 'RESERVADO', 5);


-- Inserindo dados na tabela 'reserva'
INSERT INTO reserva ( aula_id, recurso_id) VALUES 
( 1, 1),
( 2, 2),
( 3, 3),
( 4, 4),
( 5, 5);